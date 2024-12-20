package com.example.daistderwurmdrin;

import com.almasb.fxgl.gameplay.GameDifficulty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HelloController {
    @FXML private Button quitButton;
    @FXML private Button newButton;
    @FXML private Button easyButton;
    @FXML private Button mediumButton;
    @FXML private Button hardButton;

    @FXML private AnchorPane startPane;
    @FXML private AnchorPane difficultyPane;
    @FXML private AnchorPane gamePane;

    @FXML private ImageView MyImageView;
    @FXML private ImageView wurmImage;

    @FXML private File directory;
    @FXML private File[] files;
    @FXML private ArrayList<File> songs;
    @FXML private int songNumber;

    @FXML private Media media;
    @FXML private MediaPlayer mediaPlayer;

    @FXML private Scene scene;
    @FXML private Parent root;
    @FXML private Stage stage;
    // display the image in the start interface
    Image Welcome = new Image(getClass().getResourceAsStream("icon.jpg"));

    public void displayImage() {
        MyImageView.setImage(Welcome);
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
        songs = new ArrayList<File>();
        directory = new File("Music");
        files = directory.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                songs.add(files[i]);
                System.out.println(files[i]);
            }
        }
        media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.15);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                nextSong(null);
            }
        });
    }
    public void Playmusic(ActionEvent event) {
        mediaPlayer.setVolume(0.15);
        mediaPlayer.play();
        // Setting so that the next song runs after the song finishes
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                nextSong(null);
            }
        });
    }
    public void Stopmusic(ActionEvent event) {
        mediaPlayer.stop();
    }
    public void nextSong(ActionEvent event) {
        if(songNumber < songs.size() - 1){
            songNumber++;
            mediaPlayer.stop();

            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            Playmusic(null);
        }
        else{
            songNumber = 0;
            mediaPlayer.stop();

            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            Playmusic(null);
        }
    }
    public void prevSong(ActionEvent event) {
        if(songNumber > 0){
            songNumber--;
            mediaPlayer.stop();

            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            Playmusic(null);
        }
        else{
            songNumber = songs.size() - 1;
            mediaPlayer.stop();

            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            Playmusic(null);
        }
    }
    // Start a new game
    public void newGame(ActionEvent event) throws IOException {
        root = FXMLLoader.load(HelloApplication.class.getResource("PigGUI.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(true);
        stage.setTitle("Da ist der Wurm Drin");
        stage.setScene(scene);
        stage.show();
        Stopmusic(null);
    }
    public void chooseDifficulty(ActionEvent event) throws IOException {
        root = FXMLLoader.load(HelloApplication.class.getResource("Difficulty.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        difficultyPane = new AnchorPane();
        difficultyPane.setStyle("-fx-background-color: DARKBLUE;");
        stage.setResizable(true);
        stage.setTitle("Da ist der Wurm Drin");
        stage.setScene(scene);
        stage.show();
        Stopmusic(null);
    }
}