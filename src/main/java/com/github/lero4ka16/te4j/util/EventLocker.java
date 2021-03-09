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

package com.github.lero4ka16.te4j.util;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author lero4ka16
 */
public class EventLocker {

    private final AtomicBoolean locked = new AtomicBoolean();

    public void lock() {
        this.locked.set(true);
    }

    public void unlock() {
        this.locked.set(false);

        synchronized (this) {
            notifyAll();
        }
    }

    public boolean isLocked() {
        return locked.get();
    }

    public void awaitUnlock() {
        if (isLocked()) {
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
