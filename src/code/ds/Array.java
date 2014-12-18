package code.ds;
import com.sun.org.apache.xpath.internal.operations.Bool;
import java.util.HashMap;
import java.util.*;
/**
 * Created by Piyush Patel.
 */
public class Array {
    //Merge two sorted array into sorted array
    public int[] MergeArray(int[] a, int[] b) {
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
    //Find the k-th Smallest Element in the Union of Two Sorted Arrays
    // Time Complexity :  O(logk)
    public int findKthElement(int k, int[] array1, int start1, int end1, int[] array2, int start2, int end2) {
        // if (k>m+n) exception
        if (k == 0) {
            return Math.min(array1[start1], array2[start2]);
        }
        if (start1 == end1) {
            return array2[k];
        }
        if (start2 == end2) {
            return array1[k];
        }
        int mid = k / 2;
        int sub1 = Math.min(mid, end1 - start1);
        int sub2 = Math.min(mid, end2 - start2);
        if (array1[start1 + sub1] < array2[start2 + sub2]) {
            return findKthElement(k - mid, array1, start1 + sub1, end1, array2, start2, end2);
        } else {
            return findKthElement(k - mid, array1, start1, end1, array2, start2 + sub2, end2);
        }
    }
    //Given two unsorted int arrays, find the kth smallest element in the merged, sorted array.
    private void MergeUnsortedArray(int[] A1, int[] A2) {
        int[] c = new int[A1.length + A2.length];
        int length = 0;
        for (int i = 0; i < A1.length; i++) {
            c[i] = A1[i];
            length++;
        }
        for (int j = 0; j < A2.length; j++) {
            c[length + j + 1] = A2[j];
        }
        quickselect(c, 0, c.length, 3);
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
    //Find k maximum integers from an array of infinite integers.
    // Find first 100 maximum numbers from the billion numbers
    // using Min Heap takes O(n log k) (assumption is that k is significantly lesser than n)
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
    //find the sum of contiguous sub array within a one-dimensional array of numbers with negative which has the largest sum .
    private int maxSubArraySum(int a[]) {
        int max_so_far = a[0];
        int curr_max = a[0];
        for (int i = 1; i < a.length; i++) {
            curr_max = Math.max(a[i], curr_max + a[i]);
            max_so_far = Math.max(max_so_far, curr_max);
        }
        return max_so_far;
    }
    //Given an array that contains both positive and negative integers, find the product of the maximum product subarray.
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
    // Return -1 if no such element is found.
    private int MoreThanHalfElem(int a[], int n) {
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
        return -1;
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
                if (A[L] <= key && key < A[M]) R = M - 1;
                else L = M + 1;
            }
            // the upper half is sorted
            else {
                if (A[M] < key && key <= A[R]) L = M + 1;
                else R = M - 1;
            }
        }
        return -1;
    }
    //Given 3 arrays, pick 3 nos, one from each array, say a,b,c such that |a-b|+|b-c|+|c-a| is minimum
    private void findMinofabc(int a[], int b[], int c[]) {
        //quickSort(a, 0, a.length);
        //quickSort(b, 0, a.length);
        //quickSort(c, 0, a.length);
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
    //Given a sorted array with duplicates and a number, find the range in the
    //form of (startIndex, endIndex) of that number. find_range({0 2 3 3 3 10 10}, 3) should return (2,4).
    private void findRange(int a[], int num) {
        int startIndex = -1, endIndex = -1;
        boolean flag = true;
        if (a.length == 0) return;
        for (int i = 0; i < a.length; i++) {
            if (a[i] == num && flag) {
                startIndex = i;
                endIndex = i;
                flag = false;
            } else if (a[i] == num) endIndex = i;
        }
    }
    //Find duplicates in an Array in O(n) time and O(1) extra space
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
    //For example, given set S = {-1 0 1 2 -1 -4}, One possible solution set is:  (-1, 0, 1)   (-1, 2, -1)
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
                // If the current element matches with any of the two then
                // check if current element and prev element are different
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
    //Find k closest elements to a given value
    //Input: K = 4, X = 35 arr[] = {12, 16, 22, 30, 35, 39, 42,45, 48, 50, 53, 55, 56}
    //Output: 30 39 42 45
    /* Function to find the cross over point (the point before
   which elements are smaller than or equal to x and after
   which greater than x)*/
    int findCrossOver(int arr[], int low, int high, int x) {
        // Base cases
        if (arr[high] <= x) // x is greater than all
            return high;
        if (arr[low] > x)  // x is smaller than all
            return low;
        // Find the middle point
        int mid = (low + high) / 2;  /* low + (high - low)/2 */
        /* If x is same as middle element, then return mid */
        if (arr[mid] <= x && arr[mid + 1] > x) return mid;
        /* If x is greater than arr[mid], then either arr[mid + 1]
        is ceiling of x or ceiling lies in arr[mid+1...high] */
        if (arr[mid] < x) return findCrossOver(arr, mid + 1, high, x);
        return findCrossOver(arr, low, mid - 1, x);
    }
    // This function prints k closest elements to x in arr[].
    // n is the number of elements in arr[]
    void printKclosest(int arr[], int x, int k, int n) {
        // Find the crossover point
        int l = findCrossOver(arr, 0, n - 1, x); // le
        int r = l + 1;   // Right index to search
        int count = 0; // To keep track of count of elements already printed
        // If x is present in arr[], then reduce left index
        // Assumption: all elements in arr[] are distinct
        if (arr[l] == x) l--;
        // Compare elements on left and right of crossover
        // point to find the k closest elements
        while (l >= 0 && r < n && count < k) {
            if (x - arr[l] < arr[r] - x) System.out.print(arr[l--]);
            else System.out.print(arr[r++]);
            count++;
        }
        // If there are no more elements on right side, then
        // print left elements
        while (count < k && l >= 0) System.out.print(arr[l--]);
        count++;
        // If there are no more elements on left side, then
        // print right elements
        while (count < k && r < n) System.out.print(arr[r++]);
        count++;
    }
    //Given an array consisting of only 0s and 1s, sort it. He was looking for highly optimized algos
    void sort0and1(int arr[], int size)
    {
        int left = 0, right = size-1;
        while(left < right)
        {
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
    //Sort an array of 0s,1s,2s
    void sort012(int[] a) {
        int low = 0;
        int mid = 0;
        int high = a.length;
        while (mid < high) {
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
    //Rearrange positive and negative numbers in O(n) time and O(1) extra space
    //input array is [-1, 2, -3, 4, 5, 6, -7, 8, 9] output should be [9, -7, 8, -3, 5, -1, 2, 4, 6]
    void rearrange(int arr[], int n)
    {
        // The following few lines are similar to partition process
        // of QuickSort.  The idea is to consider 0 as pivot and
        // divide the array around it.
        int i = -1;
        for (int j = 0; j < n; j++)
        {
            if (arr[j] < 0)
            {
                i++;
                swap(arr, arr[i], arr[j]);
            }
        }
        // Now all positive numbers are at end and negative numbers at
        // the beginning of array. Initialize indexes for starting point
        // of positive and negative numbers to be swapped
        int pos = i+1, neg = 0;
        // Increment the negative index by 2 and positive index by 1, i.e.,
        // swap every alternate negative number with next positive number
        while (pos < n && neg < pos && arr[neg] < 0)
        {
            swap(arr, arr[neg], arr[pos]);
            pos++;
            neg += 2;
        }
    }
    //Rearrange array in alternating positive & negative items with O(1) extra space time O(N2)
    // maintaining the order of appearance. eg. -1 1 3 -2 2 ans: -1 -2 1 3 2.
    public static void sortNegPos(int[] arr) {
        int[] neg = new int[arr.length];
        int numNegSoFar = 0;
        for (int i = arr.length - 1; i >= 0; i--) {
            if (numNegSoFar != 0 && arr[i] >= 0) {
                arr[i + numNegSoFar] = arr[i];
                int temp = arr[i];
                for (int k = 0; k < numNegSoFar; k++) {
                    arr[i + k] = arr[i + k + 1];
                }
                arr[i + numNegSoFar] = temp;
            }
            if (arr[i] < 0) {
                numNegSoFar++;
            }
        }
    }
    //Time: O(N), Space O(N)
    //Rearrange array in alternating positive & negative items
    public static void sortNegPosSwap(int[] arr)
    {
        int[] neg = new int[arr.length];
        int numNeg = 0;
        int numNegSoFar = 0;
        for(int i = 0; i < arr.length; i++)
        {
            if(arr[i] < 0)
            {
                neg[numNeg++] = arr[i];
            }
        }
        for(int i = arr.length - 1; i >= 0; i--)
        {
            if(numNegSoFar != 0 && arr[i] >= 0)
            {
                arr[i + numNegSoFar] = arr[i];
            }
            if(arr[i] < 0)
            {
                numNegSoFar++;
            }
        }
        for(int i = 0; i < numNeg; i++)
        {
            arr[i] = neg[i];
        }
    }
    //Given a set of distinct unsorted integers s1, s2, .., sn how do you arrange integers such that s1 < s2 > s3 < s4.
    // without order maintaining
    private void arrange(int a[])
    {
        for ( int i = 0; i < a.length - 2; i++)
        {
            if(i % 2 == 0) //even
            {
                if(a[i] > a[i+1])
                    swap(a, i, i+1);
            }
            else //odd
            {
                if(a[i] < a[i+1])
                    swap(a, i, i+1);
            }
        }
    }
    //Find the two numbers with odd occurrences in an unsorted array
    void printTwoOdd(int arr[], int size)
    {
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
        for(i = 0; i < size; i++)
        {
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
    //Length of the largest sub-array with contiguous elements
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
    //Finding Shortest unique Prefixes for Strings in an Array
    public static void findPrefixes(String[] strings){
        System.out.println();
        String[] pre = new String[strings.length];

        for(int i=0;i<pre.length;++i){
            if(i>0){
                if(strings[i].matches(strings[i-1])){
                    System.out.println("Duplicate string - Error!"); continue;
                }
            }
            pre[i]=Character.toString(strings[i].charAt(0));
            checkPrefix(strings, pre, pre[i], i);
        }
        System.out.println(Arrays.toString(strings));
        System.out.println(Arrays.toString(pre));
    }
    public static void checkPrefix(String[] strings, String[] pre, String s, int index){
        //System.out.println(Arrays.toString(pre));
        for(int i=index-1;i>=0;--i){
            if(s.matches(pre[i])){
                if(s.length()==strings[i].length()){
                    //System.out.println("Can't update the previous one, need to update this one");
                    pre[index] = strings[index].substring(0, s.length()+1);
                    checkPrefix(strings, pre, pre[index], index);
                    return;
                }
                else if(s.length() < strings[i].length()){
                    //System.out.println("Can update the previous one");
                    pre[i] = strings[i].substring(0, s.length()+1);
                    checkPrefix(strings, pre, pre[i], i);
                    return;
                }
            }
        }
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
    //Given arrival and departure times of all trains that reach a railway station, find the minimum number of platforms required for the railway station so that no train waits.
    //Input:  arr[]  = {9:00,  9:40, 9:50,  11:00, 15:00, 18:00} O(nLogn) time.
    //dep[]  = {9:10, 12:00, 11:20, 11:30, 19:00, 20:00}  Output: 3 There are at-most three trains at a time (time between 11:00 to 11:20)
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
    int maxOnesIndex(int arr[], int n)
    {
        int max_count = 0;  // for maximum number of 1 around a zero
        int max_index = -1;      // for storing result
        int prev_zero = -1;  // index of previous zero
        int prev_prev_zero = -1; // index of previous to previous zero
        for (int curr=0; curr<n; ++curr)
        {
            // If current element is 0, then calculate the difference
            // between curr and prev_prev_zero
            if (arr[curr] == 0)
            {
                // Update result if count of 1s around prev_zero is more
                if (curr - prev_prev_zero > max_count)
                {
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

}
