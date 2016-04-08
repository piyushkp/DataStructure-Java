package code.ds;
import java.text.*;
/**
 * Created by Piyush Patel.
 */
public class Matrix {
    //Matrix Region Sum
    // Function to preprcess input mat[M][N].  This function mainly fills aux[M][N] such that aux[i][j] stores sum
    // of elements from (0,0) to (i,j) Time = O(MN)
    void preProcess(int mat[][], int aux[][], int M, int N){
        // Copy first row of mat[][] to aux[][]
        for (int i=0; i<N; i++)
            aux[0][i] = mat[0][i];
        // Do column wise sum
        for (int i=1; i<M; i++)
            for (int j=0; j<N; j++)
                aux[i][j] = mat[i][j] + aux[i-1][j];
        // Do row wise sum
        for (int i=0; i<M; i++)
            for (int j=1; j<N; j++)
                aux[i][j] += aux[i][j-1];
    }
    // A O(1) time function to compute sum of submatrix between (tli, tlj) and (rbi, rbj) using aux[][]
    // which is built by the preprocess function
    int sumQuery(int aux[][], int tli, int tlj, int rbi, int rbj){
        // result is now sum of elements between (0, 0) and rbi, rbj)
        int res = aux[rbi][rbj];
        // Remove elements between (0, 0) and (tli-1, rbj)
        if (tli > 0)
            res = res - aux[tli-1][rbj];
        // Remove elements between (0, 0) and (rbi, tlj-1)
        if (tlj > 0)
            res = res - aux[rbi][tlj-1];
        // Add aux[tli-1][tlj-1] as elements between (0, 0) and (tli-1, tlj-1) are subtracted twice
        if (tli > 0 && tlj > 0)
            res = res + aux[tli-1][tlj-1];
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
    public static void rotate(int[][] matrix, int n){
        for(int layer = 0; layer < n/2; layer++){
            int first = layer;
            int last = n - 1 - layer;
            for(int i = first; i < last; i++){
                int offset = i - first;
                int top = matrix[first][i]; // save top
                //left -> top
                matrix[first][i] = matrix[last-offset][first];
                //bottom -> left
                matrix[last-offset][first]  = matrix[last][last - offset];
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
}
