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

package te4j.util.hash;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * @author lero4ka16
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class ByteArrayHash implements Hash {

    private final int hash;
    private final byte[] value;

    public static Hash create(@NonNull byte[] value) {
        return new ByteArrayHash(Arrays.hashCode(value), value);
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ByteArrayHash)) return false;

        ByteArrayHash that = (ByteArrayHash) obj;
        return Arrays.equals(value, that.value);
    }

}
