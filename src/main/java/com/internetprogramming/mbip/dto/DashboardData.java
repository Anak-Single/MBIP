package com.internetprogramming.mbip.dto;

import java.text.DecimalFormat;

/**
 * DTO to hold dashboard statistics data
 */
public class DashboardData {
    private double totalWater;
    private double totalElectric;
    private double totalOil;
    private double totalRubbish;
    private long days;
    private long hours;
    private long minutes;
    private long seconds;

    public DashboardData() {}

    public DashboardData(double totalWater, double totalElectric, double totalOil, double totalRubbish) {
        this.totalWater = totalWater;
        this.totalElectric = totalElectric;
        this.totalOil = totalOil;
        this.totalRubbish = totalRubbish;
    }

    // Getters and Setters
    public double getTotalWater() { return totalWater; }
    public void setTotalWater(double totalWater) { this.totalWater = totalWater; }

    public double getTotalElectric() { return totalElectric; }
    public void setTotalElectric(double totalElectric) { this.totalElectric = totalElectric; }

    public double getTotalOil() { return totalOil; }
    public void setTotalOil(double totalOil) { this.totalOil = totalOil; }

    public double getTotalRubbish() { return totalRubbish; }
    public void setTotalRubbish(double totalRubbish) { this.totalRubbish = totalRubbish; }

    public long getDays() { return days; }
    public void setDays(long days) { this.days = days; }

    public long getHours() { return hours; }
    public void setHours(long hours) { this.hours = hours; }

    public long getMinutes() { return minutes; }
    public void setMinutes(long minutes) { this.minutes = minutes; }

    public long getSeconds() { return seconds; }
    public void setSeconds(long seconds) { this.seconds = seconds; }

    /**
     * Get formatted water total
     */
    public String getFormattedTotalWater() {
        return new DecimalFormat("#.##").format(totalWater);
    }

    /**
     * Get formatted electric total
     */
    public String getFormattedTotalElectric() {
        return new DecimalFormat("#.##").format(totalElectric);
    }

    /**
     * Get formatted oil total
     */
    public String getFormattedTotalOil() {
        return new DecimalFormat("#.##").format(totalOil);
    }

    /**
     * Get formatted rubbish total
     */
    public String getFormattedTotalRubbish() {
        return new DecimalFormat("#.##").format(totalRubbish);
    }
} 