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

package com.github.lero4ka16.te4j.template.parse;

import com.github.lero4ka16.te4j.modifiable.watcher.ModifyWatcherManager;
import com.github.lero4ka16.te4j.template.PlainTemplate;
import com.github.lero4ka16.te4j.template.Template;
import com.github.lero4ka16.te4j.template.context.TemplateContext;
import com.github.lero4ka16.te4j.template.path.TemplatePath;
import com.github.lero4ka16.te4j.util.type.ref.ITypeRef;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

/**
 * @author lero4ka16
 */
public final class PlainParsedTemplate extends ParsedTemplate {

    public PlainParsedTemplate(TemplateContext context,
                               byte[] content, int offset, int length) {
        super(context, content, offset, length);
    }

    @Override
    public <BoundType> Template<BoundType> compile(ModifyWatcherManager modifyWatcherManager,
                                                   String parentFile, String file,
                                                   ITypeRef<BoundType> type) {
        Template<BoundType> result = new PlainTemplate<>(content, offset, length);

        if (modifyWatcherManager != null) {
            result = Template.wrapHotReloading(modifyWatcherManager, context, result, type, file);
        }

        return result;
    }

    @Override
    public boolean hasPaths() {
        return false;
    }

    @Override
    public List<TemplatePath> getPaths() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return "Template.Plain[" + new String(content, offset, length, StandardCharsets.UTF_8) + "]";
    }
}