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

package te4j.template.option.style;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author lero4ka16
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MutableTemplateStyle implements TemplateStyle {

    private final char[] styles = new char[StyleAspect.LENGTH];

    /**
     * Creates new template style with defaults
     *
     * @return New template style
     */
    public static @NonNull TemplateStyle create() {
        return new MutableTemplateStyle()
                .style(StyleAspect.BEGIN_METHOD, '<')
                .style(StyleAspect.END_METHOD, '>')
                .style(StyleAspect.METHOD_MARKER, '*')
                .style(StyleAspect.BEGIN_VALUE, '^')
                .style(StyleAspect.END_VALUE, '^');
    }

    @Override
    public @NonNull TemplateStyle style(@NonNull StyleAspect aspect, char value) {
        styles[aspect.ordinal()] = value;
        return this;
    }

    @Override
    public char style(@NonNull StyleAspect aspect) {
        return styles[aspect.ordinal()];
    }
}