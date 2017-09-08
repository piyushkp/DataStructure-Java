package code.ds;
import java.awt.*;
import java.text.*;
import java.util.*;
import java.lang.Object.*;

/**
 * Created by Piyush Patel.
 */
public class Matrix {
    public static void main(String [] args) {
        //System.out.print("Matrix");
        /*int sol[][] = { {0, 0, 1, 0, 1},
                        {0, 1, 0, 0, 0},
                        {0, 0, 0, 1, 0}
        };
        System.out.println(getShortestPathLength(sol));*/
        int[] in = {2,1,5,6,2,3};
        printMatrix(15);

    }
    /* Find the number of islands. Given a boolean 2D matrix, find the number of islands.
    This is an variation of the standard problem: �Counting number of connected components in a undirected graph�.
    Time complexity: O(ROW x COL) */
    private static int numIslands(int[][] grid) {
        int row = grid.length;
        if (row == 0) return 0;
        int col = grid[0].length;
        int count = 0;

        boolean[][] mark = new boolean[row][col];
        Queue<Point> q = new LinkedList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == 1 && !mark[i][j]) {
                    q.add(new Point(i, j));
                    mark[i][j] = true;
                    while (!q.isEmpty()) {
                        Point temp = q.remove();
                        int x = temp.x;
                        int y = temp.y;

                        if (x + 1 < row && grid[x + 1][y] == 1 && !mark[x + 1][y]) {
                            q.add(new Point(x + 1, y));
                            mark[x + 1][y] = true;
                        }
                        if (y + 1 < col && grid[x][y + 1] == 1 && !mark[x][y + 1]) {
                            q.add(new Point(x, y + 1));
                            mark[x][y + 1] = true;
                        }
                        if (x - 1 >= 0 && grid[x - 1][y] == 1 && !mark[x - 1][y]) {
                            q.add(new Point(x - 1, y));
                            mark[x - 1][y] = true;
                        }
                        if (y - 1 >= 0 && grid[x][y - 1] == 1 && !mark[x][y - 1]) {
                            q.add(new Point(x, y - 1));
                            mark[x][y - 1] = true;
                        }
                    }
                    count += 1;
                }
            }
        }
        return count;
    }

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
    //method to rotate the image by 90 degrees clock wise Can you do this in place? time: O(n), space: O(1)
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
    public void setZeroes(int[][] matrix) {
        boolean firstRowZero = false;
        boolean firstColumnZero = false;
        //set first row and column zero or not
        for(int i=0; i<matrix.length; i++){
            if(matrix[i][0] == 0){
                firstColumnZero = true;
                break;
            }
        }
        for(int i=0; i<matrix[0].length; i++){
            if(matrix[0][i] == 0){
                firstRowZero = true;
                break;
            }
        }
        //mark zeros on first row and column
        for(int i=1; i<matrix.length; i++){
            for(int j=1; j<matrix[0].length; j++){
                if(matrix[i][j] == 0){
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }
        //use mark to set elements
        for(int i=1; i<matrix.length; i++){
            for(int j=1; j<matrix[0].length; j++){
                if(matrix[i][0] == 0 || matrix[0][j] == 0){
                    matrix[i][j] = 0;
                }
            }
        }
        //set first column and row
        if(firstColumnZero){
            for(int i=0; i<matrix.length; i++)
                matrix[i][0] = 0;
        }
        if(firstRowZero){
            for(int i=0; i<matrix[0].length; i++)
                matrix[0][i] = 0;
        }
    }
    //Count zeros in a row wise and column wise sorted matrix
    static int countNumZeroes(int[][] matrix) {
        int row = matrix.length - 1, col = 0, numZeroes = 0;
        while (col < matrix[0].length) {
            while (matrix[row][col] != 0) {
                if (--row < 0)
                    return numZeroes;
            }
            // Add one since matrix index is 0 based
            numZeroes += row + 1;
            col++;
        }
        return numZeroes;
    }
    public static int countZero(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int m = matrix.length;
        int n = matrix[0].length;
        int count =0;
        return zeroHelper(matrix, 0, m - 1, 0, n - 1, count);
    }
    private static int zeroHelper(int[][] matrix, int rowStart, int rowEnd, int colStart, int colEnd, int count) {
        if (rowStart > rowEnd || colStart > colEnd) {
            return count;
        }
        int rowMid = rowStart + (rowEnd - rowStart) / 2;
        int colMid = colStart + (colEnd - colStart) / 2;
        if (matrix[rowMid][colMid] == 1) {
            return  zeroHelper(matrix, rowStart, rowMid - 1, colStart, colMid - 1, count)+
                    zeroHelper(matrix, rowMid, rowEnd, colStart, colMid - 1, count) +
                    zeroHelper(matrix, rowStart, rowMid - 1, colMid, colEnd, count);
        }
        else if(matrix[rowEnd][colEnd] == 0)
            count += (rowEnd - rowStart + 1) * (colEnd - colStart + 1);
       else{// (matrix[rowMid][colMid] == 0 ) {
            count ++;
            //return  zeroHelper(matrix, rowMid+1, rowEnd, colStart, colMid, count); //+
                    //zeroHelper(matrix, rowStart, rowMid, colMid+1, colEnd, count); //+
                    //zeroHelper(matrix, rowMid + 1, rowEnd, colMid + 1, colEnd, count);
        }
        return  count;
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
    //http://buttercola.blogspot.com/2014/08/leetcode-search-2d-matrix.html
    //Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:
    //Integers in each row are sorted from left to right.
    //The first integer of each row is greater than the last integer of the previous row.
    public boolean searchMatrix1(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) {
            return false;
        }
        int m = matrix.length;
        int n = matrix[0].length;
        // Step 1: find the rowId of the target number
        int lo = 0;
        int hi = m - 1;
        while (lo + 1 < hi) {
            int mid = lo + (hi - lo) / 2;
            if (matrix[mid][0] == target) {
                return true;
            } else if (matrix[mid][0] < target) {
                lo = mid;
            } else {
                hi = mid - 1;
            }
        }
        if (matrix[hi][0] == target || matrix[lo][0] == target) {
            return true;
        }
        int rowId;
        if (target > matrix[lo][0] && target <= matrix[lo][n - 1]) {
            rowId = lo;
        } else {
            rowId = hi;
        }
        // Step 2: find the target number in the rowId
        lo = 0;
        hi = n - 1;
        while (lo + 1 < hi) {
            int mid = lo + (hi - lo) / 2;
            if (matrix[rowId][mid] == target) {
                return true;
            } else if (matrix[rowId][mid] < target) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        if (matrix[rowId][hi] == target || matrix[rowId][lo] == target) {
            return true;
        }
        return false;
    }


    /*Given a 2D matrix(square or rectangular) print it in spiral way.
        e.g 1 2 3
            4 5 6
            7 8 9
     Printing should be 1 2 3 6 9 8 7 4 5 */
    public static void spiralprint(int matrix[][]) {
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
    /*Write some code that accepts an integer and prints the integers from 0 to that input integer in a spiral format.
    For example, if I supplied 24 the output would be:
    ```
    20 21 22 23 24
    19  6  7  8  9
    18  5  0  1 10
    17  4  3  2 11
    16 15 14 13 12
    ```                 */
    private static void printMatrix(int n){
        double sqrt = Math.sqrt(n+1);
        int k = (int) sqrt;
        if(Math.pow(sqrt,2) != Math.pow(k,2))
           k+= 1;
        int[][] result = new int[k][k];
        int top = 0, bottom  = k -1, left = 0, right = k-1;
        int m = k * k;
        boolean flag = false;
        while(n >=0)
        {
            for(int i = right; i>= left;i--) {
                if ( !flag && n+1 < m) {
                    m--;
                    continue;
                }
                flag =true;
                result[top][i] = n;
                n--;
            }
            top++;
            for(int i = top;i<=bottom;i++) {
                if ( !flag && n+1 < m) {
                    m--;
                    continue;
                }
                flag =true;
                result[i][left] = n;
                n--;
            }
            left++;
            for(int i= left;i<=right;i++){
                if ( !flag && n+1 < m) {
                    m--;
                    continue;
                }
                flag =true;
                result[bottom][i] = n;
                n--;
            }
            bottom--;
            for(int i= bottom; i>= top;i--){
                if ( !flag && n+1 < m) {
                    m--;
                    continue;
                }
                flag =true;
                result[i][right] = n;
                n--;
            }
            right--;
        }
        for(int i =0; i<k;i++){
            for(int j = 0;j<k;j++){
                System.out.print(result[i][j] + " ");
            }
            System.out.println();
        }
    }

    /* Print matrix in below order
    * 1 2 3
    * 4 5 6
    * 7 8 9
    * Ouput:    7
    *           4 8
    *           1 5 9
    *           2 6
    *           3
    * */
    public static void printMat(int[][] mat) {
        if (mat == null || mat.length == 0)
            return;
        int left = 0, up = 0;
        int down = mat.length - 1;
        int right = mat[0].length - 1;
        for (int i = down; i >= 0; i--) {
            int x = i;
            int y = left;
            while (x <= down && y <= down) {
                System.out.print(mat[x][y] + " ");
                x++;
                y++;
            }
            System.out.println();
        }
        left++;
        for (int i = left; i <= right; i++) {
            int x = up;
            int y = i;
            while (x <= right && y <= right) {
                System.out.print(mat[x][y] + " ");
                x++;
                y++;
            }
            System.out.println();
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
            area = largestRectangleArea(temp);
            if(area > maxArea){
                maxArea = area;
            }
        }
        return maxArea;
    }
    //Given n non-negative integers representing the histogram's bar height where the width of each bar is 1, find the area of largest rectangle in the histogram.
    //input = [2,1,5,6,2,3] output = 10
    public static int largestRectangleArea(int[] height) {
        Stack<Integer> stack = new Stack<>();
        int max_area = 0;
        for (int i = 0; i <= height.length; ++i) {
            int height_bound = (i == height.length) ? 0 : height[i];
            while (!stack.isEmpty()) {
                int h = height[stack.peek()];
                // calculate the area for every ascending slope.
                if (h < height_bound) {
                    break;
                }
                stack.pop();
                // at the end, the area with the height of the minimal element.
                int index = stack.isEmpty() ? -1 : i - 1 - stack.peek();
                max_area = Math.max(max_area, h * index);
            }
            stack.push(i);
        }
        return max_area;
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
    //Flood Fill algorithm using iterative solution.
    static final int ROW = 18, COL = 20;
    private static char[][] floodFill(char[][]m , int x, int y, char target, char replace) {
        if (m[x][y] == replace) return null;  // current is same as node - 1
        Queue<java.awt.Point> q = new LinkedList();
        q.add(new Point(x, y));
        while (!q.isEmpty()) {
            Point temp = q.remove();
            x = temp.x;
            y = temp.y;
            if (m[x][y] != replace && m[x][y] == target) // 7
            {
                m[x][y] = replace;  //
                if (y < m[x].length - 1)
                    q.add(new Point(x, y + 1));
                if (y > 0)
                    q.add(new Point(x, y - 1));
                if (x < m.length - 1)
                    q.add(new Point(x + 1, y));
                if (x > 0)
                    q.add(new Point(x - 1, y));
            }
        }
        return m;
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
    //https://gist.github.com/cloudbank/703e09268dd69f06392743cfd6b47f11
    //https://gist.github.com/shufenghui/17b4effc3a48253c3c02
    public static int getShortestPathLength(int[][] maze){
        if(maze == null || maze.length == 0) return 0;
        Queue<Point> queue = new LinkedList<>();
        boolean[][] visited = new boolean[maze.length][maze[0].length];
        queue.add(new Point(0,0));
        int level = 0;
        while(queue.size() > 0){
            int count = queue.size();
            while(count -- > 0){
                Point pt = queue.remove();
                if(pt.x == maze.length - 1 && pt.y == maze[0].length - 1)
                    return level;
                visited[pt.x][pt.y] = true; // visited
                if(pt.x - 1 >= 0 && maze[pt.x-1][pt.y] == 0 && !visited[pt.x-1][pt.y])
                    queue.offer(new Point(pt.x-1, pt.y));
                if(pt.x + 1 < maze.length && !visited[pt.x+1][pt.y] && maze[pt.x+1][pt.y] == 0)
                    queue.offer(new Point(pt.x+1, pt.y));
                if(pt.y - 1 >= 0 && !visited[pt.x][pt.y-1] && maze[pt.x][pt.y-1] == 0)
                    queue.offer(new Point(pt.x, pt.y-1));
                if(pt.y + 1 < maze[0].length && !visited[pt.x][pt.y+1] && maze[pt.x][pt.y+1] == 0)
                    queue.offer(new Point(pt.x, pt.y+1));
            }
            level++;
        }
        return -1;
    }

    //Count all paths in Maze. Robot Travel Problem.
    //In Dynamic pro­gram­ming solution, we need to take care of two conditions, first we are not solving it for
    //blocked cells and while solving for other cells do not involve blocked cells.
    static int countAllMazePathDP(int [][] maze){
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
    //A matrix is Toepliz,if each descending diagonal from left to right is constant. Determine if a matrix is Toepliz.
    /*  Input:
            67892
            46789
            14678
            01467
        Output:
            True*/
    public static boolean isToepliz(int mat[][]) {
        // do for each element in first row
        for (int i = 0; i < M; i++) {
            // check descending diagonal starting from position (0, j) in the matrix
            if (!checkDiagonal(mat, 0, i))
                return false;
        }
        // do for each element in first column
        for (int i = 1; i < N; i++) {
            // check descending diagonal starting from position (i, 0) in the matrix
            if (!checkDiagonal(mat, i, 0))
                return false;
        }
        // we only reach here when each descending diagonal from left to right is same
        return true;
    }
    public static boolean checkDiagonal(int mat[][], int i, int j){
        int res = mat[i][j];
        while (++i < N && ++j < M){
            // mismatch found
            if (mat[i][j] != res)
                return false;
        }
        // we only reach here when all elements in given diagonal are same
        return true;
    }
    /*Given a 2D matrix of integers, sort it such that: - every row is sorted in ascending order from left to right
    - every column is sorted in ascending order from top to down
    - all items in the same row are unique */
    public static int[][] sortMatrix(int[][] a) {
        int rowCount = a.length;
        int colCount = a[0].length;
        int[] temp = new int[rowCount * colCount];
        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < colCount; c++) {
                temp[r * colCount + c] = a[r][c];
            }
        }
        Arrays.sort(temp);
        for (int r = 0; r < rowCount; r++) {
            int k = 0;
            for (int c = 0; c < colCount; c++) {
                a[r][c] = temp[r + 2*k];
                k++;
            }
        }
        return a;
    }
    //design a method to check if a Sudoku board is valid
    public static boolean isValidSudoku(char[][] board){
        if(board == null || board.length != 9 || board[0].length != 9)
            return false;
        HashSet<Integer> rows = new HashSet<>();
        HashSet<Integer> cols = new HashSet<>();
        HashSet<Integer> cubes = new HashSet<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                char current = board[i][j];
                if(Character.isDigit(current)){
                    int cube = 3 * (i/3) + (j/3);
                    if(!rows.add((int)current) || !cols.add((int)current) || !cubes.add(cube))
                        return  false;
                }
                else if(!Character.isWhitespace(current))
                    return false;
            }
        }
        return true;
    }
    //MinCost DP: Given a cost matrix cost[][] and a position (m, n) in cost[][], write a function that returns cost of minimum cost path to reach (m, n) from (0, 0).
    private static int minCost(int cost[][], int m, int n){
        int i, j;
        int tc[][]=new int[m+1][n+1];
        tc[0][0] = cost[0][0];
        /* Initialize first column of total cost(tc) array */
        for (i = 1; i <= m; i++)
            tc[i][0] = tc[i-1][0] + cost[i][0];
         /* Initialize first row of tc array */
        for (j = 1; j <= n; j++)
            tc[0][j] = tc[0][j-1] + cost[0][j];
         /* Construct rest of the tc array */
        for (i = 1; i <= m; i++)
            for (j = 1; j <= n; j++)
                tc[i][j] = Math.min(Math.min(tc[i-1][j-1], tc[i-1][j]), tc[i][j-1]) + cost[i][j];
        return tc[m][n];
    }


}
