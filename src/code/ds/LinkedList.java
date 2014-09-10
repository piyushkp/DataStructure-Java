package code.ds;

/**
 * Created by Piyush Patel.
 */
public class LinkedList
{
    typedef struct linked_list
    {
        int data;
        struct linked_list *next;
    } Node;

    // Traverse Linked List
    void PrintLinkedList(Node *start)
    {
        printf("\nHEAD ->");
        while (start != NULL)
        {
            printf("%d ->", start->data);
            start = start->next;
        }
        printf("NULL\n\n");
    }

    //insert a node at the beginning of the list
    Node *head;
    void InsertNodeInLinkedListAtFront(int data)
    {
        // assumption: head is already defined elsewhere in the program
        // 1. create the new node
        Node *temp = new Node;
        temp->data = data;
        temp->next = NULL; // this line is not really needed
        // 2. insert it at the first position
        temp->next = head;
        // 3. update the head to point to this new node
        head = temp;
    }

    //insert a node at the end of the list
    void InsertNodeInLinkedListAtEnd(int data)
    {
        // assumption: head is already defined elsewhere in the program
        // 1. create the new node
        Node *temp = new Node;
        temp->data = data;
        temp->next = NULL;
        // check if the list is empty
        if (head == NULL)
        {
            head = temp;
            return;
        }
        else
        {
            // 2. traverse the list till the end
            Node *traveller = head;
            while (traveller->next != NULL)
                traveller = traveller->next;
            // 3. update the last node to point to this new node
            traveller->next = temp;
        }
    }

    // insert a node in a given location in a list
    void InsertNodeInLinkedList(int data, int position)
    {
        // assumption: head is already defined elsewhere in the program
        // 1. create the new node
        Node *temp = new Node;
        temp->data = data;
        temp->next = NULL;
        // check if the position to insert is first or the list is empty
        if ((position == 1) || (head == NULL))
        {
            // set the new node to point to head
            // as the list may not be empty
            temp->next = head;
            // point head to the first node now
            head = temp;
            return;
        }
        else
        {
            // 2. traverse to the desired position
            // or till the list ends; whichever comes first
            Node *t = head;
            // remember, we already covered the 1st case
            int currPos = 2;
            while ((currPos < position) && (t->next != NULL))
            {
                t = t->next;
                currPos++;
            }
            // 3. now we are at the desired location
            // 3.a. first set the pointer for the new node
            temp->next = t->next;
            // 3.b. now set the previous node pointer
            t->next = temp;
        }
    }

    //delete a node at a specific location
    int DeleteNodeFromLinkedList(int position)
    {
        // if the list is empty, return 0
        if (head == NULL)
            return 0;
        // special case: deleting first element
        if (position == 1)
        {
            // set the head to point to the node
            // that head is pointing to
            head = head->next;
        }
        else
        {
            // deleting at any other position
            // traverse to the desired position
            // or till the list ends; whichever comes first
            Node *t = head;
            // remember, we already covered the 1st case
            int currPos = 2;
            while ((currPos < position) && (t->next != NULL))
            {
                t = t->next;
                currPos++;
            }

            // now comes the tricky part
            // you have to point the current node to its next node
            if (t->next != NULL)
                t->next = t->next->next; // NOTE THIS
            else
                return 0; // could not find the correct node
        }
        // deletion successful
        return 1;
    }

    // Sort Link List
    void Sort()
    {
        // traverse the entire list
        for (Node *list = head; list->next != NULL; list = list->next)
        {
            // compare to the list ahead
            for (Node *pass = list->next; pass != NULL; pass = pass->next)
            {
                // compare and swap
                if (list->data > pass->data)
                {
                    // swap
                    int temp = list->data;
                    list->data = pass->data;
                    pass->data = temp;
                }
            }
        }
    }

    //Search an element in linked list
    Node* Find(int value)
{
    // start at the root
    Node *currentNode = head;

    // loop through the entire list
    while (currentNode != NULL)
    {
        // if we have a match
        if (currentNode->data == value)
            return currentNode;
        else // move to the next element
            currentNode = currentNode->next;
    }
}

    // Find maximum and minimum in a linked list
    int MaxMinInList(int *max, int *min)
    {
        // start at the root
        Node *currentNode = head;
        if (currentNode == NULL)
            return 0; // list is empty

        // initialize the max and min values to the first node
        *max = *min = currentNode->data;

        // loop through the list
        for (currentNode = currentNode->next; currentNode != NULL; currentNode = currentNode->next)
        {
            if (currentNode->data > *max)
            *max = currentNode->data;
            else if (currentNode->data < *min)
            *min = currentNode->data;
        }

        // we found our answer
        return 1;
    }

    // Reverse Linked List
    void Reverse()
    {
        // Initialize currentNode pointer to the start of the list
        // and prevNode to NULL
        // (as the new list is currently pointing to NULL).
        Node *currentNode = head;
        Node *prevNode = NULL;
        Node *nextNode = NULL;
        while (currentNode != NULL)
        {
            // Save the next node in nextNode
            nextNode = currentNode->next;
            // Set the currentNode to point to the prevNode.
            currentNode->next = prevNode;
            // Move the prevNode to the currentNode.
            prevNode = currentNode;
            // Move the currentNode pointer to the nextNode.
            currentNode = nextNode;
        }
        // reset the head pointer to point to the prevNode
        // as that is now the current head of the reversed list
        head = prevNode;
    }

    // Find List List is circular or not
    bool findCircular(Node *head)
    {
        Node *slower, * faster;
        slower = head;
        faster = head;
        while(true) {

            // if the faster pointer encounters a NULL element
            if( !faster || !faster->next)
                return false;
                //if faster pointer ever equals slower or faster's next
                //pointer is ever equal to slow then it's a circular list
            else if (faster == slower || faster->next == slower)
                return true;
            else{
                // advance the pointers
                slower = slower->next;
                faster = faster->next->next;
            }
        }
    }
}
