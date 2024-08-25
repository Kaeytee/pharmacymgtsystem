package com.pharmacy.functionalities;

import com.pharmacy.entities.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Optional;

/**
 * Manages customer-related operations.
 */
public class CustomerManager {
    private ObservableList<Customer> customers;

    /**
     * Initializes a new CustomerManager.
     */
    public CustomerManager() {
        this.customers = FXCollections.observableArrayList();
    }

    /**
     * Adds a new customer.
     *
     * @param customer the customer to add
     * @throws IllegalArgumentException if the customer is null or already exists
     */
    public void addCustomer(Customer customer) {
        if (customer == null) throw new IllegalArgumentException("Customer cannot be null.");
        if (containsCustomer(customer)) throw new IllegalArgumentException("Customer already exists.");
        this.customers.add(customer);
    }

    /**
     * Removes an existing customer.
     *
     * @param customer the customer to remove
     * @throws IllegalArgumentException if the customer is null
     */
    public void removeCustomer(Customer customer) {
        if (customer == null) throw new IllegalArgumentException("Customer cannot be null.");
        this.customers.remove(customer);
    }

    /**
     * Updates the information of an existing customer.
     *
     * @param customer the customer with updated information
     * @throws IllegalArgumentException if the customer is null or does not exist
     */
    public void updateCustomer(Customer customer) {
        if (customer == null) throw new IllegalArgumentException("Customer cannot be null.");
        Optional<Customer> existingCustomer = searchCustomerByID(customer.getCustomerID());
        existingCustomer.ifPresentOrElse(
                c -> {
                    c.setCustomerName(customer.getCustomerName());
                    c.setContactInfo(customer.getContactInfo());
                },
                () -> { throw new IllegalArgumentException("Customer does not exist."); }
        );
    }

    /**
     * Searches for a customer by ID.
     *
     * @param customerID the customer ID to search for
     * @return an Optional containing the found customer, or empty if not found
     * @throws IllegalArgumentException if the customer ID is null or empty
     */
    public Optional<Customer> searchCustomerByID(String customerID) {
        if (customerID == null || customerID.isEmpty()) throw new IllegalArgumentException("Customer ID cannot be null or empty.");
        return customers.stream()
                .filter(customer -> customer.getCustomerID().equals(customerID))
                .findFirst();
    }

    /**
     * Lists all customers.
     *
     * @return an unmodifiable ObservableList of all customers
     */
    public ObservableList<Customer> listAllCustomers() {
        return FXCollections.unmodifiableObservableList(customers);
    }

    /**
     * Checks if a customer exists.
     *
     * @param customer the customer to check
     * @return true if the customer exists, false otherwise
     * @throws IllegalArgumentException if the customer is null
     */
    public boolean containsCustomer(Customer customer) {
        if (customer == null) throw new IllegalArgumentException("Customer cannot be null.");
        return customers.stream()
                .anyMatch(c -> c.getCustomerID().equals(customer.getCustomerID()));
    }
}
