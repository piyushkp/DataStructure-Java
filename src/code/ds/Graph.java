package code.ds;

/**
 * Created by Piyush Patel.
 */
public class Graph
{
    //How to find if nodes in graph are exactly 1/2/3 edges apart.
    private void breadthFirst(Graph graph, LinkedList<String> visited) {
        LinkedList<String> nodes = graph.adjacentNodes(visited.getLast());
        // examine adjacent nodes
        for (String node : nodes) {
            if (visited.contains(node)) {
                continue;
            }
            if (node.equals(END)) {
                visited.add(node);
                printPath(visited);
                visited.removeLast();
                break;
            }
        }
        // in breadth-first, recursion needs to come after visiting adjacent nodes
        for (String node : nodes) {
            if (visited.contains(node) || node.equals(END)) {
                continue;
            }
            visited.addLast(node);
            breadthFirst(graph, visited);
            visited.removeLast();
        }
    }
    //program to find out whether a given graph is Bipartite or not
    // This function returns true if graph G[V][V] is Bipartite, else false
    boolean isBipartite(int G[][V], int src)
    {
        // Create a color array to store colors assigned to all veritces. Vertex
        // number is used as index in this array. The value '-1' of  colorArr[i]
        // is used to indicate that no color is assigned to vertex 'i'.  The value
        // 1 is used to indicate first color is assigned and value 0 indicates
        // second color is assigned.
        int colorArr[V];
        for (int i = 0; i < V; ++i)
            colorArr[i] = -1;
        // Assign first color to source
        colorArr[src] = 1;
        // Create a queue (FIFO) of vertex numbers and enqueue source vertex
        // for BFS traversal
        Queue<int> q;
        q.push(src);
        // Run while there are vertices in queue (Similar to BFS)
        while (!q.empty())
        {
            // Dequeue a vertex from queue ( Refer http://goo.gl/35oz8 )
            int u = q.front();
            q.pop();
            // Find all non-colored adjacent vertices
            for (int v = 0; v < V; ++v)
            {
                // An edge from u to v exists and destination v is not colored
                if (G[u][v] && colorArr[v] == -1)
                {
                    // Assign alternate color to this adjacent v of u
                    colorArr[v] = 1 - colorArr[u];
                    q.push(v);
                }
                //  An edge from u to v exists and destination v is colored with
                // same color as u
                else if (G[u][v] && colorArr[v] == colorArr[u])
                    return false;
            }
        }
        // If we reach here, then all adjacent vertices can be colored with
        // alternate color
        return true;
    }
    //Shortest path with exactly k edges in a directed and weighted graph
    // A naive recursive function to count walks from u to v with k edges
    int shortestPath(int graph[][V], int u, int v, int k)
    {
        int INF = Integer.MAX_VALUE;
        // Base cases
        if (k == 0 && u == v)             return 0;
        if (k == 1 && graph[u][v] != INF) return graph[u][v];
        if (k <= 0)                       return INF;
        // Initialize result
        int res = INF;
        // Go to all adjacents of u and recur
        for (int i = 0; i < V; i++)
        {
            if (graph[u][i] != INF && u != i && v != i)
            {
                int rec_res = shortestPath(graph, i, v, k-1);
                if (rec_res != INF)
                    res = Math.min(res, graph[u][i] + rec_res);
            }
        }
        return res;
    }
}
