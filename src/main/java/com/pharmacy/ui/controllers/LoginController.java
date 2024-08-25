package com.pharmacy.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.pharmacy.functionalities.LoginService;
import com.pharmacy.ui.MainApp;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private LoginService loginService;

    public LoginController() {
        this.loginService = new LoginService();
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
            navigateToMainScreen();
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

    private void navigateToMainScreen() {
        try {
            MainApp mainApp = new MainApp();
            mainApp.showDashboard();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load dashboard screen.");
        }
    }
}
