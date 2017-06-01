package code.ds;
import java.time.LocalTime;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * Created by Piyush Patel.
 */
public class MISC {

    public static void main(String[] args) throws InterruptedException {
        //System.out.print("MISC");
        MISC misc = new MISC();
        /*ArrayList<Interval> input = new ArrayList<>();
        //input.add(misc.new Interval(1,4));
        input.add(misc.new Interval(5,10));
        input.add(misc.new Interval(1,2));
        //input.add(misc.new Interval(6,8));
        for(Interval item : misc.mergeIntervals(input)){
            System.out.println(item.start +"," + item.end);
        }*/
        Log l = new Log();
        l.id = "1";
        l.time = 10;
        Log l1 = new Log();
        l1.id = "2";
        l1.time = 11;
        Log l2 = new Log();
        l2.id = "1";
        l2.time = 13;
        Log l3 = new Log();
        l3.id = "1";
        l3.time = 14;
        Log l4 = new Log();
        l4.id = "1";
        l4.time = 15;
        Log[] logs = {l,l1,l2,l3,l4};
        //getBots(logs,3,3);
        getBots1(logs,3,3);

    }

    //Given a set of time intervals in any order, merge all overlapping intervals into one and output the result
    //{{1,3}, {2,4}, {5,7}, {6,8} }. output {1, 4} and {5, 8} Time Complexity: O(n Log n)
    class Interval {
        int start;
        int end;

        Interval(int s, int e) {
            start = s;
            end = e;
        }
    }

    public ArrayList<Interval> mergeIntervals(ArrayList<Interval> intervals) {
        if (intervals.size() == 0 || intervals.size() == 1)
            return intervals;
        // Sort Intervals in decreasing order of start time
        intervals.sort(new Comparator<Interval>() {
            @Override
            public int compare(Interval i1, Interval i2) {
                return i1.start - i2.start;
            }
        });
        Interval first = intervals.get(0);
        int start = first.start;
        int end = first.end;
        ArrayList<Interval> result = new ArrayList<>();
        for (int i = 1; i < intervals.size(); i++) {
            Interval current = intervals.get(i);
            if (current.start <= end) {
                end = Math.max(current.end, end);
            } else {
                result.add(new Interval(start, end));
                start = current.start;
                end = current.end;
            }
        }
        result.add(new Interval(start, end));
        return result;
    }

    /*Given a list of tuples representing intervals, return the range these UNIQUE intervals
    covered. e.g: [(1,3),(2,5),(8,9)] should return 5
    a) 1 2 3 = 2 unique intervals (1 to 2, 2 to 3)
    b) 2 3 4 5 = 2 unique intervals ( 3 to 4, 4 to 5) We did not count 2 - 3 since it was already counted.
    c) 8 9 = 1 unique interval
    result = 2 + 2 + 1 = 5 */
    private int getCoverageOfIntervals(ArrayList<Interval> intervals) {
        int range = 0;
        ArrayList<Interval> mergeIntervals = mergeIntervals(intervals);
        for (Interval _interval : mergeIntervals) {
            range += (_interval.end - _interval.start);
        }
        return range;
    }

