package code.ds;

/**
 * Created by Piyush Patel.
 */
public class Array
{

    //Merge two sorted array into sorted array
    public int[] MergeArray(int[] a, int[] b)
    {
        int[] answer = new int[a.length + b.length];
        int i = 0, j = 0, k = 0;

        while (i < a.length && j < b.length)
        {
            if (a[i] < b[j])
                answer[k++] = a[i++];
            else
                answer[k++] = b[j++];
        }
        while (i < a.length)
            answer[k++] = a[i++];
        while (j < b.length)
            answer[k++] = b[j++];

        return answer;
    }

    //Find the k-th Smallest Element in the Union of Two Sorted Arrays
    // Time Complexity :  O(logk)
    public int findKthElement(int k, int[] array1, int start1, int end1, int[] array2, int start2, int end2)
    {
        // if (k>m+n) exception
        if (k == 0)
        {
            return Math.min(array1[start1], array2[start2]);
        }
        if (start1 == end1)
        {
            return array2[k];
        }
        if (start2 == end2)
        {
            return array1[k];
        }
        int mid = k / 2;
        int sub1 = Math.min(mid, end1 - start1);
        int sub2 = Math.min(mid, end2 - start2);
        if (array1[start1 + sub1] < array2[start2 + sub2])
        {
            return findKthElement(k - mid, array1, start1 + sub1, end1, array2, start2, end2);
        }
        else
        {
            return findKthElement(k - mid, array1, start1, end1, array2, start2 + sub2, end2);
        }
    }

    //Given two unsorted int arrays, find the kth smallest element in the merged, sorted array.
    private void MergeUnsortedArray(int[] A1, int[] A2)
    {
        int[] c = new int[A1.length + A2.length];
        int length = 0;
        for (int i = 0; i < A1.length; i++)
        {
            c[i] = A1[i];
            length++;
        }
        for (int j = 0; j < A2.length; j++)
        {
            c[length + j + 1] = A2[j];
        }
        quickselect(c, 0, c.length, 3);
    }
    private int quickselect(int[] G, int first, int last, int k)
    {
        if (first <= last)
        {
            int pivot = partition(G, first, last);
            if (pivot == k)
            {
                return G[k];
            }
            if (pivot > k)
            {
                return quickselect(G, first, pivot - 1, k);
            }
            else
                return quickselect(G, pivot + 1, last, k);
        }
        return 0;
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
        int tmp = G[x];
        G[x] = G[y];
        G[y] = tmp;
    }

     //Given an array of 1s and 0s which has all 1s first followed by all 0s. Find the number of 0s. Count the number of zeroes in the given array.
    // A wrapper over recursive function firstZero()
    int countOnes(int[] arr, int n)
    {
        // Find index of first zero in given array
        int first = firstZero(arr, 0, n - 1);

        // If 0 is not present at all, return 0
        if (first == -1)
            return 0;

        return (n - first);
    }

    /* if 0 is present in arr[] then returns the index of FIRST occurrence
    of 0 in arr[low..high], otherwise returns -1.  Time Complexity: O(Logn)*/
    int firstZero(int[] arr, int low, int high)
    {
        if (high >= low)
        {
            // Check if mid element is first 0
            int mid = low + (high - low) / 2;
            if ((mid == 0 || arr[mid - 1] == 1) && arr[mid] == 0)
                return mid;

            if (arr[mid] == 1)  // If mid element is not 0
                return firstZero(arr, (mid + 1), high);
            else  // If mid element is 0, but not first 0
                return firstZero(arr, low, (mid - 1));
        }
        return -1;
    }
}

