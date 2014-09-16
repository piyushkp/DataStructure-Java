package code.ds;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by ppatel2 on 9/8/2014.
 */
public class Numbers
{
    //Implement power function efficiently
    private int intpow(int base, int exp) {
        if (exp == 0)
            return 1;
        else if (exp == 1)
            return base;
        else if ((exp & 1) != 0) //Odd
            return base * intpow(base * base, exp / 2);
        else
            return intpow(base * base, exp / 2);
    }
    //Iterative and handles negative
    public static double pow (double base, int exponent)
    {
        boolean NegExp=false;
        double result=1;
        if (exponent==0)
            return 1;
        if (exponent<0)
        {
            NegExp=true;
            exponent*=-1;
        }
        while (exponent>0)
        {
            if ((exponent&1)==1)
                result *= base;
            base*=base;
            exponent=exponent>>1;
        }
        if (NegExp)
            result=1/result;
        return result;
    }
    //Implement decimal to roman and vice versa
    public static String IntegerToRomanNumeral(int input) {
        if (input < 1 || input > 3999)
            return "Invalid Roman Number Value";
        String s = "";
        while (input >= 1000) {
            s += "M";
            input -= 1000;        }
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
    // Find the Nearest points in A Plane
    public ArrayList<Point> findNearest(Point center, int m) {
        PriorityQueue<Point> q = new PriorityQueue<Point>();
        for (Point p : points){
            double dist = Math.pow((center.getX() - p.getX()),2) + Math.pow((center.getY() - p.getY()),2) ;
            p.setDistFromCenter(dist);
            q.add(p);
        }
        ArrayList<Point> nearestPoints = new ArrayList<Point>();
        for (int i = 0; i < m; i++){
            nearestPoints.add(q.pool());
        }
        return nearestPoints;
    }
    //Given a set of time intervals in any order, merge all overlapping intervals into one and output the result
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
 }
