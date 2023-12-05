package com.mickc0.gtac.controller;

import com.mickc0.gtac.dto.*;
import com.mickc0.gtac.entity.MissionStatus;
import com.mickc0.gtac.service.MissionAssignmentService;
import com.mickc0.gtac.service.MissionService;
import com.mickc0.gtac.service.MissionTypeService;
import com.mickc0.gtac.service.VolunteerService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/missions")
public class MissionController {

    private final MissionService missionService;
    private final MissionTypeService missionTypeService;
    private final VolunteerService volunteerService;
    private final MissionAssignmentService missionAssignmentService;

    @Autowired
    public MissionController(MissionService missionService, MissionTypeService missionTypeService, VolunteerService volunteerService, MissionAssignmentService missionAssignmentService) {
        this.missionService = missionService;
        this.missionTypeService = missionTypeService;
        this.volunteerService = volunteerService;
        this.missionAssignmentService = missionAssignmentService;
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
        List<MissionTypeDTO> missionTypes = missionTypeService.findAllDto();
        if (missionTypes.isEmpty()) {
            model.addAttribute("message", "Veuillez d'abord créer un type de mission.");
            return "mission-types/no-mission-type";
        }
        model.addAttribute("mission", new MissionDTO());
        model.addAttribute("missionTypes", missionTypes);
        return "missions/new-mission/create-new-mission";
    }

    @PostMapping
    public String createMission(@ModelAttribute(name = "mission") MissionDTO missionDTO, RedirectAttributes redirectAttributes) {
        try {
            missionService.save(missionDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Mission créée avec succès.");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la création de la mission.");
        }
        return "redirect:/missions";
    }

