package code.ds;
import java.util.*;
import java.io.*;
import java.nio.*;
import java.lang.*;
/**
 * Created by ppatel2 on 8/26/2014.
 */
public class Trie
{
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
}



