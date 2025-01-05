package com.example.daistderwurmdrin;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class BoosterController {

    @FXML private ComboBox<String> targetPlayerComboBox;
    @FXML private ComboBox<Integer> checkpointComboBox;

    private PigController pigController;

    public void setPigController(PigController pigController) {
        this.pigController = pigController;
    }

    @FXML
    public void initialize() {
        targetPlayerComboBox.getItems().addAll("Player 1", "Player 2", "Player 3", "Player 4");
        checkpointComboBox.getItems().addAll(1, 2);
    }

    @FXML
    private void placeBooster() {
        String targetPlayer = targetPlayerComboBox.getValue();
        int checkpoint = checkpointComboBox.getValue();

        int targetPlayerIndex = -1;
        switch (targetPlayer) {
            case "Player 1":
                targetPlayerIndex = 0;
                break;
            case "Player 2":
                targetPlayerIndex = 1;
                break;
            case "Player 3":
                targetPlayerIndex = 2;
                break;
            case "Player 4":
                targetPlayerIndex = 3;
                break;
        }

        if (targetPlayerIndex != -1 && checkpoint > 0) {
            pigController.placeBooster(targetPlayerIndex, checkpoint);
            Stage stage = (Stage) targetPlayerComboBox.getScene().getWindow();
            stage.close();
        }
    }
}