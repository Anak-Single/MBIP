package com.internetprogramming.mbip.Controller;

import jakarta.annotation.Resource;

import com.internetprogramming.mbip.Entity.HomeArea;
import com.internetprogramming.mbip.Entity.User;
import com.internetprogramming.mbip.Service.UserDaoImpl;

//import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Resource(name = "userDaoImpl")
	private UserDaoImpl userDaoHib;

    @GetMapping("/")
    public String index() {
        return "Auth/Login";
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
		List <User> userArray = userDaoHib.findAllUser();
		
		for(User tempUser : userArray)
		{
			if(username.equals(tempUser.getUserName()))
			{
				return "Auth/loginfail";
			}
		}
		userDaoHib.saveUser(user);
        return "Auth/Login";
    }

    @PostMapping("/authentication")
    public String authentication( Model model,
                                  @RequestParam("username") String username,
						          @RequestParam("password") String password)
    {
        //Sini kena extract table Customer from database
        List <User> userArray = userDaoHib.findAllUser();
        
        for(User user : userArray)
        {
            if(username.equals(user.getUserName()))
            {
                if(password.equals(user.getPassword()))
                {
                    return "petaKarbon";
                }
                else
                    return "Auth/loginfail";
            }
        }
        return "Auth/loginfail";
    }

    @GetMapping("/petaKarbon")
    public String petaKarbon() {
        return "petaKarbon";
    }

    @GetMapping("/masukkanData")
    public String masukkanData() {
        return "masukkanData";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }
}