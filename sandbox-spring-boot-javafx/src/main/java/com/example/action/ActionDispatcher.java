package com.example.action;

import java.util.ArrayList;
import java.util.List;

public class ActionDispatcher {

    public static final String EVENT_SOURCE = ActionDispatcher.class.getCanonicalName();

    private static List<ActionEventListener> listeners = new ArrayList<>();

    public static void register(ActionEventListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    public static void dispatch(Action action) {
        listeners.forEach(listener -> {
            listener.fireAction(EVENT_SOURCE, action);
        });
    }

    private ActionDispatcher() {
    }

}
