package org.luoyunze;

public class App {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Usage: java -jar calculator-1.0.jar <number> <operator> <number>");
            System.exit(1);
        }
        Calculator calculator = new Calculator();
        try {
            double result = calculator.eval(args[0], args[1], args[2]);
            System.out.println(result);
        } catch (RuntimeException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }
}
