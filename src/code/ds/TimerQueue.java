package code.ds;

import java.util.*;

/**
 * Created by ppatel2 on 11/6/2016.
 */
//Implement Timer using queue. A Priority Queue holding multiple Timers and execute them based on their schedule.
public class TimerQueue {
    private PriorityQueue<TimerTask> m_heap;

    public TimerQueue() {
        m_heap = new PriorityQueue();
    }

    /* Schedules the given TimerTask for immediate execution.*/
    public void schedule(TimerTask t) {
        schedule(t, 0);
    }

    //Schedule the given TimerTask to be executed after <code>delay</code>  milliseconds.
    public void schedule(TimerTask t, long delay) {
        if (t == null) throw new IllegalArgumentException("Can't schedule a null TimerTask");
        if (delay < 0) delay = 0;
        t.setNextExecutionTime(System.currentTimeMillis() + delay);
        putJob(t);
    }

    protected void putJob(TimerTask task) {
        m_heap.add(task);
        task.setState(TimerTask.SCHEDULED);
        notifyAll();
    }

    protected TimerTask getJob() throws InterruptedException {
        while (m_heap.peek() == null) {
            wait();
        }
        TimerTask task = m_heap.poll();
        switch (task.getState()) {
            case TimerTask.CANCELLED:
            case TimerTask.EXECUTED:
                //don't hold onto last dead task if we wait.
                task = null;
                return getJob();
            case TimerTask.NEW:
            case TimerTask.SCHEDULED:
                return task;
            default:
                throw new IllegalStateException("TimerTask has an illegal state");
        }
    }

    protected Runnable ExecuteTask() {
        return new TimerTaskLoop();
    }

    protected void clear() {
        synchronized (this) {
            m_heap.clear();
        }
    }

    /* Class that loops getting the next job to be executed and then executing it, in the timer task thread. */
    protected class TimerTaskLoop implements Runnable {
        public void run() {
            try {
                while (true) {
                    try {
                        // Wait for the first job
                        TimerTask task =  getJob();
                        long now = System.currentTimeMillis();
                        long executionTime = task.getNextExecutionTime();
                        long timeToWait = executionTime - now;
                        boolean runTask = timeToWait <= 0;
                        if (!runTask) {
                            // Entering here when a new job is scheduled but it's not yet time to run the first one;
                            // the job was extracted from the heap, reschedule
                            putJob(task);
                            Object mutex = TimerQueue.this;
                            synchronized (mutex) {
                                // timeToWait is always strictly > 0, so I don't wait forever
                                mutex.wait(timeToWait);
                            }
                        } else {
                            if (task.isPeriodic()) {
                                // Reschedule with the new time
                                task.setNextExecutionTime(executionTime + task.getPeriod());
                                putJob(task);
                            } else {
                                // The one-shot task is already removed from
                                // the heap, mark it as executed
                                task.setState(TimerTask.EXECUTED);
                            }
                            // Run it !
                            task.execute();
                        }
                    } catch (InterruptedException x) {
                        // Exit the thread
                        break;
                    }
                }
            }
            finally{
                clear();
            }
        }
    }
}

abstract class TimerTask
{
    /** The state before the first execution */
    static final int NEW = 1;
    /** The state after first execution if the TimerTask is repeating */
    static final int SCHEDULED = 2;
    /** The state after first execution if the TimerTask is not repeating */
    static final int EXECUTED = 3;
    /** The state when cancelled */
    static final int CANCELLED = 4;

    // Attributes ----------------------------------------------------
    private final Object m_lock = new Object();
    private int m_state;
    // this is a constant, and need not be locked
    private final long m_period;
    private long m_nextExecutionTime;

    /**
     * Creates a TimerTask object that will be executed once.
     */
    protected TimerTask()
    {
        m_state = NEW;
        m_period = 0;
    }

    /**
     * Creates a TimerTask object that will be executed every <code>period</code>
     * milliseconds. <p>
     * @param period the execution period; if zero, will be executed only once.
     */
    protected TimerTask(long period)
    {
        m_state = NEW;
        if (period < 0) throw new IllegalArgumentException("Period can't be negative");
        m_period = period;
    }

    /**
     * Cancels the next execution of this TimerTask (if any). <br>
     * If the TimerTask is executing it will prevent the next execution (if any).
     * @returns true if one or more scheduled execution will not take place,
     * false otherwise.
     */
    public boolean cancel()
    {
        synchronized (getLock())
        {
            boolean ret = (m_state == SCHEDULED);
            m_state = CANCELLED;
            return ret;
        }
    }
    /**
     * The task to be executed, to be implemented in subclasses.
     */
    public abstract void execute();

    // Comparable implementation ---------------------------------------

    /**
     * A TimerTask is less than another if it will be scheduled before.
     * @throws ClassCastException if other is not a TimerTask, according to the Comparable contract
     */
    public int compareTo(Object other)
    {
        if (other == this) return 0;
        TimerTask t = (TimerTask) other;
        long diff = getNextExecutionTime() - t.getNextExecutionTime();
        return (int) diff;
    }

    /** Returns the mutex that syncs the access to this object */
    Object getLock()
    {
        return m_lock;
    }

    /** Sets the state of execution of this TimerTask */
    void setState(int state)
    {
        synchronized (getLock())
        {
            m_state = state;
        }
    }

    /** Returns the state of execution of this TimerTask */
    int getState()
    {
        synchronized (getLock())
        {
            return m_state;
        }
    }

    /** Returns whether this TimerTask is periodic */
    boolean isPeriodic()
    {
        return m_period > 0;
    }

    /** Returns the next execution time for this TimerTask */
    long getNextExecutionTime()
    {
        synchronized (getLock())
        {
            return m_nextExecutionTime;
        }
    }

    /** Sets the next execution time for this TimerTask */
    void setNextExecutionTime(long time)
    {
        synchronized (getLock())
        {
            m_nextExecutionTime = time;
        }
    }

    /** Returns the period of this TimerTask */
    protected long getPeriod()
    {
        return m_period;
    }
}