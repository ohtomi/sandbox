package com.example;

import rx.Observable;

public class Main {

	private static void helloWorld(String... names) {
		Observable.from(names).subscribe((name) -> {
			System.out.println("Hello " + name + "!");
		});
	}

	public static void main(String... args) {
		helloWorld(new String[] { "Alice", "Bob", "Charles", });
	}

}
