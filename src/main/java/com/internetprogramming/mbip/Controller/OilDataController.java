package com.internetprogramming.mbip.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.internetprogramming.mbip.Entity.OilData;
import com.internetprogramming.mbip.Entity.User;
import com.internetprogramming.mbip.Service.DataEntryService;
import com.internetprogramming.mbip.Service.OilDao;
import com.internetprogramming.mbip.Service.CalculationService;

import jakarta.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/oil")
public class OilDataController {

    @Resource(name = "dataEntryService")
    private DataEntryService dataEntryService;

    @Resource(name = "oilDao")
    private OilDao oilDao;

    @Resource(name = "calculationService")
    private CalculationService calculationService;

    /**
     * Show oil data entry page
     */
    @GetMapping("")
    public String showOilData(Model model) {
        User currentUser = dataEntryService.getCurrentUser();
        List<OilData> oilData = oilDao.findDataByUserId(currentUser.getId());
        
        double totalWeight = calculationService.calculateTotalOil(oilData);
        model.addAttribute("totalweight", totalWeight);
        
        return "MasukkanData/Minyak";
    }

    /**
     * Upload oil data
     */
    @GetMapping("/upload")
    public String uploadOilData(Model model,
                               @RequestParam("Weight") Double weight) {
        
        User currentUser = dataEntryService.getCurrentUser();
        
        // Create and save oil data
        OilData oil = new OilData(weight);
        oil.setUser(currentUser);
        oilDao.saveData(oil);
        
        // Get updated total weight
        List<OilData> oilData = oilDao.findDataByUserId(currentUser.getId());
        double totalWeight = calculationService.calculateTotalOil(oilData);
        model.addAttribute("totalweight", totalWeight);
        
        return "MasukkanData/Minyak";
    }
} 