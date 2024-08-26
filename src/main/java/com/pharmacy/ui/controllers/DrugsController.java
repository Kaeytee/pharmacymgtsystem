package com.pharmacy.ui.controllers;

import com.pharmacy.entities.Drug;
import com.pharmacy.functionalities.DrugManager;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.util.Optional;

public class DrugsController {

    @FXML
    private TableView<Drug> drugsTable;

    @FXML
    private TableColumn<Drug, String> idColumn;

    @FXML
    private TableColumn<Drug, String> nameColumn;

    @FXML
    private TableColumn<Drug, Integer> quantityColumn;

    @FXML
    private TableColumn<Drug, Integer> stockColumn;

    @FXML
    private TableColumn<Drug, Double> priceColumn;

    @FXML
    private TextField searchField;

    private DrugManager drugManager = new DrugManager();
    private ObservableList<Drug> drugList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDrugId()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDrugName()));
        quantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getDrugQuantity()).asObject());
        stockColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStockQuantity()).asObject());
        priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        loadAllDrugs();
    }

    private void loadAllDrugs() {
        drugList.setAll(drugManager.getAllDrugs());
        drugsTable.setItems(drugList);
    }

    @FXML
    private void handleViewAllDrugs() {
        loadAllDrugs();
        showAlert("Information", "All drugs have been loaded successfully.");
    }

    @FXML
    private void handleAddDrug() {
        Optional<Drug> result = showDrugDialog(null);
        result.ifPresent(drug -> {
            drugManager.addDrug(drug);
            loadAllDrugs();
        });
    }

    @FXML
    private void handleEditDrug() {
        Drug selectedDrug = drugsTable.getSelectionModel().getSelectedItem();
        if (selectedDrug != null) {
            Optional<Drug> result = showDrugDialog(selectedDrug);
            result.ifPresent(drug -> {
                drugManager.updateDrug(drug);
                loadAllDrugs();
            });
        } else {
            showAlert("No Drug Selected", "Please select a drug to edit.");
        }
    }

    @FXML
    private void handleRemoveDrug() {
        Drug selectedDrug = drugsTable.getSelectionModel().getSelectedItem();
        if (selectedDrug != null) {
            drugManager.removeDrug(selectedDrug.getDrugId());
            loadAllDrugs();
        } else {
            showAlert("No Drug Selected", "Please select a drug to delete.");
        }
    }

    @FXML
    private void handleSearchDrugs() {
        String searchText = searchField.getText();
        if (searchText != null && !searchText.isEmpty()) {
            Optional<Drug> drugOpt = drugManager.searchDrugByName(searchText);
            if (drugOpt.isPresent()) {
                // If the drug is found, update the table with that drug
                drugList.setAll(drugOpt.get()); // Use Collections.singletonList for single item
                drugsTable.setItems(drugList);
            } else {
                showAlert("No Drug Found", "No drug found with the given name.");
                // Optionally clear the table or reload all drugs
                loadAllDrugs();
            }
        } else {
            loadAllDrugs(); // Reload all drugs if search text is empty
        }
    }




    private Optional<Drug> showDrugDialog(Drug drug) {
        Dialog<Drug> dialog = new Dialog<>();
        dialog.setTitle(drug == null ? "Add Drug" : "Edit Drug");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        VBox vbox = new VBox(10);
        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField quantityField = new TextField();
        TextField stockField = new TextField();
        TextField priceField = new TextField();

        if (drug != null) {
            idField.setText(drug.getDrugId());
            nameField.setText(drug.getDrugName());
            quantityField.setText(Integer.toString(drug.getDrugQuantity()));
            stockField.setText(Integer.toString(drug.getStockQuantity()));
            priceField.setText(Double.toString(drug.getPrice()));
        }

        vbox.getChildren().addAll(
                new Label("ID:"), idField,
                new Label("Name:"), nameField,
                new Label("Quantity:"), quantityField,
                new Label("Stock:"), stockField,
                new Label("Price:"), priceField
        );

        dialog.getDialogPane().setContent(vbox);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                String id = idField.getText();
                String name = nameField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                int stock = Integer.parseInt(stockField.getText());
                double price = Double.parseDouble(priceField.getText());

                return new Drug(id, name, quantity, stock, price);
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
