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

    //Implement a delayed scheduler for jobs using pthread apis
}
