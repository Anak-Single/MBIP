package com.internetprogramming.mbip.Controller;

import jakarta.annotation.Resource;

import com.internetprogramming.mbip.Entity.User;
import com.internetprogramming.mbip.Service.UserDao;

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

//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProfileController {

    private final UserDao userDao;

    public ProfileController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userDao.findByUserName(username);

        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/profile/save")
    public String saveProfile(@RequestParam("id") Long id, @ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            userDao.updateUser(id, user);
            redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating profile: " + e.getMessage());
        }
        return "Utama";
    }
}

