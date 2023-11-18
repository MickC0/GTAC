package com.mickc0.gtac.controller;

import com.mickc0.gtac.entity.Mission;
import com.mickc0.gtac.entity.MissionStatus;
import com.mickc0.gtac.entity.MissionType;
import com.mickc0.gtac.service.MissionService;
import com.mickc0.gtac.service.MissionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/missions")
public class MissionController {

    private final MissionService missionService;
    private final MissionTypeService missionTypeService;

    @Autowired
    public MissionController(MissionService missionService, MissionTypeService missionTypeService) {
        this.missionService = missionService;
        this.missionTypeService = missionTypeService;
    }

    @GetMapping
    public String listMissions(Model model) {
        model.addAttribute("newMissions", missionService.findByStatus(MissionStatus.NEW));
        model.addAttribute("plannedMissions", missionService.findByStatus(MissionStatus.PLANNED));
        model.addAttribute("ongoingMissions", missionService.findByStatus(MissionStatus.ONGOING));
        model.addAttribute("confirmedMissions", missionService.findByStatus(MissionStatus.CONFIRMED));
        model.addAttribute("completedMissions", missionService.findByStatus(MissionStatus.DONE));
        model.addAttribute("cancelledMissions", missionService.findByStatus(MissionStatus.CANCELLED));
        return "missions/missions";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        List<MissionType> missionTypes = missionTypeService.findAll();
        if (missionTypes.isEmpty()) {
            model.addAttribute("message", "Veuillez d'abord créer un type de mission.");
            return "mission-types/no-mission-type";
        }
        model.addAttribute("mission", new Mission());
        model.addAttribute("missionTypes", missionTypeService.findAll());
        return "missions/new-mission/create-new-mission";
    }

    @PostMapping
    public String createMission(@ModelAttribute(name = "mission") Mission mission) {
        missionService.save(mission);
        return "redirect:/missions";
    }

    @GetMapping("/edit/new/{id}")
    public String showEditForm(@PathVariable(name = "id") Long id, Model model) {
        Mission mission = missionService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid mission Id:" + id));
        model.addAttribute("mission", mission);
        model.addAttribute("missionTypes", missionTypeService.findAll());
        return "missions/new-mission/edit-new-mission";
    }

    @PostMapping("/update/new/{id}")
    public String updateMission(@PathVariable(name = "id") Long id, @ModelAttribute(name = "mission") Mission mission) {
        missionService.save(mission);
        return "redirect:/missions";
    }

    @GetMapping("/delete/{id}")
    public String deleteMission(@PathVariable(name = "id") Long id) {
        missionService.deleteById(id);
        return "redirect:/missions";
    }

    @GetMapping("/{id}")
    public String showMission(@PathVariable(name = "id") Long id, Model model) {
        Mission mission = missionService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid mission Id:" + id));
        model.addAttribute("mission", mission);
        return "missions/view-mission";
    }

    @GetMapping("/plan/new/{id}")
    public String showPlanForm(@PathVariable(name = "id") Long id, Model model){
        Mission mission = missionService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid mission Id:" + id));
        model.addAttribute("mission", mission);

        return "missions/planned-mission/create-planned-mission";
    }

    @PostMapping("/plan/{id}")
    public String planMission(@PathVariable(name = "id") Long id, @ModelAttribute Mission mission){
        missionService.planMission(mission);
        return "redirect:/missions";
    }

    @GetMapping("/edit/planned/{id}")
    public String showEditPlanForm(@PathVariable(name = "id") Long id, Model model){
        Mission mission = missionService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid mission Id:" + id));
        model.addAttribute("mission", mission);
        model.addAttribute("missionTypes", missionTypeService.findAll());
        return "missions/planned-mission/edit-planned-mission";
    }

    @PostMapping("/update/planned/{id}")
    public String updatePlanMission(@PathVariable(name = "id") Long id, @ModelAttribute Mission mission){
        missionService.planMission(mission);
        return "redirect:/missions";
    }




}
