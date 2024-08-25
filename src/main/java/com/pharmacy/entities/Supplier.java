package com.pharmacy.entities;

import java.util.UUID;

/**
 * Represents a supplier in the pharmacy system.
 */
public class Supplier {
    private String supplierId; // Unique identifier for the supplier
    private String supplierName;
    private String contactInfo;
    private String location;

    /**
     * Constructs a new Supplier instance with the specified attributes.
     *
     * @param supplierName The name of the supplier.
     * @param contactInfo  The contact information of the supplier.
     * @param location     The location of the supplier.
     */
    public Supplier(String supplierName, String contactInfo, String location) {
        if (supplierName == null || supplierName.isEmpty()) {
            throw new IllegalArgumentException("Supplier name cannot be null or empty.");
        }
        if (contactInfo == null || contactInfo.isEmpty()) {
            throw new IllegalArgumentException("Contact info cannot be null or empty.");
        }
        if (location == null || location.isEmpty()) {
            throw new IllegalArgumentException("Location cannot be null or empty.");
        }
        this.supplierId = generateSupplierId();
        this.supplierName = supplierName;
        this.contactInfo = contactInfo;
        this.location = location;
    }

    /**
     * Constructs a new Supplier instance with the specified attributes including ID.
     *
     * @param supplierId   The ID of the supplier.
     * @param supplierName The name of the supplier.
     * @param contactInfo  The contact information of the supplier.
     * @param location     The location of the supplier.
     */
    public Supplier(String supplierId, String supplierName, String contactInfo, String location) {
        if (supplierId == null || supplierId.isEmpty()) {
            throw new IllegalArgumentException("Supplier ID cannot be null or empty.");
        }
        if (supplierName == null || supplierName.isEmpty()) {
            throw new IllegalArgumentException("Supplier name cannot be null or empty.");
        }
        if (contactInfo == null || contactInfo.isEmpty()) {
            throw new IllegalArgumentException("Contact info cannot be null or empty.");
        }
        if (location == null || location.isEmpty()) {
            throw new IllegalArgumentException("Location cannot be null or empty.");
        }
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.contactInfo = contactInfo;
        this.location = location;
    }

    private String generateSupplierId() {
        return UUID.randomUUID().toString();
    }

    // Getters and Setters
    public String getSupplierId() {
        return supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        if (supplierName == null || supplierName.isEmpty()) {
            throw new IllegalArgumentException("Supplier name cannot be null or empty.");
        }
        this.supplierName = supplierName;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        if (contactInfo == null || contactInfo.isEmpty()) {
            throw new IllegalArgumentException("Contact info cannot be null or empty.");
        }
        this.contactInfo = contactInfo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        if (location == null || location.isEmpty()) {
            throw new IllegalArgumentException("Location cannot be null or empty.");
        }
        this.location = location;
    }

    public void addDrug(Drug drug) {

    }
}
