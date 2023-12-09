package com.mickc0.gtac.controller;

import com.mickc0.gtac.dto.VolunteerDTO;
import com.mickc0.gtac.dto.VolunteerDetailsDTO;
import com.mickc0.gtac.dto.VolunteerStatusDTO;
import com.mickc0.gtac.entity.MissionStatus;
import com.mickc0.gtac.entity.RoleName;
import com.mickc0.gtac.service.MissionService;
import com.mickc0.gtac.service.RoleService;
import com.mickc0.gtac.service.VolunteerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class HomeController {

    private final MissionService missionService;
    private final VolunteerService volunteerService;
    private final RoleService roleService;

    public HomeController(MissionService missionService, VolunteerService volunteerService, RoleService roleService) {
        this.missionService = missionService;
        this.volunteerService = volunteerService;
        this.roleService = roleService;
    }

    @GetMapping({"","/","/home"})
    public String home(Model model){
        model.addAttribute("ongoingMissions", missionService.findByStatus(MissionStatus.ONGOING));
        model.addAttribute("confirmedMissions", missionService.findByStatus(MissionStatus.CONFIRMED));
        return "home";
    }
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/administration")
    public String administration(Model model){
        List<VolunteerDetailsDTO> volunteerManagers = volunteerService.findAllVolunteerByRole(RoleName.ROLE_VOLUNTEER);
        List<VolunteerDetailsDTO> missionManagers = volunteerService.findAllVolunteerByRole(RoleName.ROLE_MISSION);
        model.addAttribute("volunteerManagers", volunteerManagers);
        model.addAttribute("missionManagers", missionManagers);
        return "administration/administration";
    }

    @GetMapping("/administration/volunteer/create")
    public String showVolunteerWithRoleForm(Model model){
        model.addAttribute("newVolunteer", new VolunteerDetailsDTO());
        model.addAttribute("allRoles", roleService.findAllRoles());
        return "administration/create-volunteer-with-role";
    }

    @PostMapping("/administration/volunteer")
    public String createVolunteerWithRole(Model model, RedirectAttributes redirectAttributes){

        return "redirect:/administration";
    }

}
