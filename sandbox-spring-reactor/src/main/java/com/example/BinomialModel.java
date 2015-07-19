package com.example;

import reactor.Environment;
import reactor.fn.tuple.Tuple;
import reactor.fn.tuple.Tuple4;
import reactor.rx.broadcast.Broadcaster;

public class BinomialModel {

    private static void europian_call_option(double underlying, double up, double down, //
            int max_generation, double strike, double rate) throws InterruptedException {

        Environment.initialize();

        // Tuple4<current_generation, underlying, option, path>
        final Broadcaster<Tuple4<Integer, Double, Double, String>> option_price_stream = //
        Broadcaster.create(Environment.get());

        for (int i = 0; i < max_generation; i++) {
            final int current_generation = i;
            option_price_stream.filter(tuple -> tuple.getT1() == current_generation + 1)
                    .groupBy(tuple -> tuple.getT4().substring(0, current_generation + 1))
                    .flatMap(stream -> stream.dispatchOn(Environment.sharedDispatcher()))
                    // TODO sort
                    .observe(tuple -> {
                        for (int tub = 0; tub < current_generation + 1; tub++) {
                            System.out.print("    ");
                        }
                        System.out.println(current_generation + " -> " + tuple);
                    }).window(2).flatMap(stream -> stream.reduce((tuple1, tuple2) -> {
                        Tuple4<Integer, Double, Double, String> result = calculate(current_generation, tuple1, tuple2, //
                                up, down, max_generation, rate);
                        option_price_stream.onNext(result);
                        return result;
                    })).consume();
        }

        supply(max_generation, underlying, up, down, "0", max_generation, strike, option_price_stream);

        Thread.sleep(300);

        Environment.terminate();
    }

    private static void supply(int current_generation, double underlying, double up, double down, String path, //
            int max_generation, double strike, Broadcaster<Tuple4<Integer, Double, Double, String>> option_price_stream) {

        if (current_generation > 0) {
            supply(current_generation - 1, underlying * up, up, down, path + "0", //
                    max_generation, strike, option_price_stream);
            supply(current_generation - 1, underlying * down, up, down, path + "1", //
                    max_generation, strike, option_price_stream);
        } else {
            option_price_stream.onNext(Tuple.of(max_generation, underlying, Math.max(underlying - strike, 0d), path));
        }
    }

    private static Tuple4<Integer, Double, Double, String> calculate(int current_generation,
            Tuple4<Integer, Double, Double, String> tuple1, Tuple4<Integer, Double, Double, String> tuple2, //
            double up, double down, int max_generation, double rate) {

        if (!tuple1.getT4().substring(0, current_generation + 1)
                .equals(tuple2.getT4().substring(0, current_generation + 1))) {
            throw new RuntimeException("Missing tuple pair");
        }

        double r = 1 / (1.0d + rate / max_generation);
        double p = (1.0d + rate / max_generation - down) / (up - down);
        double underlying = tuple1.getT2() / up;
        double call_option = (p * tuple1.getT3() + (1 - p) * tuple2.getT3()) * r;
        String calc_path = tuple1.getT4().substring(0, current_generation + 1);

        // Tuple4<current_generation, underlying, option, path>
        Tuple4<Integer, Double, Double, String> result = //
        Tuple.of(current_generation, underlying, call_option, calc_path);

        if (current_generation == 0) {
            System.out.println(result);
        }

        return result;
    }

    public static void main(String... args) throws InterruptedException {
        europian_call_option(1000d, 1.05d, 0.95d, 1, 1020d, 0.03d);
        europian_call_option(1000d, 1.05d, 0.95d, 2, 1020d, 0.03d);

        europian_call_option(9750d, 1.1d, 0.9d, 5, 10000d, 0.0008d * 0.16667d);
        europian_call_option(9750d, 1.1d, 0.9d, 1, 10000d, 0.0008d * 0.16667d);
        europian_call_option(9750d, 1.2d, 0.8d, 1, 10000d, 0.0008d * 0.16667d);
        europian_call_option(9750d, 1.05d, 0.95d, 1, 10000d, 0.0008d * 0.16667d);

        europian_call_option(100d, 1.1d, 0.9d, 5, 105d, 0.03d);
    }

}
