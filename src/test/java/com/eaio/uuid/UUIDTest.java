/*
 * UUIDTest.java
 *
 * Created 01.05.2008.
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

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Testcase for {@link UUID}.
 *
 * @see <a href="http://johannburkard.de/software/uuid/">UUID</a>
 * @author <a href="mailto:jb&#64;eaio.com">Johann Burkard</a>
 * @version $Id: UUIDTest.java 5643 2013-04-02 20:01:22Z johann $
 */
public class UUIDTest {

    private UUID u1;

    private UUID u2;

    private UUID u3;

    @Before
    public void setUp() {
        u1 = new UUID();
        u2 = (UUID) u1.clone();
        u3 = new UUID();
    }

    /**
     * Test method for {@link com.eaio.uuid.UUID#hashCode()}.
     */
    @Test
    public void testHashCode() {
        assertThat(u2.hashCode(), is(u1.hashCode()));
    }

    /**
     * Test method for {@link com.eaio.uuid.UUID#UUID()}.
     */
    @Test
    public void testUUID() {
        assertThat(u2, is(u1));
        assertThat(u1, not(is(u3)));
    }

    /**
     * Test method for {@link com.eaio.uuid.UUID#UUID(long, long)}.
     */
    @Test
    public void testUUIDLongLong() {
        UUID u4 = new UUID(42L, 42L);
        assertThat(u4, is(u4));
        assertThat(u4.equals(null), is(false));
        assertThat(u4.equals(""), is(false));
    }

    /**
     * Test method for {@link com.eaio.uuid.UUID#UUID(com.eaio.uuid.UUID)}.
     */
    @Test
    public void testUUIDUUID() {
        UUID u5 = new UUID(u1);
        assertThat(u5, is(u1));
        assertThat(u1 == u5, is(false));
        assertThat(u5.hashCode(), is(u1.hashCode()));
    }

    /**
     * Test method for {@link com.eaio.uuid.UUID#UUID(CharSequence)}.
     */
    @Test
    public void testUUIDCharSequence() {
        UUID u = new UUID("00000000-0000-002a-0000-00000000002a");
        assertThat(u, is(new UUID(42L, 42L)));
    }

    /**
     * Test method for {@link com.eaio.uuid.UUID#compareTo(com.eaio.uuid.UUID)}.
     */
    @Test
    public void testCompareTo() {
        assertThat(u1.compareTo(u3), is(-1));
        assertThat(u3.compareTo(u1), is(1));
        assertThat(u1.compareTo(u1), is(0));
    }

    /**
     * Test method for {@link com.eaio.uuid.UUID#toString()}.
     */
    @Test
    public void testToString() {
        assertThat(new UUID(42L, 42L).toString(), is("00000000-0000-002a-0000-00000000002a"));
    }

    /**
     * Test method for {@link com.eaio.uuid.UUID#toStringBuffer(java.lang.StringBuffer)}.
     */
    @Test
    public void testToStringBuffer() {
        assertThat(new UUID(42L, 42L).toStringBuffer(null).toString(), is(new UUID(42L, 42L).toString()));
        StringBuffer buf = new StringBuffer();
        assertThat(new UUID(42L, 42L).toStringBuffer(buf).toString(), is(new UUID(42L, 42L).toString()));
    }

    /**
     * Test method for {@link com.eaio.uuid.UUID#toAppendable(java.lang.Appendable)}.
     */
    @Test
    public void testAppend() {
        assertThat(new UUID(42L, 42L).toAppendable(null).toString(), is("00000000-0000-002a-0000-00000000002a"));
        Appendable app = new StringBuilder();
        assertThat(new UUID(42L, 42L).toAppendable(app).toString(), is("00000000-0000-002a-0000-00000000002a"));
    }

    /**
     * Test method for {@link com.eaio.uuid.UUID#getTime()}.
     */
    @Test
    public void testGetTime() {
        assertThat(u1.time, is(u1.getTime()));
    }

    /**
     * Test method for {@link com.eaio.uuid.UUID#getClockSeqAndNode()}.
     */
    @Test
    public void testGetClockSeqAndNode() {
        assertThat(u1.clockSeqAndNode, is(u1.getClockSeqAndNode()));
    }

    /**
     * Test method for {@link com.eaio.uuid.UUID#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsObject() {
        assertThat(u1, is(u1));
    }

    /**
     * Test method for {@link com.eaio.uuid.UUID#nilUUID()}.
     */
    @Test
    public void testNilUUID() {
        assertThat(UUID.nilUUID().getTime(), is(0L));
        assertThat(UUID.nilUUID().getClockSeqAndNode(), is(0L));
        assertThat(UUID.nilUUID() == UUID.nilUUID(), is(false));
    }

}
