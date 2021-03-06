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

package te4j.util.compiler;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Collection;

/**
 * @author whilein
 */
public interface RuntimeCompiler {

    @NonNull String getClassName();

    @Nullable String getSuperclass();

    void setSuperclass(@Nullable String superclass);

    @NotNull Collection<@NotNull String> getInterfaces();

    void setInterfaces(@NotNull Collection<@NotNull String> interfaces);

    @NotNull StringBuilder getContent();

    @NotNull Class<?> compile() throws IOException;

}
