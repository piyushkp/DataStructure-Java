package code.ds;
import com.sun.deploy.util.ParameterUtil;
import com.sun.javafx.scene.layout.region.Margins;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.*;
import java.lang.*;
/**
 * Created by Piyush Patel.
 */
public class Array {
    public static void main(String [] args) {
        int arr[] = {2,3,5,1};
        int arr1[] = {9,10,8,7};

        //subArraySumPositive(arr,33);
        //int[] out = threeSum_Multiple(arr);
        //List<List<Integer>> out = kSum(arr,3,5,0);
        //System.out.println(minSubArraySum(arr,7));
        //int[] a = threeSum_Multiple(arr);

        MergeUnsortedArrayKthSmallest(arr,arr1,5);
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
    //merge k sorted list/array. We can merge arrays in O(nk*Logk) time using Min Heap
    class Triplet {
        int pos;
        int val;
        int index;
    }
    public List<Integer> mergeUsingHeap(List<List<Integer>> chunks){
        List<Integer> result = new ArrayList<Integer>();
        PriorityQueue<Triplet> minHeap = new PriorityQueue<Triplet>();
        //add first element of every chunk into queue
        for(int i=0; i < chunks.size(); i++){
            Triplet p = new Triplet();
            p.pos = i;
            p.val = chunks.get(i).get(0);
            p.index = 1;
            minHeap.add(p);
        }
        while(!minHeap.isEmpty()){
            Triplet p = minHeap.poll();
            result.add(p.val);
            if(p.index < chunks.get(p.pos).size()){
                p.val = chunks.get(p.pos).get(p.index);
                p.index += 1;
                minHeap.add(p);
            }
        }
        return result;
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
    //Median of two sorted arrays
    public double findMedianSortedArrays(int A[], int B[]) {
        int lengthA = A.length;
        int lengthB = B.length;
        if ((lengthA + lengthB) % 2 == 0) {
            double r1 = (double) findKSmallestElement(A, 0, lengthA, B, 0, lengthB, (lengthA + lengthB) / 2);
            double r2 = (double) findKSmallestElement(A, 0, lengthA, B, 0, lengthB, (lengthA + lengthB) / 2 + 1);
            return (r1 + r2) / 2;
        } else
            return findKSmallestElement(A, 0, lengthA, B, 0, lengthB, (lengthA + lengthB + 1) / 2);
    }
    // Given a stream of unsorted integers, find the median element in sorted order at any given time.
    // http://www.ardendertat.com/2011/11/03/programming-interview-questions-13-median-of-integer-stream/
    // provides O(1) find heap and O(logN) insert
    public Queue<Integer> minHeap =  new PriorityQueue<Integer>();
    public Queue<Integer> maxHeap =  new PriorityQueue<Integer>(10, new MaxHeapComparator());;
    public int numOfElements =0;
    public void addNumberToStream(Integer num) {
        maxHeap.add(num);
        if (numOfElements%2 == 0) {
            if (minHeap.isEmpty()) {
                numOfElements++;
                return;
            }
            else if (maxHeap.peek() > minHeap.peek()) {
                Integer maxHeapRoot = maxHeap.poll();
                Integer minHeapRoot = minHeap.poll();
                maxHeap.add(minHeapRoot);
                minHeap.add(maxHeapRoot);
            }
        } else {
            minHeap.add(maxHeap.poll());
        }
        numOfElements++;
    }
    public Double getMedian() {
        if (numOfElements%2 != 0)
            return new Double(maxHeap.peek());
        else
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
    }
    private class MaxHeapComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }
    }

