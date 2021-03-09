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

package com.github.lero4ka16.te4j;

import com.github.lero4ka16.te4j.template.context.TemplateContext;
import com.github.lero4ka16.te4j.template.output.TemplateOutputType;
import com.github.lero4ka16.te4j.template.replace.ReplaceStrategy;
import com.github.lero4ka16.te4j.util.type.ref.ClassRef;
import com.github.lero4ka16.te4j.util.type.ref.TypeRef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author lero4ka16
 */
public class TemplateTest {

    private TemplateContext context;

    @BeforeEach
    public void init() {
        context = Te4j.custom()
                .useResources()
                .outputTypes(TemplateOutputType.STRING | TemplateOutputType.BYTES)
                .replaceStrategy(ReplaceStrategy.ALL)
                .build();
    }

    @Test
    public void testPlain() {
        testTemplate(context,
                "WEB-INF/plain.html", "Hello world!Привет мир!",
                null, new ClassRef<>(Object.class));
    }

    @Test
    public void testValue() {
        testTemplate(context,
                "WEB-INF/value.html", "Hello my friend!",
                new Example_1("my friend"), new ClassRef<>(Example_1.class));
    }

    @Test
    public void testForeach() {
        testTemplate(context,
                "WEB-INF/foreach.html", "<a>0: 10</a><a>1: 20</a><a>2: 30</a><a>10</a><a>20</a><a>30</a>",
                new Example_2(10, 20, 30), new ClassRef<>(Example_2.class));
    }

    @Test
    public void testForeachCollection() {
        testTemplate(context,
                "WEB-INF/foreach.html", "<a>0: 15</a><a>1: 25</a><a>2: 35</a><a>15</a><a>25</a><a>35</a>",
                new Example_5(15, 25, 35), new ClassRef<>(Example_5.class));
    }

    @Test
    public void testForeachGeneric() {
        testTemplate(context,
                "WEB-INF/foreach_generic.html", "<a>0: 50</a><a>1: 100</a><a>2: 200</a>",
                Arrays.asList(50, 100, 200), new TypeRef<List<Integer>>() {
                });
    }

    @Test
    public void testConditionFalse() {
        testTemplate(context,
                "WEB-INF/condition.html", "<a>Result is false</a>",
                new Example_3("Hello world", false), new ClassRef<>(Example_3.class));
    }

    @Test
    public void testConditionTrue() {
        testTemplate(context,
                "WEB-INF/condition.html", "<a>Hello world</a>",
                new Example_3("Hello world", true), new ClassRef<>(Example_3.class));
    }

    @Test
    public void testSwitchCase_Condition() {
        testTemplate(context,
                "WEB-INF/switchcase.html", "<a>Goodbye my friend</a>",
                new Example_4("Goodbye my friend", true), new ClassRef<>(Example_4.class));
    }

    @Test
    public void testSwitchCase_Foreach() {
        testTemplate(context,
                "WEB-INF/switchcase.html", "<a>0: 5</a><a>1: 10</a><a>2: 15</a><a>5</a><a>10</a><a>15</a>",
                new Example_4(new int[]{5, 10, 15}), new ClassRef<>(Example_4.class));
    }

    @Test
    public void testSwitchCase_Value() {
        testTemplate(context,
                "WEB-INF/switchcase.html", "Hello you!",
                new Example_4("you"), new ClassRef<>(Example_4.class));
    }

    private <T> void testTemplate(TemplateContext context, String resource, String expectText,
                                  T object, TypeRef<T> type) {
        String result = context.load(type, resource).renderAsString(object);
        assertEquals(expectText, result);
    }

    public static class Example_1 {
        private final String name;

        public Example_1(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static class Example_2 {
        private final int[] elements;

        public Example_2(int... elements) {
            this.elements = elements;
        }

        public int[] getElements() {
            return elements;
        }
    }

    public static class Example_3 {
        private final String message;
        private final boolean condition;

        public Example_3(String message, boolean condition) {
            this.message = message;
            this.condition = condition;
        }

        public String getMessage() {
            return message;
        }

        public boolean getCondition() {
            return condition;
        }
    }

    public static class Example_4 {
        private final Check check;

        private final String name;
        private final int[] elements;
        private final String message;
        private final boolean condition;

        public Example_4(Check check, String name, int[] elements, String message, boolean condition) {
            this.check = check;
            this.name = name;
            this.elements = elements;
            this.message = message;
            this.condition = condition;
        }

        public Example_4(String name) {
            this(Check.VALUE, name, null, null, false);
        }

        public Example_4(int[] elements) {
            this(Check.FOREACH, null, elements, null, false);
        }

        public Example_4(String message, boolean condition) {
            this(Check.CONDITION, null, null, message, condition);
        }

        public Check getCheck() {
            return check;
        }

        public String getName() {
            return name;
        }

        public int[] getElements() {
            return elements;
        }

        public String getMessage() {
            return message;
        }

        public boolean isCondition() {
            return condition;
        }
    }

    public static class Example_5 {
        private final List<Integer> elements;

        public Example_5(int... elements) {
            this.elements = IntStream.of(elements).boxed().collect(Collectors.toList());
        }

        public Collection<Integer> getElements() {
            return elements;
        }
    }

    public enum Check {

        CONDITION, FOREACH, VALUE;

        public String getName() {
            return name().toLowerCase();
        }

    }

}
