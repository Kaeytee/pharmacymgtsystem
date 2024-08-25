package com.pharmacy.utils;

import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtils {

    private static final int SALT_LENGTH = 16;
    private static final int HASH_LENGTH = 32;
    private static final int ITERATIONS = 2;
    private static final int MEMORY_COST = 65536; // 64MB
    private static final int PARALLELISM = 1;

    private static final SecureRandom secureRandom = new SecureRandom();

    // Hash a password
    public static String hashPassword(String plainTextPassword) {
        byte[] salt = new byte[SALT_LENGTH];
        secureRandom.nextBytes(salt);

        Argon2Parameters.Builder builder = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
                .withSalt(salt)
                .withParallelism(PARALLELISM)
                .withMemoryAsKB(MEMORY_COST)
                .withIterations(ITERATIONS);

        Argon2BytesGenerator generator = new Argon2BytesGenerator();
        generator.init(builder.build());

        byte[] hash = new byte[HASH_LENGTH];
        generator.generateBytes(plainTextPassword.getBytes(StandardCharsets.UTF_8), hash);

        byte[] hashWithSalt = new byte[salt.length + hash.length];
        System.arraycopy(salt, 0, hashWithSalt, 0, salt.length);
        System.arraycopy(hash, 0, hashWithSalt, salt.length, hash.length);

        return Base64.getEncoder().encodeToString(hashWithSalt);
    }

    // Verify a password
    public static boolean verifyPassword(String plainTextPassword, String hashedPassword) {
        byte[] hashWithSalt = Base64.getDecoder().decode(hashedPassword);

        byte[] salt = new byte[SALT_LENGTH];
        byte[] hash = new byte[HASH_LENGTH];
        System.arraycopy(hashWithSalt, 0, salt, 0, salt.length);
        System.arraycopy(hashWithSalt, salt.length, hash, 0, hash.length);

        Argon2Parameters.Builder builder = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
                .withSalt(salt)
                .withParallelism(PARALLELISM)
                .withMemoryAsKB(MEMORY_COST)
                .withIterations(ITERATIONS);

        Argon2BytesGenerator generator = new Argon2BytesGenerator();
        generator.init(builder.build());

        byte[] calculatedHash = new byte[HASH_LENGTH];
        generator.generateBytes(plainTextPassword.getBytes(StandardCharsets.UTF_8), calculatedHash);

        for (int i = 0; i < calculatedHash.length; i++) {
            if (calculatedHash[i] != hash[i]) {
                return false;
            }
        }
        return true;
    }
}
