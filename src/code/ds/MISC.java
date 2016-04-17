package code.ds;
import java.util.*;
/**
 * Created by ppatel2 on 9/12/2014.
 */
public class MISC
{
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
            }
            else if (top.end < intervals.get(i).end) {
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
        for (int i=0; i<intervals.size(); i++){
            // If this is not first Interval and overlaps with the previous one
            if (index != 0 && intervals.get(index-1).start <= intervals.get(i).end){
                while (index != 0 && intervals.get(index-1).start <= intervals.get(i).end){
                    // Merge previous and current Intervals
                    intervals.get(index-1).end = Math.max(intervals.get(index - 1).end, intervals.get(i).end);
                    intervals.get(index-1).start = Math.min(intervals.get(index - 1).start, intervals.get(i).start);
                    index--;
                }
            }
            else // Doesn't overlap with previous, add to solution
                intervals.set(index, intervals.get(i));

            index++;
        }
        return intervals;
    }
    //Without extra Space
    void mergeIntervals(Interval arr[], int n){
        // Sort Intervals in decreasing order of
        // start time
        Arrays.sort(arr);
        int index = 0; // Stores index of last element
        // in output array (modified arr[])
        // Traverse all input Intervals
        for (int i=0; i<n; i++){
            // If this is not first Interval and overlaps with the previous one
            if (index != 0 && arr[index-1].start <= arr[i].end)
            {
                while (index != 0 && arr[index-1].start <= arr[i].end)
                {
                    // Merge previous and current Intervals
                    arr[index-1].end = Math.max(arr[index-1].end, arr[i].end);
                    arr[index-1].start = Math.min(arr[index-1].start, arr[i].start);
                    index--;
                }
            }
            else // Doesn't overlap with previous, add to
                // solution
                arr[index] = arr[i];
            index++;
        }
        // Now arr[0..index-1] stores the merged Intervals
        for (int i = 0; i < index; i++)
            System.out.print(arr[i].start+ ", " + arr[i].end);
    }


    /*Given a list of tuples representing intervals, return the range these UNIQUE intervals
    covered. e.g: [(1,3),(2,5),(8,9)] should return 5
    a) 1 2 3 = 2 unique intervals (1 to 2, 2 to 3)
    b) 2 3 4 5 = 2 unique intervals ( 3 to 4, 4 to 5) We did not count 2 - 3 since it was already counted.
    c) 8 9 = 1 unique interval
    result = 2 + 2 + 1 = 5 */
    private int getCoverageOfIntervals(ArrayList<Interval> intervals)
    {
        int range = 0;
        ArrayList<Interval> mergeIntervals = mergeIntervals(intervals);
        for (Interval _interval : mergeIntervals)
        {
            range += (_interval.end - _interval.start);
        }
        return  range;
    }
    // Method to convert infix to postfix:
    private boolean isOperator(char c) {
        return c == '+'  ||  c == '-'  ||  c == '*'  ||  c == '/'  ||  c == '^'
                || c=='(' || c==')';
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
                return !(op2=='+' || op2=='-') ;
            case '*':
            case '/':
                return op2=='^' || op2=='(';
            case '^':
                return op2=='(';
            case '(': return true;
            default:  // (shouldn't happen)
                return false;
        }
    }
    // Method to convert infix to postfix:
    public String infixToPostfix(String infix) {
        // Return a postfix representation of the expression in infix.
        Stack<String> operatorStack = new Stack<String>();  // the stack of operators
        char c;       // the first character of a token
        StringTokenizer parser = new StringTokenizer(infix,"+-*/^() ",true);
        // StringTokenizer for the input string
        StringBuffer postfix = new StringBuffer(infix.length());  // result
        // Process the tokens.
        while (parser.hasMoreTokens()) {
            String token = parser.nextToken();          // get the next token and let c be
            c = token.charAt(0);         // the first character of this token
            if ( (token.length() == 1) && isOperator(c) )
            {    // if token is an operator
                while (!operatorStack.empty() &&
                        !lowerPrecedence((operatorStack.peek()).charAt(0), c))
                    // (Operator on the stack does not have lower precedence, so
                    //  it goes before this one.)
                    postfix.append(" ").append(operatorStack.pop());
                if (c==')')
                {
                    // Output the remaining operators in the parenthesized part.
                    String operator = operatorStack.pop();
                    while (operator.charAt(0)!='(') {
                        postfix.append(" ").append(operator);
                        operator = operatorStack.pop();
                    }
                }
                else
                    operatorStack.push(token);// Push this operator onto the stack.
            }
            else if ( (token.length() == 1) && isSpace(c) ) {    // else if
                // token was a space
                ;                                                  // ignore it
            }
            else {  // (it is an operand)
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
    public int evaluate (String expr)
    {
        Stack<Integer> stack = new Stack<Integer>();
        int op1, op2, result = 0;
        String token;
        StringTokenizer tokenizer = new StringTokenizer (expr);
        while (tokenizer.hasMoreTokens())
        {
            token = tokenizer.nextToken();
            if (isOperator(token.charAt(0)))
            {
                op2 = (stack.pop()).intValue();
                op1 = (stack.pop()).intValue();
                result = evalSingleOp (token.charAt(0), op1, op2);
                stack.push (new Integer(result));
            }
            else
                stack.push (new Integer(Integer.parseInt(token)));
        }
        return result;
    }
    private int evalSingleOp (char operation, int op1, int op2)
    {
        int result = 0;
        switch (operation)
        {
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
    private int getInfluencer(Boolean [][] M)
    {
        for(int influencer =0; influencer <M.length; influencer ++)
        {
            boolean is_influencer = true;
            for(int followedBy=0; followedBy<M.length; followedBy++)
            {
                if( influencer == followedBy ) continue; //the same user, check the next user
                if( M[influencer][followedBy] || !M[followedBy][influencer] )
                {
                    is_influencer = false;
                    break;
                }
            }
            if( is_influencer )
                return influencer;
        }
        return -1;
    }
    public class Singleton {
        private static Singleton uniqInstance;
        private Singleton() {
        }
        public static synchronized Singleton getInstance() {
            if (uniqInstance == null) {
                uniqInstance = new Singleton();
            }
            return uniqInstance;
        }
    }
    //implement Java Iterable interface to read a file.
    class Line<T>{
        int LineNumber;
        byte[] LineData;
    }
    class FileReaderIterable<E> implements Iterable<E>{
        byte[] Data;
        FileReaderIterable(byte[] data)
        {this.Data = data;}
        public Iterator<E> iterator() {
            return new FileReaderIterator<E>(Data);
        }
    }
    class FileReaderIterator<T> implements Iterator<T> {
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
            if (this.buffer.isEmpty()){
                for (byte item : this.data) {
                    this.buffer.add(item);
                }
            }
        }
        public void remove() {
            throw new UnsupportedOperationException("It is read-only");
        }
    }
    //Check if a given sequence of moves for a robot is circular or not
    // G - Go one unit     L - Turn left     R - Turn right
    //Input = "GLGLGLG" output = yes
    // This function returns true if the given path is circular, else false
    boolean isCircular(char path[]){
        int N = 0, E = 1, S = 2, W = 3;
        // Initialize starting point for robot as (0, 0) and starting direction as N North
        int x = 0, y = 0;
        int dir = N;
        // Travers the path given for robot
        for (int i=0; i< path.length; i++){
            // Find current move
            char move = path[i];
            // If move is left or right, then change direction
            if (move == 'R')
                dir = (dir + 1)% 4;
            else if (move == 'L')
                dir = (4 + dir - 1)% 4;
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
    public int calculate(int eggs, int floors){
        int T[][] = new int[eggs+1][floors+1];
        int c =0;
        for(int i=0; i <= floors; i++){
            T[1][i] = i;
        }
        for(int e = 2; e <= eggs; e++){
            for(int f = 1; f <=floors; f++){
                T[e][f] = Integer.MAX_VALUE;
                for(int k = 1; k <=f ; k++){
                    c = 1 + Math.max(T[e-1][k-1], T[e][f-k]);
                    if(c < T[e][f]){
                        T[e][f] = c;
                    }
                }
            }
        }
        return T[eggs][floors];
    }
    //Given many points on a coordinate plane, find the pair of points that is the closest among all pairs of points.
    class Point{
        int x;
        int y;
        Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    private int closestPairOfPoints(Point[] px, Point[] py,int start, int end){
        if(end - start < 3){
            //brute force
            //return computeMinDistance(px, start, end);
        }
        int mid = (start + end)/2;
        Point[] pyLeft = new Point[mid-start+1];
        Point[] pyRight = new Point[end-mid];
        int i=0, j=0;
        for(Point p : px){
            if(p.x <= px[mid].x){
                pyLeft[i++] = p;
            }else{
                pyRight[j++] = p;
            }
        }
        int d1 = closestPairOfPoints(px,pyLeft,start,mid);
        int d2 = closestPairOfPoints(px, pyRight, mid+1, end);
        int d = Math.min(d1, d2);

        List<Point> deltaPoints = new ArrayList<Point>();
        for(Point p : px){
            if(Math.sqrt(distance(p,px[mid])) < Math.sqrt(d)){
                deltaPoints.add(p);
            }
        }
        int d3 = closest(deltaPoints);
        return Math.min(d3, d);
    }

    private int closest(List<Point> deltaPoints){
        int minDistance = Integer.MAX_VALUE;
        for(int i=0; i < deltaPoints.size(); i++){
            for(int j=i+1; j <= i + 7 && j < deltaPoints.size() ; j++){
                int distance = distance(deltaPoints.get(i), deltaPoints.get(j));
                if(minDistance < distance){
                    minDistance = distance;
                }
            }
        }
        return minDistance;
    }

    private int distance(Point p1, Point p2){
        return (p1.x - p2.x)*(p1.x - p2.x) + (p1.y - p2.y)*(p1.y - p2.y);
    }
}
