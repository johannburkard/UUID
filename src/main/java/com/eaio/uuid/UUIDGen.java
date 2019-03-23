/*
 * UUIDGen.java
 *
 * Created on 09.08.2003.
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

import static com.eaio.util.Resource.close;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicLong;

import com.eaio.util.lang.Hex;

/**
 * This class contains methods to generate UUID fields. These methods have been
 * refactored out of {@link com.eaio.uuid.UUID}.
 *
 * @see <a href="https://johannburkard.de/software/uuid/">UUID</a>
 * @author <a href="https://johannburkard.de">Johann Burkard</a>
 * @see com.eaio.uuid.UUID
 */
public final class UUIDGen {

    /**
     * The last time value. Used to remove duplicate UUIDs.
     */
    private static AtomicLong lastTime = new AtomicLong(Long.MIN_VALUE);

    /**
     * The cached MAC address.
     */
    private static String macAddress = null;

    /**
     * The current clock and node value.
     */
    private static long clockSeqAndNode = 0x8000000000000000L;

    static {

        if (inDockerContainer()) {
            try {
                // Via https://github.com/mrubin: "inside a docker container, the hostname is typically its container id"
                macAddress = getFirstLineOfCommand("hostname");
            }
            catch (SecurityException ignored) {}
            catch (IOException ignored) {}
        }

        if (macAddress == null) {
            macAddress = getFirstMACAddressFromNetworkInterfaces();
        }

        if (macAddress == null) {
            initializeClockSeqAndNodeFromIPAddress();
        }
        else {
            clockSeqAndNode |= Hex.parseLong(macAddress);
        }

        // Skip the clock sequence generation process and use random instead.

        clockSeqAndNode |= (long) (Math.random() * 0x3FFF) << 48;
    }

    private static void initializeClockSeqAndNodeFromIPAddress() {
        try {
            byte[] local = InetAddress.getLocalHost().getAddress();
            clockSeqAndNode |= (local[0] << 24) & 0xFF000000L;
            clockSeqAndNode |= (local[1] << 16) & 0xFF0000;
            clockSeqAndNode |= (local[2] << 8) & 0xFF00;
            clockSeqAndNode |= local[3] & 0xFF;
        }
        catch (UnknownHostException ex) {
            clockSeqAndNode |= (long) (Math.random() * 0x7FFFFFFF);
        }
    }

    private static boolean inDockerContainer() {
        boolean out = false;
        try {
            File f = new File("/.dockerenv");
            out = f.exists() && !f.isDirectory();
        }
        catch (SecurityException ignored) {}
        return out;
    }

    private static String getFirstMACAddressFromNetworkInterfaces() {
        try {
            Enumeration<NetworkInterface> ifs = NetworkInterface.getNetworkInterfaces();
            if (ifs != null) {
                while (ifs.hasMoreElements()) {
                    NetworkInterface iface = ifs.nextElement();
                    byte[] hardware = iface.getHardwareAddress();
                    if (hardware != null && hardware.length == 6
                            && hardware[1] != (byte) 0xff) {
                        return Hex.append(new StringBuilder(36), hardware).toString();
                    }
                }
            }
        }
        catch (SocketException ignored) {}
        return null;
    }

    /**
     * Returns the current clockSeqAndNode value.
     * 
     * @return the clockSeqAndNode value
     * @see UUID#getClockSeqAndNode()
     */
    public static long getClockSeqAndNode() {
        return clockSeqAndNode;
    }

    /**
     * Generates a new time field. Each time field is unique and larger than the
     * previously generated time field.
     * 
     * @return a new time value
     * @see UUID#getTime()
     */
    public static long newTime() {
        return createTime(System.currentTimeMillis());
    }

    /**
     * Creates a new time field from the given timestamp. Note that even identical
     * values of <code>currentTimeMillis</code> will produce different time fields.
     * 
     * @param currentTimeMillis the timestamp
     * @return a new time value
     * @see UUID#getTime()
     */
    public static long createTime(long currentTimeMillis) {

        long time;

        // UTC time

        long timeMillis = (currentTimeMillis * 10000) + 0x01B21DD213814000L;

        while (true) {
            long current = lastTime.get();
            if (timeMillis > current) {
                if (lastTime.compareAndSet(current, timeMillis)) {
                    break;
                }
            }
            else {
                if (lastTime.compareAndSet(current, current + 1)) {
                    timeMillis = current + 1;
                    break;
                }
            }
        }

        // time low

        time = timeMillis << 32;

        // time mid

        time |= (timeMillis & 0xFFFF00000000L) >> 16;

        // time hi and version

        time |= 0x1000 | ((timeMillis >> 48) & 0x0FFF); // version 1

        return time;

    }

    /**
     * Returns the MAC address. Not guaranteed to return anything.
     * 
     * @return the MAC address, may be <code>null</code>
     */
    public static String getMACAddress() {
        return macAddress;
    }

    /**
     * Returns the first line of the shell command.
     * 
     * @param commands the commands to run
     * @return the first line of the command
     * @throws IOException
     */
    static String getFirstLineOfCommand(String... commands) throws IOException {

        Process p = null;
        BufferedReader reader = null;

        try {
            p = Runtime.getRuntime().exec(commands);
            reader = new BufferedReader(new InputStreamReader(
                    p.getInputStream()), 128);

            return reader.readLine();
        }
        finally {
            if (p != null) {
                close(reader, p.getErrorStream(), p.getOutputStream());
                p.destroy();
            }
        }

    }

}
