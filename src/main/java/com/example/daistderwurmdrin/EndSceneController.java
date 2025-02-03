package com.example.daistderwurmdrin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.FadeTransition;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.IOException;

public class EndSceneController{
    @FXML private Scene scene;
    @FXML private Parent root;
    @FXML private Stage stage;

    @FXML private ImageView endingTitle;
    @FXML private ImageView endingImage;
    @FXML private ImageView winnerImage;

    @FXML private Button restartButton;
    @FXML private Button quitButton;

    private String result;
    private String winner;

    public void initialize(){
        if(result =="win") {
            Image win = new Image("win.png");
            Image win_title = new Image("result_win.png");
            endingTitle.setImage(win_title);
            endingImage.setImage(win);
            switch (winner){
                case "Little Gritty":
                    Image p1 = new Image("worm1.png");
                    winnerImage.setImage(p1);
                    break;
                case "Stripy Toni":
                    Image p2 = new Image("worm2.png");
                    winnerImage.setImage(p2);
                    break;
                case "Ruby Red":
                     Image p3 = new Image("worm3.png");
                     winnerImage.setImage(p3);
                     break;
                case "Lady Silver":
                    Image p4 = new Image("worm4.png");
                    winnerImage.setImage(p4);
                    break;
            }
        }
        else{
            Image draw= new Image("draw.png");
            Image draw_title= new Image("result_draw.png");
            endingTitle.setImage(draw_title);
            endingImage.setImage(draw);
        }
    }

    public void setResult(String outcome) {
        result = outcome;
    }
    public void setWinner(String winner) {
        this.winner = winner;
    }

    public void restartGame(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menuScreen.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(true);
        stage.setTitle("Da ist der Wurm Drin");
        stage.setScene(scene);
        stage.show();
    }


    public void exitGame(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to quit?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) quitButton.getScene().getWindow();
            stage.close();
        }
    }
}

