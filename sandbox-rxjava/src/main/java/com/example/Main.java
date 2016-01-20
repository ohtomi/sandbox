package com.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.subjects.AsyncSubject;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;

public class Main {

    private static void fromList() {
        Observable<String> observable = Observable.from(new String[] { "Alice", "Bob", "Charles" });
        observable.subscribe(s -> System.out.println("fromList: " + s), err -> err.printStackTrace());
    }

    private static void publishSubject() {
        PublishSubject<String> subject = PublishSubject.create();
        subject.subscribe(s -> System.out.println("publishSubject 1: " + s), err -> err.printStackTrace());

        CompletableFuture.runAsync(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    TimeUnit.MILLISECONDS.sleep(30);
                } catch (Exception ignore) {
                }
                subject.onNext(String.format("message(%d) from future", i));
            }
            subject.onCompleted();
        });

        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (Exception ignore) {
        }
        subject.subscribe(s -> System.out.println("publishSubject 2: " + s), err -> err.printStackTrace());
    }

    private static void asyncSubject() {
        AsyncSubject<String> subject = AsyncSubject.create();
        subject.subscribe(s -> System.out.println("asyncSubject 1: " + s), err -> err.printStackTrace());

        CompletableFuture.runAsync(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    TimeUnit.MILLISECONDS.sleep(30);
                } catch (Exception ignore) {
                }
                subject.onNext(String.format("message(%d) from future", i));
            }
            subject.onCompleted();
        });

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (Exception ignore) {
        }
        subject.subscribe(s -> System.out.println("asyncSubject 2: " + s), err -> err.printStackTrace());
    }

    private static void replaySubject() {
        ReplaySubject<String> subject = ReplaySubject.create();
        subject.subscribe(s -> System.out.println("replaySubject 1: " + s), err -> err.printStackTrace());

        CompletableFuture.runAsync(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    TimeUnit.MILLISECONDS.sleep(30);
                } catch (Exception ignore) {
                }
                subject.onNext(String.format("message(%d) from future", i));
            }
            subject.onCompleted();
        });

        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (Exception ignore) {
        }
        subject.subscribe(s -> System.out.println("replaySubject 2: " + s), err -> err.printStackTrace());
    }

    private static void behaviorSubject() {
        BehaviorSubject<String> subject = BehaviorSubject.create();
        Observable.combineLatest(subject.take(1), subject.skip(1), (a, b) -> {
            return String.format("%s -> %s", a, b);
        }).subscribe(s -> System.out.println("behaviorSubject: " + s));

        CompletableFuture.runAsync(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    TimeUnit.MILLISECONDS.sleep(30);
                } catch (Exception ignore) {
                }
                subject.onNext(String.format("message(%d) from future", i));
            }
            subject.onCompleted();
        });
    }

    public static void main(String... args) throws Exception {
        fromList();
        join();
        publishSubject();
        join();
        asyncSubject();
        join();
        replaySubject();
        join();
        behaviorSubject();
        join();
    }

    private static void join() throws Exception {
        TimeUnit.SECONDS.sleep(1);
        System.out.println();
    }

}
