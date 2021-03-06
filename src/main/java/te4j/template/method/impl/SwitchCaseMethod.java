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

package te4j.template.method.impl;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import te4j.template.method.TemplateMethod;
import te4j.template.method.TemplateMethodType;
import te4j.template.parser.ParsedTemplate;

/**
 * @author whilein
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class SwitchCaseMethod implements TemplateMethod {

    String value;
    String from;

    ParsedTemplate block;
    ParsedTemplate defaultBlock;

    public static TemplateMethod create(
            final @NonNull String value,
            final String from,
            final @NonNull ParsedTemplate block,
            final ParsedTemplate defaultBlock
    ) {
        return new SwitchCaseMethod(value, from, block, defaultBlock);
    }

    @Override
    public TemplateMethodType getType() {
        return TemplateMethodType.CASE;
    }
}
