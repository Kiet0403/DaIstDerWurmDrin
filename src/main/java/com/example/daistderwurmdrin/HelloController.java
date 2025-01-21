package com.example.daistderwurmdrin;

import java.io.File;
import java.io.IOException;

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

    @FXML private Media media;
    @FXML private MediaPlayer mediaPlayer;
    @FXML private MediaPlayer clickSfxPlayer;
//    @FXML private MediaPlayer hoverSfxPlayer;

    @FXML private Scene scene;
    @FXML private Parent root;
    @FXML private Stage stage;

    public String difficulty;

    private boolean isMuted = false;
    // Display the image in the start interface

    Image Mute = new Image("mute_image.png");
    Image unMute = new Image("unmute_image.png");

    //Adjust the volume of the music to 0, effectively muting it
    public void setMuteButton() {
        if (isMuted == false) {
            muteButton.setImage(Mute);
            Stopmusic();
        } else {
            muteButton.setImage(unMute);
            Playmusic();
        }
        isMuted = !isMuted; // Toggle the state
    }

    // Exit the program
    public void quit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to quit?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) quitButton.getScene().getWindow();
            stage.close();
        }
    }

    //Loading preset Song library in Music
    public void initialize() {
        String song = new File("music\\girls-frontline-shattered-connexion-ed-connexion.mp3").toURI().toString();
        media = new Media(song);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.1);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setAutoPlay(true);

        //Trigger sound effects when clicking on a button
        String clickSfx = new File("sfx/metal-pipe-clang.mp3").toURI().toString();
        Media clickSound = new Media(clickSfx);
        clickSfxPlayer = new MediaPlayer(clickSound);

//        String hoverSfx = new File("sfx/metal-pipe-clang.mp3").toURI().toString();
//        Media hoverSound = new Media(hoverSfx);
//        clickSfxPlayer = new MediaPlayer(hoverSound);

        muteButton.setImage(unMute);
    }
    // Play and stop the music functions
    public void Playmusic() {
        mediaPlayer.setVolume(0.1);
    }
    public void Stopmusic() {
        mediaPlayer.setVolume(0);
    }

    public void onHover(ActionEvent event){

    }

    // Start a new game
    public void newGame(ActionEvent event) throws IOException {
        // set the chosen difficulty
        if (event.getSource() instanceof Button) {
            Button clickedButton = (Button) event.getSource();
            String buttonText = clickedButton.getText();

            // Check which button was pressed
            switch (buttonText) {
                case "Easy":
                    difficulty = "Easy";
                    break;
                case "Medium":
                    difficulty = "Medium";
                    break;
                case "Hard":
                    difficulty = "Hard";
                    break;
            }
        }
        clickSfxPlayer.seek(clickSfxPlayer.getStartTime()); // Reset to start
        clickSfxPlayer.play();
        //Load the fxml with the game

        FXMLLoader loader = new FXMLLoader(getClass().getResource("testGame3(main).fxml"));
        root = loader.load();

        PigController pigController = loader.getController();
        pigController.setDifficulty(difficulty);

        //root = FXMLLoader.load(HelloApplication.class.getResource("testGame3(main).fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(true);
        stage.setTitle("Da ist der Wurm Drin");
        stage.setScene(scene);
        stage.show();
        Stopmusic();
    }

    // Reveal the panel that contains the difficulty buttons
    public void chooseDifficulty(ActionEvent event){
        playButton.setVisible(false);
        quitButton.setVisible(false);

        clickSfxPlayer.seek(clickSfxPlayer.getStartTime()); // Reset to start
        clickSfxPlayer.play();

        difficultyPane.setVisible(true);

    }
    // Return back to the Menu
    public void back_to_Menu(){
        clickSfxPlayer.seek(clickSfxPlayer.getStartTime()); // Reset to start
        clickSfxPlayer.play();

        difficultyPane.setVisible(false);
        playButton.setVisible(true);
        quitButton.setVisible(true);
    }
}