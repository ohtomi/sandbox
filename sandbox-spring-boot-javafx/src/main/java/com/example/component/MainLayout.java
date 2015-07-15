package com.example.component;

import javafx.scene.layout.GridPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainLayout extends GridPane {

    private InputArea inputArea;

    private OutputArea outputArea;

    @Autowired
    public MainLayout(InputArea inputArea, OutputArea outputArea) {
        this.inputArea = inputArea;
        this.outputArea = outputArea;

        add(new ToolbarLayout(), 0, 0);
        add(this.inputArea, 0, 1);
        add(this.outputArea, 0, 2);

        this.inputArea.setText("javafx.application.Platform.runLater(() -> { new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING, \"Hello, World!\", javafx.scene.control.ButtonType.OK).showAndWait(); });"); // TODO
    }

    public void focusToInput() {
        inputArea.requestFocus();
    }

}
