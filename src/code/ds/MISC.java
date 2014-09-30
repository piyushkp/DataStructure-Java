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
        // Test if the given set has at least one interval
        if (intervals.size() <= 0) return null;
        // Create an empty stack of intervals
        Stack<Interval> s = new Stack<Interval>();
        // sort the intervals based on start time
        intervals.sort(new Comparator<Interval>() {
            @Override
            public int compare(Interval i1, Interval i2) {
                return (i1.start < i2.start) ? i1.start : i2.start;
            }
        });
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
    int getInfluencer(Array [][] M) {
        for(int i=0; i<M.length; i++) {
            boolean is_influencer = true;
            for(int j=0; j<M.length; j++) {
                if( i==j ) continue;
                if( M[i][j] || !M[j][i] ) {
                    is_influencer = false;
                    break;
                }
            }
            if( is_influencer )
                return i;
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
}
