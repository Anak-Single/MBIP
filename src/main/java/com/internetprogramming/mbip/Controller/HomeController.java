package com.internetprogramming.mbip.Controller;

import jakarta.annotation.Resource;

import org.springframework.ui.Model;

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

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private PasswordEncoder passwordEncoder;

   /*  @Autowired
    private RubbishDao rubbishDao;

    @Autowired
    private OilDao oilDao;

    @Autowired
    private WaterDao waterDao;

    @Autowired
    private ElectricDao electricDao; */

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

    @GetMapping("/")
    public String index() {
        return "Auth/Login";
    }

    @GetMapping("/utama")
    public String utama(Model model) {
        List <WaterData> water = waterDao.findAllData();
        List <ElectricData> electric = electricDao.findAllData();
        List <OilData> oil = oilDao.findAllData();
        List <RubbishData> rubbish = rubbishDao.findAllData();

        double totalWater = 0;
        for(WaterData tempWater : water)
        {
            totalWater += tempWater.getWaterTotal();
        }

        double totalElectric = 0;
        for(ElectricData tempElectric : electric)
        {
            totalElectric += tempElectric.getElectricTotal();
        }

        double totalOil = 0;
        for(OilData tempOil : oil)
        {
            totalOil += tempOil.getWeight();
        }

        double totalRubbish = 0;
        for(RubbishData tempRubbish : rubbish)
        {
            totalRubbish += tempRubbish.getWeight();
        }

         DecimalFormat decimalFormat = new DecimalFormat("#.##");

        model.addAttribute("totalWater", decimalFormat.format(totalWater));
        model.addAttribute("totalElectric", decimalFormat.format(totalElectric));
        model.addAttribute("totalOil", decimalFormat.format(totalOil));
        model.addAttribute("totalRubbish", decimalFormat.format(totalRubbish));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userDao.findByUserName(username);
        if(user.getRole().equals("ADMIN"))
        {
            return "redirect:/Admin/dashboard";
        }
        else
        {
            return "Utama";
        }

    }

    @GetMapping("/registerform")
    public String registerform() {
        return "Auth/Register";
    }

    @GetMapping("/register")
    public String register( @RequestParam("fullname") String fullname,
							@RequestParam("username") String username,
							@RequestParam("password") String password,
                            @RequestParam("age") int age,
                            @RequestParam("homeaddress") String homeaddress,
                            @RequestParam("homearea") String homearea,
                            @RequestParam("role") String role)
	{
        User user = new User(username, password,  fullname, age, homeaddress, homearea, role);

        // Sini kena extract table Customer from database
        List<User> userArray = userDao.findAllUser();

        for (User tempUser : userArray) {
            if (username.equals(tempUser.getUserName())) {
                return "Auth/loginfail";
            }
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.saveUser(user);
        return "Auth/Login";
    }

    @GetMapping("/petaKarbon")
    public String petaKarbon() {
        return "petaKarbon";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "/access-denied";
    }
}