package com.example.daistderwurmdrin;

import java.io.File;
import java.io.IOException;

import javafx.animation.TranslateTransition;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.stage.Modality;

public class StartScreenController {

    @FXML private Button playButton;
    @FXML private Button quitButton;
    @FXML private Button infoButton;
    @FXML private Button muteButton;
    @FXML private Button easyButton;
    @FXML private Button mediumButton;
    @FXML private Button hardButton;
    @FXML private Button backButton;
    @FXML private Button singleplayerButton;
    @FXML private Button multiplayerButton;


    @FXML private GridPane background1;
    @FXML private GridPane background2;
    @FXML private VBox gameModePane;
    @FXML private VBox difficultyPane;


    @FXML private ImageView MyImageView;
    @FXML private ImageView wurmImage;
    @FXML private ImageView muteIcon;

    @FXML private Media media;
    @FXML private MediaPlayer mediaPlayer;
    @FXML private MediaPlayer clickSfxPlayer;
    @FXML private MediaPlayer hoverSfxPlayer;

    @FXML private Scene scene;
    @FXML private Parent root;
    @FXML private Stage stage;

    public String difficulty;

    private boolean isMuted = false;
    // Display the image in the start interface

    Image Mute = new Image("mute_image.png");
    Image unMute = new Image("unmute_image.png");

    public void initialize() {
        String song = new File("music\\menuMusic.mp3").toURI().toString();
        media = new Media(song);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.1);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setAutoPlay(true);

        //Trigger sound effects when clicking on a button
        String clickSfx = new File("sfx/buttonClickSfx.mp3").toURI().toString();
        Media clickSound = new Media(clickSfx);
        clickSfxPlayer = new MediaPlayer(clickSound);

        //Trigger sound effects when hovering on a button
        String hoverSfx = new File("sfx/buttonHoverSfx.mp3").toURI().toString();
        Media hoverSound = new Media(hoverSfx);
        hoverSfxPlayer = new MediaPlayer(hoverSound);

        muteIcon.setImage(unMute);
    }
    // Play and stop the music functions
    public void Playmusic() {
        mediaPlayer.setVolume(0.1);
    }
    public void Stopmusic() {
        mediaPlayer.setVolume(0);
    }

    //Adjust the volume of the music to 0, effectively muting it
    public void setMuteButton(ActionEvent event) {
        onClick();
        if (isMuted == false) {
            muteIcon.setImage(Mute);
            Stopmusic();
        } else {
            muteIcon.setImage(unMute);
            Playmusic();
        }
        isMuted = !isMuted; // Toggle the state
    }

    public void onHover(){
        hoverSfxPlayer.seek(hoverSfxPlayer.getStartTime()); // Reset to start
        hoverSfxPlayer.play();
    }
    public void onClick(){
        clickSfxPlayer.seek(clickSfxPlayer.getStartTime()); // Reset to start
        clickSfxPlayer.play();
    }

    public void rollUpBackground() {
        double height = background1.getHeight(); // Get the height of the background

        background2.setTranslateY(height); // Move background2 BELOW background1

        // Move the first background up and fade it out
        TranslateTransition transition1 = new TranslateTransition(Duration.seconds(2), background1);
        transition1.setByY(-height);

        // Move the second background up at the same time
        TranslateTransition transition2 = new TranslateTransition(Duration.seconds(2), background2);
        transition2.setByY(-height);

        transition1.play();
        transition2.play();

        //Hide the first background after animation completes
        transition1.setOnFinished(event -> background1.setVisible(false));
    }

    public void openInstruction() throws Exception {
        // Load the popup FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("instruction.fxml"));
        Parent root = loader.load();

        Stage popupStage = new Stage();
        popupStage.setResizable(false);
        popupStage.initModality(Modality.APPLICATION_MODAL); // Makes the popup block other windows
        popupStage.setTitle("Instructions");
        popupStage.setScene(new Scene(root, 861.6, 632)); // Adjust size to fit book layout
        popupStage.show();
    }

    public void chooseGameMode(ActionEvent event) {
        playButton.setVisible(false);

        rollUpBackground();
        backButton.setVisible(true);
        gameModePane.setVisible(true);
    }

    // Reveal the panel that contains the difficulty buttons
    public void chooseDifficulty(ActionEvent event){
        gameModePane.setVisible(false);
        difficultyPane.setVisible(true);
    }

    // Start a new singleplayer game
    public void newSingleGame(ActionEvent event) throws IOException {
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

        //Load the fxml with the game

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gameScreen.fxml"));
        loader.setControllerFactory(param -> {
            GameController pigController = new GameController();
            pigController.setDifficulty(difficulty);
            pigController.setPlayerTypes(new String[]{"human", "bot", "bot", "bot"});
            return pigController;
        });

        root = loader.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(true);
        stage.setTitle("Da ist der Wurm Drin");
        stage.setScene(scene);
        stage.show();
        Stopmusic();
    }

    // Start a new multiplayer game
    public void newMultiGame(ActionEvent event) throws IOException {
        //Load the fxml with the game

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gameScreen.fxml"));
        loader.setControllerFactory(param -> {
            GameController pigController = new GameController();
            pigController.setDifficulty(difficulty);
            pigController.setPlayerTypes(new String[]{"human", "human", "human", "human"});
            return pigController;
        });

        root = loader.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(true);
        stage.setTitle("Da ist der Wurm Drin");
        stage.setScene(scene);
        stage.show();
        Stopmusic();
    }


    // Return back to the Menu
    public void back_to_Menu(){
        difficultyPane.setVisible(false);
        playButton.setVisible(true);
        background1.setVisible(true);
        double height = background1.getHeight(); // Get the height of the background

        // Move the first background up and fade it out
        TranslateTransition transition1 = new TranslateTransition(Duration.seconds(2), background1);
        transition1.setByY(+height);

        // Move the second background up at the same time
        TranslateTransition transition2 = new TranslateTransition(Duration.seconds(2), background2);
        transition2.setByY(+height);

        transition1.play();
        transition2.play();
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
}