package org.example;

public class Main {

    public static void main(String... args) {
        Sandbox app = new Sandbox();

        try {
            app.runNashornScriptInSandbox("12345");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("====");

        try {
            app.runNashornScriptInSandbox("var files = new java.io.File('.').list(); files[0] + ' ... ' + files[files.length -1 ];");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("====");

        try {
            String[] files = new java.io.File(".").list();
            if (files != null) {
                System.out.println(String.format("files[0] is %s ... files[last] is %s", files[0], files[files.length - 1]));
            } else {
                System.out.println("files is null");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("====");

        try {
            app.runNashornScriptInSandbox("'hoge'");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("====");
    }
}
