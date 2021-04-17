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

package te4j.util.compiler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Collection;

/**
 * @author lero4ka16
 */
public interface RuntimeCompiler {

    @NotNull String getClassName();

    @Nullable String getSuperclass();

    void setSuperclass(@Nullable String superclass);

    @Nullable Collection<String> getInterfaces();

    void setInterfaces(@Nullable Collection<String> interfaces);

    @NotNull StringBuilder getContent();

    @NotNull Class<?> compile() throws IOException;

}