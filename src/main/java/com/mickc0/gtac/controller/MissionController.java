package com.mickc0.gtac.controller;

import com.mickc0.gtac.dto.MissionDTO;
import com.mickc0.gtac.model.Mission;
import com.mickc0.gtac.service.MissionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
public class MissionController {
    private final MissionService missionService;

    public MissionController(MissionService missionService) {
        this.missionService = missionService;
    }

    @GetMapping("/missions")
    public String missions(Model model){
        model.addAttribute("listMissions",missionService.findAll());
        MissionDTO missionDTO = new MissionDTO();
        model.addAttribute("missionDTO", missionDTO);
        return "missions";
    }

    @GetMapping("/view/{id}")
    public String viewMission(@PathVariable (value = "id") Long id, Model model){
        model.addAttribute(missionService.findById(id));
        return "redirect:/missions";
    }



    @PostMapping("/createMission")
    public String createMission(@ModelAttribute ("missionDTO") MissionDTO missionDTO){
        missionService.save(missionDTO);
        return "redirect:/missions";
    }

    @PutMapping("/updateMission")
    public String updateMission(MissionDTO missionDTO){
        missionService.save(missionDTO);
        return "redirect:/missions";
    }
}
