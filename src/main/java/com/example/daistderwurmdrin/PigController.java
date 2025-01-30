package com.example.daistderwurmdrin;

import java.io.File;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class PigController{

    // Data Fields
    Random random = new Random();
    Game pig;
    HelloController hello;
    private Rectangle selectedBooster = null;

    // FXML Connections
    @FXML private Media media;
    @FXML private MediaPlayer mediaPlayer;

    @FXML ImageView dieImage;

    @FXML Button holdButton;

    @FXML TextField p1turn;
    @FXML TextField p2turn;
    @FXML TextField p3turn;
    @FXML TextField p4turn;
    @FXML TextField p1total;
    @FXML TextField p2total;
    @FXML TextField p3total;
    @FXML TextField p4total;

    @FXML VBox p1box;
    @FXML VBox p2box;
    @FXML VBox p3box;
    @FXML VBox p4box;

    @FXML Label title;

    @FXML Rectangle p1booster1;
    @FXML Rectangle p1booster2;
    @FXML Rectangle p2booster1;
    @FXML Rectangle p2booster2;
    @FXML Rectangle p3booster1;
    @FXML Rectangle p3booster2;
    @FXML Rectangle p4booster1;
    @FXML Rectangle p4booster2;

    @FXML Rectangle checkpoint1_1;
    @FXML Rectangle checkpoint1_2;
    @FXML Rectangle checkpoint2_1;
    @FXML Rectangle checkpoint2_2;
    @FXML Rectangle checkpoint3_1;
    @FXML Rectangle checkpoint3_2;
    @FXML Rectangle checkpoint4_1;
    @FXML Rectangle checkpoint4_2;

    @FXML VBox progressBar1;
    @FXML VBox progressBar2;
    @FXML VBox progressBar3;
    @FXML VBox progressBar4;

    @FXML VBox bar1;
    @FXML VBox bar2;
    @FXML VBox bar3;
    @FXML VBox bar4;

    String[] playerNames = {"Alice", "Bob", "Charlie", "Diana"};
    String[] playerTypes = {"human","bot","bot","bot"};
    private String difficulty;

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
                int r = 2 + (int)(Math.random() * 5);
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

    @FXML
    public void initialize() {

        String song = new File("music\\girls-frontline-shattered-connexion-ed-connexion.mp3").toURI().toString();
        media = new Media(song);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.1);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setAutoPlay(true);

        hello = new HelloController();
        clock = new Roller();
        pig = new Game(playerNames, playerTypes, difficulty);

        updateViews();
        holdButton.setDisable(true);
        checkpoints = new Rectangle[][] {
            {checkpoint1_1, checkpoint1_2},
            {checkpoint2_1, checkpoint2_2},
            {checkpoint3_1, checkpoint3_2},
            {checkpoint4_1, checkpoint4_2}
        };
        
        addBoosterEventHandlers();
        addCheckpointEventHandlers();
    }

    
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

    private void addCheckpointEventHandlers() {
        for (int i = 0; i < checkpoints.length; i++) {
            int playerIndex = i;
            checkpoints[i][0].setOnMouseClicked(event -> placeBoosterOnCheckpoint(playerIndex, "1"));
            checkpoints[i][1].setOnMouseClicked(event -> placeBoosterOnCheckpoint(playerIndex, "2"));
        }
    }

    private void selectBooster(Rectangle booster) {
        // Check if the selected booster belongs to the current player
        if ((pig.getCurrent() == pig.getP1() && (booster == p1booster1 || booster == p1booster2)) ||
            (pig.getCurrent() == pig.getP2() && (booster == p2booster1 || booster == p2booster2)) ||
            (pig.getCurrent() == pig.getP3() && (booster == p3booster1 || booster == p3booster2)) ||
            (pig.getCurrent() == pig.getP4() && (booster == p4booster1 || booster == p4booster2))) {
            if (selectedBooster != null) {
                selectedBooster.setStroke(null);
            }
            selectedBooster = booster;
            selectedBooster.setStroke(Color.BLACK);
        }
    }

    private void placeBoosterOnCheckpoint(int targetPlayerIndex, String checkpoint) {
        if (selectedBooster == null) {
            return;
        }
        selectedBooster.setFill(Color.GREY);
        selectedBooster.setDisable(true);
        placeBooster(targetPlayerIndex, checkpoint);
        selectedBooster.setStroke(null);
        selectedBooster = null;
    }

    public void updateViews() {
        setDieImage(pig.getDie().getTop());

        p1turn.setText("" + pig.getP1().getTurnScore());
        p1total.setText("" + pig.getP1().getTotalScore());
        p2turn.setText("" + pig.getP2().getTurnScore());
        p2total.setText("" + pig.getP2().getTotalScore());
        p3turn.setText("" + pig.getP3().getTurnScore());
        p3total.setText("" + pig.getP3().getTotalScore());
        p4turn.setText("" + pig.getP4().getTurnScore());
        p4total.setText("" + pig.getP4().getTotalScore());
        //Check whether any player has reached a checkpoint
        checkpoints();
        // Update the score of each player
        progress1 = (double) pig.getP1().getTotalScore() * 10;
        bar1.setPrefHeight(progress1);

        progress2 = (double) pig.getP2().getTotalScore() * 10;
        bar2.setPrefHeight(progress2);

        progress3 = (double) pig.getP3().getTotalScore() * 10;
        bar3.setPrefHeight(progress3);

        progress4 = (double) pig.getP4().getTotalScore() * 10;
        bar4.setPrefHeight(progress4);

        // Update the background of current player to be green for clarity
        if (pig.getCurrent() == pig.getP1()) {
            p1box.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
            p2box.setBackground(null);
            p3box.setBackground(null);
            p4box.setBackground(null);
        } else if (pig.getCurrent() == pig.getP2()) {
            p1box.setBackground(null);
            p2box.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
            p3box.setBackground(null);
            p4box.setBackground(null);
        } else if (pig.getCurrent() == pig.getP3()) {
            p1box.setBackground(null);
            p2box.setBackground(null);
            p3box.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
            p4box.setBackground(null);
        } else if (pig.getCurrent() == pig.getP4()) {
            p1box.setBackground(null);
            p2box.setBackground(null);
            p3box.setBackground(null);
            p4box.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
        }
        // Check if there are 0 piece left to place, resulting in a tie
        if (pig.gameOverTie()){
            holdButton.setDisable(true);
            dieImage.setDisable(true);
            System.out.println("Tie");
        }
        //Check if a player has won the game
        if (pig.gameOver()){
            holdButton.setDisable(true);
            dieImage.setDisable(true);
        }
    }
    // Ste dice image corresponding to the die value
    public void setDieImage(int top) {
        // File f = new File("src/main/resources/Dice" + top + ".png");
        
        File f = new File("src\\main\\resources\\Dice" + top + ".png");
        dieImage.setImage(new Image(f.toURI().toString()));
    }
    // Die rolling animation
    public void rollAnimation() {
        clock.start();
    }
    // Roll the die and record the value
    public void roll() {
        pig.roll();
        dieImage.setDisable(true);
        holdButton.setDisable(false);
        updateViews();
    }
    // Pass the turn
    public void hold() {
        pig.hold();
        dieImage.setDisable(false);
        holdButton.setDisable(true);
        updateViews();
        checkBots();
    }

    public void checkpoints(){
        pig.checkProgress();
    }

    public void placeBooster(int targetPlayerIndex, String checkpoint) {
        pig.gamePlaceBooster(pig.getCurrent(), pig.getTargetPlayer(targetPlayerIndex), checkpoint);
        System.out.println("Booster placed by " + pig.getCurrent().getName() + " on Player " + (targetPlayerIndex + 1) + " at checkpoint " + checkpoint);
        updateViews();
    }

    public void checkBots() {
        //Check if the current player is a bot
        if (pig.getCurrent() instanceof bot) {

            //Roll the dice
            rollAnimation();

            PauseTransition pause2 = new PauseTransition(Duration.millis(975)); // Action delay to diasble the hold button
            pause2.setOnFinished(event -> {holdButton.setDisable(true);});
            pause2.play();

            //Betting algorithm based on difficulty
            switch (difficulty){
                // Easy case: The bot just bet on random people
                case "Easy": {
                    if (pig.checkNumBooster()){
                        int RandomTargetCheckpoint1 = random.nextInt(3);
                        int RandomTargetCheckpoint2 = random.nextInt(3);
                        pig.gamePlaceBooster(pig.getCurrent(), pig.getTargetPlayer(RandomTargetCheckpoint1), "1");
                        pig.gamePlaceBooster(pig.getCurrent(), pig.getTargetPlayer(RandomTargetCheckpoint2), "2");
                        System.out.println("Placed in 1 and 2 easy mode");
                    }
                }
                    break;
                // Medium case: the bot bets on itself
                case "Medium": {
                    if (pig.checkNumBooster()) {
                        pig.gamePlaceBooster(pig.getCurrent(), pig.getCurrent(), "1");
                        pig.gamePlaceBooster(pig.getCurrent(), pig.getCurrent(), "2");
                        System.out.println("Placed in 1 and 2 medium mode");
                    }
                    break;
                }
                // Hard case: the bot uses an optimization algorithm for betting
                case "Hard": {
                    if (pig.checkNumBooster()) {
                        pig.OptimizeBoosters1();
                        pig.OptimizeBoosters2();
                    }
                    break;
                }
                default:
                    break;
            }
            //End the turn and pass to the next player
            PauseTransition pause = new PauseTransition(Duration.seconds(3)); // Delay of 3 second between each bots round for player to process
            pause.setOnFinished(event -> {
                hold();});
            pause.play();
        }
    }
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
    public void setPlayerTypes(String[] playerTypes) {
        this.playerTypes = playerTypes;
    }
}
