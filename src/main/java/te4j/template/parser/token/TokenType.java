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

package te4j.template.parser.token;

/**
 * @author whilein
 */
public enum TokenType {

    BEGIN, END_FOR, ELSE, ELIF, END_IF, END_CASE, CASE_DEFAULT;

    public static TokenType getType(String path) {
        switch (path) {
            case "endfor":
                return END_FOR;
            case "default":
                return CASE_DEFAULT;
            case "endcase":
                return END_CASE;
            case "else":
                return ELSE;
            case "elif":
                return ELIF;
            case "endif":
                return END_IF;
            default:
                return BEGIN;
        }
    }

}
