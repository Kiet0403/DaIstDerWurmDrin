/** * OOP Java Project

 * Children Board Game Simulation:  Da ist der Wurm drin

 * Link: https://www.amazon.de/Zoch-601132100-Wurm-Kinderspiel-Jahres/dp/B004L87UQO?th=1;
 * https://www.youtube.com/watch?v=kD8JI8RpTFM;

 * @author Truong Anh Tuan Nguyen - 1589760

 */


package com.example.daistderwurmdrin;

import java.io.File;
import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoadingScreenController {

    @FXML private ImageView coloredTitle;
    @FXML private ImageView greyOutTitle;

    @FXML private Circle transitionMask;

    @FXML private Scene scene;
    @FXML private Parent root;
    @FXML private Stage stage;

    @FXML private MediaPlayer finishLoadingSfxPlayer;
    // Initialize by loading the resources: sfx, rectangle, animations.
    public void initialize() {
        String finishLoadingSfx = new File("sfx/finishLoadingSfx.mp3").toURI().toString();
        Media finishLoadingSound = new Media(finishLoadingSfx);
        finishLoadingSfxPlayer = new MediaPlayer(finishLoadingSound);

        Rectangle mask = new Rectangle(0, 0, 0, 285); // Initially 0 width
        coloredTitle.setClip(mask);

        Timeline loadingAnimation = new Timeline(
                new KeyFrame(Duration.seconds(5), // Duration of the animation
                        new KeyValue(mask.widthProperty(), 700)) // Animate to full width
        );

        Timeline transition = new Timeline(
                new KeyFrame(Duration.seconds(1.2),
                        new KeyValue(transitionMask.opacityProperty(), 1),  // Fade in transitionMask
                        new KeyValue(transitionMask.radiusProperty(), 1080)) // Increase its radius
        );

        //Start the animation
        loadingAnimation.play();
        loadingAnimation.setOnFinished(event -> {
            //Play sfx
            finishLoadingSfxPlayer.play();
            //Start transition
            transition.play();
        });
        transition.setOnFinished(event -> {transitionIntoNextScene();});
    }
    // Moving to next scene after finish animation
    public void transitionIntoNextScene() {
        try {
            root = FXMLLoader.load(Application.class.getResource("menuScreen.fxml"));

            stage = (Stage) transitionMask.getScene().getWindow();

            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
