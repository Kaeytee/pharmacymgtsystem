package com.pharmacy.dao;

import com.pharmacy.entities.Purchase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class for managing purchase-related database operations.
 */
public class PurchaseDAO {
    private Connection connection;

    /**
     * Initializes a new PurchaseDAO with the given database connection.
     *
     * @param connection The database connection.
     */
    public PurchaseDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Adds a new purchase to the database.
     *
     * @param purchase The purchase to add.
     * @throws SQLException If a database access error occurs.
     */
    public void addPurchase(Purchase purchase) throws SQLException {
        String sql = "INSERT INTO purchases (id, purchase_date) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, purchase.getPurchaseId());
            pstmt.setDate(2, Date.valueOf(purchase.getPurchaseDate()));
            pstmt.executeUpdate();
        }
    }

    /**
     * Retrieves a purchase by its ID.
     *
     * @param id The purchase ID.
     * @return The found purchase, or null if not found.
     * @throws SQLException If a database access error occurs.
     */
    public Purchase getPurchaseById(String id) throws SQLException {
        String sql = "SELECT * FROM purchases WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Purchase(
                            rs.getString("id"),
                            rs.getDate("purchase_date").toLocalDate()
                    );
                }
            }
        }
        return null;
    }

    /**
     * Retrieves all purchases from the database.
     *
     * @return A list of all purchases.
     * @throws SQLException If a database access error occurs.
     */
    public List<Purchase> getAllPurchases() throws SQLException {
        List<Purchase> purchases = new ArrayList<>();
        String sql = "SELECT * FROM purchases";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                purchases.add(new Purchase(
                        rs.getString("id"),
                        rs.getDate("purchase_date").toLocalDate()
                ));
            }
        }
        return purchases;
    }

    /**
     * Updates an existing purchase in the database.
     *
     * @param purchase The purchase to update.
     * @throws SQLException If a database access error occurs.
     */
    public void updatePurchase(Purchase purchase) throws SQLException {
        String sql = "UPDATE purchases SET purchase_date = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(purchase.getPurchaseDate()));
            pstmt.setString(2, purchase.getPurchaseId());
            pstmt.executeUpdate();
        }
    }

    /**
     * Deletes a purchase from the database by its ID.
     *
     * @param id The purchase ID.
     * @throws SQLException If a database access error occurs.
     */
    public void deletePurchase(String id) throws SQLException {
        String sql = "DELETE FROM purchases WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        }
    }
}
