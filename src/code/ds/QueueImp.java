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
    public class ArrayCircularBuffer<T> {
        // internal data storage
        private T[] data;
        // indices for inserting and removing from queue
        private int front = 0;
        private int insertLocation = 0;
        // number of elements in queue
        private int size = 0;
        public ArrayCircularBuffer(int bufferSize) {
            data = (T[]) new Object[bufferSize];
        }
        /**
         * Inserts an item at the end of the queue. If the queue is full, the oldest
         * value will be removed and head of the queue will become the second oldest
         * value. */
        public synchronized void insert(T item) {
            data[insertLocation] = item;
            insertLocation = (insertLocation + 1) % data.length;
            /**If the queue is full, this means we just overwrote the front of the
             * queue. So increment the front location.*/
            if (size == data.length) {
                front = (front + 1) % data.length;
            } else {
                size++;
            }
        }
        public synchronized int size() {
            return size;
        }
        public synchronized T removeFront() {
            if (size == 0) {
                throw new NoSuchElementException();
            }
            T retValue = data[front];
            front = (front + 1) % data.length;
            size--;
            return retValue;
        }
        /** Returns the head of the queue but does not remove it.*/
        public synchronized T peekFront() {
            if (size == 0) {
                return null;
            } else {
                return data[front];
            }
        }
        /**Returns the last element of the queue but does not remove it.*/
        public synchronized T peekLast() {
            if (size == 0) {
                return null;
            } else {
                int lastElement = insertLocation - 1;
                if (lastElement < 0) {
                    lastElement = data.length - 1;
                }
                return data[lastElement];
            }
        }
    }
}
