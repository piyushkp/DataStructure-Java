package code.ds;

import java.util.HashSet;
import java.util.*;

/**
 * Created by Piyush Patel.
 */
public class LinkList {
    public static void main(String[] args) {
        Node  head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        printReverse1(head);
    }

    static class Node {
        int data;
        Node next;
        Node random;
        Node prev;
        public Node(int data){
            this.data = data;
        }
        public Node(){}
    }

    // Traverse Linked List
    void PrintLinkedList(Node start) {
        System.out.print("\nHEAD .");
        while (start != null) {
            System.out.print(start.data);
            start = start.next;
        }
        System.out.print("null\n\n");
    }

    //insert a node at the beginning of the list
    Node head;

    void InsertNodeInLinkedListAtFront(int data) {
        // assumption: head is already defined elsewhere in the program
        // 1. create the new node
        Node temp = new Node(data);
        temp.data = data;
        // 2. insert it at the first position
        temp.next = head;
        // 3. update the head to point to this new node
        head = temp;
    }

    //insert a node at the end of the list
    void InsertNodeInLinkedListAtEnd(int data) {
        // assumption: head is already defined elsewhere in the program
        // 1. create the new node
        Node temp = new Node(data);
        temp.data = data;
        temp.next = null;
        // check if the list is empty
        if (head == null) {
            head = temp;
            return;
        } else {
            // 2. traverse the list till the end
            Node traveller = head;
            while (traveller.next != null) traveller = traveller.next;
            // 3. update the last node to point to this new node
            traveller.next = temp;
        }
    }

    // insert a node in a given location in a list
    void InsertNodeInLinkedList(int data, int position) {
        // assumption: head is already defined elsewhere in the program
        // 1. create the new node
        Node temp = new Node(data);
        temp.data = data;
        temp.next = null;
        // check if the position to insert is first or the list is empty
        if ((position == 1) || (head == null)) {
            // set the new node to point to head
            // as the list may not be empty
            temp.next = head;
            // point head to the first node now
            head = temp;
            return;
        } else {
            // 2. traverse to the desired position
            // or till the list ends; whichever comes first
            Node t = head;
            // remember, we already covered the 1st case
            int currPos = 2;
            while ((currPos < position) && (t.next != null)) {
                t = t.next;
                currPos++;
            }
            // 3. now we are at the desired location
            // 3.a. first set the pointer for the new node
            temp.next = t.next;
            // 3.b. now set the previous node pointer
            t.next = temp;
        }
    }

    //delete a node at a specific location
    int DeleteNodeFromLinkedList(int position) {
        // if the list is empty, return 0
        if (head == null) return 0;
        // special case: deleting first element
        if (position == 1) {
            // set the head to point to the node
            // that head is pointing to
            head = head.next;
        } else {
            // deleting at any other position
            // traverse to the desired position
            // or till the list ends; whichever comes first
            Node t = head;
            // remember, we already covered the 1st case
            int currPos = 2;
            while ((currPos < position) && (t.next != null)) {
                t = t.next;
                currPos++;
            }
            // now comes the tricky part
            // you have to point the current node to its next node
            if (t.next != null) t.next = t.next.next; // NOTE THIS
            else return 0; // could not find the correct node
        }
        // deletion successful
        return 1;
    }

    //Implement an algorithm to delete a node in the middle of a single linked list, given only access to that node
    //The solution to this is to simply copy the data from the next node into this node and then delete the next node
    public static boolean deleteNode(Node n) {
        if (n == null || n.next == null) {
            return false; // Failure
        }
        Node next = n.next;
        n.data = next.data;
        n.next = next.next;
        return true;
    }


    // Sort Link List
    void Sort() {
        // traverse the entire list
        for (Node list = head; list.next != null; list = list.next) {
            // compare to the list ahead
            for (Node pass = list.next; pass != null; pass = pass.next) {
                // compare and swap
                if (list.data > pass.data) {
                    // swap
                    int temp = list.data;
                    list.data = pass.data;
                    pass.data = temp;
                }
            }
        }
    }

