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

        WaterData latestWaterData = water.stream().max(Comparator.comparing(WaterData::getUpdateTime)).orElse(null);
        ElectricData latestElectricData = electric.stream().max(Comparator.comparing(ElectricData::getUpdateTime))
                .orElse(null);
        RubbishData latestRubbishData = rubbish.stream().max(Comparator.comparing(RubbishData::getUpdateTime))
                .orElse(null);
        OilData latestOilData = oil.stream().max(Comparator.comparing(OilData::getUpdateTime)).orElse(null);

        LocalDateTime latestDate = null;

        if (latestWaterData != null && (latestDate == null || latestWaterData.getUpdateTime().isAfter(latestDate))) {
            latestDate = latestWaterData.getUpdateTime();
        }
        if (latestElectricData != null
                && (latestDate == null || latestElectricData.getUpdateTime().isAfter(latestDate))) {
            latestDate = latestElectricData.getUpdateTime();
        }
        if (latestRubbishData != null
                && (latestDate == null || latestRubbishData.getUpdateTime().isAfter(latestDate))) {
            latestDate = latestRubbishData.getUpdateTime();
        }
        if (latestOilData != null && (latestDate == null || latestOilData.getUpdateTime().isAfter(latestDate))) {
            latestDate = latestOilData.getUpdateTime();
        }

        long days = 0;
        long hours = 0;
        long minutes = 0;
        long seconds = 0;

        Duration timeDifference = Duration.between(latestDate, LocalDateTime.now());

        days = timeDifference.toDays();
        hours = timeDifference.toHoursPart();
        minutes = timeDifference.toMinutesPart();
        seconds = timeDifference.toSecondsPart();

        model.addAttribute("days", days);
        model.addAttribute("hours", hours);
        model.addAttribute("minutes", minutes);
        model.addAttribute("seconds", seconds);

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

        for (RubbishData rubbishData : rubbishDataInArea) {
            totalWeight += rubbishData.getWeight();
        }

        model.addAttribute("totalWeight" + area, totalWeight);

        // Oil
        List<OilData> oilDataInArea = new ArrayList<>();

        for (User user : usersInArea) {
            oilDataInArea.addAll(oilDao.findDataByUserId(user.getId()));
        }

        for (OilData oilData : oilDataInArea) {
            oilWeight += oilData.getWeight();
        }

        model.addAttribute("oilWeight" + area, oilWeight);

        // Water
        List<WaterData> waterDataInArea = new ArrayList<>();

        for (User user : usersInArea) {
            waterDataInArea.addAll(waterDao.findBillsByUserId(user.getId()));
        }

        for (WaterData waterData : waterDataInArea) {
            waterBill += waterData.getBillAmount();
        }

        model.addAttribute("waterBill" + area, waterBill);

        // Electric
        List<ElectricData> electricDataInArea = new ArrayList<>();

        for (User user : usersInArea) {
            electricDataInArea.addAll(electricDao.findBillsByUserId(user.getId()));
        }

        for (ElectricData electricData : electricDataInArea) {
            electricBill += electricData.getBillAmount();
        }

        model.addAttribute("electricBill" + area, electricBill);
    }

}