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

public class PigController {

    // Data Fields
    Game pig;

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

    @FXML ProgressBar progressBar1;

    @FXML ProgressBar progressBar2;

    @FXML ProgressBar progressBar3;

    @FXML ProgressBar progressBar4;

    double progress1, progress2, progress3, progress4;

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
        //dieImage.setImage(new Image("pig/resources/Dice" + top + ".png"));
        File f = new File("groupProject/DaIstDerWurmDrin/src/main/resources/Dice" + top + ".png");
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
}
