package org.example;

import org.junit.Assert;
import org.junit.Test;

import java.security.AccessControlException;

public class SandboxTest {

    @Test(expected = AccessControlException.class)
    public void testSandbox() throws Exception {
        Sandbox app = new Sandbox();
        app.runNashornScriptInSandbox("12345");
        Assert.fail();
    }
}
