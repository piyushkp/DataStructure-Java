package code.ds;
import java.text.*;
import java.util.Deque;
import java.util.LinkedList;
/**
 * Created by Piyush Patel.
 */
public class Matrix {
    //Matrix Region Sum
    // Function to preprcess input mat[M][N].  This function mainly fills aux[M][N] such that aux[i][j] stores sum
    // of elements from (0,0) to (i,j) Time = O(MN)
    void preProcess(int mat[][], int aux[][], int M, int N) {
        // Copy first row of mat[][] to aux[][]
        for (int i = 0; i < N; i++)
            aux[0][i] = mat[0][i];
        // Do column wise sum
        for (int i = 1; i < M; i++)
            for (int j = 0; j < N; j++)
                aux[i][j] = mat[i][j] + aux[i - 1][j];
        // Do row wise sum
        for (int i = 0; i < M; i++)
            for (int j = 1; j < N; j++)
                aux[i][j] += aux[i][j - 1];
    }
    // A O(1) time function to compute sum of submatrix between (tli, tlj) and (rbi, rbj) using aux[][]
    // which is built by the preprocess function
    int sumQuery(int aux[][], int tli, int tlj, int rbi, int rbj) {
        // result is now sum of elements between (0, 0) and rbi, rbj)
        int res = aux[rbi][rbj];
        // Remove elements between (0, 0) and (tli-1, rbj)
        if (tli > 0) res = res - aux[tli - 1][rbj];
        // Remove elements between (0, 0) and (rbi, tlj-1)
        if (tlj > 0) res = res - aux[rbi][tlj - 1];
        // Add aux[tli-1][tlj-1] as elements between (0, 0) and (tli-1, tlj-1) are subtracted twice
        if (tli > 0 && tlj > 0) res = res + aux[tli - 1][tlj - 1];
        return res;
    }
    //Given a grid of size m by n, write an algorithm that
    //computes all paths from 0,0 to m,n such that you can always step horizontally or vertically but cannot reverse.
    int numberOfPaths(int m, int n){
        // Create a 2D table to store results of subproblems
        int count[][]  = new int[m][n];
        // Count of paths to reach any cell in first column is 1
        for (int i = 0; i < m; i++)
            count[i][0] = 1;
        // Count of paths to reach any cell in first column is 1
        for (int j = 0; j < n; j++)
            count[0][j] = 1;
        // Calculate count of paths for other cells in bottom-up manner using the recursive solution
        for (int i = 1; i < m; i++){
            for (int j = 1; j < n; j++)
                // By uncommenting the last part the code calculatest he total possible paths if the diagonal Movements are allowed
                count[i][j] = count[i-1][j] + count[i][j-1]; //+ count[i-1][j-1];
        }
        return count[m-1][n-1];
    }
    //Given  an  image  represented  by  an  NxN matrix,  where  each  pixel  in  the  image  is  4 bytes, write a
    //method to rotate the image by 90 degrees Can you do this in place? time: O(n), space: O(1)
    public static void rotate(int[][] matrix, int n) {
        for (int layer = 0; layer < n / 2; layer++) {
            int first = layer;
            int last = n - 1 - layer;
            for (int i = first; i < last; i++) {
                int offset = i - first;
                int top = matrix[first][i]; // save top
                //left -> top
                matrix[first][i] = matrix[last - offset][first];
                //bottom -> left
                matrix[last - offset][first] = matrix[last][last - offset];
                //right -> bottom
                matrix[last][last - offset] = matrix[i][last];
                //top -> right
                matrix[i][last] = top; // right <- saved top
            }
        }
    }
    //Write an algorithm such that if an element in an MxN matrix is 0, its entire row and column is set to 0
    public static void setZeros(int[][] matrix) {
        int[] row = new int[matrix.length];
        int[] column = new int[matrix[0].length];
        // Store the row and column index with value 0
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    row[i] = 1;
                    column[j] = 1;
                }
            }
        }
        // Set arr[i][j] to 0 if either row i or column j has a 0
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if ((row[i] == 1 || column[j] == 1)) {
                    matrix[i][j] = 0;
                }
            }
        }
    }
    //Given an MX N matrix in which each row and each column is sorted in ascending order, write a method to find an element.
    //As a first approach, we can do binary search on every row to find the element. This algorithm will be 0(M log(N)), since there are M rows and it takes 0(log(N)) time
    //to search each one
    //This approach is O(m+n)
    public static boolean findElement(int[][] matrix, int elem) {
        int row = 0;
        int col = matrix[0].length - 1;
        while (row < matrix.length && col >= 0) {
            if (matrix[row][col] == elem) {
                return true;
            } else if (matrix[row][col] > elem) {
                col--;
            } else {
                row++;
            }
        }
        return false;
    }
    //we can improve above by binary search Split the grid into quadrants. eliminate the 1/4th part and
    //Search the bottom left and the top right.time complexity should be greater than O(log (m + n))
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        int m = matrix.length;
        int n = matrix[0].length;
        return helper(matrix, 0, m - 1, 0, n - 1, target);
    }
    private boolean helper(int[][] matrix, int rowStart, int rowEnd, int colStart, int colEnd, int target) {
        if (rowStart > rowEnd || colStart > colEnd) {
            return false;
        }
        int rowMid = rowStart + (rowEnd - rowStart) / 2;
        int colMid = colStart + (colEnd - colStart) / 2;
        if (matrix[rowMid][colMid] == target) {
            return true;
        }
        if (matrix[rowMid][colMid] > target) {
            return helper(matrix, rowStart, rowMid - 1, colStart, colMid - 1, target) ||
                    helper(matrix, rowMid, rowEnd, colStart, colMid - 1, target) ||
                    helper(matrix, rowStart, rowMid - 1, colMid, colEnd, target);
        } else {
            return helper(matrix, rowMid + 1, rowEnd, colMid + 1, colEnd, target) ||
                    helper(matrix, rowMid + 1, rowEnd, colStart, colMid, target) ||
                    helper(matrix, rowStart, rowMid, colMid + 1, colEnd, target);
        }
    }
    /*Given a 2D matrix(square or rectangular) print it in spiral way.
        e.g 1 2 3
            4 5 6
            7 8 9
     Printing should be 1 2 3 6 9 8 7 4 5 */
    public void spiralprint(int matrix[][]) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return;
        }
        int left = 0;
        int right = matrix[0].length - 1;
        int up = 0;
        int down = matrix.length - 1;
        while (left < right && up < down) {
            for (int i = left; i <= right; i++) {
                System.out.print(matrix[up][i] + " ");
            }
            up++;
            for (int i = up; i <= down; i++) {
                System.out.print(matrix[i][right] + " ");
            }
            right--;
            for (int i = right; i >= left; i--) {
                System.out.print(matrix[down][i] + " ");
            }
            down--;
            for (int i = down; i >= up; i--) {
                System.out.print(matrix[i][left] + " ");
            }
            left++;
        }
    }
    //Read in an n by n matrix and shift each element over by one position along the edges (like a concentric circle).
    /*Input :   1    2    3
                4    5    6
                7    8    9
    Output:     4    1    2
                7    5    3
                8    9    6*/
    void rotatematrix(int mat[][]){
        int up = 0, left = 0;
        int right = mat[0].length - 1;
        int down = mat.length - 1;
        int prev, curr;
    /* row - Staring row index
       m - ending row index
       col - starting column index
       n - ending column index
       i - iterator */
        while (up < down && left < right){
            if (up + 1 == down || left + 1 == right)
                break;
            // Store the first element of next row, this element will replace first element of current row
            prev = mat[up + 1][left];
         /* Move elements of first row from the remaining rows */
            for (int i = left; i < right; i++){
                curr = mat[up][i];
                mat[up][i] = prev;
                prev = curr;
            }
            up++;
        /* Move elements of last column from the remaining columns */
            for (int i = up; i < down; i++){
                curr = mat[i][right-1];
                mat[i][right-1] = prev;
                prev = curr;
            }
            right--;
         /* Move elements of last row from the remaining rows */
            if (up < down){
                for (int i = right-1; i >= left; i--){
                    curr = mat[down-1][i];
                    mat[down-1][i] = prev;
                    prev = curr;
                }
            }
            down--;
        /* Move elements of first column from the remaining rows */
            if (left < right){
                for (int i = down-1; i >= up; i--){
                    curr = mat[i][left];
                    mat[i][left] = prev;
                    prev = curr;
                }
            }
            left++;
        }
        // Print rotated matrix
        for (int i=0; i<mat.length; i++){
            for (int j=0; j<mat[0].length; j++)
                System.out.print(mat[i][j]+" ");
            System.out.println();
        }
    }
    /*Given a 2D grid of characters and a word, find all occurrences of given word in grid. A word can be matched in
    all 8 directions at any point. Word is said be found in a direction if all characters match in this direction.
    Input = grid[][] = {"GEEKSFORGEEKS",
                        "GEEKSQUIZGEEK",
                        "IDEQAPRACTICE"};
        word = "GEEKS"
    Output: pattern found at 0, 0
            pattern found at 0, 8
            pattern found at 1, 0      */
    void patternSearch(char grid[][], String word){
        // Consider every point as starting point and search given word
        int R = grid.length; int C = grid[0].length;
        for (int row = 0; row < R; row++)
            for (int col = 0; col < C; col++)
                if (search2D(grid, row, col, word))
                    System.out.print("pattern found at "+ row +", " +col);
    }
    // For searching in all 8 direction
    int x[] = { -1, -1, -1, 0, 0, 1, 1, 1 };
    int y[] = { -1, 0, 1, -1, 1, -1, 0, 1 };
    boolean search2D(char grid[][], int row, int col, String word){
        int R = grid.length; int C = grid[0].length;
        // If first character of word doesn't match with given starting point in grid.
        if (grid[row][col] != word.charAt(0))
            return false;
        int len = word.length();
        // Search word in all 8 directions starting from (row,col)
        for (int dir = 0; dir < 8; dir++){
            // Initialize starting point for current direction
            int k, rd = row + x[dir], cd = col + y[dir];
            // First character is already checked, match remaining characters
            for (k = 1; k < len; k++){
                // If out of bound break
                if (rd >= R || rd < 0 || cd >= C || cd < 0)
                    break;
                // If not matched,  break
                if (grid[rd][cd] != word.charAt(k))
                    break;
                //  Moving in particular direction
                rd += x[dir]; cd += y[dir];
            }
            // If all character matched, then value of must be equal to length of word
            if (k == len)
                return true;
        }
        return false;
    }
    /*Given a 2D matrix of 0s and 1s. Find largest rectangle of all 1s in this matrix.
    Input:
            00010
            11100
            11110
            11000
            11010 In this test case the result needs to be 8.*/
    public int maximum(int input[][]){
        int temp[] = new int[input[0].length];
        int maxArea = 0;
        int area = 0;
        for(int i=0; i < input.length; i++){
            for(int j=0; j < temp.length; j++){
                if(input[i][j] == 0){
                    temp[j] = 0;
                }else{
                    temp[j] += input[i][j];
                }
            }
            area = maxHistogram(temp);
            if(area > maxArea){
                maxArea = area;
            }
        }
        return maxArea;
    }
    public int maxHistogram(int input[]){
        Deque<Integer> stack = new LinkedList<Integer>();
        int maxArea = 0;
        int area = 0;
        int i;
        for(i=0; i < input.length;){
            if(stack.isEmpty() || input[stack.peekFirst()] <= input[i]){
                stack.offerFirst(i++);
            }else{
                int top = stack.pollFirst();
                //if stack is empty means everything till i has to be greater or equal to input[top] so get area by
                //input[top] * i;
                if(stack.isEmpty()){
                    area = input[top] * i;
                }
                //if stack is not empty then everything from i-1 to input.peek() + 1 has to be greater or equal to input[top]
                //so area = input[top]*(i - stack.peek() - 1);
                else{
                    area = input[top] * (i - stack.peekFirst() - 1);
                }
                if(area > maxArea){
                    maxArea = area;
                }
            }
        }
        while(!stack.isEmpty()){
            int top = stack.pollFirst();
            //if stack is empty means everything till i has to be greater or equal to input[top] so get area by
            //input[top] * i;
            if(stack.isEmpty()){
                area = input[top] * i;
            }
            //if stack is not empty then everything from i-1 to input.peek() + 1 has to be greater or equal to input[top]
            //so area = input[top]*(i - stack.peek() - 1);
            else{
                area = input[top] * (i - stack.peekFirst() - 1);
            }
            if(area > maxArea){
                maxArea = area;
            }
        }
        return maxArea;
    }
    /*Write a program to find maximum sum rectangle in give 2D matrix. Assume there is at least one positive number in the 2D matrix.
     Solution: * Keep temp array with size as number of rows. Start left and right from 0
     * and keep adding values for each row and maintain them in this temp array.
     * Run Kadane's algorithm to find max sum subarray in temp. Now increment right by
     * 1. When right reaches last column reset right to 1 and left to 1.
     *Space complexity of this algorithm is O(row)
     * Time complexity of this algorithm is O(row*col*col) */
    public void maxSum(int input[][]){
        int maxSum=0; int leftBound=0; int rightBound=0; int upBound=0; int lowBound=0;
        int rows = input.length;
        int cols = input[0].length;
        int temp[] = new int[rows];
        for(int left = 0; left < cols ; left++){
            for(int i=0; i < rows; i++){
                temp[i] = 0;
            }
            for(int right = left; right < cols; right++){
                for(int i=0; i < rows; i++){
                    temp[i] += input[i][right];
                }
                KadaneResult kadaneResult = kadane(temp);
                if(kadaneResult.maxSum > maxSum){
                    maxSum = kadaneResult.maxSum;
                    leftBound = left;
                    rightBound = right;
                    upBound = kadaneResult.start;
                    lowBound = kadaneResult.end;
                }
            }
        }
        System.out.print("Result [maxSum=" + maxSum + ", leftBound=" + leftBound
                + ", rightBound=" + rightBound + ", upBound=" + upBound
                + ", lowBound=" + lowBound + "]");
    }
    class KadaneResult{
        int maxSum;
        int start;
        int end;
        public KadaneResult(int maxSum, int start, int end) {
            this.maxSum = maxSum;
            this.start = start;
            this.end = end;
        }
    }
    //max sum subarray
    private KadaneResult kadane(int arr[]){
        int max = 0;
        int maxStart = -1;
        int maxEnd = -1;
        int currentStart = 0;
        int maxSoFar = 0;
        for(int i=0; i < arr.length; i++){
            maxSoFar += arr[i];
            if(maxSoFar < 0){
                maxSoFar = 0;
                currentStart = i+1;
            }
            if(max < maxSoFar){
                maxStart = currentStart;
                maxEnd = i;
                max = maxSoFar;
            }
        }
        return new KadaneResult(max, maxStart, maxEnd);
    }
    //Given An array of strings where "L" indicates land and "W" indicates water, and a coordinate marking a starting
    //point in the middle of the ocean Find and mark the ocean in the map by changing appropriate W's to O's.
    //An ocean coordinate is defined to be any coordinate directly adjacent to any other ocean coordinate.
    static final int ROW = 18, COL = 20;
    static char[][] Ocean(char M[][], int row, int col) {
        // Make a bool array to mark visited cells. Initially all cells are unvisited
        boolean visited[][] = new boolean[ROW][COL];
        if(M[row][col] == 'W')
            M[row][col] = 'O';
        OceanUtil(M, row, col, visited);
        return M;
    }
    static boolean isOceanWater(char M[][], int row, int col, boolean visited[][]) {
        // row number is in range, column number is in range and value is 1 and not yet visited
        return (row >= 0) && (row < ROW) && (col >= 0) && (col < COL) && (M[row][col] == 'W' && !visited[row][col]);
    }
    static void OceanUtil(char M[][], int row, int col, boolean visited[][]) {
        // These arrays are used to get row and column numbers of 8 neighbors of a given cell
        int rowNbr[] = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
        int colNbr[] = new int[]{-1, 0, 1, -1, 1, -1, 0, 1};
        // Recur for all connected neighbours
        for (int k = 0; k < 8; ++k)
            if (isOceanWater(M, row + rowNbr[k], col + colNbr[k], visited)) {
                visited[row][col] = true;
                M[row + rowNbr[k]][col + colNbr[k]] = 'O';
                OceanUtil(M, row + rowNbr[k], col + colNbr[k], visited);
            }
    }
    /*A Maze is given as N*N binary matrix of blocks where source block is the upper left most block and destination
    block is lower rightmost block i.e., maze[N-1][N-1]. A rat starts from source and has to reach destination.
    The rat can move only in two directions: forward and down.*/
    /* A utility function to check if x,y is valid index for N*N maze */
    static final int M = 4, N = 4;
    boolean isSafe(int maze[][], int x, int y){
        // if (x,y outside maze) return false
        return (x >= 0 && x < M && y >= 0 && y < N && maze[x][y] == 1);
    }
    /* This function solves the Maze problem using Backtracking. It mainly uses solveMazeUtil() to solve the problem. It returns false if no
       path is possible, otherwise return true and prints the path in the form of 1s. Please note that there may be more than one solutions, this
       function prints one of the feasible solutions.*/
    boolean solveMaze(int maze[][]){
        int sol[][] =   {   {0, 0, 0, 0},
                            {0, 0, 0, 0},
                            {0, 0, 0, 0},
                            {0, 0, 0, 0}
                        };

        if (solveMazeUtil(maze, 0, 0, sol) == false){
            System.out.print("Solution doesn't exist");
            return false;
        }
        //printSolution(sol);
        return true;
    }
    /* A recursive utility function to solve Maze problem */
    boolean solveMazeUtil(int maze[][], int x, int y, int sol[][]){
        // if (x,y is goal) return true
        if (x == M - 1 && y == N - 1){
            sol[x][y] = 1;
            return true;
        }
        // Check if maze[x][y] is valid
        if (isSafe(maze, x, y) == true){
            // mark x,y as part of solution path
            sol[x][y] = 1;
            /* Move forward in x direction */
            if (solveMazeUtil(maze, x + 1, y, sol))
                return true;
             /* If moving in x direction doesn't give solution then  Move down in y direction */
            if (solveMazeUtil(maze, x, y + 1, sol))
                return true;
             /* If none of the above movements work then BACKTRACK: unmark x,y as part of solution path */
            sol[x][y] = 0;
            return false;
        }
        return false;
    }
    //Count all paths in Maze. Robot Travel Problem.
    //In Dynamic pro­gram­ming solution, we need to take care of two conditions, first we are not solving it for
    //blocked cells and while solving for other cells do not involve blocked cells.
    static int countAllPathDP(int [][] maze){
        int result [][] = maze;
        for (int i = 1; i <result.length ; i++) {
            for (int j = 1; j < result.length; j++) {
                if (result[i][j] != 0) {
                    result[i][j] = 0;
                    if (result[i - 1][j] > 0)
                        result[i][j] += result[i - 1][j];
                    if (result[i][j - 1] > 0)
                        result[i][j] += result[i][j - 1];
                }
            }
        }
        return result[maze.length-1][maze.length-1];
    }

}
