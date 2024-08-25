package com.pharmacy.entities;

import org.bouncycastle.crypto.generators.SCrypt;
import org.bouncycastle.util.encoders.Hex;

import java.security.SecureRandom;

/**
 * Represents a personnel user in the pharmacy system.
 */
public class Personnel {
    private String username;
    private String hashedPassword;

    /**
     * Constructs a new Personnel instance.
     * @param username The username for the personnel.
     * @param password The password for the personnel.
     */
    public Personnel(String username, String password) {
        if (username == null || username.isEmpty())
            throw new IllegalArgumentException("Username cannot be null or empty.");
        if (password == null || password.isEmpty())
            throw new IllegalArgumentException("Password cannot be null or empty.");
        this.username = username;
        this.hashedPassword = hashPassword(password);
    }

    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    /**
     * Hashes the password using scrypt.
     * @param password The plaintext password.
     * @return The hashed password.
     */
    private String hashPassword(String password) {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        byte[] hash = SCrypt.generate(password.getBytes(), salt, 16384, 8, 1, 32);
        return Hex.toHexString(salt) + "$" + Hex.toHexString(hash);
    }

    /**
     * Verifies a password against the hashed password.
     * @param password The plaintext password.
     * @return True if the password matches, false otherwise.
     */
    public boolean verifyPassword(String password) {
        String[] parts = hashedPassword.split("\\$");
        byte[] salt = Hex.decode(parts[0]);
        byte[] hash = SCrypt.generate(password.getBytes(), salt, 16384, 8, 1, 32);
        return hashedPassword.equals(parts[0] + "$" + Hex.toHexString(hash));
    }
}
