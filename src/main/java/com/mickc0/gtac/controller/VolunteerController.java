package com.mickc0.gtac.controller;

import com.mickc0.gtac.dto.*;
import com.mickc0.gtac.entity.Volunteer;
import com.mickc0.gtac.mapper.UnavailabilityMapper;
import com.mickc0.gtac.mapper.VolunteerMapper;
import com.mickc0.gtac.service.AvailabilityService;
import com.mickc0.gtac.service.MissionTypeService;
import com.mickc0.gtac.service.UnavailabilityService;
import com.mickc0.gtac.service.VolunteerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private final UnavailabilityService unavailabilityService;
    private final UnavailabilityMapper unavailabilityMapper;



    public VolunteerController(VolunteerService volunteerService, AvailabilityService availabilityService, MissionTypeService missionTypeService, VolunteerMapper volunteerMapper, UnavailabilityService unavailabilityService, UnavailabilityMapper unavailabilityMapper) {
        this.volunteerService = volunteerService;
        this.availabilityService = availabilityService;
        this.missionTypeService = missionTypeService;
        this.volunteerMapper = volunteerMapper;
        this.unavailabilityService = unavailabilityService;
        this.unavailabilityMapper = unavailabilityMapper;
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


    //TODO refactorer dans le service
    @PostMapping
    public String saveVolunteer(@ModelAttribute("volunteer") VolunteerNewDTO volunteerNewDTO, RedirectAttributes redirectAttributes) {
        /*List<MissionType> selectedMissionTypes = new ArrayList<>();
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
            availability.setVolunteer(newVolunteer);
            availabilityService.save(availability);
            availabilities.add(availability);
        }
        newVolunteer.setAvailabilities(availabilities);

        Set<Unavailability> unavailabilities = new HashSet<>();
        for (UnavailabilityDTO unavailabilityDTO : volunteerDTO.getUnavailabilities()){
            Unavailability unavailability = new Unavailability();
            unavailability.setStartDate(unavailabilityDTO.getStartDate());
            unavailability.setEndDate(unavailabilityDTO.getEndDate());
            unavailability.setVolunteer(newVolunteer);
            unavailabilityService.save(unavailability);
            unavailabilities.add(unavailability);
        }
        newVolunteer.setUnavailabilities(unavailabilities);
        newVolunteer.setMissionTypes(new HashSet<>(selectedMissionTypes));*/
        volunteerService.save(volunteerNewDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Volontaire enregistré avec succès.");
        return "redirect:/volunteers";
    }

    @GetMapping("/edit/{id}")
    public String editVolunteerForm(@PathVariable(value = "id") Long id, Model model){
        VolunteerDTO volunteer = volunteerService.findVolunteerEditDTOById(id);
        List<MissionTypeDTO> allMissionTypes = missionTypeService.getAll();


        allMissionTypes.forEach(missionTypeDTO -> {
            if (volunteer.getMissionTypes().contains(missionTypeDTO.getId())){
                missionTypeDTO.setSelected(true);
            }
        });

        model.addAttribute("volunteer", volunteer);
        model.addAttribute("allMissionTypes", allMissionTypes);
        return "/volunteers/volunteer/edit-volunteer";
    }

    @PostMapping("/update/{id}")
    public String updateVolunteer(@PathVariable(name = "id") Long id, @ModelAttribute ("volunteer") VolunteerDTO volunteer,
                                  RedirectAttributes redirectAttributes){
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
