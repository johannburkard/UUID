/*
 * SerializationTest.java
 *
 * Created 2009-03-05
 *
 * UUID - an implementation of the UUID specification
 * Copyright (c) 2003-2019 Johann Burkard (<https://johannburkard.de>)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.eaio.uuid;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.apache.commons.lang.SerializationUtils;
import org.junit.Test;

/**
 * Tests Serialization of UUID instances.
 *
 * @see <a href="https://johannburkard.de/software/uuid/">UUID</a>
 * @author <a href="https://johannburkard.de">Johann Burkard</a>
 */
public class SerializationTest {

    @Test
    public void testSerialization() {
        UUID u = new UUID(), u2;
        
        u2 = (UUID) SerializationUtils.deserialize(SerializationUtils.serialize(u));

        assertThat(u, is(u2));
        assertThat(u.compareTo(u), is(0));
        assertThat(u.compareTo(u2), is(0));
        assertThat(u2.compareTo(u), is(0));
    }

}
