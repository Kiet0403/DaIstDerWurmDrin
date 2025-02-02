package com.example.daistderwurmdrin;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class InstructionController {
    @FXML private ImageView leftPage;
    @FXML private ImageView rightPage;

    Image page1 = new Image("instructionPg1.png");
    Image page2 = new Image("instructionPg2.png");
    Image page3 = new Image("instructionPg3.png");
    Image page4 = new Image("instructionPg4.png");

    public void nextPage() {
        leftPage.setImage(page3);
        rightPage.setImage(page4);
    }

    public void prevPage() {
        leftPage.setImage(page1);
        rightPage.setImage(page2);
    }
}

