/*
 * MACAddressParserTest.java
 *
 * Created 30.01.2006.
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
package com.eaio.uuid;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The MAC address parser attempts to find the following patterns:
 * <ul>
 * <li>.{1,2}:.{1,2}:.{1,2}:.{1,2}:.{1,2}:.{1,2}</li>
 * <li>.{1,2}-.{1,2}-.{1,2}-.{1,2}-.{1,2}-.{1,2}</li>
 * </ul>
 *
 * @see <a href="https://johannburkard.de/software/uuid/">UUID</a>
 * @author <a href="https://johannburkard.de">Johann Burkard</a>
 */
public class MACAddressParser {
	
	public static final Pattern MAC_ADDRESS = Pattern.compile("((?:[A-F0-9]{1,2}[:-]){5}[A-F0-9]{1,2})|(?:0x)(\\d{12})(?:.+ETHER)", Pattern.CASE_INSENSITIVE);

    /**
     * Attempts to find a pattern in the given String.
     *
     * @param in the String, may not be <code>null</code>
     * @return the substring that matches this pattern or <code>null</code>
     */
    static String parse(String in) {
        Matcher m = MAC_ADDRESS.matcher(in);
        if (m.find()) {
            String g = m.group(2);
            if (g == null) {
                g = m.group(1);
            }
        	return g == null ? g : g.replace('-', ':');
        }
        return null;
    }

}
