package org.luoyunze;

import org.apache.commons.lang3.StringUtils;

public class Calculator {
    public double eval(String a, String op, String b) {
        if (StringUtils.isBlank(a) || StringUtils.isBlank(op) || StringUtils.isBlank(b)) {
            throw new IllegalArgumentException("input is blank");
        }

        final double x;
        final double y;
        try {
            x = Double.parseDouble(a.trim());
            y = Double.parseDouble(b.trim());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("non-numeric input: " + a + ", " + b, ex);
        }

        return switch (op.trim()) {
            case "+" -> x + y;
            case "-" -> x - y;
            case "*" -> x * y;
            case "/" -> divide(x, y);
            case "^" -> Math.pow(x, y);
            case "%" -> modulo(x, y);
            default -> throw new IllegalArgumentException("unknown operator: " + op);
        };
    }

    private double divide(double x, double y) {
        if (y == 0.0) {
            throw new ArithmeticException("division by zero");
        }
        return x / y;
    }

    private double modulo(double x, double y) {
        if (y == 0.0) {
            throw new ArithmeticException("division by zero");
        }
        return x % y;
    }
}