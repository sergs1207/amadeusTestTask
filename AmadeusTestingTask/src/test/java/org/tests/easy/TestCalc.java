package org.tests.easy;

import org.junit.*;
import org.junit.rules.Timeout;
import org.tests.easy.Calculator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeThat;

public class TestCalc {
    private Calculator calc;

    @BeforeClass
    public static void init(){
        System.out.println("Perform before test class");

    }

    @AfterClass
    public static void finish(){
        System.out.println("Perform after test class");
    }

    @Before
    public  void createObject(){
        calc = new Calculator();
    }

    @Test
    public void testAdd(){
        assertEquals("Add doesn't work", 10, calc.add(3, 7));
    }

    @Test
    public void testSubstract(){
        assertEquals("Substraction doesn't work", 6, calc.substract(10, 4));
    }

    @Test
    public void testMultiply(){
        assertEquals("Multiplication doesn't work", 15, calc.multiply(3, 5));
    }

    @Test
    public void testDivisionUsual(){
        assertEquals("Division doesn't work", 6, calc.divide(18, 3));
    }

    // Example of options if test throws an exception intentionally
    @Test(expected = ArithmeticException.class)
    public void testDivisionZero(){
        assertEquals("Can't divide by zero", 6, calc.divide(18, 0));
    }

    // Example of ignored test. So it's included to test report
//    @Ignore("The reason")
    @Test
    public void testDemoIgnore(){
        assertThat(1, is(1));
    }

    // Example of timeout for single test
    @Test(timeout = 1000)
    public void testDemoTimeout(){
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(1, is(1));
    }

    // Demonstration of assumption methods
    @Test
    public void testDemoAssume(){
        assumeThat(1, is(2));
    }
}
