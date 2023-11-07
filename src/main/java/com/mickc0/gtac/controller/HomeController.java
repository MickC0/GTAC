package com.mickc0.gtac.controller;

import com.mickc0.gtac.service.MissionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final MissionService missionService;

    public HomeController(MissionService missionService) {
        this.missionService = missionService;
    }

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("missions", missionService.findAll());
        return "home";
    }
}