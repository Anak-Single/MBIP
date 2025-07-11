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
@RequestMapping("/electric")
public class ElectricDataController {

    @Resource(name = "dataEntryService")
    private DataEntryService dataEntryService;

    /**
     * Show electric data entry page
     */
    @GetMapping("")
    public String showElectricData(Model model) {
        UserDataSummary summary = dataEntryService.prepareElectricDataSummary(
            dataEntryService.getCurrentUser().getId()
        );
        
        if (summary.hasData()) {
            model.addAttribute("pricePerDate", summary.getPricePerDate());
            model.addAttribute("createtime", summary.getCreationDates());
            model.addAttribute("index", summary.getIndex());
        }
        
        return "MasukkanData/Elektrik";
    }

    /**
     * Upload electric bill data
     */
    @GetMapping("/upload")
    public String uploadElectricBill(Model model,
                                    @RequestParam("ElectricTotal") Double electricTotal,
                                    @RequestParam("billID") String billID,
                                    @RequestParam("tarikhBill") String tarikhBill,
                                    @RequestParam("billAmount") Double billAmount) {
        
        // Save the electric bill data
        dataEntryService.saveElectricBill(electricTotal, billID, tarikhBill, billAmount);
        
        // Prepare updated summary
        UserDataSummary summary = dataEntryService.prepareElectricDataSummary(
            dataEntryService.getCurrentUser().getId()
        );
        
        if (summary.hasData()) {
            model.addAttribute("pricePerDate", summary.getPricePerDate());
            model.addAttribute("createtime", summary.getCreationDates());
            model.addAttribute("index", summary.getIndex());
            return "MasukkanData/Elektrik";
        } else {
            return "MasukkanData/masukkanData";
        }
    }
} 