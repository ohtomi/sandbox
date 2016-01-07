package com.example.foo;

import static org.junit.Assert.assertEquals;

import java.io.PrintStream;
import org.junit.Test;

public class HelloTest {

    @Test
    public void testPrint() throws Exception {
        String message = new Hello().print();
        assertEquals("Hello, world", message);
    }

}
