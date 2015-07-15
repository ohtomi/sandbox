package com.example.component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.repl.Evaluator;

@Component
public class InputArea extends TextField {

    @Autowired
    OutputArea outputArea;

    public InputArea() {
        this.onKeyPressedProperty().setValue(
                key -> {
                    if (key.getCode() == KeyCode.ENTER) {
                        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                PrintStream newOut = new PrintStream(baos)) {

                            Evaluator.execute(getText(), newOut);
                            outputArea.appendText("> " + getText() + System.lineSeparator());
                            outputArea.appendText(baos.toString() + System.lineSeparator());
                            this.clear();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}
