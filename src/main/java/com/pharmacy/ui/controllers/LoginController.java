package com.pharmacy.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.pharmacy.functionalities.LoginService;
import com.pharmacy.ui.MainApp;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private LoginService loginService = new LoginService();
    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Login failed", "Please enter both username and password.");
            return;
        }

        boolean loginSuccess = loginService.loginUser(username, password);
        if (loginSuccess) {
            mainApp.showDashboard();
        } else {
            showAlert("Login failed", "Incorrect username or password.");
        }
    }

    @FXML
    private void handleCancel() {
        usernameField.clear();
        passwordField.clear();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
