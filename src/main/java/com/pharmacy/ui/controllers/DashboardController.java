package com.pharmacy.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DashboardController {

    @FXML
    private BorderPane rootLayout;

    // Map to store loaded views
    private Map<String, Node> loadedViews = new HashMap<>();

    @FXML
    private void handleDashboardButtonAction() {
        loadView("/com/pharmacy/views/Dashboard.fxml");
    }

    @FXML
    private void handleProductsButtonAction() {
        loadView("/com/pharmacy/views/Drugs.fxml");
    }

    @FXML
    private void handleCustomersButtonAction() {
        loadView("/com/pharmacy/views/Customer.fxml");
    }

    @FXML
    private void handleReportsButtonAction() {
        loadView("/com/pharmacy/views/Purchase.fxml");
    }

    @FXML
    private void handleSuppliersButtonAction() {
        loadView("/com/pharmacy/views/supplier.fxml");
    }

    private void loadView(String fxmlPath) {
        try {
            // Check if the view is already loaded
            if (loadedViews.containsKey(fxmlPath)) {
                rootLayout.setCenter(loadedViews.get(fxmlPath));
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Node view = loader.load();
                loadedViews.put(fxmlPath, view); // Store the loaded view
                rootLayout.setCenter(view);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
