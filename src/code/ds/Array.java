package code.ds;
import com.sun.deploy.util.ParameterUtil;
import com.sun.javafx.scene.layout.region.Margins;
import com.sun.org.apache.xpath.internal.operations.Bool;
import java.util.HashMap;
import java.util.*;
/**
 * Created by Piyush Patel.
 */
public class Array {
    public static void main(String [] args)
    {
        int a[] = {1,2,3};
        int b[] = {4,5,6};
        int ans [] = MergeArray(a,b);
    }
    //Merge two sorted array into sorted array Time = O(N+M)
    public static int[] MergeArray(int[] a, int[] b) {
        int[] answer = new int[a.length + b.length];
        int i = 0, j = 0, k = 0;
        while (i < a.length && j < b.length) {
            if (a[i] < b[j]) answer[k++] = a[i++];
            else answer[k++] = b[j++];
        }
        while (i < a.length) answer[k++] = a[i++];
        while (j < b.length) answer[k++] = b[j++];
        return answer;
    }
    //You are given two sorted arrays, A and B, where A has a large enough buffer at the end to hold B. Write a method
    //to merge B into A in sorted order.
    public static void merge(int[] a, int[] b, int lastA, int lastB) {
        int indexMerged = lastB + lastA - 1; /* Index of last location of merged array */
        int indexA = lastA - 1; /* Index of last element in array b */
        int indexB = lastB - 1; /* Index of last element in array a */
		/* Merge a and b, starting from the last element in each */
        while (indexB >= 0) {
            if (indexA >= 0 && a[indexA] > b[indexB]) { /* end of a is bigger than end of b */
                a[indexMerged] = a[indexA]; // copy element
                indexA--;
            } else {
                a[indexMerged] = b[indexB]; // copy element
                indexB--;
            }
            indexMerged--; // move indices
        }
    }
    //There are 2 sorted arrays A and B of size n each. Write an algorithm to find the median of the array obtained
    //after merging the above 2 arrays(i.e. array of length 2n). The complexity should be O(log(n))
    public static double findMedian_ofTwoSortedArray(int a[], int b[],int start_a, int end_a, int start_b,int end_b)
    {
        if((end_a -start_a == 1) && (end_b - start_b == 1))
            return (1 * (Math.max(a[start_a],b[start_b])) + Math.min(a[end_a],b[end_b])) /2;
        int m1_index = (start_a + end_a) /2;
        int m2_index = (start_b + end_b) /2;
        int m1 = a[m1_index];
        int m2 = b[m2_index];
        if(m1 == m2)
            return m2;
        if(m1 < m2){
            start_a = m1_index;
            end_b = m2_index;
        }
        else{
            start_b = m2_index;
            end_a = m1_index;
        }
        return findMedian_ofTwoSortedArray(a,b,start_a,end_a,start_b,end_b);
    }

