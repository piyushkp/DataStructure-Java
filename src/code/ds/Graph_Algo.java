package code.ds;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Created by Piyush Patel.
 */
public class Graph_Algo {

  //DFS traversal
  public void DFS(Graph<Integer> graph) {
    Set<Long> visited = new HashSet<Long>();
    for (Vertex<Integer> vertex : graph.getAllVertex()) {
      if (!visited.contains(vertex.getId())) {
        DFSUtil(vertex, visited);
      }
    }

  }

  private void DFSUtil(Vertex<Integer> v, Set<Long> visited) {
    visited.add(v.getId());
    System.out.print(v.getId() + " ");
    for (Vertex<Integer> vertex : v.getAdjacentVertexes()) {
      if (!visited.contains(vertex.getId())) {
        DFSUtil(vertex, visited);
      }
    }
  }

  //BFS traversal
  public void BFS(Graph<Integer> graph) {
    Set<Long> visited = new HashSet<Long>();
    Queue<Vertex<Integer>> q = new LinkedList<Vertex<Integer>>();
    for (Vertex<Integer> vertex : graph.getAllVertex()) {
      if (!visited.contains(vertex.getId())) {
        q.add(vertex);
        visited.add(vertex.getId());
        while (q.size() != 0) {
          Vertex<Integer> vq = q.poll();
          System.out.print(vq.getId() + " ");
          for (Vertex<Integer> v : vq.getAdjacentVertexes()) {
            if (!visited.contains(v.getId())) {
              q.add(v);
              visited.add(v.getId());
            }
          }
        }
      }
    }
  }

  //Detect cycle in directed graph
  public boolean hasCycle(Graph<Integer> graph) {
    Set<Vertex<Integer>> whiteSet = new HashSet<>();
    Set<Vertex<Integer>> graySet = new HashSet<>();
    Set<Vertex<Integer>> blackSet = new HashSet<>();

    for (Vertex<Integer> vertex : graph.getAllVertex()) {
      whiteSet.add(vertex);
    }

    while (whiteSet.size() > 0) {
      Vertex<Integer> current = whiteSet.iterator().next();
      if (dfs(current, whiteSet, graySet, blackSet)) {
        return true;
      }
    }
    return false;
  }

  private boolean dfs(Vertex<Integer> current, Set<Vertex<Integer>> whiteSet,
      Set<Vertex<Integer>> graySet, Set<Vertex<Integer>> blackSet) {
    //move current to gray set from white set and then explore it.
    moveVertex(current, whiteSet, graySet);
    for (Vertex<Integer> neighbor : current.getAdjacentVertexes()) {
      //if in black set means already explored so continue.
      if (blackSet.contains(neighbor)) {
        continue;
      }
      //if in gray set then cycle found.
      if (graySet.contains(neighbor)) {
        return true;
      }
      if (dfs(neighbor, whiteSet, graySet, blackSet)) {
        return true;
      }
    }
    //move vertex from gray set to black set when done exploring.
    moveVertex(current, graySet, blackSet);
    return false;
  }

  private void moveVertex(Vertex<Integer> vertex, Set<Vertex<Integer>> sourceSet,
      Set<Vertex<Integer>> destinationSet) {
    sourceSet.remove(vertex);
    destinationSet.add(vertex);
  }

  /* Given an undirected graph find cycle in this graph.
   * Runtime and space complexity for both the techniques is O(v) where v is total number of vertices in the graph.*/


  /*Find single source shortest path using Dijkstra's algorithm. Space complexity - O(E + V) Time complexity - O(ElogV)*/

  private Vertex<Integer> getVertexForEdge(Vertex<Integer> v, Edge<Integer> e) {
    return e.getVertex1().equals(v) ? e.getVertex2() : e.getVertex1();
  }

  /*Given a list of packages that need to be built and the dependencies for each package, determine a valid order in which to build the packages.
  eg. 0:
      1: 0
      2: 0
      3: 1, 2
      4: 3
  output: 0, 1, 2, 3, 4 */
  // Build Order Problem using topological sort
  // Input is a list of dependencies where the index is the process number and the value is the numbers the processes it depends on
  public static List<Integer> buildOrder(int[][] dependencies) {
    Set<Integer> temporaryMarks = new HashSet<>();
    Set<Integer> permanentMarks = new HashSet<>();
    List<Integer> result = new LinkedList<>();
    // Recursively search from any unmarked node
    for (int i = 0; i < dependencies.length; i++) {
      if (!permanentMarks.contains(i)) {
        visit(i, dependencies, temporaryMarks, permanentMarks, result);
      }
    }

    return result;
  }

  // Search through all unmarked nodes accessible from process
  public static void visit(int project, int[][] dependencies, Set<Integer> temporaryMarks,
      Set<Integer> permanentMarks, List<Integer> result) {
    // Throw an error if we find a cycle
    if (temporaryMarks.contains(project)) {
      throw new RuntimeException("Graph is not acyclic");
    }

    // If we haven't visited the node, recursively search from there
    if (!permanentMarks.contains(project)) {
      temporaryMarks.add(project);

      // Perform recursive search from children
      for (int i : dependencies[project]) {
        visit(i, dependencies, temporaryMarks, permanentMarks, result);
      }
      // Add permanent mark, remove temporary mark, and add to results list
      permanentMarks.add(project);
      temporaryMarks.remove(project);
      result.add(project);
    }
  }
  //8 puzzle problem.
  //https://github.com/ChuntaoLu/Algorithms/blob/master/week4/8-puzzle/src/Solver.java

  // clone unidirected graph
  // if nodes are unique
  class UndirectedGraphNode {
    int label;
    List<UndirectedGraphNode> neighbors;

    UndirectedGraphNode(int x) {
      label = x;
      neighbors = new ArrayList<>();
    }
  }

  private HashMap<Integer, UndirectedGraphNode> map = new HashMap<>();
  private UndirectedGraphNode clone(UndirectedGraphNode node) {
    if (node == null) {
      return null;
    }

    if (map.containsKey(node.label)) {
      return map.get(node.label);
    }
    UndirectedGraphNode tmp = new UndirectedGraphNode(node.label);
    map.put(tmp.label, tmp);
    for (UndirectedGraphNode neighbor : node.neighbors) {
      tmp.neighbors.add(clone(neighbor));
    }
    return tmp;
  }

  // if nodes can have duplicate label
  UndirectedGraphNode clone(UndirectedGraphNode src, HashMap<UndirectedGraphNode, UndirectedGraphNode> visited){
    if (src == null){
      return null;
    }
    if (visited.containsKey(src)){
      return visited.get(src);
    }

    UndirectedGraphNode tmp = new UndirectedGraphNode(src.label);
    visited.put(src, tmp);
    for (UndirectedGraphNode child : src.neighbors){
        tmp.neighbors.add(clone(child, visited));
    }
    return tmp;
  }
}



