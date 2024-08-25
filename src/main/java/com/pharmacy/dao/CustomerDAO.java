package com.pharmacy.dao;

import com.pharmacy.entities.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DAO class for managing customer-related database operations.
 */
public class CustomerDAO {
    private static final Logger LOGGER = Logger.getLogger(CustomerDAO.class.getName());
    private Connection connection;

    /**
     * Initializes a new CustomerDAO with the given database connection.
     *
     * @param connection the database connection
     */
    public CustomerDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Adds a new customer to the database.
     *
     * @param customer the customer to add
     */
    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (customer_id, name, contact_info) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, customer.getCustomerID());
            pstmt.setString(2, customer.getCustomerName());
            pstmt.setString(3, customer.getContactInfo());
            pstmt.executeUpdate();
            LOGGER.log(Level.INFO, "Customer added successfully: {0}", customer);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding customer: " + customer, e);
        }
    }

    /**
     * Retrieves a customer by its ID.
     *
     * @param customerId the customer ID
     * @return the found customer, or null if not found
     */
    public Customer getCustomerById(String customerId) {
        String sql = "SELECT * FROM customers WHERE customer_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, customerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Customer customer = new Customer(
                            rs.getString("customer_id"),
                            rs.getString("name"),
                            rs.getString("contact_info")
                    );
                    LOGGER.log(Level.INFO, "Customer retrieved successfully: {0}", customer);
                    return customer;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving customer by ID: " + customerId, e);
        }
        return null;
    }

    /**
     * Retrieves all customers from the database.
     *
     * @return a list of all customers
     */
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                customers.add(new Customer(
                        rs.getString("customer_id"),
                        rs.getString("name"),
                        rs.getString("contact_info")
                ));
            }
            LOGGER.log(Level.INFO, "All customers retrieved successfully");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving all customers", e);
        }
        return customers;
    }

    /**
     * Updates an existing customer in the database.
     *
     * @param customer the customer to update
     */
    public void updateCustomer(Customer customer) {
        String sql = "UPDATE customers SET name = ?, contact_info = ? WHERE customer_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, customer.getCustomerName());
            pstmt.setString(2, customer.getContactInfo());
            pstmt.setString(3, customer.getCustomerID());
            pstmt.executeUpdate();
            LOGGER.log(Level.INFO, "Customer updated successfully: {0}", customer);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating customer: " + customer, e);
        }
    }

    /**
     * Deletes a customer from the database by its ID.
     *
     * @param customerId the customer ID
     */
    public void deleteCustomer(String customerId) {
        String sql = "DELETE FROM customers WHERE customer_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, customerId);
            pstmt.executeUpdate();
            LOGGER.log(Level.INFO, "Customer deleted successfully: {0}", customerId);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting customer: " + customerId, e);
        }
    }
}
