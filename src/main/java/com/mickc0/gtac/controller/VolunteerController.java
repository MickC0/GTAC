package com.mickc0.gtac.controller;

import com.mickc0.gtac.dto.MissionDTO;
import com.mickc0.gtac.dto.VolunteerDTO;
import com.mickc0.gtac.service.VolunteerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
public class VolunteerController {

    private VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @GetMapping("/volunteers")
    public String volunteers(Model model){
        model.addAttribute("volunteers", volunteerService.findAll());
        return "/volunteers/volunteers";
    }


    @GetMapping("/volunteers/new")
    public String createVolunteerForm(Model model){
        model.addAttribute("volunteer", new VolunteerDTO());
        return "volunteers/create_volunteer";
    }

    @PostMapping("/volunteers")
    public String saveMission(@ModelAttribute("volunteer") VolunteerDTO volunteerDTO){
        volunteerService.saveVolunteer(volunteerDTO);
        return "redirect:/volunteers";
    }

    @GetMapping("/volunteers/edit/{id}")
    public String editVolunteerForm(@PathVariable(value = "id") UUID uuid, Model model){
        model.addAttribute("volunteer", volunteerService.findVolunteerByUUID(uuid));
        return "/volunteers/edit_volunteer";
    }

    @PostMapping("/volunteers/update")
    public String updateVolunteer(@ModelAttribute ("volunteer") VolunteerDTO volunteerDTO){
        volunteerService.updateVolunteer(volunteerDTO);
        return "redirect:/volunteers";
    }

    @GetMapping("/volunteers/delete/{id}")
    public String deleteVolunteer(@PathVariable (value = "id") UUID uuid){
        volunteerService.deleteVolunteer(uuid);
        return "redirect:/volunteers";
    }

    @GetMapping("/volunteers/view/{id}")
    public String viewVolunteer(@PathVariable(value = "id") UUID uuid, Model model){
        model.addAttribute("volunteer", volunteerService.findVolunteerByUUID(uuid));
        return "volunteers/view_volunteer";
    }

    @GetMapping("/volunteers/search")
    public String searchMissions(@RequestParam(value = "query") String query, Model model){
        List<VolunteerDTO> volunteers = volunteerService.searchVolunteers(query);
        model.addAttribute("volunteers", volunteers);
        return "volunteers/volunteers";
    }
}
