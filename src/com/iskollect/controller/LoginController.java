package com.iskollect.controller;

import com.iskollect.exception.DatabaseException;
import com.iskollect.exception.InvalidInputException;
import com.iskollect.service.AuthService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    @FXML private TextField webmailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    private final AuthService authService = new AuthService();

    @FXML
    private void handleLogin() {
        errorLabel.setText("");
        if (webmailField.getText().isBlank() || passwordField.getText().isBlank()) {
            errorLabel.setText("Please fill in all fields.");
            return;
        }
        if (!webmailField.getText().endsWith("@iskolarngbayan.pup.edu.ph")) {
            errorLabel.setText("Please use your PUP webmail address.");
            return;
        }
        try {
            boolean success = authService.login(
                    webmailField.getText(),
                    passwordField.getText()
            );
            if (success) {
                loadScreen("dashboard.fxml");
            } else {
                errorLabel.setText("Incorrect webmail or password.");
            }
        } catch (InvalidInputException | DatabaseException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void goToRegister() {
        loadScreen("signup.fxml");
    }

    @FXML
    private void goToForgotPassword() {
        loadScreen("forgotpass.fxml");
    }

    private void loadScreen(String fxmlFile) {
        try {
            Stage stage = (Stage) webmailField.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/iskollect/fxml/" + fxmlFile)
            );
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            errorLabel.setText("Could not load screen: " + fxmlFile);
        }
    }
}