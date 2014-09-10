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
}
