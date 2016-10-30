package code.ds;

import java.util.concurrent.TimeUnit;
import java.util.*;
import java.util.concurrent.*;
/**
 * Created by ppatel2 on 10/28/2016.
 */
public class RateLimiter {
    // Semaphore used to count and limit the number of occurrences per
    // unit time.
    private Semaphore  _semaphore;

    // Times (in millisecond ticks) at which the semaphore should be exited.
    private ConcurrentLinkedQueue<Long> _queue;

    // Timer used to trigger exiting the semaphore.
    private ScheduledExecutorService scheduledPool;

    private ScheduledFuture futureTask;

    /// <summary>
    /// Number of occurrences allowed per unit of time.
    /// </summary>
    public int Occurrences;

    /// <summary>
    /// The length of the time unit, in milliseconds.
    /// </summary>
    public long TimeUnitMilliseconds;

    /// <summary>
    /// Initializes a <see cref="RateGate"/> with a rate of <paramref name="occurrences"/>
    /// per <paramref name="timeUnit"/>.
    /// </summary>
    /// <param name="occurrences">Number of occurrences allowed per unit of time.</param>
    /// <param name="timeUnit">Length of the time unit.</param>
    /// <exception cref="ArgumentOutOfRangeException">
    /// If <paramref name="occurrences"/> or <paramref name="timeUnit"/> is negative.
    /// </exception>
    public RateLimiter(int occurrences, int time) throws InterruptedException, ExecutionException {
        // Check the arguments.
        if (occurrences <= 0)
            throw new IllegalArgumentException("Number of occurrences must be a positive integer");
        Occurrences = occurrences;
        TimeUnitMilliseconds = TimeUnit.MILLISECONDS.convert(time,TimeUnit.SECONDS);

        // Create the semaphore, with the number of occurrences as the maximum count.
        _semaphore = new Semaphore(Occurrences);

        // Create a queue to hold the semaphore exit times.
        _queue = new ConcurrentLinkedQueue<>();

        // Create a timer to exit the semaphore. Use the time unit as the original
        // interval length because that's the earliest we will need to exit the semaphore.
        ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(1);
        futureTask =  scheduledPool.scheduleWithFixedDelay(runnabledelayedTask, TimeUnitMilliseconds,TimeUnitMilliseconds, TimeUnit.MILLISECONDS);
    }

    // Callback for the exit timer that exits the semaphore based on exit times
    // in the queue and then sets the timer for the next exit time.
    Runnable runnabledelayedTask = new Runnable() {
        @Override
        public void run(){
            // While there are exit times that are passed due still in the queue,
            // exit the semaphore and dequeue the exit time.
            long exitTime = _queue.peek();
            while ( _queue.peek() != null && exitTime - System.currentTimeMillis() <= 0) {
                _semaphore.release();
                _queue.poll();
                //System.out.println( "semaphore " + _semaphore.availablePermits());
                if(_queue.peek() != null)
                    exitTime = _queue.peek();
            }
            // Try to get the next exit time from the queue and compute the time until the next check should take place. If the
            // queue is empty, then no exit times will occur until at least one time unit has passed.
            long timeUntilNextCheck;
            if (_queue.peek() != null) {
                exitTime = _queue.peek();
                timeUntilNextCheck = exitTime - System.currentTimeMillis();
            }
            else
                timeUntilNextCheck = TimeUnitMilliseconds;
            // Set the timer.
            //changeScheduler(timeUntilNextCheck);
        }
    };
    public void changeScheduler(long time){
        if(time > 0){
            if (futureTask != null ){
                futureTask.cancel(false);
            }
            futureTask =  scheduledPool.schedule(runnabledelayedTask,time,TimeUnit.MILLISECONDS);
        }
    }
    /// <summary>
    /// Blocks the current thread until allowed to proceed or until the
    /// specified timeout elapses.
    /// </summary>
    /// <param name="millisecondsTimeout">Number of milliseconds to wait, or -1 to wait indefinitely.</param>
    /// <returns>true if the thread is allowed to proceed, or false if timed out</returns>
    public void WaitToProceed() throws InterruptedException
    {
        // Block until we can enter the semaphore or until the timeout expires.
        _semaphore.acquire();
        long timeToExit = System.currentTimeMillis() + TimeUnitMilliseconds;
        _queue.add(timeToExit);
    }
}
