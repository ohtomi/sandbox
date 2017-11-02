package org.example;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.AllPermission;
import java.security.CodeSource;
import java.security.Permissions;
import java.security.Policy;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;
import java.security.cert.Certificate;

public class Main {

    private void runNashornScriptInSandbox(String source) throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("nashorn");

        Object result = sandbox(() -> {
            return engine.eval(source);
        });
        System.out.println(String.format("result is '%s'", result));
    }

    private Object sandbox(PrivilegedExceptionAction<Object> action) throws PrivilegedActionException {
        Policy.setPolicy(new java.security.Policy() {
            @Override
            public java.security.PermissionCollection getPermissions(java.security.ProtectionDomain domain) {
                Permissions permissions = new Permissions();
                permissions.add(new AllPermission());
                return permissions;
            }
        });
        System.setSecurityManager(new SecurityManager());

        ProtectionDomain domain = new ProtectionDomain(new CodeSource(null, (Certificate[]) null), new Permissions());
        final AccessControlContext context = new AccessControlContext(new ProtectionDomain[]{domain});

        try {
            return AccessController.doPrivileged(action, context);
        } finally {
            System.setSecurityManager(null);
        }
    }

    public static void main(String... args) {
        Main app = new Main();

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
