package code.ds;
/**
 * Created by ppatel2 on 8/26/2014.
 */
public class Threading
{
    // Multi Threading implementation
    class MultiThreading implements Runnable
    {
        public Object tLock = new Object();
        private int parameter;
        public MultiThreading(int parameter) {
            this.parameter = parameter;
        }
        void ThreadExe()
        {
            Thread[] tr = new Thread[5];
            for (int i = 0; i < 5; i++)
            {
                tr[i] = new Thread(new MultiThreading(i));
            }
            //Start each thread
            for (Thread x : tr)
            {
                x.start();
            }
        }
        public void run()
        {
            synchronized (tLock)
            {
                //do work
            }
        }
    }
    // Deadlock example in Java
    private void deadLock() {
        final Object resource1 = "resource1";
        final Object resource2 = "resource2";
        Thread t1 = new Thread()
        {
            public void run()
            {
                   synchronized (resource1)
                   {
                        System.out.println("Thread 1: locked resource 1");
                        synchronized (resource2)
                        {
                            System.out.println("Thread 1: locked resource 2");
                        }
                   }
            }
        };
        Thread t2 = new Thread() {
            public void run()
            {
                synchronized (resource2)
                {
                    System.out.println("Thread 2: locked resource 2");
                    synchronized (resource1)
                    {
                        System.out.println("Thread 2: locked resource 1");
                    }
                }
            }
        };
        t1.start();
        t2.start();
    }
}
