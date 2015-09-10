/**
 * ToStringPerformance.java
 *
 * Created 2009-03-05
 *
 * UUID - an implementation of the UUID specification
 * Copyright (c) 2003-2015 Johann Burkard (<http://johannburkard.de>)
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

import com.eaio.uuid.UUID;

/**
 * Shell test to evaluate the performance of the <code>toString</code> methods.
 *
 * @see <a href="http://johannburkard.de/software/uuid/">UUID</a>
 * @author <a href="mailto:jb&#64;eaio.com">Johann Burkard</a>
 * @version $Id: UUIDTest.java 1327 2009-03-05 08:21:34Z johann $
 */
public class ToStringPerformance {

    /**
     * @param args
     */
    public static void main(String[] args) {

        UUID uuid = new UUID();

        java.util.UUID jdkUUID = java.util.UUID.randomUUID();

        for (int i = 0; i < 1000000; ++i) {
            uuid.toString();
            jdkUUID.toString();
        }

        long then = System.currentTimeMillis();

        for (int i = 0; i < 1000000; ++i) {
            uuid.toString();
        }

        System.out.println("UUID: " + (System.currentTimeMillis() - then) + " ms");

        then = System.currentTimeMillis();

        for (int i = 0; i < 1000000; ++i) {
            uuid.toString();
            jdkUUID.toString();
        }

        System.out.println("java.util.UUID: " + (System.currentTimeMillis() - then) + " ms");


    }

}
