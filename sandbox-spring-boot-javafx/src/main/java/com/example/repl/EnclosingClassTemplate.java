package com.example.repl;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class EnclosingClassTemplate {

    private String packageName = "com.example.repl";

    private String className = "SampleClass";

    private String interfaceName = "java.lang.Runnable";

    private String sourceCode;

    public String getFqcn() {
        return packageName + "." + className;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setStatement(String statement) throws IOException {
        try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw)) {

            pw.println("package " + getPackageName() + ";");
            pw.println("import " + getFqcn() + ";");
            pw.println("public class " + getClassName() + " implements " + getInterfaceName() + " {");
            pw.println("@Override");
            pw.println("public void run() {");
            pw.println(statement + ";");
            pw.println("}");
            pw.println("}");

            sourceCode = sw.toString();
        }
    }

}
