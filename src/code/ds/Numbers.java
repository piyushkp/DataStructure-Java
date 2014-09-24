package code.ds;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * Created by ppatel2 on 9/8/2014.
 */
public class Numbers {
    //Function to calculate x raised to the power y in O(logn)
    int power(int x, int y) {
        int temp;
        if (y == 0)
            return 1;
        temp = power(x, y / 2);
        if (y % 2 == 0)
            return temp * temp;
        else
            return x * temp * temp;
    }
    /* Extended version of power function that can work for float x and negative y*/
    float power(float x, int y) {
        float temp;
        if (y == 0)
            return 1;
        temp = power(x, y / 2);
        if (y % 2 == 0)
            return temp * temp;
        else {
            if (y > 0)
                return x * temp * temp;
            else
                return (temp * temp) / x;
        }
    }
    //Implement decimal to roman and vice versa
    public static String IntegerToRomanNumeral(int input) {
        if (input < 1 || input > 3999)
            return "Invalid Roman Number Value";
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
    //Find the Nearest points in A Plane O(nlogm)
    //E.g. Stored: (0, 1) (0, 2) (0, 3) (0, 4) (0, 5) findNearest(new Point(0, 0), 3) -> (0, 1), (0, 2), (0, 3)
    class Point {
        double x, y;
        public double getX() {
            return x;
        }
        public void setX(double x) {
            this.x = x;
        }
        public double getY() {
            return y;
        }
        public void setY(double y) {
            this.y = y;
        }
        private double distance2(Point center) {
            return Math.pow((center.getX() - p.getX()), 2) + Math.pow((center.getY() - p.getY()), 2);
        }
    }
    public PriorityQueue<Point> findNearest(ArrayList<Point> points, Point center, int m) {
        PriorityQueue<Point> heap = new PriorityQueue<Point>(m,new Comparator<Point>() { //max heap
            @Override
            public int compare(Point a, Point b) {
                return  (b.distance2(center), a.distance2(center));
            }
        });
        for(int i=0; i<points.size(); i++) { //O(n)
            Point p = points.get(i);
            if (p==center) continue; //use reference equals as there may be other points with the same coordinates as the center.
            if (heap.size()<m) heap.add(p);
            else {
                if (p.distance2(center)<heap.peek().distance2(center)) { //O(1)
                    heap.remove(); //O(log(m));
                    heap.add(p); //O(log(m))
                }
            }
        }
        return  heap;
    }
    //Given a set of time intervals in any order, merge all overlapping intervals into one and output the result
    // {{1,3}, {2,4}, {5,7}, {6,8} }. output {1, 4} and {5, 8}
    class Interval
    {
        int start;
        int end;
    }
    void mergeIntervals(ArrayList<Interval> intervals)
    {
        // Test if the given set has at least one interval
        if (intervals.size() <= 0)
            return;
        // Create an empty stack of intervals
        Stack<Interval> s = new Stack<Interval>();
        // sort the intervals based on start time
        sort(intervals.begin(), intervals.end(), compareInterval);
        // push the first interval to stack
        s.push(intervals.get(0));
        // Start from the next interval and merge if necessary
        for (int i = 1 ; i < intervals.size(); i++)
        {
            // get interval from stack top
            Interval top = s.peek();
            // if current interval is not overlapping with stack top,
            // push it to the stack
            if (top.end < intervals.get(i).start)
            {
                s.push( intervals.get(i) );
            }
            // Otherwise update the ending time of top if ending of current
            // interval is more
            else if (top.end < intervals.get(i).end)
            {
                top.end = intervals.get(i).end;
                s.pop();
                s.push(top);
            }
        }
        // Print contents of stack
        while (!s.empty())
        {
            Interval t = s.peek();
            System.out.print("[" + t.start + "," + t.end + "]" + " ");
            s.pop();
        }
    }
    //Calculate the angle between hour hand and minute hand
    int calcAngle(int h, int m)
    {
        if (h <0 || m < 0 || h >12 || m > 60)
            System.out.print("Wrong input");
        if (h == 12) h = 0;
        if (m == 60) m = 0;
        int hour_angle = (h*60 + m) / 2;
        int minute_angle = 6*m;
        // Find the difference between two angles
        int angle = Math.abs(hour_angle - minute_angle);
        // Return the smaller angle of two possible angles
        angle = Math.min(360-angle, angle);
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
     private int getListSum(List<NestedInteger> lni, int depth)
     {
         int sum = 0;
         NestedInteger ni = null;
         while(lni.hasNext()){
             ni = lni.next();
             if(ni.isInteger()) sum += ni.getInteger() * depth;
             else sum += getListSum(ni.getList(), depth + 1);
         }
         return sum;
     }
    public int getSum(NestedInteger ni)
    {
        if(ni.isInteger()) return ni.getInteger();
        else return getListSum(ni.getList(), 1);
    }
    //Squareroot of a Number - O(logN)
    //2^0.5logN
    private int sqrt(int num) {
        if (num < 0)
            return 0;
        if (num == 1)
            return 1;
        int low = 0;
        int high = 1 + (num / 2);
        int mid;
        int square;
        while (low + 1 < high) {
            mid = low + (high - low) / 2;
            square = mid * mid;
            if (square == num)
                return mid;
            else if (square < num)
                low = mid;
            else
                high = mid;
        }
        return low;
    }
 }
