package com.pharmacy.dao;

import com.pharmacy.entities.Supplier;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {

    private Connection connection;

    public SupplierDAO(Connection connection) {
        this.connection = connection;
    }

    public void addSupplier(Supplier supplier) throws SQLException {
        String sql = "INSERT INTO suppliers (id, name, location) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(2, supplier.getSupplierName());
            pstmt.setString(3, supplier.getLocation());
            pstmt.setString(1, supplier.getContactInfo());
            pstmt.executeUpdate();
        }
    }

    public Supplier getSupplierById(int id) throws SQLException {
        String sql = "SELECT * FROM suppliers WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Supplier(
                            rs.getString("name"),
                            rs.getString("location"),
                            rs.getString("contact")
                    );
                }
            }
        }
        return null;
    }

    public List<Supplier> getAllSuppliers() throws SQLException {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM suppliers";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                suppliers.add(new Supplier(
                        rs.getString("name"),
                        rs.getString("location"),
                        rs.getString("contact")
                ));
            }
        }
        return suppliers;
    }

    public void updateSupplier(Supplier supplier) throws SQLException {
        String sql = "UPDATE suppliers SET name = ?, location = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, supplier.getSupplierName());
            pstmt.setString(2, supplier.getLocation());
            pstmt.setString(3, supplier.getContactInfo());
            pstmt.executeUpdate();
        }
    }

    public void deleteSupplier(int id) throws SQLException {
        String sql = "DELETE FROM suppliers WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
