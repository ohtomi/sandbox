package com.example;

import reactor.Environment;
import reactor.rx.broadcast.Broadcaster;

public class Main {

	private static void helloWorld() throws InterruptedException {
		System.out.println("\n--- helloWorld ---\n");

		Environment.initialize();
		Broadcaster<String> sink = Broadcaster.create(Environment.get());
		sink.dispatchOn(Environment.cachedDispatcher()) //
				.map(String::toUpperCase) //
				.consume(System.out::println);
		sink.onNext("Hello World!");
	}

	public static void main(String... args) throws InterruptedException {
		helloWorld();
		Thread.sleep(500);
	}

}
