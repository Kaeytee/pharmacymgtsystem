package com.pharmacy.functionalities;

import com.pharmacy.entities.Drug;
import com.pharmacy.entities.Supplier;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages operations related to the pharmacy.
 */
public class Pharmacy {
    private DrugManager drugManager;
    private SupplierManager supplierManager;

    /**
     * Initializes a new Pharmacy.
     */
    public Pharmacy() {
        this.drugManager = new DrugManager();
        this.supplierManager = new SupplierManager();
    }

    /**
     * Adds a new drug.
     *
     * @param drug the drug to add
     */
    public void addDrug(Drug drug) {
        drugManager.addDrug(drug);
    }

    /**
     * Adds a new supplier.
     *
     * @param supplier the supplier to add
     */
    public void addSupplier(Supplier supplier) {
        supplierManager.addSupplier(supplier);
    }

    /**
     * Links a drug to a supplier.
     *
     * @param drug the drug to link
     * @param supplier the supplier to link
     * @throws IllegalArgumentException if the drug or supplier is null
     */
    public void linkDrugToSupplier(Drug drug, Supplier supplier) {
        if (drug == null) throw new IllegalArgumentException("Drug cannot be null.");
        if (supplier == null) throw new IllegalArgumentException("Supplier cannot be null.");
        drug.addSupplier(supplier);
        supplier.addDrug(drug);
    }

    /**
     * Searches for suppliers by location.
     *
     * @param location the location to search for
     * @return a list of suppliers in the specified location
     * @throws IllegalArgumentException if the location is null or empty
     */
    public ArrayList<Supplier> searchSuppliersByLocation(String location) {
        if (location == null || location.isEmpty()) throw new IllegalArgumentException("Location cannot be null or empty.");
        ArrayList<Supplier> suppliersByLocation = new ArrayList<>();
        for (Supplier supplier : supplierManager.listAllSuppliers()) {
            if (supplier.getLocation().equalsIgnoreCase(location)) {
                suppliersByLocation.add(supplier);
            }
        }
        return suppliersByLocation;
    }

    /**
     * Lists all drugs.
     *
     * @return a list of all drugs
     */
    public List<Drug> listAllDrugs() {
        return drugManager.getAllDrugs();
    }

    /**
     * Lists all suppliers.
     *
     * @return a list of all suppliers
     */
    public ArrayList<Supplier> listAllSuppliers() {
        return supplierManager.listAllSuppliers();
    }
}
