package com.example;

import static java.util.Comparator.nullsLast;
import static java.util.Comparator.reverseOrder;

import java.util.stream.Stream;

import rx.Observable;

public class Main {

	private static void helloWorld(String... names) {
		System.out.println("\n--- helloWorld ---\n");

		Observable.from(names).subscribe(name -> {
			System.out.println("Hello " + name + "!");
		});
	}

	private static void syncObservable() {
		System.out.println("\n--- syncObservable ---\n");

		Observable<String> observable = Observable.create(subscriber -> {
			Stream.of("Alice", "Bob", null, "Charles")
					.sorted(nullsLast(reverseOrder())).forEach(name -> {
						if (!subscriber.isUnsubscribed()) {
							subscriber.onNext("Hello " + name + "!");
						}
					});
			if (!subscriber.isUnsubscribed()) {
				subscriber.onCompleted();
			}
		});

		observable.subscribe(System.out::println);
	}

	public static void main(String... args) {
		helloWorld(new String[] { "Alice", "Bob", "Charles", });
		syncObservable();
	}

}
