package com.pharmacy.entities;

import java.util.UUID;

/**
 * Represents a customer in the pharmacy system.
 */
public class Customer {
    private String customerName;
    private  String customerID;
    private String contactInfo;

    /**
     * Constructs a new Customer instance with the specified details.
     *
     * @param customerID Unique identifier for the customer.
     * @param customerName Name of the customer.
     * @param contactInfo Contact information of the customer.
     */
    public Customer(String customerID, String customerName, String contactInfo) {
        if (customerID == null || customerID.isEmpty())
            throw new IllegalArgumentException("Customer ID cannot be null or empty.");
        if (customerName == null || customerName.isEmpty())
            throw new IllegalArgumentException("Customer name cannot be null or empty.");
        if (contactInfo == null || contactInfo.isEmpty())
            throw new IllegalArgumentException("Contact info cannot be null or empty.");
        this.customerID = customerID;
        this.customerName = customerName;
        this.contactInfo = contactInfo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        if (customerName == null || customerName.isEmpty())
            throw new IllegalArgumentException("Customer name cannot be null or empty.");
        this.customerName = customerName;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        if (contactInfo == null || contactInfo.isEmpty())
            throw new IllegalArgumentException("Contact info cannot be null or empty.");
        this.contactInfo = contactInfo;
    }
}
