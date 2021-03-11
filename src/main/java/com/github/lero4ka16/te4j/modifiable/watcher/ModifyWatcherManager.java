/*
 *    Copyright 2021 Lero4ka16
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.github.lero4ka16.te4j.modifiable.watcher;

import com.github.lero4ka16.te4j.modifiable.Modifiable;
import com.github.lero4ka16.te4j.modifiable.ModifiableReference;

import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

public final class ModifyWatcherManager {

    private final WatchService watcher;

    private final ReferenceQueue<Modifiable> queue = new ReferenceQueue<>();
    private final Map<Path, ModifyWatcherDirectory> directories = new HashMap<>();

    public ModifyWatcherManager() {
        try {
            watcher = FileSystems.getDefault().newWatchService();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ModifyWatcherThread thread = new ModifyWatcherThread(this);
        thread.start();
    }

    public WatchService getWatcher() {
        return watcher;
    }

    private void cleanup() {
        Reference<? extends Modifiable> reference;

        while ((reference = queue.poll()) != null) {
            ModifiableReference modifiable = (ModifiableReference) reference;

            for (Path path : modifiable.getFiles()) {
                Path abs = path.toAbsolutePath();
                Path absParent = abs.getParent();

                ModifyWatcherDirectory directory = directories.get(absParent);
                directory.removeFile(abs);

                if (!directory.hasFiles()) {
                    directory.remove();
                    directories.remove(absParent);
                }
            }
        }
    }

    public synchronized void handle(Path path) {
        cleanup();

        Path abs = path.toAbsolutePath();
        Path absParent = abs.getParent();

        ModifyWatcherDirectory directory = directories.get(absParent);
        if (directory == null) return;

        ModifiableReference file = directory.getFile(abs);
        if (file == null) return;

        file.handleModify();
    }

    public synchronized void register(Modifiable modifiable) {
        ModifiableReference reference = new ModifiableReference(modifiable, queue);

        for (Path path : modifiable.getFiles()) {
            Path abs = path.toAbsolutePath();
            Path absParent = abs.getParent();

            directories.computeIfAbsent(absParent, parent -> {
                try {
                    return new ModifyWatcherDirectory(parent.register(this.watcher, ENTRY_MODIFY));
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }).addFile(abs, reference);
        }
    }

}