    //Given two unsorted int arrays with elements are distinct, find the kth smallest element in the merged, sorted array.
    // Average case Time = O(n) Worst case O(n2) where n is total length of A1 and A2
    private static void MergeUnsortedArrayKthSmallest(int[] A1, int[] A2, int K) {
        int[] c = new int[A1.length + A2.length];
        int length = 0;
        for (int i = 0; i < A1.length; i++) {
            c[i] = A1[i];
            length++;
        }
        for (int j = 0; j < A2.length; j++) {
            c[length + j] = A2[j];
        }
        quickselect(c, 0, c.length-1, K - 1);
    }
    private static int quickselect(int[] G, int first, int last, int k) {
        if (first <= last) {
            //int pivot = partition(G, first, last);
            int pivot = randomPartition(G, first, last);
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
    private static int randomPartition(int arr[], int l, int r){
        int pivot = (int)(Math.random()) % (r-1);
        swap(arr, pivot, r-1);
        return partition(arr, l, r);
    }
    private static int partition(int[] G, int first, int last) {
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
    private static void swap(int[] G, int x, int y) {
        int temp = G[y];
        G[y] = G[x];
        G[x] = temp;
    }
    //Find the second largest/smallest number from the array
    static int  secondlargest(int[] a){
        int largest = a[0];
        int secondlargest = 0;
        for (int i = 1; i < a.length; i++){
            int number = a[i];
            if (number > largest){
                secondlargest = largest;
                largest = number;
            }
            else if (number > secondlargest && number != largest){
                secondlargest = number;
            }
        }
        return secondlargest;
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
    //Given sorted array find two number sum to target, Each number can be used multiple times.
    public static int[] twoSumSortedArray(int[] A, int target) {
        int left = 0, right = A.length - 1;
        while (left < right) {
            int s = A[left] + A[right];
            //if(A[left] == 0 || A[right] == 0)
            //    return new int[]{0, 0};
            if (A[left] == target/2 || A[right] == target/2)
                return new int[]{target/2, target/2};
            if (s == target)
                return new int[]{A[left], A[right]};
            else if (s > target)
                right--;
            else
                left++;
        }
        return null;
    }
    //k Sum problem, Time = O(N^k)
    public static List<List<Integer>> kSum(int[] num, int k, int target, int start_index) {
        List<List<Integer>> result = new LinkedList<List<Integer>>();
        if (k == 0) {
            if (target == 0) {
                // if we find the target,open an entry to store the whole path
                result.add(new LinkedList());
            }
            return result;
        }
        for (int i = start_index; i < num.length - k + 1; i++) {
            if ((i > start_index) && (num[i] == num[i - 1])) {
                continue;
            }
            for (List<Integer> item : kSum(num, k - 1, target - num[i], i+1)){
                item.add(0, i);
                result.add(item);
            }
        }
        return result;
    }
    //Given an array of integers, find two numbers such that they add up to a specific target number. 2Sum
    //Time = O(n) space O(n)
    public ArrayList<Integer> twoSum(int[] numbers, int target) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < numbers.length; i++) {
            if (map.containsKey(numbers[i])) {
                int index = map.get(numbers[i]);
                result.add(index+1) ;
                result.add(i+1);
                break;
            } else {
                map.put(target - numbers[i], i);
            }
        }
        return result;
    }
    //Follow up: what if we need to speedup the proceess to know whether 2-sum present or not.
    //The solution is when we store an element, we could also store the sum of the current number with all previous numbers.
    //Then in the test, we only need to check if the sum exists.
    Set<Integer> set = new HashSet<Integer>();
    List<Integer> nums = new ArrayList<Integer>();
    public void store(int input) {
        if (!nums.isEmpty()) {
            for (int num : nums) {
                set.add(input + num);
            }
        }
        nums.add(input);
    }
    public boolean faster2Sum(int val) {
        return set.contains(val);
    }

    //Count all distinct pairs with difference equal to k (2-sum) without hash
    //Given an integer array and a positive integer k, count all distinct pairs with difference equal to k.
    //Time complexity = O(nlogn) space O(1)
    int countPairsWithDiffK(int arr[], int n, int k){
        int count = 0;
        Arrays.sort(arr);  // Sort array elements
        int l = 0;
        int r = 0;
        while(r < n){
            if(arr[r] - arr[l] == k){
                count++;
                l++;
                r++;
            }
            else if(arr[r] - arr[l] > k)
                l++;
            else // arr[r] - arr[l] < sum
                r++;
        }
        return count;
    }
    //Given a set S of n integers, are there elements a, b, c in S such that a + b + c = 0? Find all unique triplets in
    //the set which gives the sum of zero. 3Sum problem
    //For example, given set S = {-1 0 1 2 -1 -4}, One possible solution set is:  (-1, 0, 1)   (-1, 2, -1) Time = O(n^2)
    public static HashSet<ArrayList<Integer>> find_triplets(int arr[]) {
        Arrays.sort(arr);
        HashSet<ArrayList<Integer>> triplets = new HashSet<ArrayList<Integer>>();
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            int j = i + 1;
            int k = n - 1;
            while (j < k) {
                ArrayList<Integer> triplet = new ArrayList<>();
                int sum_two = arr[i] + arr[j];
                if (sum_two + arr[k] < 0) {
                    j++;
                } else if (sum_two + arr[k] > 0) {
                    k--;
                } else {
                    triplet.add(0, arr[i]);
                    triplet.add(1, arr[j]);
                    triplet.add(2, arr[k]);
                    if (!triplets.contains(triplet)) triplets.add(triplet);
                    j++;
                    k--;
                }
            }
        }
        return triplets;
    }
    //Given an array S of n integers, find three integers in S such that the sum is closest to a given number, target.
    //Return the sum of the three integers. 3Sum Closest. Time = O(n^2)
    public static int threeSumClosest(int[] num, int target){
        Arrays.sort(num);
        int min = Integer.MAX_VALUE, result = 0;
        for (int i = 0; i < num.length; i++) {
            int j = i+1;
            int k = num.length - 1;
            while(j < k){
                int sum = num[i] + num[j] + num [k];
                int diff = Math.abs(sum - target);
                if(diff == 0) return sum;
                if(diff < min){
                    min = diff;
                    result = sum;
                }
                if(sum <= target)
                    j++;
                else
                    k--;
            }
        }
        return result;
    }
    //Determine if any 3 integers in an array sum to 0. Each number can be used multiple times.
    public static int[] threeSum_Multiple(int arr[]) {
        Arrays.sort(arr);
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            if(arr[i] == 0)
                return new int[] {0,0,0};
            int j = i;
            int k = n - 1;
            while (j < k) {
                int sum = arr[i] + arr[j] + arr[k];
                if (sum < 0) {
                    j++;
                } else if (sum > 0) {
                    k--;
                } else {
                    return new int[] {arr[i],arr[j],arr[k]};
                    //j++;
                    //k--;
                }
            }
        }
        return null;
    }

    //Given an array nums and a target value k, find the maximum length of a subarray that sums to k. If there isn't one, return 0 instead.
    //arr = [1, -1, 5, -2, 3], k = 3 Output = 4 (subarray [1, -1, 5, -2])
    public static int maxSubArraySumLen(int[] arr, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int max = 0;
        int curr_sum = 0;
        for (int i = 0; i < arr.length; i++) {
            curr_sum += arr[i];
            if(curr_sum == k) {
                max = Math.max(max, (i-0) + 1);
            }
            if(map.containsKey(curr_sum - k)) {
                max = Math.max(max, (i - map.get(curr_sum - k)));
            }
            else
                map.put(curr_sum , i);
        }
        return max;
    }

