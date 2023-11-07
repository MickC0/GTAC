package com.mickc0.gtac.controller;

import com.mickc0.gtac.dto.MissionDTO;
import com.mickc0.gtac.dto.MissionTypeDTO;
import com.mickc0.gtac.service.MissionTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
public class MissionTypeController {

    private final MissionTypeService missionTypeService;


    public MissionTypeController(MissionTypeService missionTypeService) {
        this.missionTypeService = missionTypeService;
    }

    @GetMapping("/mission-types")
    public String findAll(Model model){
        model.addAttribute("missionTypes", missionTypeService.findAll());
        model.addAttribute("newMissionType", new MissionTypeDTO());
        return "/mission_types/mission_types";
    }

    @PostMapping("/mission-types")
    public String saveMissionType(@ModelAttribute("newMissionType") MissionTypeDTO missionTypeDTO){
        missionTypeService.save(missionTypeDTO);
        return "redirect:/mission-types";
    }

    @GetMapping("/mission-types/delete/{id}")
    public String deleteMissionType(@PathVariable("id")UUID uuid){
        missionTypeService.deleteMissionType(uuid);
        return "redirect:/mission-types";
    }

    @GetMapping("/mission-types/search")
    public String searchMissions(@RequestParam(value = "query") String query, Model model){
        List<MissionTypeDTO> missionTypeDTOS = missionTypeService.searchMissionTypes(query);
        model.addAttribute("missionTypes", missionTypeDTOS);
        model.addAttribute("newMissionType", new MissionTypeDTO());
        return "mission_types/mission_types";
    }

}
