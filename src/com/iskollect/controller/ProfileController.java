package com.iskollect.controller;

import com.iskollect.dao.StudentDAO;
import com.iskollect.exception.DatabaseException;
import com.iskollect.model.Student;
import com.iskollect.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ProfileController {
    @FXML private TextField nameField;
    @FXML private Label statusLabel;

    private final StudentDAO studentDAO = new StudentDAO();

    @FXML
    public void initialize() {
        Student student = SessionManager.getSession();
        if (student == null) {
            return;
        }
        setField(nameField, student.getUsername());
    }

    @FXML
    public void saveProfile() {
        Student student = SessionManager.getSession();
        if (student == null) {
            setStatus("Please log in first.");
            return;
        }
        try {
            studentDAO.updateProfile(student.getStudentId(), nameField.getText().trim());
            student.setUsername(nameField.getText().trim());
            setStatus("Profile updated.");
        } catch (NumberFormatException e) {
            setStatus("Year level must be a whole number.");
        } catch (DatabaseException e) {
            setStatus("Could not update profile: " + e.getMessage());
        }
    }

    private void setField(TextField field, String value) {
        if (field != null) {
            field.setText(value);
        }
    }

    private void setStatus(String message) {
        if (statusLabel != null) {
            statusLabel.setText(message);
        }
    }
}
