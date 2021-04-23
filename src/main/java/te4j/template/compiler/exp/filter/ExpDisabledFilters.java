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

package te4j.template.compiler.exp.filter;

import lombok.NonNull;
import te4j.filter.Filters;

import java.lang.reflect.Type;

/**
 * @author lero4ka16
 */
public final class ExpDisabledFilters implements ExpFilters {

    public static final ExpFilters INSTANCE = new ExpDisabledFilters();

    @Override
    public Type getType() {
        return null;
    }

    @Override
    public @NonNull String format(@NonNull String accessor) {
        return accessor;
    }

    @Override
    public void add(@NonNull Filters filters, @NonNull String name) {
        throw new UnsupportedOperationException();
    }
}