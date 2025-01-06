package com.example.daistderwurmdrin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class HelloController {
    @FXML private Button quitButton;
    @FXML private Button playButton;
    @FXML private Button easyButton;
    @FXML private Button mediumButton;
    @FXML private Button hardButton;
    @FXML private Button backButton;

    @FXML private AnchorPane startPane;
    @FXML private VBox difficultyPane;
    @FXML private AnchorPane gamePane;

    @FXML private ImageView MyImageView;
    @FXML private ImageView wurmImage;
    @FXML private ImageView muteButton;

    @FXML private File directory;
    @FXML private File[] files;
    @FXML private ArrayList<File> songs;
    @FXML private int songNumber;

    @FXML private Media media;
    @FXML private MediaPlayer mediaPlayer;

    @FXML private Scene scene;
    @FXML private Parent root;
    @FXML private Stage stage;

    private boolean isMuted = false;
    // display the image in the start interface

    Image Mute = new Image("mute_image.png");
    Image unMute = new Image("unmute_image.png");

    public void setMuteButton() {
        if (isMuted == false) {
            muteButton.setImage(Mute);
            Stopmusic(null);
        } else {
            muteButton.setImage(unMute);
            Playmusic(null);
        }
        isMuted = !isMuted; // Toggle the state
    }

    // Exit the program
    public void quit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to quit?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) quitButton.getScene().getWindow();
            stage.close();
        }
    }

    //Loading preset Songs library in Music
    public void initialize() {
        String song = new File("music/ghostfinaltwilight-feat-kinoko蘑菇-girls-frontline-ost-ドールズフロントラインofficial.mp3").toURI().toString();
        media = new Media(song);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(1);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setAutoPlay(true);

        muteButton.setImage(unMute);
    }
    public void Playmusic(ActionEvent event) {
        mediaPlayer.setVolume(1);
    }
    public void Stopmusic(ActionEvent event) {
        mediaPlayer.setVolume(0);
    }

    // Start a new game
    public void newGame(ActionEvent event) throws IOException {
        root = FXMLLoader.load(HelloApplication.class.getResource("Game.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(true);
        stage.setTitle("Da ist der Wurm Drin");
        stage.setScene(scene);
        stage.show();
        Stopmusic(null);
    }

    public void chooseDifficulty(ActionEvent event) throws IOException {
        playButton.setVisible(false);
        quitButton.setVisible(false);
        difficultyPane.setVisible(true);
    }

    public void back_to_Menu(ActionEvent event) throws IOException {
        difficultyPane.setVisible(false);
        playButton.setVisible(true);
        quitButton.setVisible(true);
    }



}