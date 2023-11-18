package com.mickc0.gtac.controller;

import com.mickc0.gtac.entity.MissionType;
import com.mickc0.gtac.service.MissionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/mission-types")
public class MissionTypeController {

    private final MissionTypeService missionTypeService;

    @Autowired
    public MissionTypeController(MissionTypeService missionTypeService) {
        this.missionTypeService = missionTypeService;
    }

    @GetMapping
    public String listMissionTypes(Model model) {
        List<MissionType> missionTypes = missionTypeService.findAll();
        model.addAttribute("missionTypes", missionTypes);
        return "mission-types/mission-types";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("missionType", new MissionType());
        return "mission-types/create-mission-type";
    }

    @PostMapping
    public String createMissionType(@ModelAttribute MissionType missionType) {
        missionTypeService.save(missionType);
        return "redirect:/mission-types";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable(name = "id") Long id, Model model) {
        MissionType missionType = missionTypeService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid mission type Id:" + id));
        model.addAttribute("missionType", missionType);
        return "mission-types/edit-type";
    }

    @PostMapping("/update/{id}")
    public String updateMissionType(@PathVariable(name = "id") Long id, @ModelAttribute MissionType missionType) {
        missionTypeService.save(missionType);
        return "redirect:/mission-types";
    }

    @GetMapping("/delete/{id}")
    public String deleteMissionType(@PathVariable(name = "id") Long id) {
        missionTypeService.deleteById(id);
        return "redirect:/mission-types";
    }

}
