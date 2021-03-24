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

package com.github.lero4ka16.te4j.template.reader.token;

import com.github.lero4ka16.te4j.template.exception.TemplateUnexpectedTokenException;

/**
 * @author lero4ka16
 */
public class TemplateToken {

    private final String value;
    private final TemplateTokenType type;

    public TemplateToken(String value, TemplateTokenType type) {
        this.value = value;
        this.type = type;
    }

    public void expect(int position, TemplateTokenType... types) throws TemplateUnexpectedTokenException {
        for (TemplateTokenType expectType : types) {
            if (expectType == type) return;
        }

        throw new TemplateUnexpectedTokenException(types, this, position);
    }

    public String getValue() {
        return value;
    }

    public TemplateTokenType getType() {
        return type;
    }

}