    // Method to convert infix to postfix:
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^'
                || c == '(' || c == ')';
    }

    private boolean isSpace(char c) {  // Tell whether c is a space.
        return (c == ' ');
    }

    private boolean lowerPrecedence(char op1, char op2) {
        // Tell whether op1 has lower precedence than op2, where op1 is an
        // operator on the left and op2 is an operator on the right.
        // op1 and op2 are assumed to be operator characters (+,-,*,/,^).
        switch (op1) {
            case '+':
            case '-':
                return !(op2 == '+' || op2 == '-');
            case '*':
            case '/':
                return op2 == '^' || op2 == '(';
            case '^':
                return op2 == '(';
            case '(':
                return true;
            default:  // (shouldn't happen)
                return false;
        }
    }

    // Method to convert infix to postfix:
    public String infixToPostfix(String infix) {
        // Return a postfix representation of the expression in infix.
        Stack<String> operatorStack = new Stack<String>();  // the stack of operators
        char c;       // the first character of a token
        StringTokenizer parser = new StringTokenizer(infix, "+-*/^() ", true);
        // StringTokenizer for the input string
        StringBuffer postfix = new StringBuffer(infix.length());  // result
        // Process the tokens.
        while (parser.hasMoreTokens()) {
            String token = parser.nextToken();          // get the next token and let c be
            c = token.charAt(0);         // the first character of this token
            if ((token.length() == 1) && isOperator(c)) {    // if token is an operator
                while (!operatorStack.empty() &&
                        !lowerPrecedence((operatorStack.peek()).charAt(0), c))
                    // (Operator on the stack does not have lower precedence, so
                    //  it goes before this one.)
                    postfix.append(" ").append(operatorStack.pop());
                if (c == ')') {
                    // Output the remaining operators in the parenthesized part.
                    String operator = operatorStack.pop();
                    while (operator.charAt(0) != '(') {
                        postfix.append(" ").append(operator);
                        operator = operatorStack.pop();
                    }
                } else
                    operatorStack.push(token);// Push this operator onto the stack.
            } else if ((token.length() == 1) && isSpace(c)) {    // else if
                // token was a space
                ;                                                  // ignore it
            } else {  // (it is an operand)
                postfix.append(" ").append(token);  // output the operand
            }
        }
        // Output the remaining operators on the stack.
        while (!operatorStack.empty())
            postfix.append(" ").append(operatorStack.pop());
        // Return the result.
        return postfix.toString();
    }

    public void infixToPrefix(String infix) {
        //Step 1. Reverse the infix expression.
        //Step 2. Make Every '(' as ')' and every ')' as '('
        //Step 3. Convert expression to postfix form
        //Step 4. Reverse the expression.
    }

    //Evaluates the specified postfix expression.
    // infix evaluation: http://www.geeksforgeeks.org/expression-evaluation/
    public static int evaluate(String expr) {
        Stack<Integer> stack = new Stack<Integer>();
        int op1, op2, result = 0;
        String token;
        StringTokenizer tokenizer = new StringTokenizer(expr);
        while (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken();
            if (isOperator(token.charAt(0))) {
                op2 = (stack.pop()).intValue();
                op1 = (stack.pop()).intValue();
                result = evalSingleOp(token.charAt(0), op1, op2);
                stack.push(new Integer(result));
            } else
                stack.push(new Integer(Integer.parseInt(token)));
        }
        return result;
    }

    private static int evalSingleOp(char operation, int op1, int op2) {
        int result = 0;
        switch (operation) {
            case '+':
                result = op1 + op2;
                break;
            case '-':
                result = op1 - op2;
                break;
            case '*':
                result = op1 * op2;
                break;
            case '/':
                result = op1 / op2;
        }
        return result;
    }

    /*Given a matrix of following between N LinkedIn users (with ids from 0 to N-1):
    followingMatrix[i][j] == true if user i is following user j thus followingMatrix[i][j] doesn't imply followingMatrix[j][i].
    Let's also agree that followingMatrix[i][i] == false */
    //Logic: a person "i" is not an influencer if "i" is following any "j" or any "j" is not following "i"
    //Find Famous person in the list of persons.A person is a famous person if he doesn't know anyone in the list and
    //everyone else in the list should know this person.The function isKnow(i,j) => true/ false is given to us.
    static int getInfluencer(boolean[][] followingMatrix) {
        if (followingMatrix.length == 0 || followingMatrix[0].length == 0) return -1;
        // Phase 1. elimination
        int c = 0; // candidate
        for (int i = 1; i < followingMatrix.length; i++) {
            // Check if candidate
            if (followingMatrix[c][i] == true) c = i; // switch candidate
        }
        // Phase 2. validation
        for (int i = 0; i < followingMatrix.length; i++)
            if (followingMatrix[c][i] == true) return -1;
        return c;
    }

    public class Singleton {
        private Singleton uniqInstance;

        private Singleton() {
        }

        public synchronized Singleton getInstance() {
            if (uniqInstance == null) {
                uniqInstance = new Singleton();
            }
            return uniqInstance;
        }
    }

    //implement Java Iterable interface to read a file.
    class Line<T> {
        int LineNumber;
        byte[] LineData;
    }

    class FileReaderIterable<E> implements Iterable<E> {
        byte[] Data;

        FileReaderIterable(byte[] data) {
            this.Data = data;
        }

        public Iterator<E> iterator() {
            return new FileReaderIterator<E>(Data);
        }
    }

    class FileReaderIterator<T> implements Iterator {
        private byte[] data;
        private Queue<Byte> buffer = new java.util.LinkedList<Byte>();

        public FileReaderIterator(byte[] dat) {
            this.data = dat;
        }

        public synchronized boolean hasNext() {
            tryGetNext();
            return !this.buffer.isEmpty();
        }

        public synchronized Byte next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException("Nothing left");
            }
            return this.buffer.poll();
        }

        private void tryGetNext() {
            if (this.buffer.isEmpty()) {
                for (byte item : this.data) {
                    this.buffer.add(item);
                }
            }
        }

        public void remove() {
            throw new UnsupportedOperationException("It is read-only");
        }
    }

    //Given a 2-D int array, write an iterator that traverses it   from left to right and top to bottom.
    class twoDArrayIterator<T> {
        private List<List<Integer>> array;
        private int rowId;
        private int colId;
        private int numRows;

        public twoDArrayIterator(List<List<Integer>> array) {
            this.array = array;
            rowId = 0;
            colId = 0;
            numRows = array.size();
        }

        public boolean hasNext() {
            if (array == null || array.isEmpty()) {
                return false;
            }
            while (rowId < numRows && (array.get(rowId) == null ||
                    array.get(rowId).isEmpty())) {
                rowId++;
            }
            return rowId < numRows;
        }

        public int next() {
            int ret = array.get(rowId).get(colId);
            colId++;
            if (colId == array.get(rowId).size()) {
                rowId++;
                colId = 0;
            }
            return ret;
        }

        public void remove() {
            List<Integer> listToRemove;
            int rowToRemove;
            int colToRemove;
            // Case 1: if the element to remove is the last element of the row
            if (colId == 0) {
                rowToRemove = rowId - 1;
                listToRemove = array.get(rowToRemove);
                colToRemove = listToRemove.size() - 1;
                listToRemove.remove(colToRemove);
            } else { // Case 2: the element to remove is not the last element
                rowToRemove = rowId;
                listToRemove = array.get(rowToRemove);
                colToRemove = colId - 1;
                listToRemove.remove(colToRemove);
            }
            // If the list to remove has only one element
            if (listToRemove.isEmpty()) {
                array.remove(listToRemove);
                rowId--;
            }
            // Update the colId
            if (colId != 0) {
                colId--;
            }
        }
    }

    //Check if a given sequence of moves for a robot is circular or not
    // G - Go one unit     L - Turn left     R - Turn right
    //Input = "GLGLGLG" output = yes
    // This function returns true if the given path is circular, else false
    boolean isCircular(char path[]) {
        int N = 0, E = 1, S = 2, W = 3;
        // Initialize starting point for robot as (0, 0) and starting direction as N North
        int x = 0, y = 0;
        int dir = N;
        // Travers the path given for robot
        for (int i = 0; i < path.length; i++) {
            // Find current move
            char move = path[i];
            // If move is left or right, then change direction
            if (move == 'R')
                dir = (dir + 1) % 4;
            else if (move == 'L')
                dir = (4 + dir - 1) % 4;
                // If move is Go, then change  x or y according to current direction
            else {// if (move == 'G')
                if (dir == N)
                    y++;
                else if (dir == E)
                    x++;
                else if (dir == S)
                    y--;
                else // dir == W
                    x--;
            }
        }
        // If robot comes back to (0, 0), then path is cyclic
        return (x == 0 && y == 0);
    }

    //Egg dropping problem. eggs should be dropped so that total number of trials are minimized
    //Time Complexity: O(nk^2)  Auxiliary Space: O(nk) where n is eggs and K is floors
    public int calculate(int eggs, int floors) {
        int T[][] = new int[eggs + 1][floors + 1];
        int c = 0;
        for (int i = 0; i <= floors; i++) {
            T[1][i] = i;
        }
        for (int e = 2; e <= eggs; e++) {
            for (int f = 1; f <= floors; f++) {
                T[e][f] = Integer.MAX_VALUE;
                for (int k = 1; k <= f; k++) {
                    c = 1 + Math.max(T[e - 1][k - 1], T[e][f - k]);
                    if (c < T[e][f]) {
                        T[e][f] = c;
                    }
                }
            }
        }
        return T[eggs][floors];
    }

    //Given many points on a coordinate plane, find the pair of points that is the closest among all pairs of points.
    class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private int closestPairOfPoints(Point[] px, Point[] py, int start, int end) {
        if (end - start < 3) {
            //brute force
            //return computeMinDistance(px, start, end);
        }
        int mid = (start + end) / 2;
        Point[] pyLeft = new Point[mid - start + 1];
        Point[] pyRight = new Point[end - mid];
        int i = 0, j = 0;
        for (Point p : px) {
            if (p.x <= px[mid].x) {
                pyLeft[i++] = p;
            } else {
                pyRight[j++] = p;
            }
        }
        int d1 = closestPairOfPoints(px, pyLeft, start, mid);
        int d2 = closestPairOfPoints(px, pyRight, mid + 1, end);
        int d = Math.min(d1, d2);

        List<Point> deltaPoints = new ArrayList<Point>();
        for (Point p : px) {
            if (Math.sqrt(distance(p, px[mid])) < Math.sqrt(d)) {
                deltaPoints.add(p);
            }
        }
        int d3 = closest(deltaPoints);
        return Math.min(d3, d);
    }

    private int closest(List<Point> deltaPoints) {
        int minDistance = Integer.MAX_VALUE;
        for (int i = 0; i < deltaPoints.size(); i++) {
            for (int j = i + 1; j <= i + 7 && j < deltaPoints.size(); j++) {
                int distance = distance(deltaPoints.get(i), deltaPoints.get(j));
                if (minDistance < distance) {
                    minDistance = distance;
                }
            }
        }
        return minDistance;
    }

    private int distance(Point p1, Point p2) {
        return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
    }

    //draw a circle
    void drawCircle(int r) {
        // Consider a rectangle of size N*N
        int N = 2 * r + 1;
        int x, y;  // Coordinates inside the rectangle
        // Draw a square of size N*N.
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // Start from the left most corner point
                x = i - r;
                y = j - r;
                // If this point is inside the circle, print it
                if (x * x + y * y <= r * r + 1)
                    System.out.print(".");
                else // If outside the circle, print space
                    System.out.print(" ");
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    /*Museum Problem : Given a 2D grid of rooms which can be closed, open with no guard, or open with a guard.
    Return a grid with each square labeled with the distance to the nearest guard.
    Idea is to start from every guard position and do BFS search in all 4 directions and maintain the distance of every
    space from guard. If another exit in future iterator is closer than already calculated exit then update
    the distance.
    Space complexity is O(n*m) Time complexity is O(number of guard * m * n);*/
    enum Room {
        Open,
        Closed,
        GUARD
    }

    public int[][] findShortest(Room input[][]) {
        int distance[][] = new int[input.length][input[0].length];
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                distance[i][j] = Integer.MAX_VALUE;
            }
        }
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                //for every guard location do a BFS starting with this guard as the origin
                if (input[i][j] == Room.GUARD) {
                    distance[i][j] = 0;
                    setDistance(input, i, j, distance);
                }
            }
        }
        return distance;
    }

    private void setDistance(Room input[][], int x, int y, int distance[][]) {
        boolean visited[][] = new boolean[input.length][input[0].length];
        Queue<Point> q = new java.util.LinkedList<Point>();
        q.offer(new Point(x, y));
        //Do a BFS at keep updating distance.
        while (!q.isEmpty()) {
            Point p = q.poll();
            setDistanceUtil(q, input, p, getNeighbor(input, p.x + 1, p.y), distance, visited);
            setDistanceUtil(q, input, p, getNeighbor(input, p.x, p.y + 1), distance, visited);
            setDistanceUtil(q, input, p, getNeighbor(input, p.x - 1, p.y), distance, visited);
            setDistanceUtil(q, input, p, getNeighbor(input, p.x, p.y - 1), distance, visited);
        }
    }

    private void setDistanceUtil(Queue<Point> q, Room input[][], Point p, Point newPoint, int distance[][], boolean visited[][]) {
        if (newPoint != null && !visited[newPoint.x][newPoint.y]) {
            if (input[newPoint.x][newPoint.y] != Room.GUARD && input[newPoint.x][newPoint.y] != Room.Closed) {
                distance[newPoint.x][newPoint.y] = Math.min(distance[newPoint.x][newPoint.y], 1 + distance[p.x][p.y]);
                visited[newPoint.x][newPoint.y] = true;
                q.offer(newPoint);
            }
        }
    }

    private Point getNeighbor(Room input[][], int x, int y) {
        if (x < 0 || x >= input.length || y < 0 || y >= input[0].length) {
            return null;
        }
        return new Point(x, y);
    }

    //Airbnb: Achieve a mini parser, enter the string in the following format : "324" or "[123,456, [788,799,833], [[]], 10, []]"
    //Required output : 324 or [123,456, [788,799,833], [[]], 10, []].That is, to convert a string into the corresponding data formats.
    private static class NestedIntList {
        private int value;
        private boolean isNumber;
        private List<NestedIntList> intList;

        // Constructor to construct a number
        public NestedIntList(int value) {
            this.value = value;
            isNumber = true;
        }

        // Constructor to construct a list
        public NestedIntList() {
            intList = new ArrayList<NestedIntList>();
            isNumber = false;
        }

        public void add(NestedIntList num) {
            this.intList.add(num);
        }

        public NestedIntList miniParser(String s) {
            if (s == null || s.length() == 0) {
                return null;
            }
            // Corner case "123"
            if (s.charAt(0) != '[') {
                int num = Integer.parseInt(s);
                return new NestedIntList(num);
            }
            int i = 0;
            int counter = 1;
            Stack<NestedIntList> stack = new Stack<NestedIntList>();
            NestedIntList result = null;
            while (i < s.length()) {
                char c = s.charAt(i);
                if (c == '[') {
                    NestedIntList num = new NestedIntList();
                    if (!stack.isEmpty()) {
                        stack.peek().add(num);
                    }
                    stack.push(num);
                    counter = i + 1;
                } else if (c == ',' || c == ']') {
                    if (counter != i) {
                        int value = Integer.parseInt(s.substring(counter, i));
                        NestedIntList num = new NestedIntList(value);
                        stack.peek().add(num);
                    }
                    counter = i + 1;
                    if (c == ']') {
                        result = stack.pop();
                    }
                }
                i++;
            }
            return result;
        }

        public String toString() {
            if (this.isNumber) {
                return this.value + "";
            } else {
                return this.intList.toString();
            }
        }
    }

    //Given a nested list of integers, returns the sum of all integers in the list weighted by their depth. For example,
    //given the list {{1,1},2,{1,1}} the function should return 10 (four 1's at depth 2, one 2 at depth 1)
    private static int depthNestedIntSum(List<NestedIntList> input, int level) {
        if (input == null || input.size() == 0) {
            return 0;
        }
        int sum = 0;
        for (int i = 0; i < input.size(); i++) {
            if (input.get(i).isNumber) {
                sum += input.get(i).value * level;
            } else {
                sum += depthNestedIntSum(input.get(i).intList, level + 1);
            }
        }
        return sum;
    }
    //Follow up: What if the list is in reverse order?
    //In that case get the depth first and call same above function with each recursive call do level - 1;

    //Given two rectangles, find if the given two rectangles overlap or not.
    boolean doOverlap(Point l1, Point r1, Point l2, Point r2) {
        // If one rectangle is on left side of other
        if (l1.x > r2.x || l2.x > r1.x)
            return false;
        // If one rectangle is above other
        if (l1.y < r2.y || l2.y < r1.y)
            return false;
        return true;
    }

    //Given n appointments, find all conflicting appointments
    //Interval Tree implementation. http://www.davismol.net/2016/02/07/data-structures-augmented-interval-tree-to-search-for-interval-overlapping/
    class Interval1 implements Comparable<Interval1> {
        public int start;
        public int end;
        public int max;
        public Interval1 left;
        public Interval1 right;

        public Interval1(int start, int end) {
            this.start = start;
            this.end = end;
            this.max = end;
        }

        @Override
        public int compareTo(Interval1 i) {
            if (this.start < i.start) {
                return -1;
            } else if (this.start == i.start) {
                return this.end <= i.end ? -1 : 1;
            } else {
                return 1;
            }
        }
    }

    class IntervalTree {
        private Interval1 root;

        public Interval1 insert(Interval1 root, final Interval1 newNode) {
            if (root == null) {
                root = newNode;
                return root;
            }
            // Get low value of interval at root
            int l = root.start;
            // If root's low value is smaller, then new interval goes to left subtree
            if (newNode.start < l)
                root.left = insert(root.left, newNode);
                // Else, new node goes to right subtree.
            else
                root.right = insert(root.right, newNode);
            // Update the max value of this ancestor if needed
            if (root.max < newNode.end)
                root.max = newNode.end;

            return root;
        }

        //Find all overlapping intervals for given interval
        public void intersectInterval(Interval1 root, Interval1 i, List<Interval1> output) {
            if (root == null) {
                return;
            }
            if (!((root.start > i.end)) || (root.end < i.start)) {
                if (output == null) {
                    output = new ArrayList<Interval1>();
                }
                output.add(root);
            }
            if ((root.left != null) && (root.left.max >= i.start)) {
                this.intersectInterval(root.left, i, output);
            }
            this.intersectInterval(root.right, i, output);
        }

        //Find all non-overlapping intervals for given interval
        public void nonOverlappingInterval(Interval1 root, Interval1 i, List<Interval1> output) {
            if (root == null) {
                return;
            }
            if (!((root.start < i.end)) || (root.end > i.start)) {
                if (output == null) {
                    output = new ArrayList<Interval1>();
                }
                output.add(root);
            }
            if ((root.left != null) && (root.left.max <= i.start)) {
                this.intersectInterval(root.left, i, output);
            }
            this.intersectInterval(root.right, i, output);
        }

        // A utility function to check if given two intervals overlap
        boolean doOVerlap(Interval1 i1, Interval1 i2) {
            if (i1.start <= i2.end && i2.start <= i1.end)
                return true;
            return false;
        }

        Interval1 overlapSearch(Interval1 root, Interval1 i) {
            // Base Case, tree is empty
            if (root == null) return null;
            // If given interval overlaps with root
            if (doOVerlap(root, i)) {
                return root;
            }
            // If left child of root is present and max of left child is greater than or equal to given interval, then i may
            // overlap with an interval is left subtree
            if (root.left != null && root.left.max >= i.start)
                return overlapSearch(root.left, i);
            // Else interval can only overlap with right subtree
            return overlapSearch(root.right, i);
        }

        // This function prints all conflicting appointments in a given array of appointments.
        void printConflicting(List<Interval1> appt, int n) {
            // Create an empty Interval Search Tree, add first appointment
            Interval1 root = null;
            root = insert(root, appt.get(0));
            // Process rest of the intervals
            for (int i = 1; i < n; i++) {
                // If current appointment conflicts with any of the existing intervals, print it
                Interval1 res = overlapSearch(root, appt.get(i));
                if (res != null)
                    System.out.println("[" + appt.get(i).start + "," + appt.get(i).end + "] Conflicts with [" + res.start + ","
                            + res.end + "]");
                // Insert this appointment
                root = insert(root, appt.get(i));
            }
        }
    }

    /*Implement LRU Cache.
    1. If cache has free entry, add the page entry to queue and make it head.
    2. If cache hit, remove the item from present location and add it to front of queue and make it head.
    3. If cache is full and its cache miss, remove the item at end and insert the new item at front of queue.
    4. To check hit or miss, use hash table.
     So at front items would be most recently used while in the end of queue least recently used items
    O(1) all operations*/
    class DoublyNode {
        int data;
        int key;
        DoublyNode next;
        DoublyNode prev;
    }

    class LRU {
        HashMap<Integer, DoublyNode> map;
        int capacity;
        DoublyNode head;
        DoublyNode end;

        LRU(int capacity) {
            this.capacity = capacity;
            map = new HashMap<>();
        }

        private void add(DoublyNode item) {
            if (head == null) {
                head = item;
                end = item;
            }
            head.prev = item;
            item.next = head;
            head = item;
        }

        private void remove(DoublyNode item) {
            if (head == null || item == null)
                return;
            else if (head == item && head == end) {
                head = null;
                end = null;
            } else if (head == item) {
                head.next.prev = null;
                head = head.next;
            } else if (end == item) {
                end.prev.next = null;
                end = end.prev;
            } else {
                item.prev.next = item.next;
                item.next.prev = item.prev;
            }
        }

        private void moveFirst(DoublyNode item) {
            remove(item);
            add(item);
        }

        private void removeLast() {
            remove(this.end);
        }

        public int get(int key) {
            if (map.containsKey(key)) {
                DoublyNode node = map.get(key);
                moveFirst(node);
                return node.data;
            }
            return -1;
        }

        public void set(int key, int value) {
            //cache hit
            if (map.containsKey(key)) {
                DoublyNode node = map.get(key);
                moveFirst(node);
                node.data = value;
                map.put(key, node);
                return;
            }
            //cache is full and cache miss
            if (map.size() >= capacity) {
                removeLast();
                map.remove(this.end.key);
            }
            DoublyNode node = new DoublyNode();
            node.key = key;
            node.data = value;
            add(node);
            map.put(key, node);
        }

        public void removeCache(int key) {
            if (map.containsKey(key)) {
                DoublyNode node = map.get(key);
                map.remove(key);
                remove(node);
            }
        }
    }

    //Implement a peek using a existing iterator next and hasnext function.
    class PeekingIterator implements Iterator<Integer> {
        private Integer next; //cache the next peek
        private Iterator<Integer> iter;

        public PeekingIterator(Iterator<Integer> iterator) {
            // initialize any member here.
            iter = iterator;
            if (iter.hasNext()) {
                next = iter.next();
            }
        }

        // Returns the next element in the iteration without advancing the iterator.
        public Integer peek() {
            return next;
        }

        // hasNext() and next() should behave the same as in the Iterator interface. Override them if needed.
        @Override
        public Integer next() {
            Integer ret = next;
            next = iter.hasNext() ? iter.next() : null;
            return ret;
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }
    }

    //Reverse a stack using recursion. You are not allowed to use loops or data structure.
    public void reverse(Stack<Integer> stack) {
        if (stack.isEmpty() || stack.size() == 1) {
            return;
        }
        int top = stack.pop();
        this.reverse(stack);
        this.insertAtBottom(stack, top);
    }

    private void insertAtBottom(Stack<Integer> stack, int val) {
        if (stack.isEmpty()) {
            stack.push(val);
            return;
        }
        int temp = stack.pop();
        this.insertAtBottom(stack, val);
        stack.push(temp);
    }

    //palantir magic box
    //https://github.com/siddharthgoel88/problem-solving/blob/master/Hackerrank/Palantir-magic-box/src/Solution.java
    //https://github.com/vrdmr/interview-prep/blob/master/DataStructuresAndAlgorithms/src/interviewquestions/palantir/Palantir-Question.png
    private static Map<String, Integer> columnFlippingStat;
    private static int maxWishes = -1;

    private static void findFlippingSet(char[] row) {
        StringBuilder allP = new StringBuilder();
        StringBuilder allT = new StringBuilder();
        for (int i = 0; i < row.length; i++) {
            if (row[i] == 'P') {
                allP.append('0');
                allT.append('1');
            } else {
                allP.append('1');
                allT.append('0');
            }
        }
        int allPFreq = updateSet(allP.toString());
        int allTFreq = updateSet(allT.toString());
        maxWishes = Math.max(maxWishes, Math.max(allPFreq, allTFreq));
    }

    private static int updateSet(String flippedCols) {
        int freq = 0;
        if (columnFlippingStat.containsKey(flippedCols)) {
            freq = columnFlippingStat.get(flippedCols);
        }
        columnFlippingStat.put(flippedCols, freq + 1);
        return freq + 1;
    }

    // Connect4: Implement the function that takes a board string
    // and decodes it into the representative 2D array.
    //    |_|_|_|_|_|_|_|
    //    |_|_|r|_|_|_|_|
    //    |b|r|b|r|b|r|_|
    //    |b|b|b|r|r|b|_|
    //    |b|r|r|b|b|r|_|
    //    |r|b|b|r|r|r|b|
    //    CFN: 9_r4_brbrbr_3b2rb_b2r2br_r2b3rb
    public static char[][] decodeBoard(String str) {
        char[] input = str.toCharArray();
        char[][] output = new char[6][7];
        char[] temp = new char[42];
        int index = 0;
        for (int i = 0; i < input.length; i++) {
            if (isInteger(Character.toString(input[i]))) {
                int number = Character.getNumericValue(input[i]);
                for (int l = 0; l < number; l++) {
                    temp[index + l] = input[i + 1];
                }
                index += number;
                i++;
            } else {
                temp[index] = input[i];
                index++;
            }
        }
        for (int k = 0; k < 6; k++) {
            for (int j = 0; j < 7; j++) {
                output[k][j] = temp[7 * k + j];
            }
        }
        return output;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    //Implement data structure “Map” storing pairs of integers (key, value) and define following member functions in O(1)
    //runtime: void insert(key, value), void delete(key), int get(key), int getRandomKey().
    class CustomMap {
        HashMap<String, List<Integer>> _map = new HashMap<>();
        ArrayList<String> arr = new ArrayList<>();
        int index;
        int size;

        public void Insert(String key, int value) {
            if (!_map.containsKey(key)) {
                index = size;
                List<Integer> temp = new ArrayList<>();
                temp.add(value);
                temp.add(index);
                _map.put(key, temp);
                arr.add(index, key);
                size++;
            } else {
                List<Integer> _list = _map.get(key);
                _list.set(0, value);
                _map.put(key, _list);
            }
        }

        public int Get(String key) {
            return _map.get(key).get(0);
        }

        public void Delete(String key) {
            index = _map.get(key).get(1);
            //copy last element at array index and remove last element, so delete can done in O(1)
            arr.set(index, arr.get(size - 1));
            arr.remove(size - 1);
            size--;
            //delete from the map
            _map.remove(key);
            //update the index of the swapped key
            _map.get(arr.get(index)).set(1, index);
        }

        public String GetRandomKey() {
            int r = (int) (Math.random() * size + 1);
            return arr.get(r);
        }

        public void clear() {
            size = 0;
        }
    }
    //data structure to add(value), contains(value), delete(value), random()

    // data structure to add(name, value), get(name), delete(name), count(value)


    //Add a third dimension of time to a hashmap,so ur hashmap will look something like this - HashMap<K, t, V> where
    //t is a float value. Implement the get and put methods to this map. The get method should be something like - map.get(K,t)
    //which should give us the value. If t does not exists then map should return the closest t' such that t' is smaller than t.
    //For example, if map contains (K,1,V1) and (K,2,V2) and the user does a get(k,1.5) then the output should be v1 as 1 is the next smallest number to 1.5
    class TimeHashMap<Key, Time, Value> {
        private HashMap<Key, TreeMap<Time, Value>> map = new HashMap<>();

        public Value get(Key key, Time time) {
            TreeMap<Time, Value> tree = map.get(key);
            if (tree == null) return null;
            final Time floorKey = tree.floorKey(time);
            return floorKey == null ? null : tree.get(floorKey);
        }

        public void put(Key key, Time time, Value value) {
            if (!map.containsKey(key)) {
                map.put(key, new TreeMap<>());
            }
            map.get(key).put(time, value);
        }
    }

    //how do you count the number of visitors for the past 1 minute?
    class HitCounter {
        java.util.concurrent.atomic.AtomicIntegerArray time;
        java.util.concurrent.atomic.AtomicIntegerArray hit;

        /**
         * Initialize your data structure here.
         */
        public HitCounter() {
            time = new java.util.concurrent.atomic.AtomicIntegerArray(60);
            hit = new java.util.concurrent.atomic.AtomicIntegerArray(60);
        }

        /**
         * Record a hit.
         *
         * @param timestamp - The current timestamp (in seconds granularity).
         */
        public void hit(int timestamp) {
            int index = timestamp % 60;
            if (time.get(index) != timestamp) {
                time.set(index, timestamp);
                hit.set(index, 1);
            } else {
                hit.incrementAndGet(index);//add one
            }
        }

        /**
         * Return the number of hits in the past 5 minutes.
         *
         * @param timestamp - The current timestamp (in seconds granularity).
         */
        public int getHits(int timestamp) {
            int total = 0;
            for (int i = 0; i < 60; i++) {
                if (timestamp - time.get(i) < 60) {
                    total += hit.get(i);
                }
            }
            return total;
        }
    }
    /*A bot is an id that visit the site m times in the last n seconds, given a list of logs with id and time sorted by time, return all the bots's id */
    static class Log{
        String id;
        int time;
    }
    static class LogCount{
        int time;
        int count;
        public LogCount(int time, int count){
            this.time = time;
            this.count = count;
        }
    }
    public static HashSet<String> getBots1(Log[] logs, int m, int n){
        HashMap<String, LogCount> map = new HashMap<>();
        HashSet<String> out = new HashSet<>();
        for(Log log: logs){
            if (map.containsKey(log.id) && (log.time - map.get(log.id).time <= n)) {
                LogCount _lc = map.get(log.id);
                _lc.time = log.time;
                _lc.count++;
                map.put(log.id,_lc);
               if(map.get(log.id).count == m) {
                   System.out.println(log.id);
                   out.add(log.id);
               }
            }else
                map.put(log.id, new LogCount(log.time,1));
        }
        return  out;
    }
    public static HashSet<String> getBots(Log[] logs, int m, int n) {
        int[] time  = new int[n];
        AtomicReferenceArray  data = new AtomicReferenceArray(n);
        //HashMap<String, LogCount> bot = new HashMap<>(n);
        HashSet<String> output = new HashSet<>();
        int count = 0;
        for (Log log: logs) {
            HashMap<String, Integer> temp;
            int index = log.time % n;
            if (time[index] != log.time) {
                temp =  (HashMap<String, Integer>)data.get(index);
                if(temp != null && temp.containsKey(log.id))
                {
                    temp.put(log.id, temp.get(log.id) + 1);
                }
                else {
                    time[index] = log.time;
                    temp = new HashMap<>();
                    temp.put(log.id, 1);
                    data.set(index, temp);
                }
            } else {
                temp =  (HashMap<String, Integer>)data.get(index);
                if (!temp.containsKey(log.id))
                    temp.put(log.id,1);
                else
                    temp.put(log.id, temp.get(log.id) + 1);
            }
            if(count > n){
            for (int i = 0; i < n; i++) {
                if (log.time - time[i] < n) {
                    HashMap<String, Integer> _map = (HashMap<String, Integer>)data.get(i);
                    for(String key : _map.keySet()){
                        if(_map.get(key) >= m) {
                            System.out.println(key);
                            output.add(key);
                        }
                    }
                }
            }}
            count++;
        }
        return output;
    }

    //Stream deduplication - Give a real-time source of data that emits strings.
    // print last 1 minute unique data.
    class Streamdedu {
        AtomicIntegerArray time;
        java.util.concurrent.atomic.AtomicReferenceArray data;

        public Streamdedu() {
            time = new AtomicIntegerArray(60);
            data = new java.util.concurrent.atomic.AtomicReferenceArray(60);
        }

        void OnDataReceived(String input, int timestamp) {
            Set<String> temp;
            int index = timestamp % 60;
            if (time.get(index) != timestamp) {
                time.set(index, timestamp);
                temp = new HashSet<>();
                temp.add(input);
                data.set(index, temp);
            } else {
                temp = (HashSet<String>) data.get(index);
                if (!temp.contains(input))
                    temp.add(input);
            }
        }

        public Set<String> PrintData(int timestamp) {
            Set<String> output = new HashSet<>();
            for (int i = 0; i < 60; i++) {
                if (timestamp - time.get(i) < 60) {
                    output.addAll((HashSet<String>) data.get(i));
                }
            }
            return output;
        }
    }

    /* Design a logger system that receive stream of messages along with its timestamps, each message should be printed
    if and only if it is not printed in the last 10 seconds. Given a message and a timestamp (in seconds granularity),
    return true if the message should be printed in the given timestamp, otherwise returns false.
    It is possible that several messages arrive roughly at the same time.*/
    class Logger {
        // Fast but space complexity is very high as map will keep on adding data
        private Map<String, Integer> map = new HashMap<>();

        public boolean shouldPrintMessage(int timestamp, String message) {
            if (map.containsKey(message) && (timestamp - map.get(message) < 10)) {
                return false;
            }
            map.put(message, timestamp);
            return true;
        }
    }
    /* Given input which is vector of log entries of some online system each entry is something like (user_name, login_time, logout_time),
    come up with an algorithm with outputs number of users logged in the system at each time slot in the input, output should contain only the time slot which are in the input.
    input: [
            ("Jane", 1.2, 4.5),
            ("Jin", 3.1, 6.7),
            ("June", 8.9, 10.3)
           ]
    Output: [(1.2, 1), (3.1, 2), (4.5, 1), (6.7, 0), (8.9, 1), (10.3,0)] */
    class Input {
        String name;
        double login;
        double logout;
    }
    class Type implements Comparable<Type> {
        boolean loggedin;
        double time;
        public Type(double time, boolean loogedIn){
            this.time = time;
            this.loggedin = loogedIn;
        }
        @Override
        public int compareTo(Type that) {
            return (int) (this.time - that.time);
        }
    }

    class Output {
        double time;
        int numLoggedIn;
        public Output(double t, int num){
            time = t;
            numLoggedIn = num;
        }
    }

    public List<Output> findLoggedIn(List<Input> list) {
        List<Type> loggedIn = new ArrayList<>();
        List<Output> retValue = new ArrayList<>();
        int loggedInNow = 0;
        for (Input iv: list) {
            loggedIn.add(new Type(iv.login, true));
            loggedIn.add(new Type(iv.logout, false));
        }
        Collections.sort(loggedIn);
        for(Type t: loggedIn) {
            if (t.loggedin == true)
                loggedInNow++;
            else loggedInNow--;
            retValue.add(new Output(t.time, loggedInNow));
        }
        return retValue;
    }

    //Knight tour on keypad
    void KnightTour(){
        int keypad[][]  = new int[3][4];
        int[] values = {1,2,3,4,5,6,7,8,9,-1,0,-1};
        int count = 0;
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 3; j++)
                keypad[i][j] = values[count];
                count++;
        int[][] mtable = new int[11][10];

    }
    int KnightUtil(int[][] table, int[][] mem, int digits, int start)
    {
        if (digits == 1)
            return 1;
        if (mem[digits][start] == 0) {
            for(int next : nextKnightMove(start, table))
                mem[digits][start] += KnightUtil(table, mem, digits - 1, next);
        }
        //#else:
        //#print("found ...",digits,start)
        return mem[digits][start];
    }
    int[] nextKnightMove(int start, int[][] table)
    {
        return new int[2];
    }
    //Given users with locations in a list and a logged in user with locations. find given User's travel buddies (people who shared more than half of your locations).
    public static void preProcess(List<javafx.util.Pair<String, List<Integer>>> input, javafx.util.Pair<String, List<Integer>> user)
    {
        HashMap<Integer, List<String>> map = new HashMap<>(); // map key is location, values is list of users
        for(javafx.util.Pair<String, List<Integer>> p : input){
            List<String> _users;
            for(Integer location: p.getValue()) {
                if (map.get(location) == null)
                    _users = new ArrayList<>();
                else
                    _users = map.get(location);
                map.put(location,_users);
            }
        }
        findTravelBuddy(user, map);
    }
    public static List<String> findTravelBuddy(javafx.util.Pair<String, List<Integer>> user, HashMap<Integer, List<String>> map){
        List<String> _output = new ArrayList<>();
        List<String> temp = new ArrayList<>();
        int N = user.getValue().size();
        HashMap<String, Integer> _commonUsers = new HashMap<>();
        for(Integer _loc : user.getValue()){
            if(map.containsKey(_loc)){
                temp.addAll(map.get(_loc));
            }
        }
        // find majority element from the temp array
        for(String _user : temp){
            if(_commonUsers.containsKey(_user))
                _commonUsers.put(_user, _commonUsers.get(_user) + 1);
            else
                _commonUsers.put(_user,1);
        }
        for(String u : _commonUsers.keySet()){
            if(_commonUsers.get(u) > N/2)
                _output.add(u);
        }
        return _output;
    }
    //Implement Browser prev, forward, and History functionality
    class BrowserNode {
        BrowserNode next;
        BrowserNode prev;
        String url;
        Date time;
        BrowserNode curr;
        public BrowserNode(String url){
            this.url = url;
            //this.time = time;
        }
    }
    class Browser{
        BrowserNode head;
        BrowserNode end;
        BrowserNode curr;
        int size = 0;
        public Browser(int size){
            head = null;
            end = null;
            curr = null;
            this.size = size;
        }
        public void add(String url){
            BrowserNode node = new BrowserNode(url);
            if(head == null){
                head = node;
                end = head;
            }
            else{
                node.prev = end;
                end.next = node;
                end = node;
                end = node;
            }
            curr = node;
            size++;
        }
        public String forward(){
            if(curr != null && curr.next != null) {
                curr.next = curr.next.next;
                curr.prev = curr;
                curr = curr.next;
                return curr.next.url;
            }
            return  null;
        }
        public String backward(){
            if(curr != null && curr.prev != null) {
                curr.next = curr;
                curr.prev = curr.prev.prev;
                curr = curr.prev;
                return curr.prev.url;
            }
            return  null;
        }
    }
}
