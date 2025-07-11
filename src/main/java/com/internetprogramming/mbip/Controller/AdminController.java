package com.internetprogramming.mbip.Controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

@Controller
@RequestMapping("/Admin")
public class AdminController {
    
    @Resource(name = "userDao")
    private UserDao userDao;

    @Resource(name = "waterDao")
    private WaterDao waterDao;

    @Resource(name = "electricDao")
    private ElectricDao electricDao;

    @Resource(name = "oilDao")
    private OilDao oilDao;

    @Resource(name = "rubbishDao")
    private RubbishDao rubbishDao;

    @Resource(name = "calculationService")
    private CalculationService calculationService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        List<WaterData> water = waterDao.findAllData();
        List<ElectricData> electric = electricDao.findAllData();
        List<OilData> oil = oilDao.findAllData();
        List<RubbishData> rubbish = rubbishDao.findAllData();

        // Use calculation service to calculate totals
        double totalWater = calculationService.calculateTotalWater(water);
        double totalElectric = calculationService.calculateTotalElectric(electric);
        double totalOil = calculationService.calculateTotalOil(oil);
        double totalRubbish = calculationService.calculateTotalRubbish(rubbish);

        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        model.addAttribute("totalWater", decimalFormat.format(totalWater));
        model.addAttribute("totalElectric", decimalFormat.format(totalElectric));
        model.addAttribute("totalOil", decimalFormat.format(totalOil));
        model.addAttribute("totalRubbish", decimalFormat.format(totalRubbish));

        return "Admin/utama";
    }

    @GetMapping("/users")
    public String viewUsers() {
        return "Admin/viewUsers";
    }

    @GetMapping("/laporan")
    public String laporan() {
        return "Admin/Laporan";
    }

    @GetMapping("/hasilkanLaporan")
    public String hasilkanLaporan(Model model,
                                  @RequestParam("area") String area) {
        
        double rubbishWeight = 0.0;
        double oilWeight = 0.0;
        double waterBill = 0.0;
        double electricBill = 0.0;
        int totalPopulation = 0;
        
        List<RubbishData> rubbishDataInArea = new ArrayList<>();
        List<OilData> oilDataInArea = new ArrayList<>();
        List<WaterData> waterDataInArea = new ArrayList<>();
        List<ElectricData> electricDataInArea = new ArrayList<>();
    
        for (User user : userDao.findAllUser())
        {
            if (user.getHomeArea().equals(area))
            {
                totalPopulation += user.getHouseHold();

                rubbishDataInArea.addAll(rubbishDao.findDataByUserId(user.getId()));
                oilDataInArea.addAll(oilDao.findDataByUserId(user.getId()));
                waterDataInArea.addAll(waterDao.findBillsByUserId(user.getId()));
                electricDataInArea.addAll(electricDao.findBillsByUserId(user.getId()));
            }
        }
    
        // Use calculation service to calculate totals
        rubbishWeight = calculationService.calculateTotalRubbish(rubbishDataInArea);
        oilWeight = calculationService.calculateTotalOil(oilDataInArea);
        waterBill = calculationService.calculateTotalWater(waterDataInArea);
        electricBill = calculationService.calculateTotalElectric(electricDataInArea);

        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        
        model.addAttribute("area", area);
        model.addAttribute("totalPopulation", totalPopulation);

        model.addAttribute("rubbishWeight", Double.parseDouble(decimalFormat.format(rubbishWeight)));
        model.addAttribute("oilWeight", Double.parseDouble(decimalFormat.format(oilWeight)));
        model.addAttribute("waterBill", Double.parseDouble(decimalFormat.format(waterBill)));
        model.addAttribute("electricBill", Double.parseDouble(decimalFormat.format(electricBill)));

        // Use calculation service to find latest update time and calculate time difference
        var latestDate = calculationService.findLatestUpdateTime(waterDataInArea, electricDataInArea, rubbishDataInArea, oilDataInArea);
        var timeDiff = calculationService.calculateTimeDifference(latestDate);

        model.addAttribute("days", timeDiff.getDays());
        model.addAttribute("hours", timeDiff.getHours());
        model.addAttribute("minutes", timeDiff.getMinutes());
        model.addAttribute("seconds", timeDiff.getSeconds());
        
        // Use calculation service to calculate carbon emission
        double carbonEmission = calculationService.calculateCarbonEmission(rubbishWeight, waterBill, electricBill);

        model.addAttribute("carbonEmission", decimalFormat.format(carbonEmission));

        
        return "Admin/hasilkanLaporan";
    }
}