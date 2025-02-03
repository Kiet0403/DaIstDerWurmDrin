/** * OOP Java Project

 * Children Board Game Simulation:  Da ist der Wurm drin

 * Link: https://www.amazon.de/Zoch-601132100-Wurm-Kinderspiel-Jahres/dp/B004L87UQO?th=1;
 * https://www.youtube.com/watch?v=kD8JI8RpTFM;

 * @author Van Tuan Kiet Vo - 1589900

*/

package com.example.daistderwurmdrin;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Application.class.getResource("loading_screen.fxml"));
        Scene scene = new Scene(root, Color.LIGHTCYAN);
        stage.setResizable(true);
        stage.setTitle("Da ist der Wurm Drin");

        Image icon = new Image("icon.jpg");
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