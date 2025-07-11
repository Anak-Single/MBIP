package com.internetprogramming.mbip.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.internetprogramming.mbip.Entity.User;
import com.internetprogramming.mbip.Entity.WaterData;
import com.internetprogramming.mbip.Entity.ElectricData;
import com.internetprogramming.mbip.dto.UserDataSummary;

import jakarta.annotation.Resource;

@Service
public class DataEntryService {

    @Resource(name = "userDao")
    private UserDao userDao;

    @Resource(name = "waterDao")
    private WaterDao waterDao;

    @Resource(name = "electricDao")
    private ElectricDao electricDao;

    @Resource(name = "calculationService")
    private CalculationService calculationService;

    /**
     * Get current authenticated user
     */
    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userDao.findByUserName(username);
    }

    /**
     * Prepare water data summary for a user
     */
    public UserDataSummary prepareWaterDataSummary(Long userId) {
        List<WaterData> waterData = waterDao.findBillsByUserId(userId);
        return createDataSummary(waterData, WaterData::getCreationTime, WaterData::getBillAmount);
    }

    /**
     * Prepare electric data summary for a user
     */
    public UserDataSummary prepareElectricDataSummary(Long userId) {
        List<ElectricData> electricData = electricDao.findBillsByUserId(userId);
        return createDataSummary(electricData, ElectricData::getCreationTime, ElectricData::getBillAmount);
    }

    /**
     * Generic method to create data summary from any data type
     */
    private <T> UserDataSummary createDataSummary(List<T> dataList, 
                                                 java.util.function.Function<T, LocalDate> dateExtractor,
                                                 java.util.function.Function<T, Double> amountExtractor) {
        UserDataSummary summary = new UserDataSummary();
        
        if (dataList == null || dataList.isEmpty()) {
            return summary;
        }

        // Extract unique creation dates
        List<LocalDate> creationDates = new ArrayList<>();
        for (T data : dataList) {
            LocalDate creationDate = dateExtractor.apply(data);
            if (!creationDates.contains(creationDate)) {
                creationDates.add(creationDate);
            }
        }

        // Sort dates in descending order
        Collections.sort(creationDates, Collections.reverseOrder());

        // Calculate price per date
        double[] pricePerDate = new double[creationDates.size()];
        for (T data : dataList) {
            LocalDate creationDate = dateExtractor.apply(data);
            int index = creationDates.indexOf(creationDate);
            if (index >= 0) {
                pricePerDate[index] += amountExtractor.apply(data);
            }
        }

        summary.setCreationDates(creationDates);
        summary.setPricePerDate(pricePerDate);
        summary.setIndex(0);

        return summary;
    }

    /**
     * Save water bill data
     */
    public void saveWaterBill(Double waterTotal, String billID, String tarikhBill, Double billAmount) {
        User currentUser = getCurrentUser();
        WaterData waterData = new WaterData(waterTotal, billID, tarikhBill, billAmount);
        waterData.setUser(currentUser);
        waterDao.saveData(waterData);
    }

    /**
     * Save electric bill data
     */
    public void saveElectricBill(Double electricTotal, String billID, String tarikhBill, Double billAmount) {
        User currentUser = getCurrentUser();
        ElectricData electricData = new ElectricData(electricTotal, billID, tarikhBill, billAmount);
        electricData.setUser(currentUser);
        electricDao.saveData(electricData);
    }
} 