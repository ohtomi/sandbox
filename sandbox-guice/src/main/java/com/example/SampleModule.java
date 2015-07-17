package com.example;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class SampleModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(SampleInterface.class).annotatedWith(Names.named("sample")).to(SampleClass.class);
    }

}
