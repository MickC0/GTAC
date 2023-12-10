package com.mickc0.gtac.controller;

import com.mickc0.gtac.dto.*;
import com.mickc0.gtac.exception.VolunteerInUseException;
import com.mickc0.gtac.mapper.UnavailabilityMapper;
import com.mickc0.gtac.mapper.VolunteerMapper;
import com.mickc0.gtac.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/volunteers")
public class VolunteerController {


    private final VolunteerService volunteerService;
    private final MissionTypeService missionTypeService;
    private final MissionAssignmentService missionAssignmentService;


    public VolunteerController(VolunteerService volunteerService, MissionTypeService missionTypeService, MissionAssignmentService missionAssignmentService) {
        this.volunteerService = volunteerService;
        this.missionTypeService = missionTypeService;
        this.missionAssignmentService = missionAssignmentService;
    }

    @GetMapping
    public String volunteers(Model model){
        List<VolunteerStatusDTO> volunteers = volunteerService.findAllVolunteersWithStatus();
        List<UUID> volunteersInMission = missionAssignmentService.getVolunteersInMissionToday();

        volunteers.forEach(volunteer -> {
            if (volunteersInMission.contains(volunteer.getUuid())) {
                volunteer.setStatus("En mission");
            }
        });

        model.addAttribute("volunteers", volunteers);
        return "/volunteers/volunteers";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model){
        model.addAttribute("volunteer", new VolunteerDTO());
        model.addAttribute("allMissionTypes", missionTypeService.findAllDto());
        return "volunteers/volunteer/create-volunteer";
    }

    @PostMapping
    public String saveVolunteer(@ModelAttribute("volunteer") VolunteerDTO volunteerDTO,
                                @RequestParam(name = "missionTypeUuids", required = false) List<String> missionTypeUuids,
                                RedirectAttributes redirectAttributes) {
        volunteerDTO.setMissionTypes(missionTypeUuids);
        volunteerService.saveOrUpdate(volunteerDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Bénévole enregistré avec succès.");
        return "redirect:/volunteers";
    }

    @GetMapping("/edit/{id}")
    public String editVolunteerForm(@PathVariable(value = "id") UUID uuid, Model model){
        VolunteerDTO volunteerDTO = volunteerService.findVolunteerDTOByUuid(uuid);
        List<MissionTypeDTO> allMissionTypes = missionTypeService.findAllDto();

        Set<String> selectedUuidsSet = (volunteerDTO.getMissionTypes() != null) ? new HashSet<>(volunteerDTO.getMissionTypes()) : new HashSet<>();
        for (MissionTypeDTO missionTypeDTO : allMissionTypes) {
            missionTypeDTO.setSelected(selectedUuidsSet.contains(missionTypeDTO.getUuid().toString()));
        }
        model.addAttribute("volunteer", volunteerDTO);
        model.addAttribute("allMissionTypes", allMissionTypes);
        return "/volunteers/volunteer/edit-volunteer";
    }

    @PostMapping("/update/{id}")
    public String updateVolunteer(@PathVariable(name = "id") UUID uuid,
                                  @ModelAttribute ("volunteer") VolunteerDTO volunteer,
                                  RedirectAttributes redirectAttributes){
        volunteerService.saveOrUpdate(volunteer);
        redirectAttributes.addFlashAttribute("successMessage", "Bénévole modifié avec succès.");
        return "redirect:/volunteers";
    }

    @GetMapping("/delete/{id}")
    public String deleteVolunteer(@PathVariable (value = "id") UUID uuid){
        if (missionAssignmentService.isVolunteerInUse(uuid)){
            throw new VolunteerInUseException("Impossible de supprimer un bénévole qui a déjà participé à une mission.");
        }
        volunteerService.deleteVolunteer(uuid);
        return "redirect:/volunteers";
    }

    @GetMapping("/view/{id}")
    public String viewVolunteer(@PathVariable (value = "id") UUID uuid,
                                Model model,
                                RedirectAttributes redirectAttributes){
        try {
            VolunteerProfilDTO volunteer = volunteerService.findVolunteerProfilDTOByUuid(uuid);
            model.addAttribute("volunteer", volunteer);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e);
        }


        return "volunteers/volunteer/view-volunteer";
    }

    @GetMapping("/search")
    public String searchMissions(@RequestParam(value = "query") String query, Model model){
        //List<Volunteer> volunteers = volunteerService.searchVolunteers(query);
        //model.addAttribute("volunteers", volunteers);
        return "volunteers/volunteers";
    }




}
