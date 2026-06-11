package com.iskollect.controller;

import com.iskollect.model.Student;
import com.iskollect.service.BadgeService;
import com.iskollect.service.PointsService;
import com.iskollect.service.StreakService;
import com.iskollect.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class DashboardController {
    @FXML private Label nameLabel;
    @FXML private Label pointsLabel;
    @FXML private Label badgeLabel;
    @FXML private Label streakLabel;

    private final PointsService pointsService = new PointsService();
    private final BadgeService badgeService = new BadgeService();
    private final StreakService streakService = new StreakService();

    @FXML
    public void initialize() {
        refresh();
    }

    @FXML
    public void refresh() {
        Student student = SessionManager.getSession();
        if (student == null) {
            setText(nameLabel, "No active session");
            return;
        }
        int studentId = student.getStudentId();
        setText(nameLabel, student.getUsername());
        setText(pointsLabel, String.valueOf(pointsService.getTotalPoints(studentId)));
        setText(badgeLabel, badgeService.getCurrentBadge(studentId).getTierName());
        setText(streakLabel, String.valueOf(streakService.getStreakCount(studentId)));
    }

    private void setText(Label label, String text) {
        if (label != null) {
            label.setText(text);
        }
    }
    @FXML
    private void openAddBottle() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/iskollect/fxml/submitbottlepopup.fxml")
            );
            Parent root = loader.load();
            Stage popupStage = new Stage();
            popupStage.setScene(new Scene(root));
            popupStage.setTitle("Submit Bottles");
            popupStage.show();
        } catch (IOException e) {
            System.err.println("Could not open bottle popup: " + e.getMessage());
        }
    }

    @FXML
    private void goToBottleRecords() { loadScreen("bottlerecords.fxml"); }

    @FXML
    private void goToRewardsCatalog() { loadScreen("rewardsCatalog.fxml"); }

    @FXML
    private void goToTransactionHistory() { loadScreen("transactionhistory.fxml"); }

    @FXML
    private void goToProfile() { loadScreen("profile.fxml"); }

    @FXML
    private void handleLogout() {
        try {
            new com.iskollect.service.AuthService().logout();
        } catch (com.iskollect.exception.DatabaseException e) {
            System.err.println("Logout error: " + e.getMessage());
        }
        Stage stage = (Stage) nameLabel.getScene().getWindow();
        com.iskollect.util.RedirectUtil.redirectToLogin(stage);
    }

    private void loadScreen(String fxmlFile) {
        try {
            Stage stage = (Stage) nameLabel.getScene().getWindow();
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
