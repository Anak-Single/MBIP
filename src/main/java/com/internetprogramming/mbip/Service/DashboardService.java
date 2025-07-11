package com.internetprogramming.mbip.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.internetprogramming.mbip.Entity.ElectricData;
import com.internetprogramming.mbip.Entity.OilData;
import com.internetprogramming.mbip.Entity.RubbishData;
import com.internetprogramming.mbip.Entity.WaterData;
import com.internetprogramming.mbip.dto.DashboardData;

import jakarta.annotation.Resource;

@Service
public class DashboardService {

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
     * Prepare dashboard data with totals and time difference
     */
    public DashboardData prepareDashboardData() {
        // Fetch all data
        List<WaterData> waterData = waterDao.findAllData();
        List<ElectricData> electricData = electricDao.findAllData();
        List<OilData> oilData = oilDao.findAllData();
        List<RubbishData> rubbishData = rubbishDao.findAllData();

        // Calculate totals using the calculation service
        double totalWater = calculationService.calculateTotalWater(waterData);
        double totalElectric = calculationService.calculateTotalElectric(electricData);
        double totalOil = calculationService.calculateTotalOil(oilData);
        double totalRubbish = calculationService.calculateTotalRubbish(rubbishData);

        // Create dashboard data object
        DashboardData dashboardData = new DashboardData(totalWater, totalElectric, totalOil, totalRubbish);

        // Calculate time difference
        CalculationService.TimeDifference timeDiff = calculationService.calculateTimeDifference(
            calculationService.findLatestUpdateTime(waterData, electricData, rubbishData, oilData)
        );

        // Set time components
        dashboardData.setDays(timeDiff.getDays());
        dashboardData.setHours(timeDiff.getHours());
        dashboardData.setMinutes(timeDiff.getMinutes());
        dashboardData.setSeconds(timeDiff.getSeconds());

        return dashboardData;
    }

    /**
     * Prepare dashboard data for a specific area
     */
    public DashboardData prepareAreaDashboardData(String area) {
        // This method would be used for area-specific dashboards
        // Implementation would filter data by area first
        return prepareDashboardData(); // For now, return general dashboard data
    }
} 