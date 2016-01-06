package com.example;

import com.example.foo.Hello;

public class Main {

    public static void main(String... args) throws Exception {
        new Hello().print();
        Thread.sleep(100); // waiting for flushing logback stream
    }

}
