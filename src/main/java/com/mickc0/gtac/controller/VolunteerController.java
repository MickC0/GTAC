package com.mickc0.gtac.controller;

import com.mickc0.gtac.dto.*;
import com.mickc0.gtac.entity.Availability;
import com.mickc0.gtac.entity.MissionType;
import com.mickc0.gtac.entity.Volunteer;
import com.mickc0.gtac.mapper.VolunteerMapper;
import com.mickc0.gtac.service.AvailabilityService;
import com.mickc0.gtac.service.MissionTypeService;
import com.mickc0.gtac.service.VolunteerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/volunteers")
public class VolunteerController {


    private final VolunteerService volunteerService;
    private final AvailabilityService availabilityService;
    private final MissionTypeService missionTypeService;
    private final VolunteerMapper volunteerMapper;



    public VolunteerController(VolunteerService volunteerService, AvailabilityService availabilityService, MissionTypeService missionTypeService, VolunteerMapper volunteerMapper) {
        this.volunteerService = volunteerService;
        this.availabilityService = availabilityService;
        this.missionTypeService = missionTypeService;
        this.volunteerMapper = volunteerMapper;
    }

    @GetMapping
    public String volunteers(Model model){
        List<VolunteerStatusDTO> volunteers = volunteerService.findAllVolunteersWithStatus();
        model.addAttribute("volunteers", volunteers);
        return "/volunteers/volunteers";
    }


    @GetMapping("/create")
    public String showCreateForm(Model model){

        model.addAttribute("volunteer", new VolunteerNewDTO());
        model.addAttribute("allMissionTypes", missionTypeService.getAll());
        return "volunteers/volunteer/create-volunteer";
    }


    @PostMapping
    public String saveVolunteer(@ModelAttribute("volunteer") VolunteerNewDTO volunteerDTO,
                                @RequestParam(required = false) List<Long> missionTypes, RedirectAttributes redirectAttributes) {
        List<MissionType> selectedMissionTypes = new ArrayList<>();
        if (missionTypes != null && !missionTypes.isEmpty()) {
            selectedMissionTypes = missionTypeService.findAllById(missionTypes);
        }

        Volunteer newVolunteer = volunteerService.saveAndReturn(volunteerMapper.mapToEntityLowDetail(volunteerDTO));
        Set<Availability> availabilities = new HashSet<>();
        for (AvailabilityDTO availabilityDTO : volunteerDTO.getAvailabilities()) {
            Availability availability = new Availability();
            availability.setDayOfWeek(availabilityDTO.getDayOfWeek());
            availability.setStartTime(availabilityDTO.getStartTime());
            availability.setEndTime(availabilityDTO.getEndTime());
            availability.setVolunteer(newVolunteer); // Lier la disponibilité au volontaire
            availabilityService.save(availability);
            availabilities.add(availability);
        }
        newVolunteer.setAvailabilities(availabilities);
        newVolunteer.setMissionTypes(new HashSet<>(selectedMissionTypes));
        volunteerService.save(newVolunteer);
        redirectAttributes.addFlashAttribute("successMessage", "Volontaire enregistré avec succès.");
        return "redirect:/volunteers";
    }


    @GetMapping("/add-availability")
    public String AddAvailabilityForm(@RequestParam("volunteerId") Long volunteerId, Model model) {
        model.addAttribute("volunteerId", volunteerId);
        model.addAttribute("availabilityForm", new AvailabilityFormDTO());
        return "volunteers/availabilities/add-availabilities";
    }

