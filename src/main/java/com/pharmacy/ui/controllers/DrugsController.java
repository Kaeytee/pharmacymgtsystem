package com.pharmacy.ui.controllers;

import com.pharmacy.entities.Drug;
import com.pharmacy.dao.DrugDAO;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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

    private DrugDAO drugDAO;

    private ObservableList<Drug> drugList = FXCollections.observableArrayList();

    public DrugsController() {
        // No-argument constructor for FXMLLoader
    }

    @FXML
    public void initialize() {
        try {
            initializeDrugDAO();
        } catch (SQLException e) {
            showAlert("Error", "Could not initialize DAO: " + e.getMessage());
        }

        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDrugId()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDrugName()));
        quantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getDrugQuantity()).asObject());
        stockColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStockQuantity()).asObject());
        priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        try {
            loadAllDrugs();
        } catch (SQLException e) {
            showAlert("Error", "Could not load drugs: " + e.getMessage());
        }
    }

    private void initializeDrugDAO() throws SQLException {
        Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/ad_pharmacy",
                "pharmacy_user",
                "gooses2@"
        );
        this.drugDAO = new DrugDAO(connection);
    }

    private void loadAllDrugs() throws SQLException {
        drugList.setAll(drugDAO.getAllDrugs());
        drugsTable.setItems(drugList);
    }

    @FXML
    private void handleAddDrug() {
        Optional<Drug> result = showDrugDialog(null);
        result.ifPresent(drug -> {
            try {
                drugDAO.addDrug(drug);
                loadAllDrugs();
            } catch (SQLException e) {
                showAlert("Error", "Could not add drug: " + e.getMessage());
            }
        });
    }

    @FXML
    private void handleEditDrug() {
        Drug selectedDrug = drugsTable.getSelectionModel().getSelectedItem();
        if (selectedDrug != null) {
            Optional<Drug> result = showDrugDialog(selectedDrug);
            result.ifPresent(drug -> {
                try {
                    drugDAO.updateDrug(drug);
                    loadAllDrugs();
                } catch (SQLException e) {
                    showAlert("Error", "Could not update drug: " + e.getMessage());
                }
            });
        } else {
            showAlert("No Selection", "Please select a drug to edit.");
        }
    }

    @FXML
    private void handleRemoveDrug() {
        Drug selectedDrug = drugsTable.getSelectionModel().getSelectedItem();
        if (selectedDrug != null) {
            try {
                drugDAO.deleteDrug(selectedDrug.getDrugId());
                loadAllDrugs();
            } catch (SQLException e) {
                showAlert("Error", "Could not delete drug: " + e.getMessage());
            }
        } else {
            showAlert("No Selection", "Please select a drug to delete.");
        }
    }

    @FXML
    private void handleSearchDrugs() {
        String searchTerm = searchField.getText();
        try {
            drugList.setAll(drugDAO.searchDrugsByName(searchTerm));
            drugsTable.setItems(drugList);
        } catch (SQLException e) {
            showAlert("Error", "Could not search drugs: " + e.getMessage());
        }
    }

    private Optional<Drug> showDrugDialog(Drug drug) {
        // Implementation for showing a dialog to add/edit drug
        // Example: return Optional.of(new Drug(...)); if the user submits the form
        return Optional.empty();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
