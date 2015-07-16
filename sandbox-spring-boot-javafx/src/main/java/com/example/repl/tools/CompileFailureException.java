package com.example.repl.tools;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

public class CompileFailureException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Diagnostic<? extends JavaFileObject> lastErrorInfo;

    public CompileFailureException(String message, Diagnostic<? extends JavaFileObject> lastErrorInfo) {
        super(message);
        this.lastErrorInfo = lastErrorInfo;
    }

    public Diagnostic<? extends JavaFileObject> getLastErrorInfo() {
        return lastErrorInfo;
    }

}
