package com.internetprogramming.mbip.dto;

import java.util.List;

import com.internetprogramming.mbip.Entity.ElectricData;
import com.internetprogramming.mbip.Entity.OilData;
import com.internetprogramming.mbip.Entity.RubbishData;
import com.internetprogramming.mbip.Entity.User;
import com.internetprogramming.mbip.Entity.WaterData;

/**
 * DTO to hold area-specific data for carbon mapping
 */
public class AreaDataDTO {
    private String areaName;
    private List<User> usersInArea;
    private List<RubbishData> rubbishData;
    private List<OilData> oilData;
    private List<WaterData> waterData;
    private List<ElectricData> electricData;
    
    // Calculated totals
    private double totalRubbishWeight;
    private double totalOilWeight;
    private double totalWaterBill;
    private double totalElectricBill;

    public AreaDataDTO(String areaName) {
        this.areaName = areaName;
    }

    // Getters and Setters
    public String getAreaName() { return areaName; }
    public void setAreaName(String areaName) { this.areaName = areaName; }

    public List<User> getUsersInArea() { return usersInArea; }
    public void setUsersInArea(List<User> usersInArea) { this.usersInArea = usersInArea; }

    public List<RubbishData> getRubbishData() { return rubbishData; }
    public void setRubbishData(List<RubbishData> rubbishData) { this.rubbishData = rubbishData; }

    public List<OilData> getOilData() { return oilData; }
    public void setOilData(List<OilData> oilData) { this.oilData = oilData; }

    public List<WaterData> getWaterData() { return waterData; }
    public void setWaterData(List<WaterData> waterData) { this.waterData = waterData; }

    public List<ElectricData> getElectricData() { return electricData; }
    public void setElectricData(List<ElectricData> electricData) { this.electricData = electricData; }

    public double getTotalRubbishWeight() { return totalRubbishWeight; }
    public void setTotalRubbishWeight(double totalRubbishWeight) { this.totalRubbishWeight = totalRubbishWeight; }

    public double getTotalOilWeight() { return totalOilWeight; }
    public void setTotalOilWeight(double totalOilWeight) { this.totalOilWeight = totalOilWeight; }

    public double getTotalWaterBill() { return totalWaterBill; }
    public void setTotalWaterBill(double totalWaterBill) { this.totalWaterBill = totalWaterBill; }

    public double getTotalElectricBill() { return totalElectricBill; }
    public void setTotalElectricBill(double totalElectricBill) { this.totalElectricBill = totalElectricBill; }

    /**
     * Get the total number of households in this area
     */
    public int getTotalHouseholds() {
        if (usersInArea == null) return 0;
        return usersInArea.stream().mapToInt(User::getHouseHold).sum();
    }
} 