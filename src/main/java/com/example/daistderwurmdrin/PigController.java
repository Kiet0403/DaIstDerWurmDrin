package com.example.daistderwurmdrin;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;


public class PigController {

    // Data Fields
    Random random = new Random();
    Game pig;
    HelloController hello;

    // FXML Connections
    @FXML ImageView dieImage;

    @FXML Button holdButton;

    @FXML Button placeBoosterButton;

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

    @FXML VBox progressBar1;
    @FXML VBox progressBar2;
    @FXML VBox progressBar3;
    @FXML VBox progressBar4;

    @FXML VBox bar1;
    @FXML VBox bar2;
    @FXML VBox bar3;
    @FXML VBox bar4;

    String[] playerNames = {"Alice", "Bob", "Charlie", "Diana"};
    String[] playerTypes = {"human", "bot", "bot", "bot"};
    private String difficulty;

    double progress1, progress2, progress3, progress4;

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
        hello = new HelloController();
        clock = new Roller();
        pig = new Game(playerNames, playerTypes, difficulty);

        updateViews();
        holdButton.setDisable(true);
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
            placeBoosterButton.setDisable(true);
            dieImage.setDisable(true);
            System.out.println("Tie");
        }
        //Check if a player has won the game
        if (pig.gameOver()){
            holdButton.setDisable(true);
            placeBoosterButton.setDisable(true);
            dieImage.setDisable(true);
        }
    }
    // Ste dice image corresponding to the die value
    public void setDieImage(int top) {
        File f = new File("src/main/resources/Dice" + top + ".png");
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

    @FXML
    private void showBoosterWindow() {
        if (pig.checkNumBooster()){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("BoosterWindow.fxml"));
                Parent root = loader.load();

                BoosterController boosterController = loader.getController();
                boosterController.setPigController(this);

                Stage stage = new Stage();
                stage.setTitle("Place Booster");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("No Boosters Left");
            alert.setHeaderText(null);
            alert.setContentText("You have run out of boosters.");
            alert.showAndWait();            
        }
    }

    private void updateBoosterButton() {
        if (pig.checkNumBooster()) {
            placeBoosterButton.setDisable(false);
        } else {
            placeBoosterButton.setDisable(true);
        }
    }
    //Place booster on a targeted player at a targeted checkpoint
    public void placeBooster(int targetPlayerIndex, String checkpoint) {
        pig.gamePlaceBooster(pig.getCurrent(), pig.getTargetPlayer(targetPlayerIndex), checkpoint);
        updateViews();
    }

    public void checkBots() {
        //Check if the current player is a bot
        if (pig.getCurrent() instanceof bot) {
            // The delay is needed to space out the actions, otherwise the code will not process the command in next iterations
            PauseTransition pause1 = new PauseTransition(Duration.millis(1));
            pause1.setOnFinished(event -> {placeBoosterButton.setDisable(true);});
            pause1.play();

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
                        int dumbbot1 = random.nextInt(3);
                        int dumbbot2 = random.nextInt(3);
                        pig.gamePlaceBooster(pig.getCurrent(), pig.getTargetPlayer(dumbbot1), "1");
                        pig.gamePlaceBooster(pig.getCurrent(), pig.getTargetPlayer(dumbbot2), "2");
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
            }
            //End the turn and pass to the next player
            PauseTransition pause = new PauseTransition(Duration.seconds(3)); // Delay of 3 second between each bots round for player to process
            pause.setOnFinished(event -> {
                hold();
                placeBoosterButton.setDisable(false);});
            pause.play();
        }
    }
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
