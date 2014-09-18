package code.ds;
import java.util.*;
/**
 * Created by ppatel2 on 9/12/2014.
 */
public class MISC
{
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
        Stack operatorStack = new Stack();  // the stack of operators
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
                        !lowerPrecedence(((String)operatorStack.peek()).charAt(0), c))
                    // (Operator on the stack does not have lower precedence, so
                    //  it goes before this one.)
                    postfix.append(" ").append((String)operatorStack.pop());
                if (c==')')
                {
                    // Output the remaining operators in the parenthesized part.
                    String operator = (String)operatorStack.pop();
                    while (operator.charAt(0)!='(') {
                        postfix.append(" ").append(operator);
                        operator = (String)operatorStack.pop();
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
            postfix.append(" ").append((String)operatorStack.pop());
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
    followingMatrix[i][j] == true iff user i is following user j
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
}
