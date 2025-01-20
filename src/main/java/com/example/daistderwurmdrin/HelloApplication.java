package com.example.daistderwurmdrin;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("bumisdumb.fxml"));
        Scene scene = new Scene(root, Color.LIGHTCYAN);
        stage.setResizable(true);
        stage.setTitle("Da ist der Wurm Drin");

        Image icon = new Image("weasel ump9.png");
        stage.getIcons().add(icon);

        stage.setFullScreen(false);
        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("Esc"));

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}