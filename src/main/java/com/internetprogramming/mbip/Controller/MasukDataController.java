package com.internetprogramming.mbip.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;

import com.internetprogramming.mbip.Entity.WaterData;
import com.internetprogramming.mbip.Entity.ElectricData;
import com.internetprogramming.mbip.Entity.HomeArea;
import com.internetprogramming.mbip.Entity.OilData;
import com.internetprogramming.mbip.Entity.RubbishData;
import com.internetprogramming.mbip.Entity.User;
import com.internetprogramming.mbip.Service.ElectricDao;
import com.internetprogramming.mbip.Service.OilDao;
import com.internetprogramming.mbip.Service.RubbishDao;
import com.internetprogramming.mbip.Service.UserDao;
import com.internetprogramming.mbip.Service.WaterDao;

import jakarta.annotation.Resource;


@Controller
@RequestMapping("/masukkanData")
public class MasukDataController {

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

    @GetMapping("")
    public String masukkanData() {
        //this.userDao = 
        return "MasukkanData/masukkanData";
    }

    @GetMapping("/pilihSisa")
    public String pilihSisa() {
        return "MasukkanData/PilihSisa";
    }

    @GetMapping("/pilihUtiliti")
    public String pilihUtiliti() {
        return "MasukkanData/PilihUtiliti";
    }

    @GetMapping("/air")
    public String Air()
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userDao.findByUserName(username);

        List <WaterData> houseHoldWater = waterDao.getCategorizedWaterData(user.getId());

        for(WaterData data : houseHoldWater)
        {
            
        }
        
        return "MasukkanData/Air";
    }

    @GetMapping("/elektrik")
    public String Elektrik() {
        return "MasukkanData/Elektrik";
    }

    @GetMapping("/sampah")
    public String Sampah() {
        return "MasukkanData/Sampah";
    }

    @GetMapping("/minyak")
    public String Minyak() {
        return "MasukkanData/Minyak";
    }

    @GetMapping("/muatNaikBilAir")
    public String muatNaikBilAir(@RequestParam("billID") String billID,
                                 @RequestParam("tarikhBill") String tarikhBill,
                                 @RequestParam("billAmount") Double billAmount)
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userDao.findByUserName(username);

        WaterData water = new WaterData(billID, tarikhBill, billAmount);
        water.setUser(user);

        waterDao.saveData(water);

        return "MasukkanData/Success";
    }

    @GetMapping("/muatNaikBilElektrik")
    public String muatNaikBilElektrik(@RequestParam("billID") String billID,
                                      @RequestParam("tarikhBill") String tarikhBill,
                                      @RequestParam("billAmount") Double billAmount)
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userDao.findByUserName(username);

        ElectricData electric = new ElectricData(billID, tarikhBill, billAmount);
        electric.setUser(user);

        electricDao.saveData(electric);

        return "MasukkanData/Success";
    }

    @GetMapping("/muatNaikDataMinyak")
    public String muatNaikDataMinyak(@RequestParam("Weight") Double weight)
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userDao.findByUserName(username);

        OilData oil = new OilData(weight);
        oil.setUser(user);

        oilDao.saveData(oil);

        return "MasukkanData/Success";
    }

    @GetMapping("/muatNaikDataSampah")
    public String muatNaikDataSampah(@RequestParam("Weight") Double weight,
                                     @RequestParam("Category") String category)
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userDao.findByUserName(username);

        RubbishData rubbish = new RubbishData(category, weight);
        rubbish.setUser(user);
        rubbish.setHomeArea(user.getHomeArea());

        rubbishDao.saveData(rubbish);

        return "MasukkanData/Success";
    }
}