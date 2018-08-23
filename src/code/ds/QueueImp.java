package code.ds;

import java.util.concurrent.locks.*;
import java.util.Stack;

/**
 * Created by Piyush Patel.
 */
public class QueueImp {

  public static void main(String[] args) {
    System.out.print("QueueImp");
  }

  //Implement the Queue using two Stack
  public class Queue<E> {

    private Stack<Integer> s1 = new Stack<>();
    private Stack<Integer> s2 = new Stack<>();
    int front;
    int size;

    public void EnQueue(int item) {
      if (s1.isEmpty()) {
        front = item;
      }
      s1.push(item);
      size++;
    }

    public int Dequeue() {
      if (s2.size() == 0) {
        while (s1.size() != 0) {
          s2.push(s1.pop());
        }
      }
      size--;
      return s2.pop();
    }

    public boolean empty() {
      return s1.isEmpty() && s2.isEmpty();
    }

    public int peek() {
      if (!s2.isEmpty()) {
        return s2.peek();
      }
      return front;
    }

    public int size() {
      return size;
    }
  }

  //Blocking Queue  is a queue that blocks when you try to dequeue from it when the queue is empty
  // or if you try to enqueue items to it when the queue is already full.
  class BoundedBuffer {
    final Lock lock = new ReentrantLock();
    final Condition notFull  = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    final Object[] items = new Object[100];
    int putptr, takeptr, count;

    public void put(Object x) throws InterruptedException {
      lock.lock();
      try {
        while (count == items.length)
          notFull.await();
        items[putptr] = x;
        if (++putptr == items.length) putptr = 0;
        ++count;
        notEmpty.signal();
      } finally {
        lock.unlock();
      }
    }

    public Object take() throws InterruptedException {
      lock.lock();
      try {
        while (count == 0)
          notEmpty.await();
        Object x = items[takeptr];
        if (++takeptr == items.length) takeptr = 0;
        --count;
        notFull.signal();
        return x;
      } finally {
        lock.unlock();
      }
    }
  }

  //Implemented simple circular queue
  public class CircularArrayQueue<T> {

    private final int DEFAULT_CAPACITY = 100;
    private int front, rear, count;
    private T[] queue;

    public CircularArrayQueue() {
      front = rear = count = 0;
      queue = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    public CircularArrayQueue(int initialCapacity) {
      front = rear = count = 0;
      queue = ((T[]) (new Object[initialCapacity]));
    }

    public void enqueue(T element) {
      if (size() == queue.length) {
        expandCapacity();
      }

      queue[rear] = element;

      rear = (rear + 1) % queue.length;

      count++;
    }

    public T dequeue() throws Exception {
      if (isEmpty()) {
        throw new Exception("queue is Empty");
      }

      T result = queue[front];
      queue[front] = null;

      front = (front + 1) % queue.length;

      count--;

      return result;
    }

    public T first() throws Exception {
      if (isEmpty()) {
        throw new Exception("queue is Empty= ");
      }

      return queue[front];
    }

    public boolean isEmpty() {
      return (count == 0);
    }

    public int size() {
      return count;
    }

    public String toString() {
      String result = "";
      int scan = 0;

      while (scan < count) {
        if (queue[scan] != null) {
          result += queue[scan].toString() + "\n";
        }
        scan++;
      }

      return result;

    }

    public void expandCapacity() {
      T[] larger = (T[]) (new Object[queue.length * 2]);

      for (int scan = 0; scan < count; scan++) {
        larger[scan] = queue[front];
        front = (front + 1) % queue.length;
      }

      front = 0;
      rear = count;
      queue = larger;
    }
  }
}
