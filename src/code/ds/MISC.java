package code.ds;
import java.time.LocalTime;
import java.util.*;
/**
 * Created by Piyush Patel.
 */
public class MISC {

    public static void main(String[] args) {
        //System.out.print("MISC");

        List<Token> list = new ArrayList<>();
        list.add(new MISC().new Token("NUM", -4));
        list.add(new MISC().new Token("*"));
        list.add(new MISC().new Token("NUM", 3));
        list.add(new MISC().new Token("*"));
        list.add(new MISC().new Token("NUM", 2));
        list.add(new MISC().new Token("/"));
        list.add(new MISC().new Token("NUM", 2));
        list.add(new MISC().new Token("+"));
        list.add(new MISC().new Token("NUM", 4));
        System.out.println(evalExpr(list));
    }

    //Given a set of time intervals in any order, merge all overlapping intervals into one and output the result
    //{{1,3}, {2,4}, {5,7}, {6,8} }. output {1, 4} and {5, 8} Time Complexity: O(n Log n)
    class Interval {
        int start;
        int end;
    }

    ArrayList<Interval> mergeIntervals(ArrayList<Interval> intervals) {
        ArrayList<Interval> _output = new ArrayList<Interval>();
        if (intervals.size() <= 0) return null;
        Stack<Interval> s = new Stack<Interval>();
        // sort the intervals based on start time
        intervals.sort(new Comparator<Interval>() {
            @Override
            public int compare(Interval i1, Interval i2) {
                return (i1.start < i2.start) ? 1 : 0;
            }
        });
        int index = 0;
        s.push(intervals.get(0));
        for (int i = 1; i < intervals.size(); i++) {
            Interval top = s.peek();
            if (top.end < intervals.get(i).start) {
                s.push(intervals.get(i));
            } else if (top.end < intervals.get(i).end) {
                top.end = intervals.get(i).end;
                s.pop();
                s.push(top);
            }
        }
        while (!s.empty()) {
            Interval t = s.peek();
            _output.add(t);
            System.out.print("{" + t.start + "," + t.end + "}" + " ");
            s.pop();
        }
        return _output;
    }

    //Without space require
    ArrayList<Interval> mergeIntervals1(ArrayList<Interval> intervals) {
        if (intervals.size() <= 0) return null;
        // Sort Intervals in decreasing order of start time
        intervals.sort(new Comparator<Interval>() {
            @Override
            public int compare(Interval i1, Interval i2) {
                return (i1.start > i2.start) ? 1 : 0;
            }
        });
        int index = 0; // Stores index of last element in output array (modified arr[])
        // Traverse all input Intervals
        for (int i = 0; i < intervals.size(); i++) {
            // If this is not first Interval and overlaps with the previous one
            if (index != 0 && intervals.get(index - 1).start <= intervals.get(i).end) {
                while (index != 0 && intervals.get(index - 1).start <= intervals.get(i).end) {
                    // Merge previous and current Intervals
                    intervals.get(index - 1).end = Math.max(intervals.get(index - 1).end, intervals.get(i).end);
                    intervals.get(index - 1).start = Math.min(intervals.get(index - 1).start, intervals.get(i).start);
                    index--;
                }
            } else // Doesn't overlap with previous, add to solution
                intervals.set(index, intervals.get(i));

            index++;
        }
        return intervals;
    }

