package com.internetprogramming.mbip.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.internetprogramming.mbip.Entity.RubbishData;
import com.internetprogramming.mbip.Entity.User;
import com.internetprogramming.mbip.Service.DataEntryService;
import com.internetprogramming.mbip.Service.RubbishDao;
import com.internetprogramming.mbip.Service.CalculationService;

import jakarta.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/rubbish")
public class RubbishDataController {

    @Resource(name = "dataEntryService")
    private DataEntryService dataEntryService;

    @Resource(name = "rubbishDao")
    private RubbishDao rubbishDao;

    @Resource(name = "calculationService")
    private CalculationService calculationService;

    /**
     * Show rubbish data entry page
     */
    @GetMapping("")
    public String showRubbishData(Model model) {
        User currentUser = dataEntryService.getCurrentUser();
        List<RubbishData> rubbishData = rubbishDao.findDataByUserId(currentUser.getId());
        
        double totalWeight = calculationService.calculateTotalRubbish(rubbishData);
        model.addAttribute("totalweight", totalWeight);
        
        return "MasukkanData/Sampah";
    }

    /**
     * Upload rubbish data
     */
    @GetMapping("/upload")
    public String uploadRubbishData(Model model,
                                   @RequestParam("Weight") Double weight,
                                   @RequestParam("Category") String category) {
        
        User currentUser = dataEntryService.getCurrentUser();
        
        // Create and save rubbish data
        RubbishData rubbish = new RubbishData(category, weight);
        rubbish.setUser(currentUser);
        rubbish.setHomeArea(currentUser.getHomeArea());
        rubbishDao.saveData(rubbish);
        
        // Get updated total weight
        List<RubbishData> rubbishData = rubbishDao.findDataByUserId(currentUser.getId());
        double totalWeight = calculationService.calculateTotalRubbish(rubbishData);
        model.addAttribute("totalweight", totalWeight);
        
        return "MasukkanData/Sampah";
    }
} 