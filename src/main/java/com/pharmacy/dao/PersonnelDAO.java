package com.pharmacy.dao;

import com.pharmacy.entities.Personnel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonnelDAO {
    private Connection connection;

    public PersonnelDAO(Connection connection) {
        this.connection = connection;
    }

    public void addPersonnel(Personnel personnel) throws SQLException {
        String sql = "INSERT INTO personnel (username, hashed_password) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, personnel.getUsername());
            pstmt.setString(2, personnel.getHashedPassword());
            pstmt.executeUpdate();
        }
    }

    public Personnel getPersonnelByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM personnel WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Personnel(
                            rs.getString("username"),
                            rs.getString("hashed_password")
                    );
                }
            }
        }
        return null;
    }

    public List<Personnel> getAllPersonnel() throws SQLException {
        List<Personnel> personnelList = new ArrayList<>();
        String sql = "SELECT * FROM personnel";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                personnelList.add(new Personnel(
                        rs.getString("username"),
                        rs.getString("hashed_password")
                ));
            }
        }
        return personnelList;
    }

    public void updatePersonnel(Personnel personnel) throws SQLException {
        String sql = "UPDATE personnel SET hashed_password = ? WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, personnel.getHashedPassword());
            pstmt.setString(2, personnel.getUsername());
            pstmt.executeUpdate();
        }
    }

    public void deletePersonnel(String username) throws SQLException {
        String sql = "DELETE FROM personnel WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.executeUpdate();
        }
    }
}
