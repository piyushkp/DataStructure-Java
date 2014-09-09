package code.ds;

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
}
