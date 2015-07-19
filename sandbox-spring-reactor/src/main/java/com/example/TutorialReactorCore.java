package com.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.reactivestreams.Processor;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import reactor.Environment;
import reactor.core.Dispatcher;
import reactor.core.DispatcherSupplier;
import reactor.core.dispatch.SynchronousDispatcher;
import reactor.core.dispatch.ThreadPoolExecutorDispatcher;
import reactor.core.processor.RingBufferProcessor;
import reactor.core.processor.RingBufferWorkProcessor;
import reactor.core.support.Assert;
import reactor.fn.BiConsumer;
import reactor.fn.Consumer;
import reactor.fn.Function;
import reactor.fn.Supplier;
import reactor.fn.timer.Timer;
import reactor.fn.tuple.Tuple;
import reactor.fn.tuple.Tuple2;
import reactor.rx.Stream;
import reactor.rx.Streams;

public class TutorialReactorCore {

    public static void main(String... args) throws Exception {
        core_first();
        core_second();

        functional_blocks();
        tuples();

        environment();
        dispatchers();
        dispatcher_supplier();
        timer();

        ring_buffer_processor();
        ring_buffer_work_processor();
    }

    private static void core_first() {
        Environment.initializeIfEmpty();
        Dispatcher dispatcher = Environment.sharedDispatcher();
        Consumer<Integer> consumer = data -> {
            System.out.println("some data arrived: " + data);
        };
        Consumer<Throwable> errorHandler = exception -> {
            exception.printStackTrace();
        };

        dispatcher.dispatch(123, consumer, errorHandler);
        dispatcher.dispatch(456, consumer, errorHandler);

        Environment.terminate();
    }

    private static void core_second() {
        RingBufferProcessor<Integer> processor = RingBufferProcessor.create();

        processor.onNext(12);
        processor.onNext(34);

        processor.subscribe(new Subscriber<Integer>() {

            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer t) {
                System.out.println(t);
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("done!");
            }

        });

        processor.onComplete();
    }

    private static void functional_blocks() {
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String value) {
                System.out.println(value);
            }
        };

        Function<Integer, String> transformation = integer -> "" + integer;

        Supplier<Integer> supplier = () -> 123;

        BiConsumer<Consumer<String>, String> biConsumer = (callback, value) -> {
            for (int i = 0; i < 10; i++) {
                callback.accept(value);
            }
        };

        biConsumer.accept(consumer, transformation.apply(supplier.get()));
    }

    private static void tuples() {
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String value) {
                System.out.println(value);
            }
        };

        Function<Integer, String> transformation = integer -> "" + integer;

        Supplier<Integer> supplier = () -> 456;

        Consumer<Tuple2<Consumer<String>, String>> biConsumer = tuple -> {
            for (int i = 0; i < 10; i++) {
                tuple.getT1().accept(tuple.getT2());
            }
        };

        biConsumer.accept(Tuple.of(consumer, transformation.apply(supplier.get())));
    }

    @SuppressWarnings("unused")
    private static void environment() {
        Environment env = Environment.initialize();
        Assert.isTrue(Environment.get() == env);
        try {
            Dispatcher dispatcher = Environment.dispatcher("dispatcher");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Timer timer = Environment.timer();
        Environment.terminate();
    }

    private static void dispatchers() throws InterruptedException {
        Dispatcher sameThread = new SynchronousDispatcher();
        Dispatcher diffThread = new ThreadPoolExecutorDispatcher(1, 128);
        Thread currentThread = Thread.currentThread();
        final List<Thread> taskThread = new ArrayList<>();

        Consumer<String> eventConsumer = event -> {
            taskThread.clear();
            taskThread.add(Thread.currentThread());
        };
        Consumer<Throwable> errorConsumer = error -> {
            error.printStackTrace();
        };
        sameThread.dispatch("test", eventConsumer, errorConsumer);
        Assert.isTrue(currentThread == taskThread.get(0));

        CountDownLatch latch = new CountDownLatch(1);
        diffThread.dispatch("test", event -> {
            eventConsumer.accept(event);
            latch.countDown();
        }, errorConsumer);
        latch.await(5, TimeUnit.SECONDS);
        Assert.isTrue(currentThread != taskThread.get(0));
    }

    private static void dispatcher_supplier() {
        Environment.initialize();

        DispatcherSupplier supplier = Environment.newCachedDispatchers(2);
        Dispatcher d1 = supplier.get();
        Dispatcher d2 = supplier.get();
        Dispatcher d3 = supplier.get();
        Dispatcher d4 = supplier.get();
        Assert.isTrue(d1 == d3);
        Assert.isTrue(d2 == d4);
        supplier.shutdown();

        DispatcherSupplier s1 = Environment.newCachedDispatchers(3);
        Environment.cachedDispatchers("my_pool", s1);
        DispatcherSupplier s2 = Environment.cachedDispatchers("my_pool");
        Assert.isTrue(s1 == s2);
        s1.shutdown();

        Environment.terminate();
    }

    private static void timer() throws InterruptedException {
        Environment.initializeIfEmpty();
        Timer timer = Environment.timer();
        CountDownLatch latch = new CountDownLatch(10);

        timer.schedule(data -> {
            latch.countDown();
            System.out.println(LocalDateTime.now());
        }, timer.getResolution() * 2, TimeUnit.MILLISECONDS);
        // timer.getResouletion() = 50;

        latch.await(2, TimeUnit.SECONDS);
        timer.cancel();
        Environment.terminate();
    }

    private static void ring_buffer_processor() {
        Processor<Integer, Integer> processor = RingBufferProcessor.create("test", 32);
        Stream<Integer> stream = Streams.wrap(processor);

        stream.consume(data -> {
            System.out.println(Thread.currentThread() + " data=" + data);
        });
        stream.consume(data -> {
            System.out.println(Thread.currentThread() + " data=" + data);
        });
        stream.consume(data -> {
            System.out.println(Thread.currentThread() + " data=" + data);
        });

        processor.onNext(123);
        processor.onNext(456);
        processor.onNext(789);

        processor.onComplete();
    }

    private static void ring_buffer_work_processor() {
        Processor<Integer, Integer> processor = RingBufferWorkProcessor.create("test", 32);
        Stream<Integer> stream = Streams.wrap(processor);

        stream.consume(data -> {
            System.out.println(Thread.currentThread() + " data=" + data);
        });
        stream.consume(data -> {
            System.out.println(Thread.currentThread() + " data=" + data);
        });
        stream.consume(data -> {
            System.out.println(Thread.currentThread() + " data=" + data);
        });

        processor.onNext(12);
        processor.onNext(34);
        processor.onNext(56);
        processor.onNext(78);
        processor.onNext(90);

        processor.onComplete();
    }

}
