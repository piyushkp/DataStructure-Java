package code.ds;
import java.util.*;
/**
 * Created by Piyush Patel.
 */
public class StackImp {
    public static void main(String [] args) {
        System.out.print("Stack Imp");
    }
    interface Stack<T> {
        Stack<T> push(T ele);
        T pop();
        int size = 0;
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
            if (arr.length == total) resize(arr.length * 2);
            arr[total++] = ele;
            return this;
        }
        public T pop() {
            if (total == 0) throw new java.util.NoSuchElementException();
            T ele = arr[--total];
            arr[total] = null;
            if (total > 0 && total == arr.length / 4) resize(arr.length / 2);
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
        private Node first;

        private class Node {
            private T ele;
            private Node next;
        }

        public StackLinkedList() {
        }

        public StackLinkedList<T> push(T ele) {
            Node current = first;
            first = new Node();
            first.ele = ele;
            first.next = current;
            total++;
            return this;
        }

        public T pop() {
            if (first == null) new java.util.NoSuchElementException();
            T ele = first.ele;
            first = first.next;
            total--;
            return ele;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            Node tmp = first;
            while (tmp != null) {
                sb.append(tmp.ele).append(", ");
                tmp = tmp.next;
            }
            return sb.toString();
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
        if (value <= min()) {
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
    public int min() {
        if (s2.isEmpty()) {
            return Integer.MAX_VALUE;
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
        if (last == null) throw new EmptyStackException();
        int v = (Integer)last.pop();
        if (last.size() == 0) {
            stacks.remove(stacks.size() - 1);
        }
        return v;
    }
}
