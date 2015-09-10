/*
 * UUIDGenTest.java
 *
 * Created 21.02.2006.
 *
 * UUID - an implementation of the UUID specification
 * Copyright (c) 2003-2015 Johann Burkard (<http://johannburkard.de>)
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

import java.io.IOException;

import org.junit.Test;

/**
 * Test case for the {@link UUIDGenTest} class.
 *
 * @see <a href="http://johannburkard.de/software/uuid/">UUID</a>
 * @author <a href="mailto:jb@eaio.com">Johann Burkard</a>
 * @version $Id: UUIDGenTest.java 5643 2013-04-02 20:01:22Z johann $
 */
public class UUIDGenTest {

    /**
     * Test method for 'com.eaio.uuid.UUIDGen.getFirstLineOfCommand(String)'
     */
    @Test
    public void getFirstLineOfCommandShouldReturnLinux() throws IOException {
        String osName = System.getProperty("os.name", "");
        if (osName.startsWith("Linux")) {
            assertThat(UUIDGen.getFirstLineOfCommand("uname", "-a"), startsWith("Linux"));
        }
    }
    
    @Test
    public void getMACAddressShouldNotBeNull() {
        assertThat(UUIDGen.getMACAddress(), notNullValue());
    }

}
