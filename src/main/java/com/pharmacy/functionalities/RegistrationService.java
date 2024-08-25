package com.pharmacy.functionalities;

import com.pharmacy.entities.Personnel;
import com.pharmacy.utils.DatabaseUtils;
import com.pharmacy.utils.PasswordUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Manages registration-related operations.
 */
public class RegistrationService {

    /**
     * Registers a new user.
     *
     * @param username the username of the new user
     * @param plainPassword the plain text password of the new user
     */
    public void registerUser(String username, String plainPassword) {
        String hashedPassword = PasswordUtils.hashPassword(plainPassword);
        Personnel newUser = new Personnel(username, hashedPassword);

        saveUserToDatabase(newUser);
    }

    /**
     * Saves a user to the database.
     *
     * @param user the user to save
     */
    private void saveUserToDatabase(Personnel user) {
        String query = "INSERT INTO personnel (username, hashed_password) VALUES (?, ?)";

        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getHashedPassword());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
