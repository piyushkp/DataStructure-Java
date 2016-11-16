package code.ds;
/**
 * Created by Piyush Patel.
 */
import java.util.concurrent.*;
public class Threading {
    public static void main(String[] args) {
        System.out.print("Threading");
    }
    // Multi Threading implementation
    class MultiThreading implements Runnable {
        public Object tLock = new Object();
        private int parameter;
        public MultiThreading(int parameter) {
            this.parameter = parameter;
        }
        void ThreadExe() {
            Thread[] tr = new Thread[5];
            for (int i = 0; i < 5; i++) {
                tr[i] = new Thread(new MultiThreading(i));
            }
            //Start each thread
            for (Thread x : tr) {
                x.start();
            }
        }
        public void run() {
            synchronized (tLock) {
                //do work
            }
        }
    }
    // Deadlock example in Java
    // to avoid deadlock all locks should get acquire in the same order.
    private void deadLock() {
        final Object resource1 = "resource1";
        final Object resource2 = "resource2";
        Thread t1 = new Thread() {
            public void run() {
                synchronized (resource1) {
                    System.out.println("Thread 1: locked resource 1");
                    synchronized (resource2) {
                        System.out.println("Thread 1: locked resource 2");
                    }
                }
            }
        };
        Thread t2 = new Thread() {
            public void run() {
                synchronized (resource2) {
                    System.out.println("Thread 2: locked resource 2");
                    synchronized (resource1) {
                        System.out.println("Thread 2: locked resource 1");
                    }
                }
            }
        };
        t1.start();
        t2.start();
    }
    //design class such that multiple threads can change the value with condition Lower <= upper
    private static class IntPair {
        int lower, upper;
        IntPair(int _lower, int _upper) {
            this.lower = _lower;
            this.upper = _upper;
        }
    }

    public class CasNumberRange {
        private final java.util.concurrent.atomic.AtomicReference<IntPair> values = new java.util.concurrent.atomic.AtomicReference<>(new IntPair(0, 0));
        public int getLower() {
            return values.get().lower;
        }
        public int getUpper() {
            return values.get().upper;
        }
        public void setLower(int i) {
            while (true) {
                IntPair oldv = values.get();
                if (i > oldv.upper) throw new IllegalArgumentException();
                IntPair newv = new IntPair(i, oldv.upper);
                if (values.compareAndSet(oldv, newv)) return;
            }
        }
        public void setUpper(int i) {
            while (true) {
                IntPair oldv = values.get();
                if (i < oldv.lower) throw new IllegalArgumentException();
                IntPair newv = new IntPair(oldv.lower, i);
                if (values.compareAndSet(oldv, newv)) return;
            }
        }
    }
}
