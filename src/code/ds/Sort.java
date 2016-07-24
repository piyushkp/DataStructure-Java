package code.ds;

/**
 * Created by Piyush Patel.
 */
public class Sort {
    public static void main(String [] args) {
        System.out.print("Sort");
    }
    //Merge Sort
    public static void mergeSort(int[ ] data, int first, int n)
    {
        int n1; // Size of the first half of the array
        int n2; // Size of the second half of the array
        if (n > 1)
        {
            // Compute sizes of the two halves
            n1 = n / 2;
            n2 = n - n1;
            mergeSort(data, first, n1);      // Sort data[first] through data[first+n1-1]
            mergeSort(data, first + n1, n2); // Sort data[first+n1] to the end
            // Merge the two sorted halves.
            merge(data, first, n1, n2);
        }
    }
    private static void merge(int[ ] data, int first, int n1, int n2)
    {
        int[ ] temp = new int[n1+n2]; // Allocate the temporary array
        int copied  = 0; // Number of elements copied from data to temp
        int copied1 = 0; // Number copied from the first half of data
        int copied2 = 0; // Number copied from the second half of data
        int i;           // Array index to copy from temp back into data

        // Merge elements, copying from two halves of data to the temporary array.
        while ((copied1 < n1) && (copied2 < n2))
        {
            if (data[first + copied1] < data[first + n1 + copied2])
                temp[copied++] = data[first + (copied1++)];
            else
                temp[copied++] = data[first + n1 + (copied2++)];
        }
        // Copy any remaining entries in the left and right subarrays.
        while (copied1 < n1)
            temp[copied++] = data[first + (copied1++)];
        while (copied2 < n2)
            temp[copied++] = data[first + n1 + (copied2++)];
        // Copy from temp back to the data array.
        for (i = 0; i < n1+n2; i++)
            data[first + i] = temp[i];
    }
    //Quick sort
    public void quickSort(int[] array, int startIdx, int endIdx) {
        int idx = partition(array, startIdx, endIdx);
        // Recursively call quicksort with left part of the partitioned array
        if (startIdx < idx - 1) {
            quickSort(array, startIdx, idx - 1);
        }
        // Recursively call quick sort with right part of the partitioned array
        if (endIdx > idx) {
            quickSort(array, idx, endIdx);
        }
    }
    private int partition(int[] G, int first, int last)
    {
        int pivot = G[last];
        int pIndex = first;
        for (int i = first; i < last; i++)
        {
            if (G[i] < pivot)
            {
                swap(G, i, pIndex);
                pIndex++;
            }
        }
        swap(G, pIndex, last);
        return pIndex;
    }
    private void swap(int[] G, int x, int y)
    {
        G[x] ^= G[y];
        G[y] ^= G[x];
        G[x] ^= G[y];
    }
    //insertion sort. Insertion Sort is one such online algorithm that sorts the data appeared so far.
    public static int[] doInsertionSort(int[] input){
        int temp;
        for (int i = 1; i < input.length; i++) {
            for(int j = i ; j > 0 ; j--){
                if(input[j] < input[j-1]){
                    temp = input[j];
                    input[j] = input[j-1];
                    input[j-1] = temp;
                }
            }
        }
        return input;
    }
}
