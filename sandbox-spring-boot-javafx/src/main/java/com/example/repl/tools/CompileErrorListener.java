package com.example.repl.tools;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaFileObject;

public class CompileErrorListener implements DiagnosticListener<JavaFileObject> {

    private Diagnostic<? extends JavaFileObject> lastErrorInfo;

    @Override
    public void report(Diagnostic<? extends JavaFileObject> diagnostic) {
        lastErrorInfo = diagnostic;
        System.err.println("Compile Recap");
        System.out.println("errcode: " + diagnostic.getCode());
        System.out.println("line   : " + diagnostic.getLineNumber());
        System.out.println("column : " + diagnostic.getColumnNumber());
        System.out.println("message: " + diagnostic.getMessage(null));
    }

    public Diagnostic<? extends JavaFileObject> getLastErrorInfo() {
        return lastErrorInfo;
    }

}