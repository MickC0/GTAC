package com.mickc0.gtac.controller;

import com.mickc0.gtac.dto.MissionDTO;
import com.mickc0.gtac.model.Mission;
import com.mickc0.gtac.service.MissionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MissionController {
    private final MissionService missionService;

    public MissionController(MissionService missionService) {
        this.missionService = missionService;
    }

    @GetMapping("/missions")
    public String index(Model model){
        model.addAttribute("listMissions",missionService.findAll());
        MissionDTO missionDTO = new MissionDTO();
        model.addAttribute("missionDTO", missionDTO);
        return "missions";
    }

    @PostMapping("/createMission")
    public String createMission(@ModelAttribute ("missionDTO") MissionDTO missionDTO){
        missionService.save(missionDTO);
        return "redirect:/missions";
    }
}
