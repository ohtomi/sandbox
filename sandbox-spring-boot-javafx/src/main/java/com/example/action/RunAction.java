package com.example.action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import com.example.repl.Evaluator;
import com.example.repl.tools.CompileFailureException;
import com.example.store.EvaluationResultStore;

public class RunAction implements ActionCreator {

    public static final String TYPE = RunAction.class.getCanonicalName();

    public static final String STATEMENT = "statement";

    public static final String OUTPUTS = "outputs";

    public void execute(String statement) {
        ExecutorService executor = Executors.newCachedThreadPool();

        try {
            executor.submit(() -> {
                try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        PrintStream newOut = new PrintStream(baos)) {

                    String statements = EvaluationResultStore.getStatements() + statement;
                    Evaluator.execute(statements, newOut);
                    String outputs = baos.toString();
                    Action action = new Action(TYPE) //
                            .putPayloadEntry(STATEMENT, statement) //
                            .putPayloadEntry(OUTPUTS, outputs);
                    ActionDispatcher.dispatch(action);

                } catch (CompileFailureException e) {
                    Diagnostic<? extends JavaFileObject> diagnostic = e.getLastErrorInfo();
                    String outputs = diagnostic.getLineNumber() + System.lineSeparator();
                    outputs += diagnostic.getColumnNumber() + System.lineSeparator();
                    outputs += diagnostic.getMessage(null) + System.lineSeparator();
                    Action action = new Action(TYPE) //
                            .putPayloadEntry(STATEMENT, statement) //
                            .putPayloadEntry(OUTPUTS, outputs);
                    ActionDispatcher.dispatch(action);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } finally {
            executor.shutdown();
        }
    }
}
