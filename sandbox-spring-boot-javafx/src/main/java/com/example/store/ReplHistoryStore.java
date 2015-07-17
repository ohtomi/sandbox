package com.example.store;

import java.util.ArrayList;
import java.util.List;

import com.example.action.Action;
import com.example.action.ActionDispatcher;
import com.example.action.ActionEventListener;
import com.example.action.ClearAction;
import com.example.action.RunAction;

public class ReplHistoryStore implements ActionEventListener {

    public static final String EVENT_SOURCE = ReplHistoryStore.class.getCanonicalName();

    private static ReplHistoryStore instance;

    static {
        instance = new ReplHistoryStore();
        ActionDispatcher.register(instance);
    }

    public static void addListener(StoreEventListener listener) {
        instance.listeners.add(listener);
    }

    public static void removeListener(StoreEventListener listener) {
        instance.listeners.remove(listener);
    }

    public static String getStatements() {
        String statements = "";
        for (String statement : instance.history) {
            statements += statement + ";" + System.lineSeparator();
        }
        return statements;
    }

    private ReplHistoryStore() {
    }

    private List<StoreEventListener> listeners = new ArrayList<>();

    private List<String> history = new ArrayList<>();

    @Override
    public void fireAction(String eventSource, Action action) {
        if (action.getType().equals(RunAction.TYPE)) {
            String statement = action.getPayloadEntry(RunAction.STATEMENT);
            String outputs = action.getPayloadEntry(RunAction.OUTPUTS);
            history.add(statement);
            listeners.forEach(listener -> {
                listener.onEvaluate(statement, outputs);
            });
        }
        if (action.getType().equals(ClearAction.TYPE)) {
            history.clear();
            listeners.forEach(listener -> {
                listener.onClear();
            });
        }
    }

}
