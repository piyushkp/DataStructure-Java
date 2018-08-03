package code.ds;

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
  public boolean hasCycleDFS(code.ds.Graph graph) {
    Set<Vertex> visited = new HashSet<Vertex>();
    for (Collection<Vertex<Object>> vertex : graph.getAllVertex()) {
      if (visited.contains(vertex)) {
        continue;
      }
      boolean flag = hasCycleDFSUtil(vertex, visited, null);
      if (flag) {
        return true;
      }
    }
    return false;
  }

  public boolean hasCycleDFSUtil(Vertex vertex, Set<Vertex> visited, Vertex parent) {
    visited.add(vertex);
    for (Vertex<T> adj : vertex.getAdjacentVertexes()) {
      if (adj.equals(parent)) {
        continue;
      }
      if (visited.contains(adj)) {
        return true;
      }
      boolean hasCycle = hasCycleDFSUtil(adj, visited, vertex);
      if (hasCycle) {
        return true;
      }
    }
    return false;
  }

  /*Find single source shortest path using Dijkstra's algorithm. Space complexity - O(E + V) Time complexity - O(ElogV)*/
  public Map<Vertex<Integer>, Integer> shortestPath(Graph<Integer> graph,
      Vertex<Integer> sourceVertex) {
    //heap + map data structure
    BinaryMinHeap<Vertex<Integer>> minHeap = new BinaryMinHeap<>();

    //stores shortest distance from root to every vertex
    Map<Vertex<Integer>, Integer> distance = new HashMap<>();

    //stores parent of every vertex in shortest distance
    Map<Vertex<Integer>, Vertex<Integer>> parent = new HashMap<>();

    //initialize all vertex with infinite distance from source vertex
    for (Vertex<Integer> vertex : graph.getAllVertex()) {
      minHeap.add(Integer.MAX_VALUE, vertex);
    }

    //set distance of source vertex to 0
    minHeap.decrease(sourceVertex, 0);

    //put it in map
    distance.put(sourceVertex, 0);

    //source vertex parent is null
    parent.put(sourceVertex, null);

    //iterate till heap is not empty
    while (!minHeap.empty()) {
      //get the min value from heap node which has vertex and distance of that vertex from source vertex.
      BinaryMinHeap<Vertex<Integer>>.Node heapNode = minHeap.extractMinNode();
      Vertex<Integer> current = heapNode.key;

      //update shortest distance of current vertex from source vertex
      distance.put(current, heapNode.weight);

      //iterate through all edges of current vertex
      for (Edge<Integer> edge : current.getEdges()) {

        //get the adjacent vertex
        Vertex<Integer> adjacent = getVertexForEdge(current, edge);

        //if heap does not contain adjacent vertex means adjacent vertex already has shortest distance from source vertex
        if (!minHeap.containsData(adjacent)) {
          continue;
        }

        //add distance of current vertex to edge weight to get distance of adjacent vertex from source vertex
        //when it goes through current vertex
        int newDistance = distance.get(current) + edge.getWeight();

        //see if this above calculated distance is less than current distance stored for adjacent vertex from source vertex
        if (minHeap.getWeight(adjacent) > newDistance) {
          minHeap.decrease(adjacent, newDistance);
          parent.put(adjacent, current);
        }
      }
    }
    return distance;
  }

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
}



