package com.example.daistderwurmdrin;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class BoosterController {

    @FXML private ComboBox<String> targetPlayerComboBox;
    @FXML private ComboBox<String> checkpointComboBox;
    @FXML private Button placeBoosterButton;

    private PigController pigController;

    public void setPigController(PigController pigController) {
        this.pigController = pigController;
        initializeCheckpointComboBox();
    }

    @FXML
    public void initialize() {
        targetPlayerComboBox.getItems().addAll("Player 1", "Player 2", "Player 3", "Player 4");
    }

    private void initializeCheckpointComboBox(){
        // checkpointComboBox.getItems().clear();
        if (pigController.pig.getCurrent().hasUsedCheckpoint("1")) {
            checkpointComboBox.getItems().add("1 (No longer available)");
        } else {
            checkpointComboBox.getItems().add("1");
        }
        if (pigController.pig.getCurrent().hasUsedCheckpoint("2")) {
            checkpointComboBox.getItems().add("2 (No longer available)");
        } else {
            checkpointComboBox.getItems().add("2");
        }       
    }

    @FXML
    private void placeBooster() {
        String targetPlayer = targetPlayerComboBox.getValue();
        String checkpointText = checkpointComboBox.getValue();
        String checkpoint = checkpointText.substring(0,1);

        if (pigController.pig.getCurrent().hasUsedCheckpoint(checkpoint)) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Checkpoint Already Used");
            alert.setHeaderText(null);
            alert.setContentText("You can only place one booster at each checkpoint.");
            alert.showAndWait();
            return;
        }

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

        if (targetPlayerIndex != -1 && Integer.parseInt(checkpoint) > 0) {
            pigController.placeBooster(targetPlayerIndex, checkpoint);
            Stage stage = (Stage) targetPlayerComboBox.getScene().getWindow();
            stage.close();
        }
    }

    private void updateBoosterButton() {
        if (pigController.pig.checkNumBooster()) {
            placeBoosterButton.setDisable(false);
        } else {
            placeBoosterButton.setDisable(true);
        }
    }
}