    //Merge Sort LinkList Time Complexity O(nlogn)
    public static Node mergeSort(Node head) {
        if (head == null || head.next == null)
            return head;
        Node first = head;
        Node middle = findMiddle(head);
        Node second = middle.next;
        middle.next = null;
        return merge(mergeSort(first), mergeSort(second));
    }

    public static Node merge(Node first, Node second) {
        Node result = null;
  /* Base cases */
        if (first == null)
            return (second);
        else if (second == null)
            return (first);
  /* Pick either a or b, and recur */
        if (first.data <= second.data) {
            result = first;
            result.next = merge(first.next, second);
        } else {
            result = second;
            result.next = merge(first, second.next);
        }
        return (result);
    }

    public static Node findMiddle(Node head) {
        Node slow = head;
        Node fast = head;
        while (slow.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    //Search an element in linked list
    Node Find(int value) {
        // start at the root
        Node currentNode = head;
        // loop through the entire list
        while (currentNode != null) {
            // if we have a match
            if (currentNode.data == value) return currentNode;
            else // move to the next element
                currentNode = currentNode.next;
        }
        return null;
    }

    // Find maximum and minimum in a linked list
    int MaxMinInList(int max, int min) {
        Node currentNode = head;
        if (currentNode == null) return 0; // list is empty
        // initialize the max and min values to the first node
        max = min = currentNode.data;
        // loop through the list
        while (currentNode.next != null) {
            currentNode = currentNode.next;
            if (currentNode.data > max) max = currentNode.data;
            else if (currentNode.data < min) min = currentNode.data;
        }
        return 1;
    }

    //If your are given an Integer Singly linked list. Print it   backwards. Constraints: 1. Do not manipulate the list.
    //(example: do not make it a doubly linked list, do not add or delete elements, do not change any memory location of any element)
    //2. O(n) < time < O(n^2) 3. O(1) < space < O(n)
    //Time = O(N log N) space = O(log N)
    static void printReverse(Node head, Node end){
        if(head == end)
            return;
        Node slow = head, fast = head;
        while(fast.next != end && fast.next.next != end){
            slow = slow.next;
            fast = fast.next.next;
        }
        printReverse(slow.next, end);
        System.out.print(slow.data);
        printReverse(head, slow);
    }
    //print reverse in O(N) time and O(sqrt(N)) space
    static void printReverse1(Node head){
        Node node = head;
        int n = 0;
        while(node != null){
            node = node.next;
            n++;
        }
        node = head;
        int k = (int)Math.sqrt(n);
        Stack<Node> _stack = new Stack<>();
        _stack.push(node);
        int temp = 0;
        for (int i = 0; i < n; i++) {
            if(temp == k){
                _stack.push(node);temp = 0;
            }
            node = node.next;
            temp++;
        }
        Stack<Node> _stack2 = new Stack<>();
        while(_stack.size() > 0){
            Node _t = _stack.pop();
            _stack2.push(_t);
            for (int i = 1; i < k; i++) {
                _stack2.push(_t.next);
                _t = _t.next;
            }
            while(!_stack2.isEmpty()){
                System.out.print(_stack2.pop().data);
            }
        }
    }

    // Reverse Linked List
    public static void Reverse(Node head) {
        // Initialize currentNode pointer to the start of the list and prevNode to null
        // (as the new list is currently pointing to null).
        Node currentNode = head;
        Node prevNode = null;
        Node nextNode = null;
        while (currentNode != null) {
            // Save the next node in nextNode
            nextNode = currentNode.next;
            // Set the currentNode to point to the prevNode.
            currentNode.next = prevNode;
            // Move the prevNode to the currentNode.
            prevNode = currentNode;
            // Move the currentNode pointer to the nextNode.
            currentNode = nextNode;
        }
        // reset the head pointer to point to the prevNode
        // as that is now the current head of the reversed list
        head = prevNode;
    }

    //reverse single linklist recursively
    Node reverseUtil(Node curr, Node prev) {
        /* If last node mark it head*/
        if (curr.next == null) {
            head = curr;
            /* Update next to prev node */
            curr.next = prev;
            return null;
        }
        /* Save curr->next node for recursive call */
        Node next1 = curr.next;
        /* and update next ..*/
        curr.next = prev;
        reverseUtil(next1, curr);
        return head;
    }
    //Reverse a Linked List in groups of given size
    //Inputs:  1->2->3->4->5->6->7->8->NULL and k = 3  Output:  3->2->1->6->5->4->8->7->NULL.
    public Node reverseKGroup(Node head, int k) {
        Node begin;
        if (head==null || head.next ==null || k==1)
            return head;
        Node dummyhead = new Node(-1);
        dummyhead.next = head;
        begin = dummyhead;
        int i=0;
        while (head != null){
            i++;
            if (i%k == 0){
                begin = reverse(begin, head.next);
                head = begin.next;
            } else {
                head = head.next;
            }
        }
        return dummyhead.next;
    }
    public Node reverse(Node begin, Node end){
        Node curr = begin.next;
        Node next, first;
        Node prev = begin;
        first = curr;
        while (curr!=end){
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        begin.next = prev;
        first.next = curr;
        return first;
    }

    // Find List is circular or not. Detect cycle time O(n) space O(1)
    Boolean findCircular(Node head) {
        Node slower = head, faster = head;
        while (slower != null && faster != null && faster.next != null) {
            slower = slower.next;
            faster = faster.next.next;
            if (faster == slower) return true;
        }
        return false;
    }

    //Detect loop and remove it from linkList
    void detectAndRemoveLoop(Node node) {
        Node slow = head;
        Node fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                slow = head;
                while (slow != fast.next) {
                    slow = slow.next;
                    fast = fast.next;
                }
                fast.next = null;
            }
        }
    }

    //Write a function that would return the kth element from the tail (or end) of a singly linked list of integers
    void printNthFromLast(Node head, int k) {
        Node main_ptr = head;
        Node ref_ptr = head;
        int count = 0;
        if (head != null) {
            while (count < k) {
                if (ref_ptr == null) {
                    System.out.print("n is greater than the no. of nodes in list");
                    return;
                }
                ref_ptr = ref_ptr.next;
                count++;
            } /* End of while*/
            while (ref_ptr != null) {
                main_ptr = main_ptr.next;
                ref_ptr = ref_ptr.next;
            }
            System.out.print("Node no. n from last is " + main_ptr.data);
        }
    }

    //Copy a linked list with next and random pointer
    Node copyList(Node head) {
        Node copy = null, temp = null, ptr = head;
        while (ptr != null) {
            temp = ptr;
            ptr.next = temp;
            ptr = ptr.next.next;
        }
        ptr = head;
        while (ptr != null && ptr.next != null) {
            ptr.next.random = ptr.random.next;
            ptr = ptr.next.next;
        }
        ptr = head;
        Node prev = null;
        while (ptr != null) {
            if (copy == null) copy = ptr.next;
            else prev.next = ptr.next;
            prev = ptr.next;
            ptr = ptr.next.next;
        }
        return copy;
    }
    // Reverse linkList with next and random pointers. all next and random pointer should be reversed.


    //Implement a function to check if a linked list is a palindrome (like 0->1->2->1->0)
    boolean isPalindrome(Node head) {
        if (head == null) return false;
        Node p1 = head, p2 = head;
        java.util.Stack<Integer> s = new java.util.Stack<Integer>();
        while (p2 != null && p2.next != null) {
            s.push(p1.data);
            p1 = p1.next;
            p2 = p2.next.next;
        }
        // handle odd nodes
        if (p2 != null) p1 = p1.next;
        while (p1 != null) {
            if (p1.data != s.pop()) return false;
            p1 = p1.next;
        }
        return true;
    }

    //Given a singly linked list, group all odd nodes together followed by the even nodes.
    //Input: 1->2->3->4->5->NULL   Output 1->3->5->2->4->NULL
    public static Node oddEvenList(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node odd = head;
        Node even = head.next;
        Node evenHead = even;
        while (even != null && even.next != null) {
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
        }
        odd.next = evenHead;
        return head;
    }

    //Given a linked list, reverse alternate nodes and append them to end of list. Extra allowed space is O(1)
    //Input List:  1->2->3->4->5->6    Output List: 1->3->5->6->4->2
    void rearrange(Node odd) {
        // If linked list has less than 3 nodes, no change is required
        if (odd == null || odd.next == null || odd.next.next == null)
            return;
        // even points to the beginning of even list
        Node even = odd.next;
        // Remove the first even node
        odd.next = odd.next.next;
        // odd points to next node in odd list
        odd = odd.next;
        // Set terminator for even list
        even.next = null;
        // Traverse the  list
        while (odd != null && odd.next != null) {
            // Store the next node in odd list
            Node temp = odd.next.next;
            // Link the next even node at the beginning of even list
            odd.next.next = even;
            even = odd.next;
            // Remove the even node from middle
            odd.next = temp;
            // Move odd to the next odd node
            if (temp != null)
                odd = temp;
        }
        // Append the even list at the end of odd list
        odd.next = even;
    }

    //Given a singly linked list L0 -> L1 -> … -> Ln-1 -> Ln.
    //Rearrange the nodes in the list so that the new formed list is : L0 -> Ln -> L1 -> Ln-1 -> L2 -> Ln-2 …
    //Input:  1 -> 2 -> 3 -> 4     Output: 1 -> 4 -> 2 -> 3
    public void rearrange1(Node node) {
        // 1) Find the middle point using tortoise and hare method
        Node slow = node, fast = slow.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        // 2) Split the linked list in two halves, node1= head of first half    1 -> 2 -> 3 // node2, head of second half   4 -> 5
        Node node1 = node;
        Node node2 = slow.next;
        slow.next = null;
        // 3) Reverse the second half, i.e., 5 -> 4
        node2 = reverselist(node2);
        // 4) Merge alternate nodes
        node = new Node(0); // Assign dummy Node
        // curr is the pointer to this dummy Node, which will be used to form the new list
        Node curr = node;
        while (node1 != null || node2 != null) {
            // First add the element from first list
            if (node1 != null) {
                curr.next = node1;
                curr = curr.next;
                node1 = node1.next;
            }
            // Then add the element from second list
            if (node2 != null) {
                curr.next = node2;
                curr = curr.next;
                node2 = node2.next;
            }
        }
        // Assign the head of the new list to head pointer
        node = node.next;
    }

    public static Node reverselist(Node node) {
        Node prev = null, curr = node, next;
        while (curr != null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        node = prev;
        return node;
    }

    //Given a linked list and two integers M and N. Traverse the linked list such that you retain M nodes then delete next N nodes,
    //continue the same till end of the linked list.M = 2, N = 2 Input: 1->2->3->4->5->6->7->8  Output: 1->2->5->6
    // Function to skip M nodes and then delete N nodes of the linked list.
    void skipMdeleteN(Node head, int M, int N) {
        Node curr = head, t;
        int count;
        while (curr != null) {
            // Skip M nodes
            for (count = 1; count < M && curr != null; count++)
                curr = curr.next;
            // If we reached end of list, then return
            if (curr == null)
                return;
            // Start from next node and delete N nodes
            t = curr.next;
            for (count = 1; count <= N && t != null; count++) {
                t = t.next;
            }
            curr.next = t; // Link the previous list with remaining nodes
            curr = t;
        }
    }

    //Swap nodes in a linked list without swapping data
    //Input:  10->15->12->13->20->14,  x = 12, y = 20   Output: 10->15->20->13->12->14
    void swapNode(Node head, int x, int y) {
        if (x == y)
            return;
        Node prevX = null;
        Node currX = head;
        while (currX != null && currX.data != x) {
            prevX = currX;
            currX = currX.next;
        }
        Node prevY = null;
        Node currY = head;
        while (currY != null && currY.data != y) {
            prevY = currY;
            currY = currY.next;
        }
        if (prevX != null)
            prevX.next = currY;
        else
            head = currY;
        if (prevY != null)
            prevY.next = currX;
        else
            head = currX;
        Node temp = currY.next;
        currY.next = currX.next;
        currX.next = temp;
    }

    //Compare two strings represented as linked lists
    //Input: list1 = g->e->e->k->s->a list2 = g->e->e->k->s->b   Output: -1
    int compare(Node node1, Node node2) {
        if (node1 == null && node2 == null) {
            return 1;
        }
        while (node1 != null && node2 != null && node1.data == node2.data) {
            node1 = node1.next;
            node2 = node2.next;
        }
        // if the list are diffrent in size
        if (node1 != null && node2 != null) {
            return (node1.data > node2.data ? 1 : -1);
        }
        // if either of the list has reached end
        if (node1 != null && node2 == null) {
            return -1;
        }
        if (node1 == null && node2 != null) {
            return -1;
        }
        return 0;
    }

    //Merge a linked list into another linked list at alternate positions
    //function that inserts nodes of linked list q into p at alternate positions.
    void merge(LinkList q) {
        Node p_curr = head, q_curr = q.head;
        Node p_next, q_next;
        // While there are available positions in p;
        while (p_curr != null && q_curr != null) {
            // Save next pointers
            p_next = p_curr.next;
            q_next = q_curr.next;
            // make q_curr as next of p_curr
            q_curr.next = p_next; // change next pointer of q_curr
            p_curr.next = q_curr; // change next pointer of p_curr
            // update current pointers for next iteration
            p_curr = p_next;
            q_curr = q_next;
        }
        q.head = q_curr;
    }

    //Given a singly linked list, select a random node from linked list (the probability of picking a node should be
    //1/N if there are N nodes in list).
    // A reservoir sampling based function to print a random node from a linked list
    void printrandom(Node node) {
        // If list is empty
        if (node == null) {
            return;
        }
        // Use a different seed value so that we don't get same result each time we run this program
        Math.abs(java.util.UUID.randomUUID().getMostSignificantBits());
        // Initialize result as first node
        int result = node.data;
        // Iterate from the (k+1)th element to nth element
        Node current = node;
        int n;
        for (n = 2; current != null; n++) {
            // change result with probability 1/n
            if (Math.random() % n == 0) {
                result = current.data;
            }
            // Move to next node
            current = current.next;
        }
        System.out.println("Randomly selected key is " + result);
    }

    /* Function to remove duplicates from a unsorted linked list
    * Time = O(n2) Space O(1) can be solve with hashtable with O(n)*/
    void remove_duplicates() {
        Node ptr1 = null, ptr2 = null, dup = null;
        ptr1 = head;
        /* Pick elements one by one */
        while (ptr1 != null && ptr1.next != null) {
            ptr2 = ptr1;
            /* Compare the picked element with rest of the elements */
            while (ptr2.next != null) {
                /* If duplicate then delete it */
                if (ptr1.data == ptr2.next.data) {
                    /* sequence of steps is important here */
                    dup = ptr2.next;
                    ptr2.next = ptr2.next.next;
                    System.gc();
                } else /* This is tricky */ {
                    ptr2 = ptr2.next;
                }
            }
            ptr1 = ptr1.next;
        }
    }

    //Remove duplicates from a sorted linked list
    void removeDuplicates() {
        Node current = head;
        /* Pointer to store the next pointer of a node to be deleted*/
        Node next_next;
        if (head == null)
            return;
        while (current.next != null) {
            /*Compare current node with the next node */
            if (current.data == current.next.data) {
                next_next = current.next.next;
                current.next = null;
                current.next = next_next;
            } else // advance if no deletion
                current = current.next;
        }
    }

    //You have two numbers represented by a linked list, where each node contains a sin-gle digit The digits are stored
    //in reverse order, such that the 1’s digit is at the head of the list Write a function that adds the two numbers
    //and returns the sum as a linked list. Input= (3-1-5),(5-9-2) out = 9-0-7
    void addList(Node head1, Node head2, Node result) {
        Node cur;
        // first list is empty
        if (head1 == null) {
            result = head2;
            return;
        }
        // second list is empty
        else if (head2 == null) {
            result = head1;
            return;
        }
        int size1 = getSize(head1);
        int size2 = getSize(head2);
        int carry = 0;
        // Add same size lists
        if (size1 == size2)
            result = addSameSize(head1, head2, carry);
        else {
            int diff = Math.abs(size1 - size2);
            // First list should always be larger than second list. If not, swap pointers
            if (size1 < size2)
                swapPointer(head1, head2);
            // move diff. number of nodes in first list
            cur = head1;
            while (diff > 0) {
                cur = cur.next;
                diff--;
            }
            // get addition of same size lists
            result = addSameSize(cur, head2, carry);
            // get addition of remaining first list and carry
            addCarryToRemaining(head1, cur, carry, result);
        }
        // if some carry is still there, add a new node to the front of the result list. e.g. 999 and 87
        //if (carry != 0)
        //    result.addAtFront(carry);
    }

    // A utility function to swap two pointers
    void swapPointer(Node a, Node b) {
        Node t = a;
        a = b;
        b = t;
    }

    /* A utility function to get size of linked list */
    int getSize(Node node) {
        int size = 0;
        while (node != null) {
            node = node.next;
            size++;
        }
        return size;
    }

    // Adds two linked lists of same size represented by head1 and head2 and returns head of the resultant linked list.
    // Carry is propagated while returning from the recursion
    Node addSameSize(Node head1, Node head2, int carry) {
        // Since the function assumes linked lists are of same size,check any of the two head pointers
        if (head1 == null)
            return null;
        int sum;
        // Allocate memory for sum node of current two nodes
        Node result = new Node();
        // Recursively add remaining nodes and get the carry
        result.next = addSameSize(head1.next, head2.next, carry);
        // add digits of current nodes and propagated carry
        sum = head1.data + head2.data + carry;
        carry = sum / 10;
        sum = sum % 10;
        // Assign the sum to current node of resultant list
        result.data = sum;
        return result;
    }

    // This function is called after the smaller list is added to the bigger lists's sublist of same size.
    // Once the right sublist is added, the carry must be added toe left side of larger list to get the final result.
    void addCarryToRemaining(Node head1, Node cur, int carry, Node result) {
        int sum;
        // If diff. number of nodes are not traversed, add carry
        if (head1 != cur) {
            addCarryToRemaining(head1.next, cur, carry, result);
            sum = head1.data + carry;
            carry = sum / 10;
            sum %= 10;
            // add this node to the front of the result
            //result.addAtFront(sum);
        }
    }

    //Insert an element in a sorted circular linked list.
    void sortedInsert(Node new_node) {
        Node current = head;
        // Case 1 : Linked List is empty
        if (current == null) {
            new_node.next = new_node;
            head = new_node;
        }
        // Case 2: New node is to be inserted just before the head node
        // improvement for this case to swap the data part of head node and new node
        //swap(current.data, new_node.data);
        //new_node.next = head.next;
        //head.next = new_node;
        else if (current.data >= new_node.data) {
            /* If value is smaller than head's value then we need to change next of last node */
            while (current.next != head)
                current = current.next;
            current.next = new_node;
            new_node.next = head;
            head = new_node;
        }
        // Case 3:New node is to be  inserted somewhere after the head
        else {
            /* Locate the node before the point of insertion */
            while (current.next != head && current.next.data < new_node.data)
                current = current.next;
            new_node.next = current.next;
            current.next = new_node;
        }
    }

    //Write a function to remove/Delete a single occurrence of an integer from a doubly linked list if it is present.
    void remove(Node head, int value) {
        Node cur = head;
        if (head == null) {
            return;
        }
        /* If node to be deleted is head node */
        if (head.data == value) {
            head = cur.next;
        }
        while (cur != null) {
            if (cur.data == value) {
                if (cur.prev != null)
                    cur.prev.next = cur.next;
                if (cur.next != null)
                    cur.next.prev = cur.prev;
                break;
            }
            cur = cur.next;
        }
    }

    //Write a function to get the intersection point of two Linked Lists.
    int getInterSectionNode(Node head1, Node head2) {
        int c1 = getCount(head1);
        int c2 = getCount(head2);
        int d;
        if (c1 > c2) {
            d = c1 - c2;
            return _getIntesectionNode(d, head1, head2);
        } else {
            d = c2 - c1;
            return _getIntesectionNode(d, head2, head1);
        }
    }

    /* function to get the intersection point of two linked lists head1 and head2 where head1 has d more nodes than head2 */
    int _getIntesectionNode(int d, Node node1, Node node2) {
        Node current1 = node1;
        Node current2 = node2;
        for (int i = 0; i < d; i++) {
            current1 = current1.next;
        }
        while (current1 != null && current2 != null) {
            if (current1.data == current2.data) {
                return current1.data;
            }
            current1 = current1.next;
            current2 = current2.next;
        }
        return -1;
    }

    /*Takes head pointer of the linked list and returns the count of nodes in the list */
    int getCount(Node node) {
        Node current = node;
        int count = 0;
        while (current != null) {
            current = current.next;
            count++;
        }
        return count;
    }

    //Given a number represented as a linked list, add '1' to it. No extra space, and in liner time.
    Node addOne(Node head) {
        // Reverse linked list
        head = reverselist(head);
        head = addOneToList(head);
        // Reverse the modified list
        return reverselist(head);
    }

    public Node addOneToList(Node head) {
        Node res = head;
        Node temp = null;
        int carry = 1, sum;
        while (head != null) {
            sum = carry + head.data;
            // update carry for next calculation
            carry = (sum >= 10) ? 1 : 0;
            // update sum if it is greater than 10
            sum = sum % 10;
            // Create a new node with sum as data
            head.data = sum;
            // Move head and second pointers to next nodes
            temp = head;
            head = head.next;
        }
        // if some carry is still there, add a new node to result list.
        if (carry > 0) {
            Node _node = new Node();
            _node.data = carry;
            temp.next = _node;
        }
        return res;
    }

    public Node addOneToList1(Node head) {
        // Add 1 to linked list from end to beginning
        int carry = addWithCarry(head);
        // If there is carry after processing all nodes, then we need to add a new node to linked list
        if (carry > 0) {
            Node newNode = new Node();
            newNode.data = carry;
            newNode.next = head;
            return newNode; // New node becomes head now
        }
        return head;
    }

    public static int addWithCarry(Node head) {
        // If linked list is empty, then return carry
        if (head == null)
            return 1;
        // Add carry returned be next node call
        int sum = head.data + addWithCarry(head.next);
        // Update data and return new carry
        head.data = (sum) % 10;
        return (sum) / 10;
    }

    //Given a linked list of length n, and without creating any secondary data structures, how would you find an entry
    //in the linked list in less than n probes?
    public static boolean searchListfast(Node head, int target) {
        Node prev = null;
        Node ptr = head;
        if (head == null)
            return false;
        while (ptr != null) {
            if (ptr.data == target)
                return true;
            else if (ptr.data > target)
                return search(prev, ptr, target);
            prev = ptr;
            if (ptr.next != null && ptr.next.next != null)
                ptr = ptr.next.next;
            else if (ptr.next != null && ptr.next.data == target)
                return true;
            else
                return false;
        }
        return false;
    }

    public static boolean search(Node start, Node end, int target) {
        if (start == null)
            return false;
        while (start != end) {
            if (start.data == target)
                return true;
            start = start.next;
        }
        return false;
    }

    // remove/delete odd numbers from the linklist
    public static Node removeOdd(Node head) {
        if (head == null)
            return null;
        Node curr = head;
        while (curr.data % 2 != 0) {
            head = curr.next;
            curr = curr.next;
        }
        curr = curr.next;
        Node prev = head;
        while (curr != null) {
            if (curr.data % 2 != 0) {
                prev.next = curr.next;
            } else
                prev = curr;
            curr = curr.next;
        }
        return head;
    }
    // deck of card LinkList shuffling i.e input = 1 2 3 4 5 6 7 8 9 10 output = 6 1 7 2 8 3 9 4 10 5
    static void interleave(Node first, Node second) {
        Node tail = null;
        while (second != null) {
            /* Append the first element of 'second' to the list. */
            if (tail == null) {
                tail = second;
            } else {
                tail.next = second;
                tail = second;
            }
            /* Cut the head of 'second' from 'second.' */
            Node next = second.next;
            second.next = null;
            second = next;
            /* Swap the two lists. */
            Node temp = first;
            first = second;
            second = temp;
        }
    }
    //Merge two sorted link list
    Node MergeLists(Node list1, Node list2) {
        if (list1 == null)
            return list2;
        if (list2 == null)
            return list1;
        Node head;
        if (list1.data < list2.data) {
            head = list1;
        } else {
            head = list2;
            list2 = list1;
            list1 = head;
        }
        while (list1.next != null && list2 != null) {
            if (list1.next.data <= list2.data) {
                list1 = list1.next;
            } else {
                Node tmp = list1.next;
                list1.next = list2;
                list2 = tmp;
            }
        }
        if (list1.next == null)
            list1.next = list2;
        return head;
    }
    //sorted linklist to BST
    Node sortedListToBSTRecur(int n){
        /* Base Case */
        if (n <= 0)
            return null;
        /* Recursively construct the left subtree */
        Node left = sortedListToBSTRecur(n / 2);
        /* head_ref now refers to middle node, make middle node as root of BST*/
        Node root = head;
        // Set pointer to left subtree
        root.prev = left;
        /* Change head pointer of Linked List for parent recursive calls */
        head = head.next;
        /* Recursively construct the right subtree and link it with root. The number of nodes in right subtree  is
           total nodes - nodes in left subtree - 1 (for root) */
        root.next = sortedListToBSTRecur(n - n / 2 - 1);

        return root;
    }
    //Flatten Linked list. Given a linked list where in addition to the next pointer, each node has a child pointer, which may or may not point to a separate list.
    //http://www.geeksforgeeks.org/flatten-a-multi-level-linked-list-set-2-depth-wise/
    class flatList{
        int data;
        flatList next;
        flatList down;
    }
    flatList flattenLinkedList(flatList head){
        Stack<flatList> stack = new Stack<>();
        stack.push(head);
        while(!stack.isEmpty()){
            flatList node = stack.pop();
            if(node.down != null && node.next != null){
                stack.push(node.next);
                stack.push(node.down);
                node.next = node.down;
                node.down = null;
            }
            else if(node.next != null){
                stack.push(node.next);
            }
            else if(node.down != null){
                stack.push(node.down);
                node.next = node.down;
                node.down = null;
            }else
                node.next = stack.peek();
        }
        return head;
    }
}
