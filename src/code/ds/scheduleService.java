package code.ds;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by patpi02 on 8/20/17.
 */
public class scheduleService implements Runnable {

        public static ScheduledExecutorService SERVICE = Executors.newSingleThreadScheduledExecutor();
        private int delay;


        public scheduleService() {

        }

        public void run () {
            System.out.println(delay);
        }

        public static void main (String[] args) {

            scheduleService obj = new scheduleService();
            obj.delay = 10;
            SERVICE.schedule(obj, 10, TimeUnit.SECONDS);
            obj.delay = 15;
            SERVICE.schedule(obj, 15, TimeUnit.SECONDS);

            SERVICE.shutdown();
        }
    }

