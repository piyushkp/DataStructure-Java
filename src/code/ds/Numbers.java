package code.ds;
import java.util.*;
/**
 * Created by Piyush Patel.
 */
public class Numbers {
    public static void main(String [] args) {
        //int[] out = fib(50);
        //System.out.println(out.length);
        //System.out.println(fib1(3));
        double[] a = {88, 30, 11, 17, 22, 16, 39, 8, 31, 55,
                29, 63, 77, 69, 99, 90, 81, 2, 20, 53, 62, 5, 88, 33, 44, 6};
        System.out.println(MedianOfMediansSelect(a,0,a.length,7));
    }

    //Write a function that takes a number n and returns an array containing a Fibonacci sequence of length n
    // Time  = O(n), Space  = O(n)
    public static int[] fib(int n){
        /* Declare an array to store Fibonacci numbers. */
        int f[];
        if(n == 0)
            return null;
        else if (n == 1){
            f = new int[1];
            f[0] = 0;
        }
        else {
            f = new int[n];
        /* 0th and 1st number of the series are 0 and 1*/
            f[0] = 0;
            f[1] = 1;
            for (int i = 2; i < n; i++) {
       /* Add the previous 2 numbers in the series and store it */
                f[i] = f[i - 1] + f[i - 2];
            }
        }
        return f;
    }
    // Print nth Fibonacci number. Time  = O(n), space = (1)
    private static int fib1(int n){
        int a = 0, b = 1, c, i;
        if( n == 0)
            return a;
        for (i = 2; i <= n; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        return b;
    }
    //Write a function that takes a number n and returns an array containing a Factorial of length n
    // Time  = O(n), Space  = O(n)
    public static int[] factorial(int n){
        int result[];
        if(n ==0) {
            result = new int[1];
            result[0] = 1;
        }
        else {
            result = new int[n];
            result[0] = 1;
            for (int i = 1; i < n; i++) {
                result[i] = i * result[i-1];
            }
        }
        return result;
    }
    //Print nth Factorial number. Time  = O(n), space = (1)
    public static int factorial1(int n){
        int b = 1, c = 1;
        if(n ==0 || n == 1) {
            return  b;
        }
        else {
            for (int i = 2; i <= n; i++) {
                b = i * c;
                c = b;
            }
        }
        return b;
    }

    //Function to calculate x raised to the power y in O(logn)
    int power(int x, int y) {
        int temp;
        if (y == 0) return 1;
        temp = power(x, y / 2);
        if (y % 2 == 0) return temp * temp;
        else return x * temp * temp;
    }
    /* Extended version of power function that can work for float x and negative y*/
    float power(float x, int y) {
        float temp;
        if (y == 0) return 1;
        temp = power(x, y / 2);
        if (y % 2 == 0) return temp * temp;
        else {
            if (y > 0) return x * temp * temp;
            else return (temp * temp) / x;
        }
    }
    //Implement decimal to roman and vice versa
    public int romanToInt(String s) {
        Map<Character, Integer> romans = new HashMap<Character, Integer>();
        romans.put('I', 1);
        romans.put('V', 5);
        romans.put('X', 10);
        romans.put('L', 50);
        romans.put('C', 100);
        romans.put('D', 500);
        romans.put('M', 1000);
        int intNum=0;
        int prev = 0;
        for(int i = s.length()-1; i>=0 ; i--){
            int temp = romans.get(s.charAt(i));
            if(temp < prev)
                intNum-=temp;
            else
                intNum+=temp;
            prev = temp;
        }
        return intNum;
    }
    public static String IntegerToRomanNumeral(int input) {
        if (input < 1 || input > 3999) return "Invalid Roman Number Value";
        String s = "";
        while (input >= 1000) {
            s += "M";
            input -= 1000;
        }
        while (input >= 900) {
            s += "CM";
            input -= 900;
        }
        while (input >= 500) {
            s += "D";
            input -= 500;
        }
        while (input >= 400) {
            s += "CD";
            input -= 400;
        }
        while (input >= 100) {
            s += "C";
            input -= 100;
        }
        while (input >= 90) {
            s += "XC";
            input -= 90;
        }
        while (input >= 50) {
            s += "L";
            input -= 50;
        }
        while (input >= 40) {
            s += "XL";
            input -= 40;
        }
        while (input >= 10) {
            s += "X";
            input -= 10;
        }
        while (input >= 9) {
            s += "IX";
            input -= 9;
        }
        while (input >= 5) {
            s += "V";
            input -= 5;
        }
        while (input >= 4) {
            s += "IV";
            input -= 4;
        }
        while (input >= 1) {
            s += "I";
            input -= 1;
        }
        return s;
    }
    // Find K Nearest points on a plane O(nlogk)
    //E.g. Stored: (0, 1) (0, 2) (0, 3) (0, 4) (0, 5) findNearest(new Point(0, 0), 3) -> (0, 1), (0, 2), (0, 3)
    class Point implements Comparable<Point> {
        int x, y;
        Double distance;
        public Point (int x, int y, Point original) {
            this.x= x;
            this.y = y;

            // sqrt(x^2 + y^2)
            distance = Math.hypot(x - original.x, y - original.y);
        }
        @Override
        public int compareTo(Point that) {
            return this.distance.compareTo(that.distance);
        }
    }
    public List<Point> findKNearestPoints(List<Point> points, Point original, int k) {
        List<Point> result = new ArrayList<>();
        if (points == null || points.size() == 0 || original == null || k <= 0) {
            return result;
        }
        PriorityQueue<Point> pq = new PriorityQueue<>(k);
        for (Point point : points) {
            if (pq.size() < k) {
                pq.offer(point);
            } else {
                if (pq.peek().compareTo(point) > 0) {
                    pq.poll();
                    pq.offer(point);
                }
            }
        }
        result.addAll(pq);
        return result;
    }
    // K nearest points using selection algorithm. Time = O(n) but worst can be O(n^2)
    // better to use Median of medians selection algorithm
    public static List<Point>  findKNearestPointsSelection(final Point points[], final int k) {
        final int n = points.length;
        final double[] dist = new double[n];
        for (int i = 0; i < n; i++) {
            dist[i] = Math.sqrt(points[i].x * points[i].x + points[i].y * points[i].y);
        }
        final double kthMin = kthSmallest(dist, 0, n - 1, k-1);
        List<Point> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            final double d = Math.sqrt(points[i].x * points[i].x + points[i].y * points[i].y);
            if (d <= kthMin) {
                result.add(points[i]);
            }
        }
        return result;
    }
    //Median Of Medians worst case time O(n)
    public static double MedianOfMediansSelect(double[] A, int low, int high, int k){
        if(high - low + 1 <= 5)
        {
            Arrays.sort(A,low,high);
            return A[low + k - 1];
        }
        int noOfGroups = (high - low + 1) / 5;
        double[] medianArray = new double[noOfGroups];
        for(int i = 0; i < noOfGroups; i++)
        {
            medianArray[i] = MedianOfMediansSelect(A, low + i * 5, low + (i * 5) + 4, 3);
        }
        double medianOfMedians = MedianOfMediansSelect(medianArray, 0, medianArray.length - 1, noOfGroups / 2 + 1);
        //swap(A, medianOfMedians, high);
        int medianOfMediansPosition = partition1(A, low, high,medianOfMedians);
        if(medianOfMediansPosition - low + 1 == k)
            return A[low + k - 1];
        else if(k < medianOfMediansPosition -low + 1)
            return MedianOfMediansSelect(A, low, medianOfMediansPosition - 1, k);
        else
            return MedianOfMediansSelect(A, medianOfMediansPosition + 1, high, k - (medianOfMediansPosition - low + 1));
    }
    // kth smallest element in unsorted array
    public static double kthSmallest(double[] G, int first, int last, int k) {
        if (first <= last) {
            //int pivot = partition(G, first, last);
            int pivot = randomPartition(G, first, last);
            if (pivot == k) {
                return G[k];
            }
            if (pivot > k) {
                return kthSmallest(G, first, pivot - 1, k);
            } else return kthSmallest(G, pivot + 1, last, k);
        }
        return 0;
    }
    // Picks a random pivot element between l and r and partitions
    public static int randomPartition(double arr[], int l, int r){
        int pivot = (int) Math.round(l + Math.random() * (r - l));
        swap(arr, pivot, r);
        return partition(arr, l, r);
    }
    public static int partition(double[] G, int first, int last) {
        double pivot = G[last];
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
    public static int partition1(double[] G, int first, int last, double pivot) {
        int i;
        for ( i = first; i < last; i++) {
            if(G[i] == pivot)
                break;
        }
        swap(G,i,last-1);
        i = first;
        int pIndex = first;
        for (i = first; i < last; i++) {
            if (G[i] < pivot) {
                swap(G, i, pIndex);
                pIndex++;
            }
        }
        swap(G,pIndex,last-1);
        return pIndex;
    }
    private static void swap(double[] G, int x, int y) {
        double temp = G[y];
        G[y] = G[x];
        G[x] = temp;
    }
    //Calculate the angle between hour hand and minute hand
    int calcAngle(int h, int m) {
        if (h < 0 || m < 0 || h > 12 || m > 60) System.out.print("Wrong input");
        if (h == 12) h = 0;
        if (m == 60) m = 0;
        int hour_angle = (h * 60 + m) / 2;
        int minute_angle = 6 * m;
        // Find the difference between two angles
        int angle = Math.abs(hour_angle - minute_angle);
        // Return the smaller angle of two possible angles
        angle = Math.min(360 - angle, angle);
        return angle;
    }
    //Returns true if the input string is a number and false otherwise
    public static boolean isNumber(String toTest) {
        boolean flag = false;
        // implementation here
        String pattern = "-?\\d+(\\.\\d+)?";
        flag = toTest.matches(pattern);
        return flag;
    }
    /*Given a nested list of integers, returns the sum of all integers in the list weighted by their depth
    * For example, given the list {{1,1},2,{1,1}} the function should return 10 (four 1's at depth 2, one 2 at depth 1)
    * Given the list {1,{4,{6}}} the function should return 27 (one 1 at depth 1, one 4 at depth 2, one 6 at depth2)*/
    class NestedInteger{
        boolean isInteger(){return true;}
        int getInteger(){return 1;}
        List<NestedInteger> getList(){return new ArrayList<NestedInteger>();}
    }

    private int getListSum(List<NestedInteger> lni, int depth) {
        int sum = 0;
        for (NestedInteger ni : lni) {
            if (ni.isInteger()) sum += ni.getInteger() * depth;
            else sum += getListSum(ni.getList(), depth + 1);
        }
        return sum;
    }
    public int getSum(NestedInteger ni) {
        if (ni.isInteger()) return ni.getInteger();
        else return getListSum(ni.getList(), 1);
    }
    //Squareroot of a Number - O(logN)
    //2^0.5logN
    private int sqrt(int num) {
        if (num < 0) return 0;
        if (num == 1) return 1;
        int low = 0;
        int high = 1 + (num / 2);
        int mid;
        int square;
        while (low + 1 < high) {
            mid = low + (high - low) / 2;
            square = mid * mid;
            if (square == num) return mid;
            else if (square < num) low = mid;
            else high = mid;
        }
        return low;
    }
    //In "the 100 game," two players take turns adding, to a running total, any integer from 1..10.
    // The player who first causes the running total to reach or exceed 100 wins.
    boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        int[] numbers = new int[maxChoosableInteger];
        int sum = 0;
        for (int i = 0; i < maxChoosableInteger; ++i) {
            numbers[i] = i + 1;
        }
        return canWin(numbers, desiredTotal, sum);
    }
    boolean canWin(int numbers[], int desiredTotal, int sum) {
        for (int i = 0; i < numbers.length; i++) {
            int temp = sum + numbers[i];
            if ((desiredTotal - temp) % 11 == 0) {
                sum = temp;
            }
        }
        if (sum >= 100) return true;
        return false;
    }
    // Given a stream of unsorted integers, find the median element in sorted order at any given time.
    int numOfElements = 0;
    PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();
    PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }
    });
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
        if (numOfElements % 2 != 0) return new Double(maxHeap.peek());
        else return (maxHeap.peek() + minHeap.peek()) / 2.0;
    }
    // Returns the maximum value that can be put in a knapsack of capacity W
    int knapSack(int W, int wt[], int val[], int n) {
        int i, j;
        int K[][] = new int[n + 1][W + 1];
        // Build table K[][] in bottom up manner
        for (i = 0; i <= n; i++) {
            for (j = 0; j <= W; j++) {
                if (i == 0 || j == 0) K[i][j] = 0;
                else if (wt[i - 1] <= j) K[i][j] = Math.max(val[i - 1] + K[i - 1][j - wt[i - 1]], K[i - 1][j]);
                else K[i][j] = K[i - 1][j];
            }
        }
        return K[n][W];
    }

    //Divide without Division
    public void divide(int N, int D) {
        int result = 0;
        if (D == 0) {
            System.out.println("Cannot divide by 0");
        } else if (N == 0) {
            System.out.println(0);
        } else if (N == D) {
            System.out.println(1);
        } else if (N > 0 && D > 0 && N < D) {
            System.out.println(0);
        } else {
            // both negative
            if (N < 0 && D < 0) {
                while (N <= D) {
                    N += -1 * D;
                    result++;
                }
                System.out.println(result);
            }
            // either N or D negative
            else if (N < 0 || D < 0) {
                if (N < 0) {
                    N = -1 * N;
                } else {
                    D = -1 * D;
                }
                while (N >= D) {
                    N -= D;
                    result--;
                }
                System.out.println(result);
            }
            // both positive
            else {
                while (N >= D) {
                    N -= D;
                    result++;
                }
                System.out.println(result);
            }
        }
    }

    //Given a decimal number, write a function that returns its negabinary (i.e. negative 2-base) representation as a string.
    // 2 = 1 1 0 , 15  = 110001
    private static String negaBinary(int x) {
        StringBuilder sb = new StringBuilder();
        while (x != 0) {
            int rem = x % -2;
            x = x / -2;
            if (rem < 0) {
                rem += 2;
                x += 1;
            }
            sb.append(rem);
        }
        return sb.reverse().toString();
    }
}

