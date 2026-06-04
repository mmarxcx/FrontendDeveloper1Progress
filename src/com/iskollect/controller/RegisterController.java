package com.iskollect.controller;

import com.iskollect.exception.DatabaseException;
import com.iskollect.exception.InvalidInputException;
import com.iskollect.model.Student;
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

public class RegisterController {

    @FXML private TextField nameField;
    @FXML private TextField webmailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    private final AuthService authService = new AuthService();

    @FXML
    private void handleSignUp() {
        errorLabel.setText("");
        if (nameField.getText().isBlank() || webmailField.getText().isBlank() || passwordField.getText().isBlank()) {
            errorLabel.setText("Please fill in all fields.");
            return;
        }
        if (!webmailField.getText().endsWith("@iskolarngbayan.pup.edu.ph")) {
            errorLabel.setText("Please use your PUP webmail address.");
            return;
        }
        try {
            Student student = new Student(
                    nameField.getText(),
                    webmailField.getText(),
                    passwordField.getText()
            );
            boolean success = authService.register(student);
            if (success) {
                goToLogin();
            }
        } catch (InvalidInputException | DatabaseException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void goToLogin() {
        try {
            Stage stage = (Stage) nameField.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/iskollect/fxml/login.fxml")
            );
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            errorLabel.setText("Could not load login screen.");
        }
    }
}