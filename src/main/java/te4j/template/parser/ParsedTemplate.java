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

package te4j.template.parser;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import te4j.filter.Filters;
import te4j.modifiable.watcher.ModifyWatcherManager;
import te4j.template.Template;
import te4j.template.context.loader.TemplateLoader;
import te4j.template.context.parser.TemplateParser;
import te4j.template.option.minify.Minify;
import te4j.template.option.output.Output;
import te4j.template.path.TemplatePath;
import te4j.template.source.TemplateSource;

import java.util.List;
import java.util.Set;

/**
 * @author whilein
 */
public interface ParsedTemplate {

    int getOffset();

    int getLength();

    byte @NotNull [] getRawContent();

    byte @NotNull [] getContent();

    <T> @NotNull Template<T> compile(
            @NotNull Filters filters,
            @Nullable ModifyWatcherManager modifyWatcherManager,
            @NotNull TemplateParser parser,
            @NotNull Set<Output> outputTypes,
            @NotNull Set<Minify> minifyOptions,
            @NotNull String parentFile,
            @NotNull TemplateSource src,
            @NotNull TemplateLoader<T> loader
    );

    boolean hasPaths();

    @NotNull List<@NotNull TemplatePath> getPaths();

}
