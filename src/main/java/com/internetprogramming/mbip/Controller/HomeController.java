package com.internetprogramming.mbip.Controller;

import jakarta.annotation.Resource;

import com.internetprogramming.mbip.Entity.User;
import com.internetprogramming.mbip.Service.UserDao;

//import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private PasswordEncoder passwordEncoder;

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
                            @RequestParam("homearea") String homearea,
                            @RequestParam("role") String role)
	{
        User user = new User(username, password,  fullname, age, homeaddress, homearea, role);

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

    @GetMapping("/lamanUtama")
    public String lamanUtama() {
        return "lamanUtama";
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