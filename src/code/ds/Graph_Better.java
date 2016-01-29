package code.ds;
import java.util.*;
import java.io.*;
/**
 * Created by ppatel2 on 1/28/2016.
 */
public class Graph_Better {
    private int V;   // No. of vertices
    // Array  of lists for Adjacency List Representation
    private java.util.LinkedList<Integer> adj[];

    // Constructor
    Graph_Better(int v)
    {
        V = v;
        adj = new java.util.LinkedList[v];
        for (int i=0; i<v; ++i)
            adj[i] = new java.util.LinkedList();
    }
    //Function to add an edge into the graph
    void addEdge(int v, int w)
    {
        adj[v].add(w);  // Add w to v's list.
    }
    private boolean isCyclic()
    {
        boolean [] visited = new boolean[V];
        boolean [] recStack = new boolean[V];
        for(int i = 0; i < V; i++)
        {
            visited[i] = false;
            recStack[i] = false;
        }
        // Call the recursive helper function to detect cycle in different
        // DFS trees
        for(int i = 0; i < V; i++)
            if (isCyclicUtil(i, visited, recStack))
                return true;
        return false;
    }
    boolean isCyclicUtil(int v, boolean visited[], boolean recStack [])
    {
        if(visited[v] == false)
        {
            // Mark the current node as visited and part of recursion stack
            visited[v] = true;
            recStack[v] = true;
            // Recur for all the vertices adjacent to this vertex
            Iterator<Integer> i = adj[v].listIterator();
            while (i.hasNext())
            {
                int n = i.next();
                if ( !visited[n] && isCyclicUtil(n, visited, recStack) )
                    return true;
                else if (recStack[n])
                    return true;
            }
        }
        recStack[v] = false;  // remove the vertex from recursion stack
        return false;
    }
}
