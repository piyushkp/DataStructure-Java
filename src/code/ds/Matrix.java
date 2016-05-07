package code.ds;
import java.text.*;
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
    int FindAllPaths(int x, int y, int Xmax, int Ymax) {
        int nResult = 0;
        if (x == Xmax && y == Ymax) return 1;
        if (x < Xmax) nResult += FindAllPaths(x + 1, y, Xmax, Ymax);
        if (y < Ymax) nResult += FindAllPaths(x, y + 1, Xmax, Ymax);
        return nResult;
    }
    //Given a 2D array, print it in spiral form.
    void spiralPrint(int m, int n, int a[][]) {
        int i, k = 0, l = 0;
    /*  k - starting row index
        m - ending row index
        l - starting column index
        n - ending column index
        i - iterator */
        while (k < m && l < n) {
        /* Print the first row from the remaining rows */
            for (i = l; i < n; ++i) {
                System.out.print(a[k][i]);
            }
            k++;
        /* Print the last column from the remaining columns */
            for (i = k; i < m; ++i) {
                System.out.print(a[i][n - 1]);
            }
            n--;
        /* Print the last row from the remaining rows */
            if (k < m) {
                for (i = n - 1; i >= l; --i) {
                    System.out.print(a[m - 1][i]);
                }
                m--;
            }
        /* Print the first column from the remaining columns */
            if (l < n) {
                for (i = m - 1; i >= k; --i) {
                    System.out.print(a[i][l]);
                }
                l++;
            }
        }
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
    //Time O(MlogN) since there are M rows and it takes 0(log(N)) time to search each one
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
    //we can improve above by binary search Split the grid into quadrants. Search the bottom left and the top right
    class Coordinate {
        public int row;
        public int column;
        public Coordinate(int r, int c) {
            row = r;
            column = c;
        }
        public boolean isBefore(Coordinate p) {
            return row <= p.row && column <= p.column;
        }
        public void setToAverage(Coordinate min, Coordinate max) {
            row = (min.row + max.row) / 2;
            column = (min.column + max.column) / 2;
        }
    }
    public Coordinate partitionAndSearch(int[][] matrix, Coordinate origin, Coordinate dest, Coordinate pivot, int x) {
        Coordinate lowerLeftOrigin = new Coordinate(pivot.row, origin.column);
        Coordinate lowerLeftDest = new Coordinate(dest.row, pivot.column - 1);
        Coordinate upperRightOrigin = new Coordinate(origin.row, pivot.column);
        Coordinate upperRightDest = new Coordinate(pivot.row - 1, dest.column);
        Coordinate lowerLeft = findElement(matrix, lowerLeftOrigin, lowerLeftDest, x);
        if (lowerLeft == null) {
            return findElement(matrix, upperRightOrigin, upperRightDest, x);
        }
        return lowerLeft;
    }
    public Coordinate findElement(int[][] matrix, Coordinate origin, Coordinate dest, int x) {
        if (matrix[origin.row][origin.column] == x) {
            return origin;
        } else if (!origin.isBefore(dest)) {
            return null;
        }
		/* Set start to start of diagonal and end to the end of the diagonal. Since
		 * the grid may not be square, the end of the diagonal may not equal dest.
		 */
        Coordinate start = origin;
        int diagDist = Math.min(dest.row - origin.row, dest.column - origin.column);
        Coordinate end = new Coordinate(start.row + diagDist, start.column + diagDist);
        Coordinate p = new Coordinate(0, 0);
		/* Do binary search on the diagonal, looking for the first element greater than x */
        while (start.isBefore(end)) {
            p.setToAverage(start, end);
            if (x > matrix[p.row][p.column]) {
                start.row = p.row + 1;
                start.column = p.column + 1;
            } else {
                end.row = p.row - 1;
                end.column = p.column - 1;
            }
        }
		/* Split the grid into quadrants. Search the bottom left and the top right. */
        return partitionAndSearch(matrix, origin, dest, start, x);
    }
    public Coordinate findElementWrap(int[][] matrix, int x) {
        Coordinate origin = new Coordinate(0, 0);
        Coordinate dest = new Coordinate(matrix.length - 1, matrix[0].length - 1);
        return findElement(matrix, origin, dest, x);
    }
    /*Given a 2D matrix(square or rectangular) print it in spiral way.
        e.g 1 2 3
            4 5 6
            7 8 9
     Printing should be 1 2 3 6 9 8 7 4 5 */
    public void print(int matrix[][]) {
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
}
