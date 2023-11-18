package com.mickc0.gtac.controller;

import com.mickc0.gtac.dto.AvailabilityFormDTO;
import com.mickc0.gtac.dto.VolunteerStatusDTO;
import com.mickc0.gtac.entity.Availability;
import com.mickc0.gtac.entity.Volunteer;
import com.mickc0.gtac.service.AvailabilityService;
import com.mickc0.gtac.service.VolunteerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/volunteers")
public class VolunteerController {


    private final VolunteerService volunteerService;
    private final AvailabilityService availabilityService;


    public VolunteerController(VolunteerService volunteerService, AvailabilityService availabilityService) {
        this.volunteerService = volunteerService;
        this.availabilityService = availabilityService;
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
        return "volunteers/availability/add-availability";
    }

    @PostMapping("/add-availability")
    public String addAvailability(@ModelAttribute AvailabilityFormDTO availabilityForm, @RequestParam("volunteerId") Long volunteerId) {
        Volunteer volunteer = volunteerService.findVolunteerById(volunteerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid volunteer Id:" + volunteerId));
        // Ici, traitez les disponibilités soumises
        for (Availability availability : availabilityForm.getAvailabilities()) {
            Availability newAvailability = new Availability();
            newAvailability.setVolunteer(volunteer);
            newAvailability.setDayOfWeek(availability.getDayOfWeek());
            newAvailability.setStartTime(availability.getStartTime());
            newAvailability.setEndTime(availability.getEndTime());
            availabilityService.createAvailability(newAvailability);
        }
        return "redirect:/volunteers";
    }


    @GetMapping("/edit/{id}")
    public String editVolunteerForm(@PathVariable(value = "id") Long id, Model model){
        Volunteer volunteer = volunteerService.findVolunteerById(id)
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
        Volunteer volunteer = volunteerService.findVolunteerById(id)
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
