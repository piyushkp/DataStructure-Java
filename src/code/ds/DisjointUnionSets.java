package code.ds;

/**
 * Created by ppatel2 on 1/30/2017.
 */
import java.io.*;
import java.util.*;
public class DisjointUnionSets {
    public static void main(String[] args) {
        //System.out.print("DisjointUnionSets");
        ArrayList<ArrayList<Integer>> input = new ArrayList<>();
        ArrayList<Integer> tm = new ArrayList<>();
        tm.add(0);
        tm.add(1);
        //tm.add(2);

        ArrayList<Integer> tm1 = new ArrayList<>();
        tm1.add(1);
        tm1.add(2);


        ArrayList<Integer> tm2 = new ArrayList<>();
        tm2.add(2);
        tm2.add(1);

        input.add(0,tm);
        input.add(1,tm1);
        input.add(2, tm2);
        List<List<String>> out = getStacks(input, 3);
        char[][] mat = {
                {'x','.','.','x'},
                {'.','x','.','.'},
                {'.','.','x','x'},
                {'x','.','x','x'}
        };
        System.out.println(findFriendCircles(mat));
    }

    int[] rank, parent;
    int n;

    // Constructor
    public DisjointUnionSets(int n) {
        rank = new int[n];
        parent = new int[n];
        this.n = n;
        makeSet();
    }
    // Creates n sets with single item in each
    void makeSet() {
        for (int i = 0; i < n; i++) {
            // Initially, all elements are in their own set.
            parent[i] = i;
        }
    }
    // Returns representative of x's set
    int find(int x) {
        // Finds the representative of the set that x is an element of
        if (parent[x] != x) {
            // if x is not the parent of itself Then x is not the representative of his set,
            parent[x] = find(parent[x]);
            // so we recursively call Find on its parent and move i's node directly under the representative of this set
        }
        return parent[x];
    }

    // Unites the set that includes x and the set that includes x
    void union(int x, int y) {
        // Find representatives of two sets
        int xRoot = find(x), yRoot = find(y);
        // Elements are in the same set, no need to unite anything.
        if (xRoot == yRoot)
            return;
        // If x's rank is less than y's rank
        if (rank[xRoot] < rank[yRoot])
            // Then move x under y  so that depth of tree remains less
            parent[xRoot] = yRoot;
            // Else if y's rank is less than x's rank
        else if (rank[yRoot] < rank[xRoot])
            // Then move y under x so that depth of tree remains less
            parent[yRoot] = xRoot;
        else // if ranks are the same
        {
            // Then move y under x (doesn't matter which one goes where)
            parent[yRoot] = xRoot;
            // And increment the the result tree's rank by 1
            rank[xRoot] = rank[xRoot] + 1;
        }
    }
    // count the number of island
    static int countIslands(int a[][]) {
        int n = a.length;
        int m = a[0].length;

        DisjointUnionSets dus = new DisjointUnionSets(n * m);

        /* The following loop checks for its neighbours
           and unites the indexes  if both are 1. */
        for (int j = 0; j < n; j++) {
            for (int k = 0; k < m; k++) {
                // If cell is 0, nothing to do
                if (a[j][k] == 0)
                    continue;

                // Check all 8 neighbours and do a union
                // with neighbour's set if neighbour is
                // also 1
                if (j + 1 < n && a[j + 1][k] == 1)
                    dus.union(j * (m) + k, (j + 1) * (m) + k);
                if (j - 1 >= 0 && a[j - 1][k] == 1)
                    dus.union(j * (m) + k, (j - 1) * (m) + k);
                if (k + 1 < m && a[j][k + 1] == 1)
                    dus.union(j * (m) + k, (j) * (m) + k + 1);
                if (k - 1 >= 0 && a[j][k - 1] == 1)
                    dus.union(j * (m) + k, (j) * (m) + k - 1);
                if (j + 1 < n && k + 1 < m && a[j + 1][k + 1] == 1)
                    dus.union(j * (m) + k, (j + 1) * (m) + k + 1);
                if (j + 1 < n && k - 1 >= 0 && a[j + 1][k - 1] == 1)
                    dus.union(j * m + k, (j + 1) * (m) + k - 1);
                if (j - 1 >= 0 && k + 1 < m && a[j - 1][k + 1] == 1)
                    dus.union(j * m + k, (j - 1) * m + k + 1);
                if (j - 1 >= 0 && k - 1 >= 0 && a[j - 1][k - 1] == 1)
                    dus.union(j * m + k, (j - 1) * m + k - 1);
            }
        }

        // Array to note down frequency of each set
        int[] c = new int[n * m];
        int numberOfIslands = 0;
        for (int j = 0; j < n; j++) {
            for (int k = 0; k < m; k++) {
                if (a[j][k] == 1) {

                    int x = dus.find(j * m + k);

                    // If frequency of set is 0,
                    // increment numberOfIslands
                    if (c[x] == 0) {
                        numberOfIslands++;
                        c[x]++;
                    } else
                        c[x]++;
                }
            }
        }
        return numberOfIslands;
    }

    // Given Stack with the services find out what stack can be deployed disjointly ? Have no service in common
    /*
    Stack1: a ->b ->c
    Stack2: a-> d -> e-> a
    Stack3: f->g->h->f
    Output: Stack 3, (Stack 1, Stack2)
    */
    static List<List<String>> getStacks(ArrayList<ArrayList<Integer>> input,int n)
    {
        List<List<String>> result  = new ArrayList<>();
        HashSet<String> set = new HashSet<>();
        DisjointUnionSets dus = new DisjointUnionSets(n);
        dus.union(0,1);
        dus.union(1,2);
        //dus.union(0,3);
        dus.union(2,0);
        //dus.union(3,4);
        //dus.union(4,0);
        //dus.union(5,6);
        //dus.union(6,7);
        //dus.union(7,8);
        for (int i = 0; i < input.size(); i++) {
            if(!set.contains("Stack " + (i+1))) {
                List<String> temp = new ArrayList<>();
                temp.add("Stack " + (i + 1));
                set.add("Stack " + (i + 1));
                for (int j = i + 1; j < input.size(); j++) {
                    if (dus.find(input.get(i).get(0)) == dus.find(input.get(j).get(0))) {
                        temp.add("Stack " + (j + 1));
                        set.add("Stack " + (j + 1));
                    }
                }
                result.add(temp);
            }
        }
        return result;
    }
    //find number of friend circle in given matrix
    static int findFriendCircles(char[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        HashSet<Integer> set =  new HashSet<>();
        DisjointUnionSets dus = new DisjointUnionSets(m * n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 'x')
                    dus.union(i, j);
            }
        }
        for (int i = 0; i < m; i++) {
            set.add(dus.find(i));
        }
        return set.size();
    }

}
