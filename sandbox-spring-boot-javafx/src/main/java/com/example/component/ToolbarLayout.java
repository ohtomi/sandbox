package com.example.component;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class ToolbarLayout extends GridPane {

    public ToolbarLayout() {
        add(new Button("Run"), 0, 0);
        add(new Button("Clear"), 1, 0);
    }

}
