package com.mickc0.gtac.controller;

import com.mickc0.gtac.dto.VolunteerStatusDTO;
import com.mickc0.gtac.entity.Volunteer;
import com.mickc0.gtac.service.VolunteerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/volunteers")
public class VolunteerController {


    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
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
        return "volunteers/create-volunteer";
    }

    @PostMapping
    public String createVolunteer(@ModelAttribute("volunteer") Volunteer volunteer){
        volunteerService.save(volunteer);
        return "redirect:/volunteers";
    }

    @GetMapping("/edit/{id}")
    public String editVolunteerForm(@PathVariable(value = "id") Long id, Model model){
        Volunteer volunteer = volunteerService.findVolunteerById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid volunteer Id:" + id));
        model.addAttribute("volunteer", volunteer);
        return "/volunteers/edit-volunteer";
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
        return "volunteers/view-volunteer";
    }

    @GetMapping("/search")
    public String searchMissions(@RequestParam(value = "query") String query, Model model){
        //List<Volunteer> volunteers = volunteerService.searchVolunteers(query);
        //model.addAttribute("volunteers", volunteers);
        return "volunteers/volunteers";
    }
}
