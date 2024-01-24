package com.internetprogramming.mbip.Controller;

import jakarta.annotation.Resource;

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

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
//import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("Admin")
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

    @GetMapping("/dashboard")
    public String dashboard() {
        return "Admin/utama";
    }

    @GetMapping("/Laporan")
    public String laporan(Model model) {
        List<WaterData> water = waterDao.findAllData();
        List<ElectricData> electric = electricDao.findAllData();
        List<RubbishData> rubbish = rubbishDao.findAllData();
        List<OilData> oil = oilDao.findAllData();

        WaterData latestWaterData = water.stream().max(Comparator.comparing(WaterData::getUpdateTime)).orElse(null);
        ElectricData latestElectricData = electric.stream().max(Comparator.comparing(ElectricData::getUpdateTime)).orElse(null);
        RubbishData latestRubbishData = rubbish.stream().max(Comparator.comparing(RubbishData::getUpdateTime)).orElse(null);
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

        //if (latestDate != null)
        //{
            Duration timeDifference = Duration.between(latestDate, LocalDateTime.now());

            days = timeDifference.toDays();
            hours = timeDifference.toHoursPart();
            minutes = timeDifference.toMinutesPart();
            seconds = timeDifference.toSecondsPart();
        //}

        model.addAttribute("days", days);
        model.addAttribute("hours", hours);
        model.addAttribute("minutes", minutes);
        model.addAttribute("seconds", seconds);

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
    
        for (RubbishData rubbishData : rubbishDataInArea)
        {
            rubbishWeight += rubbishData.getWeight();
        }
    
        for (OilData oilData : oilDataInArea)
        {
            oilWeight += oilData.getWeight();
        }
    
        for (WaterData waterData : waterDataInArea)
        {
            waterBill += waterData.getBillAmount();
        }
    
        for (ElectricData electricData : electricDataInArea)
        {
            electricBill += electricData.getBillAmount();
        }

        model.addAttribute("area", area);
        model.addAttribute("totalPopulation", totalPopulation);

        model.addAttribute("rubbishWeight", rubbishWeight);
        model.addAttribute("oilWeight", oilWeight);
        model.addAttribute("waterBill", waterBill);
        model.addAttribute("electricBill", electricBill);

        WaterData latestWaterData = waterDataInArea.stream().max(Comparator.comparing(WaterData::getUpdateTime)).orElse(null);
        ElectricData latestElectricData = electricDataInArea.stream().max(Comparator.comparing(ElectricData::getUpdateTime)).orElse(null);
        RubbishData latestRubbishData = rubbishDataInArea.stream().max(Comparator.comparing(RubbishData::getUpdateTime)).orElse(null);
        OilData latestOilData = oilDataInArea.stream().max(Comparator.comparing(OilData::getUpdateTime)).orElse(null);

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

        //if (latestDate != null)
        //{
            Duration timeDifference = Duration.between(latestDate, LocalDateTime.now());

            days = timeDifference.toDays();
            hours = timeDifference.toHoursPart();
            minutes = timeDifference.toMinutesPart();
            seconds = timeDifference.toSecondsPart();
        //}

        model.addAttribute("days", days);
        model.addAttribute("hours", hours);
        model.addAttribute("minutes", minutes);
        model.addAttribute("seconds", seconds);
        
        double carbonEmission = 0.0;

        carbonEmission += (rubbishWeight*2.86);
        carbonEmission += (waterBill*0.419);
        carbonEmission += (electricBill*0.584);

        model.addAttribute("carbonEmission", carbonEmission);

        return "Admin/hasilkanLaporan";
    }
}