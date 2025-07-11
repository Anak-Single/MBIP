package com.internetprogramming.mbip.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/navigation")
public class NavigationController {

    /**
     * Navigate to waste selection page
     */
    @GetMapping("/waste-selection")
    public String navigateToWasteSelection() {
        return "MasukkanData/PilihSisa";
    }

    /**
     * Navigate to utility selection page
     */
    @GetMapping("/utility-selection")
    public String navigateToUtilitySelection() {
        return "MasukkanData/PilihUtiliti";
    }

    /**
     * Navigate to main data entry page
     */
    @GetMapping("/data-entry")
    public String navigateToDataEntry() {
        return "MasukkanData/masukkanData";
    }
} 