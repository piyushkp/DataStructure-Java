package code.ds;

/**
 * Created by Piyush Patel.
 */
public class Graph
{
    class Neighbor {
        public int vertexNum;
        public Neighbor next;
        public Neighbor(int vnum, Neighbor nbr) {
            this.vertexNum = vnum;
            next = nbr;
        }
    }
    class Vertex {
        String name;
        Neighbor adjList;
        Vertex(String name, Neighbor neighbors) {
            this.name = name;
            this.adjList = neighbors;
        }
    }
    // Find the number of edges in undirected weighted graph
    private int numOfEdges(int [][] adjacencyMatrix)
    {
        int numOfEdges = 0;
        for(int row =0; row < adjacencyMatrix.length; row++)
        {
            for(int col = 0; col < row; col++)
            {
                if(adjacencyMatrix[row][col] != -1)
                    numOfEdges++;
            }
        }
        return  numOfEdges++;
    }
    //How to find if nodes in graph are exactly 1/2/3 edges apart.
    //The idea is to build a 3D table where first dimension is source, second dimension is destination, third dimension
    // is number of edges from source to destination, and the value is count of walks
    // assumption: there are total k edges in graph and total V nodes
    // Time Complexity: O (V^3 K)
    private boolean isNodesKEdgesApart(int graph[][], int u, int v, int k)
    {
        int [][][] count = countWalks(graph,k,graph.length);
        boolean flag = false;
        for (int i = 1; i<=k; i++)
        {
             if(i < 4)
             {
                 if(count[u][v][i] > 0)
                     flag = true;
             }
            else
             {
                 // if nodes are more than 3 edges apart return false
                if(count[u][v][i] > 0)
                    return false;
             }
        }
        return flag;
    }
    int[][][] countWalks(int graph[][], int k, int V)
    {
        int [][][] count = new int [V][V][k+1];
        for (int e = 0; e <= k; e++)
        {
            for (int i = 0; i < V; i++)  // for source
            {
                for (int j = 0; j < V; j++) // for destination
                {
                    // initialize value
                    count[i][j][e] = 0;
                    if (e == 0 && i == j)
                        count[i][j][e] = 1;
                    if (e == 1 && graph[i][j] !=-1)
                        count[i][j][e] = 1;
                    // go to adjacent only when number of edges is more than 1
                    if (e > 1)
                    {
                        for (int a = 0; a < V; a++) // adjacent of source i
                            if (graph[i][a] != -1)
                                count[i][j][e] += count[a][j][e-1];
                    }
                }
            }
        }
        return count;
    }

    // DFS traversal of the graph
    Vertex[] adjLists;
    public void dfs() {
        boolean[] visited = new boolean[adjLists.length];
        for (int v=0; v < visited.length; v++) {
            if (!visited[v]) {
                System.out.println("\nSTARTING AT " + adjLists[v].name);
                dfs(v, visited);
            }
        }
    }
    private void dfs(int v, boolean[] visited) {
        visited[v] = true;
        System.out.println("visiting " + adjLists[v].name);
        for (Neighbor nbr=adjLists[v].adjList; nbr != null; nbr=nbr.next) {
            if (!visited[nbr.vertexNum]) {
                System.out.println("\n" + adjLists[v].name + "--" + adjLists[nbr.vertexNum].name);
                dfs(nbr.vertexNum, visited);
            }
        }
    }
    // BFS traversal of graph
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
    int shortestPath(int graph[][], int u, int v, int k)
    {
        // Base cases
        if (k == 0 && u == v)             return 0;
        if (k == 1 && graph[u][v] != -1) return graph[u][v];
        if (k <= 0)                       return -1;
        // Initialize result
        int res = -1;
        // Go to all adjacents of u and recur
        for (int i = 0; i < graph.length; i++)
        {
            if (graph[u][i] != -1 && u != i && v != i)
            {
                int rec_res = shortestPath(graph, i, v, k-1);
                if (rec_res != -1)
                    res = Math.min(res, graph[u][i] + rec_res);
            }
        }
        return res;
    }
}
