package com.example.foo;

import java.io.IOException;
import java.util.Properties;

public class Hello {

    public void print() {
        try {
            Properties props = new Properties();
            props.load(this.getClass().getResourceAsStream("/locale/messages.properties"));
            String message = props.getProperty("message.hello");
            System.out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
