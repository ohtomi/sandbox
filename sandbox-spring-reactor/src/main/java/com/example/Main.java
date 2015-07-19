package com.example;

import java.util.stream.Stream;

import reactor.Environment;
import reactor.rx.broadcast.Broadcaster;

public class Main {

    private static final String[] NAMES;
    static {
        NAMES = new String[] { "Alice", "Bob", "Charles" };
    }

    private static void helloWorld() {
        System.out.println("\n--- helloWorld ---\n");

        Environment.initialize();
        Broadcaster<String> sink = Broadcaster.create(Environment.get());
        sink.dispatchOn(Environment.cachedDispatcher()).map(String::toUpperCase).consume(name -> {
            System.out.println("Hello " + name + "!");
        });

        Stream.of(NAMES).forEach(name -> sink.onNext(name));
        sink.onComplete();
        sink.join();
    }

    public static void main(String... args) {
        helloWorld();
    }

}
