package com.pharmacy.ui.controllers;

import com.pharmacy.entities.Drug;
import com.pharmacy.functionalities.DrugManager;
import com.pharmacy.functionalities.PurchaseManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class PurchaseController {

    @FXML
    private TableView<Map.Entry<Drug, Integer>> purchasesTable;

    @FXML
    private TableColumn<Map.Entry<Drug, Integer>, String> drugColumn;

    @FXML
    private TableColumn<Map.Entry<Drug, Integer>, String> quantityColumn;

    @FXML
    private TextField searchField;

    private PurchaseManager purchaseManager = new PurchaseManager();
    private DrugManager drugManager = new DrugManager();

    @FXML
    public void initialize() {
        drugColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey().getDrugName()));
        quantityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().toString()));

        loadAllPurchases();
    }

    private void loadAllPurchases() {
        purchasesTable.setItems(FXCollections.observableArrayList(purchaseManager.listAllPurchases().entrySet()));
    }

    @FXML
    private void handleAddPurchase() {
        Optional<Map.Entry<Drug, Integer>> result = showPurchaseDialog(null);
        result.ifPresent(entry -> {
            purchaseManager.addPurchase(entry.getKey(), entry.getValue());
            loadAllPurchases();
        });
    }

    @FXML
    private void handleEditPurchase() {
        Map.Entry<Drug, Integer> selectedPurchase = purchasesTable.getSelectionModel().getSelectedItem();
        if (selectedPurchase != null) {
            Optional<Map.Entry<Drug, Integer>> result = showPurchaseDialog(selectedPurchase);
            result.ifPresent(entry -> {
                purchaseManager.addPurchase(entry.getKey(), entry.getValue() - selectedPurchase.getValue());
                loadAllPurchases();
            });
        } else {
            showAlert("No Purchase Selected", "Please select a purchase to edit.");
        }
    }

    @FXML
    private void handleRemovePurchase() {
        Map.Entry<Drug, Integer> selectedPurchase = purchasesTable.getSelectionModel().getSelectedItem();
        if (selectedPurchase != null) {
            purchaseManager.addPurchase(selectedPurchase.getKey(), -selectedPurchase.getValue());
            loadAllPurchases();
        } else {
            showAlert("No Purchase Selected", "Please select a purchase to remove.");
        }
    }

    @FXML
    private void handleSearchPurchase() {
        String searchText = searchField.getText();
        if (searchText != null && !searchText.isEmpty()) {
            Optional<Drug> drug = drugManager.searchDrugByName(searchText);
            if (drug.isPresent()) {
                // Filter purchases based on the found drug
                purchasesTable.setItems(FXCollections.observableArrayList(
                        purchaseManager.listAllPurchases().entrySet().stream()
                                .filter(entry -> entry.getKey().equals(drug.get()))
                                .toList()
                ));
            } else {
                showAlert("No Drug Found", "No drug found with the given name.");
                // Optionally clear the table or reload all purchases
                loadAllPurchases();
            }
        } else {
            loadAllPurchases(); // Reload all purchases if search text is empty
        }
    }


    @FXML
    private void handleViewAllPurchases() {
        loadAllPurchases();
    }

    private Optional<Map.Entry<Drug, Integer>> showPurchaseDialog(Map.Entry<Drug, Integer> entry) {
        Dialog<Map.Entry<Drug, Integer>> dialog = new Dialog<>();
        dialog.setTitle(entry == null ? "Add Purchase" : "Edit Purchase");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        VBox vbox = new VBox(10);
        TextField drugNameField = new TextField();
        TextField quantityField = new TextField();

        if (entry != null) {
            drugNameField.setText(entry.getKey().getDrugName());
            quantityField.setText(entry.getValue().toString());
        }

        vbox.getChildren().addAll(new Label("Drug Name:"), drugNameField, new Label("Quantity:"), quantityField);
        dialog.getDialogPane().setContent(vbox);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                String drugName = drugNameField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                Optional<Drug> drug = drugManager.searchDrugByName(drugName);
                if (drug.isPresent()) {
                    return Map.entry(drug.get(), quantity);
                } else {
                    showAlert("Invalid Drug", "The drug specified does not exist.");
                }
            }
            return null;
        });

        return dialog.showAndWait();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