    //Find the second largest number from the array
    static int  secondlargest(int[] a){
        int largest = a[0];
        int secondlargest = 0;
        for (int i = 1; i < a.length; i++){
            int number = a[i];
            if (number > largest){
                secondlargest = largest;
                largest = number;
            }
            else{
                if (number > secondlargest)
                    secondlargest = number;
            }
        }
        return secondlargest;
    }
    //Find the k-th Smallest Element in the Union of Two Sorted Arrays
    // Time Complexity :  O(log (m + n))
    //http://www.lifeincode.net/programming/leetcode-median-of-two-sorted-arrays-java/
    public int findKSmallestElement(int A[], int startA, int endA, int B[], int startB, int endB, int k) {
        int n = endA - startA;
        int m = endB - startB;
        if (n <= 0)
            return B[startB + k - 1];
        if (m <= 0)
            return A[startA + k - 1];
        if (k == 1)
            return A[startA] < B[startB] ? A[startA] : B[startB];
        int midA = (startA + endA) / 2;
        int midB = (startB + endB) / 2;
        if (A[midA] <= B[midB]) {
            if (n / 2 + m / 2 + 1 >= k)
                return findKSmallestElement(A, startA, endA, B, startB, midB, k);
            else
                return findKSmallestElement(A, midA + 1, endA, B, startB, endB, k - n / 2 - 1);
        } else {
            if (n / 2 + m / 2 + 1 >= k)
                return findKSmallestElement(A, startA, midA, B, startB, endB, k);
            else
                return findKSmallestElement(A, startA, endA, B, midB + 1, endB, k - m / 2 - 1);
        }
    }
    //Given two unsorted int arrays with elements are distinct, find the kth smallest element in the merged, sorted array.
    // Average case Time = O(n) Worst case O(n2) where n is total length of A1 and A2
    private void MergeUnsortedArray(int[] A1, int[] A2, int K) {
        int[] c = new int[A1.length + A2.length];
        int length = 0;
        for (int i = 0; i < A1.length; i++) {
            c[i] = A1[i];
            length++;
        }
        for (int j = 0; j < A2.length; j++) {
            c[length + j + 1] = A2[j];
        }
        quickselect(c, 0, c.length, K);
    }
    private int quickselect(int[] G, int first, int last, int k) {
        if (first <= last) {
            int pivot = partition(G, first, last);
            if (pivot == k) {
                return G[k];
            }
            if (pivot > k) {
                return quickselect(G, first, pivot - 1, k);
            } else return quickselect(G, pivot + 1, last, k);
        }
        return 0;
    }
    // Picks a random pivot element between l and r and partitions
    int randomPartition(int arr[], int l, int r){
        int n = r-l;
        int pivot = (int)(Math.random()) % n;
        swap(arr, pivot, r);
        return partition(arr, l, r);
    }
    private int partition(int[] G, int first, int last) {
        int pivot = G[last];
        int pIndex = first;
        for (int i = first; i < last; i++) {
            if (G[i] < pivot) {
                swap(G, i, pIndex);
                pIndex++;
            }
        }
        swap(G, pIndex, last);
        return pIndex;
    }
    private void swap(int[] G, int x, int y) {
        G[x] ^= G[y];
        G[y] ^= G[x];
        G[x] ^= G[y];
    }
    //Find k maximum integers from an array of infinite integers. Find first 100 maximum numbers from billion numbers
    // Time Complexity: O(k + (n-k)Logk) without sorted output.If sorted output is needed then O(k + (n-k)Logk + kLogk)
    // (assumption is that k is significantly lesser than n)
    public static int[] getTopElements(int[] arr, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();
        for (int i = 0; i < arr.length; ++i) {
            int currentNum = arr[i];
            if (minHeap.size() < k) minHeap.add(currentNum);
            else if (currentNum > minHeap.peek()) {
                minHeap.poll();
                minHeap.add(currentNum);
            }
        }
        int[] result = new int[minHeap.size()];
        int index = 0;
        while (!minHeap.isEmpty()) {
            result[index++] = minHeap.poll();
        }
        return result;
    }
    //Given an array of 1s and 0s which has all 1s first followed by all 0s. Find the number of 0s.
    // Count the number of zeroes in the given array.
    int countOnes(int[] arr, int n) {
        // Find index of first zero in given array
        int first = firstZero(arr, 0, n - 1);
        // If 0 is not present at all, return 0
        if (first == -1) return 0;
        return (n - first);
    }
    /* if 0 is present in arr[] then returns the index of FIRST occurrence
    of 0 in arr[low..high], otherwise returns -1.  Time Complexity: O(Logn)*/
    int firstZero(int[] arr, int low, int high) {
        if (high >= low) {
            // Check if mid element is first 0
            int mid = low + (high - low) / 2;
            if ((mid == 0 || arr[mid - 1] == 1) && arr[mid] == 0) return mid;
            if (arr[mid] == 1)  // If mid element is not 0
                return firstZero(arr, (mid + 1), high);
            else  // If mid element is 0, but not first 0
                return firstZero(arr, low, (mid - 1));
        }
        return -1;
    }
    //Given an unsorted array of nonnegative integers, find a continous subarray which adds to a given number.
    //arr[] = {1, 4, 20, 3, 10, 5}, sum = 33  output = indexes 2 and 4
    // Time  =O(2n)
    int subArraySum(int arr[], int n, int sum){
    /* Initialize curr_sum as value of first element
       and starting point as 0 */
        int curr_sum = arr[0], start = 0, i;
    /* Add elements one by one to curr_sum and if the curr_sum exceeds the sum, then remove starting element */
        for (i = 1; i <= n; i++){
            // If curr_sum exceeds the sum, then remove the starting elements
            while (curr_sum > sum && start < i-1){
                curr_sum = curr_sum - arr[start];
                start++;
            }
            // If curr_sum becomes equal to sum, then return true
            if (curr_sum == sum){
                System.out.println ("Sum found between indexes "+ start + " and "+ (i-1));
                return 1;
            }
            // Add this element to curr_sum
            if (i < n)
                curr_sum = curr_sum + arr[i];
        }
        return 0;
    }
    //find the sum of contiguous sub array within a one-dimensional array of numbers with negative which has the largest sum .
    // input {-2, -3, 4, -1, -2, 1, 5, -3} output = 7
    private int maxSubArraySum(int a[]) {
        int max_so_far = a[0];
        int curr_max = a[0];
        for (int i = 1; i < a.length; i++) {
            curr_max = Math.max(a[i], curr_max + a[i]);
            max_so_far = Math.max(max_so_far, curr_max);
        }
        return max_so_far;
    }
    //find the length of longest increasing subarray
    //{1,3,2,4,5} output = 3
    int findlen(int[] a){
        int min = a[0];
        int max_len = 1;
        int count = 1;
        for (int i = 1; i < a.length; i++){
            if (a[i] > min){
                count++;
            }
            else {
                max_len = Math.max(max_len, count);
                count = 1;
            }
            min = a[i];
        }
        max_len = Math.max(max_len, count);
        return max_len;
    }
    //Given an array, describe an algorithm to identify the subarray with the maximum sum.
    private static int[] findMaxSumIndex(int[] arr){
        int[] result = new int[3];
        int maxSumTillNow = Integer.MIN_VALUE;
        int tempStartIndex = 0;
        int tempSum = 0;
        for (int i = 0; i < arr.length; i++) {
            tempSum = tempSum + arr[i];
            if(tempSum > maxSumTillNow){
                maxSumTillNow = tempSum;
                result[0] = tempStartIndex; // start index
                result[1] = i;              // end index
                result[2] = maxSumTillNow;  // largest sum
            }
            if(tempSum<0){
                tempSum = 0;
                tempStartIndex = i + 1;
            }
        }
        return result;
    }
    //Given an array that contains both positive and negative integers, find the maximum product of elements of subarray.
    int maxSubarrayProduct(int arr[]) {
        // max positive product ending at the current position
        int max_ending_here = 1;
        // min negative product ending at the current position
        int min_ending_here = 1;
        // Initialize overall max product
        int max_so_far = 1;
    /* max_ending_here is always 1 or some positive product ending with arr[i]
       min_ending_here is always 1 or some negative product ending with arr[i] */
        for (int i = 0; i < arr.length; i++) {
        /* If this element is positive, update max_ending_here. Update
           min_ending_here only if min_ending_here is negative */
            if (arr[i] > 0) {
                max_ending_here = max_ending_here * arr[i];
                min_ending_here = Math.min(min_ending_here * arr[i], 1);
            }
        /* If this element is 0, then the maximum product cannot
           end here, make both max_ending_here and min_ending_here 0
           Assumption: Output is always greater than or equal to 1. */
            else if (arr[i] == 0) {
                max_ending_here = 1;
                min_ending_here = 1;
            }
        /* If element is negative. This is tricky
           max_ending_here can either be 1 or positive. min_ending_here can either be 1
           or negative.
           next min_ending_here will always be prev. max_ending_here * arr[i]
           next max_ending_here will be 1 if prev min_ending_here is 1, otherwise
           next max_ending_here will be prev min_ending_here * arr[i] */
            else {
                int temp = max_ending_here;
                max_ending_here = Math.max(min_ending_here * arr[i], 1);
                min_ending_here = temp * arr[i];
            }
            // update max_so_far, if needed
            if (max_so_far < max_ending_here) max_so_far = max_ending_here;
        }
        return max_so_far;
    }
    //Write a program to find the element in an array that is repeated more than half number of times.
    // Return -1 if no such element is found. Time complexity = O(n), aux space O(1)
    int MoreThanHalfElem(int a[]){
        /* Find the candidate for Majority*/
        int cand = findCandidate(a);
        /* Print the candidate if it is Majority*/
        if(isMajority(a, cand))
            return cand;
        return -1;
    }
    /* Function to find the candidate for Majority, based on Moore�s Voting Algorithm.*/
    int findCandidate(int a[]){
        int maj_index = 0, count = 1;
        int i;
        for(i = 1; i < a.length; i++) {
            if(a[maj_index] == a[i])
                count++;
            else
                count--;
            if(count == 0){
                maj_index = i;
                count = 1;
            }
        }
        return a[maj_index];
    }
    /* Function to check if the candidate occurs more than n/2 times */
    boolean isMajority(int a[], int cand) {
        int i, count = 0;
        for (i = 0; i < a.length; i++)
            if(a[i] == cand)
                count++;
        if (count > a.length/2)
            return true;
        else
            return false;
    }
    /* Rotate elements in an array to left k times */
    static void leftRotate(int arr[], int k){
        int n = arr.length;
        rvereseArray(arr, 0, k-1);
        rvereseArray(arr, k, n-1);
        rvereseArray(arr, 0, n-1);
    }
    static void rightRotate(int arr[], int k){
        int n = arr.length;
        rvereseArray(arr, 0, n-k-1);
        rvereseArray(arr, n-k, n-1);
        rvereseArray(arr, 0, n-1);
    }
    /*Function to reverse arr[] from index start to end*/
    static void rvereseArray(int arr[], int start, int end){
        while (start < end){
            arr[start] ^= arr[end];
            arr[end]  ^= arr[start];
            arr[start] ^= arr[end];
            start++;
            end--;
        }
    }
    //Searching an Element in a Rotated Sorted Array
    private int rotated_binary_search(int A[], int N, int key) {
        int L = 0;
        int R = N - 1;
        while (L <= R) {
            // Avoid overflow, same as M=(L+R)/2
            int M = L + ((R - L) / 2);
            if(A[M] == key) return M;
            // the bottom half is sorted
            if (A[L] <= A[M]) {
                if ( key >= A[L] && key < A[M]) R = M - 1;
                else L = M + 1;
            }
            // the upper half is sorted
            else {
                if (key > A[M] && key <= A[R]) L = M + 1;
                else R = M - 1;
            }
        }
        return -1;
    }
    //Given a sorted array of n integers with duplicates that has been rotated an unknown number of times, write code to find an element
    //in the array. You may assume that the array was originally sorted in increasing order. worst case O(N)
    public static int searchRotatedUnknowntimes(int a[], int left, int right, int x) {
        int mid = (left + right) / 2;
        if (x == a[mid]) { // Found element
            return mid;
        }
        if (right < left) {
            return -1;
        }
        if (a[left] < a[mid]) { // Left is normally ordered.
            if (x >= a[left] && x <= a[mid]) {
                return searchRotatedUnknowntimes(a, left, mid - 1, x);
            } else {
                return searchRotatedUnknowntimes(a, mid + 1, right, x);
            }
        } else if (a[mid] < a[left]) { // Right is normally ordered.
            if (x >= a[mid] && x <= a[right]) {
                return searchRotatedUnknowntimes(a, mid + 1, right, x);
            } else {
                return searchRotatedUnknowntimes(a, left, mid - 1, x);
            }
        } else if (a[left] == a[mid]) { // Left is either all repeats OR loops around (with the right half being all dups)
            if (a[mid] != a[right]) { // If right half is different, search there
                return searchRotatedUnknowntimes(a, mid + 1, right, x);
            } else { // Else, we have to search both halves
                int result = searchRotatedUnknowntimes(a, left, mid - 1, x);
                if (result == -1) {
                    return searchRotatedUnknowntimes(a, mid + 1, right, x);
                } else {
                    return result;
                }
            }
        }
        return -1;
    }
    //Find the minimum element in a sorted and rotated array. Assumes that all elements are distinct.Time O(logn)
    //Input: {5, 6, 1, 2, 3, 4}     Output: 1
    private int findMin(int arr[], int low, int high){
        // If there is only one element left
        if (high == low) return arr[low];
        // Find mid
        int mid = low + (high - low)/2; /*(low + high)/2;*/
        // Check if element (mid+1) is minimum element. Consider the cases like {3, 4, 5, 1, 2}
        if (mid < high && arr[mid+1] < arr[mid])
            return arr[mid+1];
        // Check if mid itself is minimum element
        if (mid > low && arr[mid] < arr[mid - 1])
            return arr[mid];
        // Decide whether we need to go to left half or right half
        if (arr[high] > arr[mid])
            return findMin(arr, low, mid - 1);
        return findMin(arr, mid + 1, high);
    }
    // The function that handles duplicates.  It can be O(n) in worst case.
    //Input: {3, 3, 3, 4, 4, 4, 4, 5, 3, 3} output = 3
    static int findMinDuplicate(int arr[], int low, int high){
        // This condition is needed to handle the case when array is not rotated at all
        if (high < low)  return arr[0];
        // If there is only one element left
        if (high == low) return arr[low];
        // Find mid
        int mid = low + (high - low)/2; /*(low + high)/2;*/
        // Check if element (mid+1) is minimum element. Consider the cases like {1, 1, 0, 1}
        if (mid < high && arr[mid + 1] < arr[mid])
            return arr[mid+1];
        // This case causes O(n) time
        if (arr[low] == arr[mid] && arr[high] == arr[mid])
            return Math.min(findMinDuplicate(arr, low, mid - 1), findMinDuplicate(arr, mid + 1, high));
        // Check if mid itself is minimum element
        if (mid > low && arr[mid] < arr[mid - 1])
            return arr[mid];
        // Decide whether we need to go to left half or right half
        if (arr[high] > arr[mid])
            return findMinDuplicate(arr, low, mid - 1);
        return findMinDuplicate(arr, mid + 1, high);
    }
    //Given 3 arrays, pick 3 nos, one from each array, say a,b,c such that |a-b|+|b-c|+|c-a| is minimum
    private void findMinOfabc(int a[], int b[], int c[]) {
        //quickSort(a, 0, a.length);
        //quickSort(b, 0, b.length);
        //quickSort(c, 0, c.length);
        int min = Integer.MAX_VALUE;
        int i = 0, j = 0, k = 0;
        int index1 = 0, index2 = 0, index3 = 0;
        while (i < a.length && j < b.length && k < c.length) {
            int n = Math.abs(a[i] - b[j]) + Math.abs(b[j] - c[k]) + Math.abs(c[k] - a[i]);
            if (n < min) {
                min = n;
                index1 = i;
                index2 = j;
                index3 = k;
            }
            if (a[i] < b[j] && a[i] < c[k]) i++;
            else if (b[j] < a[i] && b[j] < c[k]) j++;
            else k++;
        }
        System.out.print(a[index1] + " " + b[index2] + " " + c[index3]);
    }
    //Given a sorted array with duplicates and a positive number, find the range in the
    //form of (startIndex, endIndex) of that number. find_range({0 2 3 3 3 10 10}, 3) should return (2,4).Time = O(logn)
    private int[] findRange(int a[], int num) {
        ;
        int i; // index of first occurrence of x in arr[0..n-1]
        int j; // index of last occurrence of x in arr[0..n-1]
        /* get the index of first occurrence of x */
        i = first(a, 0, a.length-1, num, a.length);
        /* If x doesn't exist in arr[] then return -1 */
        if(i == -1)
            return null;
        /* Else get the index of last occurrence of x. Note that we are only looking in the subarray after first occurrence */
         j = last(a, i, a.length-1, num, a.length);
        int[] output = {i,j};
        return output;
    }
    /* if x is present in arr[] then returns the index of FIRST occurrence of x in arr[0..n-1], otherwise returns -1 */
    int first(int arr[], int low, int high, int x, int n){
        if(high >= low){
            int mid = (low + high)/2;  /*low + (high - low)/2;*/
            if( ( mid == 0 || x > arr[mid-1]) && arr[mid] == x)
                return mid;
            else if(x > arr[mid])
                return first(arr, (mid + 1), high, x, n);
            else return first(arr, low, (mid - 1), x, n);
        }
        return -1;
    }
    /* if x is present in arr[] then returns the index of LAST occurrence of x in arr[0..n-1], otherwise returns -1 */
    int last(int arr[], int low, int high, int x, int n){
        if(high >= low){
            int mid = (low + high)/2;  /*low + (high - low)/2;*/
            if( ( mid == n-1 || x < arr[mid+1]) && arr[mid] == x )
                return mid;
            else if(x < arr[mid])
                return last(arr, low, (mid - 1), x, n);
            else
                return last(arr, (mid + 1), high, x, n);
        }
        return -1;
    }
    //Find duplicates in an Array which contains elements from 0 to n-1 in O(n) time and O(1) extra space
    void printRepeating(int arr[]) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[Math.abs(arr[i])] >= 0)
                arr[Math.abs(arr[i])] = -arr[Math.abs(arr[i])];
            else
                System.out.print(Math.abs(arr[i]));
        }
    }
    //Given an array arr[] of n integers, construct a Product Array prod[] (Self Excluding)
    //such that prod[i] is equal to the product of all the elements of arr[] except arr[i].
    //Solve it without division operator and in O(n). e.g. [3, 1, 4, 2] => [8, 24, 6, 12]
    private int[] selfExcludingProduct(int a[]) {
        int temp = 1;
        int[] prod = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            prod[i] = temp;
            temp *= a[i];
        }
        temp = 1;
        for (int i = a.length; i >= 0; i--) {
            prod[i] *= temp;
            temp *= a[i];
        }
        return prod;
    }
    //Given a set S of n integers, are there elements a, b, c in S such that a + b + c = 0? Find all unique triplets in the set which gives the sum of zero.
    //For example, given set S = {-1 0 1 2 -1 -4}, One possible solution set is:  (-1, 0, 1)   (-1, 2, -1) Time = O(n^2)
    HashSet<ArrayList<Integer>> find_triplets(int arr[]) {
        Arrays.sort(arr);
        HashSet<ArrayList<Integer>> triplets = new HashSet<ArrayList<Integer>>();
        ArrayList<Integer> triplet = new ArrayList<Integer>();
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            int j = i + 1;
            int k = n - 1;
            while (j < k) {
                int sum_two = arr[i] + arr[j];
                if (sum_two + arr[k] < 0) {
                    j++;
                } else if (sum_two + arr[k] > 0) {
                    k--;
                } else {
                    triplet.set(0, arr[i]);
                    triplet.set(1, arr[j]);
                    triplet.set(2, arr[k]);
                    if (!triplets.contains(triplet)) triplets.add(triplet);
                    j++;
                    k--;
                }
            }
        }
        return triplets;
    }
    //Given three arrays sorted in non-decreasing order, print all common elements in these arrays.
    //e.g.ar1[] = {1, 5, 10, 20, 40, 80} ar2[] = {6, 7, 20, 80} ar3[] = {3, 4, 15, 20, 30, 80} Output: 20, 80
    // Time Complexity = O(n1 + n2 + n3)
    void findCommon(int ar1[], int ar2[], int ar3[], int n1, int n2, int n3) {
        // Initialize starting indexes for ar1[], ar2[] and ar3[]
        int i = 0, j = 0, k = 0;
        // Iterate through three arrays while all arrays have elements
        while (i < n1 && j < n2 && k < n3) {
            // If x = y and y = z, print any of them and move ahead in all arrays
            if (ar1[i] == ar2[j] && ar2[j] == ar3[k]) {
                System.out.print(ar1[i]);
                i++;
                j++;
                k++;
            }
            // x < y
            else if (ar1[i] < ar2[j]) i++;
                // y < z
            else if (ar2[j] < ar3[k]) j++;
                // We reach here when x > y and z < y, i.e., z is smallest
            else k++;
        }
    }
    //Given an unsorted array arr[] and two numbers x and y, find the minimum distance between x and y in arr[]
    //arr[] = {3, 4, 5}, x = 3, y = 5 Minimum distance between 3 and 5 is 2
    int minDist(int arr[], int n, int x, int y) {
        int i = 0;
        int min_dist = Integer.MAX_VALUE;
        int prev=0;
        for (i = 0; i < n; i++) {
            if (arr[i] == x || arr[i] == y) {
                prev = i;
                break;
            }
        }
        //Traverse after the first occurrence
        for (; i < n; i++) {
            if (arr[i] == x || arr[i] == y) {
                // If the current element matches with any of the two then check if current element and prev element are different
                // Also check if this value is smaller than minimum distance so far
                if (arr[prev] != arr[i] && (i - prev) < min_dist) {
                    min_dist = i - prev;
                    prev = i;
                } else prev = i;
            }
        }
        return min_dist;
    }
    //Find the first repeating element in an array of integers. O(n)
    private int findFirstRepeating(int a[]) {
        int min = -1;
        HashSet<Integer> _hash = new HashSet<Integer>();
        for (int i = a.length; i > 0; i--) {
            if (_hash.contains(a[i])) min = i;
            else _hash.add(a[i]);
        }
        return a[min];
    }
    //Find k closest elements to a given value. Assumption: all elements in arr[] are distinct. Time = O(k) + O(logn)
    //Input: K = 4, X = 35 arr[] = {12, 16, 22, 30, 35, 39, 42,45, 48, 50, 53, 55, 56} Output: 30 39 42 45
    //Function to find the cross over point (the point before which elements are smaller than or equal to x and after
    //which greater than x)
    int findCrossOver(int arr[], int low, int high, int x) {
        if (arr[high] <= x) // x is greater than all
            return high;
        if (arr[low] > x)  // x is smaller than all
            return low;
        int mid = (low + high) / 2;  /* low + (high - low)/2 */
        /* If x is same as middle element, then return mid */
        if (arr[mid] <= x && arr[mid + 1] > x) return mid;
        if (arr[mid] < x) return findCrossOver(arr, mid + 1, high, x);
        return findCrossOver(arr, low, mid - 1, x);
    }
    // This function prints k closest elements to x in arr[]. n is the number of elements in arr[]
    void printKclosest(int arr[], int x, int k, int n) {
        // Find the crossover point
        int l = findCrossOver(arr, 0, n - 1, x);
        int r = l + 1;   // Right index to search
        int count = 0; // To keep track of count of elements already printed
        // If x is present in arr[], then reduce left index
        if (arr[l] == x) l--;
        // Compare elements on left and right of crossover point to find the k closest elements
        while (l >= 0 && r < n && count < k) {
            if (x - arr[l] < arr[r] - x) System.out.print(arr[l--]);
            else System.out.print(arr[r++]);
            count++;
        }
        // If there are no more elements on right side, then print left elements
        while (count < k && l >= 0) System.out.print(arr[l--]);
        count++;
        // If there are no more elements on left side, then print right elements
        while (count < k && r < n) System.out.print(arr[r++]);
        count++;
    }
    //Given an array consisting of only 0s and 1s, sort it. He was looking for highly optimized
    //Can also solve: the even numbers are on the left side of the array and all the odd numbers are on the right side
    void sort0and1(int arr[], int size){
        int left = 0, right = size-1;
        while(left < right){
            while(arr[left] == 0 && left < right)
                left++;
            while(arr[right] == 1 && left < right)
                right--;
            if(left < right)
            {
                arr[left] = 0;
                arr[right] = 1;
            }
        }
    }
    //Sort an array of 0s,1s,2s. Input={0, 1, 1, 0, 1, 2, 1, 2, 0, 0, 0, 1} Output={0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2}
    void sort012(int[] a) {
        int low = 0;
        int mid = 0;
        int high = a.length - 1;
        while (mid <= high) {
            if (a[mid] == 0) {
                swap(a, low, mid);
                low++;
                mid++;
            } else if (a[mid] == 1) {
                mid++;
            } else if (a[mid] == 2) {
                swap(a, mid, high);
                high--;
            }
        }
    }
    //Give you an array which has n integers,it has both  positive and negative integers.Now you need sort this array
    //in a special way.After that,the negative integers should in the front,and the positive integers should in the back.
    //Also the relative position should not be changed.eg. -1 1 3 -2 2 ans: -1 -2 1 3 2. should be Time= O(n) and Space O(1)
    //below function doest not maintain the order of positive numbers
    public void sortNegPos(int[] arr) {
        int left = 0, right = arr.length-1;
        while(left < right){
            while(arr[left] < 0 && left < right)
                left++;
            while(arr[right] > 0 && left < right)
                right--;
            if(left < right) {
                swap(arr,left,right);
            }
        }
    }
    //Time: O(N), Space O(1)
    //Given an array of positive and negative numbers, arrange them in an alternate fashion such that every
    //positive number is followed by negative and vice-versa maintaining the order of appearance.
    //input = {1, 2, 3, -4, -1, 4}  Output: arr[] = {-4, 1, -1, 2, 3, 4}
    void rearrangeWithOrder(int arr[], int n){
        int outofplace = -1;
        for (int index = 0; index < n; index ++){
            if (outofplace >= 0){
                // find the item which must be moved into the out-of-place
                // entry if out-of-place entry is positive and current
                // entry is negative OR if out-of-place entry is negative
                // and current entry is negative then right rotate
                //
                // [...-3, -4, -5, 6...] -->   [...6, -3, -4, -5...]
                //      ^                          ^
                //      |                          |
                //     outofplace      -->      outofplace
                //
                if (((arr[index] >= 0) && (arr[outofplace] < 0))
                        || ((arr[index] < 0) && (arr[outofplace] >= 0)))
                {
                    rightrotate(arr, n, outofplace, index);
                    // the new out-of-place entry is now 2 steps ahead
                    if (index - outofplace > 2)
                        outofplace = outofplace + 2;
                    else
                        outofplace = -1;
                }
            }
            // if no entry has been flagged out-of-place
            if (outofplace == -1){
                // check if current entry is out-of-place
                if (((arr[index] >= 0) && (index %2 == 0)) || ((arr[index] < 0) && (index %2 != 0))) {
                    outofplace = index;
                }
            }
        }
    }
    // Utility function to right rotate all elements between [outofplace, cur]
    void rightrotate(int arr[], int n, int outofplace, int cur)
    {
        int tmp = arr[cur];
        for (int i = cur; i > outofplace; i--)
            arr[i] = arr[i-1];
        arr[outofplace] = tmp;
    }

    //Rearrange positive and negative numbers in O(n) time and O(1) extra space
    //input array is [-1, 2, -3, 4, 5, 6, -7, 8, 9] output should be [9, -7, 8, -3, 5, -1, 2, 4, 6]
    //Rearrange the array elements so that positive and negative numbers are placed alternatively. Number of positive
    //and negative numbers need not be equal. If there are more positive numbers they appear at the end of the array.
    //If there are more negative numbers, they too appear in the end of the array.
    void rearrange(int arr[], int n){
        // The following few lines are similar to partition process of QuickSort.  The idea is to consider 0 as pivot and
        // divide the array around it.
        int i = -1;
        for (int j = 0; j < n; j++){
            if (arr[j] < 0){
                i++;
                swap(arr, arr[i], arr[j]);
            }
        }
        // Now all positive numbers are at end and negative numbers at the beginning of array. Initialize indexes for starting point
        // of positive and negative numbers to be swapped
        int pos = i+1, neg = 0;
        // Increment the negative index by 2 and positive index by 1, i.e.,
        // swap every alternate negative number with next positive number
        while (pos < n && neg < pos && arr[neg] < 0){
            swap(arr, arr[neg], arr[pos]);
            pos++;
            neg += 2;
        }
    }
    //Given a set of distinct unsorted integers s1, s2, .., sn how do you arrange integers such that s1 <= s2 >= s3 <= s4.
    // without order maintaining. wiggle-sort
    public void wiggleSort1(int[] nums) {
        for (int i=1; i<nums.length; i++) {
            int a = nums[i-1];
            if ((i%2 == 1) == (a > nums[i])) {
                nums[i-1] = nums[i];
                nums[i] = a;
            }
        }
    }
    //Given an unsorted array nums, reorder it such that nums[0] < nums[1] > nums[2] < nums[3]....
    //Example: (1) Given nums = [1, 5, 1, 1, 6, 4], one possible answer is [1, 4, 1, 5, 1, 6].
    private void wiggleSort(int [] nums) {
        if (nums.length < 2) {
            return;
        }

        int cntForMedian = (nums.length + 1) / 2;
        int median = findKthSmallest(nums, cntForMedian, 0, nums.length - 1);
        mapSortedArrayToWiggleInPlace(nums, median);
    }
    private void mapSortedArrayToWiggleInPlace(int[] nums, int median) {
        int i  = 1;
        int begin = 1;
        int end = nums.length;
        int n = nums.length;
        while (i <= end) {
            int Ai = indexMap(n, i);
            if (nums[Ai] > median) {
                swap(nums, Ai, indexMap(n, begin++));
                i ++;
            } else if (nums[Ai] < median) {
                swap(nums, Ai, indexMap(n, end--));
            } else {
                i++;
            }
        }
    }
    private int indexMap(int n, int i) {
        return (2 * i - 1) % (n | 1);
    }
    private int findKthSmallest(int [] nums, int k, int start, int end) {
        int len = nums.length;
        if (len < 1) {
            return 0;
        }
        int left = start;
        int right = end;
        int pivot = nums[end];
        while (true) {
            while (left < right && nums[left] < pivot) {
                left ++;
            }
            while (left < right && nums[right] >= pivot) {
                right --;
            }
            if (left >= right) {
                break;
            }
            swap1(nums, left, right);
        }
        swap1(nums, left, end);
        if (k > left - start + 1) {
            return findKthSmallest(nums, k - (left - start + 1), left + 1, end);
        } else if (k == left - start + 1) {
            return nums[left];
        } else {
            return findKthSmallest(nums, k, start, left - 1);
        }
    }
    private void swap1 (int []nums, int start, int end) {
        int temp = nums[start];
        nums[start] = nums[end];
        nums[end] = temp;
    }

    //Find the two numbers with odd occurrences in an unsorted array
    //Input: {12, 23, 34, 12, 12, 23, 12, 45}
    //Output: 34 and 45
    void printTwoOdd(int arr[], int size){
        int xor2 = arr[0]; /* Will hold XOR of two odd occurring elements */
        int set_bit_no;  /* Will have only single set bit of xor2 */
        int i;
        int n = size - 2;
        int x = 0, y = 0;
    /* Get the xor of all elements in arr[]. The xor will basically
     be xor of two odd occurring elements */
        for(i = 1; i < size; i++)
            xor2 = xor2 ^ arr[i];
    /* Get one set bit in the xor2. We get rightmost set bit
     in the following line as it is easy to get */
        set_bit_no = xor2 & ~(xor2-1);
    /* Now divide elements in two sets:
    1) The elements having the corresponding bit as 1.
    2) The elements having the corresponding bit as 0.  */
        for(i = 0; i < size; i++){
    /* XOR of first set is finally going to hold one odd
       occurring number x */
            if((arr[i] & set_bit_no) == 0)
                x = x ^ arr[i];
      /* XOR of second set is finally going to hold the other
       odd occurring number y */
            else
                y = y ^ arr[i];
        }
        System.out.print(x + " "+ y);
    }
    //Given an array of distinct integers, find length of the longest subarray which contains numbers that can be
    //arranged in a continuous sequence. input = {14, 12, 11, 20}; output = 2
    int findLength(int arr[], int n) {
        int max_len = 1;  // Initialize result
        for (int i=0; i<n-1; i++) {
            // Initialize min and max for all subarrays starting with i
            int mn = arr[i], mx = arr[i];
            // Consider all subarrays starting with i and ending with j
            for (int j=i+1; j<n; j++){
                // Update min and max in this subarray if needed
                mn = Math.min(mn, arr[j]);
                mx = Math.max(mx, arr[j]);
                // If current subarray has all contiguous elements
                if ((mx - mn) == j-i)
                    max_len = Math.max(max_len, mx - mn + 1);
            }
        }
        return max_len;  // Return result
    }
    //Given an array of duplicate integers, Find the Length of the largest sub-array with contiguous elements
    //Input:  arr[] = {10, 12, 12, 10, 10, 11, 10} Output: 2 Time Complexity: O(n^2)
    private int findContiguousLength(int a[]) {
        int n = a.length;
        int max_len = 1;
        for (int i = 0; i < n - 1; i++) {
            HashSet<Integer> set = new HashSet<Integer>();
            set.add(a[i]);
            int mn = a[i], mx = a[i];
            for (int j = i + 1; j < n; j++) {
                if (set.contains(a[j])) break;
                set.add(a[j]);
                mn = Math.min(mn, a[j]);
                mx = Math.max(mx, a[j]);
                if (mx - mn == j - i) max_len = Math.max(max_len, mx - mn + 1);
            }
        }
        return max_len;
    }

    //Given two sorted arrays and a number x, find the pair whose sum is closest to x and the pair has an element from each array.
    //ar1[] = {1, 4, 5, 7}; ar2[] = {10, 20, 30, 40};    x = 32     Output:  1 and 30
    void printClosest(int ar1[], int ar2[], int m, int n, int x)
    {
        int diff = Integer.MAX_VALUE;
        int res_l = 0, res_r = 0;
        int l = 0, r = n-1;
        while (l<m && r>=0)
        {
            // If this pair is closer to x than the previously
            // found closest, then update res_l, res_r and diff
            if (Math.abs(ar1[l] + ar2[r] - x) < diff)
            {
                res_l = l;
                res_r = r;
                diff = Math.abs(ar1[l] + ar2[r] - x);
            }
            // If sum of this pair is more than x, move to smaller side
            if (ar1[l] + ar2[r] > x)
                r--;
            else  // move to the greater side
                l++;
        }
        System.out.print(ar1[res_l] + " " + ar2[res_r]);
    }
    //Given arrival and departure times of all trains that reach a railway station, find the minimum number of platforms
    //required for the railway station so that no train waits.
    //Input:  arr[]  = {9:00,  9:40, 9:50,  11:00, 15:00, 18:00} O(nLogn) time.
    //dep[]          = {9:10, 12:00, 11:20, 11:30, 19:00, 20:00}
    //Output: 3 There are at-most three trains at a time (time between 11:00 to 11:20)
    int findPlatform(int arr[], int dep[], int n)
    {
        // Sort arrival and departure arrays
        Arrays.sort(arr);
        Arrays.sort(dep);
        // plat_needed indicates number of platforms needed at a time
        int plat_needed = 1, result = 1;
        int i = 1, j = 0;
        while (i < n && j < n)
        {
            // If next event in sorted order is arrival, increment count of
            // platforms needed
            if (arr[i] < dep[j])
            {
                plat_needed++;
                i++;
                if (plat_needed > result)  // Update result if needed
                    result = plat_needed;
            }
            else // Else decrement count of platforms needed
            {
                plat_needed--;
                j++;
            }
        }
        return result;
    }
    //Find Index of 0 to be replaced with 1 to get longest continuous sequence of 1s in a binary array
    //Input: arr[] =  {1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1} Output:Index 9
    int maxOnesIndex(int arr[], int n){
        int max_count = 0;  // for maximum number of 1 around a zero
        int max_index = -1;      // for storing result
        int prev_zero = -1;  // index of previous zero
        int prev_prev_zero = -1; // index of previous to previous zero
        for (int curr=0; curr<n; curr++){
            // If current element is 0, then calculate the difference between curr and prev_prev_zero
            if (arr[curr] == 0){
                // Update result if count of 1s around prev_zero is more
                if (curr - prev_prev_zero > max_count){
                    max_count = curr - prev_prev_zero;
                    max_index = prev_zero;
                }
                // Update for next iteration
                prev_prev_zero = prev_zero;
                prev_zero = curr;
            }
        }
        // Check for the last encountered zero
        if (n-prev_prev_zero > max_count)
            max_index = prev_zero;
        return max_index;
    }
    //Given a set of n nuts of different sizes and n bolts of different sizes.
    //There is a one-one mapping between nuts and bolts. Match nuts and bolts efficiently.
    //Nuts and bolts are represented as array of characters
    //char nuts[] = {'@', '#', '$', '%', '^', '&'};
    //char bolts[] = {'$', '%', '&', '^', '@', '#'};
    //Method which works just like quick sort Time = O(nLogn)
    private static void matchPairs(char[] nuts, char[] bolts, int low, int high){
        if (low < high){
            //Choose last character of bolts array for nuts partition.
            int pivot = partition(nuts, low, high, bolts[high]);
            //Now using the partition of nuts choose that for bolts partition.
            partition(bolts, low, high, nuts[pivot]);
            //Recur for [low...pivot-1] & [pivot+1...high] for nuts and bolts array.
            matchPairs(nuts, bolts, low, pivot-1);
            matchPairs(nuts, bolts, pivot+1, high);
        }
    }
    // Similar to standard partition method. Here we pass the pivot element
    // too instead of choosing it inside the method.
    private static int partition(char[] arr, int low, int high, char pivot){
        int i = low;
        char temp1, temp2;
        for (int j = low; j < high; j++){
            if (arr[j] < pivot){
                temp1 = arr[i];
                arr[i] = arr[j];
                arr[j] = temp1;
                i++;
            } else if(arr[j] == pivot){
                temp1 = arr[j];
                arr[j] = arr[high];
                arr[high] = temp1;
                j--;
            }
        }
        temp2 = arr[i];
        arr[i] = arr[high];
        arr[high] = temp2;
        // Return the partition index of an array based on the pivot element of other array.
        return i;
    }
    //Given an unsorted array that may contain duplicates.returns true if array contains duplicates within k distance.
    private boolean checkDuplicatesWithinK(int a[], int k)
    {
        HashSet<Integer> hash = new HashSet<Integer>();
        for(int i = 0; i < a.length; i++)
        {
            if(hash.contains(a[i]))
                return  true;
            hash.add(a[i]);
            if(i >= k)
                hash.remove(a[i-k]);
        }
        return  false;
    }
    //Given an array of integers where each element represents the max number of steps that can be made forward from that element.
    //Write a function to return the minimum number of jumps to reach the end of the array
    //Returns minimum number of jumps to reach arr[n-1] from arr[0]. Dynamic Programming time O(n^2)
    //Input: arr[] = {1, 3, 5, 8, 9, 2, 6, 7, 6, 8, 9}
    //Output: 3 (1-> 3 -> 8 ->9)
    int minJumps(int arr[])
    {
        int n = arr.length;
        int[] jumps = new int[n];  // jumps[n-1] will hold the result
        int i, j;
        if (n == 0 || arr[0] == 0)
            return Integer.MAX_VALUE;
        jumps[0] = 0;
        // Find the minimum number of jumps to reach arr[i]
        // from arr[0], and assign this value to jumps[i]
        for (i = 1; i < n; i++)
        {
            jumps[i] = Integer.MAX_VALUE;
            for (j = 0; j < i; j++)
            {
                if (i <= j + arr[j] && jumps[j] != Integer.MAX_VALUE)
                {
                    jumps[i] = Math.min(jumps[i], jumps[j] + 1);
                    break;
                }
            }
        }
        return jumps[n-1];
    }
    //Given an array and a value, how to implement a function to remove all instances of that value in place and return the new length?
    //input - {4, 3, 2, 1, 2, 3, 6} and 2 output: 5
    public int removeNumber(int[] A, int n) {

        if (A == null || A.length == 0) return 0;
        int i = 0;
        for (int j=0; j<A.length; j++)
            if (A[j] != n) A[i++] = A[j];
        return i; // The new dimension of the array
    }
    //Given an array of numbers and a sliding window size, how to get the maximal numbers in all sliding windows?
    //Maximum of all subarrays of size k
    //input: {2, 3, 4, 2, 6, 2, 5, 1} output: {4, 4, 6, 6, 6, 5}
    //Time  = O(n) and Aux Space complexity O(K)
    void findMaxSliding(int[] x, int k){
        Deque<Integer> q=new ArrayDeque<Integer>();
        int i = 0;
        for(;i<k;i++){
        // For every element, the previous smaller elements are useless so remove them from q
            while(!q.isEmpty() && x[q.peekLast()]<=x[i]){
                q.removeLast();
            }
            q.addLast(i); // Add new element at rear of queue
        }
        for ( ; i <x.length; ++i)
        {
            // The element at the front of the queue is the largest element of previous window, so print it
            System.out.println(x[q.peek()]);
            // Remove the elements which are out of this window
            while(!q.isEmpty() && q.peekFirst()<=i-k){
                q.removeFirst();// Remove from rear
            }
            // Remove all elements smaller than the currently being added element (remove useless elements)
            while(!q.isEmpty() && x[q.peekLast()]<=x[i]){
                q.removeLast();
            }
            q.addLast(i);
        }
        System.out.println(x[q.peek()]);
    }
    //Given a sorted array of positive integers, rearrange the array alternately
    //i.e first element should be maximum value, second minimum value, third second max, fourth second min and so on.
    //Time = O(n)
    void rearrange(int arr[], int n){
        for (int i=0; i<n; i++){
            int temp = arr[i];
            // If number is negative then we have already processed it. Else process all numbers which
            // are to be replaced by each other in cyclic way
            while (temp > 0){
                // Find the index where arr[i] should go
                int j = (i < n/2)? 2*i + 1 : 2*(n-1-i);
                // If arr[i] is already at its correct position, mark it as negative
                if (i == j){
                    arr[i] = -temp;
                    break;
                }
                // Swap the number 'temp' with the current number at its target position
                swap(temp, arr[j]);
                // Mark the number as processed
                arr[j] = -arr[j];
                // Next process the previous number at target position
                i = j;
            }
        }
        // Change the number to original value
        for (int i=0; i<n; i++)
            arr[i] = -arr[i];
    }
    //price array of a given stock on day i.If you were only permitted to complete at most one transaction find the
    // maximum profit.
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        int max_diff = prices[1] - prices[0];
        int min_element = prices[0];
        int i;
        for (i = 1; i < prices.length; i++) {
            if (prices[i] - min_element > max_diff) max_diff = prices[i] - min_element;
            if (prices[i] < min_element) min_element = prices[i];
        }
        return max_diff;
    }
    // Stock problem: multiple transactions are allowed. you must sell the stock before you buy again
    public int maxProfitMultiTrans(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }
        // used to record max profit can get until each day
        int[] maxProfit = new int[prices.length];
        maxProfit[0] = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                // price go up, max profit is max profit get by yesterday plus new profit
                maxProfit[i] = prices[i] - prices[i - 1] + maxProfit[i - 1];
            } else {
                // price go down, max profit can get by today should be equal to yesterday.
                maxProfit[i] = maxProfit[i - 1];
            }
        }
        return maxProfit[maxProfit.length - 1];
    }
    //one more solution
    public int maxProfit2(int[] prices) {
        int profit = 0;
        for(int i=1; i<prices.length; i++){
            int diff = prices[i]-prices[i-1];
            if(diff > 0){
                profit += diff;
            }
        }
        return profit;
    }
    //A magic index in an array A[0...n] is defined to be an index such that A[i] = i. Given a sorted array of duplicate
    // integers, write a method to find a magic index, if one exists, in array A.
    private static int getMagicIndexDup(int[] a, int start, int end) {
        if (start > end) return -1;
        int mid = start + (end - start) / 2;
        if (a[mid] == mid) return mid;
        // search left
        int result = getMagicIndexDup(a, start, Math.min(mid - 1, a[mid]));
        // search right
        if (result == -1) {
            result = getMagicIndexDup(a, Math.max(mid + 1, a[mid]), end);
        }
        return result;
    }
    //You're given an unsorted array of integers where every integer appears exactly twice, except for one integer which
    //appears only once.  Write an algorithm that finds the integer that appears only once. TIme O(n) space O(1)
    int oddManOut(int[] array) {
        int val = 0;
        for (int i = 0; i < array.length; i++) {
            val ^= array[i];
        }
        return val;
    }
    //Max product of the three numbers for a given array of size N
    static int maxproductofThree(int[] a){
        int largest = a[0];
        int secondlargest = 0;
        int thirdlargest = 0;
        int min1 = 0;
        int min2 = 0;
        for (int i = 0; i < a.length; i++){
            int number = a[i];
            if (number > largest){
                thirdlargest = secondlargest;
                secondlargest = largest;
                largest = number;
            }
            else if (number > secondlargest){
                thirdlargest = secondlargest;
                secondlargest = number;
            }
            else if (number > thirdlargest){
                thirdlargest = number;
            }
            if (number < min1){
                min2 = min1;
                min1 = number;
            }
            else if (number < min2)
                min2 = number;
        }
        return largest * Math.max((thirdlargest * secondlargest), (min1 * min2));
    }
    //Given an unsorted array of positive numbers, write a function that returns true if array consists of consecutive numbers.
    public boolean areConsecutive(int input[]){
        int min = Integer.MAX_VALUE;
        for(int i=0; i < input.length; i++){
            if(input[i] < min){
                min = input[i];
            }
        }
        for(int i=0; i < input.length; i++){
            if(Math.abs(input[i]) - min >= input.length){
                return false;
            }
            if(input[Math.abs(input[i]) - min] < 0){
                return false;
            }
            input[Math.abs(input[i]) - min] = -input[Math.abs(input[i]) - min];
        }
        for(int i=0; i < input.length ; i++){
            input[i] = Math.abs(input[i]);
        }
        return true;
    }
    //find missing number from array 0 to n
    //You are given a list of n-1 integers and these integers are in the range of 1 to n. There are no duplicates in list.
    int getMissingNo(int a[], int n){
        int i;
        int x1 = a[0]; /* For xor of all the elements in array */
        int x2 = 1; /* For xor of all the elements from 1 to n+1 */
        for (i = 1; i< n; i++)
            x1 = x1^a[i];
        for ( i = 2; i <= n+1; i++)
            x2 = x2^i;
        return (x1^x2);
    }
    //Find two missing numbers from the array with given length
    public static void findNumbers(int[] a, int N){
        int x = 0;
        for (int i = 0; i < a.length; i++){
            x = x ^ a[i];
        }
        for (int i = 1; i <= N; i++){
            x = x ^ i;
        }
        x = x & (~(x - 1));
        int p = 0, q = 0;
        for (int i = 0; i < a.length; i++){
            if ((a[i] & x) == x){
                p = p ^ a[i];
            }
            else{
                q = q ^ a[i];
            }
        }
        for (int i = 1; i <= N; i++){
            if ((i & x) == x){
                p = p ^ i;
            }
            else{
                q = q ^ i;
            }
        }
        System.out.println("N1: " + p + " N2: " + q);
    }


}
