package code.ds;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.atomic.*;

/**
 * Created by Piyush Patel.
 */
public class StackImp {

  public static void main(String[] args) {
    System.out.print("Stack Imp");
  }

  interface Stack<T> {

    Stack<T> push(T ele);

    T pop();

    int size = 0;
  }

  //Implement Stack with one queue
  class StackQueue {

    private Queue<Integer> q1 = new LinkedList<>();

    public void push(int x) {
      q1.add(x);
      int sz = q1.size();
      while (sz > 1) {
        q1.add(q1.remove());
        sz--;
      }
    }

    public Integer pop() {
      return q1.remove();
    }

    public boolean empty() {
      return q1.isEmpty();
    }

    public int top() {
      return q1.peek();
    }
  }

  //Implementing a Stack in Java using Arrays
  public class StackArray<T> implements Stack<T> {
    private T[] arr;
    private int total;

    public StackArray() {
      arr = (T[]) new Object[2];
    }

    private void resize(int capacity) {
      T[] tmp = (T[]) new Object[capacity];
      System.arraycopy(arr, 0, tmp, 0, total);
      arr = tmp;
    }

    public StackArray<T> push(T ele) {
      if (arr.length == total) {
        resize(arr.length * 2);
      }
      arr[total++] = ele;
      return this;
    }

    public T pop() {
      if (total == 0) {
        throw new java.util.NoSuchElementException();
      }
      T ele = arr[--total];
      arr[total] = null;
      if (total > 0 && total == arr.length / 4) {
        resize(arr.length / 2);
      }
      return ele;
    }

    @Override
    public String toString() {
      return java.util.Arrays.toString(arr);
    }
  }

  //Implementing a Stack in Java using LinkList
  public class StackLinkedList<T> implements Stack<T> {
    private int total;
    private Node head;
    private class Node {
      private T ele;
      private Node next;
    }

    public StackLinkedList<T> push(T ele) {
      Node current = head;
      head = new Node();
      head.ele = ele;
      head.next = current;
      total++;
      return this;
    }

    public T pop() {
      if (head == null) {
        new java.util.NoSuchElementException();
      }
      T ele = head.ele;
      head = head.next;
      total--;
      return ele;
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();
      Node tmp = head;
      while (tmp != null) {
        sb.append(tmp.ele).append(", ");
        tmp = tmp.next;
      }
      return sb.toString();
    }
  }
  //implement nonblocking ConcurrentStack
  class ConcurrentStack <E> {
    AtomicReference<Node<E>> top = new AtomicReference<>();
    public void push(E item) {
      Node<E> newHead = new Node<E>(item);
      Node<E> oldHead;
      do {
        oldHead = top.get();
        newHead.next = oldHead;
      } while (!top.compareAndSet(oldHead, newHead));
    }

    public E pop() {
      Node<E> oldHead;
      Node<E> newHead;
      do {
        oldHead = top.get();
        if (oldHead == null)
          return null;
        newHead = oldHead.next;
      } while (!top.compareAndSet(oldHead, newHead));
      return oldHead.item;
    }

    class Node<E> {

      public final E item;
      public Node<E> next;

      public Node(E item) {
        this.item = item;
      }
    }
  }

  //Sort a stack using only one additional stack and no other data structure.
  public static java.util.Stack<Integer> sortStack(java.util.Stack<Integer> inputStack) {
    if (inputStack == null) {
      return null;
    }
    java.util.Stack<Integer> tempStack = new java.util.Stack<Integer>();
    while (!inputStack.isEmpty()) {
      int temp = inputStack.pop();
      while (!tempStack.isEmpty() && tempStack.peek() > temp) {
        inputStack.push(tempStack.pop());
      }
      tempStack.push(temp);
    }
    return tempStack;
  }
}

//How would you design a stack which, in addition to push and pop, also has a function min which returns the minimum
//element? Push, pop and min should all operate in O(1) time.
//you can also do 2*value - Minvalue but overflow might occur
class StackWithMin extends java.util.Stack<Integer> {

  java.util.Stack<Integer> s2;

  public StackWithMin() {
    s2 = new java.util.Stack<Integer>();
  }

  public void push(int value) {
    Integer temp = min();
    if (temp == null || value <= temp) {
      s2.push(value);
    }
    super.push(value);
  }

  public Integer pop() {
    int value = super.pop();
    if (value == min()) {
      s2.pop();
    }
    return value;
  }

  public Integer min() {
    if (s2.isEmpty()) {
      return null;
    } else {
      return s2.peek();
    }
  }
}

//Imagine a (literal) stack of plates. If the stack gets too high, it might topple. Therefore, in real life, we would
//likely start a new stack when the previous stack exceeds some threshold. Implement a data structure SetOfStacks that
//mimics this. SetOfStacks should be composed of several stacks, and should create a new stack once the previous one
//exceeds capacity. SetOfStacks.push() and SetOfStacks.pop() should behave identically to a single stack (that is,
//pop() should return the same values as it would if there were just a single stack).
class SetOfStacks {

  ArrayList<Stack> stacks = new ArrayList<Stack>();
  public int capacity;

  public SetOfStacks(int capacity) {
    this.capacity = capacity;
  }

  public Stack getLastStack() {
    if (stacks.size() == 0) {
      return null;
    }
    return stacks.get(stacks.size() - 1);
  }

  public void push(int v) {
    Stack last = getLastStack();
    if (last != null && last.size() != last.capacity()) { // add to last
      last.push(v);
    } else { // must create new stack
      Stack stack = new Stack();
      stack.push(v);
      stacks.add(stack);
    }
  }

  public int pop() {
    Stack last = getLastStack();
    if (last == null) {
      throw new EmptyStackException();
    }
    int v = (Integer) last.pop();
    if (last.size() == 0) {
      stacks.remove(stacks.size() - 1);
    }
    return v;
  }
}
