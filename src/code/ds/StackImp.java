package code.ds;

/**
 * Created by Piyush Patel.
 */
public class StackImp
{
    interface Stack<T> {
        Stack<T> push(T ele);
        T pop();
    }
    //Implementing a Stack in Java using Arrays
    public class StackArray<T> implements Stack<T> {
        private T[] arr;
        private int total;
        public StackArray()
        {
            arr = (T[]) new Object[2];
        }
        private void resize(int capacity)
        {
            T[] tmp = (T[]) new Object[capacity];
            System.arraycopy(arr, 0, tmp, 0, total);
            arr = tmp;
        }
        public StackArray<T> push(T ele)
        {
            if (arr.length == total) resize(arr.length * 2);
            arr[total++] = ele;
            return this;
        }
        public T pop()
        {
            if (total == 0) throw new java.util.NoSuchElementException();
            T ele = arr[--total];
            arr[total] = null;
            if (total > 0 && total == arr.length / 4) resize(arr.length / 2);
            return ele;
        }
        @Override
        public String toString()
        {
            return java.util.Arrays.toString(arr);
        }
    }
    //Implementing a Stack in Java using LinkList
    public class StackLinkedList<T> implements Stack<T> {
        private int total;
        private Node first;
        private class Node {
            private T ele;
            private Node next;
        }
        public StackLinkedList() { }
        public StackLinkedList<T> push(T ele)
        {
            Node current = first;
            first = new Node();
            first.ele = ele;
            first.next = current;
            total++;
            return this;
        }

        public T pop()
        {
            if (first == null) new java.util.NoSuchElementException();
            T ele = first.ele;
            first = first.next;
            total--;
            return ele;
        }
        @Override
        public String toString()
        {
            StringBuilder sb = new StringBuilder();
            Node tmp = first;
            while (tmp != null) {
                sb.append(tmp.ele).append(", ");
                tmp = tmp.next;
            }
            return sb.toString();
        }
    }
}
