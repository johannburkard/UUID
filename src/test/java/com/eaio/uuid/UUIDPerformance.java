/**
 * UUIDPerformance.java
 * 
 * Created 2013-04-02
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

import org.apache.commons.lang.time.StopWatch;

/**
 * Shell test to evaluate the performance of UUID creation.
 * 
 * @see <a href="http://johannburkard.de/software/uuid/">UUID</a>
 * @author <a href="mailto:jb&#64;eaio.com">Johann Burkard</a>
 * @version $Id: UUIDPerformance.java 5643 2013-04-02 20:01:22Z johann $
 */
public class UUIDPerformance {

    static long count = 500000000;

    public static void main(String[] args) {

        Thread[] threads = new Thread[Runtime.getRuntime().availableProcessors()];

        for (int i = 0; i < threads.length; ++i) {
            threads[i] = new Thread(new UUIDRunnable(count / threads.length));
        }

        StopWatch watch = new StopWatch();
        watch.start();

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            }
            catch (InterruptedException e) {
                // Moo
            }
        }

        watch.stop();
        System.out.println(watch.getTime());        
    }

    static class UUIDRunnable implements Runnable {

        private final long count;

        public UUIDRunnable(long count) {
            this.count = count;
        }

        public void run() {
            for (long i = 0; i < count; ++i) {
                new UUID();
            }
        }
        
    }

}