    //Without extra Space
    void mergeIntervals(Interval arr[], int n) {
        // Sort Intervals in decreasing order of
        // start time
        Arrays.sort(arr);
        int index = 0; // Stores index of last element
        // in output array (modified arr[])
        // Traverse all input Intervals
        for (int i = 0; i < n; i++) {
            // If this is not first Interval and overlaps with the previous one
            if (index != 0 && arr[index - 1].start <= arr[i].end) {
                while (index != 0 && arr[index - 1].start <= arr[i].end) {
                    // Merge previous and current Intervals
                    arr[index - 1].end = Math.max(arr[index - 1].end, arr[i].end);
                    arr[index - 1].start = Math.min(arr[index - 1].start, arr[i].start);
                    index--;
                }
            } else // Doesn't overlap with previous, add to
                // solution
                arr[index] = arr[i];
            index++;
        }
        // Now arr[0..index-1] stores the merged Intervals
        for (int i = 0; i < index; i++)
            System.out.print(arr[i].start + ", " + arr[i].end);
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
    private boolean isOperator(char c) {
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
    public int evaluate(String expr) {
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

    private int evalSingleOp(char operation, int op1, int op2) {
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
    followingMatrix[i][j] == true if user i is following user j
    thus followingMatrix[i][j] doesn't imply followingMatrix[j][i].
    Let's also agree that followingMatrix[i][i] == false */
    //Logic: a person "i" is not an influencer if "i" is following any "j" or any "j" is not following "i"
    private int getInfluencer(Boolean[][] M) {
        for (int influencer = 0; influencer < M.length; influencer++) {
            boolean is_influencer = true;
            for (int followedBy = 0; followedBy < M.length; followedBy++) {
                if (influencer == followedBy) continue; //the same user, check the next user
                if (M[influencer][followedBy] || !M[followedBy][influencer]) {
                    is_influencer = false;
                    break;
                }
            }
            if (is_influencer)
                return influencer;
        }
        return -1;
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

    /* Find the number of islands. Given a boolean 2D matrix, find the number of islands.
    This is an variation of the standard problem: �Counting number of connected components in a undirected graph�.
    Time complexity: O(ROW x COL) */
    //No of rows and columns
    static final int ROW = 5, COL = 5;

    // A function to check if a given cell (row, col) can be included in DFS
    boolean isSafe(int M[][], int row, int col, boolean visited[][]) {
        // row number is in range, column number is in range and value is 1 and not yet visited
        return (row >= 0) && (row < ROW) && (col >= 0) && (col < COL) && (M[row][col] == 1 && !visited[row][col]);
    }

    // A utility function to do DFS for a 2D boolean matrix. It only considers the 8 neighbors as adjacent vertices
    void DFS(int M[][], int row, int col, boolean visited[][]) {
        // These arrays are used to get row and column numbers of 8 neighbors of a given cell
        int rowNbr[] = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
        int colNbr[] = new int[]{-1, 0, 1, -1, 1, -1, 0, 1};
        // Mark this cell as visited
        visited[row][col] = true;
        // Recur for all connected neighbours
        for (int k = 0; k < 8; ++k)
            if (isSafe(M, row + rowNbr[k], col + colNbr[k], visited))
                DFS(M, row + rowNbr[k], col + colNbr[k], visited);
    }

    // The main function that returns count of islands in a given boolean 2D matrix
    int countIslands(int M[][]) {
        // Make a bool array to mark visited cells. Initially all cells are unvisited
        boolean visited[][] = new boolean[ROW][COL];
        // Initialize count as 0 and travese through the all cells of given matrix
        int count = 0;
        for (int i = 0; i < ROW; ++i)
            for (int j = 0; j < COL; ++j)
                if (M[i][j] == 1 && !visited[i][j]) // If a cell with
                {                                 // value 1 is not
                    // visited yet, then new island found, Visit all
                    // cells in this island and increment island count
                    DFS(M, i, j, visited);
                    ++count;
                }
        return count;
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
    2. If cache is full and its cache hit, remove the item from present location and add it to front of queue and make it head.
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
            map = new HashMap<Integer, DoublyNode>();
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
            if (map.containsKey(key)) {
                DoublyNode node = map.get(key);
                moveFirst(node);
                node.data = value;
                return;
            }
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
    class Map1 {
        HashMap<String, List<Integer>> _map = new HashMap<>();
        ArrayList<String> arr = new ArrayList<>();
        int length;
        int index;

        public void Insert(String key, int value) {
            index = length;
            List<Integer> temp = new ArrayList<>();
            temp.add(value);
            temp.add(index);
            _map.put(key, temp);
            arr.add(index, key);
        }

        public int Get(String key) {
            return _map.get(key).get(0);
        }

        public void Delete(String key) {
            index = _map.get(key).get(1);
            // swap array index elements with the last element, so delete can done in O(1)
            String temp = arr.get(index);
            arr.set(index, arr.get(length));
            arr.remove(length);
            length -= 1;
            //delete from the map
            _map.remove(key);
            //update the index of the swapped key
            _map.get(arr.get(index)).set(1, index);
        }

        public String GetRandomKey() {
            int r = (int) (Math.random() % length);
            return arr.get(r);
        }
    }

    //evalexpr(-4 - 3 * 2 / 2 + 4) -> result (float or double) without paranthesis
    //[Token(NUM, -4.), Token(SUB), Token(NUM, 3), Token(MUL)…]
    class Token {
        String type;
        double value;
        public Token(String _type, double _value) {
            type = _type;
            value = _value;
        }
        public Token(String _type) {
            type = _type;
        }
    }
    public static double evalExpr(List<Token> tokenList) {
        int i = 0;
        double left = tokenList.get(i++).value;
        while (i < tokenList.size()) {
            String operator = tokenList.get(i++).type;
            double right = Double.valueOf(tokenList.get(i++).value);
            switch (operator) {
                case "*":
                    left = left * right;
                    break;
                case "/":
                    left = left / right;
                    break;
                case "+":
                case "-":
                    while (i < tokenList.size()) {
                        String operator2 = tokenList.get(i++).type;
                        if (operator2.equals("+") || operator2.equals("-")) {
                            i--;
                            break;
                        }
                        if (operator2.equals("*")) {
                            right = right * Double.valueOf(tokenList.get(i++).value);
                        }
                        if (operator2.equals("/")) {
                            right = right / Double.valueOf(tokenList.get(i++).value);
                        }
                    }
                    if (operator.equals("+")) {
                        left = left + right;
                    } else {
                        left = left - right;
                    }
                    break;
            }
        }
        return left;
    }
}
