/*
 * MACAddressParserTest.java
 *
 * Created 30.01.2006.
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

import static com.eaio.util.Resource.close;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Test;

/**
 * Test case for the {@link com.eaio.uuid.MACAddressParser} class.
 *
 * @see <a href="http://johannburkard.de/software/uuid/">UUID</a>
 * @author <a href="mailto:jb@eaio.com">Johann Burkard</a>
 * @version $Id: MACAddressParserTest.java 5643 2013-04-02 20:01:22Z johann $
 */
public class MACAddressParserTest {

    @Test
    public void testParse() {
        assertThat(MACAddressParser.parse("kalifornia blorb blob"), nullValue());
        assertThat(MACAddressParser.parse("bla: keks: blorb: blubbix:a"), nullValue());
        assertThat(MACAddressParser.parse("blablorb (10.20.30.40) at 0:3:ba:1b:c4:74 permanent published"), is("0:3:ba:1b:c4:74"));
        assertThat(MACAddressParser.parse("        Physikalische Adresse . . . . . . : 00-12-F0-21-F1-57"), is("00:12:F0:21:F1:57"));
    }

    @Test
    public void testParse2() {
        String addr;

        addr = "blablorb (10.20.30.40) at 0:3:ba:1b:c4:74 permanent published";
        for (int i = addr.length(); i > 40; --i) {
            assertThat(MACAddressParser.parse(addr.substring(0, i)), is("0:3:ba:1b:c4:74"));
        }
        assertThat(MACAddressParser.parse(addr.substring(0, 40)), is("0:3:ba:1b:c4:7"));
        for (int i = 39; i != 0; --i) {
            assertThat(MACAddressParser.parse(addr.substring(0, i)), nullValue());
        }
    }

    @Test
    public void testParseLanscan() {
        assertThat(MACAddressParser.parse("Hardware Station        Crd  Hdw   Net-Interface    NM   MAC       HP-DLPI DLPI"), nullValue());
        assertThat(MACAddressParser.parse("Path     Address        In#  State NamePPA          ID   Type      Support Mjr#"), nullValue());
        assertThat(MACAddressParser.parse("0/1/2/0  0x001560045000 0    UP    lan0 snap0       1    ETHER       Yes   119"), is("001560045000"));
    }

    /**
     * <code>Description . . . . . . . . . . . : Broadcom 440x 10/100 Integrated Controller</code>
     *
     * Mail from Graham Matthews, 2008-01-29.
     *
     */
    @Test
    public void testBroadcom() {
        assertThat(MACAddressParser.parse("Description . . . . . . . . . . . : Broadcom 440x 10/100 Integrated Controller"), nullValue());
        assertThat(MACAddressParser.parse("Physical Address. . . . . . . . . : 00-1C-23-AD-D1-5A"), is("00:1C:23:AD:D1:5A"));
    }

    @Test
    public void testMacOSXLeopard() throws IOException {
        File f = new File("src/test/resources/macosx-leopard.txt");
        byte[] buf = new byte[(int) f.length()];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            fis.read(buf);
        }
        finally {
            close(fis);
        }
        String macosxLeopard = new String(buf);
        String[] lines = macosxLeopard.split("\n");
        String mac = null;
        for (int i = 0; i < lines.length; ++i) {
            mac = MACAddressParser.parse(lines[i]);
            if (mac != null) {
                break;
            }
        }
        assertThat(mac, is("00:1e:c2:00:43:51"));
    }

}
