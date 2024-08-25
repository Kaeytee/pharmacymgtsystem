package com.pharmacy.functionalities;

import com.pharmacy.entities.Drug;
import java.util.HashMap;

/**
 * Manages purchase-related operations.
 */
public class PurchaseManager {
    private HashMap<Drug, Integer> purchases;

    /**
     * Initializes a new PurchaseManager.
     */
    public PurchaseManager() {
        this.purchases = new HashMap<>();
    }

    /**
     * Adds a new purchase.
     *
     * @param drug the drug being purchased
     * @param quantity the quantity of the drug being purchased
     * @throws IllegalArgumentException if the drug is null or quantity is not positive
     */
    public void addPurchase(Drug drug, int quantity) {
        if (drug == null) throw new IllegalArgumentException("Drug cannot be null.");
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive.");
        purchases.put(drug, purchases.getOrDefault(drug, 0) + quantity);
    }

    /**
     * Gets the total quantity purchased for a specific drug.
     *
     * @param drug the drug to get the total quantity for
     * @return the total quantity purchased
     * @throws IllegalArgumentException if the drug is null
     */
    public int getTotalPurchased(Drug drug) {
        if (drug == null) throw new IllegalArgumentException("Drug cannot be null.");
        return purchases.getOrDefault(drug, 0);
    }

    /**
     * Lists all purchases.
     *
     * @return a map of all purchases with drugs and their quantities
     */
    public HashMap<Drug, Integer> listAllPurchases() {
        return new HashMap<>(purchases);
    }
}
