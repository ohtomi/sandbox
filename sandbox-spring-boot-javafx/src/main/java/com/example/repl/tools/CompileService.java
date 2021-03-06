package com.example.repl.tools;

import java.util.Arrays;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

public class CompileService {

    protected CompileErrorListener listener = new CompileErrorListener();

    protected JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

    public <T> Class<T> execute(String className, String sourceCode) throws CompileFailureException {
        JavaFileObject sourceObject = new JavaClassSourceCode(className, sourceCode);

        List<JavaFileObject> compilationUnits = Arrays.asList(sourceObject);
        List<String> options = Arrays.asList("-classpath", System.getProperty("java.class.path"));
        JavaFileManager manager = new ClassFileManager(compiler, listener);
        CompilationTask task = compiler.getTask(null, manager, listener, options, null, compilationUnits);

        boolean successCompile = task.call();
        if (!successCompile) {
            throw new CompileFailureException("Failed to compile " + className, listener.getLastErrorInfo());
        }

        ClassLoader cl = manager.getClassLoader(null);
        try {
            @SuppressWarnings("unchecked")
            Class<T> c = (Class<T>) cl.loadClass(className);
            return c;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
