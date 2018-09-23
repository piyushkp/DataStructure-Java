package code.ds;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.*;

/**
 * Created by ppatel2 on 3/11/2017.
 */
public class N_Tree {

  public static void main(String[] args) {
    N_Tree t = new N_Tree();
    t.deserialize("ABE)FK)))C)DG)H)I)J)))");

  }

  static class NTree {

    char data;
    ArrayList<NTree> children = new ArrayList<>();

    public NTree(char c) {
      this.data = c;
    }

    public void addChild(NTree node) {
      children.add(node);
    }
  }

  //serialize non- binary tree (n-ary tree)
  private StringBuffer sb = new StringBuffer();

  public String serialize(NTree root) {
    serializeRecursive(root);
    return sb.toString();
  }

  private void serializeRecursive(NTree root) {
    if (root == null) {
      return;
    }
    sb.append(root.data);
    for (NTree node : root.children) {
      serializeRecursive(node);
    }
    sb.append(')');
  }

  public NTree deserialize(String str) {
    NTree root = deserializeRecursive(str);
    return root;
  }

  private int currentIndex = 0;

  private NTree deserializeRecursive(String str) {
    if (currentIndex >= str.length()) {
      return null;
    } else if (str.charAt(currentIndex) == ')') {
      return null;
    }
    NTree root = new NTree(str.charAt(currentIndex));
    while (currentIndex < str.length()) {
      currentIndex++;
      NTree child = deserializeRecursive(str);
      if (child == null) {
        break;
      }
      root.addChild(child);
    }
    return root;
  }

  public static String serialize1( NTree root ) {
    StringBuilder sb = new StringBuilder();
    if ( root != null ) {
      sb.append( root.data ).append( "," );
      for ( NTree child : root.children ) {
        sb.append( serialize1( child ) );
      }
      sb.append( ")" );
    }
    return sb.toString();
  }

  public static NTree simpleDeserialize( String str ) {
    NTree root = null;
    Stack<NTree> stack = new Stack<>();
    StringBuilder data = new StringBuilder();

    for ( int i = 0; i < str.length(); i++ ) {
      if ( str.charAt( i ) == ',' ) {
        NTree child = new NTree(data.charAt(0));
        if ( !stack.isEmpty() ) stack.peek().addChild( child );
        else root = child;
        stack.push( child );
        data = new StringBuilder();
      } else if ( str.charAt( i ) == ')' ) {
        stack.pop();
      } else {
        data.append( str.charAt( i ) );
      }
    }
    return root;
  }

  //longest path of tree undirected
  static int maxSoFar;

  private static int findLongestPath(NTree node) {
    HashSet<Integer> set = new HashSet<>();
    dfs(node, 0, set);
    System.out.println(maxSoFar);
    return maxSoFar;
  }

  static int dfs(NTree node, int idx, HashSet<Integer> set) {
    int maxFirst = 0;
    int maxSecond = 0;
    set.add(idx);
    for (NTree next : node.children) {
      if (set.contains(next.data)) {
        continue;
      }
      int val = dfs(node, next.data, set);
      if (val > maxFirst) {
        maxSecond = maxFirst;
        maxFirst = val;
      } else if (val > maxSecond) {
        maxSecond = val;
      }
    }
    maxSoFar = Math.max(maxSoFar, (maxSecond + maxFirst));
    return ++maxFirst;
  }

}
