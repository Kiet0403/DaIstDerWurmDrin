package com.example.daistderwurmdrin;

import java.io.File;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
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
import javafx.scene.shape.Rectangle;

public class PigController{

    // Data Fields
    Game pig;
    private Rectangle selectedBooster = null;

    // FXML Connections
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

    @FXML ProgressBar progressBar1;

    @FXML ProgressBar progressBar2;

    @FXML ProgressBar progressBar3;

    @FXML ProgressBar progressBar4;

    ProgressBar[] progressBars = new ProgressBar[4];

    double progress1, progress2, progress3, progress4;
    Rectangle[][] checkpoints;

    private Roller clock;
    private class Roller extends AnimationTimer {

        private long FRAMES_PER_SEC = 50L;
        private long INTERVAL = 1000000000L / FRAMES_PER_SEC;
        private int MAX_ROLLS = 20;

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
        clock = new Roller();
        pig = new Game("Player 1", "Player 2", "Player 3", "Player 4");
        updateViews();

        progressBar1.setStyle("-fx-accent: red;");
        progressBar2.setStyle("-fx-accent: green;");
        progressBar3.setStyle("-fx-accent: blue;");
        progressBar4.setStyle("-fx-accent: yellow;");

        holdButton.setDisable(true);
        progressBars = new ProgressBar[]{progressBar1, progressBar2, progressBar3, progressBar4};
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

        checkpoints();

        progress1 = (double) pig.getP1().getTotalScore() / 64;
        progressBar1.setProgress(progress1);

        progress2 = (double) pig.getP2().getTotalScore() / 64;
        progressBar2.setProgress(progress2);

        progress3 = (double) pig.getP3().getTotalScore() / 64;
        progressBar3.setProgress(progress3);

        progress4 = (double) pig.getP4().getTotalScore() / 64;
        progressBar4.setProgress(progress4);

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
    }

    public void setDieImage(int top) {
        // File f = new File("src/main/resources/Dice" + top + ".png");
        File f = new File("DaIstDerWurmDrin\\src\\main\\resources\\Dice" + top + ".png");
        dieImage.setImage(new Image(f.toURI().toString()));
    }

    public void rollAnimation() {
        clock.start();
    }

    public void roll() {
        pig.roll();
        dieImage.setDisable(true);
        holdButton.setDisable(false);
        updateViews();
    }

    public void hold() {
        pig.hold();
        dieImage.setDisable(false);
        holdButton.setDisable(true);
        updateViews();
    }

    public void checkpoints(){
        pig.checkProgress();
    }

    public void placeBooster(int targetPlayerIndex, String checkpoint) {
        pig.gamePlaceBooster(pig.getCurrent(), pig.getTargetPlayer(targetPlayerIndex), checkpoint);
        System.out.println("Booster placed by " + pig.getCurrent().getName() + " on Player " + (targetPlayerIndex + 1) + " at checkpoint " + checkpoint);
        updateViews();
    }
}
