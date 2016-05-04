package code.ds;
import java.util.*;
/**
 * Created by ppatel2 on 9/8/2014.
 */
public class Numbers {
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
        double distance;
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
    public List<Point> findKClosest(Point[] p, int k) {
        PriorityQueue<Point> pq = new PriorityQueue<Point>(10, new Comparator<Point>() {
            @Override
            public int compare(Point a, Point b) {
                return (b.x * b.x + b.y * b.y) - (a.x * a.x + a.y * a.y);
            }
        });

        for (int i = 0; i < p.length; i++) {
            if (i < k)
                pq.offer(p[i]);
            else {
                Point tmp = pq.peek();
                if ((p[i].x * p[i].x + p[i].y * p[i].y) - (tmp.x * tmp.x + tmp.y * tmp.y) < 0) {
                    pq.poll();
                    pq.offer(p[i]);
                }
            }
        }

        List<Point> x = new ArrayList<Point>();
        while (!pq.isEmpty())
            x.add(pq.poll());

        return x;
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

