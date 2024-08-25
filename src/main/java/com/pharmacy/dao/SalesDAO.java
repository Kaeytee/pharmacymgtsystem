package com.pharmacy.dao;

import com.pharmacy.entities.Sales;
import com.pharmacy.entities.Drug;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DAO class for managing sales-related database operations.
 */
public class SalesDAO {
    private Connection connection;

    /**
     * Initializes a new SalesDAO with the given database connection.
     *
     * @param connection the database connection
     */
    public SalesDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Adds a new sale to the database.
     *
     * @param sales the sale to add
     * @throws SQLException if a database access error occurs
     */
    public void addSale(Sales sales) throws SQLException {
        String salesSQL = "INSERT INTO sales (sales_id, sales_date, total_amount, customer_name) VALUES (?, ?, ?, ?)";
        String salesItemsSQL = "INSERT INTO sales_items (sales_id, drug_id, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement salesStmt = connection.prepareStatement(salesSQL);
             PreparedStatement salesItemsStmt = connection.prepareStatement(salesItemsSQL)) {
            connection.setAutoCommit(false); // Start transaction
            salesStmt.setString(1, sales.getSalesId());
            salesStmt.setDate(2, Date.valueOf(sales.getSalesDate()));
            salesStmt.setDouble(3, sales.getTotalAmount());
            salesStmt.setString(4, sales.getCustomerName());
            salesStmt.executeUpdate();

            for (Map.Entry<Drug, Integer> entry : sales.getItemsSold().entrySet()) {
                salesItemsStmt.setString(1, sales.getSalesId());
                salesItemsStmt.setString(2, entry.getKey().getDrugId());
                salesItemsStmt.setInt(3, entry.getValue());
                salesItemsStmt.executeUpdate();
            }
            connection.commit(); // Commit transaction
        } catch (SQLException e) {
            connection.rollback(); // Rollback on error
            throw e;
        } finally {
            connection.setAutoCommit(true); // Restore default
        }
    }

    /**
     * Retrieves a sale by its ID.
     *
     * @param salesId the sale ID
     * @return the found sale, or null if not found
     * @throws SQLException if a database access error occurs
     */
    public Sales getSaleById(String salesId) throws SQLException {
        String salesSQL = "SELECT * FROM sales WHERE sales_id = ?";
        String salesItemsSQL = "SELECT * FROM sales_items WHERE sales_id = ?";
        try (PreparedStatement salesStmt = connection.prepareStatement(salesSQL);
             PreparedStatement salesItemsStmt = connection.prepareStatement(salesItemsSQL)) {
            salesStmt.setString(1, salesId);
            try (ResultSet rs = salesStmt.executeQuery()) {
                if (rs.next()) {
                    LocalDate salesDate = rs.getDate("sales_date").toLocalDate();
                    double totalAmount = rs.getDouble("total_amount");
                    String customerName = rs.getString("customer_name");
                    Sales sales = new Sales(salesId, salesDate, customerName, totalAmount);

                    salesItemsStmt.setString(1, salesId);
                    try (ResultSet itemsRS = salesItemsStmt.executeQuery()) {
                        Map<Drug, Integer> itemsSold = new HashMap<>();
                        while (itemsRS.next()) {
                            String drugId = itemsRS.getString("drug_id");
                            int quantity = itemsRS.getInt("quantity");
                            Drug drug = new Drug(drugId, "", 0, 0, 0); // Placeholder for Drug object
                            itemsSold.put(drug, quantity);
                        }
                        sales.setItemsSold(itemsSold);
                    }
                    return sales;
                }
            }
        }
        return null;
    }

    /**
     * Retrieves all sales from the database.
     *
     * @return a list of all sales
     * @throws SQLException if a database access error occurs
     */
    public List<Sales> getAllSales() throws SQLException {
        List<Sales> salesList = new ArrayList<>();
        String sql = "SELECT * FROM sales";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String salesId = rs.getString("sales_id");
                LocalDate salesDate = rs.getDate("sales_date").toLocalDate();
                double totalAmount = rs.getDouble("total_amount");
                String customerName = rs.getString("customer_name");
                Sales sales = new Sales(salesId, salesDate, customerName, totalAmount);
                salesList.add(sales);
            }
        }
        return salesList;
    }

    /**
     * Updates an existing sale in the database.
     *
     * @param sales the sale to update
     * @throws SQLException if a database access error occurs
     */
    public void updateSale(Sales sales) throws SQLException {
        String sql = "UPDATE sales SET sales_date = ?, total_amount = ?, customer_name = ? WHERE sales_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(sales.getSalesDate()));
            pstmt.setDouble(2, sales.getTotalAmount());
            pstmt.setString(3, sales.getCustomerName());
            pstmt.setString(4, sales.getSalesId());
            pstmt.executeUpdate();
        }
    }

    /**
     * Deletes a sale from the database by its ID.
     *
     * @param salesId the sale ID
     * @throws SQLException if a database access error occurs
     */
    public void deleteSale(String salesId) throws SQLException {
        String sql = "DELETE FROM sales WHERE sales_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, salesId);
            pstmt.executeUpdate();
        }
    }
}
