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

package te4j.template.reader;

import lombok.NonNull;
import te4j.include.DefaultIncludePath;
import te4j.template.exception.TemplateException;
import te4j.template.exception.TemplateUnexpectedTokenException;
import te4j.template.method.TemplateMethod;
import te4j.template.method.TemplateMethodType;
import te4j.template.method.impl.ConditionMethod;
import te4j.template.method.impl.ForeachMethod;
import te4j.template.method.impl.IncludeMethod;
import te4j.template.method.impl.SwitchCaseMethod;
import te4j.template.method.impl.ValueMethod;
import te4j.template.option.minify.Minify;
import te4j.template.parser.EmptyParsedTemplate;
import te4j.template.parser.ParsedTemplate;
import te4j.template.parser.PlainParsedTemplate;
import te4j.template.parser.StandardParsedTemplate;
import te4j.template.parser.token.ImmutableToken;
import te4j.template.parser.token.ImmutableTokenizedTemplate;
import te4j.template.parser.token.Token;
import te4j.template.parser.token.TokenType;
import te4j.template.parser.token.TokenizedTemplate;
import te4j.template.path.DefaultTemplatePath;
import te4j.template.path.TemplatePath;
import te4j.util.formatter.TextFormatter;
import te4j.util.io.BytesReader;
import te4j.util.io.DataReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class DefaultTemplateReader implements TemplateReader {

    private final Set<Minify> minifyOptions;

    private final byte[] value;
    private final DataReader reader;

    private DefaultTemplateReader(byte[] value, Set<Minify> minifyOptions, DataReader reader) {
        this.value = value;
        this.minifyOptions = minifyOptions;
        this.reader = reader;
    }

    public static TemplateReader create(byte @NonNull [] value, @NonNull Set<Minify> minifyOptions) {
        Objects.requireNonNull(value, "value");
        Objects.requireNonNull(minifyOptions, "minifyOptions");

        return new DefaultTemplateReader(value, minifyOptions, new BytesReader(value));
    }

    private ParsedTemplate newTemplate(List<TemplatePath> paths, int begin, int end, boolean inner) {
        ParsedTemplate template;

        if (paths.isEmpty()) {
            if (!inner) {
                byte[] processed = new TextFormatter(value, begin, end - begin)
                        .disableEscaping()
                        .minify(minifyOptions)
                        .formatAsBytes();

                if (processed.length == 0) {
                    template = EmptyParsedTemplate.getInstance();
                } else {
                    template = PlainParsedTemplate.create(processed, 0, processed.length);
                }
            } else {
                template = PlainParsedTemplate.create(value, begin, end - begin);
            }
        } else {
            template = StandardParsedTemplate.create(paths, value, begin, end - begin);
        }

        return template;
    }

    @Override
    public @NonNull ParsedTemplate readTemplate() {
        int begin = reader.position();

        List<TemplatePath> paths = new ArrayList<>();
        readPathsTo(paths);

        int end = reader.position();

        return newTemplate(paths, begin, end, false);
    }

    private void readPathsTo(List<TemplatePath> paths) {
        int value;

        while ((value = reader.read()) != -1) {
            int position = reader.position();
            TemplatePath path;

            switch (value) {
                case '^': // possible value begin
                    reader.roll();
                    path = readValue();
                    break;
                case '<': // possible operation begin
                    reader.roll();
                    path = readOperation();
                    break;
                default:
                    continue;
            }

            if (path == null) {
                reader.position(position);
            } else {
                paths.add(path);
            }
        }
    }

    private TemplatePath readValue() {
        int pathBegin = reader.position();
        int startA = reader.read();
        int startB = reader.read();

        // должно быть ^^, без пробелов и других знаков
        if (startA != startB) return null;

        int valueBegin = reader.position();

        for (; ; ) {
            int value = reader.read();
            if (value == -1) return null;

            if (value == '^') {
                if (reader.read() == '^')
                    break;
                else
                    reader.roll();

                if (valueBegin + 1 == reader.position()) {
                    valueBegin++;
                    pathBegin++;
                }
            }
        }

        int pathEnd = reader.position();
        int valueEnd = pathEnd - 2; // }}

        return DefaultTemplatePath.create(
                pathBegin, pathEnd - pathBegin,
                ValueMethod.create(reader.substring(valueBegin, valueEnd).trim())
        );
    }

    private TokenizedTemplate readInner(TokenType... types) throws TemplateUnexpectedTokenException {
        int blockBegin = reader.position();
        int blockEnd;

        List<TemplatePath> innerPaths = new ArrayList<>();
        Token token;

        try {
            readPathsTo(innerPaths);

            // исключение должно обязательно прилететь
            throw new IllegalStateException("No inner block found");
        } catch (TemplateUnexpectedTokenException e) {
            token = e.getToken();
            blockEnd = e.getPosition();

            token.expect(blockEnd, types);
        }

        return ImmutableTokenizedTemplate.builder()
                .template(newTemplate(innerPaths, blockBegin, blockEnd, true))
                .token(token)
                .build();
    }

    private TemplatePath readOperation() throws TemplateUnexpectedTokenException {
        int begin = reader.position();

        Token token = readToken(reader);
        if (token == null) return null;

        token.expect(begin, TokenType.BEGIN);

        String path = token.getPath();
        TemplateMethodType methodType = TemplateMethodType.findType(token.getMethod());
        TemplateMethod method;

        switch (methodType) {
            default:
                throw new IllegalArgumentException("unknown method: " + token.getMethod());
            case INCLUDE: {
                method = IncludeMethod.create(DefaultIncludePath.create(path));
                break;
            }
            case FOR: {
                int separator = path.indexOf(':');

                if (separator == -1) {
                    throw new IllegalStateException("Not found ':' in " + path);
                }

                String as = path.substring(0, separator).trim();
                String value = path.substring(separator + 1).trim();

                method = ForeachMethod.create(value, as, readInner(TokenType.END_FOR).getTemplate());
                break;
            }
            case CASE: {
                int separator = path.indexOf(':');

                String value;
                String from;

                if (separator == -1) {
                    value = path;
                    from = null;
                } else {
                    value = path.substring(0, separator).trim();
                    from = path.substring(separator + 1).trim();
                }

                TokenizedTemplate inner = readInner(TokenType.CASE_DEFAULT, TokenType.END_CASE);

                switch (inner.getToken().getType()) {
                    case END_CASE:
                        method = SwitchCaseMethod.create(value, from, inner.getTemplate(), null);
                        break;
                    case CASE_DEFAULT:
                        method = SwitchCaseMethod.create(value, from, inner.getTemplate(),
                                readInner(TokenType.END_CASE).getTemplate());
                        break;
                    default:
                        throw new IllegalStateException("Unexpected token: " + inner.getToken().getType());
                }

                break;
            }
            case CONDITION:
                method = readCondition(path);
                break;
        }

        return DefaultTemplatePath.create(begin, reader.position() - begin, method);
    }

    private TemplateMethod readCondition(String path) {
        TokenizedTemplate inner = readInner(TokenType.ELSE, TokenType.ELIF, TokenType.END_IF);

        switch (inner.getToken().getType()) {
            case ELSE:
                return ConditionMethod.create(path, inner.getTemplate(),
                        readInner(TokenType.END_IF).getTemplate());
            case ELIF:
                return ConditionMethod.create(path, inner.getTemplate(),
                        readCondition(inner.getToken().getPath()));
            case END_IF:
                return ConditionMethod.create(path, inner.getTemplate(),
                        (ParsedTemplate) null);
            default:
                throw new IllegalStateException("Unexpected token: " + inner.getToken().getType());
        }
    }

    private Token readToken(DataReader in) {
        if (in.read() != '<' || in.read() != '*') { // <*
            return null;
        }

        // читаем первый символ, который не является пробелом
        if (!in.moveNonWhitespace()) return null;

        int start = in.position();

        for (; ; ) {
            if (!in.move('*')) {
                throw new TemplateException("Not found closing for token");
            }

            in.next();

            int ch = in.readNonWhitespace();
            if (ch == -1) return null;
            if (ch == '>') break;
        }

        int end = in.position() - 2; // *>

        String fullPath = in.substring(start, end).trim();

        int separator = fullPath.indexOf(' ');

        String method;
        String path;

        if (separator == -1) {
            method = fullPath;
            path = "";
        } else {
            method = fullPath.substring(0, separator);
            path = fullPath.substring(separator + 1);
        }

        return ImmutableToken.builder()
                .method(method)
                .path(path)
                .type(TokenType.getType(method))
                .build();
    }

}