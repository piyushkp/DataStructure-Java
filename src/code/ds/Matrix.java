package code.ds;
import java.text.*;
/**
 * Created by Piyush Patel.
 */
public class Matrix {
    //Matrix Region Sum
    private int[][] matrix;
    private long[][] sumMatrix;
    public void MatrixRegionSum(int[][] matrix) {
        if (matrix == null) {
            throw new NullPointerException("null matrix is not allowed.");
        }
        this.matrix = matrix;
        this.sumMatrix = new long[matrix.length][];
        preComputeSums();
    }
    private void preComputeSums() {
        for (int col = 0; col < matrix[0].length; col++) {
            sumMatrix[0][col] += matrix[0][col];
        }
        for (int row = 1; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                sumMatrix[row][col] = sumMatrix[row - 1][col] + matrix[row][col];
            }
        }
        for (int row = matrix.length - 1; row >= 0; row--) {
            for (int col = 1; col < matrix[0].length; col++) {
                sumMatrix[row][col] += sumMatrix[row][col - 1];
            }
        }
    }
    // (lx, ly) is the top left co-ordinate of the rectangle.
    // (rx, ry) is the bottom right co-ordinate of the rectangle.
    public long findSum(int lx, int ly, int rx, int ry) {
        if (!valid(lx, ly) || !valid(rx, ry)) {
            throw new IllegalArgumentException("The co-ordinates: are not valid co-ordinates.");
        }
        long sum = sumMatrix[rx][ry];
        sum -= (ly == 0 ? 0 : sumMatrix[rx][ly - 1]);
        sum -= (lx == 0 ? 0 : sumMatrix[lx - 1][ry]);
        sum += (lx == 0 || ly == 0 ? 0 : sumMatrix[lx - 1][ly - 1]);
        return sum;
    }
    public boolean valid(int x, int y) {
        return x >= 0 && x < matrix.length && y >= 0 && y < matrix[0].length;
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


}
