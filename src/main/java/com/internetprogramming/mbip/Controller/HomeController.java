package com.internetprogramming.mbip.Controller;

import jakarta.annotation.Resource;

import com.internetprogramming.mbip.Entity.HomeArea;
import com.internetprogramming.mbip.Entity.User;
import com.internetprogramming.mbip.Entity.RubbishData;
import com.internetprogramming.mbip.Entity.OilData;
import com.internetprogramming.mbip.Entity.WaterData;
import com.internetprogramming.mbip.Entity.ElectricData;
import com.internetprogramming.mbip.Service.RubbishDao;
import com.internetprogramming.mbip.Service.OilDao;
import com.internetprogramming.mbip.Service.WaterDao;
import com.internetprogramming.mbip.Service.ElectricDao;
import com.internetprogramming.mbip.Service.UserDao;
import com.mysql.cj.protocol.Security;

//import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RubbishDao rubbishDao;

    @Autowired
    private OilDao oilDao;

    @Autowired
    private WaterDao waterDao;

    @Autowired
    private ElectricDao electricDao;
    

    @Resource(name = "userDao")
	private UserDao userDao;

    @GetMapping("/")
    public String index() {
        return "Auth/Login";
    }

    @GetMapping("/utama")
    public String utama() {
        return "Utama";
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
                            @RequestParam("homearea") String homearea)
	{
        HomeArea enumhome = HomeArea.valueOf(homearea);

        User user = new User(username, password,  fullname, age, homeaddress, enumhome);

        //Sini kena extract table Customer from database
		List <User> userArray = userDao.findAllUser();
		
		for(User tempUser : userArray)
		{
			if(username.equals(tempUser.getUserName()))
			{
				return "Auth/loginfail";
			}
		}
        user.setPassword(passwordEncoder.encode(user.getPassword()));
		userDao.saveUser(user);
        return "Auth/Login";
    }

    /* @PostMapping("/authentication")
    public String authentication( Model model,
                                  @RequestParam("username") String username,
						          @RequestParam("password") String password)
    {
        //Sini kena extract table Customer from database
        List <User> userArray = userDao.findAllUser();
        
        for(User user : userArray)
        {
            if(username.equals(user.getUserName()))
            {
                if(password.equals(user.getPassword()))
                {
                    return "Utama";
                }
                else
                model.addAttribute("errorMessage", "Incorrect Username or Password");
                return "Auth/login";
            }
        }
        model.addAttribute("errorMessage", "Incorrect Username or Password");
        return "Auth/login";
    } */

    @GetMapping("/petaKarbon")
    public String petaKarbon(Model model) {
        // Retrieve userId based on the logged-in user's principal
     
       

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userDao.findByUserName(username);
        // Assuming you want to get the weight of the latest RubbishData for the user
        List<RubbishData> rubbishDataList = rubbishDao.findDataByUserId(user.getId());

        if (!rubbishDataList.isEmpty()) {
            RubbishData latestRubbishData = rubbishDataList.get(0); // Assuming the list is ordered by updateTime
            double weight = latestRubbishData.getWeight();
            model.addAttribute("weight", weight);
        } else {
            // Handle the case where no RubbishData is available for the user
            model.addAttribute("weight", 0.0);
        }

         List<OilData> oilDataList = oilDao.findDataByUserId(user.getId());

        if (!oilDataList.isEmpty()) {
            OilData latestOilData = oilDataList.get(0); // Assuming the list is ordered by updateTime
            double oilweight = latestOilData.getWeight();
            model.addAttribute("oilweight", oilweight);
        } else {
            // Handle the case where no ElectricData is available for the user
            model.addAttribute("oilweight", 0.0);
        }

        List<ElectricData> electricDataList = electricDao.findBillsByUserId(user.getId());

        if (!electricDataList.isEmpty()) {
            ElectricData latestElectricData = electricDataList.get(0); // Assuming the list is ordered by updateTime
            double electricbill = latestElectricData.getBillAmount();
            model.addAttribute("electricbill", electricbill);
        } else {
            // Handle the case where no ElectricData is available for the user
            model.addAttribute("electricbill", 0.0);
        }


           List<WaterData> waterDataList = waterDao.findDataByUserId(user.getId());

        if (!waterDataList.isEmpty()) {
            WaterData latestWaterData = waterDataList.get(0); // Assuming the list is ordered by updateTime
            double waterbill = latestWaterData.getBillAmount();
            model.addAttribute("waterbill", waterbill);
        } else {
            // Handle the case where no ElectricData is available for the user
            model.addAttribute("waterbill", 0.0);
        }


        return "petaKarbon";
    }

    @GetMapping("/masukkanData")
    public String masukkanData() {
        return "masukkanData";
    }

}