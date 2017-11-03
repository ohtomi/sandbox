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

public class Sandbox {

    public void runNashornScriptInSandbox(String source) throws Exception {
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
}
