package com.kamilglazer.to_lab03;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 700);
        stage.setTitle("Virus simulation");
        stage.setResizable(false);

        String css = Objects.requireNonNull(HelloApplication.class.getResource("/com/kamilglazer/to_lab03/style.css")).toExternalForm();

        if(css == null){
            throw new RuntimeException("Nie znaleziono pliku css!");
        }

        scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}