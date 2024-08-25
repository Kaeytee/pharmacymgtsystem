package com.pharmacy.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Objects;

/**
 * Represents a drug in the pharmacy.
 */
public class Drug {
    private String drugId; // Unique identifier for the drug
    private String drugName;
    private int drugQuantity;
    private int stockQuantity;
    private double price;
    private Map<String, Supplier> suppliers; // Map to store suppliers by their IDs

    /**
     * Constructs a new Drug instance with the specified attributes.
     *
     * @param drugId
     * @param drugName      The name of the drug.
     * @param drugQuantity  The quantity of the drug available for sale.
     * @param stockQuantity The quantity of the drug in stock.
     * @param price         The price of the drug.
     */
    public Drug(String drugId, String drugName, int drugQuantity, int stockQuantity, double price) {
        if (drugName == null || drugName.isEmpty()) {
            throw new IllegalArgumentException("Drug name cannot be null or empty.");
        }
        if (drugQuantity < 0) {
            throw new IllegalArgumentException("Drug quantity cannot be negative.");
        }
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.drugId = generateDrugCode(); // Generate unique ID using UUID
        this.drugName = drugName;
        this.drugQuantity = drugQuantity;
        this.stockQuantity = stockQuantity;
        this.price = price;
        this.suppliers = new HashMap<>();
    }

    private String generateDrugCode() {
        return UUID.randomUUID().toString();
    }

    // Getters and Setters

    public String getDrugId() {
        return drugId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        if (drugName == null || drugName.isEmpty()) {
            throw new IllegalArgumentException("Drug name cannot be null or empty.");
        }
        this.drugName = drugName;
    }

    public int getDrugQuantity() {
        return drugQuantity;
    }

    public void setDrugQuantity(int drugQuantity) {
        if (drugQuantity < 0) {
            throw new IllegalArgumentException("Drug quantity cannot be negative.");
        }
        this.drugQuantity = drugQuantity;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative.");
        }
        this.stockQuantity = stockQuantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.price = price;
    }

    public Map<String, Supplier> getSuppliers() {
        return new HashMap<>(suppliers);
    }

    // Methods for managing suppliers
    public void addSupplier(Supplier supplier) {
        if (supplier == null) {
            throw new IllegalArgumentException("Supplier cannot be null.");
        }
        suppliers.put(supplier.getSupplierId(), supplier);
    }

    public void removeSupplier(String supplierId) {
        suppliers.remove(supplierId);
    }
}


