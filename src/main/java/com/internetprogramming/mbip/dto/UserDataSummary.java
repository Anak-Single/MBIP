package com.internetprogramming.mbip.dto;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO to hold user data summary information
 */
public class UserDataSummary {
    private List<LocalDate> creationDates;
    private double[] pricePerDate;
    private int index;

    public UserDataSummary() {}

    public UserDataSummary(List<LocalDate> creationDates, double[] pricePerDate, int index) {
        this.creationDates = creationDates;
        this.pricePerDate = pricePerDate;
        this.index = index;
    }

    // Getters and Setters
    public List<LocalDate> getCreationDates() { return creationDates; }
    public void setCreationDates(List<LocalDate> creationDates) { this.creationDates = creationDates; }

    public double[] getPricePerDate() { return pricePerDate; }
    public void setPricePerDate(double[] pricePerDate) { this.pricePerDate = pricePerDate; }

    public int getIndex() { return index; }
    public void setIndex(int index) { this.index = index; }

    /**
     * Check if summary has data
     */
    public boolean hasData() {
        return creationDates != null && !creationDates.isEmpty();
    }
} 