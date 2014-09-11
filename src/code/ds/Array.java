package code.ds;

import java.util.HashMap;

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
    //find the sum of contiguous sub array within a one-dimensional array of numbers which has the largest sum.
    private int maxSubArraySum(int a[])
    {
        int max_so_far = 0, max_ending_here = 0;
        int i;
        for(i = 0; i < a.length; i++)
        {
            max_ending_here = max_ending_here + a[i];
            if(max_ending_here < 0)
                max_ending_here = 0;
     /* Do not compare for all elements. Compare only
        when  max_ending_here > 0 */
            else if (max_so_far < max_ending_here)
                max_so_far = max_ending_here;
        }
        return max_so_far;
    }
    //Given an array that contains both positive and negative integers, find the product of the maximum product subarray.
    int maxSubarrayProduct(int arr[])
    {
        // max positive product ending at the current position
        int max_ending_here = 1;
        // min negative product ending at the current position
        int min_ending_here = 1;
        // Initialize overall max product
        int max_so_far = 1;
    /* Traverse throught the array. Following values are maintained after the ith iteration:
       max_ending_here is always 1 or some positive product ending with arr[i]
       min_ending_here is always 1 or some negative product ending with arr[i] */
        for (int i = 0; i < arr.length; i++)
        {
        /* If this element is positive, update max_ending_here. Update
           min_ending_here only if min_ending_here is negative */
            if (arr[i] > 0)
            {
                max_ending_here = max_ending_here*arr[i];
                min_ending_here = Math.min(min_ending_here * arr[i], 1);
            }
        /* If this element is 0, then the maximum product cannot
           end here, make both max_ending_here and min_ending_here 0
           Assumption: Output is alway greater than or equal to 1. */
            else if (arr[i] == 0)
            {
                max_ending_here = 1;
                min_ending_here = 1;
            }
        /* If element is negative. This is tricky
           max_ending_here can either be 1 or positive. min_ending_here can either be 1
           or negative.
           next min_ending_here will always be prev. max_ending_here * arr[i]
           next max_ending_here will be 1 if prev min_ending_here is 1, otherwise
           next max_ending_here will be prev min_ending_here * arr[i] */
            else
            {
                int temp = max_ending_here;
                max_ending_here = Math.max (min_ending_here * arr[i], 1);
                min_ending_here = temp * arr[i];
            }
            // update max_so_far, if needed
            if (max_so_far <  max_ending_here)
                max_so_far  =  max_ending_here;
        }
        return max_so_far;
    }
    //Write a program to find the element in an array that is repeated more than half number of times.
    // Return -1 if no such element is found.
    private int MoreThanHalfElem(int a[],int n)
    {
        int ln = a.length;
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < a.length; i++) {
            int freq = 0;
            if (map.get(a[i]) == null) {
                freq = 1;
                map.put(a[i], 1);
            } else {
                freq = map.get(a[i]);
                freq++;
                map.put(a[i], freq);
            }
            if (freq > (a.length / 2)) {
                return a[i];
            }
        }
        return  -1;
    }
    //Searching an Element in a Rotated Sorted Array
    private int rotated_binary_search(int A[], int N, int key) {
        int L = 0;
        int R = N - 1;
        while (L <= R) {
            // Avoid overflow, same as M=(L+R)/2
            int M = L + ((R - L) / 2);
            // the bottom half is sorted
            if (A[L] <= A[M]) {
                if (A[L] <= key && key < A[M])
                    R = M - 1;
                else
                    L = M + 1;
            }
            // the upper half is sorted
            else {
                if (A[M] < key && key <= A[R])
                    L = M + 1;
                else
                    R = M - 1;
            }
        }
        return -1;
    }
}

