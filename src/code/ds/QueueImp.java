package code.ds;
import java.util.*;
import java.util.LinkedList;

/**
 * Created by Piyush Patel.
 */
public class QueueImp
{
    //Implement the Queue using two Stack
    public class Queue<E>{
        private Stack<E> Inbox = new Stack<E>();
        private Stack<E> Outbox = new Stack<E>();
        public void EnQueue(E item) {
            Inbox.push(item);
        }
        public E Dequeue() {
            if (Outbox.size() == 0) {
                while (Inbox.size() != 0) {
                    Outbox.push(Inbox.pop());
                }
            }
            return Outbox.pop();
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
        public synchronized void enqueue(Object item)
                throws InterruptedException  {
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
        public synchronized Object dequeue()
                throws InterruptedException{
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
    //http://www.museful.net/2012/software-development/circulararraylist-for-java
    public class CircularQueue {
        private int size;
        private int head;
        private int tail;
        private Object q[];
        public CircularQueue(int s) {
            size = s;
            q = new Object[s+1];
            head = 0;
            tail = 0;
        }
        public synchronized void initialize() {
            head = 0;
            tail = 0;
        }
        public int size() {
            if(tail > head)
                return tail - head;
            return size - head + tail;
        }
        public boolean isEmpty() {
            return (tail == head) ? true : false;
        }
        public boolean isFull() {
            int diff = tail - head;
            if(diff == -1 || diff == (size -1))
                return true;
            return false;
        }
        public synchronized void enqueue(Object v) throws Exception{
            if(isFull()){
                throw new Exception("Queue is Full.");
            }
            else {
                q[tail] = v;
                tail = (tail + 1) % size;
            }
        }
        public synchronized Object dequeue() throws Exception{
            Object tmp;
            if (isEmpty())
                throw new Exception("queue underflow!");
            else {
                tmp = q[head];
                q[head] = null;
                head = (head + 1) % size;
            }
            return tmp;
        }
        //Creates a new array to store the contents of the queue with twice the capacity of the old one.
        /*public void expandCapacity(){
           Object[] larger = (Object[])(new Object[q.length *2]);
            for(int scan=0; scan < size; scan++){
                larger[scan] = q[head];
                head=(head+1) % q.length;
            }
            head = 0;
            tail = size;
            q = larger;
        }*/
    }
}
