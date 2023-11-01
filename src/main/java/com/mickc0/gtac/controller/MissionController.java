package com.mickc0.gtac.controller;

import com.mickc0.gtac.dto.MissionDTO;
import com.mickc0.gtac.model.Mission;
import com.mickc0.gtac.service.MissionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MissionController {
    private final MissionService missionService;

    public MissionController(MissionService missionService) {
        this.missionService = missionService;
    }

    @GetMapping("/index")
    public String index(Model model){
        List<MissionDTO> missionList=missionService.findAll();
        model.addAttribute("listMissions",missionList);
        return "missions";
    }
}
