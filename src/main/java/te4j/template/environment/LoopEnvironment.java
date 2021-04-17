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

package te4j.template.environment;

import te4j.template.compiler.path.PathAccessor;
import te4j.template.path.TemplatePathIterator;
import te4j.util.type.GenericInfo;

/**
 * @author lero4ka16
 */
public final class LoopEnvironment implements Environment {

    private final String counterField;

    private boolean hasCounter;

    public LoopEnvironment(String counterField) {
        this.counterField = counterField;
    }

    public boolean hasCounter() {
        return hasCounter;
    }

    @Override
    public PathAccessor resolve(TemplatePathIterator iterator) {
        if (!iterator.hasNext()) {
            throw new IllegalStateException(iterator.getText());
        }

        String value = iterator.next();

        switch (value) {
            case "index":
                if (iterator.hasNext()) {
                    throw new IllegalStateException(iterator.getText());
                }

                hasCounter = true;

                return new PathAccessor(GenericInfo.PRIMITIVE_INT, counterField);
        }

        throw new IllegalStateException(iterator.getText());
    }
}