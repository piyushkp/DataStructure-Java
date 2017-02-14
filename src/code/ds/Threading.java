package code.ds;
/**
 * Created by Piyush Patel.
 */
import java.util.HashMap;
import java.util.concurrent.locks.*;
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
    //There are three threads in a process.The first thread prints 1 1 1 …, the second one prints 2 2 2 …, and the third one prints 3 3 3 … endlessly.
    //How do you schedule these three threads in order to print 1 2 3 1 2 3 …?
    class ThreeThreads {
        public  void main(String[] args) {
            final Lock lock = new ReentrantLock();
            final Condition condition = lock.newCondition();

            ThreadId threadId = new ThreeThreads.ThreadId();
            threadId.setId(1);
            Thread t1 = setThread(lock, condition, 1, 2, threadId);
            Thread t2 = setThread(lock, condition, 2, 3, threadId);
            Thread t3 = setThread(lock, condition, 3, 1, threadId);
            t1.start();
            t2.start();
            t3.start();
        }
        private class ThreadId {
            private int id;
            public ThreadId(){}
            public int getId() {
                return id;
            }
            public void setId(int id) {
                this.id = id;
            }
        }
        private Thread setThread(final Lock lock, final Condition condition, int currentThreadId, int nextThreadId, ThreadId threadId) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    while (true) {
                        lock.lock();
                        try {
                            while (threadId.getId() != currentThreadId)
                                try {
                                    condition.await();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            System.out.println("" + currentThreadId);
                            threadId.setId(nextThreadId);
                            condition.signalAll();
                        } finally {
                            lock.unlock();
                        }
                    }
                }
            };
            return thread;
        }
    }
    // Implement the Read/Write reentrant Lock
    class ReadWriteLock{
        int writeRequests  = 0;
        int writeAccess = 0;
        Thread writingThread = null;
        HashMap<Thread, Integer> readers = new HashMap<>();
        public synchronized void lockRead() throws InterruptedException
        {
            Thread callingThread = Thread.currentThread();
            while(!canGrantReadAccess(callingThread))
                wait();
           readers.put(callingThread,readers.get(callingThread) + 1);
        }
        private boolean canGrantReadAccess(Thread callingThread){
            if( writingThread == callingThread )    return true;
            if( writingThread != null  )            return false;
            if( readers.get(callingThread) != null )return true;
            if( writeRequests > 0 )                 return false;
            return true;
        }

        public synchronized void unlockRead() throws InterruptedException
        {
            Thread callingThread = Thread.currentThread();
            if(readers.get(callingThread) == 1)
                readers.remove(callingThread);
            else
                readers.put(callingThread,readers.get(callingThread) - 1);
            notifyAll();
        }
        public synchronized void lockWrite() throws InterruptedException
        {
            writeRequests++;
            Thread callingThread = Thread.currentThread();
            while(!canGrantWriteAccess(callingThread))
                wait();
            writeRequests--;
            writeAccess++;
            writingThread = callingThread;
        }
        private boolean canGrantWriteAccess(Thread callingThread){
            if(readers.size() == 1 && readers.get(callingThread) != null)    return true;
            if(readers.size() > 0)                   return false;
            if(writingThread == null)          return true;
            if(writingThread != callingThread)       return false;
            return true;
        }
        public synchronized void unlockWrite() throws InterruptedException
        {
            writeAccess--;
            if(writeAccess == 0){
                writingThread = null;
            }
            notifyAll();
        }
    }
}
