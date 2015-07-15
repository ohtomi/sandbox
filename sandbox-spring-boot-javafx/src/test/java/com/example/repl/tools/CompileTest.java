package com.example.repl.tools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.Test;

public class CompileTest {

    @Test
    public void test() {
        try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw)) {

            pw.println("package com.example.repl.tools;");
            pw.println("import com.example.repl.tools.CompileTestInterface;");
            pw.println("public class SampleClass implements CompileTestInterface {");
            pw.println("public String getValue() {");
            pw.println("return \"ok\";");
            pw.println("}");
            pw.println("}");

            String src = sw.toString();
            CompileService service = new CompileService();
            Class<CompileTestInterface> c = service.execute("com.example.repl.tools.SampleClass", src);
            CompileTestInterface s = c.newInstance();
            assertEquals("ok", s.getValue());

        } catch (IOException e) {
            fail();
        } catch (InstantiationException e) {
            fail();
        } catch (IllegalAccessException e) {
            fail();
        }
    }

}
