package code.ds;

/**
 * Created by Piyush Patel.
 */
public class LinkedList {
    class Node {
        int data;
        Node next;
        Node random;
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
        Node temp = new Node();
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
        Node temp = new Node();
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
        Node temp = new Node();
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
    public static Node mergeSort(Node head){
        if(head == null || head.next == null)
            return head;
        Node first = head;
        Node middle = findMiddle(head);
        Node second = middle.next;
        middle.next = null;
        return merge(mergeSort(first),mergeSort(second));
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
    public static Node findMiddle(Node head){
        Node slow = head;
        Node fast = head;
        while(fast.next != null && fast.next.next != null){
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
        while(currentNode.next != null) {
            currentNode = currentNode.next;
            if (currentNode.data > max) max = currentNode.data;
            else if (currentNode.data < min) min = currentNode.data;
        }
        return 1;
    }
    // Reverse Linked List
    void Reverse() {
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
    // Find List is circular or not. time O(n) space O(1)
    Boolean findCircular(Node head) {
        Node slower = head, faster = head;
        while (slower != null && faster != null && faster.next != null) {
            slower = slower.next;
            faster = faster.next.next;
            if (faster == slower ) return true;
        }
        return  false;
    }
    //Detect loop and remove it from linkList
    void detectAndRemoveLoop(Node node) {
        Node slow =head;
        Node fast = head;
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            if(slow == fast) {
                slow = head;
                Node prev = null;
                while(slow != fast){
                    slow = slow.next;
                    prev = fast;
                    fast = fast.next;
                }
                prev.next = null;
            }
        }
    }
    //Write a function that would return the 5th element from the tail (or end) of a singly linked list of integers
    void printNthFromLast(Node head, int n) {
        Node main_ptr = head;
        Node ref_ptr = head;
        int count = 0;
        if (head != null) {
            while (count < n) {
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
    //Given a linked list, reverse alternate nodes and append them to end of list. Extra allowed space is O(1)
    //Input List:  1->2->3->4->5->6    Output List: 1->3->5->6->4->2
    void rearrange(Node odd)
    {
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
        while (odd != null && odd.next != null)
        {
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
    //Given a linked list and two integers M and N. Traverse the linked list such that you retain M nodes then delete next N nodes,
    //continue the same till end of the linked list.M = 2, N = 2 Input: 1->2->3->4->5->6->7->8  Output: 1->2->5->6
    // Function to skip M nodes and then delete N nodes of the linked list.
    void skipMdeleteN(Node  head, int M, int N)
    {
        Node curr = head, t;
        int count;
        while (curr != null)
        {
            // Skip M nodes
            for (count = 1; count<M && curr!= null; count++)
                curr = curr.next;
            // If we reached end of list, then return
            if (curr == null)
                return;
            // Start from next node and delete N nodes
            t = curr.next;
            for (count = 1; count<=N && t!= null; count++){
                t = t.next;
            }
            curr.next = t; // Link the previous list with remaining nodes
            curr = t;
        }
    }
    //Swap nodes in a linked list without swapping data
    //Input:  10->15->12->13->20->14,  x = 12, y = 20   Output: 10->15->20->13->12->14
    void swapNode(Node head, int x, int y)
    {
        if(x == y)
            return;
        Node prevX = null;Node currX = head;
        while(currX != null && currX.data != x)
        {
            prevX = currX;
            currX = currX.next;
        }
        Node prevY = null;Node currY = head;
        while(currY != null && currY.data != y)
        {
            prevY = currY;
            currY = currY.next;
        }
        if(prevX != null)
            prevX.next = currY;
        else
            head = currY;
        if(prevY != null)
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
            return 1;
        }
        if (node1 == null && node2 != null) {
            return -1;
        }
        return 0;
    }
    //Merge a linked list into another linked list at alternate positions
    //function that inserts nodes of linked list q into p at alternate positions.
    void merge(LinkedList q){
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
    //Reverse a Linked List in groups of given size
    //Inputs:  1->2->3->4->5->6->7->8->NULL and k = 3  Output:  3->2->1->6->5->4->8->7->NULL.
    Node reverse(Node head, int k){
        Node current = head;
        Node next = null;
        Node prev = null;
        int count = 0;
       /* Reverse first k nodes of linked list */
        while (count < k && current != null){
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
            count++;
        }
       /*next is now a pointer to (k+1)th node Recursively call for the list starting from current.
        And make rest of the list as next of first node */
        if (next != null)
            head.next = reverse(next, k);
        // prev is now head of input list
        return prev;
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
}
