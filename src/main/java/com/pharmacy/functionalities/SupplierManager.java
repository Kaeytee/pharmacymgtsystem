package com.pharmacy.functionalities;

import com.pharmacy.entities.Drug;
import com.pharmacy.entities.Supplier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Manages supplier-related operations.
 */
public class SupplierManager {
    private List<Supplier> suppliers;

    /**
     * Initializes a new SupplierManager.
     */
    public SupplierManager() {
        this.suppliers = new ArrayList<>();
    }

    /**
     * Adds a new supplier.
     *
     * @param supplier the supplier to add
     * @throws IllegalArgumentException if the supplier is null or already exists
     */
    public void addSupplier(Supplier supplier) {
        if (supplier == null) throw new IllegalArgumentException("Supplier cannot be null.");
        if (containsSupplier(supplier)) throw new IllegalArgumentException("Supplier already exists.");
        this.suppliers.add(supplier);
    }

    /**
     * Removes an existing supplier.
     *
     * @param supplier the supplier to remove
     * @throws IllegalArgumentException if the supplier is null
     */
    public void removeSupplier(Supplier supplier) {
        if (supplier == null) throw new IllegalArgumentException("Supplier cannot be null.");
        this.suppliers.remove(supplier);
    }

    /**
     * Searches for a supplier by name.
     *
     * @param supplierName the supplier name to search for
     * @return an Optional containing the found supplier, or empty if not found
     * @throws IllegalArgumentException if the supplier name is null or empty
     */
    public Optional<Supplier> searchSupplierByName(String supplierName) {
        if (supplierName == null || supplierName.isEmpty()) throw new IllegalArgumentException("Supplier name cannot be null or empty.");
        return suppliers.stream()
                .filter(supplier -> supplier.getSupplierName().equalsIgnoreCase(supplierName))
                .findFirst();
    }

    /**
     * Lists all suppliers.
     *
     * @return a list of all suppliers
     */
    public ArrayList<Supplier> listAllSuppliers() {
        return new ArrayList<>(suppliers);
    }

    /**
     * Checks if a supplier exists.
     *
     * @param supplier the supplier to check
     * @return true if the supplier exists, false otherwise
     * @throws IllegalArgumentException if the supplier is null
     */
    public boolean containsSupplier(Supplier supplier) {
        if (supplier == null) throw new IllegalArgumentException("Supplier cannot be null.");
        return this.suppliers.contains(supplier);
    }

    /**
     * Updates the information of an existing supplier.
     *
     * @param supplier the supplier with updated information
     * @throws IllegalArgumentException if the supplier is null or does not exist
     */
    public void updateSupplier(Supplier supplier) {
        if (supplier == null) throw new IllegalArgumentException("Drug cannot be null.");
        int index = this.suppliers.indexOf(supplier);
        if (index == -1) throw new IllegalArgumentException("Drug does not exist.");
        this.suppliers.set(index, supplier);
    }
}
