package com.pharmacy.ui.controllers;

import com.pharmacy.entities.Supplier;
import com.pharmacy.functionalities.SupplierManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class SupplierController {

    @FXML
    private TableView<Supplier> suppliersTable;

    @FXML
    private TableColumn<Supplier, String> nameColumn;

    @FXML
    private TableColumn<Supplier, String> locationColumn;

    @FXML
    private TableColumn<Supplier, String> contactColumn;

    @FXML
    private TextField searchField;

    private SupplierManager supplierManager = new SupplierManager();

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSupplierName()));
        locationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocation()));
        contactColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContactInfo()));

        loadAllSuppliers();
    }

    private void loadAllSuppliers() {
        suppliersTable.setItems(FXCollections.observableArrayList(supplierManager.listAllSuppliers()));
    }

    @FXML
    private void handleAddSupplier() {
        Optional<Supplier> result = showSupplierDialog(null);
        result.ifPresent(supplier -> {
            supplierManager.addSupplier(supplier);
            loadAllSuppliers();
        });
    }

    @FXML
    private void handleEditSupplier() {
        Supplier selectedSupplier = suppliersTable.getSelectionModel().getSelectedItem();
        if (selectedSupplier != null) {
            Optional<Supplier> result = showSupplierDialog(selectedSupplier);
            result.ifPresent(supplier -> {
                supplierManager.updateSupplier(supplier);
                loadAllSuppliers();
            });
        } else {
            showAlert("No Supplier Selected", "Please select a supplier to edit.");
        }
    }

    @FXML
    private void handleRemoveSupplier() {
        Supplier selectedSupplier = suppliersTable.getSelectionModel().getSelectedItem();
        if (selectedSupplier != null) {
            supplierManager.removeSupplier(selectedSupplier);
            loadAllSuppliers();
        } else {
            showAlert("No Supplier Selected", "Please select a supplier to remove.");
        }
    }

    @FXML
    private void handleSearchSupplier() {
        String searchText = searchField.getText();
        if (searchText != null && !searchText.isEmpty()) {
            Optional<Supplier> supplier = supplierManager.searchSupplierByName(searchText);
            supplier.ifPresent(s -> suppliersTable.setItems(FXCollections.observableArrayList(s)));
        } else {
            loadAllSuppliers();
        }
    }

    @FXML
    private void handleViewAllSuppliers() {
        loadAllSuppliers();
    }

    private Optional<Supplier> showSupplierDialog(Supplier supplier) {
        Dialog<Supplier> dialog = new Dialog<>();
        dialog.setTitle(supplier == null ? "Add Supplier" : "Edit Supplier");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        VBox vbox = new VBox(10);
        TextField nameField = new TextField();
        TextField locationField = new TextField();
        TextField contactField = new TextField();

        if (supplier != null) {
            nameField.setText(supplier.getSupplierName());
            locationField.setText(supplier.getLocation());
            contactField.setText(supplier.getContactInfo());
        }

        vbox.getChildren().addAll(new Label("Name:"), nameField, new Label("Location:"), locationField, new Label("Contact:"), contactField);
        dialog.getDialogPane().setContent(vbox);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                String name = nameField.getText();
                String location = locationField.getText();
                String contact = contactField.getText();

                try {
                    if (supplier == null) {
                        return new Supplier(name, location, contact);
                    } else {
                        supplier.setSupplierName(name);
                        supplier.setLocation(location);
                        supplier.setContactInfo(contact);
                        return supplier;
                    }
                } catch (IllegalArgumentException e) {
                    showAlert("Invalid Input", e.getMessage());
                    return null;
                }
            } else {
                return null;
            }
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