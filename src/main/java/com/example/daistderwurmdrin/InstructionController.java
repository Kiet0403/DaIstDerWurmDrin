/** * OOP Java Project

 * Children Board Game Simulation:  Da ist der Wurm drin

 * Link: https://www.amazon.de/Zoch-601132100-Wurm-Kinderspiel-Jahres/dp/B004L87UQO?th=1;
 * https://www.youtube.com/watch?v=kD8JI8RpTFM;

 * @author Truong Anh Tuan Nguyen - 1589760

 */


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
    //Move to next page of the instruction
    public void nextPage() {
        leftPage.setImage(page3);
        rightPage.setImage(page4);
    }
    //Move to previous page of the instruction
    public void prevPage() {
        leftPage.setImage(page1);
        rightPage.setImage(page2);
    }
}

