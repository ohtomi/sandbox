package com.example;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

public class Main {

    private static Injector injector;

    public static void main(String... args) {
        injector = Guice.createInjector(new SampleModule());
        new Main().getInstance();
    }

    public void getInstance() {
        SampleInterface instance = injector.getInstance(Key.get(SampleInterface.class, Names.named("sample1")));
        System.out.println("get? " + instance);
        System.out.println("type " + instance.getClass());
        System.out.println("cast " + instance.getClass().cast(instance);
        //System.out.println("foo? " + instance.getClass().cast(instance).foo());
    }

}
