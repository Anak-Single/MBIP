package com.internetprogramming.mbip.Controller;

import com.internetprogramming.mbip.Entity.HomeArea;
import com.internetprogramming.mbip.Entity.OilData;
import com.internetprogramming.mbip.Entity.WaterData;
import com.internetprogramming.mbip.Entity.ElectricData;
import com.internetprogramming.mbip.Entity.User;
import com.internetprogramming.mbip.Entity.RubbishData;
import com.internetprogramming.mbip.Service.ElectricDao;
import com.internetprogramming.mbip.Service.OilDao;
import com.internetprogramming.mbip.Service.RubbishDao;
import com.internetprogramming.mbip.Service.UserDao;
import com.internetprogramming.mbip.Service.WaterDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

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
            waterDataInArea.addAll(waterDao.findDataByUserId(user.getId()));
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