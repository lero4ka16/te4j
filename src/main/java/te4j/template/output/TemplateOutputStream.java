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

package te4j.template.output;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import te4j.template.exception.TemplateException;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author whilein
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class TemplateOutputStream extends AbstractTemplateOutput {

    private final OutputStream os;

    public static @NonNull TemplateOutput create(@NonNull OutputStream os) {
        return new TemplateOutputStream(os);
    }

    @Override
    public void write(byte[] b) {
        try {
            os.write(b);
        } catch (IOException e) {
            throw new TemplateException("Cannot write to output stream", e);
        }
    }

    @Override
    public void write(byte[] b, int off, int len) {
        try {
            os.write(b, off, len);
        } catch (IOException e) {
            throw new TemplateException("Cannot write to output stream", e);
        }
    }

    @Override
    public void write(int b) {
        try {
            os.write(b);
        } catch (IOException e) {
            throw new TemplateException("Cannot write to output stream", e);
        }
    }

    @Override
    public byte[] toByteArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException();
    }

}
