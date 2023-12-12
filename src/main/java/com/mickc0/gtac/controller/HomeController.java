package com.mickc0.gtac.controller;

import com.mickc0.gtac.dto.MissionTypeDTO;
import com.mickc0.gtac.dto.VolunteerDTO;
import com.mickc0.gtac.dto.VolunteerDetailsDTO;
import com.mickc0.gtac.dto.VolunteerRoleProfilDTO;
import com.mickc0.gtac.entity.MissionStatus;
import com.mickc0.gtac.entity.RoleName;
import com.mickc0.gtac.service.MissionService;
import com.mickc0.gtac.service.MissionTypeService;
import com.mickc0.gtac.service.RoleService;
import com.mickc0.gtac.service.VolunteerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
        List<VolunteerDetailsDTO> administrators = volunteerService.findAllVolunteerByRole(RoleName.ROLE_ADMIN);
        model.addAttribute("volunteerManagers", volunteerManagers);
        model.addAttribute("missionManagers", missionManagers);
        model.addAttribute("administrators", administrators);
        return "administration/administration";
    }

    @GetMapping("/administration/volunteer/create")
    public String showVolunteerWithRoleForm(Model model){
        model.addAttribute("newVolunteer", new VolunteerDetailsDTO());
        model.addAttribute("allRoles", roleService.findAllRoles());
        return "administration/create-volunteer-with-role";
    }

    @PostMapping("/administration/volunteer")
    public String createVolunteerWithRole(@ModelAttribute("newVolunteer") VolunteerDetailsDTO volunteerDetailsDTO,
                                          RedirectAttributes redirectAttributes,
                                          @RequestParam("roleNames") List<String> roleNames,
                                          Authentication authentication){
        volunteerDetailsDTO.setRoles(roleNames);
        volunteerService.saveOrUpdateVolunteerDetails(volunteerDetailsDTO, false, authentication);
        redirectAttributes.addFlashAttribute("successMessage", "Bénévole enregistré avec succès.");
        return "redirect:/administration";
    }

    @GetMapping("/administration/volunteer/edit/{id}")
    public String showEditVolunteerWithRoleForm(@PathVariable(value = "id") UUID uuid,
                                                Model model,
                                                Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String currentUserEmail = userDetails.getUsername();
        model.addAttribute("volunteer", volunteerService.findVolunteerDetailsByUuid(uuid));
        model.addAttribute("allRoles", roleService.findAllRoles());
        model.addAttribute("currentUserEmail", currentUserEmail);
        return "administration/edit-volunteer-with-role";
    }

    @PostMapping("/administration/volunteer/edit")
    public String updateVolunteer(@ModelAttribute("volunteer") VolunteerDetailsDTO volunteerDetailsDTO,
                                  RedirectAttributes redirectAttributes,
                                  @RequestParam(required = false, defaultValue = "false") boolean resetPassword,
                                  Authentication authentication) {
        volunteerService.saveOrUpdateVolunteerDetails(volunteerDetailsDTO, resetPassword, authentication);
        redirectAttributes.addFlashAttribute("successMessage", "Bénévole mis à jour avec succès.");
        return "redirect:/administration";
    }

    @GetMapping("/administration/profil")
    public String showProfil(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        VolunteerRoleProfilDTO volunteerRoleProfilDTO = volunteerService.findVolunteerRoleProfilByEmail(email);
        model.addAttribute("volunteer", volunteerRoleProfilDTO);
        return "/administration/profil";
    }

    @PostMapping("/administration/profil/changePassword")
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmNewPassword") String confirmNewPassword,
                                 Authentication authentication,
                                 RedirectAttributes redirectAttributes,
                                 @RequestParam(name = "source", defaultValue = "administration/profil") String sourcePage) {
        if (!newPassword.equals(confirmNewPassword)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Les mots de passe ne correspondent pas.");
            return "redirect:/administration/profil/changePassword";
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        boolean passwordChanged = volunteerService.changePassword(email, oldPassword, newPassword);
        if (!passwordChanged) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ancien mot de passe incorrect.");
            return "redirect:/administration/profil/changePassword";
        }
        redirectAttributes.addFlashAttribute("successMessage", "Mot de passe mis à jour avec succès.");
        String redirectPath = "/administration/profil";
        if (sourcePage.equals("change-password")) {
            redirectPath = "/home";
        }
        return "redirect:" + redirectPath;
    }

    @GetMapping("/change-password")
    public String showChangePasswordForm(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        VolunteerRoleProfilDTO volunteerRoleProfilDTO = volunteerService.findVolunteerRoleProfilByEmail(email);
        model.addAttribute("volunteer", volunteerRoleProfilDTO);
        String sourcePage = "change-password";
        model.addAttribute("sourcePage", sourcePage);
        return "/administration/change-password";
    }



}
