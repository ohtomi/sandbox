package com.example.store;

public interface StoreEventListener {

    void onEvaluate(String statement, String outputs);

    void onClear();

}
