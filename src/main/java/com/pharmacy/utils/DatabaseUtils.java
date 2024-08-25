package com.pharmacy.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtils {

    private static final String URL = "jdbc:postgresql://localhost:5432/ad_pharmacy";
    private static final String USER = "pharmacy_user";
    private static final String PASSWORD = "gooses2@";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
