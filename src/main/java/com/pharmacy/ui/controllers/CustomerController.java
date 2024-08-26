package com.pharmacy.ui.controllers;

import com.pharmacy.entities.Customer;
import com.pharmacy.functionalities.CustomerManager; // Replace with the actual class name
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class CustomerController {

    @FXML
    private TableView<Customer> customersTable;

    @FXML
    private TableColumn<Customer, String> idColumn;

    @FXML
    private TableColumn<Customer, String> nameColumn;

    @FXML
    private TableColumn<Customer, String> contactInfoColumn;

    @FXML
    private TextField searchField;

    private CustomerManager customerManager = new CustomerManager();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerID()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerName()));
        contactInfoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContactInfo()));

        loadAllCustomers();
    }

    private void loadAllCustomers() {
        customersTable.setItems(FXCollections.observableArrayList(customerManager.listAllCustomers()));
    }

    @FXML
    private void handleAddCustomer() {
        Optional<Customer> result = showCustomerDialog(null);
        result.ifPresent(customer -> {
            customerManager.addCustomer(customer);
            loadAllCustomers();
        });
    }

    @FXML
    private void handleEditCustomer() {
        Customer selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            Optional<Customer> result = showCustomerDialog(selectedCustomer);
            result.ifPresent(customer -> {
                customerManager.updateCustomer(customer);
                loadAllCustomers();
            });
        } else {
            showAlert("No Customer Selected", "Please select a customer to edit.");
        }
    }

    @FXML
    private void handleRemoveCustomer() {
        Customer selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            customerManager.removeCustomer(selectedCustomer);
            loadAllCustomers();
        } else {
            showAlert("No Customer Selected", "Please select a customer to remove.");
        }
    }

    @FXML
    private void handleSearchCustomer() {
        String searchText = searchField.getText();
        if (searchText != null && !searchText.isEmpty()) {
            Optional<Customer> customer = customerManager.searchCustomerByID(searchText); // Adjust as needed
            customer.ifPresent(c -> customersTable.setItems(FXCollections.observableArrayList(c)));
        } else {
            loadAllCustomers();
        }
    }

    @FXML
    private void handleViewAllCustomers() {
        loadAllCustomers();
    }

    private Optional<Customer> showCustomerDialog(Customer customer) {
        Dialog<Customer> dialog = new Dialog<>();
        dialog.setTitle(customer == null ? "Add Customer" : "Edit Customer");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        VBox vbox = new VBox(10);
        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField contactField = new TextField();

        if (customer != null) {
            idField.setText(customer.getCustomerID());
            nameField.setText(customer.getCustomerName());
            contactField.setText(customer.getContactInfo());
        }

        vbox.getChildren().addAll(new Label("ID:"), idField, new Label("Name:"), nameField, new Label("Contact:"), contactField);
        dialog.getDialogPane().setContent(vbox);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                String id = idField.getText();
                String name = nameField.getText();
                String contact = contactField.getText();

                try {
                    if (customer == null) {
                        return new Customer(id, name, contact); // Ensure you have an appropriate constructor
                    } else {
                        customer.setCustomerID(id);
                        customer.setCustomerName(name);
                        customer.setContactInfo(contact);
                        return customer;
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
