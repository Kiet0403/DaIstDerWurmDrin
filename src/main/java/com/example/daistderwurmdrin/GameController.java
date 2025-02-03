/** * OOP Java Project

 * Children Board Game Simulation:  Da ist der Wurm drin

 * Link: https://www.amazon.de/Zoch-601132100-Wurm-Kinderspiel-Jahres/dp/B004L87UQO?th=1;
 * https://www.youtube.com/watch?v=kD8JI8RpTFM;

 * @author Van Tuan Kiet Vo - 1589900

 * @author Truong Anh Tuan Nguyen - 1589760

 * @author Duy Nguyen - 1584439

 */


package com.example.daistderwurmdrin;
import java.io.File;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameController {

    // Data Fields
    Random random = new Random();
    Game game;
    StartScreenController start;
    private Rectangle selectedBooster = null;

    @FXML private Scene scene;
    @FXML private Parent root;
    @FXML private Stage stage;

    // FXML Connections
    @FXML private Media media;
    @FXML private MediaPlayer mediaPlayer;
    @FXML private MediaPlayer dieSfxPlayer;
    @FXML private MediaPlayer winSfxPlayer;

    @FXML HBox gamePane;

    @FXML ImageView dieImage;

    @FXML Button holdButton;
    @FXML Button resultButton;


    @FXML VBox p1box;
    @FXML VBox p2box;
    @FXML VBox p3box;
    @FXML VBox p4box;

    @FXML Label title;

    @FXML Label bluePieces;
    @FXML Label orangePieces;
    @FXML Label purplePieces;
    @FXML Label yellowPieces;
    @FXML Label greenPieces;
    @FXML Label redPieces;

    @FXML Rectangle p1booster1;
    @FXML Rectangle p1booster2;
    @FXML Rectangle p2booster1;
    @FXML Rectangle p2booster2;
    @FXML Rectangle p3booster1;
    @FXML Rectangle p3booster2;
    @FXML Rectangle p4booster1;
    @FXML Rectangle p4booster2;

    @FXML ImageView p1booster1Image;
    @FXML ImageView p1booster2Image;
    @FXML ImageView p2booster1Image;
    @FXML ImageView p2booster2Image;
    @FXML ImageView p3booster1Image;
    @FXML ImageView p3booster2Image;
    @FXML ImageView p4booster1Image;
    @FXML ImageView p4booster2Image;

    @FXML Rectangle checkpoint1_1;
    @FXML Rectangle checkpoint1_2;
    @FXML Rectangle checkpoint1_3;
    @FXML Rectangle checkpoint1_4;
    @FXML Rectangle checkpoint2_1;
    @FXML Rectangle checkpoint2_2;
    @FXML Rectangle checkpoint2_3;
    @FXML Rectangle checkpoint2_4;

    @FXML VBox progressBar1;
    @FXML VBox progressBar2;
    @FXML VBox progressBar3;
    @FXML VBox progressBar4;

    @FXML VBox bar1;
    @FXML VBox bar2;
    @FXML VBox bar3;
    @FXML VBox bar4;

    String[] playerNames = {"Little Gritty", "Stripy Toni", "Ruby Red", "Lady Silver"};
    String[] playerTypes;
    private String difficulty;
    private String result;
    private String winner;

    double progress1, progress2, progress3, progress4;
    Rectangle[][] checkpoints;

    private Roller clock;
    // Handle the animation of the die rolling
    private class Roller extends AnimationTimer {

        private long FRAMES_PER_SEC = 20L;
        private long INTERVAL = 1000000000L / FRAMES_PER_SEC;
        private int MAX_ROLLS = 15;

        private long last = 0;
        private int count = 0;

        @Override
        public void handle(long now) {
            if (now - last > INTERVAL) {
                int r = 1 + (int)(Math.random() * 6);
                setDieImage(r);
                last = now;
                count++;
                if (count > MAX_ROLLS) {
                    clock.stop();
                    roll();
                    count = 0;
                }
            }
        }
    }
    // Initialize by loading resources: music, sfx, images, animations.
    @FXML public void initialize() {
        //Initialize music
        String song = new File("music\\gameMusic.mp3").toURI().toString();
        media = new Media(song);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.1);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setAutoPlay(true);
        // Initialize sfx
        String dieSfx = new File("sfx/diceSfx.mp3").toURI().toString();
        Media dieSound = new Media(dieSfx);
        dieSfxPlayer = new MediaPlayer(dieSound);

        String winSfx = new File("sfx/winSfx.mp3").toURI().toString();
        Media winSound = new Media(winSfx);
        winSfxPlayer = new MediaPlayer(winSound);
        // Call objects
        start = new StartScreenController();
        clock = new Roller();
        game = new Game(playerNames, playerTypes, difficulty);
        // Update the die image
        updateViews();
        holdButton.setDisable(true);

        // Load the checkpoints
        checkpoints = new Rectangle[][] {
            {checkpoint1_1, checkpoint2_1},
            {checkpoint1_2, checkpoint2_2},
            {checkpoint1_3, checkpoint2_3},
            {checkpoint1_4, checkpoint2_4}
        };
        // Initialize the Boosters
        addBoosterEventHandlers();
        addCheckpointEventHandlers();
        bindCheckpoints();
    }
    // Display the available pieces still in the game
    private void setAvailablePieces(){
        int[] availPieces= game.getAvail();
        bluePieces.setText(Integer.toString(availPieces[0]));
        orangePieces.setText(Integer.toString(availPieces[1]));
        purplePieces.setText(Integer.toString(availPieces[2]));
        yellowPieces.setText(Integer.toString(availPieces[3]));
        greenPieces.setText(Integer.toString(availPieces[4]));
        redPieces.setText(Integer.toString(availPieces[5]));
    }
    //  Grey out boosters after usage
    private void greyOutBoosterImage(ImageView boosterImage) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(-1); // Set saturation to -1 to grey out the image
        boosterImage.setEffect(colorAdjust);
    }
    // Show notifications when a player placed a booster
    private void showNotification(String currentPlayerName, String targetPlayerName, String checkpoint) {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Booster Placed");
            alert.setHeaderText(null);
            alert.setContentText(currentPlayerName + " placed a booster on " + targetPlayerName + " at " + checkpoint);
            alert.showAndWait();
        });
    }
    // Bind the checkpoints to ensure that booster object scale with parent containers
    private void bindCheckpoints() {
        double scaleFactor = 10; // Configurable scale factor
        for (Rectangle[] checkpointPair : checkpoints) {
            for (Rectangle checkpoint : checkpointPair) {
                if (checkpoint.getParent() != null) {
                    // Bind width and height to ensure a square shape
                    checkpoint.widthProperty().bind(Bindings.createDoubleBinding(
                        () -> {
                            Bounds bounds = checkpoint.getParent().layoutBoundsProperty().get();
                            return Math.min(bounds.getWidth(), bounds.getHeight()) / scaleFactor;
                        },
                        checkpoint.getParent().layoutBoundsProperty()
                    ));

                    checkpoint.heightProperty().bind(checkpoint.widthProperty()); // Keep the square ratio
                }
            }
        }
    }
    // Add the ability to select a booster on mouse click
    private void addBoosterEventHandlers() {
        p1booster1.setOnMouseClicked(event -> selectBooster(p1booster1));
        p1booster2.setOnMouseClicked(event -> selectBooster(p1booster2));
        p2booster1.setOnMouseClicked(event -> selectBooster(p2booster1));
        p2booster2.setOnMouseClicked(event -> selectBooster(p2booster2));
        p3booster1.setOnMouseClicked(event -> selectBooster(p3booster1));
        p3booster2.setOnMouseClicked(event -> selectBooster(p3booster2));
        p4booster1.setOnMouseClicked(event -> selectBooster(p4booster1));
        p4booster2.setOnMouseClicked(event -> selectBooster(p4booster2));
    }
    // Add the ability to select a checkpoint on mouse click
    private void addCheckpointEventHandlers() {
        for (int i = 0; i < checkpoints.length; i++) {
            int playerIndex = i;
            checkpoints[i][0].setOnMouseClicked(event -> placeBoosterOnCheckpoint(playerIndex, "1"));
            checkpoints[i][1].setOnMouseClicked(event -> placeBoosterOnCheckpoint(playerIndex, "2"));
        }
    }
    // set the default visibility of checkpoints to false
    private void setCheckpointVisibility(){
        checkpoint1_1.setVisible(false);
        checkpoint1_2.setVisible(false);
        checkpoint1_3.setVisible(false);
        checkpoint1_4.setVisible(false);
        checkpoint2_1.setVisible(false);
        checkpoint2_2.setVisible(false);
        checkpoint2_3.setVisible(false);
        checkpoint2_4.setVisible(false);
    }
    // Add the ability function when selecting a booster
    private void selectBooster(Rectangle booster) {

        setCheckpointVisibility();
        // Check if the selected booster belongs to the current player
        if ((game.getCurrent() == game.getP1() && (booster == p1booster1 || booster == p1booster2)) ||
            (game.getCurrent() == game.getP2() && (booster == p2booster1 || booster == p2booster2)) ||
            (game.getCurrent() == game.getP3() && (booster == p3booster1 || booster == p3booster2)) ||
            (game.getCurrent() == game.getP4() && (booster == p4booster1 || booster == p4booster2))) {
            if (selectedBooster != null) {
                selectedBooster.setStroke(null);
            }
            selectedBooster = booster;
            selectedBooster.setStroke(Color.BLACK);
            if(booster == p1booster1||booster == p2booster1||booster == p3booster1||booster == p4booster1 ){
                checkpoint1_1.setVisible(true);
                checkpoint1_2.setVisible(true);
                checkpoint1_3.setVisible(true);
                checkpoint1_4.setVisible(true);
            }
            if(booster == p1booster2||booster == p2booster2||booster == p3booster2||booster == p4booster2){
                checkpoint2_1.setVisible(true);
                checkpoint2_2.setVisible(true);
                checkpoint2_3.setVisible(true);
                checkpoint2_4.setVisible(true);
            }
        }
    }
    // Add the methods when a booster is placed on a checkpoint
    private void placeBoosterOnCheckpoint(int targetPlayerIndex, String checkpoint) {
        if (selectedBooster == null) {
            return;
        }
        // Check if the selected booster can be placed on the selected checkpoint
        if ((checkpoint.equals("1") && (selectedBooster == p1booster1 || selectedBooster == p2booster1 || selectedBooster == p3booster1 || selectedBooster == p4booster1)) ||
            (checkpoint.equals("2") && (selectedBooster == p1booster2 || selectedBooster == p2booster2 || selectedBooster == p3booster2 || selectedBooster == p4booster2))) {
            
            selectedBooster.setFill(Color.GREY);
            selectedBooster.setDisable(true);
            placeBooster(targetPlayerIndex, checkpoint);
            selectedBooster.setStroke(null);
            selectedBooster = null;

            if (selectedBooster == p1booster1) {
                greyOutBoosterImage(p1booster1Image);
            } else if (selectedBooster == p1booster2) {
                greyOutBoosterImage(p1booster2Image);
            } else if (selectedBooster == p2booster1) {
                greyOutBoosterImage(p2booster1Image);
            } else if (selectedBooster == p2booster2) {
                greyOutBoosterImage(p2booster2Image);
            } else if (selectedBooster == p3booster1) {
                greyOutBoosterImage(p3booster1Image);
            } else if (selectedBooster == p3booster2) {
                greyOutBoosterImage(p3booster2Image);
            } else if (selectedBooster == p4booster1) {
                greyOutBoosterImage(p4booster1Image);
            } else if (selectedBooster == p4booster2) {
                greyOutBoosterImage(p4booster2Image);
            }

            // Disable boosters if the current player is a bot
            disableBotBoosters();
        } else {
            // Show an alert if the booster cannot be placed on the selected checkpoint
            showInvalidBoosterPlacementAlert();
        }
    }
    // Show an alert if a booster is placed incorrectly
    private void showInvalidBoosterPlacementAlert() {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid Booster Placement");
            alert.setHeaderText(null);
            alert.setContentText("The selected booster cannot be placed on the selected checkpoint.");
            alert.showAndWait();
        });
    }
    // Update the board state
    public void updateViews() {
        setDieImage(game.getDie().getTop());
        //Check whether any player has reached a checkpoint
        checkpoints();
        // Update the score of each player
        progress1 = ((double) game.getP1().getTotalScore() + game.START_SCORE) * 12;
        bar1.setPrefHeight(progress1);

        progress2 = ((double) game.getP2().getTotalScore() + game.START_SCORE) * 12;
        bar2.setPrefHeight(progress2);

        progress3 = ((double) game.getP3().getTotalScore() + game.START_SCORE) * 12;
        bar3.setPrefHeight(progress3);

        progress4 = ((double) game.getP4().getTotalScore() + game.START_SCORE) * 12;
        bar4.setPrefHeight(progress4);

        // Update the background of current player to be green for clarity
        if (game.getCurrent() == game.getP1()) {
            p1box.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
            p2box.setBackground(null);
            p3box.setBackground(null);
            p4box.setBackground(null);
        } else if (game.getCurrent() == game.getP2()) {
            p1box.setBackground(null);
            p2box.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
            p3box.setBackground(null);
            p4box.setBackground(null);
        } else if (game.getCurrent() == game.getP3()) {
            p1box.setBackground(null);
            p2box.setBackground(null);
            p3box.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
            p4box.setBackground(null);
        } else if (game.getCurrent() == game.getP4()) {
            p1box.setBackground(null);
            p2box.setBackground(null);
            p3box.setBackground(null);
            p4box.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
        }
        // Check if there are 0 piece left to place, resulting in a tie
        if (game.gameOverTie()){
            gamePane.setVisible(false);
            resultButton.setVisible(true);
            setResult("tie");
        }
        //Check if a player has won the game
        if (game.gameOver()){
            gamePane.setVisible(false);
            resultButton.setVisible(true);
            setResult("win");
            setWinner(game.getCurrent().getName());
        }
        // Update the availability of the game pieces
        setAvailablePieces();
    }
    // Set dice image corresponding to the die value
    public void setDieImage(int top) {
        // File f = new File("src/main/resources/Dice" + top + ".png");
        
        File f = new File("src\\main\\resources\\Dice" + top + ".png");
        dieImage.setImage(new Image(f.toURI().toString()));
    }
    // Die rolling animation
    public void rollAnimation() {
        clock.start();
        //Play sfx
        dieSfxPlayer.seek(dieSfxPlayer.getStartTime()); // Reset to start
        dieSfxPlayer.play();
    }
    // Roll the die and record the value
    public void roll() {
        game.roll();
        dieImage.setDisable(true);
        holdButton.setDisable(false);
        updateViews();
    }
    // Pass the turn
    public void hold() {
        game.hold();
        dieImage.setDisable(false);
        holdButton.setDisable(true);
        updateViews();
        setCheckpointVisibility();
        checkBots();
    }

    public void checkpoints(){
        game.checkProgress();
    }

    public void placeBooster(int targetPlayerIndex, String checkpoint) {
        Player currentPlayer = game.getCurrent();
        Player targetPlayer = game.getTargetPlayer(targetPlayerIndex);
        game.gamePlaceBooster(currentPlayer, targetPlayer, checkpoint);
        String message = currentPlayer.getName() + " placed a booster on " + targetPlayer.getName() + " at " + checkpoint;
        System.out.println(message);
        showNotification(currentPlayer.getName(), targetPlayer.getName(), checkpoint);
        updateViews();
    }

    public void checkBots() {
        //Check if the current player is a bot
        if (game.getCurrent() instanceof Bot) {

            //Roll the dice
            rollAnimation();

            PauseTransition pause = new PauseTransition(Duration.millis(975)); // Action delay to diasble the hold button
            pause.setOnFinished(event -> {holdButton.setDisable(true);});
            pause.play();

            //Betting algorithm based on difficulty
            switch (difficulty){
                // Easy case: The bot just bet on random people
                case "Easy": {
                    if (game.checkNumBooster()){
                        int randomTargetCheckpoint1 = random.nextInt(3);
                        int randomTargetCheckpoint2 = random.nextInt(3);
                        
                        placeBooster(randomTargetCheckpoint1, "1");
                        placeBooster(randomTargetCheckpoint2, "2");
                        System.out.println("Placed in 1 and 2 easy mode");
                    }
                }
                    break;

                // Medium case: the bot bets on itself
                case "Medium": {
                    if (game.checkNumBooster()) {
                        placeBooster(game.getCurrentPlayerIndex(), "1");
                        placeBooster(game.getCurrentPlayerIndex(), "2");
                        System.out.println("Placed in 1 and 2 medium mode");
                    }
                    break;
                }

                // Hard case: the bot uses an optimization algorithm for betting
                case "Hard": {
                    if (game.checkNumBooster()) {
                        game.OptimizeBoosters1();
                        game.OptimizeBoosters2();
                    }
                    break;
                }
            }
            // Disable boosters if the current player is a bot
            disableBotBoosters();

            //End the turn and pass to the next player
            PauseTransition pause10 = new PauseTransition(Duration.seconds(4)); // Delay of 4 second between each bots round for player to process
            pause10.setOnFinished(Event -> {
                hold();});
            pause10.play();
        
        }
    }
    
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
    public void setPlayerTypes(String[] playerTypes) {
        this.playerTypes = playerTypes;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public void setWinner(String winner) {
        this.winner = winner;
    }

    public void disableBotBoosters() {
        if (game.getCurrent() instanceof Bot) {
            if (game.getCurrent() == game.getP1()) {
                p1booster1.setDisable(true);
                p1booster1.setFill(Color.GREY);
                p1booster2.setDisable(true);
                p1booster2.setFill(Color.GREY);
            } else if (game.getCurrent() == game.getP2()) {
                p2booster1.setDisable(true);
                p2booster1.setFill(Color.GREY);
                p2booster2.setDisable(true);
                p2booster2.setFill(Color.GREY);
            } else if (game.getCurrent() == game.getP3()) {
                p3booster1.setDisable(true);
                p3booster1.setFill(Color.GREY);
                p3booster2.setDisable(true);
                p3booster2.setFill(Color.GREY);
            } else if (game.getCurrent() == game.getP4()) {
                p4booster1.setDisable(true);
                p4booster1.setFill(Color.GREY);
                p4booster2.setDisable(true);
                p4booster2.setFill(Color.GREY);
            }
        }
    }
    public void showEndingScene(ActionEvent event) throws Exception {
        winSfxPlayer.seek(winSfxPlayer.getStartTime()); // Reset to start
        winSfxPlayer.play();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ending_scene.fxml"));
        loader.setControllerFactory(param -> {
            EndSceneController endScene = new EndSceneController();
            endScene.setResult(result);
            endScene.setWinner(winner);
            return endScene;
        });
        root = loader.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("Da ist der Wurm Drin");
        stage.setScene(scene);
        stage.show();
        mediaPlayer.stop();
    }
}
