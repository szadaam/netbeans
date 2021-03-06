/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.modules.php.doctrine2.annotations.orm.parser;

import java.util.Map;
import org.netbeans.junit.NbTestCase;
import org.netbeans.modules.csl.api.OffsetRange;
import org.netbeans.modules.php.spi.annotation.AnnotationLineParser;
import org.netbeans.modules.php.spi.annotation.AnnotationParsedLine;

/**
 *
 * @author Ondrej Brejla <obrejla@netbeans.org>
 */
public class JoinColumnLineParserTest extends NbTestCase {
    private ParameterizedAnnotationLineParser parser;

    public JoinColumnLineParserTest(String name) {
        super(name);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.parser = new ParameterizedAnnotationLineParser();
    }

    public void testIsAnnotationParser() throws Exception {
        assertTrue(parser instanceof AnnotationLineParser);
    }

    public void testReturnValueIsJoinColumnParsedLine_01() throws Exception {
        assertTrue(parser.parse("JoinColumn") instanceof AnnotationParsedLine.ParsedLine);
    }

    public void testReturnValueIsJoinColumnParsedLine_02() throws Exception {
        assertTrue(parser.parse("Annotations\\JoinColumn") instanceof AnnotationParsedLine.ParsedLine);
    }

    public void testReturnValueIsJoinColumnParsedLine_03() throws Exception {
        assertTrue(parser.parse("\\Foo\\Bar\\JoinColumn") instanceof AnnotationParsedLine.ParsedLine);
    }

    public void testReturnValueIsJoinColumnParsedLine_04() throws Exception {
        assertTrue(parser.parse("Annotations\\JoinColumn(name=\"customer_id\", referencedColumnName=\"id\")") instanceof AnnotationParsedLine.ParsedLine);
    }

    public void testReturnValueIsNull() throws Exception {
        assertNull(parser.parse("JoinColumns"));
    }

    public void testValidUseCase_01() throws Exception {
        AnnotationParsedLine parsedLine = parser.parse("JoinColumn");
        assertEquals("JoinColumn", parsedLine.getName());
        assertEquals("", parsedLine.getDescription());
        Map<OffsetRange, String> types = parsedLine.getTypes();
        for (Map.Entry<OffsetRange, String> entry : types.entrySet()) {
            OffsetRange offsetRange = entry.getKey();
            String value = entry.getValue();
            assertEquals(0, offsetRange.getStart());
            assertEquals(10, offsetRange.getEnd());
            assertEquals("JoinColumn", value);
        }
    }

    public void testValidUseCase_02() throws Exception {
        AnnotationParsedLine parsedLine = parser.parse("JoinColumn   ");
        assertEquals("JoinColumn", parsedLine.getName());
        assertEquals("", parsedLine.getDescription());
        Map<OffsetRange, String> types = parsedLine.getTypes();
        for (Map.Entry<OffsetRange, String> entry : types.entrySet()) {
            OffsetRange offsetRange = entry.getKey();
            String value = entry.getValue();
            assertEquals(0, offsetRange.getStart());
            assertEquals(10, offsetRange.getEnd());
            assertEquals("JoinColumn", value);
        }
    }

    public void testValidUseCase_03() throws Exception {
        AnnotationParsedLine parsedLine = parser.parse("JoinColumn\t\t  ");
        assertEquals("JoinColumn", parsedLine.getName());
        assertEquals("", parsedLine.getDescription());
        Map<OffsetRange, String> types = parsedLine.getTypes();
        for (Map.Entry<OffsetRange, String> entry : types.entrySet()) {
            OffsetRange offsetRange = entry.getKey();
            String value = entry.getValue();
            assertEquals(0, offsetRange.getStart());
            assertEquals(10, offsetRange.getEnd());
            assertEquals("JoinColumn", value);
        }
    }

    public void testValidUseCase_04() throws Exception {
        AnnotationParsedLine parsedLine = parser.parse("JoinColumn(name=\"customer_id\", referencedColumnName=\"id\")");
        assertEquals("JoinColumn", parsedLine.getName());
        assertEquals("(name=\"customer_id\", referencedColumnName=\"id\")", parsedLine.getDescription());
        Map<OffsetRange, String> types = parsedLine.getTypes();
        assertNotNull(types);
        for (Map.Entry<OffsetRange, String> entry : types.entrySet()) {
            OffsetRange offsetRange = entry.getKey();
            String value = entry.getValue();
            assertEquals(0, offsetRange.getStart());
            assertEquals(10, offsetRange.getEnd());
            assertEquals("JoinColumn", value);
        }
    }

    public void testValidUseCase_05() throws Exception {
        AnnotationParsedLine parsedLine = parser.parse("Annotations\\JoinColumn(name=\"customer_id\", referencedColumnName=\"id\")  \t");
        assertEquals("JoinColumn", parsedLine.getName());
        assertEquals("(name=\"customer_id\", referencedColumnName=\"id\")", parsedLine.getDescription());
        Map<OffsetRange, String> types = parsedLine.getTypes();
        assertNotNull(types);
        for (Map.Entry<OffsetRange, String> entry : types.entrySet()) {
            OffsetRange offsetRange = entry.getKey();
            String value = entry.getValue();
            assertEquals(0, offsetRange.getStart());
            assertEquals(22, offsetRange.getEnd());
            assertEquals("Annotations\\JoinColumn", value);
        }
    }

    public void testValidUseCase_06() throws Exception {
        AnnotationParsedLine parsedLine = parser.parse("\\Foo\\Bar\\JoinColumn(name=\"customer_id\", referencedColumnName=\"id\")  \t");
        assertEquals("JoinColumn", parsedLine.getName());
        assertEquals("(name=\"customer_id\", referencedColumnName=\"id\")", parsedLine.getDescription());
        Map<OffsetRange, String> types = parsedLine.getTypes();
        assertNotNull(types);
        for (Map.Entry<OffsetRange, String> entry : types.entrySet()) {
            OffsetRange offsetRange = entry.getKey();
            String value = entry.getValue();
            assertEquals(0, offsetRange.getStart());
            assertEquals(19, offsetRange.getEnd());
            assertEquals("\\Foo\\Bar\\JoinColumn", value);
        }
    }

    public void testValidUseCase_07() throws Exception {
        AnnotationParsedLine parsedLine = parser.parse("joincolumn");
        assertEquals("JoinColumn", parsedLine.getName());
        assertEquals("", parsedLine.getDescription());
        Map<OffsetRange, String> types = parsedLine.getTypes();
        for (Map.Entry<OffsetRange, String> entry : types.entrySet()) {
            OffsetRange offsetRange = entry.getKey();
            String value = entry.getValue();
            assertEquals(0, offsetRange.getStart());
            assertEquals(10, offsetRange.getEnd());
            assertEquals("joincolumn", value);
        }
    }

    public void testValidUseCase_08() throws Exception {
        AnnotationParsedLine parsedLine = parser.parse("\\Foo\\Bar\\joincolumn(name=\"customer_id\", referencedColumnName=\"id\")  \t");
        assertEquals("JoinColumn", parsedLine.getName());
        assertEquals("(name=\"customer_id\", referencedColumnName=\"id\")", parsedLine.getDescription());
        Map<OffsetRange, String> types = parsedLine.getTypes();
        assertNotNull(types);
        for (Map.Entry<OffsetRange, String> entry : types.entrySet()) {
            OffsetRange offsetRange = entry.getKey();
            String value = entry.getValue();
            assertEquals(0, offsetRange.getStart());
            assertEquals(19, offsetRange.getEnd());
            assertEquals("\\Foo\\Bar\\joincolumn", value);
        }
    }

}
