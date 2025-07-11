package com.internetprogramming.mbip.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.internetprogramming.mbip.Service.DashboardService;
import com.internetprogramming.mbip.dto.DashboardData;

import jakarta.annotation.Resource;

/**
 * Main data entry controller - handles the main dashboard
 * Other operations have been moved to specialized controllers:
 * - Water operations -> WaterDataController (/water)
 * - Electric operations -> ElectricDataController (/electric)  
 * - Rubbish operations -> RubbishDataController (/rubbish)
 * - Oil operations -> OilDataController (/oil)
 * - Navigation -> NavigationController (/navigation)
 */
@Controller
@RequestMapping("/masukkanData")
public class MasukDataController {

    @Resource(name = "dashboardService")
    private DashboardService dashboardService;

    /**
     * Show main data entry dashboard
     */
    @GetMapping("")
    public String masukkanData(Model model) {
        // Use dashboard service to prepare data
        DashboardData dashboardData = dashboardService.prepareDashboardData();
        
        model.addAttribute("days", dashboardData.getDays());
        model.addAttribute("hours", dashboardData.getHours());
        model.addAttribute("minutes", dashboardData.getMinutes());
        model.addAttribute("seconds", dashboardData.getSeconds());

        return "MasukkanData/masukkanData";
    }
} 