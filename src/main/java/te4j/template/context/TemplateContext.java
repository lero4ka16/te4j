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

package te4j.template.context;

import lombok.NonNull;
import te4j.filter.Filters;
import te4j.modifiable.watcher.ModifyWatcherManager;
import te4j.template.context.loader.TemplateLoader;
import te4j.template.context.parser.TemplateParser;
import te4j.template.option.minify.Minify;
import te4j.template.option.output.Output;
import te4j.util.type.ref.TypeReference;

import java.util.Set;

/**
 * @author lero4ka16
 */
public interface TemplateContext {

    @NonNull <T> TemplateLoader<T> load(@NonNull TypeReference<T> type);

    @NonNull <T> TemplateLoader<T> load(@NonNull Class<T> cls);

    @NonNull <T> TemplateLoader<T> load(@NonNull TypeReference<T> type, boolean enableAutoReloading);

    @NonNull <T> TemplateLoader<T> load(@NonNull Class<T> cls, boolean enableAutoReloading);

    @NonNull TemplateParser parse();

    @NonNull Filters getFilters();

    boolean useResources();

    ModifyWatcherManager getModifyWatcherManager();

    @NonNull Set<Output> getOutputTypes();

    @NonNull Set<Minify> getMinifyOptions();


}
