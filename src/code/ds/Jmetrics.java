package code.ds;


import java.util.concurrent.atomic.*;
import java.util.Date;
import java.util.concurrent.*;

import java.util.concurrent.TimeUnit;

/**
 * Created by patpi02 on 7/15/17.
 */
public class Jmetrics {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Jmetrics obj = new Jmetrics();
        obj.Onboard_product();
        obj.Onboard_product();
        obj.Onboard_product();
        obj.Onboard_tenant();

    }

    public void Onboard_product(){

        String key = "Onboard" + "onboard_products";
        System.out.println(key + " request time: " + new Date());
        Jmetric _metric;
        if(!JmetricsUtil.map.containsKey(key)){
             _metric = new Jmetric();
            _metric.source = "onboard";
            _metric.metric_id = "onboard_products";
            _metric.metric_unit = "value";
            _metric.metric_value.set(1);
            _metric.interval_time.set(60);
            JmetricsUtil.map.put(key, _metric);
            JmetricsUtil util = new JmetricsUtil(key, 60);
        }
        else
        {
            _metric = JmetricsUtil.map.get(key);
            _metric.metric_value.incrementAndGet();
        }


    }
    public void Onboard_tenant(){
        String key = "Onboard" + "onboard_tenant";
        System.out.println(key + " request time: " + new Date());
        Jmetric _metric;
        if(!JmetricsUtil.map.containsKey(key)){
            _metric = new Jmetric();
            _metric.source = "onboard";
            _metric.product_id = "axa";
            _metric.metric_id = "onboard_tenant";
            _metric.metric_unit = "value";
            _metric.metric_value.set(1);
            _metric.interval_time.set(20);
            JmetricsUtil.map.put(key, _metric);
            JmetricsUtil util = new JmetricsUtil(key, 20);
        }
        else
        {
            _metric = JmetricsUtil.map.get(key);
            _metric.metric_value.incrementAndGet();
        }

    }
    public void Onboard_doctype(){

    }

}
class JmetricsUtil{
    static ConcurrentHashMap<String, Jmetric> map = new ConcurrentHashMap<>();
    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    public JmetricsUtil(String key, int delay){

        Task task1 = new Task (key, executor);
        executor.schedule(task1, delay, TimeUnit.SECONDS);
    }

    class Task implements Runnable
    {
        private String key;
        private ScheduledExecutorService executor;
        public Task(String key, ScheduledExecutorService executor ) {
            this.key = key;
            this.executor = executor;
        }


        @Override
        public void run()
        {
            try {
                if(map.containsKey(key)) {
                    Jmetric _metric = map.get(key);
                    System.out.println("Doing a task during : " + key + " - Time - " + new Date());
                    System.out.println(key + " metric_value: " + _metric.metric_value.get());
                    map.remove(key);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                executor.shutdownNow();
            }
        }
    }
}
class Jmetric{

    String product_id;
    String tenant_id;
    String doc_type;
    String source;
    String metric_id;
    AtomicLong metric_value = new AtomicLong();
    String metric_unit;
    AtomicLong interval_time = new AtomicLong();

}
