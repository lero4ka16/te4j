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

package com.github.lero4ka16.te4j.template.method.impl;

import com.github.lero4ka16.te4j.template.ParsedTemplate;
import com.github.lero4ka16.te4j.template.method.TemplateMethod;
import com.github.lero4ka16.te4j.template.method.TemplateMethodType;
import lombok.Data;

/**
 * @author lero4ka16
 */
@Data
public class ForeachMethod implements TemplateMethod {

    private final String path;
    private final String as;

    private final ParsedTemplate block;

    @Override
    public TemplateMethodType getType() {
        return TemplateMethodType.FOR;
    }

    @Override
    public String toString() {
        return "Foreach[" + path + " : " + as + "; block=" + block + "]";
    }

}
