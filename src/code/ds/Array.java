package code.ds;

import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import javafx.util.Pair;

/**
 * Created by Piyush Patel.
 */
public class Array {

  //https://github.com/tongzhang1994/Facebook-Interview-Coding
  public static void main(String[] args) throws InterruptedException, ExecutionException {

  }

  //Merge two sorted array into sorted array Time = O(N+M)
  public static int[] MergeArray(int[] a, int[] b) {
    int[] answer = new int[a.length + b.length];
    int i = 0, j = 0, k = 0;
    while (i < a.length && j < b.length) {
      if (a[i] < b[j]) {
        answer[k++] = a[i++];
      } else {
        answer[k++] = b[j++];
      }
    }
    while (i < a.length) {
      answer[k++] = a[i++];
    }
    while (j < b.length) {
      answer[k++] = b[j++];
    }
    return answer;
  }

  //merge k sorted list/array. We can merge arrays in O(nk*Logk) time using Min Heap space O(k)
  class Triplet {

    int pos;
    int val;
    int index;
  }

  public List<Integer> mergeUsingHeap(List<List<Integer>> chunks) {
    List<Integer> result = new ArrayList<>();
    PriorityQueue<Triplet> minHeap = new PriorityQueue<>();
    //add first element of every chunk into queue
    for (int i = 0; i < chunks.size(); i++) {
      Triplet p = new Triplet();
      p.pos = i;
      p.val = chunks.get(i).get(0);
      p.index = 0;
      minHeap.add(p);
    }
    while (!minHeap.isEmpty()) {
      Triplet p = minHeap.poll();
      result.add(p.val);
      if (p.index < chunks.get(p.pos).size()) {
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
    int indexA = lastA - 1; /* Index of last element in array a */
    int indexB = lastB - 1; /* Index of last element in array b */
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
  public int findKSmallestElement(int A[], int startA, int endA, int B[], int startB, int endB,
      int k) {
    int n = endA - startA;
    int m = endB - startB;
    if (n <= 0) {
      return B[startB + k - 1];
    }
    if (m <= 0) {
      return A[startA + k - 1];
    }
    if (k == 1) {
      return A[startA] < B[startB] ? A[startA] : B[startB];
    }
    int midA = (startA + endA) / 2;
    int midB = (startB + endB) / 2;
    if (A[midA] <= B[midB]) {
      if (n / 2 + m / 2 + 1 >= k) {
        return findKSmallestElement(A, startA, endA, B, startB, midB, k);
      } else {
        return findKSmallestElement(A, midA + 1, endA, B, startB, endB, k - n / 2 - 1);
      }
    } else {
      if (n / 2 + m / 2 + 1 >= k) {
        return findKSmallestElement(A, startA, midA, B, startB, endB, k);
      } else {
        return findKSmallestElement(A, startA, endA, B, midB + 1, endB, k - m / 2 - 1);
      }
    }
  }

  //Median of two sorted arrays
  public double findMedianSortedArrays(int A[], int B[]) {
    int lengthA = A.length;
    int lengthB = B.length;
    if ((lengthA + lengthB) % 2 == 0) {
      double r1 = (double) findKSmallestElement(A, 0, lengthA, B, 0, lengthB,
          (lengthA + lengthB) / 2);
      double r2 = (double) findKSmallestElement(A, 0, lengthA, B, 0, lengthB,
          (lengthA + lengthB) / 2 + 1);
      return (r1 + r2) / 2;
    } else {
      return findKSmallestElement(A, 0, lengthA, B, 0, lengthB, (lengthA + lengthB + 1) / 2);
    }
  }

  // Given a stream of unsorted integers, find the median element in sorted order at any given time.
  // http://www.ardendertat.com/2011/11/03/programming-interview-questions-13-median-of-integer-stream/
  // provides O(1) find heap and O(logN) insert
  public Queue<Integer> minHeap = new PriorityQueue<Integer>();
  public Queue<Integer> maxHeap = new PriorityQueue<Integer>(10, new MaxHeapComparator());
  public int numOfElements = 0;

  public void addNumberToStream(Integer num) {
    maxHeap.add(num);
    if (numOfElements % 2 == 0) {
      if (minHeap.isEmpty()) {
        numOfElements++;
        return;
      } else if (maxHeap.peek() > minHeap.peek()) {
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
    if (numOfElements % 2 != 0) {
      return new Double(maxHeap.peek());
    } else {
      return (maxHeap.peek() + minHeap.peek()) / 2.0;
    }
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
    quickselect(c, 0, c.length - 1, K - 1);
  }

  public static int quickselect(int[] G, int first, int last, int k) {
    if (first <= last) {
      //int pivot = partition(G, first, last);
      int pivot = randomPartition(G, first, last);
      if (pivot == k) {
        return G[k];
      }
      if (pivot > k) {
        return quickselect(G, first, pivot - 1, k);
      } else {
        return quickselect(G, pivot + 1, last, k);
      }
    }
    return 0;
  }

  // Picks a random pivot element between l and r and partitions
  public static int randomPartition(int arr[], int l, int r) {
    int pivot = (int) Math.round(l + Math.random() * (r - l));
    swap(arr, pivot, r);
    return partition(arr, l, r);
  }

  public static int partition(int[] G, int first, int last) {
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
  static int secondlargest(int[] a) {
    int largest = a[0];
    int secondlargest = Integer.MIN_VALUE;
    for (int i = 1; i < a.length; i++) {
      int number = a[i];
      if (number > largest) {
        secondlargest = largest;
        largest = number;
      } else if (number > secondlargest && number != largest) {
        secondlargest = number;
      }
    }
    if (secondlargest == Integer.MIN_VALUE) {
      return -1;
    } else {
      return secondlargest;
    }
  }

  //Find k maximum integers from an array of infinite integers. Find first 100 maximum numbers from billion numbers
  // Time Complexity: O(k + (n-k)Logk) without sorted output.If sorted output is needed then O(k + (n-k)Logk + kLogk)
  // (assumption is that k is significantly lesser than n)
  public static int[] getTopElements(int[] arr, int k) {
    PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();
    for (int i = 0; i < arr.length; ++i) {
      int currentNum = arr[i];
      if (minHeap.size() < k) {
        minHeap.add(currentNum);
      } else if (currentNum > minHeap.peek()) {
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
  static int countZeros(int[] arr, int n) {
    // Find index of first zero in given array
    int first = firstZero(arr, 0, n - 1);
    // If 0 is not present at all, return 0
    if (first == -1) {
      return 0;
    }
    return (n - first);
  }

  /* if 0 is present in arr[] then returns the index of FIRST occurrence
    of 0 in arr[low..high], otherwise returns -1.  Time Complexity: O(Logn)*/
  static int firstZero(int[] arr, int low, int high) {
    if (high >= low) {
      // Check if mid element is first 0
      int mid = low + (high - low) / 2;
      if ((mid == 0 || arr[mid - 1] == 1) && arr[mid] == 0) {
        return mid;
      }
      if (arr[mid] == 1)  // If mid element is not 0
      {
        return firstZero(arr, (mid + 1), high);
      } else  // If mid element is 0, but not first 0
      {
        return firstZero(arr, low, (mid - 1));
      }
    }
    return -1;
  }

  //Given a set of numbers: {1, 3, 2, 5, 4, 9}, find the number of subsets that sum to a particular value
  //Algo complexity is O(Sum * N) and use O(Sum) memory
  private static int GetmNumberOfSubsets(int[] numbers, int sum) {
    int[] dp = new int[sum + 1];
    dp[0] = 1;
    int currentSum = 0;
    for (int i = 0; i < numbers.length; i++) {
      currentSum += numbers[i];
      for (int j = Math.min(sum, currentSum); j >= numbers[i]; j--) {
        dp[j] += dp[j - numbers[i]];
      }
    }
    return dp[sum];
  }

  //Given an integer array with all positive numbers and no duplicates, find the number of possible combinations that add up to a positive integer target.
    /*nums = [1, 2, 3] target = 4 The possible combination ways are:
    (1, 1, 1, 1)
    (1, 1, 2)
    (1, 2, 1)
    (1, 3)
    (2, 1, 1)
    (2, 2)
    (3, 1)
    Note that different sequences are counted as different combinations.
    Therefore the output is 7.*/
  public int combinationSum4(int[] nums, int target) {
    int[] comb = new int[target + 1];
    comb[0] = 1;
    for (int i = 1; i < comb.length; i++) {
      for (int j = 0; j < nums.length; j++) {
        if (i - nums[j] >= 0) {
          comb[i] += comb[i - nums[j]];
        }
      }
    }
    return comb[target];
  }

  //Given sorted array find two number sum to target, Each number can be used multiple times.
  public static int[] twoSumSortedArray(int[] A, int target) {
    int left = 0, right = A.length - 1;
    while (left < right) {
      int s = A[left] + A[right];
      //if(A[left] == 0 || A[right] == 0)
      //    return new int[]{0, 0};
      if (A[left] == target / 2 || A[right] == target / 2) {
        return new int[]{target / 2, target / 2};
      }
      if (s == target) {
        return new int[]{A[left], A[right]};
      } else if (s > target) {
        right--;
      } else {
        left++;
      }
    }
    return null;
  }

  //k-Sum problem, Time = O(N^k)
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
      for (List<Integer> item : kSum(num, k - 1, target - num[i], i + 1)) {
        item.add(0, i);
        result.add(item);
      }
    }
    return result;
  }

  //Given an array of integers, find two numbers such that they add up to a specific target number. 2Sum
  //Time = O(n) space O(n)
  public ArrayList<Integer> twoSum(int[] numbers, int target) {
    HashMap<Integer, Integer> map = new HashMap<>();
    ArrayList<Integer> result = new ArrayList<>();
    for (int i = 0; i < numbers.length; i++) {
      if (map.containsKey(numbers[i])) {
        int index = map.get(numbers[i]);
        result.add(index + 1);
        result.add(i + 1);
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
  Set<Integer> set = new HashSet<>();
  List<Integer> nums = new ArrayList<>();

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
  int countPairsWithDiffK(int arr[], int n, int k) {
    int count = 0;
    Arrays.sort(arr);  // Sort array elements
    int l = 0;
    int r = 0;
    while (r < n) {
      if (arr[r] - arr[l] == k) {
        count++;
        l++;
        r++;
      } else if (arr[r] - arr[l] > k) {
        l++;
      } else // arr[r] - arr[l] < sum
      {
        r++;
      }
    }
    return count;
  }

  /*Given an unsorted array of integers, find all the pairs that they add up to a specific target number. The array may contain duplicated elements.
    The output should not contain duplicated pairs, and each pair needs to be in ascending order, e.g., [1, 2] instead of [2, 1].*/
  public static List<List<Integer>> twoSumWithDuplicates(int[] num, int target) {
    List<List<Integer>> _list = new ArrayList<>();
    int n = num.length;
    if (n < 2) {
      return _list;
    }
    // value and number of times element present
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < n; i++) {
      int k1 = num[i], k2 = target - num[i];
      if (map.containsKey(k2) && map.get(k2) > 0) {
        List<Integer> list;
        if (k1 < k2) {
          list = Arrays.asList(k1, k2);
        } else {
          list = Arrays.asList(k2, k1);
        }
        if (!_list.contains(list)) {
          _list.add(list);
        }
        map.put(k2, map.get(k2) - 1);
      } else {
        if (!map.containsKey(k1)) {
          map.put(k1, 1);
        } else {
          map.put(k1, map.get(k1) + 1);
        }
      }
    }
    return _list;
  }

  //Given a set S of n unique integers, are there elements a, b, c in S such that a + b + c = 0? Find all unique triplets in
  //the set which gives the sum of zero. 3Sum problem
  //For example, given set S = {-1 0 1 2 -1 -4}, One possible solution set is:  (-1, 0, 1)   (-1, 2, -1) Time = O(n^2)
  public static void find_triplets(int arr[]) {
    Arrays.sort(arr);
    //List<ArrayList<Integer>> triplets = new ArrayList<ArrayList<Integer>>();
    int n = arr.length;
    for (int i = 0; i < n; i++) {
      int j = i + 1;
      int k = n - 1;
      if (i > 0 && arr[i] == arr[i - 1]) {
        continue;
      }
      while (j < k) {
        //ArrayList<Integer> triplet = new ArrayList<>();
        int sum_two = arr[i] + arr[j];
        if (sum_two + arr[k] < 0) {
          j++;
        } else if (sum_two + arr[k] > 0) {
          k--;
        } else {
          //triplet.add(0, arr[i]);
          //triplet.add(1, arr[j]);
          //triplet.add(2, arr[k]);
          System.out.println(arr[i] + " " + arr[j] + " " + arr[k]);
          while (j < k && arr[j] == arr[j + 1]) {
            j++;
          }
          while (j < k && arr[k] == arr[k - 1]) {
            k--;
          }
          //System.out.println(i +" "+  j + " " + k);
          //triplets.add(triplet);
          j++;
          k--;
        }
      }
    }
    //return triplets;
  }

  public static void find_tripletsDuplicates(int arr[]) {
    HashSet<List<Integer>> set = new HashSet<>();
    for (int i = 0; i < arr.length; i++) {
      ArrayList<ArrayList<Integer>> out = twoSumWithDuplicatesIndex(arr, -arr[i], i);
      for (List<Integer> list : out) {
        list.add(i);
        Collections.sort(list);
        if (!set.contains(list)) {
          System.out.println(list.get(0) + " " + list.get(1) + " " + list.get(2));
          set.add(list);
        }
      }
    }
  }

  public static ArrayList<ArrayList<Integer>> twoSumWithDuplicatesIndex(int[] num, int target,
      int exclude) {
    ArrayList<ArrayList<Integer>> _list = new ArrayList<ArrayList<Integer>>();
    int n = num.length;
    if (n < 2) {
      return _list;
    }
    // value and number of times element present
    HashMap<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < n; i++) {
      if (i == exclude) {
        continue;
      }
      int k1 = num[i], k2 = target - num[i];
      if (map.containsKey(k2)) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(i);
        list.add(map.get(k2));
        _list.add(list);
      } else {
        if (!map.containsKey(k1)) {
          map.put(k1, i);
        }
      }
    }
    return _list;
  }

  //Given an array S of n integers, find three integers in S such that the sum is closest to a given number, target.
  //Return the sum of the three integers. 3Sum Closest. Time = O(n^2)
  public static int threeSumClosest(int[] num, int target) {
    Arrays.sort(num);
    int min = Integer.MAX_VALUE, result = 0;
    for (int i = 0; i < num.length; i++) {
      int j = i + 1;
      int k = num.length - 1;
      while (j < k) {
        int sum = num[i] + num[j] + num[k];
        int diff = Math.abs(sum - target);
        if (diff == 0) {
          return sum;
        }
        if (diff < min) {
          min = diff;
          result = sum;
        }
        if (sum <= target) {
          j++;
        } else {
          k--;
        }
      }
    }
    return result;
  }

  //Determine if any 3 integers in an array sum to 0. Each number can be used multiple times.
  public static int[] threeSum_Multiple(int arr[]) {
    Arrays.sort(arr);
    int n = arr.length;
    for (int i = 0; i < n; i++) {
      if (arr[i] == 0) {
        return new int[]{0, 0, 0};
      }
      int j = i;
      int k = n - 1;
      while (j < k) {
        int sum = arr[i] + arr[j] + arr[k];
        if (sum < 0) {
          j++;
        } else if (sum > 0) {
          k--;
        } else {
          return new int[]{arr[i], arr[j], arr[k]};
          //j++;
          //k--;
        }
      }
    }
    return null;
  }

  //3sum smaller Input: nums = [-2,0,1,3], and target = 2 Output: 2 Time = O(n^2)
  //Given an array of n integers nums and a target, find the number of index triplets i, j, k
  //with 0 <= i < j < k < n that satisfy the condition nums[i] + nums[j] + nums[k] < target.
  public static int threeSumSmaller(int[] nums, int target) {
    Arrays.sort(nums);
    int sum = 0;
    for (int i = 0; i < nums.length - 2; i++) {
      sum += twoSumSmaller(nums, i + 1, target - nums[i]);
    }
    return sum;
  }

  private static int twoSumSmaller(int[] nums, int startIndex, int target) {
    int sum = 0;
    int left = startIndex;
    int right = nums.length - 1;
    while (left < right) {
      if (nums[left] + nums[right] < target) {
        sum += right - left;
        left++;
      } else {
        right--;
      }
    }
    return sum;
  }


  //Given an unsorted array of integers, find a subarray which adds to a given number.
  // If there are more than one subarrays with sum as the given number, print any of them.
  //arr[] = {1, 4, 20, 3, 10, 5}, sum = 33  output = true
  //time complexity is O(2n) only positive numbers
  public static void subArraySumPositive(int[] A, int target) {
    for (int i = 0, j = 0, sum = 0; i < A.length; i++) {
      for (; j < A.length && sum < target; j++) {
        sum += A[j];
      }
      if (sum == target) {
        System.out.print("Start index: " + i + " End index: " + (j - 1));
        return;
      }
      sum -= A[i];
    }
    System.out.print("No SubArray Found.");
  }

  public static boolean isValid(int[] a, int sum) {
    int count = 0, temp = 0;
    for (int i = 0; i < a.length; i++) {
      temp += a[i];
      while (temp > sum) {
        temp -= a[count];
        count++;
      }
      if (temp == sum) {
        return true;
      }
    }
    return false;
  }

  //Given an unsorted array of positive and negative integers, find a subarray which adds to a given number. handles negative numbers as well
  public static void subArraySum(int arr[], int sum) {
    HashMap<Integer, Integer> map = new HashMap<>();
    int curr_sum = 0;
    for (int i = 0; i < arr.length; i++) {
      curr_sum += arr[i];
      if (curr_sum == sum) {
        System.out.print("Sum found at " + 0 + "and " + i);
        return;
      }
      if (map.containsKey(curr_sum - sum)) {
        System.out.print("Sum found at " + map.get(curr_sum - sum) + 1 + "and " + i);
        return;
      } else {
        map.put(curr_sum, i);
      }
    }
  }

  // given +ve/-ve numbers, return all subarray that adds to sum k
  // input = {5, 6, 1, -2, -4, 3, 1, 5}, k = 5
  public static void allSubArraySum(int arr[], int k) {
    Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
    int preSum = 0;
    List<Integer> initial = new ArrayList<Integer>();
    initial.add(-1);
    map.put(0, initial);
    // Loop across all elements of the array
    for (int i = 0; i < arr.length; i++) {
      preSum += arr[i];
      // If point where sum = (preSum - k) is present, it means that between that
      // point and this, the sum has to equal k
      if (map.containsKey(preSum - k)) {   // Subarray found
        List<Integer> startIndices = map.get(preSum - k);
        for (int start : startIndices) {
          System.out.println("Start: " + (start + 1) + "\tEnd: " + i);
        }
      }
      List<Integer> newStart = new ArrayList<Integer>();
      if (map.containsKey(preSum)) {
        newStart = map.get(preSum);
      }
      newStart.add(i);
      map.put(preSum, newStart);
    }
  }

  //Given an array unique nums and a target value k, find the maximum length of a subarray that sums to k. If there isn't one, return 0 instead.
  //arr = [1, -1, 5, -2, 3], k = 3 Output = 4 (subarray [1, -1, 5, -2])
  public static int maxSubArraySumLen(int[] arr, int k) {
    HashMap<Integer, Integer> map = new HashMap<>();
    int max = 0;
    int curr_sum = 0;
    for (int i = 0; i < arr.length; i++) {
      curr_sum += arr[i];
      if (curr_sum == k) {
        max = Math.max(max, (i - 0) + 1);
      }
      if (map.containsKey(curr_sum - k)) {
        max = Math.max(max, (i - map.get(curr_sum - k)));
      } else {
        map.put(curr_sum, i);
      }
    }
    return max;
  }

  //Given +ve/-ve numbers with duplicates find min subarray length that sum to k
  //input = {2,3,1,1,-1,6,4,3,8}; output = 2
  public static int minSubArraySum(int arr[], int k) {
    Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
    int curr_sum = 0;
    int min_len = Integer.MAX_VALUE;
    List<Integer> _list = new ArrayList<Integer>();
    _list.add(-1);
    map.put(0, _list);
    // Loop across all elements of the array
    for (int i = 0; i < arr.length; i++) {
      curr_sum += arr[i];
      // If point where sum = (curr_sum - k) is present, it means that between that
      // point and this, the sum has to equal k
      if (map.containsKey(curr_sum - k)) {   // Subarray found
        List<Integer> items = map.get(curr_sum - k);
        for (int start : items) {
          //System.out.println("Start: "+ (start+1)+ "\tEnd: "+ i);
          min_len = Math.min(min_len, (i - start));
        }
      }
      List<Integer> temp = new ArrayList<Integer>();
      if (map.containsKey(curr_sum)) {
        temp = map.get(curr_sum);
      }
      temp.add(i);
      map.put(curr_sum, temp);
    }
    return (min_len == Integer.MAX_VALUE) ? 0 : min_len;
  }

  //Find the largest subarray with 0 sum
  public static int maxSubArrayZero(int arr[]) {
    // Creates an empty hashMap hM
    HashMap<Integer, Integer> hM = new HashMap<Integer, Integer>();
    int sum = 0;      // Initialize sum of elements
    int max_len = 0;  // Initialize result
    // Traverse through the given array
    for (int i = 0; i < arr.length; i++) {
      // Add current element to sum
      sum += arr[i];
      if (arr[i] == 0 && max_len == 0) {
        max_len = 1;
      }
      if (sum == 0) {
        max_len = i + 1;
      }
      // Look this sum in hash table
      Integer prev_i = hM.get(sum);
      // If this sum is seen before, then update max_len if required
      if (prev_i != null) {
        max_len = Math.max(max_len, i - prev_i);
      } else  // Else put this sum in hash table
      {
        hM.put(sum, i);
      }
    }
    return max_len;
  }

  //find the sum of contiguous sub array within a one-dimensional array of numbers with negative which has the largest sum .
  // input {-2, -3, 4, -1, -2, 1, 5, -3} output = 7
  private static int maxSubArraySum(int a[]) {
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
  private static int[] findMaxSumIndex(int[] arr) {
    int[] result = new int[3];
    int max_so_far = Integer.MIN_VALUE;
    int startIndex = 0;
    int curr_max = 0;
    for (int i = 0; i < arr.length; i++) {
      curr_max = curr_max + arr[i];
      if (curr_max > max_so_far) {
        max_so_far = curr_max;
        result[0] = startIndex; // start index
        result[1] = i;              // end index
        result[2] = max_so_far;  // largest sum
      }
      if (curr_max < 0) {
        curr_max = 0;
        startIndex = i + 1;
      }
    }
    return result;
  }

  //max subarray sum for stream of input
  static List<Integer> _stream = new ArrayList<>();
  static int[] _out = new int[3];
  static int max_so_far = Integer.MIN_VALUE;
  static int curr_max = 0;
  static int startIndex = 0;

  private static void maxSubArraySumOfStreamNum(int num) {
    _stream.add(num);
    if (curr_max + num > max_so_far) {
      curr_max = 0;
      for (int i = startIndex; i < _stream.size(); i++) {
        curr_max = curr_max + _stream.get(i);
        if (curr_max > max_so_far) {
          max_so_far = curr_max;
          _out[0] = startIndex; // start index
          _out[1] = i;              // end index
          _out[2] = max_so_far;  // largest sum
        }
        if (curr_max < 0) {
          curr_max = 0;
          startIndex = i + 1;
        }
      }
    }
  }

  private static int[] getMaxSubArraySum() {
    return _out;
  }

  /* Given an array of integers and a number k, find k non-overlapping subarrays which have the largest sum.
  The number in each subarray should be contiguous. Return the largest sum.*/
  static int maxKSubArray(int[] nums, int k) {
    if (nums.length < k) {
      return 0;
    }
    int len = nums.length;
    //d[i][j]: select j subarrays from the first i elements, the max sum we can get.
    int[] d = new int[len + 1];
    for (int j = 1; j <= k; j++) {
      for (int i = len; i >= j; i--) {
        d[i] = Integer.MIN_VALUE;
        int endMax = 0;
        int max = Integer.MIN_VALUE;
        for (int p = i - 1; p >= j - 1; p--) {
          endMax = Math.max(nums[p], endMax + nums[p]);
          max = Math.max(endMax, max);
          if (d[i] < d[p] + max) {
            d[i] = d[p] + max;
          }
        }
      }
    }
    return d[len];
  }

  /*For a given array, find the subarray (containing at least k number) which has the largest sum. try to do it in O(n) time
      Followup, if input is stream, how to solve it
        Example: [-4, -2, 1, -3], k = 2, return -1, and the subarray is [-2, 1]
         [1, 1, 1, 1, 1, 1], k = 3, return 6, and the subarray is [1, 1, 1, 1, 1, 1] */
  static int maxSubArrayWithK(int[] a, int k) {
    int i, maxendhere = 0, maxsofar, sumoflastk;
    Set<Integer> set = new HashSet<>();
    for (i = 0; i < k; i++) {
      maxendhere += a[i];
      set.add(a[i]);
    }
    maxsofar = maxendhere;
    sumoflastk = maxendhere;
    for (; i < a.length; i++) {
      if (set.contains(a[i])) {
        maxendhere += a[i];
      }
      sumoflastk += a[i] - a[i - k];
      if (maxendhere < sumoflastk) {
        maxendhere = sumoflastk;
      }
      if (maxsofar < maxendhere) {
        maxsofar = maxendhere;
      }
      set.add(a[i]);
    }
    return maxsofar;
  }

  // Returns maximum sum in a subarray of size k.
  public static int maxSum(int arr[], int n, int k) {
    // k must be greater
    if (n < k) {
      System.out.println("Invalid");
      return -1;
    }
    // Compute sum of first window of size k
    int res = 0;
    for (int i = 0; i < k; i++) {
      res += arr[i];
    }
    // Compute sums of remaining windows by removing first element of previous window and adding last element of
    // current window.
    int curr_sum = res;
    for (int i = k; i < n; i++) {
      curr_sum += arr[i] - arr[i - k];
      res = Math.max(res, curr_sum);
    }
    return res;
  }
  /*Maximum Sum of 3 Non-Overlapping Subarrays size of k
  Input: [1,2,1,2,6,7,5,1], 2
  Output: [0, 3, 5]
  Explanation: Subarrays [1, 2], [2, 6], [7, 5] correspond to the starting indices [0, 3, 5].
  We could have also taken [2, 1], but an answer of [1, 3, 5] would be lexicographically larger.*/



  /* Sum of max M subarray(Non Overlapping) of size K. you have given array of Size N and two numbers M, K. K is size of subarray and M is count of subarray.
     You have to return sum of max M subarray of size K (non-overlapping)
     N = 7, M = 3 , K = 1  A={2 10 7 18 5 33 0} = 61 ,  subsets are: 33, 18, 10 (top M of size K)
     M=2,K=2 {3,2,100,1} = 106 - subsets are: (3,2), (100,1) 2 subsets of size 2
  */

  //Smallest subarray with sum greater than a given value,  If there isn't one, return n+1 instead.
  // arr[] = {1, 4, 45, 6, 0, 19}   x  =  51   Output: 3
  public static int smallestSubWithSum(int arr[], int n, int x) {
    // Initialize current sum and minimum length
    int curr_sum = 0, min_len = n + 1;
    // Initialize starting and ending indexes
    int start = 0, end = 0;
    while (end < n) {
      // Keep adding array elements while current sum is smaller than x
      while (curr_sum <= x && end < n) {
        curr_sum += arr[end++];
      }
      // If current sum becomes greater than x.
      while (curr_sum > x && start < n) {
        // Update minimum length if needed
        if (end - start < min_len) {
          min_len = end - start;
        }
        // remove starting elements
        curr_sum -= arr[start++];
      }
    }
    return min_len;
  }

  //find the length of longest increasing subarray
  //{1,3,2,4,5} output = 3
  int findlen(int[] a) {
    int min = a[0];
    int max_len = 1;
    int count = 1;
    for (int i = 1; i < a.length; i++) {
      if (a[i] > min) {
        count++;
      } else {
        max_len = Math.max(max_len, count);
        count = 1;
      }
      min = a[i];
    }
    max_len = Math.max(max_len, count);
    return max_len;
  }

  //longest increasing subsequence Time O(NlogN)
  static int CeilIndex(int A[], int l, int r, int key) {
    while (r - l > 1) {
      int m = l + (r - l) / 2;
      if (A[m] >= key) {
        r = m;
      } else {
        l = m;
      }
    }
    return r;
  }

  static int LongestIncreasingSubsequenceLength(int A[], int size) {
    // Add boundary case, when array size is one
    int[] temp = new int[size];
    int len; // always points empty slot
    temp[0] = A[0];
    len = 1;
    for (int i = 1; i < size; i++) {
      if (A[i] < temp[0])
      // new smallest value
      {
        temp[0] = A[i];
      } else if (A[i] > temp[len - 1])
      // A[i] wants to extend largest subsequence
      {
        temp[len++] = A[i];
      } else
      // A[i] wants to be current end candidate of an existing subsequence. It will replace ceil value in tailTable
      {
        temp[CeilIndex(temp, -1, len - 1, A[i])] = A[i];
      }
    }
    return len;
  }

  // Method to find length of longest bitonic subarray
  //A[] = {12, 4, 78, 90, 45, 23}, the maximum length bitonic subarray is {4, 78, 90, 45, 23} which is of length 5.
  static int maxLenBitonicSubArray(int[] A) {
    int n = A.length;
    if (n == 0) {
      return 0;
    }
    int end_index = 0, max_len = 0;
    int i = 0;
    while (i + 1 < n) {
      int len = 1;
      // run till sequence is increasing
      while (i + 1 < n && A[i] < A[i + 1]) {
        i++;
        len++;
      }
      // run till sequence is decreasing
      while (i + 1 < n && A[i] > A[i + 1]) {
        i++;
        len++;
      }
      // update Longest Bitonic Subarray if required
      if (len > max_len) {
        max_len = len;
        end_index = i;
      }
    }
    return max_len;
  }
    /* Given an array of integers, find the length of longest subsequence which is first increasing then decreasing.  **Example: **
    For the given array [1 11 2 10 4 5 2 1] Longest subsequence is [1 2 10 4 2 1] Return value 6 */


  //Maximum Sum Increasing Subsequence
  static int maxSumIS(int arr[], int n) {
    int i, j, max = 0;
    int msis[] = new int[n];
    /* Initialize msis values for all indexes */
    for (i = 0; i < n; i++) {
      msis[i] = arr[i];
    }
    /* Compute maximum sum values in bottom up manner */
    for (i = 1; i < n; i++) {
      for (j = 0; j < i; j++) {
        if (arr[i] > arr[j]) {
          msis[i] = Math.max(msis[j] + arr[i], msis[i]);
          max = Math.max(max, msis[i]);
        }
      }
    }
    return max;
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
      if (max_so_far < max_ending_here) {
        max_so_far = max_ending_here;
      }
    }
    return max_so_far;
  }

  //Given an array of size n, the array contains numbers in range from 0 to k-1 where k is a positive integer and k <= n.
  //Find the maximum repeating (most frequent) number in this array.
  // The array elements are in range from 0 to k-1
  static int maxRepeating(int arr[], int n, int k) {
    // Iterate though input array, for every element arr[i], increment arr[arr[i]%k] by k
    for (int i = 0; i < n; i++) {
      arr[(arr[i] % k)] += k;
    }
    // Find index of the maximum repeating element
    int max = arr[0], result = 0;
    for (int i = 1; i < n; i++) {
      if (arr[i] > max) {
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

  //Given an integer array, find the most frequent number and it's count in the array. Write the code in O(1) space
  static String mostFrequent(int[] a) {
    Arrays.sort(a);
    int count = 1;
    int maxCount = 1;
    int num = a[0];
    int maxNum = a[0];
    for (int i = 1; i < a.length; i++) {
      if (num == a[i]) {
        count++;
        if (count > maxCount) {
          maxCount = count;
          maxNum = a[i];
        }
      } else {
        count = 1;
        num = a[i];
      }
    }

    return maxNum + ": " + maxCount;
  }

  //Write a program to find the element in an array that is repeated more than half number of times.
  // Return -1 if no such element is found. Time complexity = O(n), aux space O(1)
  int MoreThanHalfElem(int a[]) {
    /* Find the candidate for Majority*/
    int cand = findCandidate(a);
    /* Print the candidate if it is Majority*/
    if (isMajority(a, cand)) {
      return cand;
    }
    return -1;
  }

  /* Function to find the candidate for Majority, based on Moore�s Voting Algorithm.*/
  int findCandidate(int a[]) {
    int maj_index = 0, count = 1;
    int i;
    for (i = 1; i < a.length; i++) {
      if (a[maj_index] == a[i]) {
        count++;
      } else {
        count--;
      }
      if (count == 0) {
        maj_index = i;
        count = 1;
      }
    }
    return a[maj_index];
  }

  /* Function to check if the candidate occurs more than n/2 times */
  boolean isMajority(int a[], int cand) {
    int i, count = 0;
    for (i = 0; i < a.length; i++) {
      if (a[i] == cand) {
        count++;
      }
    }
    if (count > a.length / 2) {
      return true;
    } else {
      return false;
    }
  }

  /* Rotate elements in an array to left k times */
  static void leftRotate(int arr[], int k) {
    int n = arr.length;
    rvereseArray(arr, 0, k - 1);
    rvereseArray(arr, k, n - 1);
    rvereseArray(arr, 0, n - 1);
  }

  static void rightRotate(int arr[], int k) {
    int n = arr.length;
    rvereseArray(arr, 0, n - k - 1);
    rvereseArray(arr, n - k, n - 1);
    rvereseArray(arr, 0, n - 1);
  }

  /*Function to reverse arr[] from index start to end*/
  static void rvereseArray(int arr[], int start, int end) {
    while (start < end) {
      arr[start] ^= arr[end];
      arr[end] ^= arr[start];
      arr[start] ^= arr[end];
      start++;
      end--;
    }
  }

  //Searching an Element in a Rotated Sorted Array. no duplicates
  private static int rotated_binary_search(int nums[], int N, int key) {
    int left = 0;
    int right = N - 1;
    while (left <= right) {
      // Avoid overflow, same as M=(L+R)/2
      int mid = left + ((right - left) / 2);
      if (nums[mid] == key) {
        return mid;
      }
      // the bottom half is sorted
      if (nums[left] <= nums[mid]) {
        if (nums[left] <= key && key < nums[mid]) {
          right = mid - 1;
        } else {
          left = mid + 1;
        }
      }
      // the upper half is sorted
      else {
        if (key > nums[mid] && key <= nums[right]) {
          left = mid + 1;
        } else {
          right = mid - 1;
        }
      }
    }
    return -1;
  }

  //search target in rotated sorted array with duplicates allowed
  private static boolean rotatedArrayWithDuplicates(int nums[], int target) {
    int left = 0, right = nums.length - 1, mid;
    while (left <= right) {
      mid = (left + right) >> 1;
      if (nums[mid] == target) {
        return true;
      }
      // the only difference from the first one, tricky case, just update left and right
      if ((nums[left] == nums[mid]) && (nums[right] == nums[mid])) {
        ++left;
        --right;
      } else if (nums[left] <= nums[mid]) {
        if ((nums[left] <= target) && (nums[mid] > target)) {
          right = mid - 1;
        } else {
          left = mid + 1;
        }
      } else {
        if ((nums[mid] < target) && (nums[right] >= target)) {
          left = mid + 1;
        } else {
          right = mid - 1;
        }
      }
    }
    return false;
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
    } else if (a[left]
        == a[mid]) { // Left is either all repeats OR loops around (with the right half being all dups)
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
  private int findMin(int arr[], int low, int high) {
    // If there is only one element left
    if (high == low) {
      return arr[low];
    }
    // Find mid
    int mid = low + (high - low) / 2; /*(low + high)/2;*/
    // Check if element (mid+1) is minimum element. Consider the cases like {3, 4, 5, 1, 2}
    if (mid < high && arr[mid + 1] < arr[mid]) {
      return arr[mid + 1];
    }
    // Check if mid itself is minimum element
    if (mid > low && arr[mid] < arr[mid - 1]) {
      return arr[mid];
    }
    // Decide whether we need to go to left half or right half
    if (arr[high] > arr[mid]) {
      return findMin(arr, low, mid - 1);
    }
    return findMin(arr, mid + 1, high);
  }

  // The function that handles duplicates.  It can be O(n) in worst case.
  //Input: {3, 3, 3, 4, 4, 4, 4, 5, 3, 3} output = 3
  static int findMinDuplicate(int arr[], int low, int high) {
    // This condition is needed to handle the case when array is not rotated at all
    if (high < low) {
      return arr[0];
    }
    // If there is only one element left
    if (high == low) {
      return arr[low];
    }
    // Find mid
    int mid = low + (high - low) / 2; /*(low + high)/2;*/
    // Check if element (mid+1) is minimum element. Consider the cases like {1, 1, 0, 1}
    if (mid < high && arr[mid + 1] < arr[mid]) {
      return arr[mid + 1];
    }
    // This case causes O(n) time
    if (arr[low] == arr[mid] && arr[high] == arr[mid]) {
      return Math.min(findMinDuplicate(arr, low, mid - 1), findMinDuplicate(arr, mid + 1, high));
    }
    // Check if mid itself is minimum element
    if (mid > low && arr[mid] < arr[mid - 1]) {
      return arr[mid];
    }
    // Decide whether we need to go to left half or right half
    if (arr[high] > arr[mid]) {
      return findMinDuplicate(arr, low, mid - 1);
    }
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
      if (a[i] < b[j] && a[i] < c[k]) {
        i++;
      } else if (b[j] < a[i] && b[j] < c[k]) {
        j++;
      } else {
        k++;
      }
    }
    System.out.print(a[index1] + " " + b[index2] + " " + c[index3]);
  }

  //Given a sorted array with duplicates and a positive number, find the range in the
  //form of (startIndex, endIndex) of that number. find_range({0 2 3 3 3 10 10}, 3) should return (2,4).Time = O(logn)
  static int[] findRange(int a[], int num) {
    int i; // index of first occurrence of x in arr[0..n-1]
    int j; // index of last occurrence of x in arr[0..n-1]
    /* get the index of first occurrence of x */
    i = first(a, 0, a.length - 1, num, a.length);
    /* If x doesn't exist in arr[] then return -1 */
    if (i == -1) {
      return null;
    }
    /* Else get the index of last occurrence of x. Note that we are only looking in the subarray after first occurrence */
    j = last(a, i, a.length - 1, num, a.length);
    int[] output = {i, j};
    return output;
  }

  /* if x is present in arr[] then returns the index of FIRST occurrence of x in arr[0..n-1], otherwise returns -1 */
  static int first(int arr[], int low, int high, int x, int n) {
    if (high >= low) {
      int mid = (low + high) / 2;  /*low + (high - low)/2;*/
      if ((mid == 0 || x > arr[mid - 1]) && arr[mid] == x) {
        return mid;
      } else if (x > arr[mid]) {
        return first(arr, (mid + 1), high, x, n);
      } else {
        return first(arr, low, (mid - 1), x, n);
      }
    }
    return -1;
  }

  /* if x is present in arr[] then returns the index of LAST occurrence of x in arr[0..n-1], otherwise returns -1 */
  static int last(int arr[], int low, int high, int x, int n) {
    if (high >= low) {
      int mid = low + (high - low) / 2;
      if ((mid == n - 1 || x < arr[mid + 1]) && arr[mid] == x) {
        return mid;
      } else if (x < arr[mid]) {
        return last(arr, low, (mid - 1), x, n);
      } else {
        return last(arr, (mid + 1), high, x, n);
      }
    }
    return -1;
  }

  //Given a sorted array [2, 4, 4, 4, 8, 9, 9, 11], write a function to give number of elements in range [3, 10]
  //Time O(N)
  public static int findNumbersInRange(int[] a, int min, int max) {
    int left = 0;
    int right = a.length - 1;
    if (min > a[right] || max < a[left]) {
      return 0;
    }
    while (left < right) {
      if (a[left] < min) {
        left++;
      }
      if (a[right] > max) {
        right--;
      }
      if ((a[left] == min || a[left] > min) && (a[right] == max || a[right] < max)) {
        break;
      }
    }
    return right - left + 1;
  }

  //Time O(2logN)
  public static int findNumbersInRange1(int[] a, int min, int max) {
    if (min > a[a.length - 1] || max < a[0]) {
      return 0;
    }
    int index_left = binarySearchForLeftRange(a, 0, a.length - 1, min);
    int index_right = binarySearchForRightRange(a, index_left, a.length - 1, max);

    if (index_left == -1 || index_right == -1 || index_left > index_right) {
      return 0;
    } else {
      return index_right - index_left + 1;
    }
  }

  static int binarySearchForLeftRange(int[] a, int start, int end, int left_range) {
    int low = start;
    int high = end;
    while (low <= high) {
      int mid = low + ((high - low) / 2);
      if (a[mid] >= left_range) {
        high = mid - 1;
      } else //if(a[mid]<i)
      {
        low = mid + 1;
      }
    }
    return high + 1;
  }

  static int binarySearchForRightRange(int[] a, int start, int end, int right_range) {
    int low = start;
    int high = end;
    while (low <= high) {
      int mid = low + ((high - low) / 2);
      if (a[mid] > right_range) {
        high = mid - 1;
      } else //if(a[mid]<i)
      {
        low = mid + 1;
      }
    }
    return low - 1;
  }

  //Find duplicates in an Array which contains elements from 0 to n-1 in O(n) time and O(1) extra space
  //Note that this method modifies the original array
  static void findDuplicate(int[] arr) {
    int i;
    for (i = 0; i < arr.length; i++) {
      if (arr[i] == Integer.MIN_VALUE) {
        if (arr[0] > 0) {
          arr[0] = -arr[0];
        } else {
          System.out.println("0");
        }
      } else if (arr[Math.abs(arr[i])] == 0) {
        arr[Math.abs(arr[i])] = Integer.MIN_VALUE;
      } else if (arr[Math.abs(arr[i])] > 0) {
        arr[Math.abs(arr[i])] = -arr[Math.abs(arr[i])];
      } else {
        System.out.println(Math.abs(arr[i]));
      }
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
  public static boolean containsNearbyDuplicate(int[] arr, int k) { //time: O(n)  space: O(k)
    if (arr.length == 0) {
      return false;
    }
    HashSet<Integer> hs = new HashSet<Integer>();
    for (int i = 0; i < arr.length; i++) {
      if (hs.contains(arr[i])) {
        return true;
      }
      if (hs.size() >= k) {
        hs.remove(arr[i - k]);
      }
      hs.add(arr[i]);
    }
    return false;
  }

  //Given an array arr[] of n integers, construct a Product Array prod[] (Self Excluding)
  //such that prod[i] is equal to the product of all the elements of arr[] except arr[i].
  //Solve it without division operator and in O(n). e.g. [3, 1, 4, 2] => [8, 24, 6, 12]
  private static int[] selfExcludingProduct(int a[]) {
    int temp = 1;
    int[] prod = new int[a.length];
    for (int i = 0; i < a.length; i++) {
      prod[i] = temp;
      temp *= a[i];
    }
    temp = 1;
    for (int i = a.length - 1; i >= 0; i--) {
      prod[i] *= temp;
      temp *= a[i];
    }
    return prod;
  }

  // find common intersection of two sorted array
  static void printIntersection(int arr1[], int arr2[], int m, int n) {
    int i = 0, j = 0;
    while (i < m && j < n) {
      if (arr1[i] < arr2[j]) {
        i++;
      } else if (arr2[j] < arr1[i]) {
        j++;
      } else {
        System.out.print(arr2[j++] + " ");
        i++;
      }
    }
  }

  //print union of two sorted array
  static int printUnion(int arr1[], int arr2[], int m, int n) {
    int i = 0, j = 0;
    while (i < m && j < n) {
      if (arr1[i] < arr2[j]) {
        System.out.print(arr1[i++] + " ");
      } else if (arr2[j] < arr1[i]) {
        System.out.print(arr2[j++] + " ");
      } else {
        System.out.print(arr2[j++] + " ");
        i++;
      }
    }
    /* Print remaining elements of the larger array */
    while (i < m) {
      System.out.print(arr1[i++] + " ");
    }
    while (j < n) {
      System.out.print(arr2[j++] + " ");
    }
    return 0;
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
      else if (ar1[i] < ar2[j]) {
        i++;
      }
      // y < z
      else if (ar2[j] < ar3[k]) {
        j++;
      }
      // We reach here when x > y and z < y, i.e., z is smallest
      else {
        k++;
      }
    }
  }

  //Given an unsorted array arr[] and two numbers x and y, find the minimum distance between x and y in arr[]
  //arr[] = {3, 4, 5}, x = 3, y = 5 Minimum distance between 3 and 5 is 2
  int minDist(int arr[], int n, int x, int y) {
    int i = 0;
    int min_dist = Integer.MAX_VALUE;
    int prev = 0;
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
        } else {
          prev = i;
        }
      }
    }
    return min_dist;
  }

  //Find the first repeating element in an array of integers. O(n)
  private int findFirstRepeating(int a[]) {
    int min = -1;
    HashSet<Integer> _hash = new HashSet<Integer>();
    for (int i = a.length; i > 0; i--) {
      if (_hash.contains(a[i])) {
        min = i;
      } else {
        _hash.add(a[i]);
      }
    }
    return a[min];
  }

  //Find k closest elements to a given value. Assumption: all elements in arr[] are distinct. Time = O(k) + O(logn)
  //Input: K = 4, X = 35 arr[] = {12, 16, 22, 30, 35, 39, 42,45, 48, 50, 53, 55, 56} Output: 30 39 42 45
  //Function to find the cross over point (the point before which elements are smaller than or equal to x and after
  //which greater than x)
  public static int findCrossOver(int arr[], int low, int high, int x) {
    if (arr[high] <= x) // x is greater than all
    {
      return high;
    }
    if (arr[low] > x)  // x is smaller than all
    {
      return low;
    }
    int mid = (low + high) / 2;  /* low + (high - low)/2 */
    /* If x is same as middle element, then return mid */
    if (arr[mid] <= x && arr[mid + 1] > x) {
      return mid;
    }
    if (arr[mid] < x) {
      return findCrossOver(arr, mid + 1, high, x);
    }
    return findCrossOver(arr, low, mid - 1, x);
  }

  // This function prints k closest elements to x in arr[]. n is the number of elements in arr[]
  public static void printKclosest(int arr[], int x, int k, int n) {
    // Find the crossover point
    int l = findCrossOver(arr, 0, n - 1, x);
    int r = l + 1;   // Right index to search
    int count = 0; // To keep track of count of elements already printed
    // If x is present in arr[], then reduce left index
    if (arr[l] == x) {
      l--;
    }
    // Compare elements on left and right of crossover point to find the k closest elements
    while (l >= 0 && r < n && count < k) {
      if (x - arr[l] < arr[r] - x) {
        System.out.println(arr[l--]);
      } else {
        System.out.println(arr[r++]);
      }
      count++;
    }
    // If there are no more elements on right side, then print left elements
    while (count < k && l >= 0) {
      System.out.println(arr[l--]);
    }
    count++;
    // If there are no more elements on left side, then print right elements
    while (count < k && r < n) {
      System.out.println(arr[r++]);
    }
    count++;
  }

  //Given an array consisting of only 0s and 1s, sort it. He was looking for highly optimized
  //Can also solve: the even numbers are on the left side of the array and all the odd numbers are on the right side
  public static void sort0and1(int arr[], int size) {
    int left = 0, right = size - 1;
    while (left < right) {
      while (arr[left] == 0 && left < right) {
        left++;
      }
      while (arr[right] == 1 && left < right) {
        right--;
      }
      if (left < right) {
        arr[left] = 0;
        arr[right] = 1;
      }
    }
  }

  //Sort an array of 0s,1s,2s. Input={0, 1, 1, 0, 1, 2, 1, 2, 0, 0, 0, 1} Output={0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2}
  public static int[] sort012(int[] a) {
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
    return a;
  }

  //Given an array of n objects with k different colors (numbered from 1 to k),
  // sort them so that objects of the same color are adjacent, with the colors in the order 1, 2, ... k.
  //Method I: O(k) space, O(n) time; two-pass algorithm, counting sort
  public void sortKColors2TwoPass(int[] colors, int k) {
    int[] count = new int[k];
    for (int color : colors) {
      count[color - 1]++;
    }
    int index = 0;
    for (int i = 0; i < k; i++) {
      while (count[i] > 0) {
        colors[index++] = i + 1;
        count[i]--;
      }
    }
  }

  /**
   * Method II: Each time sort the array into three parts: [all min] [all unsorted others] [all
   * max], then update min and max and sort the [all unsorted others] with the same method.
   */
  public void sortKColors2(int[] colors, int k) {
    int pl = 0;
    int pr = colors.length - 1;
    int i = 0;
    int min = 1, max = k;
    while (min < max) {
      while (i <= pr) {
        if (colors[i] == min) {
          swap(colors, pl, i);
          i++;
          pl++;
        } else if (colors[i] == max) {
          swap(colors, pr, i);
          pr--;
        } else {
          i++;
        }
        // printArray(colors);
      }
      i = pl;
      min++;
      max--;
    }
  }

  //Give you an array which has n integers,it has both  positive and negative integers.Now you need sort this array
  //in a special way.After that,the negative integers should in the front,and the positive integers should in the back.
  //Also the relative position should not be changed.eg. -1 1 3 -2 2 ans: -1 -2 1 3 2. should be Time= O(n) and Space O(1)
  //below function doest not maintain the order of positive numbers
  public static void sortNegPos(int[] arr) {
    int left = 0, right = arr.length - 1;
    while (left < right) {
      while (arr[left] < 0 && left < right) {
        left++;
      }
      while (arr[right] > 0 && left < right) {
        right--;
      }
      if (left < right) {
        swap(arr, left, right);
      }
    }
  }

  //Time: O(N), Space O(1)
  //Given an array of positive and negative numbers, arrange them in an alternate fashion such that every
  //positive number is followed by negative and vice-versa maintaining the order of appearance.
  //input = {1, 2, 3, -4, -1, 4}  Output: arr[] = {-4, 1, -1, 2, 3, 4}
  public static void rearrangeWithOrder(int arr[], int n) {
    int outofplace = -1;
    for (int index = 0; index < n; index++) {
      if (outofplace >= 0) {
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
            || ((arr[index] < 0) && (arr[outofplace] >= 0))) {
          rightrotate(arr, n, outofplace, index);
          // the new out-of-place entry is now 2 steps ahead
          if (index - outofplace > 2) {
            outofplace = outofplace + 2;
          } else {
            outofplace = -1;
          }
        }
      }
      // if no entry has been flagged out-of-place
      if (outofplace == -1) {
        // check if current entry is out-of-place
        if (((arr[index] >= 0) && (index % 2 == 0)) || ((arr[index] < 0) && (index % 2 != 0))) {
          outofplace = index;
        }
      }
    }
  }

  // Utility function to right rotate all elements between [outofplace, cur]
  public static void rightrotate(int arr[], int n, int outofplace, int cur) {
    int tmp = arr[cur];
    for (int i = cur; i > outofplace; i--) {
      arr[i] = arr[i - 1];
    }
    arr[outofplace] = tmp;
  }

  //Rearrange positive and negative numbers in O(n) time and O(1) extra space
  //input array is [-1, 2, -3, 4, 5, 6, -7, 8, 9] output should be [9, -7, 8, -3, 5, -1, 2, 4, 6]
  //Rearrange the array elements so that positive and negative numbers are placed alternatively. Number of positive
  //and negative numbers need not be equal. If there are more positive numbers they appear at the end of the array.
  //If there are more negative numbers, they too appear in the end of the array.
  public static void rearrange(int arr[], int n) {
    // The following few lines are similar to partition process of QuickSort.  The idea is to consider 0 as pivot and
    // divide the array around it.
    int i = -1;
    for (int j = 0; j < n; j++) {
      if (arr[j] < 0) {
        i++;
        swap(arr, arr[i], arr[j]);
      }
    }
    // Now all positive numbers are at end and negative numbers at the beginning of array. Initialize indexes for starting point
    // of positive and negative numbers to be swapped
    int pos = i + 1, neg = 0;
    // Increment the negative index by 2 and positive index by 1, i.e.,
    // swap every alternate negative number with next positive number
    while (pos < n && neg < pos && arr[neg] < 0) {
      swap(arr, arr[neg], arr[pos]);
      pos++;
      neg += 2;
    }
  }

  //Given a set of distinct unsorted integers s1, s2, .., sn how do you arrange integers such that s1 <= s2 >= s3 <= s4.
  // without order maintaining. wiggle-sort
  public static int[] wiggleSort(int[] nums) {
    if (nums == null || nums.length <= 1) {
      return nums;
    }
    for (int i = 0; i < nums.length - 1; i++) {
      if (i % 2 == 0) {
        if (nums[i] > nums[i + 1]) {
          swap(nums, i, i + 1);
        }
      } else {
        if (nums[i] < nums[i + 1]) {
          swap(nums, i, i + 1);
        }
      }
    }
    return nums;
  }

  //Given an unsorted array nums, reorder it such that   nums[0] < nums[1] > nums[2] < nums[3]....
  public void wiggleSortll(int[] nums) {
    //int median = findKthLargest(nums, (nums.length + 1) / 2);
    int median = 0;
    int n = nums.length;

    int left = 0, i = 0, right = n - 1;

    while (i <= right) {

      if (nums[newIndex(i, n)] > median) {
        swap(nums, newIndex(left++, n), newIndex(i++, n));
      } else if (nums[newIndex(i, n)] < median) {
        swap(nums, newIndex(right--, n), newIndex(i, n));
      } else {
        i++;
      }
    }
  }

  private int newIndex(int index, int n) {
    return (1 + 2 * index) % (n | 1);
  }

  //Find the two numbers with odd occurrences in an unsorted array
  //Input: {12, 23, 34, 12, 12, 23, 12, 45}
  //Output: 34 and 45
  void printTwoOdd(int arr[], int size) {
    int xor2 = arr[0]; /* Will hold XOR of two odd occurring elements */
    int set_bit_no;  /* Will have only single set bit of xor2 */
    int i, x = 0, y = 0;
    /* Get the xor of all elements in arr[]. The xor will basically be xor of two odd occurring elements */
    for (i = 1; i < size; i++) {
      xor2 = xor2 ^ arr[i];
    }
    /* Get one set bit in the xor2. We get rightmost set bit in the following line as it is easy to get */
    set_bit_no = xor2 & ~(xor2 - 1);
    /* Now divide elements in two sets:
    1) The elements having the corresponding bit as 1.
    2) The elements having the corresponding bit as 0.  */
    for (i = 0; i < size; i++) {
      /* XOR of first set is finally going to hold one odd occurring number x */
      if ((arr[i] & set_bit_no) == 0) {
        x = x ^ arr[i];
      }
      /* XOR of second set is finally going to hold the other odd occurring number y */
      else {
        y = y ^ arr[i];
      }
    }
    System.out.print(x + " " + y);
  }

  //longest consecutive subsequence
  //Given an array of integers, find the length of the longest sub-sequence such that elements in the subsequence
  //are consecutive integers, the consecutive numbers can be in any order.
  //Input = {1, 9, 3, 10, 4, 20, 2}; output = 4
  static int findLongestConseqSubseq(int arr[]) {
    HashSet<Integer> map = new HashSet<>();
    int MaxCount = 0;
    int count = 1;
    // Hash all the array elements
    for (int i = 0; i < arr.length; ++i) {
      map.add(arr[i]);
    }
    // check each possible sequence from the start then update optimal length
    for (int i = 0; i < arr.length; ++i) {
      // if current element is the starting element of a sequence
      if (!map.contains(arr[i] - 1)) {
        // Then check for next elements in the sequence
        int temp = arr[i] + 1;
        while (map.contains(temp)) {
          temp++;
          count++;
        }
        MaxCount = Math.max(count, MaxCount);
        count = 1;
      }
    }
    return MaxCount;
  }

  //Given an array of distinct integers, find length of the longest subarray which contains numbers that can be
  //arranged in a continuous sequence. input = {14, 12, 11, 20}; output = 2
  int findLength(int arr[], int n) {
    int max_len = 1;  // Initialize result
    for (int i = 0; i < n - 1; i++) {
      // Initialize min and max for all subarrays starting with i
      int mn = arr[i], mx = arr[i];
      // Consider all subarrays starting with i and ending with j
      for (int j = i + 1; j < n; j++) {
        // Update min and max in this subarray if needed
        mn = Math.min(mn, arr[j]);
        mx = Math.max(mx, arr[j]);
        // If current subarray has all contiguous elements
        if ((mx - mn) == j - i) {
          max_len = Math.max(max_len, mx - mn + 1);
        }
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
        if (set.contains(a[j])) {
          break;
        }
        set.add(a[j]);
        mn = Math.min(mn, a[j]);
        mx = Math.max(mx, a[j]);
        if (mx - mn == j - i) {
          max_len = Math.max(max_len, mx - mn + 1);
        }
      }
    }
    return max_len;
  }

  //Given two sorted arrays and a number x, find the pair whose sum is closest to x and the pair has an element from each array.
  //ar1[] = {1, 4, 5, 7}; ar2[] = {10, 20, 30, 40};    x = 32     Output:  1 and 30
  void printClosest(int ar1[], int ar2[], int m, int n, int x) {
    int diff = Integer.MAX_VALUE;
    int res_l = 0, res_r = 0;
    int l = 0, r = n - 1;
    while (l < m && r >= 0) {
      // If this pair is closer to x than the previously
      // found closest, then update res_l, res_r and diff
      if (Math.abs(ar1[l] + ar2[r] - x) < diff) {
        res_l = l;
        res_r = r;
        diff = Math.abs(ar1[l] + ar2[r] - x);
      }
      // If sum of this pair is more than x, move to smaller side
      if (ar1[l] + ar2[r] > x) {
        r--;
      } else  // move to the greater side
      {
        l++;
      }
    }
    System.out.print(ar1[res_l] + " " + ar2[res_r]);
  }

  //You are given n activities with their start and finish times. Select the maximum number of activities that can be
  //performed by a single person, assuming that a person can only work on a single activity at a time.
  //start[]  =  {1, 3, 0, 5, 8, 5}; finish[] =  {2, 4, 6, 7, 9, 9}; Output =  {0, 1, 3, 4}
  //Use greedy algorithm. choose best option each time
  void printMaxActivities(int s[], int f[], int n) {
    //sort the finish array
    Arrays.sort(f);
    int i, j;
    // The first activity always gets selected
    i = 0;
    System.out.print(i);
    // Consider rest of the activities
    for (j = 1; j < n; j++) {
      // If this activity has start time greater than or equal to the finish time of previously selected
      // activity, then select it
      if (s[j] >= f[i]) {
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
  public int minCost(int ticket[][]) {
    assert ticket != null && ticket.length > 0 && ticket.length == ticket[0].length;
    int T[] = new int[ticket.length];
    int T1[] = new int[ticket.length];
    T1[0] = -1;
    for (int i = 1; i < T.length; i++) {
      T[i] = ticket[0][i];
      T1[i] = i - 1;
    }
    for (int i = 1; i < T.length; i++) {
      for (int j = i + 1; j < T.length; j++) {
        if (T[j] > T[i] + ticket[i][j]) {
          T[j] = T[i] + ticket[i][j];
          T1[j] = i;
        }
      }
    }
    //printing actual stations
    int i = ticket.length - 1;
    while (i != -1) {
      System.out.print(i + " ");
      i = T1[i];
    }
    System.out.println();
    return T[ticket.length - 1];
  }

  //Given arrival and departure times of all trains that reach a railway station, find the minimum number of platforms
  //required for the railway station so that no train waits.
  //Input:  arr[]  = {9:00,  9:40, 9:50,  11:00, 15:00, 18:00} O(nLogn) time.
  //dep[]          = {9:10, 12:00, 11:20, 11:30, 19:00, 20:00}
  //Output: 3 There are at-most three trains at a time (time between 11:00 to 11:20)
  int findPlatform(int arr[], int dep[], int n) {
    // Sort arrival and departure arrays
    Arrays.sort(arr);
    Arrays.sort(dep);
    // plat_needed indicates number of platforms needed at a time
    int plat_needed = 1, result = 1;
    int i = 1, j = 0;
    while (i < n && j < n) {
      // If next event in sorted order is arrival, increment count of
      // platforms needed
      if (arr[i] < dep[j]) {
        plat_needed++;
        i++;
        if (plat_needed > result)  // Update result if needed
        {
          result = plat_needed;
        }
      } else // Else decrement count of platforms needed
      {
        plat_needed--;
        j++;
      }
    }
    return result;
  }

  //Find Index of 0 to be replaced with 1 to get longest continuous sequence of 1s in a binary array
  //Input: arr[] =  {1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1} Output:Index 9
  int maxOnesIndex(int arr[], int n) {
    int max_count = 0;  // for maximum number of 1 around a zero
    int max_index = -1;      // for storing result
    int prev_zero = -1;  // index of previous zero
    int prev_prev_zero = -1; // index of previous to previous zero
    for (int curr = 0; curr < n; curr++) {
      // If current element is 0, then calculate the difference between curr and prev_prev_zero
      if (arr[curr] == 0) {
        // Update result if count of 1s around prev_zero is more
        if (curr - prev_prev_zero > max_count) {
          max_count = curr - prev_prev_zero;
          max_index = prev_zero;
        }
        // Update for next iteration
        prev_prev_zero = prev_zero;
        prev_zero = curr;
      }
    }
    // Check for the last encountered zero
    if (n - prev_prev_zero > max_count) {
      max_index = prev_zero;
    }
    return max_index;
  }

  /*Given an array of 0s and 1s, and k, Find the longest continuous streak of 1s after flipping k 0s to 1s.
    E.x  array is {1,1,0,0,1,1,1,0,1,1} k = 1 (which means we can flip ‘k’ one 0 to 1)
    Answer: 6 (if we flip 0 at index 7, we get the longest continuous streak of 1s having length 6)*/
  public static int findmaxOne(int[] a, int k) {
    int max_count = 0;
    int max_index = 0;
    int currCount = 0;
    for (int i = 0; i < a.length; i++) {
      if (a[i] == 0 && max_index < k) {
        currCount++;
        max_index++;
      } else if (a[i] == 1) {
        currCount++;
      } else {
        max_count = Math.max(max_count, currCount);
        max_index = 0;
        currCount = 0;
      }
    }
    max_count = Math.max(max_count, currCount);
    return max_count;
  }

  //Given a set of n nuts of different sizes and n bolts of different sizes.
  //There is a one-one mapping between nuts and bolts. Match nuts and bolts efficiently.
  //Nuts and bolts are represented as array of characters
  //char nuts[] = {'@', '#', '$', '%', '^', '&'};
  //char bolts[] = {'$', '%', '&', '^', '@', '#'};
  //Method which works just like quick sort Time = O(nLogn)
  // can be solved by hashMap in O(N)
  private static void matchPairs(char[] nuts, char[] bolts, int low, int high) {
    if (low < high) {
      //Choose last character of bolts array for nuts partition.
      int pivot = partition(nuts, low, high, bolts[high]);
      //Now using the partition of nuts choose that for bolts partition.
      partition(bolts, low, high, nuts[pivot]);
      //Recur for [low...pivot-1] & [pivot+1...high] for nuts and bolts array.
      matchPairs(nuts, bolts, low, pivot - 1);
      matchPairs(nuts, bolts, pivot + 1, high);
    }
  }

  // Similar to standard partition method. Here we pass the pivot element
  // too instead of choosing it inside the method.
  private static int partition(char[] arr, int low, int high, char pivot) {
    int i = low;
    char temp1, temp2;
    for (int j = low; j < high; j++) {
      if (arr[j] < pivot) {
        temp1 = arr[i];
        arr[i] = arr[j];
        arr[j] = temp1;
        i++;
      } else if (arr[j] == pivot) {
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

  //Given array remove duplicates items from unsorted array.
  public static int[] removeDup(int[] a) {
    HashSet<Integer> set = new HashSet<>();
    int k = 0;
    if (a.length < 2) {
      return a;
    }
    for (int i = 0; i < a.length; i++) {
      if (!set.contains(a[i])) {
        a[k] = a[i];
        k++;
        set.add(a[i]);
      }
    }
    return Arrays.copyOf(a, k);
  }

  //Given a sorted array, remove the duplicates in place such that each element appear only once and return the new length.
  // You can also use Binary Search for Avg Time O(logn)
  //http://stackoverflow.com/questions/26958118/finding-unique-numbers-from-sorted-array-in-less-than-on
  public static int[] removeDuplicates(int[] A) {
    if (A.length < 2) {
      return A;
    }
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
  // for upto K modify if (i < k || n > nums[i-k])
  public static int removeDuplicates1(int[] nums) {
    int i = 0;
    for (int n : nums) {
      if (i < 2 || n > nums[i - 2]) {
        nums[i++] = n;
      }
    }
    return i;
  }

  //Remove duplicates from unsorted array
  //find unique integers from list of integers. Input = {1,2,3,4,6,2,3,4,5} out = {1,5,6}
  public static List<Integer> findUnique(int[] a) {
    HashMap<Integer, Integer> map = new HashMap<>();
    List<Integer> out = new ArrayList<>();
    for (int i = 0; i < a.length; i++) {
      if (map.containsKey(a[i])) {
        map.put(a[i], map.get(a[i]) + 1);
      } else {
        map.put(a[i], 1);
      }
    }
    for (Map.Entry<Integer, Integer> e : map.entrySet()) {
      if (e.getValue() == 1) {
        out.add(e.getKey());
      }
    }
    return out;
  }

  //find unique integers from sorted list of integers. Input = {1,2,2,2,3,3,4,4,5} out = {1,5}
  public static void findUniqueSorted(int[] nums) {
    int count = 0;
    for (int i = 0; i < nums.length; i++) {
      if (i == nums.length - 1) {
        if (count == 0) {
          System.out.println(nums[i]);
        }
        break;
      }
      if (nums[i] != nums[i + 1]) {
        if (count == 0) {
          System.out.println(nums[i]);
        }
        count = 0;
      } else {
        count++;
      }
    }
  }

  // find unique numbers from sorted array in less than O(N). using Binary search. works well when lots of duplicates
  // average complexity is O(logn) worst case is O(N)
  public static List<Integer> findUniqueNumbers(int[] data) {
    List<Integer> result = new LinkedList<Integer>();
    for (int i = 0; i < data.length; ) {
      int temp = last(data, i, data.length - 1, data[i], data.length);
      if (i == temp) {
        result.add(data[i]);
        i++;
      } else {
        i = temp + 1;
      }
    }
    return result;
  }

  //Given an unsorted array that may contain duplicates.returns true if array contains duplicates within k distance.
  private boolean checkDuplicatesWithinK(int a[], int k) {
    HashSet<Integer> hash = new HashSet<Integer>();
    for (int i = 0; i < a.length; i++) {
      if (hash.contains(a[i])) {
        return true;
      }
      hash.add(a[i]);
      if (i >= k) {
        hash.remove(a[i - k]);
      }
    }
    return false;
  }

  //Given an array of non-negative integers, you are initially positioned at the first index of the array.
  //Each element in the array represents your maximum jump length at that position. Determine if you are able to reach the last index
  public boolean canJump(int[] A) {
    if (A.length <= 1) {
      return true;
    }
    int max = A[0]; //max stands for the largest index that can be reached.
    for (int i = 0; i < A.length; i++) {
      //if not enough to go to next
      if (max <= i && A[i] == 0) {
        return false;
      }
      //update max
      if (i + A[i] > max) {
        max = i + A[i];
      }
      //max is enough to reach the end
      if (max >= A.length - 1) {
        return true;
      }
    }
    return false;
  }

  //Given an array of integers where each element represents the max number of steps that can be made forward from that element.
  //Write a function to return the minimum number of jumps to reach the end of the array
  //Returns minimum number of jumps to reach arr[n-1] from arr[0].
  //Input: arr[] = {1, 3, 5, 8, 9, 2, 6, 7, 6, 8, 9}
  //Output: 3 (1-> 3 -> 8 ->9)
  public int minJump(int[] nums) {
    if (nums == null || nums.length == 0) {
      return 0;
    }
    int lastReach = 0;
    int reach = 0;
    int step = 0;
    for (int i = 0; i <= reach && i < nums.length; i++) {
      //when last jump can not read current i, increase the step by 1
      if (i > lastReach) {
        step++;
        lastReach = reach;
      }
      //update the maximal jump
      reach = Math.max(reach, nums[i] + i);
    }
    if (reach < nums.length - 1) {
      return 0;
    }
    return step;
  }

  //Given an array and a value, how to implement a function to remove all instances of that value in place and return the new length?
  //input - {4, 3, 2, 1, 2, 3, 6} and 2 output: 5
  public int removeNumber(int[] A, int n) {

    if (A == null || A.length == 0) {
      return 0;
    }
    int i = 0;
    for (int j = 0; j < A.length; j++) {
      if (A[j] != n) {
        A[i++] = A[j];
      }
    }
    return i; // The new dimension of the array
  }

  //Given an array of numbers and a sliding window size, how to get the maximal numbers in all sliding windows?
  //Maximum of all subarrays of size k
  //input: {2, 3, 4, 2, 6, 2, 5, 1} output: {4, 4, 6, 6, 6, 5}
  //Time  = O(n) and Aux Space complexity O(K)
  void findMaxSliding(int[] x, int k) {
    Deque<Integer> q = new ArrayDeque<Integer>();
    int i = 0;
    for (; i < k; i++) {
      // For every element, the previous smaller elements are useless so remove them from q
      while (!q.isEmpty() && x[q.peekLast()] <= x[i]) {
        q.removeLast();
      }
      q.addLast(i); // Add new element at rear of queue
    }
    for (; i < x.length; ++i) {
      // The element at the front of the queue is the largest element of previous window, so print it
      System.out.println(x[q.peek()]);
      // Remove the elements which are out of this window
      while (!q.isEmpty() && q.peekFirst() <= i - k) {
        q.removeFirst();// Remove from rear
      }
      // Remove all elements smaller than the currently being added element (remove useless elements)
      while (!q.isEmpty() && x[q.peekLast()] <= x[i]) {
        q.removeLast();
      }
      q.addLast(i);
    }
    System.out.println(x[q.peek()]);
  }

  //Given an unsorted array, sort it in such a way that the first element is the largest value, the second element is the smallest,
  //the third element is the second largest element and so on. [2, 4, 3, 5, 1] ->  [5, 1, 4, 2, 3]
  //Given a sorted array of positive integers, rearrange the array alternately
  //i.e first element should be maximum value, second minimum value, third second max, fourth second min and so on.
  //Time = O(n)
  static void rearrange0(int arr[], int n) {
    Arrays.sort(arr);
    // initialize index of first minimum and first maximum element
    int max_idx = n - 1, min_idx = 0;
    // store maximum element of array
    int max_elem = arr[n - 1] + 1;
    // traverse array elements
    for (int i = 0; i < n; i++) {
      // at even index : we have to put maximum element
      if (i % 2 == 0) {
        arr[i] += (arr[max_idx] % max_elem) * max_elem;
        max_idx--;
      }
      // at odd index : we have to put minimum element
      else {
        arr[i] += (arr[min_idx] % max_elem) * max_elem;
        min_idx++;
      }
    }
    // array elements back to it's original form
    for (int i = 0; i < n; i++) {
      arr[i] = arr[i] / max_elem;
    }
  }

  void rearrange1(int arr[], int n) {
    for (int i = 0; i < n; i++) {
      int temp = arr[i];
      // If number is negative then we have already processed it. Else process all numbers which
      // are to be replaced by each other in cyclic way
      while (temp > 0) {
        // Find the index where arr[i] should go
        int j = (i < n / 2) ? 2 * i + 1 : 2 * (n - 1 - i);
        // If arr[i] is already at its correct position, mark it as negative
        if (i == j) {
          arr[i] = -temp;
          break;
        }
        // Swap the number 'temp' with the current number at its target position
        swap(arr, temp, arr[j]);
        // Mark the number as processed
        arr[j] = -arr[j];
        // Next process the previous number at target position
        i = j;
      }
    }
    // Change the number to original value
    for (int i = 0; i < n; i++) {
      arr[i] = -arr[i];
    }
  }

  //price array of a given stock on day i.If you were only permitted to complete at most one transaction find the
  // maximum profit.
  public static int maxProfit(int[] prices) {
    if (prices == null || prices.length < 2) {
      return 0;
    }
    int max_diff = 0;
    int min_element = prices[0];
    int i;
    for (i = 1; i < prices.length; i++) {
      if (prices[i] - min_element > max_diff) {
        max_diff = prices[i] - min_element;
      }
      if (prices[i] < min_element) {
        min_element = prices[i];
      }
    }
    return max_diff;
  }

  // Stock problem: multiple transactions are allowed. you must sell the stock before you buy again
  public static int maxProfitMultiTrans(int[] arr) {
    if (arr.length == 0) {
      return 0;
    }
    int profit = 0;
    int localMin = arr[0];
    for (int i = 1; i < arr.length; i++) {
      if (arr[i - 1] >= arr[i]) {
        localMin = arr[i];
      } else {
        profit += arr[i] - localMin;
        localMin = arr[i];
      }
    }
    return profit;
  }

  //one more solution
  public static int maxProfit2(int[] prices) {
    int profit = 0;
    for (int i = 1; i < prices.length; i++) {
      int diff = prices[i] - prices[i - 1];
      if (diff > 0) {
        profit += diff;
      }
    }
    return profit;
  }

  //Say you have an array for which the ith element is the price of a given stock on day i.
  // Design an algorithm to find the maximum profit. You may complete at most two transactions.
  public static int maxProfit3(int[] prices) {
    int maxProfit1 = 0;
    int maxProfit2 = 0;
    int lowestBuyPrice1 = Integer.MAX_VALUE;
    int lowestBuyPrice2 = Integer.MAX_VALUE;

    for (int p : prices) {
      maxProfit2 = Math.max(maxProfit2, p - lowestBuyPrice2);
      lowestBuyPrice2 = Math.min(lowestBuyPrice2, p - maxProfit1);
      maxProfit1 = Math.max(maxProfit1, p - lowestBuyPrice1);
      lowestBuyPrice1 = Math.min(lowestBuyPrice1, p);
    }
    return maxProfit2;
  }

  //Given stock prices for certain days how to maximize profit by buying or selling with at most K transactions
  //Time complexity - O(number of transactions * number of days) Space complexity - O(number of transcations * number of days)
  public static int maxProfitAtMostKTrans(int prices[], int k) {
    // table to store results of subproblems profit[t][i] stores maximum profit using atmost
    // t transactions up to day i (including day i)
    int n = prices.length;
    int profit[][] = new int[k + 1][n + 1];

    // For day 0, you can't earn money irrespective of how many times you trade
    for (int i = 0; i <= k; i++) {
      profit[i][0] = 0;
    }
    // profit is 0 if we don't do any transation (i.e. k =0)
    for (int j = 0; j <= n; j++) {
      profit[0][j] = 0;
    }

    // fill the table in bottom-up fashion
    for (int i = 1; i <= k; i++) {
      int prevDiff = Integer.MIN_VALUE;
      for (int j = 1; j < n; j++) {
        prevDiff = Math.max(prevDiff, profit[i - 1][j - 1] - prices[j - 1]);
        profit[i][j] = Math.max(profit[i][j - 1], prices[j] + prevDiff);
      }
    }
    return profit[k][n - 1];
  }

  //stock max profit with commision fee. Design an algorithm to find the maximum profit. You may complete as many
  //transactions as you like (ie, buy one and sell one share of the stock multiple times). However, you may not engage
  //in multiple transactions at the same time (ie, you must sell the stock before you buy again).
  // [1, 3, 7, 5, 10, 3], 3 out = 6 [1, 4, 6, 2, 8, 3, 10, 14], 3 out = 13
  private static int stockWithFees(int prices[], int fee) {
    int afterBuy = -prices[0];
    int afterSell = 0;
    for (int i = 1; i < prices.length; i++) {
      int oldBuy = afterBuy;
      int oldSell = afterSell;
      afterBuy = Math.max(oldBuy, oldSell - prices[i]);
      afterSell = Math.max(oldSell, oldBuy + prices[i] - fee);
    }
    return afterSell;
  }

  //Weighted Job Scheduling problem
  // Maximize ad revenue given a set of of advertisements with a start time, end time and revenue
  class Ad {

    int start, finish, revenue;
  }

  // Used to sort ads according to finish times
  class JobComparator implements Comparator<Ad> {

    public int compare(Ad a, Ad b) {
      return a.finish < b.finish ? -1 : a.finish == b.finish ? 0 : 1;
    }
  }

  public int scheduleAds(Ad ads[]) {
    // Sort ads according to finish time
    Arrays.sort(ads, new JobComparator());
    // Create an array to store solutions of subproblems. table[i] stores the profit for ads till ads[i]
    // (including ads[i])
    int n = ads.length;
    int table[] = new int[n];
    table[0] = ads[0].revenue;
    // Fill entries in table[] using recursive property
    for (int i = 1; i < n; i++) {
      // Find profit including the current ad
      int inclProf = ads[i].revenue;
      int l = binarySearch(ads, i);
      if (l != -1) {
        inclProf += table[l];
      }
      // Store maximum of including and excluding
      table[i] = Math.max(inclProf, table[i - 1]);
    }
    return table[n - 1];
  }

  /* A Binary Search based function to find the latest ad (before current ad) that doesn't conflict with current
     ad. "index" is index of the current ad.  This function returns -1 if all ads before index conflict with it.
     The array ads[] is sorted in increasing order of finish time. */
  static public int binarySearch(Ad ads[], int index) {
    // Initialize 'lo' and 'hi' for Binary Search
    int lo = 0, hi = index - 1;
    // Perform binary Search iteratively
    while (lo <= hi) {
      int mid = (lo + hi) / 2;
      if (ads[mid].finish <= ads[index].start) {
        if (ads[mid + 1].finish <= ads[index].start) {
          lo = mid + 1;
        } else {
          return mid;
        }
      } else {
        hi = mid - 1;
      }
    }
    return -1;
  }

  /*Provide a set of positive integers (an array of integers). Each integer represent number of nights user request on Airbnb.com.
     If you are a host, you need to design and implement an algorithm to find out the maximum number a nights you can accommodate.
     The constraint is that you have to reserve at least one day between each request,
     input: [5, 1, 2, 6, 20, 2] => output: 27*/
  //house robbing problem. Time = O(N) space = O(1)
  public static int rob(int[] num) {
    if (num == null || num.length == 0) {
      return 0;
    }
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
    int[] maxDaysToPos = new int[arr.length + 1];
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
    if (start > end) {
      return -1;
    }
    int mid = start + (end - start) / 2;
    if (a[mid] == mid) {
      return mid;
    }
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

  //Max product of the three numbers for a given array of size N, non duplicate elements
  //Find maximum of the sum/multiplication of 3 numbers in a array
  static int maxproductofThree(int[] a) {
    int largest = a[0];
    int secondlargest = 0;
    int thirdlargest = 0;
    int min1 = 0;
    int min2 = 0;
    for (int i = 0; i < a.length; i++) {
      int number = a[i];
      if (number > largest) {
        thirdlargest = secondlargest;
        secondlargest = largest;
        largest = number;
      } else if (number > secondlargest) {
        thirdlargest = secondlargest;
        secondlargest = number;
      } else if (number > thirdlargest) {
        thirdlargest = number;
      }
      if (number < min1) {
        min2 = min1;
        min1 = number;
      } else if (number < min2) {
        min2 = number;
      }
    }
    return largest * Math.max((thirdlargest * secondlargest), (min1 * min2));
  }

  //Given an unsorted array of positive numbers, write a function that returns true if array consists of consecutive numbers.
  public boolean areConsecutive(int input[]) {
    int min = Integer.MAX_VALUE;
    for (int i = 0; i < input.length; i++) {
      if (input[i] < min) {
        min = input[i];
      }
    }
    for (int i = 0; i < input.length; i++) {
      if (Math.abs(input[i]) - min >= input.length) {
        return false;
      }
      if (input[Math.abs(input[i]) - min] < 0) {
        return false;
      }
      input[Math.abs(input[i]) - min] = -input[Math.abs(input[i]) - min];
    }
    for (int i = 0; i < input.length; i++) {
      input[i] = Math.abs(input[i]);
    }
    return true;
  }

  //find missing number from array 0 to n
  //You are given a list of n-1 integers and these integers are in the range of 1 to n. There are no duplicates in list.
  int getMissingNo(int a[], int n) {
    int i;
    int x1 = a[0]; /* For xor of all the elements in array */
    for (i = 1; i < n; i++) {
      x1 = x1 ^ a[i];
    }
    for (i = 1; i <= n; i++) {
      x1 = x1 ^ i;
    }
    return x1;
  }

  //Find two missing numbers from the array with given length
  //this function also can be used for Find the two repeating elements in a given array
  public static void findNumbers(int[] a, int N) {
    int x = 0;
    for (int i = 0; i < a.length; i++) {
      x = x ^ a[i];
    }
    for (int i = 1; i <= N; i++) {
      x = x ^ i;
    }
    x = x & (~(x - 1));
    int p = 0, q = 0;
    for (int i = 0; i < a.length; i++) {
      if ((a[i] & x) == x) {
        p = p ^ a[i];
      } else {
        q = q ^ a[i];
      }
    }
    for (int i = 1; i <= N; i++) {
      if ((i & x) == x) {
        p = p ^ i;
      } else {
        q = q ^ i;
      }
    }
    System.out.println("N1: " + p + " N2: " + q);
  }

  //Single Number II: Given a non-empty array of integers, every element appears three times except for one,
  // which appears exactly once. Find that single one.


  //Given a stream of numbers, print average (or mean) of the stream at every point.
  // Returns the new average after including x
  float getAvg(float prev_avg, int x, int n) {
    return (prev_avg * n + x) / (n + 1);
  }

  // Prints average of a stream of numbers
  void streamAvg(int arr[], int n) {
    float avg = 0;
    for (int i = 0; i < n; i++) {
      avg = getAvg(avg, arr[i], i);
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
  void randomize(int arr[], int n) {
    // Start from the last element and swap one by one. We don't need to run for the first element that's why i > 0
    for (int i = n - 1; i > 0; i--) {
      // Pick a random index from 0 to i
      int j = (int) Math.random() % (i + 1);
      // Swap arr[i] with the element at random index
      arr[i] ^= arr[j];
      arr[j] ^= arr[i];
      arr[i] ^= arr[j];
    }
  }

  //Given an array of strings, write a method to serialize that array into one single string, and a method to
  //deserialize the single string back into the original array
  static String serialize(String[] arr) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < arr.length; i++) {
      byte[] bLength = Integer.toString(arr[i].length()).getBytes(StandardCharsets.US_ASCII);
      byte[] bData = arr[i].getBytes(StandardCharsets.US_ASCII);
      sb.append(String.format("%8s", Integer.toBinaryString(bLength[0] & 0xFF)).replace(' ', '0'));
      for (byte item : bData) {
        sb.append(String.format("%8s", Integer.toBinaryString(item & 0xFF)).replace(' ', '0'));
      }
    }
    return sb.toString();
  }

  static String[] deSerialize(String arr) {
    List<String> output = new ArrayList<String>();
    byte[] bytes = GetBytes(arr);
    for (int i = 0; i < bytes.length; ) {
      int length = Integer
          .valueOf((new String(bytes, StandardCharsets.US_ASCII)).substring(i, i + 1));
      i++;
      String data = (new String(bytes, StandardCharsets.US_ASCII)).substring(i, i + length);
      i += length;
      output.add(data);
    }
    return output.toArray(new String[0]);
  }

  private static byte[] GetBytes(String bitString) {
    int numBytes = bitString.length() / 8;
    if (bitString.length() % 8 != 0) {
      numBytes++;
    }
    byte[] bytes = new byte[numBytes];
    int byteIndex = 0, bitIndex = 0;
    for (int i = 0; i < bitString.length(); i++) {
      if (bitString.charAt(i) == '1') {
        bytes[byteIndex] |= (byte) (1 << (7 - bitIndex));
      }
      bitIndex++;
      if (bitIndex == 8) {
        bitIndex = 0;
        byteIndex++;
      }
    }
    return bytes;
  }

  //Given an unsorted array of numbers, write a function that returns true if array consists of consecutive numbers.
  //http://www.geeksforgeeks.org/check-if-array-elements-are-consecutive/
  boolean areConsecutive(int arr[], int n) {
    if (n < 1) {
      return false;
    }
    /* 1) Get the minimum element in array */
    int min = getMin(arr, n);
    /* 2) Get the maximum element in array */
    int max = getMax(arr, n);
    /* 3) max - min + 1 is equal to n,  then only check all elements */
    if (max - min + 1 == n) {
      /* Create a temp array to hold visited flag of all elements. Note that, calloc is used here so that all values are initialized
         as false */
      boolean[] visited = new boolean[n];
      int i;
      for (i = 0; i < n; i++) {
        /* If we see an element again, then return false */
        if (visited[arr[i] - min] != false) {
          return false;
        }
        /* If visited first time, then mark the element as visited */
        visited[arr[i] - min] = true;
      }
      /* If all elements occur once, then return true */
      return true;
    }
    return false; // if (max - min  + 1 != n)
  }

  int getMin(int arr[], int n) {
    int min = arr[0];
    for (int i = 1; i < n; i++) {
      if (arr[i] < min) {
        min = arr[i];
      }
    }
    return min;
  }

  int getMax(int arr[], int n) {
    int max = arr[0];
    for (int i = 1; i < n; i++) {
      if (arr[i] > max) {
        max = arr[i];
      }
    }
    return max;
  }

  //Sort a list of numbers in which each number is at a distance k from its actual position
  //Input = 3,4,1,2,7,8,5,6 and K=2 output = 1,2,3,4,5,6
  public static int[] SortNearlySorted(int[] a, int k) {
    if (k == 0) {
      return a;
    }
    int count = 0;
    for (int i = 0; i < a.length; i++) {
      Swap(i, i + k, a);
      count++;
      if (count == k) {
        i += k;
        count = 0;
      }
    }
    return a;
  }

  public static void Swap(int x, int y, int a[]) {
    a[x] ^= a[y];
    a[y] ^= a[x];
    a[x] ^= a[y];
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
  public int minimumCoinBottomUp(int total, int coins[]) {
    int T[] = new int[total + 1];
    int R[] = new int[total + 1];
    T[0] = 0;
    for (int i = 1; i <= total; i++) {
      T[i] = Integer.MAX_VALUE - 1;
      R[i] = -1;
    }
    for (int j = 0; j < coins.length; j++) {
      for (int i = 1; i <= total; i++) {
        if (i >= coins[j]) {
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
    while (start != 0) {
      int j = R[start];
      System.out.print(coins[j] + " ");
      start = start - coins[j];
    }
    System.out.print("\n");
  }

  //Given a total and coins of certain denominations find number of ways total
  //can be formed from coins assuming infinity supply of coins
  //https://github.com/mission-peace/interview/blob/master/src/com/interview/dynamic/CoinChanging.java
  public int numberOfSolutionsOnSpace(int total, int arr[]) {
    int temp[] = new int[total + 1];
    temp[0] = 1;
    for (int i = 0; i < arr.length; i++) {
      for (int j = 1; j <= total; j++) {
        if (j >= arr[i]) {
          temp[j] += temp[j - arr[i]];
        }
      }
    }
    return temp[total];
  }

  //This program plays the game "Fizzbuzz".  It counts to 100, replacing each multiple of 5 with the word "fizz", each
  //multiple of 7 with the word "buzz", and each multiple of both with the word "fizzbuzz".
  public static void fizzbuzz() {
    for (int i = 1; i <= 100; i++) {                    // count from 1 to 100
      if (((i % 5) == 0) && ((i % 7) == 0))            // A multiple of both?
      {
        System.out.print("fizzbuzz");
      } else if ((i % 5) == 0) {
        System.out.print("fizz"); // else a multiple of 5?
      } else if ((i % 7) == 0) {
        System.out.print("buzz"); // else a multiple of 7?
      } else {
        System.out.print(i);                        // else just print it
      }
      System.out.print(" ");
    }
    System.out.println();
  }

  //Maximum difference between two elements such that larger element appears after the smaller number
  //Input =  [2, 3, 10, 6, 4, 8, 1]  output = 8
  int maxDiff(int arr[], int arr_size) {
    int max_diff = arr[1] - arr[0];
    int min_element = arr[0];
    int i;
    for (i = 1; i < arr_size; i++) {
      if (arr[i] - min_element > max_diff) {
        max_diff = arr[i] - min_element;
      }
      if (arr[i] < min_element) {
        min_element = arr[i];
      }
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
      if (words[i] == a) {
        last_a = i;
      } else if (words[i] == b) {
        last_b = i;
      } else if (words[i] == c) {
        last_c = i;
      }
      if (last_a >= 0 && last_b >= 0 && last_c >= 0) {
        min_distance = Math.min(min_distance,
            Math.abs(last_a - last_c) + Math.abs(last_a - last_b) + Math.abs(last_b - last_c));
      }
    }
    return min_distance;
  }

  /*airbnb: Given an array of numbers A = [x1, x2, ..., xn] and T = Round(x1+x2+... +xn). We want to find a way to round each
    element in A such that after rounding we get a new array B = [y1, y2, ...., yn] such that y1+y2+...+yn = T where yi = Floor(xi) or Ceil(xi), ceiling or floor of xi.
    We also want to minimize sum |xi-yi|
    Minimize rounding sum: input = 30.3, 2.4, 3.5 output = 30 2 4
    */
  public static double[] minimizeRoundSum(double[] input, int T) {
    double sum = 0;
    double[] output = new double[input.length];
    for (int i = 0; i < input.length; i++) {
      sum += Math.round(input[i]);
      output[i] = Math.round(input[i]);
    }
    double diff = T - sum;
    if (diff != 0) {
      int size = Math.abs((int) Math.round(diff));
      Queue<Pair<Double, Integer>> maxHeap = new PriorityQueue<Pair<Double, Integer>>(size,
          new Comparator<Pair<Double, Integer>>() {
            @Override
            public int compare(Pair<Double, Integer> o1, Pair<Double, Integer> o2) {
              return (int) (o2.getKey() - o1.getKey());
            }
          });
      for (int i = 0; i < input.length; i++) {
        double _deta = Math.abs(input[i] - output[i]);
        if (i < Math.round(diff)) {
          maxHeap.add(new Pair(_deta, i));
        } else if (_deta > maxHeap.peek().getKey()) {
          maxHeap.poll();
          maxHeap.add(new Pair(_deta, i));
        }
      }
      while (!maxHeap.isEmpty() && diff > 0) {
        int index = maxHeap.poll().getValue();
        output[index] += 1;
        diff -= 1;
      }
    }
    return output;
  }

  //Given an array, print the Next Greater Element (NGE) for every element.
  //input = [4, 5, 2, 25} output = {(4,5),(5,25),(2,25),(25,-1)
  private static void FindNextGreaterElement(int[] array) {
    Stack<Integer> stack = new java.util.Stack<>();
    //1) Push the first element to stack.
    stack.push(array[0]);
    //2) Pick rest of the elements one by one and follow following steps in loop.
    for (int i = 1; i < array.length; i++) {
      while (!stack.isEmpty() && array[i] > stack.peek()) {
        //d) Keep popping from the stack while the popped element is smaller than next.
        //next becomes the next greater element for all such popped elements
        Integer poppedElement = stack.pop();
        System.out.println(poppedElement + ", " + array[i]);
      }
      //g) If next is smaller than the popped element, then push the popped element back
      stack.push(array[i]);
    }
    //3) After the loop in step 2 is over, pop all the elements from stack and
    //print -1 as next element for them.
    while (!stack.isEmpty()) {
      System.out.println(stack.pop() + ", " + -1);
    }
  }

  //without extra space
  static void nextGreaterElement(int[] arr) {
    int max = arr[arr.length - 1];
    System.out.println("for el: " + arr[arr.length - 1] + " next greater is: " + -1);
    for (int i = arr.length - 2; i >= 0; i--) {
      if (arr[i] < arr[i + 1]) {
        System.out.println("for " + arr[i] + " next greater is: " + arr[i + 1]);
      } else if (arr[i] < max) {
        System.out.println("for " + arr[i] + " next greater is: " + max);
      } else {
        System.out.println("for " + arr[i] + " next greater is: " + (-1));
      }
      if (arr[i + 1] > max) {
        max = arr[i + 1];
      }
    }
  }

  //Write a class to take in a large arbitrary number, also provide a function to increment the number. The number will be passed on as an array of integers.
  public static int[] incrLargeNumber(int[] inputArray) {
    if (inputArray == null) {
      return inputArray;
    }
    int arrLength = inputArray.length;
    // check for all 9s
    boolean all9s = true;
    for (int i = 0; i < arrLength; i++) { // 0(k) --> k is the first non 9
      if (inputArray[i] != 9) {
        all9s = false;
        break;
      }
    }
    if (all9s) {
      int[] newInputArray = new int[arrLength + 1];
      newInputArray[0] = 1;    // 0(1)
      for (int i = 1; i < newInputArray.length; i++) { // 0(n)
        newInputArray[i] = 0;
      }
      return newInputArray;
    } else {
      for (int i = arrLength - 1; i > -1; i--) { // O(k+1)  where is the first non 9
        int k = inputArray[i];
        if (k == 9) {
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
  public static int getMaxOfMaxPrefix(int a[]) {
    if (a.length < 2) {
      return 1;
    }
    int count = 0, maxPrefix = 0, temp = a[0];
    for (int i = 1; i < a.length; i++) {
      if (a[i] > temp) {
        count++;
      } else {
        temp = a[i];
        count = 0;
      }
      if (count > maxPrefix) {
        maxPrefix = count;
      }
    }
    return maxPrefix;
  }

  //A list L is too big to fit in memory. L is partially sorted. Partially sorted in a specific way: x-sorted L[i] < L[i+x].
  // Any element is at most x indices out of position. Sort the list L
  private static int[] sortPartialList(int[] a, int x) {
    PriorityQueue<Integer> pq = new PriorityQueue<>();
    int[] result = new int[a.length];
    int k = 0;
    for (int i = 0; i < a.length; i++) {
      if (i < x) {
        pq.add(a[i]);
      } else {
        result[k++] = pq.poll();
        pq.add(a[i]);
      }
    }
    while (!pq.isEmpty()) {
      result[k] = pq.poll();
      k++;
    }
    return result;
  }

  //Given an array of integers, find if it’s possible to remove exactly one integer from the array that divides the array into two subarrays with the same sum.
  //Input:  arr = [6, 2, 3, 2, 1] Output:  true ( [6], and [3, 2, 1])
  public static boolean divideArray(int[] a) {
    int sum = 0;
    int sum_so_far = 0;
    for (int i = 0; i < a.length; i++) {
      sum += a[i];
    }
    for (int i = 0; i < a.length; i++) {
      if (2 * sum_so_far + a[i] == sum) {
        return true;
      }
      sum_so_far += a[i];
    }
    return false;
  }

  //Given an array arr[], find the maximum j – i such that arr[j] > arr[i]
  public static int maxIndexDiff(int arr[], int n) {
    int maxDiff;
    int i, j;
    int RMax[] = new int[n];
    int LMin[] = new int[n];
    /* Construct LMin[] such that LMin[i] stores the minimum value from (arr[0], arr[1], ... arr[i]) */
    LMin[0] = arr[0];
    for (i = 1; i < n; ++i) {
      LMin[i] = Math.min(arr[i], LMin[i - 1]);
    }
    /* Construct RMax[] such that RMax[j] stores the maximum value from (arr[j], arr[j+1], ..arr[n-1]) */
    RMax[n - 1] = arr[n - 1];
    for (j = n - 2; j >= 0; --j) {
      RMax[j] = Math.max(arr[j], RMax[j + 1]);
    }
    /* Traverse both arrays from left to right to find optimum j - i This process is similar to merge() of MergeSort */
    i = 0;
    j = 0;
    maxDiff = -1;
    while (j < n && i < n) {
      if (LMin[i] < RMax[j]) {
        maxDiff = Math.max(maxDiff, j - i);
        j = j + 1;
      } else {
        i = i + 1;
      }
    }
    return maxDiff;
  }

  //Move all zeroes to end of array
  public static int[] pushZerosToEnd(int arr[]) {
    int left = 0, right = arr.length - 1;
    while (left < right) {
      while (arr[left] != 0 && left < right) {
        left++;
      }
      while (arr[right] == 0 && left < right) {
        right--;
      }
      if (left < right) {
        arr[left] ^= arr[right];
        arr[right] ^= arr[left];
        arr[left] ^= arr[right];
      }
    }
    return arr;
  }

  // Given an array nums, write a function to move all 0's to the front of it while maintaining the relative order
  // of the non-zero elements. For example, given nums = [0, 1, 0, 3, 12], after calling your function,
  // nums should be [0,0,1, 3, 12]
  public static void moveZeroWithOrder(int[] input) {
    for (int lastNonZeroFoundAt = input.length - 1, cur = input.length - 1; cur > 0; cur--) {
      if (input[cur] != 0) {
        swap(input, lastNonZeroFoundAt, cur);
        lastNonZeroFoundAt--;
      }
    }
  }

  //Given an array of Ints find a maximum sum of non adjacent elements. for ex. arr = [1,0,3,9,2] then ans would be 10 = 1 + 9 (non adjacent element)
  public static int MaxSumNonAdjacent(int[] a) {
    int[] output = new int[a.length];
    if (a.length == 0) {
      return 0;
    } else if (a.length < 2) {
      return a[0];
    } else if (a.length == 2) {
      return Math.max(a[0], a[1]);
    } else {
      output[0] = a[0];
      output[1] = Math.max(a[0], a[1]);
      for (int i = 2; i < a.length; i++) {
        int temp = Math.max(a[i], a[i] + output[i - 2]);
        output[i] = Math.max(output[i - 1], temp);
      }
    }
    return output[a.length - 1];
  }

  /*Function to return max sum such that no two elements are adjacent without space arr[] = {5,  5, 10, 40, 50, 35}*/
  static int FindMaxSumNonAdjacent(int arr[]) {
    int incl = arr[0];
    int excl = 0;
    int excl_new;
    for (int i = 1; i < arr.length; i++) {
      /* current max excluding i */
      excl_new = Math.max(incl, excl);
      /* current max including i */
      incl = excl + arr[i];
      excl = excl_new;
    }
    /* return max of incl and excl */
    return Math.max(incl, excl);
  }

  //input a list of array [[1, 2, 3], [1], [1, 2]] return the list of array, each array is a combination of one element in each array.
  //[[1, 1, 1], [1, 1, 2], [2, 1, 1], [2, 1, 2], [3, 1, 1], [3, 1, 2]] Time = O(k^n) where k = max array size and n is list size
  // Recursive is non-efficient solution. please see the Iterative one below.
  static void printCombination(List<int[]> input) {
    int n = input.size();
    List<Integer> result = new ArrayList<>();
    combinationUtil(input, result, 0, "");
    for (Integer num : result) {
      System.out.println(num);
    }
  }

  static void combinationUtil(List<int[]> arr, List<Integer> result, int level, String current) {
    if (level == arr.size()) {
      result.add(Integer.parseInt(current));
      return;
    }
    for (int i = 0; i < arr.get(level).length; ++i) {
      combinationUtil(arr, result, level + 1, current + arr.get(level)[i]);
    }
  }

  //Followup: each array in the input list is an iterator, which can only be looped once.
  //Time = O(N*M^2) where N is number of arrays and M is average length of array elements.
  public static <T> List<List<T>> getCombinations(List<List<T>> inputs) {
    List<List<T>> ouput = new ArrayList<List<T>>();
    List<List<T>> tempOuput;
    int index = 0;
    //extract each of the integers in the first list and add each to ints as a new list
    for (T i : inputs.get(0)) {
      List<T> newList = new ArrayList<T>();
      newList.add(i);
      ouput.add(newList);
    }
    index++;
    while (index < inputs.size()) {
      List<T> nextList = inputs.get(index);
      tempOuput = new ArrayList<List<T>>();
      for (List<T> first : ouput) {
        for (T second : nextList) {
          List<T> tempList = new ArrayList<T>();
          tempList.addAll(first);
          tempList.add(second);
          tempOuput.add(tempList);
        }
      }
      ouput = tempOuput;
      index++;
    }
    return ouput;
  }

  // Find the candidates having more votes, if there are two candidates with same votes pick up last one based on sorting.
  static String abc(String[] votes) {
    TreeMap<String, Integer> map = new TreeMap<>();
    int count = 1;
    for (int i = 0; i < votes.length; i++) {
      if (!map.containsKey(votes[i])) {
        map.put(votes[i], 1);
      } else {
        map.put(votes[i], map.get(votes[i]) + 1);
      }
    }
    int max = Integer.MIN_VALUE;
    String key = "";
    for (Map.Entry<String, Integer> entry : map.entrySet()) {
      if (entry.getValue() >= max) {
        key = entry.getKey();
        max = entry.getValue();
      }
    }
    return key;
  }

  //Given two arrays which have same values but in different order, we need to make second array same as first array using minimum number of swaps.
  public static int MinSwap(int[] a, int[] b) {
    HashMap<Integer, Integer> map = new HashMap<>();
    int count = 0;
    for (int i = 0; i < a.length; i++) {
      map.put(a[i], i);
    }
    for (int j = 0; j < b.length; j++) {
      int index = map.get(b[j]);
      if (index != j) {
        Swap(index, j, b);
        count++;
      }
    }
    return count++;
  }

  //Given a number M (N-digit integer) and K-swap operations(a swap operation can swap 2 digits), devise an algorithm
  //to get the maximum possible integer?
  static int[] Kswap(int[] arr, int k) {
    int i, j, min_idx;
    int n = arr.length;
    // One by one move boundary of unsorted subarray
    for (i = 0; i < k; i++) {
      // Find the max element in unsorted array
      min_idx = i;
      for (j = i + 1; j < n; j++) {
        if (arr[j] > arr[min_idx]) {
          min_idx = j;
        }
      }
      // Swap the found max element with the first element
      swap(arr, min_idx, i);
    }
    return arr;
  }

  //Given a sorted array in which all elements appear twice (one after one) and one element appears only once.
  // Find that element in O(log n) complexity.
  public static int findOneOccurance(int[] a, int low, int high) {
    if (low > high) {
      return -1;
    }
    if (high == low) {
      return a[low];
    }
    int mid = low + (high - low) / 2;
    if (mid % 2 == 0) {
      if (a[mid] == a[mid + 1]) {
        findOneOccurance(a, mid + 2, high);
      } else {
        findOneOccurance(a, low, mid);
      }
    } else {
      if (a[mid] == a[mid - 1]) {
        findOneOccurance(a, mid + 1, high);
      } else {
        findOneOccurance(a, low, mid - 1);
      }
    }
    return -1;
  }

  //Given a sorted array, where each element but one occurs twice, return the element that repeats.
  private static int findRepeatingNum(int[] a) {
    int low = 0, high = a.length;
    while (low != high) {
      int mid = (low + high) / 2;
      if ((a[mid] - a[0]) >= mid) {
        low = mid + 1;
      } else {
        high = mid;
      }
    }
    return a[low];
  }

  //find Maximum and minimum of an array using minimum number of comparisons
  class pair {

    int min;
    int max;
  }

  private pair getMinMax(int arr[], int n) {
    pair minmax = new pair();
    int i;
    /* If array has even number of elements then initialize the first two elements as minimum and maximum */
    if (n % 2 == 0) {
      if (arr[0] > arr[1]) {
        minmax.max = arr[0];
        minmax.min = arr[1];
      } else {
        minmax.min = arr[0];
        minmax.max = arr[1];
      }
      i = 2;  /* set the startung index for loop */
    }
    /* If array has odd number of elements then initialize the first element as minimum and maximum */
    else {
      minmax.min = arr[0];
      minmax.max = arr[0];
      i = 1;  /* set the startung index for loop */
    }
    /* In the while loop, pick elements in pair and compare the pair with max and min so far */
    while (i < n - 1) {
      if (arr[i] > arr[i + 1]) {
        if (arr[i] > minmax.max) {
          minmax.max = arr[i];
        }
        if (arr[i + 1] < minmax.min) {
          minmax.min = arr[i + 1];
        }
      } else {
        if (arr[i + 1] > minmax.max) {
          minmax.max = arr[i + 1];
        }
        if (arr[i] < minmax.min) {
          minmax.min = arr[i];
        }
      }
      i += 2; /* Increment the index by 2 as two elements are processed in loop */
    }
    return minmax;
  }

  //We want to make a row of bricks that is goal inches long. We have a number of small bricks (1 inch each) and big bricks (5 inches each).
  // Return true if it is possible to make the goal by choosing from the given bricks. This is a little harder than it
  public boolean makeBricks(int small, int big, int goal) {
    if (goal > big * 5 + small) {
      return false;
    }
    if (goal % 5 > small) {
      return false;
    }
    return true;
    // remainder = Math.abs(goal - ( (5* big) + small));
  }

  /*Given a sequence of Land and water codes, find the longest island you can build by filling the water in between any two lands.
    You can fill only sequence of water, but any number of slots in that sequence.
     Example sequence: L, W, L, W, W, L, W
     The Longest sequence length is 4 because (Filled water slots are bolded)*/
  private static int longestIsland(char[] arr) {
    int max = 0;
    int start = 0;
    int curr = 0;
    for (int i = 0; i < arr.length; i++) {
      if (arr[i] == 'L') {
        max = Math.max(max, i - start + 1);
        if (i == 0 || arr[i - 1] == 'W') {
          curr = i;
        }
      } else {
        start = curr;
      }
    }
    max = Math.max(max, arr.length - start);
    return max;
  }

  //Given a sorted array consisting of only integers where every element appears twice except for one element which
  //appears once. Find this single element that appears only once.
  public int singleNonDuplicate(int[] nums) {
    // binary search
    int n = nums.length, lo = 0, hi = n / 2;
    while (lo < hi) {
      int m = (lo + hi) / 2;
      if (nums[2 * m] != nums[2 * m + 1]) {
        hi = m;
      } else {
        lo = m + 1;
      }
    }
    return nums[2 * lo];

  }

  public int singleNonDuplicate1(int[] nums) {
    int low = 0;
    int high = nums.length - 1;

    while (low < high) {
      int mid = low + (high - low) / 2;
      if (nums[mid] != nums[mid + 1] && nums[mid] != nums[mid - 1]) {
        return nums[mid];
      } else if (nums[mid] == nums[mid + 1] && mid % 2 == 0) {
        low = mid + 1;
      } else if (nums[mid] == nums[mid - 1] && mid % 2 == 1) {
        low = mid + 1;
      } else {
        high = mid - 1;
      }
    }
    return nums[low];
  }

  /*Find minimum cost of tickets required to buy for traveling on known days of the month (1...30). Three types of
    tickets are available : 1-day ticket valid for 1 days and costs 2 units, 7-days ticket valid for 7 days and costs 7 units,
    30-days ticket valid for 30 days and costs 25 units.For eg: I want to travel on [1,4,6,7,28,30] days of the month
    i.e. 1st, 4th, 6th ... day of the month. How to buy tickets so that the cost is minimum.*/
  public static int finMinTicketsCost(int[] a) {
    boolean[] dayTrip = new boolean[31]; // note: initializes to false
    for (final int day : a) {
      dayTrip[day] = true;
    }
    int[] minCostDP = new int[31];
    minCostDP[0] = 0; // technically redundant
    for (int d = 1; d <= 30; d++) {
      if (!dayTrip[d]) {
        minCostDP[d] = minCostDP[d - 1];
        continue;
      }
      int minCost;
      // Possibility #1: one-day pass on day d:
      minCost = minCostDP[d - 1] + 2;
      // Possibility #2: seven-day pass ending on or after day d:
      for (int prevD = Math.max(0, d - 7); prevD <= d - 4; ++prevD) {
        minCost = Math.min(minCost, minCostDP[prevD] + 7);
      }
      // Possibility #3: 30-day pass for the whole period:
      minCost = Math.min(minCost, 25);

      minCostDP[d] = minCost;
    }
    return minCostDP[30];
  }

  //Find position of an element in a sorted array of infinite numbers
  int findPos(int arr[], int key) {
    int l = 0, h = 1;
    int val = arr[0];
    // Find h to do binary search
    while (val < key) {
      l = h;        // store previous high
      h = 2
          * h;      // double high index,  Doubling/Halving will ensure that your time complexity will never exceed by O(log n)
      val = arr[h]; // update new val
    }
    // at this point we have updated low and high indices, thus use binary search between them
    return Search.binarySearch(arr, l, h, key);
  }

  private static int totalScore(String[] a) {
    Stack<Integer> _stack = new Stack<>();
    int totalScore = 0;
    for (int i = 0; i < a.length; i++) {
      if (a[i] == "X") {
        totalScore += 2 * (_stack.peek());
        _stack.push(2 * _stack.peek());
      } else if (a[i] == "Z") {
        totalScore -= _stack.peek();
        _stack.pop();
      } else if (a[i] == "+") {
        int temp = _stack.pop();
        int t2 = _stack.peek() + temp;
        totalScore += t2;
        _stack.push(temp);
        _stack.push(t2);
      } else {
        totalScore += Integer.valueOf(a[i]);
        _stack.push(Integer.valueOf(a[i]));
      }
    }
    return totalScore;
  }

  //longest continuous zig-zag subarray in an integer array
  // in = {10, 22, 9, 33, 49, 50, 31, 60} out = 6 (10, 22, 9, 50, 31, 60)
  private static int longestZigZagSubArray(int[] a) {
    int b[] = new int[a.length - 1];
    for (int i = 0; i < a.length - 1; i++) {
      if (a[i] > a[i + 1]) {
        b[i] = 0;
      } else {
        b[i] = 1;
      }
    }
    int count = 1;
    for (int i = 0; i < b.length - 1; i++) {
      if (b[i] != b[i + 1]) {
        count++;
      }
    }
    return count + 1;
  }

  //sum of k largest elements in array
  static int SumkLargest(int[] a, int k) {
    int sum = 0;
    quick(a, 0, a.length - 1, k);
    for (int i = 0; i < k; i++) {
      sum += a[i];
    }
    System.out.print(sum);
    return sum;
  }

  static void quick(int[] a, int start, int end, int k) {
    if (start <= end) {
      int pivot = part(a, start, end);
      if (pivot < k) {
        quick(a, pivot + 1, end, k);
      } else {
        quick(a, start, pivot - 1, k);
      }
    }
  }

  static int part(int[] a, int start, int end) {
    int pivot = a[end];
    int index = start;
    int i;
    for (i = start; i < end; i++) {
      if (a[i] > pivot) {
        swap(a, index, i);
        index++;
      }
    }
    swap(a, index, end);
    return index;
  }

  //Given a task sequence tasks such as ABBABBC, and an integer k, which is the cool down time between two same tasks.
  //Assume the execution for each individual task is 1 unit.
  //Follow up: Given a task sequence and the cool down time, rearrange the task sequence such that the execution time is minimal.
  //http://tzutalin.blogspot.com/2017/02/interview-type-questions-task-sequence.html
  public static int computeTotalTaskTime(char[] tasks, int k) {
    HashMap<Character, Integer> map = new HashMap<>();
    int total = 0;
    for (char task : tasks) {
      if (map.containsKey(task)) {
        int exceptedTime = map.get(task) + k + 1;
        if (exceptedTime > total) {
          total = exceptedTime;
        } else {
          total++;
        }
      } else {
        total++;
      }
      map.put(task, total);
    }
    return total;
  }

  public static int leastInterval(char[] tasks, int n) {
    int[] map = new int[26];
    for (char c : tasks) {
      map[c - 'A']++;
    }
    Arrays.sort(map);
    int max_val = map[25] - 1, idle_slots = max_val * n;
    for (int i = 24; i >= 0 && map[i] > 0; i--) {
      idle_slots -= Math.min(map[i], max_val);
    }
    return idle_slots > 0 ? idle_slots + tasks.length : tasks.length;
  }

  //Given a task sequence and the cool down time, rearrange the task sequence such that the execution time is minimal.
  static class Task {

    char id;
    int frequency;

    Task(char i, int f) {
      id = i;
      frequency = f;
    }
  }

  public static char[] findBestTaskArrangement(char[] tasks, int k) {
    int n = tasks.length;
    PriorityQueue<Task> queue = new PriorityQueue<>(new Comparator<Task>() {
      @Override
      public int compare(Task a, Task b) {
        return b.frequency - a.frequency;
      }
    });
    Map<Character, Integer> map = new HashMap<>();
    for (char task : tasks) {
      map.put(task, map.getOrDefault(task, 0) + 1);
    }

    for (Map.Entry<Character, Integer> entry : map.entrySet()) {
      queue.offer(new Task(entry.getKey(), entry.getValue()));
    }
    tasks = new char[n];
    int i = 0;
    while (!queue.isEmpty()) {
      int c = 0;
      List<Task> nextRoundTask = new ArrayList<>();
      while (c++ < k && !queue.isEmpty()) {
        Task task = queue.poll();
        task.frequency--;
        // Locate the next empty slot
        tasks[i++] = task.id;
        if (task.frequency > 0) {
          nextRoundTask.add(task);
        }
      }
      for (Task task : nextRoundTask) {
        queue.offer(task);
      }
    }
    return tasks;
  }

  // If a=1, b=2...z=26, the users give an input string, suppose 1123. Now the program should tell all the different combinations of the string.
  //Eg: 1123 =aabc, kbc , kw , alc, aaw etc.
  //decodeWay
  public static int decodeWay(String s) {
    final int len = s.length();
    if (len == 0) {
      throw new IllegalArgumentException("s can't be empty");
    }

    int pre = 1, cur = s.charAt(0) == '0' ? 0 : 1, tmp;
    for (int i = 1; i < len && cur != 0; i++) {
      tmp = cur;
      if (s.charAt(i - 1) == '1' || (s.charAt(i - 1) == '2' && s.charAt(i) <= '6')) {
        if (s.charAt(i) == '0') {
          cur = pre;
        } else {
          cur += pre;
        }
      } else if (s.charAt(i) == '0') {
        cur = 0;
      }
      pre = tmp;
    }
    return cur;
  }


  static void print() {

  }

  //Find out if the given string forms a valid lottery number. A valid lottery number contains 7 unique digits between 1 and 59.
  //4938532894754 (yes) -> 49 38 53 28 9 47 54   1634616512 (yes) -> 1 6 34 6 16 51 2    1122334 (no)
  public static boolean isValidLotteryNUmber(String input) {
    HashSet<Integer> set = new HashSet<>();
    if (input.isEmpty() || input.length() < 7 || input.length() > 14) {
      return false;
    }
    int prev = 1, curr = 1, prev_prev = 1;
    for (int i = 1; i < input.length(); i++) {
      if (!set.contains((int) input.charAt(i))) {
        if ((int) input.charAt(i - 1) < 5 + 48 || ((int) input.charAt(i - 1) == 5 + 48
            && (int) input.charAt(i) <= 9 + 48)) {
          curr = prev + prev_prev;
        } else {
          curr = prev;
        }
        prev_prev = prev;
        prev = curr;
      }
      set.add((int) input.charAt(i));
    }
    return curr == 7;
  }

  //Sort array after converting elements to their squares
  public static void sortSquares(int arr[]) {
    int n = arr.length;
    // first dived array into part negative and positive
    int k;
    for (k = 0; k < n; k++) {
      if (arr[k] >= 0) {
        break;
      }
    }
    // Merge two half are sorted and we traverse first half in reverse meaner becaus first half contain negative element
    int i = k - 1; // Initial index of first half
    int j = k; // Initial index of second half
    int ind = 0; // Initial index of temp array

    int[] temp = new int[n];
    while (i >= 0 && j < n) {
      if (arr[i] * arr[i] < arr[j] * arr[j]) {
        temp[ind++] = arr[i] * arr[i];
        i--;
      } else {
        temp[ind++] = arr[j] * arr[j];
        j++;
      }
    }
    while (i >= 0) {
      temp[ind++] = arr[i] * arr[i];
      i--;
    }
    while (j < n) {
      temp[ind++] = arr[j] * arr[j];
      j++;
    }
    // copy 'temp' array into original array
    for (int x = 0; x < n; x++) {
      arr[x] = temp[x];
    }
  }

  /* There are N gas stations along a circular route, where the amount of gas at station i is gas[i]. You have a car
    with an unlimited gas tank and it costs cost[i] of gas to travel from station i to its next station (i+1).
    You begin the journey with an empty tank at one of the gas stations.Return the starting gas station's index if you can travel
    around the circuit once, otherwise return -1. */
  public static int canCompleteCircuit(int[] gas, int[] cost) {
    int sumGas = 0, sumCost = 0, start = 0, tank = 0;
    for (int i = 0; i < gas.length; i++) {
      sumGas += gas[i];
      sumCost += cost[i];
      tank += gas[i] - cost[i];
      if (tank < 0) {
        start = i + 1;
        tank = 0;
      }
    }
    if (sumGas < sumCost) {
      return -1;
    } else {
      return start;
    }
  }

  //Smallest missing positive natural number in a linked list in linear time without a hash table
  public static int firstMissingPositive(int[] nums) {
    int n = nums.length;
    for (int i = 0; i < n; i++) {
      while (nums[i] > 0 && nums[i] < n && nums[i] != nums[nums[i]]) {
        swap(nums, i, nums[i]);
      }
    }
    for (int i = 0; i < n; i++) {
      if (nums[i] != i) {
        return i;
      }
    }
    return n + 1;
  }

  // Combination SUM
  // Find all possible combinations of k numbers that add up to a number n, given that only numbers from 1 to 9 can be
  // used and each combination should be a unique set of numbers.
  //Input: k = 3, n = 7    Output: [[1,2,4]]
  public static List<List<Integer>> combinationSum3(int k, int n) {
    List<List<Integer>> ans = new ArrayList<>();
    combination(ans, new ArrayList<>(), k, 1, n);
    return ans;
  }

  private static void combination(List<List<Integer>> ans, List<Integer> comb, int k, int start,
      int n) {
    if (comb.size() == k && n == 0) {
      List<Integer> li = new ArrayList<Integer>(comb);
      ans.add(li);
      return;
    }
    for (int i = start; i <= 9; i++) {
      comb.add(i);
      combination(ans, comb, k, i + 1, n - i);
      comb.remove(comb.size() - 1);
    }
  }

  // combination multiply
  public static List<List<Integer>> combinationMultiply(int n) {
    List<List<Integer>> ans = new ArrayList<>();
    combinationMultiplyUtil(ans, new ArrayList<>(), n, 1, n);
    return ans;
  }

  private static void combinationMultiplyUtil(List<List<Integer>> ans, List<Integer> comb,
      int target, int start, int n) {
    if (target % n == 0 && start != 1) {
      comb.add(n);
      List<Integer> li = new ArrayList<>(comb);
      ans.add(li);
      return;
    }
    for (int i = start; i <= target; i++) {
      if (target % i == 0) {
        comb.add(i);
        combinationMultiplyUtil(ans, comb, target, i + 1, n / i);
        comb.clear();
      }
    }
  }

  // first bad commit version
  public int firstBadVersion(int n) {
    int left = 1;
    int right = n;
    while (left < right) {
      int mid = left + (right - left) / 2;
      if (isBadVersion(mid)) {
        right = mid;
      } else {
        left = mid + 1;
      }
    }
    return left;
  }

  boolean isBadVersion(int n) {
    return true;
  }

    /* Given two lists A and B, and B is an anagram of A. B is an anagram of A means B is made by
    randomizing the order of the elements in A. We want to find an index mapping P, from A to B.
    A mapping P[i] = j means the ith element in A appears in B at index j.
    These lists A and B may contain duplicates. If there are multiple answers, output any of them.
    A = [12, 28, 46, 32, 50]
    B = [50, 12, 32, 46, 28]
    output = [1, 4, 3, 2, 0]   */

  /*Given a sorted array of integers nums and integer values a, b and c. Apply a function of the
  form f(x) = ax2 + bx + c to each element x in the array. The returned array must be in sorted order.
  Expected time complexity: O(n)*/
  public static int[] sortTransformedArray(int[] nums, int a, int b, int c) {
    int n = nums.length;
    int[] sorted = new int[n];
    int i = 0, j = n - 1;
    int index = a >= 0 ? n - 1 : 0;
    while (i <= j) {
      if (a >= 0) {
        sorted[index--] =
            quad(nums[i], a, b, c) >= quad(nums[j], a, b, c) ? quad(nums[i++], a, b, c)
                : quad(nums[j--], a, b, c);
      } else {
        sorted[index++] =
            quad(nums[i], a, b, c) >= quad(nums[j], a, b, c) ? quad(nums[j--], a, b, c)
                : quad(nums[i++], a, b, c);
      }
    }
    return sorted;
  }

  private static int quad(int x, int a, int b, int c) {
    return a * x * x + b * x + c;
  }

  /* Given an array with n integers, your task is to check if it could become non-decreasing by modifying at most 1 element.
    We define an array is non-decreasing if array[i] <= array[i + 1] holds for every i (1 <= i < n).
    Monotonous Array, input = [4,2,3] output = true; [4,2,1] => false
     [3,4,2,3] output = false*/
  public boolean checkPossibility(int[] nums) {
    int index = -1;
    for (int i = 1; i < nums.length; i++) {
      if (nums[i - 1] > nums[i]) {
        if (index != -1) {
          return false;
        } else {
          index = i;
        }
      }
    }
    return (index == -1 ||   // no reversed pair
        index == 1 || index == nums.length - 1 ||  // reversed pair is first or last element
        nums[index - 2] <= nums[index] || nums[index - 1] <= nums[index
        + 1]); // normal case range [p-2 --> p+1] all valid
  }

  public static boolean checkPossibility1(int[] nums) {
    int cnt = 0, n = nums.length;
    for (int i = 0; i < n - 1; i++) {
      if (nums[i] > nums[i + 1]) {
        cnt++;
        if (i >= 1 && nums[i + 1] < nums[i - 1]) {
          nums[i + 1] = nums[i];
        }
      }
      if (cnt > 1) {
        return false;
      }
    }
    return true;
  }

  /*Give a Pair (M, N) to represent the coordinates, you start from (1, 1), each time (x, y) => (x + y, y) or (x, x + y)
  moves to the lower right, if Can reach (M, N) is True, otherwise False.
  Idea: Starting from (M, N), M and N must be one big and one small, otherwise it is impossible to meet the above conditions.
  So the larger of the two is X + Y, the smaller is X or Y. From the bottom right to the top left,
  there is only one path for each step, so the final can reach (1, 1) is True
  Time = O(m + n)  */
  public static boolean canReachMN(int m, int n) {
    int[] prev = new int[]{m, n};
    while (prev[0] >= 1 && prev[1] >= 1) {
      getPreviousPos(prev);
      if (prev[0] == 1 && prev[1] == 1) {
        return true;
      }
    }
    return false;
  }

  private static void getPreviousPos(int[] cur) {
    if (cur[0] < cur[1]) {
      cur[1] -= cur[0];
    } else {
      cur[0] -= cur[1];
    }
  }

  //write an algorithm to determine the number of set bits in integer.
  public static int countSetBits(int x) {
    int count = 0;
    while (x > 0) {
      x = x & (x - 1);
      count++;
    }
    return count;
  }

  private static int countSetBit(int number) {
    int counter = 0;
    while (number > 0) {
      if (number % 2 == 1) {
        counter++;
      }
      number = number / 2; //or number = number >> 1
    }
    return counter;
  }

  //Find smallest range from k sorted lists
  //You have k lists of sorted integers in ascending order. Find the smallest range that includes
  // at least one number from each of the k lists.
  //Input:[[4,10,15,24,26], [0,9,12,20], [5,18,22,30]]
  //Output: [20,24]
  static class KSortedList {

    int position;
    int value;
    int kIndex;
  }

  static class KSortedListComparator implements Comparator<KSortedList> {

    @Override
    public int compare(KSortedList e1, KSortedList e2) {
      if (e1.value >= e2.value) {
        return 1;
      } else if (e1.value <= e2.value) {
        return -1;
      }
      return 0;
    }
  }

  // Time = O(K) + O(n * logK)
  static List<Integer> smallestRange(List<List<Integer>> input) {
    PriorityQueue<KSortedList> minHeap = new PriorityQueue<>(new KSortedListComparator());
    int max = Integer.MIN_VALUE;
    int range = Integer.MAX_VALUE;
    int start = -1, end = -1;
    //add first element of every chunk into queue
    for (int i = 0; i < input.size(); i++) {
      KSortedList list = new KSortedList();
      list.position = 0;
      list.value = input.get(i).get(0);
      list.kIndex = i;
      minHeap.add(list);
      max = Math.max(max, input.get(i).get(0));
    }
    while (minHeap.size() == input.size()) {
      KSortedList item = minHeap.poll();
      if (max - item.value < range) {
        range = max - item.value;
        start = item.value;
        end = max;
      }
      if (item.position < input.get(item.kIndex).size()) {
        item.value = input.get(item.kIndex).get(item.position);
        item.position += 1;
        minHeap.add(item);
        if (item.value > max) {
          max = item.value;
        }
      }
    }
    return new ArrayList<>(Arrays.asList(start, end));
  }

  //Trapping Rain Water: Given n non-negative integers representing an elevation map where the width
  // of each bar is 1, compute how much water it is able to trap after raining.
  // Input: [0,1,0,2,1,0,1,3,2,1,2,1], return 6
  static int trapWater(int[] input) {
    int left = 0, right = input.length - 1;
    int ans = 0;
    int left_max = 0, right_max = 0;
    while (left < right) {
      if (input[left] < input[right]) {
        if (input[left] >= left_max) {
          left_max = input[left];
        } else {
          ans += (left_max - input[left]);
        }
        ++left;
      } else {
        if (input[right] >= right_max) {
          right_max = input[right];
        } else {
          ans += (right_max - input[right]);
        }
        --right;
      }
    }
    return ans;
  }

}
