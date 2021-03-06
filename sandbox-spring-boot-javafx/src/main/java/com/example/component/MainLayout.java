package com.example.component;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.action.ClearAction;
import com.example.action.RunAction;
import com.example.store.ReplHistoryStore;
import com.example.store.StoreEventListener;

@Component
public class MainLayout extends GridPane implements StoreEventListener {

    private ToolbarLayout toolbar;

    private TextField inputArea;

    private TextArea outputArea;

    @Autowired
    public MainLayout(ToolbarLayout toolbar) {
        this.toolbar = toolbar;
        this.inputArea = new TextField();
        this.outputArea = new TextArea();

        buildComponent();
        buildLayout();
        buildEventListener();
    }

    public void buildComponent() {
        inputArea.setPrefWidth(794);
        outputArea.setPrefWidth(794);
        outputArea.setPrefHeight(515);

        outputArea.setEditable(false);
        outputArea.setWrapText(true);
    }

    private void buildLayout() {
        add(toolbar, 0, 0);
        add(inputArea, 0, 1);
        add(outputArea, 0, 2);

        setPadding(new Insets(3, 3, 3, 3));
    }

    private void buildEventListener() {
        ReplHistoryStore.addListener(this);

        toolbar.setRunButtonListener(event -> {
            new RunAction().execute(inputArea.getText());
        });

        toolbar.setClearButtonListener(event -> {
            new ClearAction().execute();
        });

        inputArea.setOnKeyPressed(key -> {
            if (key.getCode() == KeyCode.ENTER) {
                new RunAction().execute(inputArea.getText());
            }
        });
    }

    @Override
    public void onEvaluate(String statement, String outputs) {
        Platform.runLater(() -> {
            outputArea.appendText("> " + statement + System.lineSeparator());
            outputArea.appendText(outputs + System.lineSeparator());
            inputArea.clear();
        });
    }

    @Override
    public void onClear() {
        Platform.runLater(() -> {
            outputArea.clear();
        });
    }

    public void focusToInput() {
        inputArea.requestFocus();
    }

}
