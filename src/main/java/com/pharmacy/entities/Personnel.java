package com.pharmacy.entities;

import org.bouncycastle.crypto.generators.SCrypt;
import org.bouncycastle.util.encoders.Hex;

import java.security.SecureRandom;

public class Personnel {
    private String username;
    private String hashedPassword;

    public Personnel(String username, String hashedPassword) {
        if (username == null || username.isEmpty())
            throw new IllegalArgumentException("Username cannot be null or empty.");
        if (hashedPassword == null || hashedPassword.isEmpty())
            throw new IllegalArgumentException("Password cannot be null or empty.");
        this.username = username;
        this.hashedPassword = hashedPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    // Method to hash the password during registration or password change
    public static String hashPassword(String password) {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        byte[] hash = SCrypt.generate(password.getBytes(), salt, 16384, 8, 1, 32);
        return Hex.toHexString(salt) + "$" + Hex.toHexString(hash);
    }

    // Method to verify password
    public boolean verifyPassword(String password) {
        String[] parts = hashedPassword.split("\\$");
        byte[] salt = Hex.decode(parts[0]);
        byte[] hash = SCrypt.generate(password.getBytes(), salt, 16384, 8, 1, 32);
        return Hex.toHexString(hash).equals(parts[1]);
    }
}
