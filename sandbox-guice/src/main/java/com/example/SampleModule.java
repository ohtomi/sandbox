package com.example;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class SampleModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(SampleInterface.class).annotatedWith(Names.named("sample1")).to(Sample1Class.class);
        bind(SampleInterface.class).annotatedWith(Names.named("sample2")).to(Sample2Class.class);
    }

}