    @GetMapping("/edit/new/{id}")
    public String showEditForm(@PathVariable(name = "id") UUID uuid, Model model) {
        MissionDTO missionDTO = missionService.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("L'id " + uuid + "de la mission n'est pas valide."));
        model.addAttribute("mission", missionDTO);
        model.addAttribute("missionTypes", missionTypeService.findAllDto());
        return "missions/new-mission/edit-new-mission";
    }

    @PostMapping("/update/new/{id}")
    public String updateMission(@PathVariable(name = "id") UUID uuid, @ModelAttribute(name = "mission") MissionDTO missionDTO,
                                RedirectAttributes redirectAttributes) {
        try {
            missionService.save(missionDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Mission mise à jour avec succès.");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la mise à jour de la mission.");
        }
        return "redirect:/missions";
    }

    @GetMapping("/delete/{id}")
    public String deleteMission(@PathVariable(name = "id") UUID uuid, RedirectAttributes redirectAttributes) {
        try {
            missionService.deleteByUuid(uuid);
            redirectAttributes.addFlashAttribute("successMessage", "Mission supprimée avec succès.");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la suppression de la mission.");
        }
        return "redirect:/missions";
    }

    @GetMapping("/{id}")
    public String showMission(@PathVariable(name = "id") UUID uuid, Model model) {
        MissionViewDTO missionViewDTO = new MissionViewDTO();
        MissionDTO missionDTO = missionService.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("L'id " + uuid + "de la mission n'est pas valide."));
        missionViewDTO.setMissionDTO(missionDTO);
        List<MissionAssignmentDTO> assignedVolunteers = missionAssignmentService.findAllCurrentMissionAssignment(uuid);
        missionViewDTO.setAssignedVolunteers(assignedVolunteers);

        model.addAttribute("mission", missionViewDTO);
        return "missions/view-mission";
    }

    @GetMapping("/plan/new/{id}")
    public String showPlanForm(@PathVariable(name = "id") UUID uuid, Model model){
        MissionDTO missionDTO = missionService.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("L'id " + uuid + "de la mission n'est pas valide."));
        model.addAttribute("mission", missionDTO);
        return "missions/planned-mission/create-planned-mission";
    }

    @PostMapping("/plan/{id}")
    public String planMission(@PathVariable(name = "id") UUID uuid, @ModelAttribute MissionDTO missionDTO,
                              RedirectAttributes redirectAttributes){
        try {
            missionService.planMission(missionDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Mission planifiée avec succès.");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la planification de la mission.");
        }
        return "redirect:/missions";
    }

    @GetMapping("/edit/planned/{id}")
    public String showEditPlanForm(@PathVariable(name = "id") UUID uuid, Model model){
        MissionDTO missionDTO = missionService.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("L'id " + uuid + "de la mission n'est pas valide."));
        model.addAttribute("mission", missionDTO);
        model.addAttribute("missionTypes", missionTypeService.findAllDto());
        return "missions/planned-mission/edit-planned-mission";
    }

    @PostMapping("/update/planned/{id}")
    public String updatePlanMission(@PathVariable(name = "id") UUID uuid, @ModelAttribute MissionDTO missionDTO,
                                    RedirectAttributes redirectAttributes){
        try {
            missionService.planMission(missionDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Mission planifiée mise à jour avec succès.");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la mise à jour " +
                    " de la planification de la mission.");
        }
        return "redirect:/missions";
    }

    @GetMapping({"/confirm/{id}", "/edit/confirmed/{id}"})
    public String showConfirmMissionForm(@PathVariable("id") UUID uuid, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){
        String requestUri = request.getRequestURI();
        if (volunteerService.findAll().isEmpty()) {
            redirectAttributes.addFlashAttribute("informationMessage", "Veuillez d'abord créer des bénévoles pour confirmer une mission.");
            return "redirect:/missions";
        }

        MissionConfirmationDTO missionConfirmationDTO = new MissionConfirmationDTO();

        MissionDTO missionDTO = missionService.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("L'id " + uuid + "de la mission n'est pas valide."));
        missionConfirmationDTO.setMission(missionDTO);
        if (missionDTO.getRequiredVolunteerNumber() == 0){
            redirectAttributes.addFlashAttribute("informationMessage", "Veuillez d'abord modifier le nombre de bénévoles requis pour effectuer " +
                    "la mission.");
            return "redirect:/missions";
        }

        List<VolunteerDTO> availableVolunteers = volunteerService.getAvailableUsersForMission(missionDTO.getStartDateTime(),
                missionDTO.getEndDateTime(),missionDTO.getMissionType().getUuid(), uuid)
                .stream().limit(missionDTO.getRequiredVolunteerNumber()*2L).collect(Collectors.toList());
        missionConfirmationDTO.setAvailableVolunteers(availableVolunteers);

        List<MissionAssignmentDTO> currentAssignments = missionAssignmentService.findAllCurrentMissionAssignment(uuid);

        if (availableVolunteers.isEmpty() && currentAssignments.isEmpty()) {
            redirectAttributes.addFlashAttribute("informationMessage", "Il n'y a pas de bénévoles disponibles pour la mission.");
            return "redirect:/missions";
        }

        missionConfirmationDTO.setCurrentAssignments(currentAssignments);
        List<UUID> assignedVolunteerUuids = currentAssignments.stream()
                .map(assignment -> assignment.getVolunteer().getUuid())
                .collect(Collectors.toList());
        UUID chiefUuid = currentAssignments.stream()
                .filter(MissionAssignmentDTO::isChief)
                .findFirst()
                .map(assignment -> assignment.getVolunteer().getUuid())
                .orElse(null);
        model.addAttribute("assignedVolunteerUuids", assignedVolunteerUuids);
        model.addAttribute("chiefUuid", chiefUuid);
        model.addAttribute("confirmation", missionConfirmationDTO);

        if (requestUri.contains("/edit/confirmed/")) {
            model.addAttribute("missionTypes", missionTypeService.findAllDto());
        }
        return requestUri.contains("/confirm/") ? "missions/confirmed-mission/create-confirmed-mission" : "missions/confirmed-mission/edit-confirmed-mission";
    }

    @PostMapping("/confirm/{id}")
    public String confirmMission(@PathVariable("id") UUID uuid,
                                 @RequestParam("volunteerUuids") List<UUID> volunteerUuids,
                                 @RequestParam(value = "chiefUuid", required = false) UUID chiefUuid,
                                 RedirectAttributes redirectAttributes){
        MissionDTO missionDTO = missionService.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("L'id " + uuid + "de la mission n'est pas valide."));

        try {
            missionAssignmentService.assignVolunteersToMission(uuid, volunteerUuids, chiefUuid);
            if (chiefUuid == null){
                missionService.planMission(missionDTO);
                redirectAttributes.addFlashAttribute("informationMessage",
                        "Attention, la mise à jour c'est correctement effectuée mais la mission a été déplacée" +
                                " dans les missions \"A confirmer\" car il n'y a pas de chef de mission désigné.");
            } else {
                missionService.confirmMission(missionDTO);
                redirectAttributes.addFlashAttribute("successMessage", "Mission confirmée avec succès.");
            }
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la confirmation de la mission.");
        }

        return "redirect:/missions";
    }


    @PostMapping("/update/confirmed/{id}")
    public String updateConfirmMission(@PathVariable("id") UUID uuid,
                                       @RequestParam(value = "volunteerUuids", required = false) List<UUID> volunteerUuids,
                                       @RequestParam(value = "chiefUuid", required = false) UUID chiefUuid,
                                       RedirectAttributes redirectAttributes){
        MissionDTO missionDTO = missionService.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("L'id " + uuid + "de la mission n'est pas valide."));
        if (volunteerUuids == null || volunteerUuids.isEmpty()){
            missionAssignmentService.deleteAllAssignmentsForMission(uuid);
            if (missionDTO.getStatus() == MissionStatus.CONFIRMED){
                missionService.planMission(missionDTO);
                redirectAttributes.addFlashAttribute("informationMessage",
                        "Attention, la mise à jour c'est correctement effectuée mais la mission a été déplacée" +
                                " dans les missions \"A confirmer\"");
            }
        } else {
            try {
                missionAssignmentService.assignVolunteersToMission(uuid, volunteerUuids, chiefUuid);
                if (chiefUuid == null){
                    missionService.planMission(missionDTO);
                    redirectAttributes.addFlashAttribute("informationMessage",
                            "Attention, la mise à jour c'est correctement effectuée mais la mission a été déplacée" +
                                    " dans les missions \"A confirmer\" car il n'y a pas de chef de mission désigné.");
                } else {
                    missionService.confirmMission(missionDTO);
                    redirectAttributes.addFlashAttribute("successMessage", "Mission confirmée mise à jour avec succès.");
                }
            } catch (EntityNotFoundException e) {
                redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la mise à jour de la confirmation " +
                        "de la mission.");
            }
        }
        return "redirect:/missions";
    }

    @GetMapping("/launch/{id}")
    public String launchMissionManually(@PathVariable(name = "id") UUID uuid, RedirectAttributes redirectAttributes){
        try {
            missionService.launchMission(uuid);
            redirectAttributes.addFlashAttribute("successMessage", "La mission a été lancée avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors du lancement de la mission.");
        }
        return "redirect:/missions";
    }

    @GetMapping("/end/{id}")
    public String endMissionManually(@PathVariable(name = "id") UUID uuid, RedirectAttributes redirectAttributes){
        try {
            missionService.endMission(uuid);
            redirectAttributes.addFlashAttribute("successMessage", "La mission a été terminée avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la fin de la mission.");
        }
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
}
