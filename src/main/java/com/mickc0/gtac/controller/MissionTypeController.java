package com.mickc0.gtac.controller;

import com.mickc0.gtac.dto.MissionTypeDTO;
import com.mickc0.gtac.service.MissionTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

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
        List<MissionTypeDTO> missionTypes = missionTypeService.findAllDto();
        model.addAttribute("missionTypes", missionTypes);
        return "mission-types/mission-types";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("missionType", new MissionTypeDTO());
        return "mission-types/create-mission-type";
    }

    @PostMapping
    public String createMissionType(@Valid @ModelAttribute( name = "missionType") MissionTypeDTO missionType, BindingResult result,
                                    RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()){
            model.addAttribute("missionType", missionType);
            return "mission-types/create-mission-type";
        }
        missionTypeService.save(missionType);
        redirectAttributes.addFlashAttribute("successMessage", "Type de mission enregistré avec succès.");
        return "redirect:/mission-types";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable(name = "id") UUID uuid, Model model) {
        MissionTypeDTO missionType = missionTypeService.findMissionTypeDTOByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Invalid mission type Id:" + uuid));
        model.addAttribute("missionType", missionType);
        return "mission-types/edit-mission-type";
    }

    @PostMapping("/update/{id}")
    public String updateMissionType(@PathVariable(name = "id") UUID uuid, @Valid @ModelAttribute MissionTypeDTO missionType,
                                    BindingResult result,
                                    RedirectAttributes redirectAttributes,
                                    Model model) {
        if (result.hasErrors()){
            model.addAttribute("missionType", missionType);
            return "mission-types/edit-mission-type";
        }
        missionTypeService.save(missionType);
        redirectAttributes.addFlashAttribute("successMessage", "Type de mission modifié avec succès.");
        return "redirect:/mission-types";
    }

    @GetMapping("/delete/{id}")
    public String deleteMissionType(@PathVariable(name = "id") UUID uuid) {
        missionTypeService.deleteByUuid(uuid);
        return "redirect:/mission-types";
    }

    @GetMapping("/deactivate/{id}")
    public String deactivateMissionType(@PathVariable(name = "id") UUID uuid) {
        missionTypeService.deactivateByUuid(uuid);
        return "redirect:/mission-types";
    }

    @GetMapping("/activate/{id}")
    public String activateMissionType(@PathVariable(name = "id") UUID uuid) {
        missionTypeService.activateByUuid(uuid);
        return "redirect:/mission-types";
    }

}
