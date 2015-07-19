package com.example;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import reactor.Environment;
import reactor.core.DispatcherSupplier;
import reactor.fn.tuple.Tuple;
import reactor.rx.BiStreams;
import reactor.rx.Promise;
import reactor.rx.Stream;
import reactor.rx.Streams;
import reactor.rx.action.Control;
import reactor.rx.broadcast.Broadcaster;

public class TutorialReactorStream {

    public static void main(String... args) throws Exception {
        stream_first();

        cold_data_source();
        create_and_defer();
        create_with();

        hot_data_source();
        wiring_up_stream();
        publish_subscribe();

        filters_and_transformations();

        partitioning();
    }

    private static void stream_first() {
        Environment.initialize();

        //@formatter:off
        Streams.from(new String[] { "Alice is a girl", "Bob is a boy", "Charles is a boy" })
                .dispatchOn(Environment.sharedDispatcher())
                .flatMap(sentence -> {
                    return Streams.from(sentence.split(" "));
//                    return Streams.from(sentence.split(" "))
//                            .dispatchOn(Environment.cachedDispatcher())
//                            .filter(word -> {
//                                return !word.trim().isEmpty();
//                            })
//                            .observe(word -> {
//                                System.out.println("<observe>" + word);
//                            });
                })
                .map(word -> Tuple.of(word, 1))
                .window(1, TimeUnit.SECONDS)
                .flatMap(words -> {
                    return BiStreams.reduceByKey(words, (prev, next) -> {
                        return prev + next;
                    })
                    .sort((wordWithCountA, wordWithCountB) -> {
                        return -1 * wordWithCountA.t2.compareTo(wordWithCountB.t2);
                    })
                    .take(10)
                    .finallyDo(event -> {
                        System.out.println("--- window complete! ---");
                    });
                }).consume(wordWithCount -> {
                    System.out.println(wordWithCount.t1 + ": " + wordWithCount.t2);
                }, error -> {
                    error.printStackTrace();
                });
        //@formatter:on

        Environment.terminate();
    }

    private static void cold_data_source() {
        Environment.initialize();

        Stream<String> stream = Streams.just("Hello", "World", "!");
        stream.dispatchOn(Environment.cachedDispatcher()).map(String::toUpperCase)
                .consume(word -> System.out.printf("%s greeting = %s%n", Thread.currentThread(), word));

        Environment.terminate();
    }

    private static void create_and_defer() {
        Environment.initialize();

        final Stream<String> stream1 = Streams.create(new Publisher<String>() {

            @Override
            public void subscribe(Subscriber<? super String> subscriber) {
                Subscription subscription = new Subscription() {

                    @Override
                    public void request(long demand) {
                        if (demand == 2L) {
                            subscriber.onNext("1");
                            subscriber.onNext("2");
                            subscriber.onComplete();
                        }
                    }

                    @Override
                    public void cancel() {
                        System.out.println("Canceled!!");
                    }

                };
                subscription.request(2);
                subscriber.onSubscribe(subscription);
            }

        });

        final Stream<String> stream2 = Streams.create(subscriber -> {
            subscriber.onNext("3");
            subscriber.onNext("4");
            subscriber.onComplete();
        });

        final AtomicInteger counterSubscriber = new AtomicInteger();

        Stream<String> deferred = Streams.defer(() -> {
            if (counterSubscriber.incrementAndGet() % 2 == 1) {
                return stream1;
            } else {
                return stream2;
            }
        });

        deferred.consume(data -> System.out.printf("%s First subscription = %s%n", Thread.currentThread(), data));
        deferred.consume(data -> System.out.printf("%s Second subscription = %s%n", Thread.currentThread(), data));
        deferred.consume(data -> System.out.printf("%s Third subscription = %s%n", Thread.currentThread(), data));
        deferred.consume(data -> System.out.printf("%s Fourth subscription = %s%n", Thread.currentThread(), data));

        Environment.terminate();
    }

    private static void create_with() {
        Environment.initialize();

        final Stream<String> stream = Streams.createWith((demand, subscriber) -> {
            Integer context = subscriber.context();
            System.out.printf("%s context = %d%n", Thread.currentThread(), context);
            if (demand >= 2L && !subscriber.isCancelled()) {
                subscriber.onNext("1");
                subscriber.onNext("2");
                subscriber.onComplete();
            }
        }, subscriber -> 12345, context -> System.out.println("Cancelled!! " + context));
        stream.consume(data -> System.out.printf("%s greeting = %s%n", Thread.currentThread(), data));

        Environment.terminate();
    }

    private static void hot_data_source() {
        Environment.initialize();

        final Broadcaster<String> sink = Broadcaster.create(Environment.get());
        sink.map(String::toUpperCase).consume(
                word -> System.out.printf("%s GREETING = %s%n", Thread.currentThread(), word));
        sink.onNext("Hello Alice!!");
        sink.map(String::toLowerCase).consume(
                word -> System.out.printf("%s greeting = %s%n", Thread.currentThread(), word));
        sink.onNext("Hello Bob!!");
        sink.onNext("Hello Charles!!");

        Environment.terminate();
    }

    private static void wiring_up_stream() throws InterruptedException {
        Environment.initialize();

        Stream<String> stream = Streams.just("a", "b", "c", "d", "e", "f", "g", "h");
        Stream<String> actionChain1 = stream.map(String::toUpperCase).filter(ch -> ch.equals("C"));
        Stream<Long> actionChain2 = stream.dispatchOn(Environment.sharedDispatcher()).take(5).count();
        actionChain1.consume(System.out::println);
        Control control = actionChain2.consume(System.out::println);
        Thread.sleep(300);
        control.cancel();

        Environment.terminate();
    }

    private static void publish_subscribe() throws InterruptedException {
        Environment.initialize();

        Stream<String> stream = Streams.just("a", "b", "c", "d", "e", "f", "g", "h");
        Stream<String> sharedStream = stream.observe(System.out::println).broadcast();
        Stream<String> actionChain1 = sharedStream.map(String::toUpperCase).filter(ch -> ch.equals("C"));
        Stream<Long> actionChain2 = sharedStream.take(5).count();
        actionChain1.consume(System.out::println);
        actionChain2.consume(System.out::println);

        Environment.terminate();
    }

    private static void filters_and_transformations() throws InterruptedException {
        Environment.initialize();

        System.out.println();
        Streams.range(1, 10).filter(i -> i % 2 == 0).map(i -> i + 11).take(5)
                .flatMap(i -> Streams.range(i, i + 10).subscribeOn(Environment.workDispatcher()))
                .consume(System.out::println, Throwable::printStackTrace, i -> System.out.println("xxx"));

        Thread.sleep(300);

        System.out.println();
        Promise<List<Long>> result = Streams.range(1, 10).subscribeOn(Environment.workDispatcher()).toList();
        System.out.println(result.await());
        result.onSuccess(System.out::println);

        Environment.terminate();
    }

    private static void partitioning() throws InterruptedException {
        Environment.initialize();

        DispatcherSupplier supplier1 = Environment.newCachedDispatchers(2, "groupByPool");
        DispatcherSupplier supplier2 = Environment.newCachedDispatchers(5, "partitionPool");

        System.out.println();
        Streams.range(1, 10).groupBy(n -> n % 2 == 1)
                .flatMap(stream -> stream.dispatchOn(supplier1.get()).log("groupBy  ")).partition(5)
                .flatMap(stream -> stream.dispatchOn(supplier2.get()).log("partition"))
                .dispatchOn(Environment.sharedDispatcher()).log("join     ").consume();

        Thread.sleep(300);

        Environment.terminate();
    }

}
