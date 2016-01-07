package com.example.foo;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hello {

    private static final Logger LOGGER = LoggerFactory.getLogger(Hello.class);

    public String print() {
        try {
            Properties props = new Properties();
            props.load(this.getClass().getResourceAsStream("/locale/messages.properties"));
            String message = props.getProperty("message.hello");
            return message;
        } catch (IOException e) {
            LOGGER.error("Failed to load properties", e);
            return "";
        }
    }

}
