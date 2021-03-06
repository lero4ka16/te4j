/*
 *    Copyright 2021 Whilein
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

package te4j.template.path;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.NoSuchElementException;

/**
 * @author whilein
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class DefaultTemplatePathIterator implements TemplatePathIterator {

    final String path;

    int startIndex;
    int endIndex;

    public static @NonNull TemplatePathIterator create(@NonNull String path) {
        return new DefaultTemplatePathIterator(path);
    }

    @Override
    public void prev() {
        if (!hasPrev()) {
            throw new NoSuchElementException();
        }

        endIndex = startIndex;
        startIndex = path.lastIndexOf('.', startIndex - 2) + 1;
    }

    @Override
    public boolean hasPrev() {
        return startIndex != 0 || endIndex == -1;
    }

    @Override
    public @NonNull String getPath() {
        return path;
    }

    @Override
    public String next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        try {
            endIndex = path.indexOf('.', startIndex + 1);
            return path.substring(startIndex, endIndex == -1 ? path.length() : endIndex);
        } finally {
            startIndex = endIndex + 1;
        }
    }

    @Override
    public boolean hasNext() {
        return endIndex != -1;
    }

    @Override
    public String toString() {
        return path;
    }

}
