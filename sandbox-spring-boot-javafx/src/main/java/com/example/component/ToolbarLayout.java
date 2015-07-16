package com.example.component;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import org.springframework.stereotype.Component;

@Component
public class ToolbarLayout extends GridPane {

    private Button runButton;

    private Button clearButton;

    private EventHandler<? super MouseEvent> runClicked;

    private EventHandler<? super MouseEvent> clearClicked;

    public ToolbarLayout() {
        this.runButton = new Button("Run");
        this.clearButton = new Button("Clear");

        buildLayout();
        buildEventListener();
    }

    private void buildLayout() {
        super.setPadding(new Insets(0, 0, 3, 0));

        add(runButton, 0, 0);
        add(clearButton, 1, 0);
    }

    private void buildEventListener() {
        runButton.setOnMouseClicked(event -> {
            if (runClicked != null) {
                runClicked.handle(event);
            }
        });
        clearButton.setOnMouseClicked(event -> {
            if (clearClicked != null) {
                clearClicked.handle(event);
            }
        });
    }

    public void setRunButtonListener(EventHandler<? super MouseEvent> listener) {
        runClicked = listener;
    }

    public void setClearButtonListener(EventHandler<? super MouseEvent> listener) {
        clearClicked = listener;
    }

}
