package com.internetprogramming.mbip.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.internetprogramming.mbip.Entity.ElectricData;
import com.internetprogramming.mbip.Entity.OilData;
import com.internetprogramming.mbip.Entity.RubbishData;
import com.internetprogramming.mbip.Entity.WaterData;

@Service
public class CalculationService {

    // Carbon emission factors - extracted from hardcoded values
    public static final double RUBBISH_EMISSION_FACTOR = 2.86;
    public static final double WATER_EMISSION_FACTOR = 0.419;
    public static final double ELECTRIC_EMISSION_FACTOR = 0.584;

    /**
     * Calculate carbon emission based on consumption data
     */
    public double calculateCarbonEmission(double rubbishWeight, double waterBill, double electricBill) {
        return (rubbishWeight * RUBBISH_EMISSION_FACTOR) +
               (waterBill * WATER_EMISSION_FACTOR) +
               (electricBill * ELECTRIC_EMISSION_FACTOR);
    }

    /**
     * Calculate total from a list of data using a getter function
     */
    public <T> double calculateTotal(List<T> dataList, Function<T, Double> getter) {
        if (dataList == null || dataList.isEmpty()) {
            return 0.0;
        }
        return dataList.stream()
                .mapToDouble(item -> {
                    Double value = getter.apply(item);
                    return value != null ? value : 0.0;
                })
                .sum();
    }

    /**
     * Calculate total water consumption
     */
    public double calculateTotalWater(List<WaterData> waterDataList) {
        return calculateTotal(waterDataList, WaterData::getWaterTotal);
    }

    /**
     * Calculate total electric consumption
     */
    public double calculateTotalElectric(List<ElectricData> electricDataList) {
        return calculateTotal(electricDataList, ElectricData::getElectricTotal);
    }

    /**
     * Calculate total oil weight
     */
    public double calculateTotalOil(List<OilData> oilDataList) {
        return calculateTotal(oilDataList, OilData::getWeight);
    }

    /**
     * Calculate total rubbish weight
     */
    public double calculateTotalRubbish(List<RubbishData> rubbishDataList) {
        return calculateTotal(rubbishDataList, RubbishData::getWeight);
    }

    /**
     * Find the latest update time from multiple data lists
     */
    public LocalDateTime findLatestUpdateTime(List<WaterData> waterData, 
                                            List<ElectricData> electricData,
                                            List<RubbishData> rubbishData,
                                            List<OilData> oilData) {
        LocalDateTime latestDate = null;

        // Check water data
        if (waterData != null && !waterData.isEmpty()) {
            WaterData latestWater = waterData.stream()
                    .max(Comparator.comparing(WaterData::getUpdateTime))
                    .orElse(null);
            if (latestWater != null && (latestDate == null || latestWater.getUpdateTime().isAfter(latestDate))) {
                latestDate = latestWater.getUpdateTime();
            }
        }

        // Check electric data
        if (electricData != null && !electricData.isEmpty()) {
            ElectricData latestElectric = electricData.stream()
                    .max(Comparator.comparing(ElectricData::getUpdateTime))
                    .orElse(null);
            if (latestElectric != null && (latestDate == null || latestElectric.getUpdateTime().isAfter(latestDate))) {
                latestDate = latestElectric.getUpdateTime();
            }
        }

        // Check rubbish data
        if (rubbishData != null && !rubbishData.isEmpty()) {
            RubbishData latestRubbish = rubbishData.stream()
                    .max(Comparator.comparing(RubbishData::getUpdateTime))
                    .orElse(null);
            if (latestRubbish != null && (latestDate == null || latestRubbish.getUpdateTime().isAfter(latestDate))) {
                latestDate = latestRubbish.getUpdateTime();
            }
        }

        // Check oil data
        if (oilData != null && !oilData.isEmpty()) {
            OilData latestOil = oilData.stream()
                    .max(Comparator.comparing(OilData::getUpdateTime))
                    .orElse(null);
            if (latestOil != null && (latestDate == null || latestOil.getUpdateTime().isAfter(latestDate))) {
                latestDate = latestOil.getUpdateTime();
            }
        }

        return latestDate;
    }

    /**
     * Calculate time difference components from a reference date
     */
    public TimeDifference calculateTimeDifference(LocalDateTime referenceDate) {
        if (referenceDate == null) {
            return new TimeDifference(0, 0, 0, 0);
        }

        Duration timeDifference = Duration.between(referenceDate, LocalDateTime.now());
        
        return new TimeDifference(
            timeDifference.toDays(),
            timeDifference.toHoursPart(),
            timeDifference.toMinutesPart(),
            timeDifference.toSecondsPart()
        );
    }

    /**
     * Inner class to hold time difference components
     */
    public static class TimeDifference {
        private final long days;
        private final long hours;
        private final long minutes;
        private final long seconds;

        public TimeDifference(long days, long hours, long minutes, long seconds) {
            this.days = days;
            this.hours = hours;
            this.minutes = minutes;
            this.seconds = seconds;
        }

        // Getters
        public long getDays() { return days; }
        public long getHours() { return hours; }
        public long getMinutes() { return minutes; }
        public long getSeconds() { return seconds; }
    }
} 