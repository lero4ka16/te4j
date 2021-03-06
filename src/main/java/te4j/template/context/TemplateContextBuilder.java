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

package te4j.template.context;

import org.jetbrains.annotations.NotNull;
import te4j.filter.Filters;
import te4j.modifiable.watcher.ModifyWatcherManager;
import te4j.template.option.minify.Minify;
import te4j.template.option.output.Output;
import te4j.template.resolver.TemplateResolver;

import java.util.Collection;

/**
 * @author whilein
 */
public interface TemplateContextBuilder {

    @NotNull TemplateContextBuilder filters(@NotNull Filters filters);

    @NotNull TemplateContextBuilder resolver(@NotNull TemplateResolver resolver);

    @NotNull TemplateContextBuilder output(@NotNull Output @NotNull ... types);

    @NotNull TemplateContextBuilder output(@NotNull Collection<@NotNull Output> types);

    @NotNull TemplateContextBuilder outputAll();

    @NotNull TemplateContextBuilder disableAutoReloading();

    @NotNull TemplateContextBuilder enableAutoReloading(@NotNull ModifyWatcherManager modifyWatcherManager);

    @NotNull TemplateContextBuilder enableAutoReloading();

    @NotNull TemplateContextBuilder useResources();

    @NotNull TemplateContextBuilder minify(@NotNull Minify @NotNull ... options);

    @NotNull TemplateContextBuilder minify(@NotNull Collection<@NotNull Minify> options);

    @NotNull TemplateContextBuilder minifyAll();

    @NotNull TemplateContext build();

}
