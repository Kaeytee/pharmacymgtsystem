package com.pharmacy.entities;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Represents a purchase transaction in the pharmacy.
 */
public class Purchase {
    private String purchaseId;
    private LocalDate purchaseDate;
    private Map<Drug, Integer> itemsPurchased;
    private double totalAmount;

    /**
     * Constructs a new Purchase instance.
     *
     * @param purchaseId    The unique ID of the purchase.
     * @param purchaseDate  The date of the purchase.
     */
    public Purchase(String purchaseId, LocalDate purchaseDate) {
        if (purchaseId == null || purchaseId.isEmpty()) {
            throw new IllegalArgumentException("Purchase ID cannot be null or empty.");
        }
        if (purchaseDate == null) {
            throw new IllegalArgumentException("Purchase date cannot be null.");
        }
        this.purchaseId = generatePurchaseID();
        this.purchaseDate = purchaseDate;
        this.itemsPurchased = new HashMap<>();
        this.totalAmount = 0.0; // Initialize total amount to zero
    }

    private String generatePurchaseID()
    {
        return UUID.randomUUID().toString();
    }

    // Getters and Setters

    /**
     * Retrieves the purchase ID.
     *
     * @return The purchase ID.
     */
    public String getPurchaseId() {
        return purchaseId;
    }

    /**
     * Retrieves the purchase date.
     *
     * @return The purchase date.
     */
    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * Retrieves the items purchased in the transaction.
     *
     * @return A map of drugs and their quantities purchased.
     */
    public Map<Drug, Integer> getItemsPurchased() {
        return new HashMap<>(itemsPurchased);
    }

    /**
     * Sets the items purchased in the transaction.
     *
     * @param itemsPurchased A map of drugs and their quantities purchased.
     */
    public void setItemsPurchased(Map<Drug, Integer> itemsPurchased) {
        if (itemsPurchased == null || itemsPurchased.isEmpty()) {
            throw new IllegalArgumentException("Items purchased cannot be null or empty.");
        }
        this.itemsPurchased = new HashMap<>(itemsPurchased);
    }

    /**
     * Retrieves the total amount of the purchase.
     *
     * @return The total amount of the purchase.
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Sets the total amount of the purchase.
     *
     * @param totalAmount The total amount of the purchase.
     */
    public void setTotalAmount(double totalAmount) {
        if (totalAmount < 0) {
            throw new IllegalArgumentException("Total amount cannot be negative.");
        }
        this.totalAmount = totalAmount;
    }
}
