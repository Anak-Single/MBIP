package com.internetprogramming.mbip.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.internetprogramming.mbip.Service.UserDao;
import com.internetprogramming.mbip.Service.WaterDao;

import jakarta.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/masukkanData")
public class MasukDataController {

    @Resource(name = "userDao")
	private UserDao userDao;

    @Resource(name = "waterDao")
	private WaterDao waterDao;

    @GetMapping("/")
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
    public String Air() {
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

    @PostMapping("/muatnaikbilair")
    public String muatNaikBilAir(@RequestParam("billID") String billID,
                                 @RequestParam("tarikhBill") String tarikhBill,
                                 @RequestParam("billAmount") Double billAmount)
    {

        return "MasukkanData/Air";
    }
}
