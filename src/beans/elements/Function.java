package beans.elements;

import abstractions.AbstractElement;
import interfaces.functional_interfaces.FI_Function_count;

import java.util.Arrays;
import java.util.List;

import static support.constants.Constants.*;

public class Function extends AbstractElement {
    public enum Content {
        SIN("sin", n -> Math.sin(Math.toRadians(n))),
        COS("cos", n -> Math.cos(Math.toRadians(n))),
        TAN("tan", n -> Math.tan(Math.toRadians(n))),
        SQRT("sqrt", Math::sqrt),
        UNARY_MINUS("um", n -> n = -n),

        DEFAULT();

        private String value;
        private FI_Function_count<Double> function;

        Content() {
            value = "";
        }

        Content(String value, FI_Function_count<Double> function) {
            this.value = value;
            this.function = function;
        }

        public Double count(Double n) {
            return function.count(n);
        }

        public String getValue() {
            return value;
        }
    }

    @Override
    public Content getElement(String s) {
        return Arrays.stream(Content.values())
                     .filter(Content -> Content.value != null && Content.value.equals(s))
                     .findFirst()
                     .orElse(Content.DEFAULT);
    }

    @Override
    public boolean found(String s) {
        return getElement(s).value.equals(s);
    }

    public void setUnaryMinus(List<String> list, int i) {
        if (list != null) {
            String current = list.get(i);
            if (current.equals(MINUS))
                list.set(i, isUnaryMinus(list, i) ? UNARY_MINUS : current);
        }
    }

    private boolean isUnaryMinus(List<String> list, int i) {
        return (list.size() != 1 && i == 0 && OPERAND.found(list.get(i + 1)))
                || (i != list.size() - 1 && i != 0 && list.get(i - 1).equals(LEFT_BRACKET) && OPERAND.found(list.get(i + 1)))
                || (i != list.size() - 1 && i != 0 && (OPERATOR.found(list.get(i - 1)) )) && !list.get(i - 1).equals(RIGHT_BRACKET)
                && (OPERAND.found(list.get(i + 1)) || list.get(i + 1).equals(LEFT_BRACKET));
    }
}
