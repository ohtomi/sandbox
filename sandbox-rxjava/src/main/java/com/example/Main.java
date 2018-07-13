package com.example;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.SingleScheduler;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.Subject;

public class Main {

    private static Observable<Integer> createCounter() {
        return Observable.range(1, 10);
    }

    private void sample1() throws Exception {
        // 時間tを流れるintegerのstream
        Observable<Integer> counter = Main.createCounter();
        // 観測する
        Disposable d = counter.subscribe(i -> {
            System.out.println("sample1() => i: " + i);
        });

        Thread.sleep(100);
        d.dispose();
    }

    private void sample2() throws Exception {
        // 時間tを流れるintegerのstream
        Observable<Integer> counter = Main.createCounter();
        // 関数を適用する
        Observable<String> countLog = counter.map(i -> "[" + i + "]");
        String log = countLog
                .reduce((a, b) -> a + ", " + b)
                .toSingle()
                .blockingGet();
        System.out.println("sample2() => " + log);
    }

    private static Observable<Integer> fromList(Integer... array) {
        return Observable.fromArray(array);
    }

    private void sample3() throws Exception {
        // Schedulerをセットする
        Scheduler scheduler1 = new SingleScheduler();
        Scheduler scheduler2 = new SingleScheduler();
        Observable<Integer> counter = Main.fromList(1, 3, 5, 7, 9);
        Disposable d = counter.subscribeOn(scheduler1)
                .observeOn(scheduler2)
                .subscribe(i -> {
                    System.out.println("sample3() => i: " + i);
                    scheduler1.shutdown();
                    scheduler2.shutdown();
                });

        scheduler1.start();
        scheduler2.start();

        Thread.sleep(100);
        d.dispose();
    }

    private void sample4() throws Exception {
        // エラーハンドリングする
        Observable<Integer> counter = Main.fromList(1, 3, 5, 7, 9);
        Disposable d = counter.subscribe(i -> {
            System.out.println("sample4() i: " + i);
        }, th -> {
            System.out.println("sample4() th: " + th.getMessage());
            th.printStackTrace();
        }, () -> {
            System.out.println("sample4() done");
        });

        Thread.sleep(100);
        d.dispose();
    }

    private void sample5() throws Exception {
        // Promise化でさよならAsyncTask
        Scheduler scheduler = new SingleScheduler();
        Observable<Integer> counter = Observable.just(13579);
        Disposable d = counter.subscribeOn(scheduler)
                .subscribe(i -> {
                    System.out.println("sample5() i: " + i);
                }, th -> {
                    System.out.println("sample5() th: " + th.getMessage());
                    th.printStackTrace();
                }, () -> {
                    scheduler.shutdown();
                    System.out.println("sample5() done");
                });

        scheduler.start();

        Thread.sleep(100);
        d.dispose();
    }

    private void sample6() throws Exception {
        // mergeを使った並列リクエスト
        Observable<Object> counter = Observable.merge(
                Observable.just(13579),
                Observable.just("97531"));
        Disposable d = counter.subscribe(o -> {
            System.out.println("sample6() o: " + o.getClass().getCanonicalName());
        }, th -> {
            System.out.println("sample6() th: " + th.getMessage());
            th.printStackTrace();
        }, () -> {
            System.out.println("sample6() done");
        });

        Thread.sleep(100);
        d.dispose();
    }

    private void sample7() throws Exception {
        // Subject
        Observable<Integer> counter = Main.fromList(1, 3, 5, 7, 9);
        Subject<Integer> replay = ReplaySubject.createWithSize(3);
        Disposable d1 = counter.subscribe(i -> {
            replay.onNext(i * i);
        }, th -> {
            replay.onError(th);
            th.printStackTrace();
        }, () -> {
            System.out.println("sample7() eos");
            replay.onComplete();
        });
        Disposable d2 = replay.subscribe(i -> {
            System.out.println("sample7() i: " + i);
        }, th -> {
            System.out.println("sample7() th: " + th.getMessage());
            th.printStackTrace();
        }, () -> {
            System.out.println("sample7() done");
        });

        Thread.sleep(100);
        d1.dispose();
        d2.dispose();
    }

    public static void main(String... args) throws Exception {
        Main app = new Main();
        app.sample1();
        app.sample2();
        app.sample3();
        app.sample4();
        app.sample5();
        app.sample6();
        app.sample7();
    }
}
