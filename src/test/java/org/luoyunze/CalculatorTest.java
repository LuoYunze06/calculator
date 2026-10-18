package org.luoyunze;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorTest {
    private final Calculator calculator = new Calculator();

    @Test
    void addWorks() {
        assertEquals(4.0, calculator.eval("2", "+", "2"));
    }

    @Test
    void subtractWorks() {
        assertEquals(3.0, calculator.eval("5", "-", "2"));
    }

    @Test
    void multiplyWorks() {
        assertEquals(81.0, calculator.eval("9", "*", "9"));
    }

    @Test
    void divideWorks() {
        assertEquals(2.0, calculator.eval("10", "/", "5"));
    }

    @Test
    void powerWorks() {
        assertEquals(8.0, calculator.eval("2", "^", "3"));
    }

    @Test
    void moduloWorks() {
        assertEquals(1.0, calculator.eval("10", "%", "3"));
    }

    @Test
    void addZeroBoundary() {
        assertEquals(5.0, calculator.eval("5", "+", "0"));
    }

    @Test
    void negativeNumbersWork() {
        assertEquals(-2.0, calculator.eval("-5", "+", "3"));
    }

    @Test
    void blankInputThrows() {
        assertThrows(IllegalArgumentException.class, () -> calculator.eval("", "+", "2"));
    }

    @Test
    void unknownOperatorThrows() {
        assertThrows(IllegalArgumentException.class, () -> calculator.eval("2", "?", "2"));
    }

    @Test
    void nonNumericInputThrows() {
        assertThrows(IllegalArgumentException.class, () -> calculator.eval("a", "+", "2"));
    }

    @Test
    void divisionByZeroThrows() {
        assertThrows(ArithmeticException.class, () -> calculator.eval("10", "/", "0"));
    }

    @Test
    void moduloByZeroThrows() {
        assertThrows(ArithmeticException.class, () -> calculator.eval("10", "%", "0"));
    }
}
