package com.example.component;

import javafx.scene.control.TextArea;

import org.springframework.stereotype.Component;

@Component
public class OutputArea extends TextArea {

    public OutputArea() {
        this.setEditable(false);
    }

}
