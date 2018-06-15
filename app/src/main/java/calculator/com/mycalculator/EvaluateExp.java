package calculator.com.mycalculator;

import java.util.Stack;

public class EvaluateExp {
    static double calc(char ch, double a, double b) {
        switch (ch) {
            case '+':
                return a + b;
            case '-':
                return b - a;
            case '*':
                return a * b;
            case '/':
                return b / a;
        }
        return 0;
    }

    static boolean precendence(char a, char b) {
        if (a == '(' || a == ')')
            return false;
        else if ((a == '+' || a == '-') && (b == '*' || b == '/'))
            return false;
        else
            return true;
    }

    static double evaluateMathExp(String str) {

        Stack<Double> value = new Stack<Double>();
        Stack<Character> op = new Stack<Character>();

        for (int i = 0; i < str.length(); i++) {
            String dbl = "";
            int j = i;

            if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
                double num = 0;
                while (	(i < str.length()) &&
                        (str.charAt(i) >= '0' && str.charAt(i) <= '9')
                        && (str.charAt(i) != '.')
                        )
                {
                    num = num * 10 + (str.charAt(i) - 48);
                    i++;
                }

                if ((i < str.length()) && (str.charAt(i) == '.')) {
                    i = j;
                    while (	(i < str.length()) &&
                            (
                                    (str.charAt(i) >= '0' && str.charAt(i) <= '9') ||
                                            (str.charAt(i) == '.')
                            )
                            )
                    {
                        dbl += str.charAt(i++);
                    }
                    num = Double.parseDouble(dbl);
                }

                i--;
                value.push(num);
            } else if (str.charAt(i) == '(')
                op.push(str.charAt(i));
            else if (str.charAt(i) == '+' || str.charAt(i) == '-' || str.charAt(i) == '*' || str.charAt(i) == '/') {
                while (!op.empty() && precendence(op.peek(), str.charAt(i))) {

                    double x = value.pop();
                    double y = value.pop();
                    value.push(calc(op.peek(), x, y));
                    op.pop();
                }
                op.push(str.charAt(i));
            } else if (str.charAt(i) == ')') {
                while (!op.empty() && op.peek() != '(') {
                    double x = value.pop();
                    double y = value.pop();
                    value.push(calc(op.peek(), x, y));
                    op.pop();
                }

                if (!op.empty())
                    op.pop();
            }
        }

        while (!op.empty()) {
            double x = value.pop();
            double y = value.pop();
            value.push(calc(op.pop(), x, y));
        }
        return value.peek();

    }
}
