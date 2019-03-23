/*
 * HexTest.java
 *
 * Created 01.05.2008.
 *
 * UUID - an implementation of the UUID specification
 * Copyright (c) 2003-2019 Johann Burkard (<https://johannburkard.de>)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
 * NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.eaio.util.lang;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Test;

/**
 * Testcase for {@link Hex}.
 *
 * @see <a href="https://johannburkard.de/software/uuid/">UUID</a>
 * @author <a href="https://johannburkard.de">Johann Burkard</a>
 */
public class HexTest {

    @Test
    public void testAppendAppendableInt() {
        Appendable a = new StringBuilder();
        Hex.append(a, 42);
        assertThat(a.toString(), is("0000002a"));

        a = new StringBuilder();
        Hex.append(a, 0x2a000000);
        assertThat(a.toString(), is("2a000000"));
    }

    @Test
    public void testAppendAppendableIntInt() {
        Appendable a = new StringBuilder();
        Hex.append(a, 42, 6);
        assertThat(a.toString(), is("00002a"));

        a = new StringBuilder();
        Hex.append(a, 0x2a000000, 6);
        assertThat(a.toString(), is("000000"));
    }

    @Test
    public void testAppendAppendableByteArray() {
        Appendable a = new StringBuilder();
        Hex.append(a, new byte[] { 0x20, 0x30, 0x40 });
        assertThat(a.toString(), is("203040"));
    }

    @Test
	public void testParseShort() {
		assertThat(Hex.parseShort("0042"), is((short) 66));
		assertThat(Hex.parseShort("00-42"), is((short) 66));
		assertThat(Hex.parseShort("00:42"), is((short) 66));
		assertThat(Hex.parseShort("00,,..--..,,42"), is((short) 66));

		assertThat(Hex.parseShort("00:FF:B5:03:0C:EA"), is((short) 0xff));
		assertThat(Hex.parseShort("00:ff:B5:03:0C:EA"), is((short) 0xff));
	}

    @Test
	public void testParseLong() {
		assertThat(Hex.parseLong("00:FF:B5:03:0C:EA"), is(0xffb5030ceaL));
		assertThat(Hex.parseLong("12:ff:b5:03:0c:ea"), is(0x12ffb5030ceaL));
	}
    
    @Test
    public void testDockerHostnames() {
        assertThat(Hex.parseLong("64e3b0fa7da8"), is(0x64e3b0fa7da8L));
        assertThat(Hex.parseLong("be57a73c25eb"), is(0xbe57a73c25ebL));
    }

}
