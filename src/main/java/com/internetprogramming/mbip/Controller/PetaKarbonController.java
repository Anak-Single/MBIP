package com.internetprogramming.mbip.Controller;

import java.util.List;

import com.internetprogramming.mbip.Entity.ElectricData;
import com.internetprogramming.mbip.Entity.OilData;
import com.internetprogramming.mbip.Entity.RubbishData;
import com.internetprogramming.mbip.Entity.WaterData;
import com.internetprogramming.mbip.Service.CalculationService;
import com.internetprogramming.mbip.Service.CarbonMappingService;
import com.internetprogramming.mbip.constants.AreaConstants;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PetaKarbonController {

    @Autowired
    private CalculationService calculationService;

    @Autowired
    private CarbonMappingService carbonMappingService;

    @GetMapping("/petaKarbon")
    public String petaKarbon(Model model) {
        // Process all areas using the carbon mapping service
        carbonMappingService.processMultipleAreas(AreaConstants.ALL_AREAS, model);
        
        // Calculate time difference for the dashboard
        addTimeDifferenceToModel(model);

        return "petaKarbon";
    }

    /**
     * Add time difference attributes to the model
     */
    private void addTimeDifferenceToModel(Model model) {
        // Get all data for time calculation
        List<WaterData> water = carbonMappingService.getAllWaterData();
        List<ElectricData> electric = carbonMappingService.getAllElectricData();
        List<RubbishData> rubbish = carbonMappingService.getAllRubbishData();
        List<OilData> oil = carbonMappingService.getAllOilData();

        // Calculate time difference
        var latestDate = calculationService.findLatestUpdateTime(water, electric, rubbish, oil);
        var timeDiff = calculationService.calculateTimeDifference(latestDate);

        model.addAttribute("days", timeDiff.getDays());
        model.addAttribute("hours", timeDiff.getHours());
        model.addAttribute("minutes", timeDiff.getMinutes());
        model.addAttribute("seconds", timeDiff.getSeconds());
    }
        
}

