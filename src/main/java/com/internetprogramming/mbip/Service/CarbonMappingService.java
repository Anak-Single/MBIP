package com.internetprogramming.mbip.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.internetprogramming.mbip.Entity.ElectricData;
import com.internetprogramming.mbip.Entity.OilData;
import com.internetprogramming.mbip.Entity.RubbishData;
import com.internetprogramming.mbip.Entity.User;
import com.internetprogramming.mbip.Entity.WaterData;
import com.internetprogramming.mbip.dto.AreaDataDTO;

import jakarta.annotation.Resource;

@Service
public class CarbonMappingService {

    @Resource(name = "userDao")
    private UserDao userDao;

    @Resource(name = "waterDao")
    private WaterDao waterDao;

    @Resource(name = "electricDao")
    private ElectricDao electricDao;

    @Resource(name = "oilDao")
    private OilDao oilDao;

    @Resource(name = "rubbishDao")
    private RubbishDao rubbishDao;

    @Resource(name = "calculationService")
    private CalculationService calculationService;

    /**
     * Process data for a specific area
     */
    public AreaDataDTO processAreaData(String areaName) {
        AreaDataDTO areaData = new AreaDataDTO(areaName);
        
        // Step 1: Find users in the area
        List<User> usersInArea = findUsersInArea(areaName);
        areaData.setUsersInArea(usersInArea);
        
        // Step 2: Collect all data for users in this area
        collectUserData(areaData, usersInArea);
        
        // Step 3: Calculate totals
        calculateTotals(areaData);
        
        return areaData;
    }

    /**
     * Find all users in a specific area
     */
    private List<User> findUsersInArea(String areaName) {
        List<User> usersInArea = new ArrayList<>();
        List<User> allUsers = userDao.findAllUser();
        
        for (User user : allUsers) {
            if (user.getHomeArea().equals(areaName)) {
                usersInArea.add(user);
            }
        }
        
        return usersInArea;
    }

    /**
     * Collect all data for users in the area
     */
    private void collectUserData(AreaDataDTO areaData, List<User> usersInArea) {
        List<RubbishData> rubbishData = new ArrayList<>();
        List<OilData> oilData = new ArrayList<>();
        List<WaterData> waterData = new ArrayList<>();
        List<ElectricData> electricData = new ArrayList<>();

        for (User user : usersInArea) {
            // Collect rubbish data
            rubbishData.addAll(rubbishDao.findDataByUserId(user.getId()));
            
            // Collect oil data
            oilData.addAll(oilDao.findDataByUserId(user.getId()));
            
            // Collect water data
            waterData.addAll(waterDao.findBillsByUserId(user.getId()));
            
            // Collect electric data
            electricData.addAll(electricDao.findBillsByUserId(user.getId()));
        }

        areaData.setRubbishData(rubbishData);
        areaData.setOilData(oilData);
        areaData.setWaterData(waterData);
        areaData.setElectricData(electricData);
    }

    /**
     * Calculate totals for the area
     */
    private void calculateTotals(AreaDataDTO areaData) {
        // Calculate rubbish total
        double totalRubbishWeight = calculationService.calculateTotalRubbish(areaData.getRubbishData());
        areaData.setTotalRubbishWeight(totalRubbishWeight);

        // Calculate oil total
        double totalOilWeight = calculationService.calculateTotalOil(areaData.getOilData());
        areaData.setTotalOilWeight(totalOilWeight);

        // Calculate water total
        double totalWaterBill = calculationService.calculateTotalWater(areaData.getWaterData());
        areaData.setTotalWaterBill(totalWaterBill);

        // Calculate electric total
        double totalElectricBill = calculationService.calculateTotalElectric(areaData.getElectricData());
        areaData.setTotalElectricBill(totalElectricBill);
    }

    /**
     * Add area data to model attributes
     */
    public void addAreaDataToModel(AreaDataDTO areaData, org.springframework.ui.Model model) {
        String areaName = areaData.getAreaName();
        
        model.addAttribute("totalWeight" + areaName, areaData.getTotalRubbishWeight());
        model.addAttribute("oilWeight" + areaName, areaData.getTotalOilWeight());
        model.addAttribute("waterBill" + areaName, areaData.getTotalWaterBill());
        model.addAttribute("electricBill" + areaName, areaData.getTotalElectricBill());
    }

    /**
     * Process multiple areas and add to model
     */
    public void processMultipleAreas(List<String> areaNames, org.springframework.ui.Model model) {
        for (String areaName : areaNames) {
            AreaDataDTO areaData = processAreaData(areaName);
            addAreaDataToModel(areaData, model);
        }
    }

    /**
     * Get all water data for time calculation
     */
    public List<WaterData> getAllWaterData() {
        return waterDao.findAllData();
    }

    /**
     * Get all electric data for time calculation
     */
    public List<ElectricData> getAllElectricData() {
        return electricDao.findAllData();
    }

    /**
     * Get all rubbish data for time calculation
     */
    public List<RubbishData> getAllRubbishData() {
        return rubbishDao.findAllData();
    }

    /**
     * Get all oil data for time calculation
     */
    public List<OilData> getAllOilData() {
        return oilDao.findAllData();
    }
} 