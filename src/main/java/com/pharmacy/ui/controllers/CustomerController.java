package com.pharmacy.ui.controllers;

import com.pharmacy.entities.Customer;
import com.pharmacy.dao.CustomerDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class CustomerController {
    @FXML private TableView<Customer> customersTable;
    @FXML private TableColumn<Customer, String> idColumn;
    @FXML private TableColumn<Customer, String> nameColumn;
    @FXML private TableColumn<Customer, String> contactInfoColumn;
    @FXML private TextField searchField;

    private CustomerDAO customerDAO;

    public CustomerController(Connection connection) {
        this.customerDAO = new CustomerDAO(connection);
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerID()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerName()));
        contactInfoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContactInfo()));

        try {
            loadAllCustomers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAllCustomers() throws SQLException {
        customersTable.setItems(FXCollections.observableArrayList(customerDAO.getAllCustomers()));
    }

    @FXML
    private void handleAddCustomer() {
        Optional<Customer> result = showCustomerDialog(null);
        result.ifPresent(customer -> {
            try {
                customerDAO.addCustomer(customer);
                loadAllCustomers();
            } catch (SQLException e) {
                showAlert("Error", "Could not add customer: " + e.getMessage());
            }
        });
    }

    @FXML
    private void handleEditCustomer() {
        Customer selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            Optional<Customer> result = showCustomerDialog(selectedCustomer);
            result.ifPresent(customer -> {
                try {
                    customerDAO.updateCustomer(customer);
                    loadAllCustomers();
                } catch (SQLException e) {
                    showAlert("Error", "Could not update customer: " + e.getMessage());
                }
            });
        } else {
            showAlert("No Selection", "Please select a customer to edit.");
        }
    }

    @FXML
    private void handleRemoveCustomer() {
        Customer selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            try {
                customerDAO.deleteCustomer(selectedCustomer.getCustomerID());
                loadAllCustomers();
            } catch (SQLException e) {
                showAlert("Error", "Could not delete customer: " + e.getMessage());
            }
        } else {
            showAlert("No Selection", "Please select a customer to delete.");
        }
    }

    @FXML
    private void handleViewAllCustomers() {
        try {
            loadAllCustomers();
        } catch (SQLException e) {
            showAlert("Error", "Could not load all customers: " + e.getMessage());
        }
    }

    @FXML
    private void handleSearchCustomer() {
        String customerId = searchField.getText();
        if (customerId == null || customerId.isEmpty()) {
            showAlert("No Input", "Please enter a customer ID to search.");
            return;
        }

        Optional<Customer> result = Optional.ofNullable(customerDAO.getCustomerById(customerId));
        if (result.isPresent()) {
            customersTable.setItems(FXCollections.observableArrayList(result.get()));
        } else {
            showAlert("Not Found", "Customer with ID " + customerId + " not found.");
        }
    }

    private Optional<Customer> showCustomerDialog(Customer customer) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/pharmacy/ui/CustomerDialog.fxml"));
            GridPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Customer");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(customersTable.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            CustomerDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCustomer(customer);

            dialogStage.showAndWait();

            return controller.isOkClicked() ? Optional.of(controller.getCustomer()) : Optional.empty();
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
