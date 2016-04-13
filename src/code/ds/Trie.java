package code.ds;
import java.util.*;
import java.io.*;
import java.nio.*;
import java.lang.*;
/**
 * Created by ppatel2 on 8/26/2014.
 */
public class Trie{
    //Given a text file and a word, find the positions that the word occurs in the file.
    class Node{
        char letter;
        boolean isTerminal=false;
        Map<Character,Node> children=new TreeMap<Character, Node>();
        List<Integer> positions = new ArrayList<Integer>();
        public Node(){}
        public Node(char letter){
            this.letter=letter;
        }
    }
    Node root = new Node();
    boolean contains(java.lang.String word){
        Node current = root;
        char[] word_ = word.toCharArray();
        for(Character c:word_){
            Node next = current.children.get(c);
            if(next==null) return false;
            else current=next;
        }
        return current.isTerminal;
    }
    List<Integer> getItem(java.lang.String word){
        Node current = root;
        char[] word_ = word.toCharArray();
        for(Character c:word_){
            Node next = current.children.get(c);
            if(next==null) {next = new Node(c); current.children.put(c,next); }
            current=next;
        }
        current.isTerminal=true;
        return current.positions;
    }
    void print(){
        List<Node> l = new ArrayList<Node>();
        l.add(root);
        output(l,"");
    }
    //Depth First Search
    void output(List<Node> currentPath, java.lang.String indent){
        Node current = currentPath.get(currentPath.size()-1);
        if(current.isTerminal){
            java.lang.String word="";
            for(Node n:currentPath) word+=n.letter;
            System.out.println(indent+word+" "+current.positions);
        }
        for(Map.Entry<Character,Node> e:current.children.entrySet()){
            Node node = e.getValue();
            List<Node> newlist = new ArrayList<Node>();
            newlist.addAll(currentPath);
            newlist.add(node);
            output(newlist,indent);
        }
    }
    //Finding Shortest unique Prefixes for Strings in an Array
    // Input = {"zebra", "dog", "duck", "dove"}     Output: dog, dov, du, z
    //Time complexity if this step also is O(N) where N is total number of characters in all words.
    class trieNode {
        trieNode child[];
        int freq;  // To store frequency
    };
    // Function to create a new trie node.
    trieNode newTrieNode(){
    trieNode newNode = new trieNode();
    newNode.freq   = 1;
    for (int i = 0; i< Integer.MAX_VALUE; i++)
        newNode.child[i] = null;
    return newNode;
    }
    // Method to insert a new string into Trie
    void insert(trieNode root, String str){
        int len = str.length();
        trieNode pCrawl = root;
        // Traversing over the length of given str.
        for (int level = 0; level<len; level++){
            // Get index of child node from current character in str.
            int index = str.indexOf(level);
            // Create a new child if not exist already
            if (pCrawl.child[index] != null)
                pCrawl.child[index] = newTrieNode();
            else
                (pCrawl.child[index].freq)++;
            // Move to the child
            pCrawl = pCrawl.child[index];
        }
    }
    // This function prints unique prefix for every word stored in Trie. Prefixes one by one are stored in prefix[].
    // 'ind' is current index of prefix[]
    void findPrefixesUtil(trieNode root, char prefix[],int ind){
        // Corner case
        if (root == null)
            return;
        // Base case
        if (root.freq == 1){
            System.out.println(prefix);
            return;
        }
        for (int i=0; i<256; i++){
            if (root.child[i] != null){
                prefix[ind] = i;
                findPrefixesUtil(root.child[i], prefix, ind+1);
            }
        }
    }
    // Function to print all prefixes that uniquely represent all words in arr[0..n-1]
    void findPrefixes(String arr[], int n) {
        // Construct a Trie of all words
        trieNode root = newTrieNode();
        root.freq = 0;
        for (int i = 0; i<n; i++)
            insert(root, arr[i]);
        // Create an array to store all prefixes. Maximum length of an input word is 500
        char prefix [] = new char[500];
        // Print all prefixes using Trie Traversal
        findPrefixesUtil(root, prefix, 0);
    }
}