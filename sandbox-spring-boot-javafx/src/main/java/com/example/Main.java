package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.example.component.MainLayout;

@EnableAutoConfiguration
@ComponentScan
public class Main extends Application {

    private ConfigurableApplicationContext context;

    @Autowired
    private MainLayout mainLayout;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(mainLayout));
        stage.setTitle("Java REPL Console");
        stage.setWidth(800);
        stage.setHeight(600);
        stage.setResizable(false);
        stage.show();
        mainLayout.focusToInput();
    }

    @Override
    public void init() throws Exception {
        SpringApplication app = new SpringApplication(Main.class);
        app.setWebEnvironment(false);
        context = app.run();
        context.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        context.close();
    }

    public static void main(String... args) throws Exception {
        launch(args);
    }

}
