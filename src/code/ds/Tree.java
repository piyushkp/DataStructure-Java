package code.ds;
import com.sun.org.apache.bcel.internal.generic.SWAP;

import java.util.*;
/**
 * Created by Piyush Patel.
 */
public class Tree {
    public class Node
    {
        public int data;
        public Node left;
        public Node right;
        public Node parent;
    }
    public Node root;
    // insert node in BST
    public void insert(Node root, int node)
    {
        if (root == null)
        {
            Node newNode = new Node();
            newNode.data = node;
            root = newNode;
        }
        if (node < root.data)
        {
            insert(root.left, node);
        }
        else if (node > root.data)
        {
            insert(root.right, node);
        }
    }
    // Insert node into tree Iterative
    public void insertItr(Node root, int data)
    {
        Node newNode = new Node();
        newNode.data = data;
        if (root == null)
        {
            root.data = data;
        }
        else
        {
            Node current = root;
            while (true)
            {
                if (data < current.data)
                {
                    current = current.left;
                    if (current == null)
                    {
                        current.left = newNode;
                        return;
                    }
                }
                else
                {
                    current = current.right;
                    if (current == null)
                    {
                        current.right = newNode;
                        return;
                    }
                }
            }
        }
    }
    // recursive search in BST
    public Node search(Node root, int data)
    {
        if (root == null)
            return null;
        if (root.data == data)
            return root;
        if (data < root.data)
            search(root.left, data);
        else if (data > root.data)
            search(root.right, data);
        return null;
    }
    // DFS : Implement a depth first search algorithm recursively.
    private Boolean DFS(Node root, int target)
    {
        if (root == null)
            return false;
        if (root.data == target)
            return true;
        return DFS(root.left, target) || DFS(root.right, target);
    }
    //DFS: Iterative
    private Boolean DFSIterative(Node root, int target) {
        if (root == null)
            return false;
        Stack<Node> _stack = new Stack<Node>();
        _stack.push(root);
        while (_stack.size() > 0) {
            Node temp = _stack.peek();
            if (temp.data == target)
                return true;
            if (temp.left != null)
                _stack.push(temp.left);
            else if (temp.right != null)
                _stack.push(temp.right);
            else
                _stack.pop();
        }
        return false;
    }
    // BFS : Implement a breadth first search algorithm.
    // Level order traversal
    private Boolean BFS(Node root, int target)
    {
        if (root == null)
            return false;
        Queue<Node> _queue = null;
        Node tmp = null;
        _queue.add(root);

        while (_queue.size() > 0)
        {
            tmp = _queue.remove();
            if (tmp.data == target)
                return true;
            if (tmp.left != null)
                _queue.add(tmp.left);
            if (tmp.right != null)
                _queue.add(tmp.right);
        }
        return false;
    }
    //Print binary tree level by level in new line
    // Level order search
    public static void printLevels(Node tree) {
        Queue<Node> queue = new java.util.LinkedList<Node>();
        Queue<Integer> levels = new java.util.LinkedList<Integer>();
        queue.add(tree);
        levels.add(0);
        int lastLevel= 0;
        while (queue.size() > 0) {
            Node node = queue.remove();
            int level = levels.remove();
            if(level!=lastLevel){
                System.out.println();
                lastLevel = level;
            }
            System.out.print(node.data + " ");
            if(node.left!=null){
                queue.add(node.left);
                levels.add(level+1);
            }
            if(node.right !=null ){
                queue.add(node.right);
                levels.add(level+1);
            }
        }
    }
    /* Given a binary tree, print its nodes in reverse level order */
    void reverseLevelOrder(Node root)
    {
        Stack<Node> S = new Stack<Node>();
        Queue<Node> Q = null;
        Q.add(root);
        // Do something like normal level order traversal order. Following are the
        // differences with normal level order traversal
        // 1) Instead of printing a node, we push the node to stack
        // 2) Right subtree is visited before left subtree
        while (Q.size() > 0)
        {
            root = Q.peek();
            Q.poll();
            S.push(root);
            if (root.right != null)
                Q.add(root.right); // NOTE: RIGHT CHILD IS ENQUEUED BEFORE LEFT
            if (root.left != null)
                Q.add(root.left);
        }
        // Now pop all items from stack one by one and print them
        while (S.size() > 0)
        {
            root = S.peek();
            System.out.print(root.data);
            S.pop();
        }
    }
    // Inorder traversal of BST
    public void inOrder(Node root)
    {
        if (root != null)
        {
            inOrder(root.left);
            System.out.print(root.data);
            inOrder(root.right);
        }
    }
    // Preorder traversal of BST
    public void preOrder(Node root)
    {
        if (root != null)
        {
            System.out.print(root.data);
            preOrder(root.left);
            preOrder(root.right);
        }
    }
    // An iterative process to print preorder traversal of Binary tree
    void iterativePreorder(Node root)
    {
        // Base Case
        if (root == null)
            return;
        Stack<Node> nodeStack = new Stack<Node>();
        nodeStack.push(root);

            /* Pop all items one by one. Do following for every popped item
               a) print it
               b) push its right child
               c) push its left child
            Note that right child is pushed first so that left is processed first */
        while (nodeStack.size() > 0)
        {
            // Pop the top item from stack and print it
            Node node = nodeStack.peek();
            System.out.print(node.data);
            nodeStack.pop();
            if (node.right != null)
                nodeStack.push(node.right);
            if (node.left != null)
                nodeStack.push(node.left);
        }
    }
    // Postorder traversal of BST
    public void postOrder(Node root)
    {
        if (root != null)
        {
            postOrder(root.left);
            postOrder(root.right);
            System.out.print(root.data);
        }
    }
    // An iterative function to do postorder traversal of a given binary tree
    public ArrayList<Integer> postorderTraversal(Node root)
    {
        ArrayList<Integer> res = new ArrayList<Integer>();
        Stack<Node> stack = new Stack<Node>();
        Node cur = root;
        while (cur != null || !stack.empty()) {
            if (cur != null) {
                res.add(cur.data);
                stack.push(cur);
                cur = cur.right;
            }
            else {
                cur = stack.pop();
                cur = cur.left;
            }
        }
        Collections.reverse(res);
        return res;
    }
    // Delete node from tree
    public void delete(int key)
    {
        Node parent = null;

        Node nodetoDelete = FindParent(key, parent);

        if (nodetoDelete.left == null && nodetoDelete.right == null)
        {
            if (parent != null)
            {
                if (parent.left == nodetoDelete)
                    parent.left = null;
                else
                    parent.right = null;
            }
            else
                root = null;
        }
        else if (nodetoDelete.left == null)
        {
            if (parent != null)
            {
                if (parent.left == nodetoDelete)
                {
                    parent.left = nodetoDelete.right;
                }
                else
                {
                    parent.right = nodetoDelete.right;
                }
            }
            else
                root = nodetoDelete.right;
        }
        else if (nodetoDelete.right == null)
        {
            if (parent != null)
            {
                if (parent.left == nodetoDelete)
                {
                    parent.left = nodetoDelete.left;
                }
                else
                {
                    parent.right = nodetoDelete.left;
                }
            }
            else
                root = nodetoDelete.left;
        }
        else {
            Node successor = FindSuccessor(nodetoDelete, parent);
            parent.left = successor.right;
            nodetoDelete = successor;
        }
    }
    private Node FindParent(int key, Node Parent)
    {
        Node node = root;
        while (node != null)
        {
            if (node.data == key)
                return node;
            if (key < node.data)
            {
                Parent = node;
                node = node.left;
            }
            else
            {
                Parent = node;
                node = node.right;
            }
        }
        return null;
    }
    private Node FindSuccessor(Node startNode, Node parent)
    {
        parent = startNode;
        startNode = startNode.right;
        while (startNode.left != null)
        {
            parent = startNode;
            startNode = startNode.right;
        }
        return startNode;
    }
    //In a binary search tree, find the lowest common ancestor.
    private Node FindLCA(Node root, Node one, Node two)
    {
        while (root != null)
        {
            if (root.data < one.data && root.data < two.data)
                return root.right;
            else if (root.data > one.data && root.data > two.data)
                return root.left;
            else
                return root;
        }
        return null;
    }
    // Find LCA of Binary Tree.
    //The run time complexity is O(h), where h is the tree’s height. The space complexity is also O(h)
    public Node FindLCA_BTree(Node root, Node one, Node two)
    {
        HashSet<Node> hash = new HashSet<Node>();
        while (one != null || two != null)
        {
            if (one != null)
            {
                if (hash.contains(one))
                    return one;
                else
                    hash.add(one);
                one = one.parent;
            }
            if (two != null)
            {
                if (hash.contains(two))
                    return two;
                else
                    hash.add(two);
                two = two.parent;
            }
        }
        return null;
    }
    // Find LCA best way no Space Required
    public Node FindLCA_Best(Node root, Node one, Node two)
    {
        int h1 = getHeight(one);
        int h2 = getHeight(two);
        //swap elements
        if (h1 > h2)
        {
            //swapping h1 and h2 using XOR gate
            h1 ^= h2;
            h2 ^= h1;
            h1 ^= h2;
            swap(one.data, two.data);
        }
        int dh = h2 - h1;
        for (int i = 0; i < dh; i++)
        {
            two = two.parent;
        }
        while (one != null && two != null)
        {
            if (one.data == two.data)
                return one;
            one = one.parent;
            two = two.parent;
        }
        return null;
    }
    public int getHeight(Node node)
    {
        int height = 0;
        while (node != null)
        {
            height++;
            node = node.parent;
        }
        return height;
    }
    //Find LCA without parent pointer
    static Node FindLowestCommonAncestor(Node n, int n1, int n2)
    {
        if (n == null) return null;
        if (n.data == n1 || n.data == n2) return n;
        Node left = FindLowestCommonAncestor(n.left, n1, n2);
        Node right = FindLowestCommonAncestor(n.right, n1, n2);
        if (left == null && right == null) return null;
        if (left != null && right != null) return n;
        if (left != null) return left;
        if (right != null) return right;
        return null;
    }
    // Find the Diameter of Binary Tree
    public int diameterOfBinaryTree(Node node)
    {
        if (node == null)
            return 0;

        int leftHeight = heightOfBinaryTree(node.left);
        int rightHeight = heightOfBinaryTree(node.right);

        int leftDiameter = diameterOfBinaryTree(node.left);
        int rightDiameter = diameterOfBinaryTree(node.right);

        return Math.max(leftHeight + rightHeight + 1, Math.max(leftDiameter, rightDiameter));
    }
    public int heightOfBinaryTree(Node node)
    {
        if (node == null)
            return 0;
        else
        {
            return 1 + Math.max(heightOfBinaryTree(node.left), heightOfBinaryTree(node.right));
        }
    }
    // Convert sorted array into balanced tree
    private Node sortedArraytoBST(int[] arr, int start, int end)
    {
        if (end > start)
            return null;
        int mid = (start + end) / 2;
        Node tree = new Node();
        tree.data = arr[mid];
        tree.left = sortedArraytoBST(arr, start, mid - 1);
        tree.right = sortedArraytoBST(arr, mid + 1, end);
        return tree;
    }
    // Find out if given tree is Binary Search Tree or not
    private Boolean IsBSTUtil(Node root, int minValue, int maxValue)
    {
        if(root == null)
            return  true;
        if( root.data < minValue
            && root.data > maxValue
            && IsBSTUtil(root.left, minValue, root.data)
            && IsBSTUtil(root.right, root.data, maxValue))
            return  true;
        else
            return  false;
    }
    Node prev;
    private Boolean isBST(Node root)
    {
        // do inorder traversal and check it is sorted or not
        if (root != null)
        {
            if (!isBST(root.left))
                return false;
            if (prev != null && root.data <= prev.data)
                return false;
            prev = root;

            return isBST(root.right);
        }
        return true;
    }
    // Find the Kth smallest element from the BST
    Node temp;
    public Node getNthMin(int target)
    {
        if (target == 1)
            return getMin();
        else
        {
            int counter = 1;
            temp = getMin();
            while (true)
            {
                temp = temp.parent;
                counter++;
                if (counter == target)
                    return temp;
                if (temp.right != null)
                    counter++;
                if (counter == target)
                    return temp.right;
            }
        }
    }
    public Node getMin()
    {
        temp = root;
        while (true)
        {
            if (temp.left == null)
                return temp;
            else
                temp = root.left;
        }
    }
    //given a binary search tree and you are asked to find the Kth smallest element in that tree.
    private Node findKthNode(Node root, int k)
    {
        if(root == null)
            return null;
        int leftSize = findLeftTreeSize(root.left);
        if(leftSize == k - 1)
            return root;
        else if (leftSize < k - 1)
            findKthNode(root.left, k);
        else
            findKthNode(root.right, k-leftSize-1);
        return null;
    }
    private int findLeftTreeSize(Node root){
        return 1+ findLeftTreeSize(root.left) + findLeftTreeSize(root.right);
    }
    /* A O(n) iterative program for construction of BST from preorder traversal */
    public Node constuctTreeFromPreOrder(int[] preOrder)
    {
        Node root = new Node();
        root.data = preOrder[0];
        Stack<Node> _stack = new Stack<Node>();
        Node temp;
        for (int i = 1; i < preOrder.length; i++)
        {
            temp = null;
            while (_stack.size() > 0 && preOrder[i] > _stack.peek().data)
            {
                temp = _stack.pop();
            }
            if (temp != null)
            {
                temp.right.data = preOrder[i];
                _stack.push(temp.right);
            }
            else
            {
                temp.left.data = preOrder[i];
                _stack.push(temp.left);
            }
        }
        return root;
    }
    //Two nodes of a BST are swapped, correct the BST
    public void CorrectBST(Node root)
    {
        Node first = null, middle = null, last = null, prev = null;
        CorrectBSTUtil(root, first, middle, last, prev);
        if (first != null && last != null)
            swap(first.data, last.data);
        else if (first != null && middle != null)
            swap(first.data, middle.data);
    }
    public void CorrectBSTUtil(Node root, Node first, Node middle, Node last, Node prev)
    {
        if (root != null)
        {
            CorrectBSTUtil(root.left, first, middle, last, prev);
            if (prev != null && root.data < prev.data)
            {
                if (first == null)
                {
                    first = prev;
                    middle = root;
                }
                else
                {
                    last = root;
                }
            }
            prev = root;
            CorrectBSTUtil(root.right, first, middle, last, prev);
        }
    }
    public void swap(int a, int b)
    {
        int t = a;
        a = b;
        b = t;
    }
    //Binary Tree Maximum Path Sum
    public int maxPathSum(Node root)
    {
        if (root == null)
        {
            return 0;
        }
        int maxLeft = maxPathSum(root.left); //max length in the whole left subtree
        int maxRight = maxPathSum(root.right); //max length in the whole right subtree
        int leftLen = 0; //max length in the left subtree starting with left child
        int rightLen = 0; //max length in the left subtree starting with left child
        if (root.left != null)
        {
            leftLen = Math.max(root.left.data, 0);
        }
        if (root.right != null)
        {
            rightLen = Math.max(root.right.data, 0);
        }
        int maxLength = root.data;
        if (leftLen > 0)
        {
            maxLength += leftLen;
        }
        if (rightLen > 0)
        {
            maxLength += rightLen;
        }
        if (root.left != null)
        {
            maxLength = Math.max(maxLeft, maxLength);
        }
        if (root.right != null)
        {
            maxLength = Math.max(maxRight, maxLength);
        }
        //root.val is replaced with the maximum length starting from root downwards
        root.data = Math.max(leftLen, rightLen) + root.data;
        return maxLength;
    }
    //Given a binary tree, every node has a int value, return the root node of subtree with the largest sum up value.
    private Node maxSumSubtree(Node root)
    {
        if (root == null) return null;

        int maxsum = 0;
        Node res = null;
        helper(root, res, maxsum);
        return res;
    }
    int helper(Node p, Node res, int maxsum)
    {
        if (p == null) return 0;
        int lsum = helper(p.left, res, maxsum);
        int rsum = helper(p.right, res, maxsum);
        int total = lsum + rsum + p.data;
        if (total > maxsum)
        {
            maxsum = total;
            res = p;
        }
        return total;
    }
    //Find the maximum sum of the subtree (triangle) from the given tree.
    private int FindMaxSumSubtree(Node root)
    {
        int max_sum = 0;
        max_sum = MaxSumSubtree(root, max_sum);
        return max_sum;
    }
    private int MaxSumSubtree(Node root, int max_sum)
    {
        int sum = 0;
        int lsum = 0;
        int rsum = 0;
        if (root == null)
            return 0;
        if (root.left != null)
            lsum = MaxSumSubtree(root.left, max_sum);
        if (root.right != null)
            rsum = MaxSumSubtree(root.right, max_sum);
        sum = root.data + lsum + rsum;
        if (max_sum < sum)
            max_sum = sum;
        return max_sum;
    }
    //Print Right View of a Binary Tree
    public void rightView()
    {
        int max_level = 0;
        rightViewUtil(root, 1, max_level);
    }
    public void rightViewUtil(Node root, int level, int max_level)
    {
        if (root == null) return;
        if (max_level < level)
        {
            System.out.print(root.data);
            max_level = level;
        }
        rightViewUtil(root.right, level + 1, max_level);
        rightViewUtil(root.left, level + 1, max_level);
    }
    // Given a Binary Tree mirror it with left and right subtree
    private Node mirrorTree(Node root)
    {
        Node newNode = new Node();
        if(root == null)
            return null;
        else {
            newNode.data = root.data;
            newNode.left = mirrorTree(root.right);
            newNode.right = mirrorTree(root.left);
        }
        return  newNode;
    }
    private Node mirrorTreeIterative(Node root)
    {
        Node newNode = new Node();
        if(root == null)
            return null;
        Stack<Node> _stack = new Stack<Node>();
        _stack.push(root);
        while(!_stack.empty())
        {
            newNode = _stack.pop();
            //SWAP(newNode.left, newNode.right);
            if(newNode.left != null)
                _stack.push(newNode.left);
            if(newNode.right != null)
                _stack.push(newNode.right);
        }
        return  newNode;
    }
    //Given the root of a binary search tree and 2 numbers min and max,
   // trim the tree such that all the numbers in the new tree are between min and max (inclusive).
   private Node trimBST(Node root, int minValue, int maxValue)
   {
       if(root == null)
           return null;
       root.left = trimBST(root.left, minValue, maxValue);
       root.right = trimBST(root.right, minValue, maxValue);
       if(minValue <= root.data && root.data <= maxValue)
           return root;
       if(root.data < minValue)
           return  root.right;
       if(root.data > maxValue)
           return root.left;
       return  null;
   }
   // Given a Binary Search Tree and a value, find the closest element to the given value in BST.
    private void closest_element(Node root, int value, int minValue)
    {
        if(root == null)
            return;
        int diff = Math.abs(root.data - value);
        if(minValue > diff)
            minValue = diff;
        if(root.data > value)
            closest_element(root.left, value, minValue);
        else
            closest_element(root.right, value, minValue);
    }
    //Given a binary search tree, sum all the nodes which are at on the same vertical line.
    int HD_OFFSET = 16;
    private void verticalSUM(Node root, int[] sum, int hd, int min, int max)
    {
        int index = hd + HD_OFFSET /2;
        if(index < min) min = index;
        if(index > max) max = index;
        sum[index] += root.data;
        verticalSUM(root.left,sum, hd-1,min,max);
        verticalSUM(root.right, sum, hd+1, min, max);
    }
    //Given a binary tree where all the right nodes are leaf nodes,
    // flip it upside down and turn it into a tree with left leaf nodes.
    Node FlipTree ( Node root )
    {
        if (root == null)
            return null;
        // Working base condition
        if( root.left == null && root.right == null)
        {
            return root.left;
        }
        Node newRoot = FlipTree(root.left);
        root.left.left = root.right;
        root.left.right = root;
        root.left = null;
        root.right = null;
        return newRoot;
    }
    // Build max-heap
    public static void BuildMaxHeap( int[ ] arr )
    {
        for( int i = arr.length - 1 ; i >= 0; i-- )
            MaxHeapify( arr, i );
    }
    public static void MaxHeapify( int[ ] arr, int i )
    {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int largest = i;
        if( left < arr.length && arr[ left ] > arr[ largest ] )
            largest = left;
        if( right < arr.length && arr[ right ] > arr[ largest ] )
            largest = right;
        if( largest != i )
        {
            int temp = arr[ i ];
            arr[ i ] = arr[ largest ];
            arr[ largest ] = temp;
            MaxHeapify( arr, largest );
        }
    }
    //Check if tree is a balanced tree.
    public static boolean isTreeBalanced(Node rootNode) {
        int maxDepth = maxDepth(rootNode);
        int minDepth = minDepth(rootNode);
        System.out.println("max depth " + maxDepth);
        System.out.println("min depth " + minDepth);
        return maxDepth - minDepth <= 1;
    }
    private static int maxDepth(Node currentNode) {
        if (currentNode == null) {
            return 0;
        }
        int maxLeft = maxDepth(currentNode.left);
        int maxRight = maxDepth(currentNode.right);
        int max = Math.max(maxLeft, maxRight);
        return 1 + max;
    }
    private static int minDepth(Node currentNode) {
        if (currentNode == null) {
            return 0;
        }
        int minLeft = minDepth(currentNode.left);
        int minRight = minDepth( currentNode.right);
        int min = Math.min(minLeft, minRight);
        return 1 + min;
    }
}

