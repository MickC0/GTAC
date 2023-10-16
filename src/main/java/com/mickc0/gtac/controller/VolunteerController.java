package com.mickc0.gtac.controller;

import com.mickc0.gtac.service.VolunteerService;
import org.springframework.stereotype.Controller;

@Controller
public class VolunteerController {

    private VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }
}