    //Given an unsorted array of integers, find a subarray which adds to a given number.
    // If there are more than one subarrays with sum as the given number, print any of them.
    //arr[] = {1, 4, 20, 3, 10, 5}, sum = 33  output = true
    //time complexity is O(2n) and space is O(n)
    // only positive numbers
    public static void subArraySumPositive(int[] A, int target) {
        for (int i = 0, j = 0, sum = 0; i < A.length; i++) {
            for (; j < A.length && sum < target;j++) {
                sum += A[j];
            }
            if (sum == target) {
                System.out.print("Start index: " + i + " End index: " + (j-1));
                return;
            }
            sum -= A[i];
        }
        System.out.print("No SubArray Found.");
    }
    public static boolean isValid(int[] a, int sum) {
        int count = 0,temp = 0;
        for (int i = 0; i < a.length; i++) {
            temp += a[i];
            while (temp > sum) {
                temp -= a[count];
                count++;
            }
            if (temp == sum)
                return true;
        }
        return false;
    }
    //Given an unsorted array of positive and negative integers, find a subarray which adds to a given number. handles negative numbers as well
    public static void subArraySum(int arr[], int sum){
        HashMap<Integer, Integer> map = new HashMap<>();
        int curr_sum = 0;
        for (int i = 0; i < arr.length; i++) {
            curr_sum += arr[i];
            if(curr_sum == sum) {
                System.out.print("Sum found at " + 0 + "and " + i);
                return;
            }
            if(map.containsKey(curr_sum - sum)) {
                System.out.print("Sum found at " + map.get(curr_sum - sum) + 1 + "and " + i);
                return;
            }
            else
                map.put(curr_sum , i);
        }
    }
    // given +ve/-ve numbers, return all subarray that adds to sum k
    // input = {5, 6, 1, -2, -4, 3, 1, 5}, k = 5
    public static void allSubArraySum(int arr[], int k){
        Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
        int preSum = 0;
        List<Integer> initial = new ArrayList<Integer>();
        initial.add(-1);
        map.put(0, initial);
        // Loop across all elements of the array
        for(int i=0; i< arr.length; i++) {
            preSum += arr[i];
            // If point where sum = (preSum - k) is present, it means that between that
            // point and this, the sum has to equal k
            if(map.containsKey(preSum - k)) {   // Subarray found
                List<Integer> startIndices = map.get(preSum - k);
                for(int start : startIndices) {
                    System.out.println("Start: "+ (start+1)+ "\tEnd: "+ i);
                }
            }
            List<Integer> newStart = new ArrayList<Integer>();
            if(map.containsKey(preSum)) {
                newStart = map.get(preSum);
            }
            newStart.add(i);
            map.put(preSum, newStart);
        }
    }
    //Given +ve/-ve numbers find min subarray length that sum to k
    //input = {2,3,1,1,-1,6,4,3,8}; output = 2
    public static int minSubArraySum(int arr[], int k){
        Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
        int curr_sum = 0;
        int min_len = Integer.MAX_VALUE;
        List<Integer> _list = new ArrayList<Integer>();
        _list.add(-1);
        map.put(0, _list);
        // Loop across all elements of the array
        for(int i=0; i< arr.length; i++) {
            curr_sum += arr[i];
            // If point where sum = (preSum - k) is present, it means that between that
            // point and this, the sum has to equal k
            if(map.containsKey(curr_sum - k)) {   // Subarray found
                List<Integer> items = map.get(curr_sum - k);
                for(int start : items) {
                    //System.out.println("Start: "+ (start+1)+ "\tEnd: "+ i);
                    min_len = Math.min(min_len, (i - (start+1)) + 1);
                }
            }
            List<Integer> temp = new ArrayList<Integer>();
            if(map.containsKey(curr_sum)) {
                temp = map.get(curr_sum);
            }
            temp.add(i);
            map.put(curr_sum, temp);
        }
        return (min_len == Integer.MAX_VALUE) ? 0 : min_len;
    }
    //Find the largest subarray with 0 sum
    public static int maxSubArrayZero(int arr[]){
        // Creates an empty hashMap hM
        HashMap<Integer, Integer> hM = new HashMap<Integer, Integer>();
        int sum = 0;      // Initialize sum of elements
        int max_len = 0;  // Initialize result
        // Traverse through the given array
        for (int i = 0; i < arr.length; i++){
            // Add current element to sum
            sum += arr[i];
            if (arr[i] == 0 && max_len == 0)
                max_len = 1;
            if (sum == 0)
                max_len = i+1;
            // Look this sum in hash table
            Integer prev_i = hM.get(sum);
            // If this sum is seen before, then update max_len if required
            if (prev_i != null)
                max_len = Math.max(max_len, i-prev_i);
            else  // Else put this sum in hash table
                hM.put(sum, i);
        }
        return max_len;
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
    //Given an array, describe an algorithm to identify the subarray with the maximum sum.
    //Largest sum contiguous subarray
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
    //Smallest subarray with sum greater than a given value,  If there isn't one, return n+1 instead.
    // arr[] = {1, 4, 45, 6, 0, 19}   x  =  51   Output: 3
    public static int smallestSubWithSum(int arr[], int n, int x){
        // Initialize current sum and minimum length
        int curr_sum = 0, min_len = n + 1;
        // Initialize starting and ending indexes
        int start = 0, end = 0;
        while (end < n){
            // Keep adding array elements while current sum is smaller than x
            while (curr_sum <= x && end < n)
                curr_sum += arr[end++];
            // If current sum becomes greater than x.
            while (curr_sum > x && start < n){
                // Update minimum length if needed
                if (end - start < min_len)
                    min_len = end - start;
                // remove starting elements
                curr_sum -= arr[start++];
            }
        }
        return min_len;
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
    //Given an array of size n, the array contains numbers in range from 0 to k-1 where k is a positive integer and k <= n.
    //Find the maximum repeating (most frequent) number in this array.
    // The array elements are in range from 0 to k-1
    static int maxRepeating(int arr[], int n, int k){
        // Iterate though input array, for every element arr[i], increment arr[arr[i]%k] by k
        for (int i = 0; i< n; i++)
            arr[(arr[i]%k)] += k;
        // Find index of the maximum repeating element
        int max = arr[0], result = 0;
        for (int i = 1; i < n; i++) {
            if (arr[i] > max){
                max = arr[i];
                result = i;
            }
        }
        /* Uncomment this code to get the original array back
        for (int i = 0; i< n; i++)
          arr[i] = arr[i]%k; */
        // Return index of the maximum element
        return result;
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
    //Given a sorted array [2, 4, 4, 4, 8, 9, 9, 11], write a function to give number of elements in range [3, 10]
    //Time O(N)
    public static int findNumbersInRange(int[] a, int min, int max){
        int left = 0;
        int right = a.length - 1;
        if (min > a[right] || max < a[left])
            return 0;
        while (left < right){
            if (a[left] < min)
                left++;
            if (a[right] > max)
                right--;
            if((a[left] == min || a[left] > min) && (a[right] == max || a[right] < max))
                break;
        }
        return right - left + 1;
    }
    //Time O(2logN)
    public static int findNumbersInRange1(int[] a, int min, int max){
        if (min > a[a.length-1] || max < a[0])
            return 0;
        int index_left = binarySearchForLeftRange(a, 0, a.length - 1, min);
        int index_right = binarySearchForRightRange(a, index_left, a.length - 1, max);

        if (index_left == -1 || index_right == -1 || index_left > index_right)
            return 0;
        else
            return index_right - index_left + 1;
    }
    static int binarySearchForLeftRange(int[] a, int start, int end, int left_range){
        int low = start;
        int high = end;
        while (low <= high){
            int mid = low + ((high - low) / 2);
            if (a[mid] >= left_range)
                high = mid - 1;
            else //if(a[mid]<i)
                low = mid + 1;
        }
        return high + 1;
    }
    static int binarySearchForRightRange(int[] a, int start, int end, int right_range){
        int low = start;
        int high = end;
        while (low <= high){
            int mid = low + ((high - low) / 2);
            if (a[mid] > right_range)
                high = mid - 1;
            else //if(a[mid]<i)
                low = mid + 1;
        }
        return low - 1;
    }

    //Find duplicates in an Array which contains elements from 0 to n-1 in O(n) time and O(1) extra space
    //Note that this method modifies the original array
    static void findDuplicate(int[] arr){
        int i;
        for (i = 0; i < arr.length; i++){
            if (arr[i] == Integer.MIN_VALUE){
                if (arr[0] > 0)
                    arr[0] = -arr[0];
                else
                    System.out.print("0");
            }
            else if (arr[Math.abs(arr[i])] == 0)
                arr[Math.abs(arr[i])] = Integer.MIN_VALUE;
            else if (arr[Math.abs(arr[i])] > 0)
                arr[Math.abs(arr[i])] = -arr[Math.abs(arr[i])];
            else
                System.out.print(Math.abs(arr[i]));
        }
    }
    //find the single number that duplicates one or more times in an array in O(1) space and O(n) time without modifying the array
    //Tortoise and Hair Cycle detection algorithm (pr Floyd’s cycle-finding algorithm)
    //http://www.zrzahid.com/find-the-single-number-that-duplicates-one-or-more-times/
    public int findDuplicate1(int[] a) {
        if (a.length == 0 || a.length == 1) {
            return -1;
        }
        int slow = a[0];
        int fast = a[a[0]];
        while (slow != fast) {
            slow = a[slow];
            fast = a[a[fast]];
        }
        fast = 0;
        while (slow != fast) {
            slow = a[slow];
            fast = a[fast];
        }
        return fast;
    }
    /*Write a function that is given an array of integers and an integer k. It should return true if and only if there
    are two distinct indices i and j into the array such that arr[i] = arr[j] and the difference between i and j is at
    most k.*/
    public static boolean containsNearbyDuplicate(int[] arr, int k){ //time: O(n)  space: O(k)
        if (arr.length == 0)
            return false;
        HashSet<Integer> hs = new HashSet<Integer> ();
        for (int i=0;i<arr.length;i++) {
            if (hs.contains (arr [i])) {
                return true;
            }
            if (hs.size() >= k) {
                hs.remove (arr [i - k]);
            }
            hs.add (arr [i]);
        }
        return false;
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
        for (int i = a.length-1; i >= 0; i--) {
            prod[i] *= temp;
            temp *= a[i];
        }
        return prod;
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
    public static int findCrossOver(int arr[], int low, int high, int x) {
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
    public static void printKclosest(int arr[], int x, int k, int n) {
        // Find the crossover point
        int l = findCrossOver(arr, 0, n - 1, x);
        int r = l + 1;   // Right index to search
        int count = 0; // To keep track of count of elements already printed
        // If x is present in arr[], then reduce left index
        if (arr[l] == x) l--;
        // Compare elements on left and right of crossover point to find the k closest elements
        while (l >= 0 && r < n && count < k) {
            if (x - arr[l] < arr[r] - x) System.out.println(arr[l--]);
            else System.out.println(arr[r++]);
            count++;
        }
        // If there are no more elements on right side, then print left elements
        while (count < k && l >= 0) System.out.println(arr[l--]);
        count++;
        // If there are no more elements on left side, then print right elements
        while (count < k && r < n) System.out.println(arr[r++]);
        count++;
    }
    //Given an array consisting of only 0s and 1s, sort it. He was looking for highly optimized
    //Can also solve: the even numbers are on the left side of the array and all the odd numbers are on the right side
    public static void sort0and1(int arr[], int size){
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
    public static void sort012(int[] a) {
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
    public static void sortNegPos(int[] arr) {
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
    public static void rearrangeWithOrder(int arr[], int n){
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
    public static void rightrotate(int arr[], int n, int outofplace, int cur)
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
    public static void rearrange(int arr[], int n){
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
    public static void wiggleSort1(int[] nums) {
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
    private static void wiggleSort(int [] nums) {
        if (nums.length < 2) {
            return;
        }

        int cntForMedian = (nums.length + 1) / 2;
        int median = findKthSmallest(nums, cntForMedian, 0, nums.length - 1);
        mapSortedArrayToWiggleInPlace(nums, median);
    }
    private static void mapSortedArrayToWiggleInPlace(int[] nums, int median) {
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
    private static int indexMap(int n, int i) {
        return (2 * i - 1) % (n | 1);
    }
    private static int findKthSmallest(int [] nums, int k, int start, int end) {
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
    private static void swap1 (int []nums, int start, int end) {
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
        int i,x = 0, y = 0;
    /* Get the xor of all elements in arr[]. The xor will basically be xor of two odd occurring elements */
        for(i = 1; i < size; i++)
            xor2 = xor2 ^ arr[i];
    /* Get one set bit in the xor2. We get rightmost set bit in the following line as it is easy to get */
        set_bit_no = xor2 & ~(xor2-1);
    /* Now divide elements in two sets:
    1) The elements having the corresponding bit as 1.
    2) The elements having the corresponding bit as 0.  */
        for(i = 0; i < size; i++){
    /* XOR of first set is finally going to hold one odd occurring number x */
            if((arr[i] & set_bit_no) == 0)
                x = x ^ arr[i];
      /* XOR of second set is finally going to hold the other odd occurring number y */
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
    //You are given n activities with their start and finish times. Select the maximum number of activities that can be
    //performed by a single person, assuming that a person can only work on a single activity at a time.
    //start[]  =  {1, 3, 0, 5, 8, 5}; finish[] =  {2, 4, 6, 7, 9, 9}; Output =  {0, 1, 3, 4}
    //Use greedy algorithm. choose best option each time
    void printMaxActivities(int s[], int f[], int n){
        //sort the finish array
        Arrays.sort(f);
        int i, j;
        // The first activity always gets selected
        i = 0;
        System.out.print(i);
        // Consider rest of the activities
        for (j = 1; j < n; j++){
            // If this activity has start time greater than or equal to the finish time of previously selected
            // activity, then select it
            if (s[j] >= f[i]){
                System.out.println(j);
                i = j;
            }
        }
    }
    //There are N stations on route of a train. The train goes from station 0 to N-1. The ticket cost for all pair of
    // stations (i, j) is given where j is greater than i. Find the minimum cost to reach the destination.
    /*Input: cost[N][N] = { {0, 15, 80, 90},
            {INF, 0, 40, 50},
            {INF, INF, 0, 70},
            {INF, INF, INF, 0}
        };  The minimum cost is 65*/
    public int minCost(int ticket[][]){
        assert ticket != null && ticket.length > 0 && ticket.length == ticket[0].length;
        int T[] = new int[ticket.length];
        int T1[] = new int[ticket.length];
        T1[0] = -1;
        for(int i=1; i < T.length; i++){
            T[i] = ticket[0][i];
            T1[i] = i-1;
        }
        for(int i=1; i < T.length; i++){
            for(int j=i+1; j < T.length; j++){
                if(T[j] > T[i] + ticket[i][j]){
                    T[j] = T[i] + ticket[i][j];
                    T1[j] = i;
                }
            }
        }
        //printing actual stations
        int i = ticket.length-1;
        while(i != -1){
            System.out.print(i + " ");
            i = T1[i];
        }
        System.out.println();
        return T[ticket.length-1];
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
    /*Given an array of 0s and 1s, and k, Find the longest continuous streak of 1s after flipping k 0s to 1s.
    E.x  array is {1,1,0,0,1,1,1,0,1,1} k = 1 (which means we can flip ‘k’ one 0 to 1)
    Answer: 6 (if we flip 0 at index 7, we get the longest continuous streak of 1s having length 6)*/
    public static int findmaxOne(int []a, int k){
        int max_count = 0;
        int max_index = 0;
        int currCount = 0;
        for(int i = 0;i<a.length;i++){
            if(a[i] == 0 && max_index < k){
                currCount++;
                max_index++;
            }
            else if(a[i] == 1)
                currCount++;
            else{
                max_count = Math.max(max_count,currCount);
                max_index = 0;
                currCount = 0;
            }
        }
        max_count = Math.max(max_count,currCount);
        return max_count;
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
    //Given a sorted array, remove the duplicates in place such that each element appear only once and return the new length.
    public static int[] removeDuplicates(int[] A) {
        if (A.length < 2)
            return A;
        int j = 0;
        int i = 1;
        while (i < A.length) {
            if (A[i] == A[j]) {
                i++;
            } else {
                j++;
                A[j] = A[i];
                i++;
            }
        }
        int[] B = Arrays.copyOf(A, j + 1);
        return B;
    }
    //Follow up for "Remove Duplicates": What if duplicates are allowed at most twice?
    public int removeDuplicates1(int[] A) {
        if (A.length <= 2)
            return A.length;
        int prev = 1; // point to previous
        int curr = 2; // point to current
        while (curr < A.length) {
            if (A[curr] == A[prev] && A[curr] == A[prev - 1]) {
                curr++;
            } else {
                prev++;
                A[prev] = A[curr];
                curr++;
            }
        }
        return prev + 1;
    }
    //Given an unsorted array that may contain duplicates.returns true if array contains duplicates within k distance.
    private boolean checkDuplicatesWithinK(int a[], int k){
        HashSet<Integer> hash = new HashSet<Integer>();
        for(int i = 0; i < a.length; i++){
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
    void rearrange1(int arr[], int n){
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
                swap(arr,temp, arr[j]);
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
        int max_diff = 0;
        int min_element = prices[0];
        int i;
        for (i = 1; i < prices.length; i++) {
            if (prices[i] - min_element > max_diff) max_diff = prices[i] - min_element;
            if (prices[i] < min_element) min_element = prices[i];
        }
        return max_diff;
    }
    // Stock problem: multiple transactions are allowed. you must sell the stock before you buy again
    public int maxProfitMultiTrans(int[] arr) {
        if (arr.length == 0) {
            return 0;
        }
        int profit = 0;
        int localMin = arr[0];
        for(int i=1; i < arr.length;i++){
            if(arr[i-1] >= arr[i]){
                localMin = arr[i];
            }else{
                profit += arr[i] - localMin;
                localMin = arr[i];
            }
        }
        return profit;
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
    //Given stock prices for certain days how to maximize profit by buying or selling with at most K transactions
    //Time complexity - O(number of transactions * number of days ^ 2) Space complexity - O(number of transcations * number of days)
    public int maxProfitSlowSolution(int prices[], int K) {
        if (K == 0 || prices.length == 0) {
            return 0;
        }
        int T[][] = new int[K+1][prices.length];
        for (int i = 1; i < T.length; i++) {
            for (int j = 1; j < T[0].length; j++) {
                int maxVal = 0;
                for (int m = 0; m < j; m++) {
                    maxVal = Math.max(maxVal, prices[j] - prices[m] + T[i-1][m]);
                }
                T[i][j] = Math.max(T[i][j-1], maxVal);
            }
        }
        return T[K][prices.length - 1];
    }

    //Weighted Job Scheduling problem
    // Maximize ad revenue given a set of of advertisements with a start time, end time and revenue
    class Ad{
        int start, finish, revenue;
    }
    // Used to sort ads according to finish times
    class JobComparator implements Comparator<Ad>{
        public int compare(Ad a, Ad b){
            return a.finish < b.finish ? -1 : a.finish == b.finish ? 0 : 1;
        }
    }
    public int scheduleAds(Ad ads[]){
        // Sort ads according to finish time
        Arrays.sort(ads, new JobComparator());
        // Create an array to store solutions of subproblems. table[i] stores the profit for ads till ads[i]
        // (including ads[i])
        int n = ads.length;
        int table[] = new int[n];
        table[0] = ads[0].revenue;
        // Fill entries in table[] using recursive property
        for (int i=1; i<n; i++){
            // Find profit including the current ad
            int inclProf = ads[i].revenue;
            int l = binarySearch(ads, i);
            if (l != -1)
                inclProf += table[l];
            // Store maximum of including and excluding
            table[i] = Math.max(inclProf, table[i-1]);
        }
        return table[n-1];
    }
    /* A Binary Search based function to find the latest ad (before current ad) that doesn't conflict with current
     ad. "index" is index of the current ad.  This function returns -1 if all ads before index conflict with it.
     The array ads[] is sorted in increasing order of finish time. */
    static public int binarySearch(Ad ads[], int index){
        // Initialize 'lo' and 'hi' for Binary Search
        int lo = 0, hi = index - 1;
        // Perform binary Search iteratively
        while (lo <= hi){
            int mid = (lo + hi) / 2;
            if (ads[mid].finish <= ads[index].start){
                if (ads[mid + 1].finish <= ads[index].start)
                    lo = mid + 1;
                else
                    return mid;
            }
            else
                hi = mid - 1;
        }
        return -1;
    }
    /*Provide a set of positive integers (an array of integers). Each integer represent number of nights user request on Airbnb.com.
     If you are a host, you need to design and implement an algorithm to find out the maximum number a nights you can accommodate.
     The constraint is that you have to reserve at least one day between each request,
     input: [5, 1, 2, 6, 20, 2] => output: 27*/
    //house robbing problem. Time = O(N) space = O(1)
    public static int rob(int[] num) {
        if(num==null || num.length == 0)
            return 0;
        int even = 0;
        int odd = 0;
        for (int i = 0; i < num.length; i++) {
            if (i % 2 == 0) {
                even += num[i];
                even = even > odd ? even : odd;
            } else {
                odd += num[i];
                odd = even > odd ? even : odd;
            }
        }
        return even > odd ? even : odd;
    }
    //Using Dynamic programming Time = O(N) and Spcae = O(N)
    private static int findMaxDays(int arr[]) {
        int [] maxDaysToPos = new int[arr.length + 1];
        maxDaysToPos[0] = 0;
        maxDaysToPos[1] = arr[0];
        for (int i = 2; i < maxDaysToPos.length; i++) {
            maxDaysToPos[i] = Math.max(maxDaysToPos[i - 1], maxDaysToPos[i - 2] + arr[i - 1]);
        }
        return maxDaysToPos[maxDaysToPos.length - 1];
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
    //this function also can be used for Find the two repeating elements in a given array
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
    //Given a stream of numbers, print average (or mean) of the stream at every point.
    // Returns the new average after including x
    float getAvg(float prev_avg, int x, int n)    {
        return (prev_avg*n + x)/(n+1);
    }
    // Prints average of a stream of numbers
    void streamAvg(int arr[], int n)    {
        float avg = 0;
        for(int i = 0; i < n; i++)        {
            avg  = getAvg(avg, arr[i], i);
            System.out.println("Average of" + i + 1 + " numbers is" + avg);
        }
    }
    //Given an array of numbers, arrange them in a way that yields the largest value.
    //Input= {1, 34, 3, 98, 9, 76, 45, 4} output = 998764543431
    //we compare two numbers XY (Y appended at the end of X) and YX (X appended at the end of Y). If XY is larger,
    //then X should come before Y in output, else Y should come before.
    //Time  = O(mlgn) where n = the size of the array and m = the total number of digits across those numbers
    public static String largestNumber(int[] num) {
        if (num == null || num.length == 0) {
            return "";
        }
        String[] array = new String[num.length];
        for (int i = 0; i < num.length; i++) {
            array[i] = String.valueOf(num[i]);
        }
        Arrays.sort(array, comparator);
        String result = "";
        for (String str : array) {
            result = str + result;
        }
        return result;
    }
    public static Comparator<String> comparator = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            String comb1 = o1 + o2;
            String comb2 = o2 + o1;
            return comb1.compareTo(comb2);
        }
    };
    //Shuffle a given array. Fisher–Yates shuffle Algorithm works in O(n) time complexity.
    void randomize ( int arr[], int n ){
        // Start from the last element and swap one by one. We don't need to run for the first element that's why i > 0
        for (int i = n-1; i > 0; i--){
            // Pick a random index from 0 to i
            int j = (int) Math.random() % (i+1);
            // Swap arr[i] with the element at random index
            arr[i] ^= arr[j];
            arr[j] ^= arr[i];
            arr[i] ^= arr[j];
        }
    }
    //Given an array of strings, write a method to serialize that array into one single string, and a method to
    //deserialize the single string back into the original array
    static String serialize(String[] arr){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++){
            byte[] bLength = Integer.toString(arr[i].length()).getBytes(StandardCharsets.US_ASCII);
            byte[] bData = arr[i].getBytes(StandardCharsets.US_ASCII);
            sb.append(String.format("%8s", Integer.toBinaryString(bLength[0] & 0xFF)).replace(' ', '0'));
            for (byte item : bData)
                sb.append(String.format("%8s", Integer.toBinaryString(item & 0xFF)).replace(' ', '0'));
        }
        return sb.toString();
    }
    static String[] deSerialize(String arr){
        List<String> output = new ArrayList<String>();
        byte[] bytes = GetBytes(arr);
        for (int i = 0; i < bytes.length;){
            int length = Integer.valueOf((new String(bytes,StandardCharsets.US_ASCII)).substring(i, i+1));
            i++;
            String data = (new String(bytes,StandardCharsets.US_ASCII)).substring(i, i + length);
            i += length;
            output.add(data);
        }
        return output.toArray(new String[0]);
    }
    private static byte[] GetBytes(String bitString){
        int numBytes = bitString.length() / 8;
        if (bitString.length() % 8 != 0) numBytes++;
        byte[] bytes = new byte[numBytes];
        int byteIndex = 0, bitIndex = 0;
        for (int i = 0; i < bitString.length(); i++){
            if (bitString.charAt(i) == '1')
                bytes[byteIndex] |= (byte)(1 << (7 - bitIndex));
            bitIndex++;
            if (bitIndex == 8){
                bitIndex = 0;
                byteIndex++;
            }
        }
        return bytes;
    }
    //Given an unsorted array of numbers, write a function that returns true if array consists of consecutive numbers.
    //http://www.geeksforgeeks.org/check-if-array-elements-are-consecutive/
    boolean areConsecutive(int arr[], int n){
        if ( n <  1 )
            return false;
  /* 1) Get the minimum element in array */
        int min = getMin(arr, n);
  /* 2) Get the maximum element in array */
        int max = getMax(arr, n);
  /* 3) max - min + 1 is equal to n,  then only check all elements */
        if (max - min  + 1 == n){
      /* Create a temp array to hold visited flag of all elements. Note that, calloc is used here so that all values are initialized
         as false */
            boolean[] visited = new boolean[n];
            int i;
            for (i = 0; i < n; i++){
         /* If we see an element again, then return false */
                if ( visited[arr[i] - min] != false )
                    return false;
         /* If visited first time, then mark the element as visited */
                visited[arr[i] - min] = true;
            }
      /* If all elements occur once, then return true */
            return true;
        }
        return false; // if (max - min  + 1 != n)
    }
    int getMin(int arr[], int n){
        int min = arr[0];
        for (int i = 1; i < n; i++)
            if (arr[i] < min)
                min = arr[i];
        return min;
    }
    int getMax(int arr[], int n){
        int max = arr[0];
        for (int i = 1; i < n; i++)
            if (arr[i] > max)
                max = arr[i];
        return max;
    }
    //Sort a list of numbers in which each number is at a distance k from its actual position
    //Input = 3,4,1,2,7,8,5,6 and K=2 output = 1,2,3,4,5,6
    public static int[] SortNearlySorted(int []a, int k){
        if(k==0)
            return a;
        int count =0;
        for(int i=0;i<a.length;i++){
            Swap(i,i+k, a);
            count++;
            if(count == k) {
                i += k;
                count = 0;
            }
        }
        return a;
    }
    public static void Swap(int x, int y, int a[]){
        a[x] ^=a[y];
        a[y]^=a[x];
        a[x]^=a[y];
    }
    //Given an array of n elements, where each element is at most k away from its target position,
    //devise an algorithm that sorts in O(n log k) time.
    // Solution: 1) Create a Min Heap of size k+1 with first k+1 elements. This will take O(k) time (See this GFact)
    //2) One by one remove min element from heap, put it in result array, and add a new element to heap from remaining elements.
    //So overall complexity will be O(k) + O((n-k)*logK)

    /* Given a total and coins of certain denomination with infinite supply, what is the minimum number
    * of coins it takes to form this total.
    * Time complexity - O(coins.size * total)
    * Space complexity - O(coins.size * total) */
    public int minimumCoinBottomUp(int total, int coins[]){
        int T[] = new int[total + 1];
        int R[] = new int[total + 1];
        T[0] = 0;
        for(int i=1; i <= total; i++){
            T[i] = Integer.MAX_VALUE-1;
            R[i] = -1;
        }
        for(int j=0; j < coins.length; j++){
            for(int i=1; i <= total; i++){
                if(i >= coins[j]){
                    if (T[i - coins[j]] + 1 < T[i]) {
                        T[i] = 1 + T[i - coins[j]];
                        R[i] = j;
                    }
                }
            }
        }
        printCoinCombination(R, coins);
        return T[total];
    }
    private void printCoinCombination(int R[], int coins[]) {
        if (R[R.length - 1] == -1) {
            System.out.print("No solution is possible");
            return;
        }
        int start = R.length - 1;
        System.out.print("Coins used to form total ");
        while ( start != 0 ) {
            int j = R[start];
            System.out.print(coins[j] + " ");
            start = start - coins[j];
        }
        System.out.print("\n");
    }
    //Given a total and coins of certain denominations find number of ways total
    //can be formed from coins assuming infinity supply of coins
    //https://github.com/mission-peace/interview/blob/master/src/com/interview/dynamic/CoinChanging.java
    public int numberOfSolutionsOnSpace(int total, int arr[]){
        int temp[] = new int[total+1];
        temp[0] = 1;
        for(int i=0; i < arr.length; i++){
            for(int j=1; j <= total ; j++){
                if(j >= arr[i]){
                    temp[j] += temp[j-arr[i]];
                }
            }
        }
        return temp[total];
    }
    //This program plays the game "Fizzbuzz".  It counts to 100, replacing each multiple of 5 with the word "fizz", each
    //multiple of 7 with the word "buzz", and each multiple of both with the word "fizzbuzz".
    public static void fizzbuzz(){
        for(int i = 1; i <= 100; i++) {                    // count from 1 to 100
            if (((i % 5) == 0) && ((i % 7) == 0))            // A multiple of both?
                System.out.print("fizzbuzz");
            else if ((i % 5) == 0) System.out.print("fizz"); // else a multiple of 5?
            else if ((i % 7) == 0) System.out.print("buzz"); // else a multiple of 7?
            else System.out.print(i);                        // else just print it
            System.out.print(" ");
        }
        System.out.println();
    }
    //Maximum difference between two elements such that larger element appears after the smaller number
    //Input =  [2, 3, 10, 6, 4, 8, 1]  output = 8
    int maxDiff(int arr[], int arr_size){
        int max_diff = arr[1] - arr[0];
        int min_element = arr[0];
        int i;
        for(i = 1; i < arr_size; i++) {
            if (arr[i] - min_element > max_diff)
                max_diff = arr[i] - min_element;
            if (arr[i] < min_element)
                min_element = arr[i];
        }
        return max_diff;
    }
    //Find the minimum (index) distance sum of 3 words. For example: arr = {"2", "1", "0", "2", "0", "3", "0"},
    //input = "1","2","3". The result should be 8 since the 2nd "2" and "1", "3"'s distance are 3, 1, 5 and abs(3,1)+abs(3,5)+abs(5,1)=8.
    static int minimum_distance_sum(int[] words, int a, int b, int c) {
        int last_a = -1;
        int last_b = -1;
        int last_c = -1;
        int min_distance = Integer.MAX_VALUE;
        for (int i = 0; i < words.length; i++) {
            if (words[i] == a)
                last_a = i;
            else if (words[i] == b)
                last_b = i;
            else if (words[i] == c)
                last_c = i;
            if (last_a >= 0 && last_b >= 0 && last_c >= 0)
                min_distance = Math.min(min_distance, Math.abs(last_a-last_c)+ Math.abs(last_a-last_b)+ Math.abs(last_b-last_c));
        }
        return min_distance;
    }
    /*Given an array of numbers A = [x1, x2, ..., xn] and T = Round(x1+x2+... +xn). We want to find a way to round each
    element in A such that after rounding we get a new array B = [y1, y2, ...., yn] such that y1+y2+...+yn = T where yi = Floor(xi) or Ceil(xi), ceiling or floor of xi. We also want to minimize sum |xi-yi|
    */
    public static double[] minimizeRoundSum(double[] input, int T){
        double sum = 0;
        double [] output = new double [input.length];
        for(int i = 0; i < input.length;i++){
            sum += Math.round(input[i]);
            output[i] = Math.round(input[i]);
        }
        double diff  = T - sum;
        int  i = 0;
        while(diff > 0){
            output[i] += 1;
            diff -=1;
            i++;
        }
        return output;
    }
    //Given an array, print the Next Greater Element (NGE) for every element.
    //input = [4, 5, 2, 25} output = {(4,5),(5,25),(2,25),(25,-1)
    private static void FindNextGreaterElement(int[] array){
        Stack<Integer> stack = new java.util.Stack<Integer>();
        //1) Push the first element to stack.
        stack.push(array[0]);
        //2) Pick rest of the elements one by one and follow following steps in loop.
        for(int i = 1; i < array.length ; i++){
            //a) Mark the current element as next. array[i] == next
            //b) If stack is not empty, then pop an element from stack and compare it with next.
            if(stack.peek() < array[i]){
                //c) If next is greater than the popped element,
                //then next is the next greater element for the popped element.
                while(!stack.isEmpty()){
                    //d) Keep popping from the stack while the popped element is smaller than next.
                    //next becomes the next greater element for all such popped elements
                    Integer poppedElement = stack.pop();
                    System.out.println(poppedElement +", " + array[i]);
                }
            }
            //g) If next is smaller than the popped element, then push the popped element back
            stack.push(array[i]);
        }
        //3) After the loop in step 2 is over, pop all the elements from stack and
        //print -1 as next element for them.
        while(!stack.isEmpty()){
            System.out.println(stack.pop());
        }
    }
    //Write a class to take in a large arbitrary number, also provide a function to increment the number. The number will be passed on as an array of integers.
    public static int[] incrLargeNumber(int []inputArray) {
        if(inputArray == null) return inputArray;
        int arrLength = inputArray.length;
        // check for all 9s
        boolean all9s = true;
        for(int i=0;i<arrLength;i++) { // 0(k) --> k is the first non 9
            if(inputArray[i] != 9) {
                all9s = false;
                break;
            }
        }
        if(all9s) {
            int []newInputArray = new int[arrLength + 1];
            newInputArray[0] = 1;    // 0(1)
            for(int i=1;i<newInputArray.length;i++) { // 0(n)
                newInputArray[i] = 0;
            }
            return newInputArray;
        } else {
            for (int i = arrLength-1; i >-1; i --) { // O(k+1)  where is the first non 9
                int k = inputArray[i];
                if(k == 9) {
                    inputArray[i] = 0;
                } else {
                    inputArray[i] = ++k; // o(1)
                    return inputArray;
                }
            }
        }
        return inputArray;
    }
    //Given an array of n integers. MaxPrefix is defined as count of elements those are greater than the element and in the right side of
    //array wrt to the element. Write a program to give the max of MaxPrefix. Input: 10 -4 6 2 8 9 4 Output: 5
    public static int getMaxOfMaxPrefix(int a[]){
        if(a.length < 2)
            return 1;
        int count = 0, maxPrefix = 0,temp = a[0];
        for (int i = 1; i < a.length ; i++) {
            if(a[i] > temp)
                count++;
            else{
                temp = a[i];
                count = 0;
            }
            if(count > maxPrefix)
                maxPrefix = count;
        }
        return  maxPrefix;
    }
    //A list L is too big to fit in memory. L is partially sorted. Partially sorted in a specific way: x-sorted L[i] < L[i+x].
    // Any element is at most x indices out of position. Sort the list L
    private static int[] sortPartialList(int []a, int x){
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int[] result = new int[a.length];
        int k = 0;
        for (int i = 0; i < a.length; i++) {
            if(i < x)
                pq.add(a[i]);
            else{
                result[k] = pq.poll();
                pq.add(a[i]);
            }
        }
        while(!pq.isEmpty()){
            result[k] = pq.poll();
            k++;
        }
        return result;
    }
    //Given an array of integers, find if it’s possible to remove exactly one integer from the array that divides the array into two subarrays with the same sum.
    //Input:  arr = [6, 2, 3, 2, 1] Output:  true ( [6], and [3, 2, 1])
    public static boolean divideArray(int []a){
        int sum = 0;
        int sum_so_far = 0;
        for (int i = 0; i < a.length; i++) {
            sum+=a[i];
        }
        for (int i = 0; i < a.length; i++) {
            if(2 * sum_so_far + a[i] == sum)
                return  true;
            sum_so_far+= a[i];
        }
        return false;
    }
    //Given an array arr[], find the maximum j – i such that arr[j] > arr[i]
    public static int maxIndexDiff(int arr[], int n){
        int maxDiff;
        int i, j;
        int RMax[] = new int[n];
        int LMin[] = new int[n];
         /* Construct LMin[] such that LMin[i] stores the minimum value from (arr[0], arr[1], ... arr[i]) */
        LMin[0] = arr[0];
        for (i = 1; i < n; ++i)
            LMin[i] = Math.min(arr[i], LMin[i - 1]);
         /* Construct RMax[] such that RMax[j] stores the maximum value from (arr[j], arr[j+1], ..arr[n-1]) */
        RMax[n - 1] = arr[n - 1];
        for (j = n - 2; j >= 0; --j)
            RMax[j] = Math.max(arr[j], RMax[j + 1]);
        /* Traverse both arrays from left to right to find optimum j - i This process is similar to merge() of MergeSort */
        i = 0; j = 0; maxDiff = -1;
        while (j < n && i < n){
            if (LMin[i] < RMax[j]){
                maxDiff = Math.max(maxDiff, j - i);
                j = j + 1;
            }
            else
                i = i + 1;
        }
        return maxDiff;
    }
    //Move all zeroes to end of array
    public static void pushZerosToEnd(int arr[], int n){
        int count = 0;  // Count of non-zero elements
        // Traverse the array. If element encountered is non-zero, then replace the element at index 'count' with this element
        for (int i = 0; i < n; i++)
            if (arr[i] != 0)
                arr[count++] = arr[i]; // here count is incremented
        while (count < n)
            arr[count++] = 0;
    }
}
