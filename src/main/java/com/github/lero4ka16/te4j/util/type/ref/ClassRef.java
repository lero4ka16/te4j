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

package com.github.lero4ka16.te4j.util.type.ref;

import java.lang.reflect.Type;

/**
 * @author lero4ka16
 */
public final class ClassRef<T> implements ITypeRef<T> {

    private final Class<T> cls;

    public ClassRef(Class<T> cls) {
        this.cls = cls;
    }

    @Override
    public Type getType() {
        return cls;
    }

    @Override
    public Class<T> getRawType() {
        return cls;
    }

    @Override
    public String getSimpleName() {
        return cls.getSimpleName();
    }

    @Override
    public String getCanonicalName() {
        return cls.getCanonicalName();
    }
}