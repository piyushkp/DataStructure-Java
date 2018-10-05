package code.ds;


import java.io.*;
import java.util.*;

/* The outpur should look like this

[
  []
  [1, 2, 3, 4]
  [5, 6]
  []
  [7]
  [8, 9, 10]
  []
]
[
  []
  [10, 9, 8, 7]
  [6, 5]
  []
  [4]
  [3, 2, 1]
  []
]

*/

class Solution {
  public static void main(String[] argv) {
    int[][] arrayOfArrays = {
        {},
        {1,2,3,4,5},
        {6},
        {7},
        {8,9},
        {}
    };
    print(arrayOfArrays);
    reverse(arrayOfArrays);
    print(arrayOfArrays);

    reverse1(new int[] {1,2,3,4,5,6,7});
  }

  private static void print(int[][] arrayOfArrays) {
    System.out.println("[");
    for(int[] subList : arrayOfArrays) {
      System.out.print("\t[");
      for(int i = 0; i < subList.length; i++) {
        if(i > 0) {
          System.out.print(", ");
        }
        System.out.print(subList[i]);
      }
      System.out.println("]");
    }
    System.out.println("]");
  }

  private static void reverse(int[][] aoa) {
    // IMPLEMENT THIS
    // You must do the reverse in-place meaning you can only
    // use fixed-size temporary variables.
    int bottomRow = aoa.length -1;
    int bottomCol = aoa[bottomRow].length;
    for(int i =0; i< aoa.length;i++){
      bottomRow -= i;
      if(bottomRow == i){
        reverse1(aoa[bottomRow]);
      }else {
        if (bottomRow >= 0) {
          for (int j = 0; j < aoa[i].length; j++) {
            bottomCol = aoa[bottomRow].length - 1 - j;
            if (bottomCol >= 0) {
              int temp = aoa[i][j];
              aoa[i][j] = aoa[bottomRow][bottomCol];
              aoa[bottomRow][bottomCol] = temp;
            } else {
              if (--bottomRow >= 0 && aoa[bottomRow].length - 1 >= 0) {
                bottomCol = aoa[bottomRow].length - 1;
                int temp = aoa[i][j];
                aoa[i][j] = aoa[bottomRow][bottomCol];
                aoa[bottomRow][bottomCol] = temp;
              }
            }

          }
        }
      }
    }

  }
  // (0,0) (6,0)
  private static void reverse1(int[] input){

    if(input == null || input.length <= 1){
      return;
    }

    for(int i=0;i<input.length/2;i++){
      int temp = input[i];
      input[i] = input[input.length - i - 1];
      input[input.length- i - 1] = temp;
    }

    //System.out.println(Arrays.toString(input));
  }

}

/* The outpur should look like this

[
  []
  [1, 2, 3, 4]
  [5, 6]
  []
  [7]
  [8, 9, 10]
  []
]
[
  []
  [10, 9, 8, 7]
  [6, 5]
  []
  [4]
  [3, 2, 1]
  []
]

*/
