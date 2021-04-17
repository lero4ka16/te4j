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

package te4j.template.option.output;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * @author lero4ka16
 */
public enum Output {

    STRING("STRING_"),
    BYTES("BYTES_");

    private final String prefix;

    Output(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    private static volatile Set<Output> values;

    public static Set<Output> getValues() {
        if (values == null) {
            synchronized (Output.class) {
                if (values == null) {
                    values = Collections.unmodifiableSet(EnumSet.allOf(Output.class));
                }
            }
        }

        return values;
    }

}