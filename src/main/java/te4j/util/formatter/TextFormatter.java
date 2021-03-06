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

package te4j.util.formatter;

import te4j.template.option.minify.Minify;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public final class TextFormatter {

    private final byte[] buf;
    private final int off;
    private final int len;

    private Set<Minify> minifyOptions;
    private boolean escaping = true;

    public TextFormatter(byte[] buf, int off, int len) {
        this.buf = buf;
        this.off = off;
        this.len = len;
    }

    public TextFormatter(byte[] buf) {
        this(buf, 0, buf.length);
    }

    public TextFormatter(String value) {
        this(value.getBytes(StandardCharsets.UTF_8), 0, value.length());
    }

    public TextFormatter minify(Set<Minify> minifyOptions) {
        this.minifyOptions = minifyOptions;
        return this;
    }

    public TextFormatter disableEscaping() {
        escaping = false;
        return this;
    }

    public byte[] formatAsBytes() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            write(baos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return baos.toByteArray();
    }

    public String format() {
        return new String(formatAsBytes());
    }

    public void write(Appendable out) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        write(baos);

        out.append(baos.toString());
    }

    public void write(OutputStream out) throws IOException {
        boolean insertSpace = false;

        for (int i = 0; i < len; i++) {
            int ch = buf[i + off];

            if (insertSpace && ch != ' ') {
                out.write(' ');
                insertSpace = false;
            }

            switch (ch) {
                case '"':
                    if (escaping) {
                        out.write('\\');
                        out.write(ch);
                        continue;
                    }

                    break;
                case '\\':
                    if (escaping) {
                        out.write(ch);
                        out.write(ch);
                        continue;
                    }

                    break;
                case '\n':
                    if (minifyOptions.contains(Minify.DEL_LF)) {
                        continue;
                    }

                    if (escaping) {
                        out.write('\\');
                        out.write('n');
                        continue;
                    }
                case '\t':
                    if (minifyOptions.contains(Minify.DEL_TAB)) {
                        continue;
                    }

                    if (escaping) {
                        out.write('\\');
                        out.write('t');
                        continue;
                    }
                case '\r':
                    if (minifyOptions.contains(Minify.DEL_CR)) {
                        continue;
                    }

                    if (escaping) {
                        out.write('\\');
                        out.write('r');
                        continue;
                    }
                case ' ':
                    if (minifyOptions.contains(Minify.DEL_REPEATING_SPACES)) {
                        insertSpace = true;
                        continue;
                    }
                    break;
            }

            out.write(ch);
        }

        if (insertSpace) {
            out.write(' ');
        }
    }

}
