package code.ds;
import java.util.Iterator;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
/**
 * Created by ppatel2 on 8/17/2016.
 */
public class DelayQueueTest {
    public static void main(String... args){
        DelayQueue  dq=new DelayQueue();
        DelayObject ob1=new DelayObject("foo",10);
        DelayObject ob2=new DelayObject("bar", 5);
        DelayObject ob3=new DelayObject("test", 15);

        dq.offer(ob1);
        dq.offer(ob2);
        dq.offer(ob3);

        Iterator itr=dq.iterator();
        while(itr.hasNext()){
            DelayObject dt=(DelayObject)itr.next();
            if(dt.getDelay(TimeUnit.SECONDS)> 0)
                System.out.println(dt.data);
        }
    }
}
class DelayObject implements Delayed {
    public String data;
    public long startTime;

    public DelayObject(String data, long delay) {
        this.data = data;
        this.startTime = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) + delay;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long diff = startTime - TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        return unit.convert(diff, unit);
    }

    @Override
    public int compareTo(Delayed o) {
        if (this.startTime < ((DelayObject) o).startTime) {
            return -1;
        }
        if (this.startTime > ((DelayObject) o).startTime) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "{" +
                "data='" + data + '\'' +
                ", startTime=" + startTime +
                '}';
    }
}