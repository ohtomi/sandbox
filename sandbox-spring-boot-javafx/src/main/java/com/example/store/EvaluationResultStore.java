package com.example.store;

import java.util.ArrayList;
import java.util.List;

import com.example.action.Action;
import com.example.action.ActionDispatcher;
import com.example.action.ActionEventListener;
import com.example.action.ClearAction;
import com.example.action.RunAction;

public class EvaluationResultStore implements ActionEventListener {

    public static final String EVENT_SOURCE = EvaluationResultStore.class.getCanonicalName();

    private static EvaluationResultStore instance;

    static {
        instance = new EvaluationResultStore();
        ActionDispatcher.register(instance);
    }

    public static void addListener(StoreEventListener listener) {
        instance.listeners.add(listener);
    }

    public static void removeListener(StoreEventListener listener) {
        instance.listeners.remove(listener);
    }

    public static String getStatements() {
        String allStatement = "";
        for (String s : instance.statements) {
            allStatement += s + ";" + System.lineSeparator();
        }
        return allStatement;
    }

    private EvaluationResultStore() {
    }

    private List<StoreEventListener> listeners = new ArrayList<>();

    private List<String> statements = new ArrayList<>();

    @Override
    public void fireAction(String eventSource, Action action) {
        if (action.getType().equals(RunAction.TYPE)) {
            String statement = action.getPayloadEntry(RunAction.STATEMENT);
            String outputs = action.getPayloadEntry(RunAction.OUTPUTS);
            statements.add(statement);
            listeners.forEach(listener -> {
                listener.onEvaluate(statement, outputs);
            });
        }
        if (action.getType().equals(ClearAction.TYPE)) {
            statements.clear();
            listeners.forEach(listener -> {
                listener.onClear();
            });
        }
    }

}
