package com.pharmacy.functionalities;

import com.pharmacy.entities.Personnel;
import com.pharmacy.utils.DatabaseUtils;
import com.pharmacy.utils.PasswordUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Manages login operations.
 */
public class LoginService {

    /**
     * Logs in a user.
     *
     * @param username the username of the user
     * @param plainPassword the plain text password of the user
     * @return true if the login is successful, false otherwise
     */
    public boolean loginUser(String username, String plainPassword) {
        Personnel user = getUserFromDatabase(username);

        if (user != null) {
            return PasswordUtils.verifyPassword(plainPassword, user.getHashedPassword());
        }
        return false;
    }

    /**
     * Retrieves a user from the database.
     *
     * @param username the username to search for
     * @return the found Personnel object, or null if not found
     */
    private Personnel getUserFromDatabase(String username) {
        String query = "SELECT * FROM personnel WHERE username = ?";

        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String dbUsername = resultSet.getString("username");
                String hashedPassword = resultSet.getString("hashed_password");
                return new Personnel(dbUsername, hashedPassword);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
