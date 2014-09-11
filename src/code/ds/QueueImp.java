package code.ds;
import java.util.*;
import java.util.LinkedList;

/**
 * Created by Piyush Patel.
 */
public class QueueImp
{
    //Implement the Queue using two Stack
    public class Queue<E>
    {
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
    public class BlockingQueue
    {
        private List queue = new LinkedList();
        private int  limit = 10;

        public BlockingQueue(int limit){
            this.limit = limit;
        }
        public synchronized void enqueue(Object item)
                throws InterruptedException  {
            while(this.queue.size() == this.limit) {
                wait();
            }
            if(this.queue.size() == 0) {
                notifyAll();
            }
            this.queue.add(item);
        }
        public synchronized Object dequeue()
                throws InterruptedException{
            while(this.queue.size() == 0){
                wait();
            }
            if(this.queue.size() == this.limit){
                notifyAll();
            }
            return this.queue.remove(0);
        }
    }
    //Implemented simple thread safe circular queue
    public class CircularQueue {
        private int size;
        private int head;
        private int tail;
        private int q[];
        public CircularQueue(int s) {
            size = s;
            q = new int[s+1];
            head = 0;
            tail = 0;
        }
        public synchronized void initialize() {
            head = 0;
            tail = 0;
        }
        public synchronized boolean enqueue(int v) {
            int tmp = (tail+1) % size;
            if (tmp == head) return false;
            q[tail] = v;
            tail = tmp;
            return true;
        }
        public synchronized int dequeue() throws Exception{
            if (head == tail) throw new Exception("queue underflow!");
            int tmp = q[head];
            head = (head + 1) % size;
            return tmp;
        }
    }

}
