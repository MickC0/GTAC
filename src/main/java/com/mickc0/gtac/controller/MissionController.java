package com.mickc0.gtac.controller;

import com.mickc0.gtac.dto.MissionDTO;
import com.mickc0.gtac.service.MissionService;
import com.mickc0.gtac.service.MissionTypeService;
import com.mickc0.gtac.service.internal.MissionInternalService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
public class MissionController {
    private final MissionService missionService;
    private final MissionTypeService missionTypeService;

    private final MissionInternalService missionInternalService;

    public MissionController(MissionService missionService, MissionTypeService missionTypeService, MissionInternalService missionInternalService) {
        this.missionService = missionService;
        this.missionTypeService = missionTypeService;
        this.missionInternalService = missionInternalService;
    }

    @GetMapping("/missions")
    public String missions(Model model){
        model.addAttribute("listMissions",missionService.findAll());
        return "missions/missions";
    }

    @GetMapping("/missions/new")
    public String createMissionForm(Model model){
        MissionDTO missionDTO = new MissionDTO();
        model.addAttribute("mission", missionDTO);
        model.addAttribute("missionTypes", missionTypeService.findAllOnlyUuidName());
        return "missions/create_mission";
    }

    @PostMapping("/missions")
    public String saveMission(@Valid @ModelAttribute ("mission") MissionDTO newMissionDTO){
        missionInternalService.saveMissionWithType(newMissionDTO);
        return "redirect:/missions";
    }

    @GetMapping("/missions/edit/{id}")
    public String editMissionForm(@PathVariable(value = "id")UUID uuid, Model model){
        model.addAttribute("mission", missionService.findMissionByUUID(uuid));
        model.addAttribute("missionTypes", missionTypeService.findAllOnlyUuidName());
        return "/missions/edit_mission";
    }

    @PostMapping("/missions/update")
    public String updateMission(@Valid @ModelAttribute ("mission") MissionDTO missionDTO){
        missionInternalService.updateMissionWithType(missionDTO);
        return "redirect:/missions";
    }

    @GetMapping("/missions/delete/{id}")
    public String deleteMission(@PathVariable (value = "id") UUID uuid){
        missionService.deleteMission(uuid);
        return "redirect:/missions";
    }

    @GetMapping("/missions/view/{id}")
    public String viewMission(@PathVariable(value = "id") UUID uuid, Model model){
        model.addAttribute("mission", missionService.findMissionByUUID(uuid));
        return "missions/view_mission";
    }

    @GetMapping("/missions/search")
    public String searchMissions(@RequestParam(value = "query") String query, Model model){
        List<MissionDTO> missions = missionService.searchMissions(query);
        model.addAttribute("listMissions", missions);
        return "missions/missions";
    }






}
