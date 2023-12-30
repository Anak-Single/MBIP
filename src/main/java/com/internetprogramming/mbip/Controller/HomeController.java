package com.internetprogramming.mbip.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String hello(Model model) {
        model.addAttribute("message", "Hello, Welcome to MBIP");
        return "hello";
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