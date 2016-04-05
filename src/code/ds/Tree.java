package code.ds;
import com.sun.org.apache.bcel.internal.generic.SWAP;

import java.util.*;
/**
 * Created by Piyush Patel.
 */
public class Tree {
    public class Node {
        public int data;
        public Node left;
        public Node right;
        public Node parent;
        Node(int data)
        {data = data;}
        Node(){}
    }
    public Node root;
    // insert node in BST
    public void insert(Node root, int node) {
        if (root == null) {
            Node newNode = new Node();
            newNode.data = node;
            root = newNode;
        }
        if (node < root.data) {
            insert(root.left, node);
        } else if (node > root.data) {
            insert(root.right, node);
        }
    }
    // Insert node into tree Iterative
    public void insertItr(Node root, int data) {
        Node newNode = new Node();
        newNode.data = data;
        if (root == null) {
            root.data = data;
        } else {
            Node current = root;
            while (true) {
                if (data < current.data) {
                    current = current.left;
                    if (current == null) {
                        current.left = newNode;
                        return;
                    }
                } else {
                    current = current.right;
                    if (current == null) {
                        current.right = newNode;
                        return;
                    }
                }
            }
        }
    }
    // recursive search in BST
    public Node search(Node root, int data) {
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
    private Boolean DFS(Node root, int target) {
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
    // BFS : Implement a breadth first search algorithm. Level order traversal
    private Boolean BFS(Node root, int target) {
        if (root == null)
            return false;
        Queue<Node> _queue = null;
        Node tmp = null;
        _queue.add(root);
        while (_queue.size() > 0) {
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
    public static void printLevels(Node root) {
        if (root == null)  return;
        // Create an empty queue for level order tarversal
        Queue<Node> q = null ;
        // Enqueue Root and initialize height
        q.add(root);
        while (true){
            // nodeCount (queue size) indicates number of nodes at current level.
            int nodeCount = q.size();
            if (nodeCount == 0)
                break;
            // Dequeue all nodes of current level and Enqueue all nodes of next level
            while (nodeCount > 0){
                Node node = q.peek();
                System.out.print(node.data);
                q.remove();
                if (node.left != null)
                    q.add(node.left);
                if (node.right != null)
                    q.add(node.right);
                nodeCount--;
            }
            System.out.println();
        }
    }
    /* Given a binary tree, print its nodes in reverse level order */
    void reverseLevelOrder(Node root) {
        Stack<Node> S = new Stack<Node>();
        Queue<Node> Q = null;
        Q.add(root);
        // Do something like normal level order traversal order. Following are the
        // differences with normal level order traversal
        // 1) Instead of printing a node, we push the node to stack
        // 2) Right subtree is visited before left subtree
        while (Q.size() > 0) {
            root = Q.peek();
            Q.poll();
            S.push(root);
            if (root.right != null)
                Q.add(root.right); // NOTE: RIGHT CHILD IS ENQUEUED BEFORE LEFT
            if (root.left != null)
                Q.add(root.left);
        }
        // Now pop all items from stack one by one and print them
        while (S.size() > 0) {
            root = S.peek();
            System.out.print(root.data);
            S.pop();
        }
    }
    //Given a binary tree , print it’s nodes in spiral fashion. Example for Given Tree,Output should be F,B,G,I,D,A,C,E,H
    private void spiralLevelOrderTraversal(Node root) {
        if (root == null)
            return;
        Stack<Node> _stack1 = new Stack<Node>();
        Stack<Node> _stack2 = new Stack<Node>();
        _stack1.push(root);
        while (!_stack1.empty() || !_stack2.empty()) {
            while (!_stack1.empty()) {
                Node node = _stack1.pop();
                System.out.print(node.data);
                if (node.right != null) _stack2.add(node.right);
                if (node.left != null) _stack2.add(node.left);
            }
            while (!_stack2.isEmpty()) {
                Node node = _stack2.pop();
                System.out.print(node.data);
                if (node.left != null) _stack1.add(node.left);
                if (node.right != null) _stack1.add(node.right);
            }
        }
    }
    // Inorder traversal of BST
    public void inOrder(Node root) {
        if (root != null) {
            inOrder(root.left);
            System.out.print(root.data);
            inOrder(root.right);
        }
    }
    // Preorder traversal of BST
    public void preOrder(Node root) {
        if (root != null) {
            System.out.print(root.data);
            preOrder(root.left);
            preOrder(root.right);
        }
    }
    // an iterative inOrder traversal
    public void inorder(Node root ) {
        Node node = root;
        Stack<Node> stack = new Stack<Node>();
        while (!stack.isEmpty() || node != null) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                System.out.print(node.data + " ");
                node = node.right;
            }
        }
    }
    // An iterative process to print preorder traversal of Binary tree
    void iterativePreorder(Node root) {
        if (root == null)
            return;
        Stack<Node> nodeStack = new Stack<Node>();
        nodeStack.push(root);
            /* Pop all items one by one. Do following for every popped item
               a) print it
               b) push its right child
               c) push its left child
            Note that right child is pushed first so that left is processed first */
        while (nodeStack.size() > 0) {
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
    public void postOrder(Node root) {
        if (root != null) {
            postOrder(root.left);
            postOrder(root.right);
            System.out.print(root.data);
        }
    }
    // An iterative function to do postorder traversal of a given binary tree
    // Post order with two stack
    /*  1. Push root to first stack.
        2. Loop while first stack is not empty
            2.1 Pop a node from first stack and push it to second stack
            2.2 Push left and right children of the popped node to first stack
        3. Print contents of second stack*/
    // below is solution with one stack
    public void postOrderIterative(Node node) {
        Stack<Node> S = new Stack<Node>();
        // Check for empty tree
        if (node == null) {
            return;
        }
        S.push(node);
        Node prev = null;
        while (!S.isEmpty()) {
            Node current = S.peek();
            /* go down the tree in search of a leaf an if so process it and pop sack otherwise move down */
            if (prev == null || prev.left == current || prev.right == current) {
                if (current.left != null) {
                    S.push(current.left);
                } else if (current.right != null) {
                    S.push(current.right);
                } else {
                    S.pop();
                    System.out.print(current.data);
                }
                /* go up the tree from left node, if the child is right
                push it onto stack otherwise process parent and pop stack */
            } else if (current.left == prev) {
                if (current.right != null) {
                    S.push(current.right);
                } else {
                    S.pop();
                    System.out.print(current.data);
                }
                /* go up the tree from right node and after coming back from right node process parent and pop stack */
            } else if (current.right == prev) {
                S.pop();
                System.out.print(current.data);
            }
            prev = current;
        }
    }
    // Delete node from binary tree
    public void delete(int key) {
        Node parent = null;
        Node nodetoDelete = null;
        if(root.data == key) {
            nodetoDelete = root;
        }
        else
          parent = getParent(root, key, nodetoDelete);
        if (nodetoDelete.left == null && nodetoDelete.right == null) {
            if (parent != null) {
                if (parent.left == nodetoDelete)
                    parent.left = null;
                else
                    parent.right = null;
            } else
                root = null;
        } else if (nodetoDelete.left == null) {
            if (parent != null) {
                if (parent.left == nodetoDelete) {
                    parent.left = nodetoDelete.right;
                } else {
                    parent.right = nodetoDelete.right;
                }
            } else
                root = nodetoDelete.right;
        } else if (nodetoDelete.right == null) {
            if (parent != null) {
                if (parent.left == nodetoDelete) {
                    parent.left = nodetoDelete.left;
                } else {
                    parent.right = nodetoDelete.left;
                }
            } else
                root = nodetoDelete.left;
        } else {
            Node successor = FinMinValue(nodetoDelete.right);
            if (parent != null) {
                if(parent.right == nodetoDelete)
                    parent.right = successor;
                else
                    parent.left = successor;
            }
            else
                root = successor;
        }
    }
    private Node getParent(Node root, int target, Node NodetoDelete) {
        if (root != null) {
            if (root.left != null) {
                if ((root.left.data == target)) {
                    NodetoDelete = root.left;
                    return root;
                }
            }
            if (root.right != null) {
                if ((root.right.data == target)) {
                    NodetoDelete = root.right;
                    return root;
                }
            }
            getParent(root.left, target, NodetoDelete);
            getParent(root.right, target, NodetoDelete);
        }
        return root;
    }
    private Node FinMinValue(Node startNode) {
        Node parent = null;
        while (startNode.left != null) {
            parent = startNode;
            startNode = startNode.left;
        }
        if(parent != null)
            parent.left = null;
        return startNode;
    }
    /* Given a binary search tree and a key, this function deletes the key and returns the new root */
    Node deleteRec(Node root, int key){
        /* Base Case: If the tree is empty */
        if (root == null)  return root;
        /* Otherwise, recur down the tree */
        if (key < root.data)
            root.left = deleteRec(root.left, key);
        else if (key > root.data)
            root.right = deleteRec(root.right, key);
        // if key is same as root's key, then This is the node to be deleted
        else{
            // node with only one child or no child
            if (root.left == null) {
                Node temp = root.right;
                return temp;
            }
            else if (root.right == null){
                Node temp = root.left;
                return temp;}
            // node with two children: Get the inorder successor (smallest
            // in the right subtree)
            root.data = minValuedata(root.right);
            // Delete the inorder successor
            root.right = deleteRec(root.right, root.data);
        }
        return root;
    }
    int minValuedata(Node root){
        int minv = root.data;
        while (root.left != null){
            minv = root.left.data;
            root = root.left;
        }
        return minv;
    }
    //In a binary search tree, find the lowest common ancestor.
    private Node FindLCA(Node root, Node one, Node two) {
        while (root != null) {
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
    public Node FindLCA_BTree(Node root, Node one, Node two) {
        HashSet<Node> hash = new HashSet<Node>();
        while (one != null || two != null) {
            if (one != null) {
                if (hash.contains(one))
                    return one;
                else
                    hash.add(one);
                one = one.parent;
            }
            if (two != null) {
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
    public Node FindLCA_Best(Node root, Node one, Node two) {
        int h1 = getHeight(one);
        int h2 = getHeight(two);
        //swap elements
        if (h1 > h2) {
            //swapping h1 and h2 using XOR gate
            h1 ^= h2;
            h2 ^= h1;
            h1 ^= h2;
            swap(one.data, two.data);
        }
        int dh = h2 - h1;
        for (int i = 0; i < dh; i++) {
            two = two.parent;
        }
        while (one != null && two != null) {
            if (one.data == two.data)
                return one;
            one = one.parent;
            two = two.parent;
        }
        return null;
    }
    public int getHeight(Node node) {
        int height = 0;
        while (node != null) {
            height++;
            node = node.parent;
        }
        return height;
    }
    //Find LCA without parent pointer
    static Node FindLowestCommonAncestor(Node n, int n1, int n2) {
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
    private class HeightWrapper {
        int height = 0;
    }
    private int getDiameter_helper(Node root, HeightWrapper wrapper) {
        if (root == null) {
            return 0; // diameter and height are 0
        }
    /* wrappers for heights of the left and right subtrees */
        HeightWrapper lhWrapper = new HeightWrapper();
        HeightWrapper rhWrapper = new HeightWrapper();
    /* get heights of left and right subtrees and their diameters */
        int leftDiameter = getDiameter_helper(root.left, lhWrapper);
        int rightDiameter = getDiameter_helper(root.right, rhWrapper);
    /* calculate root diameter */
        int rootDiameter = lhWrapper.height + rhWrapper.height + 1;
    /* calculate height of current node */
        wrapper.height = Math.max(lhWrapper.height, rhWrapper.height) + 1;
    /* calculate the diameter */
        return Math.max(rootDiameter, Math.max(leftDiameter, rightDiameter));
    }
    // Convert sorted array into balanced tree
    private Node sortedArraytoBST(int[] arr, int start, int end) {
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
    //Integer.MIN_VALUE, Integer.MAX_VALUE
    public static boolean validateBST(Node root, int min, int max) {
        if (root == null) {
            return true;
        }
        // not in range
        if (root.data <= min || root.data >= max) {
            return false;
        }
        // left subtree must be < root.val && right subtree must be > root.val
        return validateBST(root.left, min, root.data) && validateBST(root.right, root.data, max);
    }
    private Boolean isBST(Node root) {
        // do inorder traversal and check it is sorted or not
        if (root != null) {
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
    void find_kth_smallest(Node root, int n, int K){
        if(root == null) return;
        find_kth_smallest(root.left, n, K);
        n++;
        if(K == n){
            System.out.print(root.data);
            return;
        }
        find_kth_smallest(root.right, n, K);
    }
    //given a binary search tree and you are asked to find the Kth smallest element in that tree.
    private Node findKthNode(Node root, int k) {
        if (root == null)
            return null;
        int leftSize = findLeftTreeSize(root.left);
        if (leftSize == k - 1)
            return root;
        else if (leftSize < k - 1)
            findKthNode(root.left, k);
        else
            findKthNode(root.right, k - leftSize - 1);
        return null;
    }
    private int findLeftTreeSize(Node root) {
        if(root == null)
            return 0;
        else
            return 1 + findLeftTreeSize(root.left) + findLeftTreeSize(root.right);
    }
    /* A O(n) iterative program for construction of BST from preorder traversal */
    int[] currIndex = new int[1];
    currIndex[0] = 0;
    int min  = Integer.MIN_VALUE;
    int max  = Integer.MAX_VALUE;
    private Node deserializeArrayOptimized(int[] preorder, int[] currIndex, int min, int max){
        if (currIndex[0] >= preorder.length) return null;
        Node root = null;
        if ((preorder[currIndex[0]] > min) && (preorder[currIndex[0]] < max)){
            root = new Node(preorder[currIndex[0]]);
            currIndex[0] += 1;
            root.left = deserializeArrayOptimized(preorder, currIndex, min, root.data);
            root.right = deserializeArrayOptimized(preorder, currIndex, root.data, max);
        }
        return root;
    }
    //Two nodes of a BST are swapped, correct the BST
    public void CorrectBST(Node root) {
        Node first = null, middle = null, last = null, prev = null;
        CorrectBSTUtil(root, first, middle, last, prev);
        if (first != null && last != null)
            swap(first.data, last.data);
        else if (first != null && middle != null)
            swap(first.data, middle.data);
    }
    public void CorrectBSTUtil(Node root, Node first, Node middle, Node last, Node prev) {
        if (root != null) {
            CorrectBSTUtil(root.left, first, middle, last, prev);
            if (prev != null && root.data < prev.data) {
                if (first == null) {
                    first = prev;
                    middle = root;
                } else {
                    last = root;
                }
            }
            prev = root;
            CorrectBSTUtil(root.right, first, middle, last, prev);
        }
    }
    public void swap(int a, int b) {
        int t = a;
        a = b;
        b = t;
    }
    //Binary Tree Maximum Path Sum
    public int maxPathSum(Node root) {
        if (root == null) {
            return 0;
        }
        int maxLeft = maxPathSum(root.left); //max length in the whole left subtree
        int maxRight = maxPathSum(root.right); //max length in the whole right subtree
        int leftLen = 0; //max length in the left subtree starting with left child
        int rightLen = 0; //max length in the left subtree starting with left child
        if (root.left != null) {
            leftLen = Math.max(root.left.data, 0);
        }
        if (root.right != null) {
            rightLen = Math.max(root.right.data, 0);
        }
        int maxLength = root.data;
        if (leftLen > 0) {
            maxLength += leftLen;
        }
        if (rightLen > 0) {
            maxLength += rightLen;
        }
        if (root.left != null) {
            maxLength = Math.max(maxLeft, maxLength);
        }
        if (root.right != null) {
            maxLength = Math.max(maxRight, maxLength);
        }
        //root.val is replaced with the maximum length starting from root downwards
        root.data = Math.max(leftLen, rightLen) + root.data;
        return maxLength;
    }
    //Given a binary tree, every node has a int value, return the root node of subtree with the largest sum up value.
    private Node maxSumSubtree(Node root) {
        if (root == null) return null;

        int maxsum = 0;
        Node res = null;
        helper(root, res, maxsum);
        return res;
    }
    int helper(Node p, Node res, int maxsum) {
        if (p == null) return 0;
        int lsum = helper(p.left, res, maxsum);
        int rsum = helper(p.right, res, maxsum);
        int total = lsum + rsum + p.data;
        if (total > maxsum) {
            maxsum = total;
            res = p;
        }
        return total;
    }
    //Find the maximum sum of the subtree (triangle) from the given tree.
    private int FindMaxSumSubtree(Node root) {
        int max_sum = 0;
        max_sum = MaxSumSubtree(root, max_sum);
        return max_sum;
    }
    private int MaxSumSubtree(Node root, int max_sum) {
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
    public void rightView() {
        int max_level = 0;
        rightViewUtil(root, 1, max_level);
    }
    public void rightViewUtil(Node root, int level, int max_level) {
        if (root == null) return;
        if (max_level < level) {
            System.out.print(root.data);
            max_level = level;
        }
        rightViewUtil(root.right, level + 1, max_level);
        rightViewUtil(root.left, level + 1, max_level);
    }
    // Given a Binary Tree mirror it with left and right subtree
    //modify the existing binary tree
    void mirror(Node node) {
        if (node == null) {
            return;
        } else {
            Node temp;
             /* swap the objects/values in this node */
            temp = node.left;
            node.left = node.right;
            node.right = temp;
            /* do the subtrees */
            mirror(node.left);
            mirror(node.right);

        }
    }
    //with new tree
    private Node mirrorTree(Node root) {
        Node newNode = new Node();
        if (root == null)
            return null;
        else {
            newNode.data = root.data;
            newNode.left = mirrorTree(root.right);
            newNode.right = mirrorTree(root.left);
        }
        return newNode;
    }
    private Node mirrorTreeIterative(Node root) {
        Node newNode = new Node();
        if (root == null)
            return null;
        Stack<Node> _stack = new Stack<Node>();
        _stack.push(root);
        while (!_stack.empty()) {
            newNode = _stack.pop();
            //SWAP(newNode.left, newNode.right);
            if (newNode.left != null)
                _stack.push(newNode.left);
            if (newNode.right != null)
                _stack.push(newNode.right);
        }
        return newNode;
    }
    //Given a binary tree, check whether it is a mirror of itself.
    boolean isMirror(Node node1, Node node2) {
        // if both trees are empty, then they are mirror image
        if (node1 == null && node2 == null) {
            return true;
        }
        // For two trees to be mirror images, the following three conditions must be true
        // 1 - Their root node's key must be same
        // 2 - left subtree of left tree and right subtree of right tree have to be mirror images
        // 3 - right subtree of left tree and left subtree of right tree have to be mirror images
        if (node1 != null && node2 != null && node1.data == node2.data) {
            return (isMirror(node1.left, node2.right)
                    && isMirror(node1.right, node2.left));
        }
        // if neither of the above conditions is true then root1 and root2 are mirror images
        return false;
    }
    //Given the root of a binary search tree and 2 numbers min and max,
    //trim the tree such that all the numbers in the new tree are between min and max (inclusive).
    private Node trimBST(Node root, int minValue, int maxValue) {
        if (root == null)
            return null;
        root.left = trimBST(root.left, minValue, maxValue);
        root.right = trimBST(root.right, minValue, maxValue);
        if (minValue <= root.data && root.data <= maxValue)
            return root;
        if (root.data < minValue)
            return root.right;
        if (root.data > maxValue)
            return root.left;
        return null;
    }
    // Given a Binary Search Tree and a value, find the closest element to the given value in BST.
     int min_diff(int a, int b,int key) {
        if (Math.abs(a - key) <= Math.abs(b - key))
            return a;
        else
            return b;
    }
    int searchClosest(Node root, int key) {
        int close = Integer.MAX_VALUE;
        if (root == null)
            return 0;
        if (key == root.data)
            return key;
        close = min_diff(close, root.data, key);
        if (key > root.data && root.right != null)
            close = min_diff(close, searchClosest(root.right, key), key);
        if (key < root.data && root.left != null)
            close = min_diff(close, searchClosest(root.left, key), key);
        return close;
    }
    //Given a binary search tree, sum all the nodes which are at on the same vertical line.
    int HD_OFFSET = 16;
    private void verticalSUM(Node root, int[] sum, int hd, int min, int max) {
        int index = hd + HD_OFFSET / 2;
        if (index < min) min = index;
        if (index > max) max = index;
        sum[index] += root.data;
        verticalSUM(root.left, sum, hd - 1, min, max);
        verticalSUM(root.right, sum, hd + 1, min, max);
    }
    //Given a binary tree where all the right nodes are leaf nodes,
    // flip it upside down and turn it into a tree with left leaf nodes.
    Node FlipTree(Node root) {
        if (root == null)
            return null;
        // Working base condition
        if (root.left == null && root.right == null) {
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
    public static void BuildMaxHeap(int[] arr) {
        for (int i = arr.length - 1; i >= 0; i--)
            MaxHeapify(arr, i);
    }
    public static void MaxHeapify(int[] arr, int i) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int largest = i;
        if (left < arr.length && arr[left] > arr[largest])
            largest = left;
        if (right < arr.length && arr[right] > arr[largest])
            largest = right;
        if (largest != i) {
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;
            MaxHeapify(arr, largest);
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
        int minRight = minDepth(currentNode.right);
        int min = Math.min(minLeft, minRight);
        return 1 + min;
    }
    //Inorder Successor in Binary Search Tree
    private Node inOrderSuccessor(Node root, Node n) {
        if (n.right != null)
            return minValue(n.right);
        Node succ = null;
        while (root != null) {
            if (n.data < root.data) {
                succ = root;
                root = root.left;
            } else if (n.data > root.data)
                root = root.right;
            else
                break;
        }
        return succ;
    }
    Node inOrderSuccessorWithParent(Node root, Node n)
    {
        // step 1 of the above algorithm
        if( n.right != null )
            return minValue(n.right);
        // step 2 of the above algorithm
       Node p = n.parent;
        while(p != null && n == p.right)
        {
            n = p;
            p = p.parent;
        }
        return p;
    }
    private Node minValue(Node node) {
        Node curr = node;
        while (curr.left != null)
            curr = curr.left;
        return curr;
    }
    //Get Level of a node in a Binary Tree, level = 1
    private int getLevel(Node node, int data, int level)
    {
        if (node == null)
            return 0;
        if (node.data == data)
            return level;
        int downlevel = getLevel(node.left, data, level + 1);
        if (downlevel != 0)
            return downlevel;
        downlevel = getLevel(node.right, data, level + 1);
        return downlevel;
    }
    // Recursive function to check if two Nodes are siblings
    Boolean isSibling(Node root, Node a, Node b)
    {
        if (root== null)  return false;
        return ((root.left==a && root.right==b)||
                (root.left==b && root.right==a)||
                isSibling(root.left, a, b)||
                isSibling(root.right, a, b));
    }
    //Check if two nodes are cousins in a Binary Tree
    private Boolean isCousin(Node root, Node a, Node b)
    {
        if ((getLevel(root, a.data, 1) == getLevel(root, b.data, 1)) && !(isSibling(root,a,b)))
            return true;
        return false;
    }
    //Find distance between two given keys of a Binary Tree
    //Dist(n1, n2) = Dist(root, n1) + Dist(root, n2) - 2*Dist(root, lca)

    //Morris in-order traversal, Find the median of the BST
    public void morrisTraverse(Node root){
        while(root!=null){
            if(root.left==null){
                System.out.println(root.data);
                root=root.right;
            }
            else{
                Node ptr=root.left;
                while(ptr.right!=null && ptr.right!= root)
                    ptr=ptr.right;

                if(ptr.right==null){
                    ptr.right = root;
                    root=root.left;
                }

                else{
                    ptr.right = null;
                    System.out.println(root.data);
                    root=root.right;
                }
            }
        }
    }
    //Find two node in BST that add up to given number x in O(logn) space and O(n) time
    List<Integer> find_sum(int search, Node root) {
        Stack<Node> s1 = new Stack<Node>();
        Stack<Node> s2 = new Stack<Node>();
        Node curr1 = root;
        Node curr2 = root;
        boolean done1, done2;
        done1 = done2 = false;
        int val1 = 0, val2 = 0;
        List<Integer> result = new ArrayList<Integer>();
        while (true) {
            while (!done1) {
                if (curr1 != null) {
                    s1.push(curr1);
                    curr1 = curr1.left;
                } else if (s1.empty()) done1 = true;
                else {
                    curr1 = s1.pop();
                    val1 = curr1.data;
                    curr1 = curr1.right;
                    done1 = true;
                }
            }
        }
        while (!done2) {
            if (curr2 != null) {
                s2.push(curr2);
                curr2 = curr2.right;
            } else {
                if (s2.empty()) done2 = true;
                else {
                    curr2 = s2.pop();
                    val2 = curr2.data;
                    curr2 = curr2.left;
                    done2 = true;
                }
            }
        }
        if (val1 + val2 == search) {
            result.add(val1);
            result.add(val2);
            return result;
        } else if (val1 + val2 > search) {
            done2 = false;
        } else
            done1 = false;
    }

    //Perfect Binary Tree Specific Level Order Traversal
    //1 2 3 4 7 5 6 8 15 9 14 10 13 11 12 16 31 17 30 18 29 19 28 20 27 21 26  22 25 23 24
    //the enqueue order will be: 1st node’s left child, 2nd node’s right child, 1st node’s right child and 2nd node’s left child.
    void printSpecificLevelOrder(Node root)
    {
        if (root == null)
            return;
        System.out.print(root.data);
        // / Since it is perfect Binary Tree, right is not checked
        if (root.left != null)
            System.out.print(root.left.data + " " + root.right.data);
        if (root.left.left == null)
            return;
        // Create a queue and enqueue left and right children of root
        Queue <Node> q = null;
        q.add(root.left);
        q.add(root.right);
        // We process two nodes at a time, so we need two variables to store two front items of queue
        Node first = null, second = null;
        while (!q.isEmpty())
        {
            // Pop two items from queue
            first = q.peek();
            q.poll();
            second = q.peek();
            q.poll();
            // Print children of first and second in reverse order
            System.out.print(first.left.data + " " + second.right.data);
            System.out.print(first.right.data + " " + second.left.data);
            // If first and second have grandchildren, enqueue them in reverse order
            if (first.left.left != null)
            {
                q.add(first.left);
                q.add(second.right);
                q.add(first.right);
                q.add(second.left);
            }
        }
    }
    //Find sum of all left leaves in a given Binary Tree. O(n)
    int leftLeavesSum(Node root)
    {
        int res = 0;
        // Update result if root is not NULL
        if (root != null)
        {
            // If left of root is NULL, then add key of left child
            if (isLeaf(root.left))
                res += root.left.data;
            else // Else recur for left child of root
                res += leftLeavesSum(root.left);
            // Recur for right child of root and update res
            res += leftLeavesSum(root.right);
        }
        return res;
    }
    Boolean isLeaf(Node node)
    {
        if (node == null)
            return false;
        if (node.left == null && node.right == null)
            return true;
        return false;
    }
    //convert a binary tree to a circular doubly-linked list
    Node prev = null;
    Node head = null;
    void treeToDoublyList(Node root, Node prev, Node head)
    {
        if (root == null) return;
        treeToDoublyList(root.left, prev, head);
        // current node's left points to previous node
        root.left = prev;
        if (prev != null)
            prev.right = root; // previous node's right points to current node
        else
            head = root;        // if previous is NULL that current node is head
        Node right = root.right; //Saving right node
        //Now we need to make list created till now as circular
        head.left = root;
        root.right = head;
        //For right-subtree/parent, current node is in-order predecessor
        prev = root;
        treeToDoublyList(right, prev, head);
    }
    //Serialize/Deserialize the binary tree
    // ‘is used to indicate an internal node set bit, and ‘/’ is used as NULL marker for node which has one child
    void Serialize(Node root) {
        if (root == null)
            return;
        Stack<Node> nodeStack = new Stack<Node>();
        nodeStack.push(root);
        StringBuffer sb = new StringBuffer();
        while (nodeStack.size() > 0) {
            // Pop the top item from stack and print it
            Node node = nodeStack.peek();
            nodeStack.pop();
            if(node != null) {
                if(node.left != null && node.right != null) {
                    sb.append(node.data + "'");
                    nodeStack.push(node.right);
                    nodeStack.push(node.left);
                }
                else if(node.left == null && node.right == null)
                    sb.append(node.data);
                else {
                    sb.append(node.data + "'");
                    nodeStack.push(node.right);
                    nodeStack.push(node.left);
                }
            }
            else{
                sb.append( "/");
            }
        }
    }
    int currentIndex = 0;
    public Node Deserialize(String str) {
        if (currentIndex > str.length()) return null;
        else if (str.charAt(currentIndex) == '/')
            return null;
        else if(str.charAt(currentIndex + 1) == '\'') {
            Node root = new Node(str.charAt(currentIndex));
            currentIndex += 2;
            root.left = Deserialize(str);
            root.right = Deserialize(str);
        }
        else{
            Node root = new Node(str.charAt(currentIndex));
            root.left = null;
            root.right = null;
            currentIndex++;
            return root;
        }
        return root;
    }

}