package com.mickc0.gtac.controller;

import com.mickc0.gtac.model.CompletionDate;
import com.mickc0.gtac.model.Mission;
import com.mickc0.gtac.service.CompletionDateService;
import com.mickc0.gtac.service.MissionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class MissionController {
    private final MissionService missionService;
    private final CompletionDateService completionDateService;

    @GetMapping("/index")
    public String index(Model model){
        List<Mission> missionList=missionService.findAll();
        model.addAttribute("listMissions",missionList);
        List<CompletionDate> completionDateList=completionDateService.findAll();
        model.addAttribute("listCompletionDates",completionDateList);
        return "missions";
    }
}
