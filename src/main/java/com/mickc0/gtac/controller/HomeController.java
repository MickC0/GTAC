package com.mickc0.gtac.controller;

import com.mickc0.gtac.dto.VolunteerAdminDTO;
import com.mickc0.gtac.dto.VolunteerDetailsDTO;
import com.mickc0.gtac.dto.VolunteerRoleProfilDTO;
import com.mickc0.gtac.entity.MissionStatus;
import com.mickc0.gtac.entity.RoleName;
import com.mickc0.gtac.service.MissionService;
import com.mickc0.gtac.service.RoleService;
import com.mickc0.gtac.service.VolunteerService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

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
    public String home(Model model, HttpSession session){
        model.addAttribute("ongoingMissions", missionService.findByStatus(MissionStatus.ONGOING));
        model.addAttribute("confirmedMissions", missionService.findByStatus(MissionStatus.CONFIRMED));
        String errorMessage = (String) session.getAttribute("errorMessage");
        model.addAttribute("errorMessage", errorMessage);
        session.removeAttribute("errorMessage");
        boolean adminExists = volunteerService.existsAdminAccount();
        model.addAttribute("adminExists", adminExists);
        return "home";
    }
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
    @GetMapping("/create-admin")
    public String registerPage(Model model){
        model.addAttribute("admin", new VolunteerAdminDTO());
        return "create-admin";
    }

    @PostMapping("/register")
    public String registerNewAdmin(@Valid @ModelAttribute("admin") VolunteerAdminDTO volunteerAdminDTO,
                                   BindingResult result,
                                   Model model){
        if (!volunteerAdminDTO.getPassword().equals(volunteerAdminDTO.getMatchingPassword())) {
            result.rejectValue("matchingPassword", "error.admin", "Les mots de passe ne correspondent pas");
        }
        if(result.hasErrors()){
            model.addAttribute("admin", volunteerAdminDTO );
            return "create-admin";
        }
        volunteerService.saveNewAdmin(volunteerAdminDTO);
        return "redirect:/login";
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
    public String createVolunteerWithRole(@Valid @ModelAttribute("newVolunteer") VolunteerDetailsDTO volunteerDetailsDTO,
                                          BindingResult result,
                                          Model model,
                                          RedirectAttributes redirectAttributes,
                                          @RequestParam("roleNames") List<String> roleNames,
                                          Authentication authentication){
        if (result.hasErrors()){
            model.addAttribute("newVolunteer", volunteerDetailsDTO);
            return "administration/create-volunteer-with-role";
        }
        volunteerDetailsDTO.setRoles(roleNames);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        volunteerService.saveOrUpdateVolunteerDetails(volunteerDetailsDTO, authentication);
        volunteerService.resetPassword(false, email);
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
    public String updateVolunteer(@Valid @ModelAttribute("volunteer") VolunteerDetailsDTO volunteerDetailsDTO,
                                  BindingResult result,
                                  Model model,
                                  @RequestParam("roleNames") List<String> roleNames,
                                  RedirectAttributes redirectAttributes,
                                  @RequestParam(required = false, defaultValue = "false") boolean resetPassword,
                                  Authentication authentication) {
        if (result.hasErrors()){
            model.addAttribute("volunteer", volunteerDetailsDTO);
            return "administration/edit-volunteer-with-role";
        }
        volunteerDetailsDTO.setRoles(roleNames);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        volunteerService.saveOrUpdateVolunteerDetails(volunteerDetailsDTO, authentication);
        volunteerService.resetPassword(resetPassword,email);
        redirectAttributes.addFlashAttribute("successMessage", "Bénévole mis à jour avec succès.");
        return "redirect:/administration";
    }

    @GetMapping("/profil")
    public String showProfil(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        VolunteerRoleProfilDTO volunteerRoleProfilDTO = volunteerService.findVolunteerRoleProfilByEmail(email);
        model.addAttribute("volunteer", volunteerRoleProfilDTO);
        return "/profil/profil";
    }

    @GetMapping("/profil/change-password")
    public String showChangePasswordForm(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        VolunteerRoleProfilDTO volunteerRoleProfilDTO = volunteerService.findVolunteerRoleProfilByEmail(email);
        model.addAttribute("volunteer", volunteerRoleProfilDTO);
        return "/profil/change-password";
    }

    @PostMapping("/profil/change-password")
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmNewPassword") String confirmNewPassword,
                                 @RequestParam("mustChangePassword") boolean mustChangePassword,
                                 Authentication authentication,
                                 RedirectAttributes redirectAttributes) {
        if (!newPassword.equals(confirmNewPassword)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Les mots de passe ne correspondent pas.");
            return "redirect:/profil/change-password";
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        boolean passwordChanged = volunteerService.changePassword(email, oldPassword, newPassword);
        if (!passwordChanged) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ancien mot de passe incorrect.");
            return "redirect:/profil/change-password";
        }
        redirectAttributes.addFlashAttribute("successMessage", "Mot de passe mis à jour avec succès.");
        String redirectPath = "/profil";
        if (mustChangePassword) {
            redirectPath = "/home";
        }
        return "redirect:" + redirectPath;
    }






}
