package com.pharmacy.functionalities;

import com.pharmacy.entities.Drug;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Manages drug-related operations.
 */
public class DrugManager {
    private List<Drug> drugs;

    /**
     * Initializes a new DrugManager.
     */
    public DrugManager() {
        this.drugs = new ArrayList<>();
    }

    /**
     * Adds a new drug.
     *
     * @param drug the drug to add
     * @throws IllegalArgumentException if the drug is null or already exists
     */
    public void addDrug(Drug drug) {
        if (drug == null) throw new IllegalArgumentException("Drug cannot be null.");
        if (containsDrug(drug)) throw new IllegalArgumentException("Drug already exists.");
        this.drugs.add(drug);
    }

    /**
     * Updates the information of an existing drug.
     *
     * @param drug the drug with updated information
     * @throws IllegalArgumentException if the drug is null or does not exist
     */
    public void updateDrug(Drug drug) {
        if (drug == null) throw new IllegalArgumentException("Drug cannot be null.");
        int index = this.drugs.indexOf(drug);
        if (index == -1) throw new IllegalArgumentException("Drug does not exist.");
        this.drugs.set(index, drug);
    }

    /**
     * Removes an existing drug.
     *
     * @param drug the drug to remove
     * @throws IllegalArgumentException if the drug is null
     */
    public void removeDrug(Drug drug) {
        if (drug == null) throw new IllegalArgumentException("Drug cannot be null.");
        this.drugs.remove(drug);
    }

    /**
     * Searches for a drug by name.
     *
     * @param drugName the drug name to search for
     * @return an Optional containing the found drug, or empty if not found
     * @throws IllegalArgumentException if the drug name is null or empty
     */
    public Optional<Drug> searchDrugByName(String drugName) {
        if (drugName == null || drugName.isEmpty()) throw new IllegalArgumentException("Drug name cannot be null or empty.");
        return drugs.stream()
                .filter(drug -> drug.getDrugName().equalsIgnoreCase(drugName))
                .findFirst();
    }

    /**
     * Checks if the drug already exists.
     *
     * @param drug the drug to check
     * @return true if the drug exists, false otherwise
     */
    private boolean containsDrug(Drug drug) {
        return this.drugs.contains(drug);
    }

    /**
     * Returns all drugs.
     *
     * @return a list of all drugs
     */
    public List<Drug> getAllDrugs() {
        return new ArrayList<>(this.drugs);
    }
}
