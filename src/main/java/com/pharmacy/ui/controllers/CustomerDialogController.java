package com.pharmacy.ui.controllers;

import com.pharmacy.entities.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CustomerDialogController {
    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField contactInfoField;

    private Stage dialogStage;
    private Customer customer;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;

        if (customer != null) {
            idField.setText(customer.getCustomerID());
            nameField.setText(customer.getCustomerName());
            contactInfoField.setText(customer.getContactInfo());
        }
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            if (customer == null) {
                customer = new Customer(idField.getText(), nameField.getText(), contactInfoField.getText());
            } else {
                customer.setCustomerName(nameField.getText());
                customer.setContactInfo(contactInfoField.getText());
            }

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (idField.getText() == null || idField.getText().isEmpty()) {
            errorMessage += "No valid customer ID!\n";
        }
        if (nameField.getText() == null || nameField.getText().isEmpty()) {
            errorMessage += "No valid customer name!\n";
        }
        if (contactInfoField.getText() == null || contactInfoField.getText().isEmpty()) {
            errorMessage += "No valid contact info!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }

    public Customer getCustomer() {
        return customer;
    }
}
