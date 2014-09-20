package code.ds;


import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.HashMap;
import java.util.*;

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
        G[x] ^= G[y];
        G[y] ^= G[x];
        G[x] ^= G[y];
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
            int max_so_far = a[0];
            int curr_max = a[0];
            for (int i = 1; i < a.length; i++)
            {
                curr_max = Math.max(a[i], curr_max+a[i]);
                max_so_far = Math.max(max_so_far, curr_max);
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
    //Given 3 arrays, pick 3 nos, one from each array, say a,b,c such that |a-b|+|b-c|+|c-a| is minimum
    private void findMinofabc(int a[],int b[],int c[])
    {
        quickSort(a,0,a.length);
        quickSort(b,0,a.length);
        quickSort(c,0,a.length);
        int min = Integer.MAX_VALUE;
        int i = 0, j = 0, k = 0;
        int index1=0,index2=0,index3=0;
        while( i < a.length && j < b.length && k < c.length)
        {
            int n = Math.abs(a[i]- b[j]) + Math.abs(b[j] - c[k])+ Math.abs(c[k] - a[i]);
            if(n < min)
            {
                min = n;
                index1 = i;
                index2 = j;
                index3 = k;
            }
            if(a[i] < b[j] && a[i] < c[k])
                i++;
            else if (b[j] < a[i] && b[j] < c[k])
                j++;
            else
                k++;
        }
        System.out.print(a[index1] + " " + b[index2] + " " + c[index3]);
    }
    //Given a sorted array with duplicates and a number, find the range in the
    //form of (startIndex, endIndex) of that number. find_range({0 2 3 3 3 10 10}, 3) should return (2,4).
    private void findRange(int a[], int num)
    {
        int startIndex = -1, endIndex = -1;
        boolean flag = true;
        if(a.length == 0)
            return;
        for(int i = 0; i<a.length; i++)
        {
            if(a[i] == num && flag)
            {
                startIndex = i;
                endIndex = i;
                flag = false;
            }
            else if(a[i] == num)
                endIndex = i;
        }
    }
    //Given an array arr[] of n integers, construct a Product Array prod[] (of same size)
    //such that prod[i] is equal to the product of all the elements of arr[] except arr[i].
    //Solve it without division operator and in O(n). e.g. [3, 1, 4, 2] => [8, 24, 6, 12]
    private int[] productArray(int a[])
    {
        int temp = 1;
        int [] prod = new int[a.length];
        for(int i =0; i<a.length;i++)
        {
            prod[i] = temp;
            temp *= a[i];
        }
        temp = 1;
        for(int i= a.length; i>=0;i--)
        {
            prod[i] *= temp;
            temp *= a[i];
        }
        return prod;
    }
    //Given a set S of n integers, are there elements a, b, c in S such that a + b + c = 0? Find all unique triplets in the set which gives the sum of zero.
    //For example, given set S = {-1 0 1 2 -1 -4}, One possible solution set is:  (-1, 0, 1)   (-1, 2, -1)
    HashSet<ArrayList<Integer>> find_triplets(int arr[]) {
        Arrays.sort(arr);
        HashSet<ArrayList<Integer>> triplets = new HashSet<ArrayList<Integer>>();
        ArrayList<Integer> triplet = new ArrayList<Integer>();
        int n = arr.length;
        for (int i = 0;i < n; i++) {
            int j = i + 1;
            int k = n - 1;
            while (j < k) {
                int sum_two = arr[i] + arr[j];
                if (sum_two + arr[k] < 0) {
                    j++;
                } else if (sum_two + arr[k] > 0) {
                    k--;
                } else {
                    triplet.set(0,arr[i]);
                    triplet.set(1,arr[j]);
                    triplet.set(2,arr[k]);
                    if(!triplets.contains(triplet))
                        triplets.add(triplet);
                    j++;
                    k--;
                }
            }
        }
        return triplets;
    }
    //Given three arrays sorted in non-decreasing order, print all common elements in these arrays.
    //e.g.ar1[] = {1, 5, 10, 20, 40, 80} ar2[] = {6, 7, 20, 80} ar3[] = {3, 4, 15, 20, 30, 80} Output: 20, 80
    void findCommon(int ar1[], int ar2[], int ar3[], int n1, int n2, int n3)
    {
        // Initialize starting indexes for ar1[], ar2[] and ar3[]
        int i = 0, j = 0, k = 0;
        // Iterate through three arrays while all arrays have elements
        while (i < n1 && j < n2 && k < n3)
        {
            // If x = y and y = z, print any of them and move ahead in all arrays
            if (ar1[i] == ar2[j] && ar2[j] == ar3[k])
            {   System.out.print(ar1[i]);   i++; j++; k++; }
            // x < y
            else if (ar1[i] < ar2[j])
                i++;
                // y < z
            else if (ar2[j] < ar3[k])
                j++;
                // We reach here when x > y and z < y, i.e., z is smallest
            else
                k++;
        }
    }
}

