package com.mickc0.gtac.controller;

import com.mickc0.gtac.dto.AvailabilityFormDTO;
import com.mickc0.gtac.dto.VolunteerStatusDTO;
import com.mickc0.gtac.entity.Availability;
import com.mickc0.gtac.entity.MissionType;
import com.mickc0.gtac.entity.Volunteer;
import com.mickc0.gtac.service.AvailabilityService;
import com.mickc0.gtac.service.MissionTypeService;
import com.mickc0.gtac.service.VolunteerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/volunteers")
public class VolunteerController {


    private final VolunteerService volunteerService;
    private final AvailabilityService availabilityService;
    private final MissionTypeService missionTypeService;


    public VolunteerController(VolunteerService volunteerService, AvailabilityService availabilityService, MissionTypeService missionTypeService) {
        this.volunteerService = volunteerService;
        this.availabilityService = availabilityService;
        this.missionTypeService = missionTypeService;
    }

    @GetMapping
    public String volunteers(Model model){
        List<VolunteerStatusDTO> volunteers = volunteerService.findAllVolunteersWithStatus();
        model.addAttribute("volunteers", volunteers);
        return "/volunteers/volunteers";
    }


    @GetMapping("/create")
    public String showCreateForm(Model model){
        model.addAttribute("volunteer", new Volunteer());
        return "volunteers/volunteer/create-volunteer";
    }

    @PostMapping
    public String createVolunteer(@ModelAttribute("volunteer") Volunteer volunteer, RedirectAttributes redirectAttributes){
        Volunteer savedVolunteer = volunteerService.save(volunteer);
        redirectAttributes.addAttribute("volunteerId", savedVolunteer.getId());
        return "redirect:/volunteers/add-availability";
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
        List<MissionType> allMissionTypes = missionTypeService.findAll(); // Récupérez tous les types de missions
        model.addAttribute("allMissionTypes", allMissionTypes);
        model.addAttribute("volunteerId", volunteerId);
        return "volunteers/mission-types/add-mission-types";
    }

    @PostMapping("/add-mission-types")
    public String addMissionTypes(@RequestParam("volunteerId") Long volunteerId,
                                  @RequestParam(value = "missionTypeIds", required = false) List<Long> missionTypeIds,
                                  RedirectAttributes redirectAttributes) {
        Volunteer volunteer = volunteerService.findById(volunteerId)
                .orElseThrow(() -> new EntityNotFoundException("Volontaire avec l'ID " + volunteerId + " non trouvé"));

        // Nettoyez les anciens types de mission associés si nécessaire
        volunteer.getPreferredMissionTypes().clear();

        // Vérifiez si des types de mission ont été sélectionnés
        if (missionTypeIds != null && !missionTypeIds.isEmpty()) {
            for (Long missionTypeId : missionTypeIds) {
                Optional<MissionType> missionTypeOptional = missionTypeService.findById(missionTypeId);
                missionTypeOptional.ifPresent(volunteer.getPreferredMissionTypes()::add);
            }
        }

        volunteerService.save(volunteer); // Sauvegardez les modifications du volontaire

        redirectAttributes.addFlashAttribute("successMessage", "Le volontaire a été créé avec succès.");
        return "redirect:/volunteers"; // Redirigez vers la liste des volontaires ou une autre page appropriée
    }





    @GetMapping("/edit/{id}")
    public String editVolunteerForm(@PathVariable(value = "id") Long id, Model model){
        Volunteer volunteer = volunteerService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid volunteer Id:" + id));
        model.addAttribute("volunteer", volunteer);
        return "/volunteers/volunteer/edit-volunteer";
    }

    @PostMapping("/update/{id}")
    public String updateVolunteer(@PathVariable(name = "id") Long id, @ModelAttribute ("volunteer") Volunteer volunteer){
        volunteerService.updateVolunteer(volunteer);
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
