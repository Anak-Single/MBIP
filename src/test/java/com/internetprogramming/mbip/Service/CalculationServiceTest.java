package com.internetprogramming.mbip.Service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.internetprogramming.mbip.Entity.ElectricData;
import com.internetprogramming.mbip.Entity.OilData;
import com.internetprogramming.mbip.Entity.RubbishData;
import com.internetprogramming.mbip.Entity.WaterData;

class CalculationServiceTest {

    private CalculationService calculationService;

    @BeforeEach
    void setUp() {
        calculationService = new CalculationService();
    }

    @Test
    void testCalculateTotalWater() {
        // Create test data
        WaterData water1 = new WaterData();
        water1.setWaterTotal(10.5);
        
        WaterData water2 = new WaterData();
        water2.setWaterTotal(15.3);
        
        List<WaterData> waterDataList = Arrays.asList(water1, water2);
        
        // Test calculation
        double result = calculationService.calculateTotalWater(waterDataList);
        
        // Assert
        assertEquals(25.8, result, 0.01);
    }

    @Test
    void testCalculateTotalElectric() {
        // Create test data
        ElectricData electric1 = new ElectricData();
        electric1.setElectricTotal(20.0);
        
        ElectricData electric2 = new ElectricData();
        electric2.setElectricTotal(30.5);
        
        List<ElectricData> electricDataList = Arrays.asList(electric1, electric2);
        
        // Test calculation
        double result = calculationService.calculateTotalElectric(electricDataList);
        
        // Assert
        assertEquals(50.5, result, 0.01);
    }

    @Test
    void testCalculateTotalOil() {
        // Create test data
        OilData oil1 = new OilData();
        oil1.setWeight(5.2);
        
        OilData oil2 = new OilData();
        oil2.setWeight(8.7);
        
        List<OilData> oilDataList = Arrays.asList(oil1, oil2);
        
        // Test calculation
        double result = calculationService.calculateTotalOil(oilDataList);
        
        // Assert
        assertEquals(13.9, result, 0.01);
    }

    @Test
    void testCalculateTotalRubbish() {
        // Create test data
        RubbishData rubbish1 = new RubbishData();
        rubbish1.setWeight(12.5);
        
        RubbishData rubbish2 = new RubbishData();
        rubbish2.setWeight(18.3);
        
        List<RubbishData> rubbishDataList = Arrays.asList(rubbish1, rubbish2);
        
        // Test calculation
        double result = calculationService.calculateTotalRubbish(rubbishDataList);
        
        // Assert
        assertEquals(30.8, result, 0.01);
    }

    @Test
    void testCalculateTotalWithEmptyList() {
        // Test with empty lists
        assertEquals(0.0, calculationService.calculateTotalWater(Collections.emptyList()), 0.01);
        assertEquals(0.0, calculationService.calculateTotalElectric(Collections.emptyList()), 0.01);
        assertEquals(0.0, calculationService.calculateTotalOil(Collections.emptyList()), 0.01);
        assertEquals(0.0, calculationService.calculateTotalRubbish(Collections.emptyList()), 0.01);
    }

    @Test
    void testCalculateTotalWithNullList() {
        // Test with null lists
        assertEquals(0.0, calculationService.calculateTotalWater(null), 0.01);
        assertEquals(0.0, calculationService.calculateTotalElectric(null), 0.01);
        assertEquals(0.0, calculationService.calculateTotalOil(null), 0.01);
        assertEquals(0.0, calculationService.calculateTotalRubbish(null), 0.01);
    }

    @Test
    void testCalculateCarbonEmission() {
        double rubbishWeight = 10.0;
        double waterBill = 20.0;
        double electricBill = 30.0;
        
        double expected = (rubbishWeight * CalculationService.RUBBISH_EMISSION_FACTOR) +
                         (waterBill * CalculationService.WATER_EMISSION_FACTOR) +
                         (electricBill * CalculationService.ELECTRIC_EMISSION_FACTOR);
        
        double result = calculationService.calculateCarbonEmission(rubbishWeight, waterBill, electricBill);
        
        assertEquals(expected, result, 0.01);
    }

    @Test
    void testCalculateTimeDifference() {
        LocalDateTime pastDate = LocalDateTime.now().minusDays(2).minusHours(5).minusMinutes(30);
        
        CalculationService.TimeDifference timeDiff = calculationService.calculateTimeDifference(pastDate);
        
        // Should be approximately 2 days, 5 hours, 30 minutes
        assertEquals(2, timeDiff.getDays());
        assertEquals(5, timeDiff.getHours());
        assertEquals(30, timeDiff.getMinutes());
    }

    @Test
    void testCalculateTimeDifferenceWithNullDate() {
        CalculationService.TimeDifference timeDiff = calculationService.calculateTimeDifference(null);
        
        assertEquals(0, timeDiff.getDays());
        assertEquals(0, timeDiff.getHours());
        assertEquals(0, timeDiff.getMinutes());
        assertEquals(0, timeDiff.getSeconds());
    }
} 