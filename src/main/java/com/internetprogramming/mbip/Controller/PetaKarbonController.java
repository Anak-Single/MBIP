package com.internetprogramming.mbip.Controller;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.internetprogramming.mbip.Entity.HomeArea;

import com.internetprogramming.mbip.Entity.ElectricData;
import com.internetprogramming.mbip.Entity.OilData;
import com.internetprogramming.mbip.Entity.RubbishData;
import com.internetprogramming.mbip.Entity.User;
import com.internetprogramming.mbip.Entity.WaterData;
import com.internetprogramming.mbip.Service.ElectricDao;
import com.internetprogramming.mbip.Service.OilDao;
import com.internetprogramming.mbip.Service.RubbishDao;
import com.internetprogramming.mbip.Service.UserDao;
import com.internetprogramming.mbip.Service.WaterDao;
import com.internetprogramming.mbip.Service.CalculationService;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PetaKarbonController {

    @Autowired
    private RubbishDao rubbishDao;

    @Autowired
    private OilDao oilDao;

    @Autowired
    private WaterDao waterDao;

    @Autowired
    private ElectricDao electricDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CalculationService calculationService;

    @GetMapping("/petaKarbon")

    public String petaKarbon(Model model) {
        processArea("SKUDAI", model);
        processArea("LIMA_KEDAI", model);
        processArea("GELANG_PATAH", model);
        processArea("KANGKAR_PULAI", model);
        processArea("ISKANDAR_PUTERI", model);
        processArea("ULU_CHOH", model);
        
        List<WaterData> water = waterDao.findAllData();
        List<ElectricData> electric = electricDao.findAllData();
        List<RubbishData> rubbish = rubbishDao.findAllData();
        List<OilData> oil = oilDao.findAllData();

        // Use calculation service to find latest update time and calculate time difference
        var latestDate = calculationService.findLatestUpdateTime(water, electric, rubbish, oil);
        var timeDiff = calculationService.calculateTimeDifference(latestDate);

        model.addAttribute("days", timeDiff.getDays());
        model.addAttribute("hours", timeDiff.getHours());
        model.addAttribute("minutes", timeDiff.getMinutes());
        model.addAttribute("seconds", timeDiff.getSeconds());

        return "petaKarbon";
    }

    private void processArea(String area, Model model) {
        double totalWeight = 0.0;
        double oilWeight = 0.0;
        double waterBill = 0.0;
        double electricBill = 0.0;

        List<User> usersInArea = new ArrayList<>();

        // Rubbish
        List<RubbishData> rubbishDataInArea = new ArrayList<>();

        for (User user : userDao.findAllUser()) {
            if (user.getHomeArea().equals(area)) {
                usersInArea.add(user);

                // Rubbish
                rubbishDataInArea.addAll(rubbishDao.findDataByUserId(user.getId()));
            }
        }

        // Use calculation service to calculate totals
        totalWeight = calculationService.calculateTotalRubbish(rubbishDataInArea);
        model.addAttribute("totalWeight" + area, totalWeight);

        // Oil
        List<OilData> oilDataInArea = new ArrayList<>();
        for (User user : usersInArea) {
            oilDataInArea.addAll(oilDao.findDataByUserId(user.getId()));
        }
        oilWeight = calculationService.calculateTotalOil(oilDataInArea);
        model.addAttribute("oilWeight" + area, oilWeight);

        // Water
        List<WaterData> waterDataInArea = new ArrayList<>();
        for (User user : usersInArea) {
            waterDataInArea.addAll(waterDao.findBillsByUserId(user.getId()));
        }
        waterBill = calculationService.calculateTotalWater(waterDataInArea);
        model.addAttribute("waterBill" + area, waterBill);

        // Electric
        List<ElectricData> electricDataInArea = new ArrayList<>();
        for (User user : usersInArea) {
            electricDataInArea.addAll(electricDao.findBillsByUserId(user.getId()));
        }
        electricBill = calculationService.calculateTotalElectric(electricDataInArea);
        model.addAttribute("electricBill" + area, electricBill);
    }
        
}

