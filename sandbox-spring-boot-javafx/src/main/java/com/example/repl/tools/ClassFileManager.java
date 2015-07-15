package com.example.repl.tools;

import java.io.IOException;
import java.security.SecureClassLoader;
import java.util.HashMap;
import java.util.Map;

import javax.tools.DiagnosticListener;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;

public class ClassFileManager extends ForwardingJavaFileManager<JavaFileManager> {

    public ClassFileManager(JavaCompiler compiler, DiagnosticListener<? super JavaFileObject> listener) {
        super(compiler.getStandardFileManager(listener, null, null));
    }

    protected final Map<String, JavaClassByteCode> map = new HashMap<String, JavaClassByteCode>();

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, Kind kind, FileObject sibling)
            throws IOException {

        JavaClassByteCode byteCode = new JavaClassByteCode(className, kind);
        map.put(className, byteCode);
        return byteCode;
    }

    protected ClassLoader loader = null;

    @Override
    public ClassLoader getClassLoader(Location location) {
        if (loader == null) {
            loader = new Loader();
        }
        return loader;
    }

    private class Loader extends SecureClassLoader {

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            JavaClassByteCode byteCode = map.get(name);
            if (byteCode == null) {
                return super.findClass(name);
            }

            Class<?> c = byteCode.getDefinedClass();
            if (c == null) {
                byte[] b = byteCode.getBytes();
                c = super.defineClass(name, b, 0, b.length);
                byteCode.setDefinedClass(c);
            }
            return c;
        }

    }

}
