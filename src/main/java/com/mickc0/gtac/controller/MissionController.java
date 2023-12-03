package com.mickc0.gtac.controller;

import com.mickc0.gtac.dto.MissionDTO;
import com.mickc0.gtac.dto.VolunteerDTO;
import com.mickc0.gtac.entity.MissionStatus;
import com.mickc0.gtac.entity.MissionType;
import com.mickc0.gtac.service.MissionService;
import com.mickc0.gtac.service.MissionTypeService;
import com.mickc0.gtac.service.VolunteerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/missions")
public class MissionController {

    private final MissionService missionService;
    private final MissionTypeService missionTypeService;
    private final VolunteerService volunteerService;

    @Autowired
    public MissionController(MissionService missionService, MissionTypeService missionTypeService, VolunteerService volunteerService) {
        this.missionService = missionService;
        this.missionTypeService = missionTypeService;
        this.volunteerService = volunteerService;
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
        model.addAttribute("mission", new MissionDTO());
        model.addAttribute("missionTypes", missionTypeService.findAll());
        return "missions/new-mission/create-new-mission";
    }

    @PostMapping
    public String createMission(@ModelAttribute(name = "mission") MissionDTO missionDTO) {
        missionService.save(missionDTO);
        return "redirect:/missions";
    }

    @GetMapping("/edit/new/{id}")
    public String showEditForm(@PathVariable(name = "id") UUID uuid, Model model) {
        MissionDTO missionDTO = missionService.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Invalid mission Id:" + uuid));
        model.addAttribute("mission", missionDTO);
        model.addAttribute("missionTypes", missionTypeService.findAll());
        return "missions/new-mission/edit-new-mission";
    }

    @PostMapping("/update/new/{id}")
    public String updateMission(@PathVariable(name = "id") UUID uuid, @ModelAttribute(name = "mission") MissionDTO missionDTO) {
        missionService.save(missionDTO);
        return "redirect:/missions";
    }

    @GetMapping("/delete/{id}")
    public String deleteMission(@PathVariable(name = "id") UUID uuid) {
        missionService.deleteByUuid(uuid);
        return "redirect:/missions";
    }

    @GetMapping("/{id}")
    public String showMission(@PathVariable(name = "id") UUID uuid, Model model) {
        MissionDTO missionDTO = missionService.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Invalid mission Id:" + uuid));
        model.addAttribute("mission", missionDTO);
        return "missions/view-mission";
    }

    @GetMapping("/plan/new/{id}")
    public String showPlanForm(@PathVariable(name = "id") UUID uuid, Model model){
        MissionDTO missionDTO = missionService.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Invalid mission Id:" + uuid));
        model.addAttribute("mission", missionDTO);
        return "missions/planned-mission/create-planned-mission";
    }

    @PostMapping("/plan/{id}")
    public String planMission(@PathVariable(name = "id") UUID uuid, @ModelAttribute MissionDTO missionDTO){
        missionService.planMission(missionDTO);
        return "redirect:/missions";
    }

    @GetMapping("/edit/planned/{id}")
    public String showEditPlanForm(@PathVariable(name = "id") UUID uuid, Model model){
        MissionDTO missionDTO = missionService.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Invalid mission Id:" + uuid));
        model.addAttribute("mission", missionDTO);
        model.addAttribute("missionTypes", missionTypeService.findAll());
        return "missions/planned-mission/edit-planned-mission";
    }

    @PostMapping("/update/planned/{id}")
    public String updatePlanMission(@PathVariable(name = "id") UUID uuid, @ModelAttribute MissionDTO missionDTO){
        missionService.planMission(missionDTO);
        return "redirect:/missions";
    }

    @GetMapping("/cancel/{id}")
    public String cancelMission(@PathVariable(name = "id") UUID uuid, RedirectAttributes redirectAttributes) {
        try {
            missionService.cancelMission(uuid);
            redirectAttributes.addFlashAttribute("successMessage", "Mission annulée avec succès.");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de l'annulation de la mission.");
        }
        return "redirect:/missions";
    }

    @GetMapping("/confirm/{id}")
    public String showConfirmedMissionForm(@PathVariable("id") UUID uuid, Model model, RedirectAttributes redirectAttributes){
        MissionDTO missionDTO = missionService.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Invalid mission Id:" + uuid));
        model.addAttribute("mission", missionDTO);
        List<VolunteerDTO> availableVolunteers = volunteerService.getAvailableUsersForMission(missionDTO.getStartDateTime(),
                missionDTO.getEndDateTime(),missionDTO.getMissionType().getUuid());
        model.addAttribute("availableVolunteers", availableVolunteers);
        return "missions/confirmed-mission/create-confirmed-mission";
    }


}
