package com.pharmacy.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {

    @FXML
    private Label mainLabel;

    @FXML
    private void handleDashboard() {
        mainLabel.setText("Dashboard View");
    }

    @FXML
    private void handleManageDrugs() {
        mainLabel.setText("Manage Drugs View");
    }

    @FXML
    private void handleManageSuppliers() {
        mainLabel.setText("Manage Suppliers View");
    }

    @FXML
    private void handlePurchase() {
        mainLabel.setText("Purchase View");
    }
}
