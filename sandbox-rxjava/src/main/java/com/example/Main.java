package com.example;

import rx.Observable;

public class Main {

    private static final String[] NAMES;
    static {
        NAMES = new String[] { "Alice", "Bob", "Charles" };
    }

    private static void helloWorld() {
        System.out.println("\n--- helloWorld ---\n");

        Observable.from(NAMES).map(String::toUpperCase).subscribe(name -> {
            System.out.println("Hello " + name + "!");
        });
    }

    public static void main(String... args) {
        helloWorld();
    }

}
