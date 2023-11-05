package com.mickc0.gtac.controller;

import com.mickc0.gtac.dto.MissionDTO;
import com.mickc0.gtac.service.MissionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return "missions/missions";
    }

    @GetMapping("/missions/new")
    public String createMissionForm(Model model){
        MissionDTO missionDTO = new MissionDTO();
        model.addAttribute("newMissionDTO", missionDTO);
        return "missions/create_mission";
    }

    @PostMapping("/missions")
    public String saveMission(@ModelAttribute ("newMissionDTO") MissionDTO newMissionDTO){
        missionService.save(newMissionDTO);
        return "redirect:/missions";
    }

    @GetMapping("/missions/edit/{id}")
    public String editMissionForm(@PathVariable(value = "id")UUID uuid, Model model){
        model.addAttribute("mission", missionService.findByUUID(uuid));
        return "/missions/edit_mission";
    }

    @PostMapping("/missions/update")
    public String updateMission(@ModelAttribute ("mission") MissionDTO missionDTO){
        missionService.update(missionDTO);
        return "redirect:/missions";
    }






}
