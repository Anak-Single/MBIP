package com.internetprogramming.mbip.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.internetprogramming.mbip.Service.DataEntryService;
import com.internetprogramming.mbip.dto.UserDataSummary;

import jakarta.annotation.Resource;

@Controller
@RequestMapping("/water")
public class WaterDataController {

    @Resource(name = "dataEntryService")
    private DataEntryService dataEntryService;

    /**
     * Show water data entry page
     */
    @GetMapping("")
    public String showWaterData(Model model) {
        UserDataSummary summary = dataEntryService.prepareWaterDataSummary(
            dataEntryService.getCurrentUser().getId()
        );
        
        if (summary.hasData()) {
            model.addAttribute("pricePerDate", summary.getPricePerDate());
            model.addAttribute("createtime", summary.getCreationDates());
            model.addAttribute("index", summary.getIndex());
        }
        
        return "MasukkanData/Air";
    }

    /**
     * Upload water bill data
     */
    @GetMapping("/upload")
    public String uploadWaterBill(Model model,
                                 @RequestParam("waterTotal") Double waterTotal,
                                 @RequestParam("billID") String billID,
                                 @RequestParam("tarikhBill") String tarikhBill,
                                 @RequestParam("billAmount") Double billAmount) {
        
        // Save the water bill data
        dataEntryService.saveWaterBill(waterTotal, billID, tarikhBill, billAmount);
        
        // Prepare updated summary
        UserDataSummary summary = dataEntryService.prepareWaterDataSummary(
            dataEntryService.getCurrentUser().getId()
        );
        
        if (summary.hasData()) {
            model.addAttribute("pricePerDate", summary.getPricePerDate());
            model.addAttribute("createtime", summary.getCreationDates());
            model.addAttribute("index", summary.getIndex());
            return "MasukkanData/Air";
        } else {
            return "MasukkanData/masukkanData";
        }
    }
} 