package services;

import beans.Operator;

import java.util.ArrayDeque;
import java.util.List;

import static support.constants.Constants.*;

public class ReversePolishNotationBuilder {

    public String toRPN(String s) {
        return String.valueOf(asList(s));
    }

    public List asList(String s) {
        return ELEMENT.asElementsList(toRPN(s));
    }

    public String toRPN(List<String> s) {
        ArrayDeque<String> operators = new ArrayDeque<>();
        StringBuilder result = new StringBuilder();
        String current;

        for (String value : s) {
            current = value;
            if (OPERAND.found(current)) {
                result.append(current).append(SPACE);
            } else if (OPERATOR.found(current)) {
                replaceOperator(current, operators, result);
            }
        }
        while (!operators.isEmpty()) {
            result.append(operators.pop()).append(operators.isEmpty() ? EMPTY : SPACE);
        }
        return result.toString();
    }

    private void replaceOperator(String current, ArrayDeque<String> operators,
                                 StringBuilder result) {
        if (OPERATOR.isBracket(current)) {
            replaceBrackets(current, operators, result);
        } else {
            if (!operators.isEmpty()) {
                while (Operator.comparePriority(current, operators.peek()) < 1) {
                    result.append(operators.pop()).append(SPACE);
                }
            }
            operators.push(current);
        }
    }

    private void replaceBrackets(String current, ArrayDeque<String> operators,
                                 StringBuilder result) {
        String operator;
        if (current.equals(LEFT_BRACKET)) {
            operators.push(current);
        } else if (current.equals(RIGHT_BRACKET)) {
            operator = operators.pop();
            while (!operator.equals(LEFT_BRACKET)) {
                result.append(operator).append(SPACE);
                operator = operators.pop();
            }
        }
    }
}
