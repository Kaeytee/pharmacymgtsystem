package com.pharmacy.functionalities;

import com.pharmacy.entities.Drug;
import java.util.HashMap;

/**
 * Manages sales-related operations.
 */
public class SalesManager {
    private HashMap<Drug, Integer> sales;

    /**
     * Initializes a new SalesManager.
     */
    public SalesManager() {
        this.sales = new HashMap<>();
    }

    /**
     * Adds a new sale.
     *
     * @param drug the drug being sold
     * @param quantity the quantity of the drug being sold
     * @throws IllegalArgumentException if the drug is null or quantity is not positive
     */
    public void addSale(Drug drug, int quantity) {
        if (drug == null) throw new IllegalArgumentException("Drug cannot be null.");
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive.");
        sales.put(drug, sales.getOrDefault(drug, 0) + quantity);
    }

    /**
     * Gets the total quantity sold for a specific drug.
     *
     * @param drug the drug to get the total quantity for
     * @return the total quantity sold
     * @throws IllegalArgumentException if the drug is null
     */
    public int getTotalSold(Drug drug) {
        if (drug == null) throw new IllegalArgumentException("Drug cannot be null.");
        return sales.getOrDefault(drug, 0);
    }

    /**
     * Lists all sales.
     *
     * @return a map of all sales with drugs and their quantities
     */
    public HashMap<Drug, Integer> listAllSales() {
        return new HashMap<>(sales);
    }
}
