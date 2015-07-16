package com.example.action;

public class ClearAction implements ActionCreator {

    public static final String TYPE = ClearAction.class.getCanonicalName();

    public void execute() {
        Action action = new Action(TYPE);
        ActionDispatcher.dispatch(action);
    }

}
