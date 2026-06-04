package com.iskollect.controller;

import com.iskollect.model.RedeemResult;
import com.iskollect.model.Student;
import com.iskollect.service.RewardService;
import com.iskollect.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class RewardsController {

    @FXML private Label currentPointsLabel;
    @FXML private Button redeemSuppliesBtn;
    @FXML private Button redeemSnack1Btn;
    @FXML private Button redeemSnack2Btn;
    @FXML private Button redeemLunchBtn;
    @FXML private Label statusLabel;

    private final RewardService rewardService = new RewardService();

    private static final int SUPPLIES_ID = 1;
    private static final int SNACK_V1_ID = 2;
    private static final int SNACK_V2_ID = 3;
    private static final int LUNCH_ID    = 4;

    private static final double SUPPLIES_COST = 10;
    private static final double SNACK_V1_COST = 30;
    private static final double SNACK_V2_COST = 50;
    private static final double LUNCH_COST    = 100;

    @FXML
    public void initialize() {
        refreshPoints();
    }

    private void refreshPoints() {
        Student student = SessionManager.getSession();
        if (student == null) return;

        double points = student.getTotalPoints();
        currentPointsLabel.setText((int) points + " points");

        redeemSuppliesBtn.setDisable(points < SUPPLIES_COST);
        redeemSnack1Btn.setDisable(points < SNACK_V1_COST);
        redeemSnack2Btn.setDisable(points < SNACK_V2_COST);
        redeemLunchBtn.setDisable(points < LUNCH_COST);
    }

    @FXML
    private void handleRedeemSupplies() { redeem(SUPPLIES_ID); }

    @FXML
    private void handleRedeemSnack1() { redeem(SNACK_V1_ID); }

    @FXML
    private void handleRedeemSnack2() { redeem(SNACK_V2_ID); }

    @FXML
    private void handleRedeemLunch() { redeem(LUNCH_ID); }

    private void redeem(int rewardId) {
        Student student = SessionManager.getSession();
        if (student == null) {
            statusLabel.setText("Please log in first.");
            return;
        }
        try {
            RedeemResult result = rewardService.redeem(student.getStudentId(), rewardId);
            if (result.isSuccess()) {
                statusLabel.setText("Success! Coupon code: " + result.getCouponCode());
                student.setTotalPoints(result.getRemainingPoints());
                refreshPoints();
            } else {
                statusLabel.setText(result.getMessage());
            }
        } catch (Exception e) {
            statusLabel.setText("Redemption failed: " + e.getMessage());
        }
    }
}