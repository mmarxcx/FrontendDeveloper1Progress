package com.iskollect.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class BottleRecordsController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private void openAddBottle() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/iskollect/fxml/submitbottlepopup.fxml")
            );
            Parent root = loader.load();

            Stage popupStage = new Stage();
            popupStage.initOwner(rootPane.getScene().getWindow());
            popupStage.setScene(new Scene(root));
            popupStage.setTitle("Submit Bottles");
            popupStage.show();
        } catch (IOException e) {
            System.err.println("Could not open bottle popup: " + e.getMessage());
        }
    }

    @FXML
    private void goToDashboard() {
        loadScreen("dashboard.fxml");
    }

    @FXML
    private void goToRewardsCatalog() {
        loadScreen("rewardsCatalog.fxml");
    }

    @FXML
    private void goToTransactionHistory() {
        loadScreen("transactionhistory.fxml");
    }

    @FXML
    private void goToProfile() {
        loadScreen("profile.fxml");
    }

    private void loadScreen(String fxmlFile) {
        try {
            Stage stage = (Stage) rootPane.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/iskollect/fxml/" + fxmlFile)
            );
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            System.err.println("Navigation error: " + e.getMessage());
        }
    }
}