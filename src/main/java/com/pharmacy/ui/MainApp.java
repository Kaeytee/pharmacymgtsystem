package com.pharmacy.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainApp extends Application {

    private Stage primaryStage;
    private Connection connection;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        try {
            initDatabaseConnection();
            showDashboard();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initDatabaseConnection() throws SQLException {
        // Replace with your database connection details
        String url = "jdbc:postgresql://localhost:5432/ad_pharmacy";
        String user = "pharmacy_user";
        String password = "gooses2@";
        this.connection = DriverManager.getConnection(url, user, password);
    }

    public Connection getConnection() {
        return connection;
    }

    public void initLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pharmacy/views/LoginLayout.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 800);
            scene.getStylesheets().add(getClass().getResource("/com/pharmacy/styles/login.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("AD Chemist - Login");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pharmacy/views/Dashboard.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 800);
            scene.getStylesheets().add(getClass().getResource("/com/pharmacy/styles/Dashboard.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("AD Chemist - Dashboard");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
