package code.ds;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by ppatel2 on 8/17/2016.
 */
public class DelayQueueTest {

  static DelayQueue dq = new DelayQueue();
  static HashSet<String> _set = new HashSet<>();

  public static void main(String... args) throws InterruptedException {
    add("foo", 10);
    add("bar", 4);
    add("bar2", 14);
    add("bar3", 12);
    add("foo1", 7);
    add("foo2", 8);
    add("foo3", 9);
    add("foo4", 3);

    get();
  }

  public static void add(String data, int time) {
    DelayObject ob1 = new DelayObject(data, time);
    if (!_set.contains(ob1.data)) {
      _set.add(ob1.data);
      dq.offer(ob1);
    }
  }

  public static ArrayList<String> get() throws InterruptedException {
    ArrayList<String> _out = new ArrayList<>();
    Iterator itr = dq.iterator();
    Thread.sleep(5000);
    while (itr.hasNext()) {
      DelayObject dt = (DelayObject) itr.next();
      if (dt.getDelay(TimeUnit.SECONDS) > 0) {
        System.out.println(dt.data);
        _out.add(dt.data);
      } else {
        itr.remove();
        _set.remove(dt.data);
      }
    }
    System.out.println(dq.size());
    return _out;
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
    //return unit.convert(diff, unit);
    return diff;
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