    @PostMapping("/add-availability")
    public String addAvailability(@ModelAttribute AvailabilityFormDTO availabilityForm, @RequestParam("volunteerId") Long volunteerId,
                                  RedirectAttributes redirectAttributes, BindingResult result) {
        Volunteer volunteer = volunteerService.findById(volunteerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid volunteer Id:" + volunteerId));
        if (availabilityForm.getAvailabilities() != null) {
            for (Availability availability : availabilityForm.getAvailabilities()) {
                if (availability.getDayOfWeek() != null || availability.getStartTime() != null || availability.getEndTime() != null) {
                    if (availability.getDayOfWeek() == null || availability.getStartTime() == null || availability.getEndTime() == null || availability.getStartTime().isAfter(availability.getEndTime())) {
                        result.rejectValue("availabilities", "InvalidAvailability", "Vérifiez que tous les champs de disponibilité sont remplis correctement et que l'heure de début est antérieure à l'heure de fin.");
                        break;
                    }
                }
            }
        }
        if (result.hasErrors()) {
            redirectAttributes.addAttribute("volunteerId", volunteerId);
            return "redirect:/volunteers/add-availability";
        }
        // Ici, traitez les disponibilités soumises
        for (Availability availability : availabilityForm.getAvailabilities()) {
            Availability newAvailability = new Availability();
            newAvailability.setVolunteer(volunteer);
            newAvailability.setDayOfWeek(availability.getDayOfWeek());
            newAvailability.setStartTime(availability.getStartTime());
            newAvailability.setEndTime(availability.getEndTime());
            availabilityService.createAvailability(newAvailability);
        }
        redirectAttributes.addAttribute("volunteerId", volunteerId);
        redirectAttributes.addFlashAttribute("successMessage", "Disponibilités ajoutées avec succès.");
        return "redirect:/volunteers/add-mission-types";
    }

    @GetMapping("/add-mission-types")
    public String showAddMissionTypesForm(Model model, @RequestParam("volunteerId") Long volunteerId) {
        List<MissionType> allMissionTypes = missionTypeService.findAll();
        model.addAttribute("allMissionTypes", allMissionTypes);
        model.addAttribute("volunteerId", volunteerId);
        return "volunteers/mission-types/add-mission-types";
    }

    @PostMapping("/add-mission-types")
    public String addMissionTypes(@RequestParam("volunteerId") Long volunteerId,
                                  @RequestParam(value = "missionTypeIds", required = false) List<Long> missionTypeIds,
                                  RedirectAttributes redirectAttributes) {
        Volunteer volunteer = volunteerService.findById(volunteerId)
                .orElseThrow(() -> new EntityNotFoundException("Bénévole avec l'ID " + volunteerId + " non trouvé"));
        volunteer.getMissionTypes().clear();


        if (missionTypeIds != null && !missionTypeIds.isEmpty()) {
            for (Long missionTypeId : missionTypeIds) {
                Optional<MissionType> missionTypeOptional = missionTypeService.findById(missionTypeId);
                missionTypeOptional.ifPresent(volunteer.getMissionTypes()::add);
            }
        }

        volunteerService.save(volunteer);

        redirectAttributes.addFlashAttribute("successMessage", "Le bénévole a été créé avec succès.");
        return "redirect:/volunteers";
    }





    @GetMapping("/edit/{id}")
    public String editVolunteerForm(@PathVariable(value = "id") Long id, Model model){
        Volunteer volunteer = volunteerService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid volunteer Id:" + id));
        model.addAttribute("volunteer", volunteer);
        return "/volunteers/volunteer/edit-volunteer";
    }

    @PostMapping("/update/{id}")
    public String updateVolunteer(@PathVariable(name = "id") Long id, @ModelAttribute ("volunteer") Volunteer volunteer, RedirectAttributes redirectAttributes){
        volunteerService.updateVolunteer(volunteer);
        redirectAttributes.addFlashAttribute("successMessage", "Bénévole modifié avec succès.");
        return "redirect:/volunteers";
    }

    @GetMapping("/delete/{id}")
    public String deleteVolunteer(@PathVariable (value = "id") Long id){
        volunteerService.deleteVolunteer(id);
        return "redirect:/volunteers";
    }

    @GetMapping("/view/{id}")
    public String viewVolunteer(@PathVariable (value = "id") Long id, Model model){
        Volunteer volunteer = volunteerService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid volunteer Id:" + id));
        model.addAttribute("volunteer", volunteer);
        return "volunteers/volunteer/view-volunteer";
    }

    @GetMapping("/search")
    public String searchMissions(@RequestParam(value = "query") String query, Model model){
        //List<Volunteer> volunteers = volunteerService.searchVolunteers(query);
        //model.addAttribute("volunteers", volunteers);
        return "volunteers/volunteers";
    }
}
