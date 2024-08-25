package com.pharmacy.dao;

import com.pharmacy.entities.Drug;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class for managing drug-related database operations.
 */
public class DrugDAO {

    private final Connection connection;

    /**
     * Initializes a new DrugDAO with the given database connection.
     *
     * @param connection the database connection
     */
    public DrugDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Adds a new drug to the database.
     *
     * @param drug the drug to add
     * @throws SQLException if a database access error occurs
     */
    public void addDrug(Drug drug) throws SQLException {
        String sql = "INSERT INTO drugs (drug_id, drug_name, quantity, stock_quantity, price) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, drug.getDrugId());
            pstmt.setString(2, drug.getDrugName());
            pstmt.setInt(3, drug.getDrugQuantity());
            pstmt.setInt(4, drug.getStockQuantity());
            pstmt.setDouble(5, drug.getPrice());
            pstmt.executeUpdate();
        }
    }

    /**
     * Retrieves a drug by its ID.
     *
     * @param drugId the drug ID
     * @return the found drug, or null if not found
     * @throws SQLException if a database access error occurs
     */
    public Drug getDrugById(String drugId) throws SQLException {
        String sql = "SELECT * FROM drugs WHERE drug_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, drugId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Drug(
                            rs.getString("drug_id"),
                            rs.getString("drug_name"),
                            rs.getInt("quantity"),
                            rs.getInt("stock_quantity"),
                            rs.getDouble("price")
                    );
                }
            }
        }
        return null;
    }

    /**
     * Retrieves all drugs from the database.
     *
     * @return a list of all drugs
     * @throws SQLException if a database access error occurs
     */
    public List<Drug> getAllDrugs() throws SQLException {
        List<Drug> drugs = new ArrayList<>();
        String sql = "SELECT * FROM drugs";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                drugs.add(new Drug(
                        rs.getString("drug_id"),
                        rs.getString("drug_name"),
                        rs.getInt("quantity"),
                        rs.getInt("stock_quantity"),
                        rs.getDouble("price")
                ));
            }
        }
        return drugs;
    }

    /**
     * Updates an existing drug in the database.
     *
     * @param drug the drug to update
     * @throws SQLException if a database access error occurs
     */
    public void updateDrug(Drug drug) throws SQLException {
        String sql = "UPDATE drugs SET drug_name = ?, quantity = ?, stock_quantity = ?, price = ? WHERE drug_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, drug.getDrugName());
            pstmt.setInt(2, drug.getDrugQuantity());
            pstmt.setInt(3, drug.getStockQuantity());
            pstmt.setDouble(4, drug.getPrice());
            pstmt.setString(5, drug.getDrugId());
            pstmt.executeUpdate();
        }
    }

    /**
     * Deletes a drug from the database by its ID.
     *
     * @param drugId the drug ID
     * @throws SQLException if a database access error occurs
     */
    public void deleteDrug(String drugId) throws SQLException {
        String sql = "DELETE FROM drugs WHERE drug_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, drugId);
            pstmt.executeUpdate();
        }
    }

    /**
     * Searches for drugs by name.
     *
     * @param name the drug name to search for
     * @return a list of matching drugs
     * @throws SQLException if a database access error occurs
     */
    public List<Drug> searchDrugsByName(String name) throws SQLException {
        List<Drug> drugs = new ArrayList<>();
        String sql = "SELECT * FROM drugs WHERE drug_name ILIKE ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + name + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    drugs.add(new Drug(
                            rs.getString("drug_id"),
                            rs.getString("drug_name"),
                            rs.getInt("quantity"),
                            rs.getInt("stock_quantity"),
                            rs.getDouble("price")
                    ));
                }
            }
        }
        return drugs;
    }
}
