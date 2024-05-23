import java.util.Stack;

public class Evaluate {
    private String expression;  // No longer static

    public Evaluate(String expression) {
        this.expression = expression;
    }

    public String eval() {  // Now public and non-static
        try {
            return evaluateExpression(expression);
        } catch (Exception ex) {
            System.out.println("Wrong expression: " + expression + ex);
        }
        return "0";
    }

    private String evaluateExpression(String expression) {
        Stack<Integer> operandStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();

        expression = insertBlanks(expression);
        String[] tokens = expression.split(" ");

        for (String token : tokens) {
            if (token.isEmpty()) continue;
            else if (token.charAt(0) == '+' || token.charAt(0) == '-' ||
                    token.charAt(0) == '*' || token.charAt(0) == '/' ||
                    token.charAt(0) == '^') {
                while (!operatorStack.isEmpty() && precedence(operatorStack.peek()) >= precedence(token.charAt(0))) {
                    processAnOperator(operandStack, operatorStack);
                }
                operatorStack.push(token.charAt(0));
            } else if (token.charAt(0) == '(') {
                operatorStack.push('(');
            } else if (token.charAt(0) == ')') {
                while (operatorStack.peek() != '(') {
                    processAnOperator(operandStack, operatorStack);
                }
                operatorStack.pop();
            } else {
                operandStack.push(Integer.parseInt(token));
            }
        }

        while (!operatorStack.isEmpty()) {
            processAnOperator(operandStack, operatorStack);
        }
        return operandStack.pop().toString();
    }

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
                operandStack.push(op2 / op1);
                break;
            case '^':
                operandStack.push((int) Math.pow(op2, op1));  // Ensure correct operand order
                break;
        }
    }

    private int precedence(char operator) {
        if (operator == '+' || operator == '-') {
            return 1;
        } else if (operator == '*' || operator == '/' || operator == '^') {
            return 2;
        }
        return -1;
    }

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
