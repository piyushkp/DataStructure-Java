package code.ds;
import java.util.*;
import java.util.LinkedList;

/**
 * Created by Piyush Patel.
 */
public class QueueImp{
    public static void main(String [] args) {
        System.out.print("QueueImp");
    }
    //Implement the Queue using two Stack
    public class Queue<E>{
        private Stack<Integer> s1 = new Stack<>();
        private Stack<Integer> s2 = new Stack<>();
        int front;
        int size;
        public void EnQueue(int item) {
            if(s1.isEmpty())
                front = item;
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
        public int size()
        {
            return size;
        }
    }
    //Blocking Queue  is a queue that blocks when you try to dequeue from it when the queue is empty
    // or if you try to enqueue items to it when the queue is already full.
    public class BlockingQueue {
        private List queue = new LinkedList();
        private int  limit = 10;
        public BlockingQueue(int limit){
            this.limit = limit;
        }
        public synchronized void enqueue(Object item) throws InterruptedException  {
            try {
                while (this.queue.size() == this.limit) {
                    wait();
                }
            }catch (InterruptedException ie) {
                notify();
                throw  ie;
            }
            this.queue.add(item);
            if(this.queue.size() < limit)
                notify();
        }
        public synchronized Object dequeue() throws InterruptedException{
            try {
                while (this.queue.size() == 0) {
                    wait();
                }
            }catch (InterruptedException ie) {
                notify();
                throw  ie;
            }
            Object x = this.queue.remove(0);
            if(this.queue.size() > 1)
                notify();
            return x;
        }
        public synchronized void multiput(List<Object> objs) throws Exception {
            if (objs.size() > limit) {
                throw new IllegalArgumentException();
            }
            while (!hasCapacity(objs.size())) {
                try {
                    wait();
                } catch (final InterruptedException e) { }
            }
            for (final Object obj : objs) {
                Objects.requireNonNull(obj);
                this.queue.add(obj);
            }
            notifyAll();
        }
        private synchronized boolean hasCapacity(int n) {
            return (this.queue.size() + n <= limit);
        }
    }
    //Implemented simple thread safe circular queue
    public class CircularArrayQueue<T> {
        private final int DEFAULT_CAPACITY = 100;
        private int front, rear, count;
        private T[] queue;
        public CircularArrayQueue()
        {
            front = rear = count = 0;
            queue = (T[]) (new Object[DEFAULT_CAPACITY]);
        }
        public CircularArrayQueue (int initialCapacity)
        {
            front = rear = count = 0;
            queue = ( (T[])(new Object[initialCapacity]) );
        }
        public void enqueue (T element)
        {
            if (size() == queue.length)
                expandCapacity();

            queue[rear] = element;

            rear = (rear+1) % queue.length;

            count++;
        }
        public T dequeue() throws Exception
        {
            if (isEmpty())
                throw new Exception ("queue is Empty");

            T result = queue[front];
            queue[front] = null;

            front = (front+1) % queue.length;

            count--;

            return result;
        }
        public T first() throws Exception
        {
            if (isEmpty())
                throw new Exception ("queue is Empty= ");

            return queue[front];
        }
        public boolean isEmpty()
        {
            return (count == 0);
        }
        public int size()
        {
            return count;
        }
        public String toString()
        {
            String result = "";
            int scan = 0;

            while(scan < count)
            {
                if(queue[scan]!=null)
                {
                    result += queue[scan].toString()+"\n";
                }
                scan++;
            }

            return result;

        }

        public void expandCapacity()
        {
            T[] larger = (T[])(new Object[queue.length *2]);

            for(int scan=0; scan < count; scan++)
            {
                larger[scan] = queue[front];
                front=(front+1) % queue.length;
            }

            front = 0;
            rear = count;
            queue = larger;
        }
    }
}
