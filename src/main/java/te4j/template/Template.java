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

package te4j.template;

import org.jetbrains.annotations.NotNull;
import te4j.template.output.TemplateOutputBuffer;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author lero4ka16
 */
public interface Template<T> {

    ThreadLocal<TemplateOutputBuffer> bytesOptimized
            = ThreadLocal.withInitial(TemplateOutputBuffer::new);

    ThreadLocal<StringBuilder> stringOptimized
            = ThreadLocal.withInitial(StringBuilder::new);

    @NotNull String[] getIncludes();

    @NotNull String renderAsString(@NotNull T object);

    byte @NotNull [] renderAsBytes(@NotNull T object);

    void renderTo(@NotNull T object, @NotNull OutputStream os) throws IOException;

}