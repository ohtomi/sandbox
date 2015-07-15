package com.example.repl.tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

public class JavaClassByteCode extends SimpleJavaFileObject {

    public JavaClassByteCode(String name, Kind kind) {
        super(URI.create("string:///" + name.replace('.', '/') + kind.extension), kind);
    }

    protected final ByteArrayOutputStream baos = new ByteArrayOutputStream();

    @Override
    public OutputStream openOutputStream() throws IOException {
        return baos;
    }

    public byte[] getBytes() {
        return baos.toByteArray();
    }

    private Class<?> clazz = null;

    public void setDefinedClass(Class<?> c) {
        clazz = c;
    }

    public Class<?> getDefinedClass() {
        return clazz;
    }

}
