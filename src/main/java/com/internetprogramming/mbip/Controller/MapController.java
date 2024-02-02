package com.internetprogramming.mbip.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MapController {
    
    @GetMapping("/map")
    public String getMap() {
        return "map";
    }
    
}
