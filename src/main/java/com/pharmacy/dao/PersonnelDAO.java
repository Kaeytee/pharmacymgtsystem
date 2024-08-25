package com.pharmacy.dao;

import com.pharmacy.entities.Personnel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class for managing personnel-related database operations.
 */
public class PersonnelDAO {

    private Connection connection;

    /**
     * Initializes a new PersonnelDAO with the given database connection.
     *
     * @param connection the database connection
     */
    public PersonnelDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Adds a new personnel to the database.
     *
     * @param personnel the personnel to add
     * @throws SQLException if a database access error occurs
     */
    public void addPersonnel(Personnel personnel) throws SQLException {
        String sql = "INSERT INTO personnel (username, hashed_password) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, personnel.getUsername());
            pstmt.setString(2, personnel.getHashedPassword());
            pstmt.executeUpdate();
        }
    }

    /**
     * Retrieves a personnel by its username.
     *
     * @param username the personnel username
     * @return the found personnel, or null if not found
     * @throws SQLException if a database access error occurs
     */
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

    /**
     * Retrieves all personnel from the database.
     *
     * @return a list of all personnel
     * @throws SQLException if a database access error occurs
     */
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

    /**
     * Updates an existing personnel in the database.
     *
     * @param personnel the personnel to update
     * @throws SQLException if a database access error occurs
     */
    public void updatePersonnel(Personnel personnel) throws SQLException {
        String sql = "UPDATE personnel SET hashed_password = ? WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, personnel.getHashedPassword());
            pstmt.setString(2, personnel.getUsername());
            pstmt.executeUpdate();
        }
    }

    /**
     * Deletes a personnel from the database by its username.
     *
     * @param username the personnel username
     * @throws SQLException if a database access error occurs
     */
    public void deletePersonnel(String username) throws SQLException {
        String sql = "DELETE FROM personnel WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.executeUpdate();
        }
    }
}
