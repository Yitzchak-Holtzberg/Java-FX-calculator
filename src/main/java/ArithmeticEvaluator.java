import java.util.Stack;

/**
 * This class provides a method to evaluate arithmetic expressions represented as strings.
 */
public class ArithmeticEvaluator {
    private String expression;  // Holds the arithmetic expression to be evaluated.

    /**
     * Constructs an instance with the specified arithmetic expression.
     *
     * @param expression the arithmetic expression
     */
    public ArithmeticEvaluator(String expression) {
        this.expression = expression;
    }

    /**
     * Evaluates the arithmetic expression and returns the result.
     *
     * @return the result of the evaluation as a string, returns "0" if an error occurs
     */
    public String evaluate() {
        try {
            return evaluateExpression(expression);
        } catch (Exception ex) {
            System.out.println("Wrong expression: " + expression + " " + ex);
            return "0";
        }
    }

    /**
     * Helper method to evaluate the expression using stacks for operands and operators.
     *
     * @param expression the arithmetic expression to be evaluated
     * @return the result of the evaluation as a string
     */
    private String evaluateExpression(String expression) {
        Stack<Integer> operandStack = new Stack<>();  // Stack to store integers.
        Stack<Character> operatorStack = new Stack<>();  // Stack to store operators.

        expression = insertBlanks(expression);  // Add spaces before and after each operator and parenthesis.
        String[] tokens = expression.split(" ");  // Split the expression into tokens.

        for (String token : tokens) {
            if (token.isEmpty()) continue;
            else if (token.charAt(0) == '+' || token.charAt(0) == '-' ||
                    token.charAt(0) == '*' || token.charAt(0) == '/' ||
                    token.charAt(0) == '^') {
                // Process all operators in the stack that have higher or equal precedence.
                while (!operatorStack.isEmpty() && precedence(operatorStack.peek()) >= precedence(token.charAt(0))) {
                    processAnOperator(operandStack, operatorStack);
                }
                operatorStack.push(token.charAt(0));
            } else if (token.charAt(0) == '(') {
                operatorStack.push('(');
            } else if (token.charAt(0) == ')') {
                // Process all operators until the matching '(' is encountered.
                while (operatorStack.peek() != '(') {
                    processAnOperator(operandStack, operatorStack);
                }
                operatorStack.pop();  // Pop '('
            } else {
                operandStack.push(Integer.parseInt(token));  // Push operand to stack.
            }
        }

        // Clear out the operators stack now.
        while (!operatorStack.isEmpty()) {
            processAnOperator(operandStack, operatorStack);
        }
        return operandStack.pop().toString();
    }

    /**
     * Processes an operator by popping two operands and applying the operator.
     *
     * @param operandStack  the stack of operands
     * @param operatorStack the stack of operators
     */
    private void processAnOperator(Stack<Integer> operandStack, Stack<Character> operatorStack) {
        char op = operatorStack.pop();
        int op1 = operandStack.pop();
        int op2 = operandStack.pop();
        switch (op) {
            case '+':
                operandStack.push(op2 + op1);
                break;
            case '-':
                operandStack.push(op2 - op1);
                break;
            case '*':
                operandStack.push(op2 * op1);
                break;
            case '/':
                if (op1 == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                operandStack.push(op2 / op1);
                break;
            case '^':
                operandStack.push((int) Math.pow(op2, op1));
                break;
        }
    }

    /**
     * Determines the precedence of an operator.
     *
     * @param operator the operator to check
     * @return an integer value representing the precedence level
     */
    private int precedence(char operator) {
        if (operator == '+' || operator == '-') {
            return 1;
        } else if (operator == '*' || operator == '/') {
            return 2;
        } else if (operator == '^') {
            return 3;
        }
        return -1;
    }

    /**
     * Inserts spaces around operators and parentheses for easier tokenization.
     *
     * @param s the input string
     * @return the string with added spaces
     */
    private String insertBlanks(String s) {
        StringBuilder result = new StringBuilder();
        for (char c : s.toCharArray()) {
            if ("+-*/^()".indexOf(c) != -1) {
                result.append(" ").append(c).append(" ");
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
