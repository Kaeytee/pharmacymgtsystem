package com.pharmacy.entities;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a sales transaction in the pharmacy.
 */
public class Sales {
    private String salesId;
    private LocalDate salesDate;
    private String customerName;
    private Map<Drug, Integer> itemsSold;
    private double totalAmount;

    /**
     * Constructs a new Sales instance.
     *
     * @param salesId      the unique ID of the sale
     * @param salesDate    the date of the sale
     * @param customerName the name of the customer
     * @param totalAmount  the total amount of the sale
     */
    public Sales(String salesId, LocalDate salesDate, String customerName, double totalAmount) {
        if (salesId == null || salesId.isEmpty()) {
            throw new IllegalArgumentException("Sales ID cannot be null or empty.");
        }
        if (salesDate == null) {
            throw new IllegalArgumentException("Sales date cannot be null.");
        }
        if (customerName == null || customerName.isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be null or empty.");
        }
        if (totalAmount < 0) {
            throw new IllegalArgumentException("Total amount cannot be negative.");
        }
        this.salesId = salesId;
        this.salesDate = salesDate;
        this.customerName = customerName;
        this.itemsSold = new HashMap<>();
        this.totalAmount = totalAmount;
    }

    // Getters and Setters
    public String getSalesId() { return salesId; }

    public LocalDate getSalesDate() { return salesDate; }

    public String getCustomerName() { return customerName; }

    public Map<Drug, Integer> getItemsSold() { return new HashMap<>(itemsSold); }

    public void setItemsSold(Map<Drug, Integer> itemsSold) {
        if (itemsSold == null || itemsSold.isEmpty()) {
            throw new IllegalArgumentException("Items sold cannot be null or empty.");
        }
        this.itemsSold = new HashMap<>(itemsSold);
    }

    public double getTotalAmount() { return totalAmount; }

    public void setTotalAmount(double totalAmount) {
        if (totalAmount < 0) {
            throw new IllegalArgumentException("Total amount cannot be negative.");
        }
        this.totalAmount = totalAmount;
    }
}
