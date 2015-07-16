package com.example.repl;

import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.repl.tools.CompileFailureException;
import com.example.repl.tools.CompileService;

public class Evaluator {

    public static void execute(String statement, PrintStream newOut) throws CompileFailureException {
        PrintStream oldOut = System.out;
        ExecutorService executor = Executors.newCachedThreadPool();

        try {
            System.setOut(newOut);
            CompileService service = new CompileService();
            EnclosingClassTemplate template = new EnclosingClassTemplate();
            template.setStatement(statement);
            Class<Runnable> c = service.execute(template.getFqcn(), template.getSourceCode());
            Runnable task = c.newInstance();
            executor.submit(task).get();

        } catch (CompileFailureException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace(newOut);
        } finally {
            System.setOut(oldOut);
            executor.shutdownNow();
        }
    }

}
