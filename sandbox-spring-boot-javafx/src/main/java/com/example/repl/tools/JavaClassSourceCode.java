package com.example.repl.tools;

import java.net.URI;

import javax.tools.SimpleJavaFileObject;

public class JavaClassSourceCode extends SimpleJavaFileObject {

    private String code;

    public JavaClassSourceCode(String name, String code) {
        super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
        this.code = code;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return code;
    }

}
