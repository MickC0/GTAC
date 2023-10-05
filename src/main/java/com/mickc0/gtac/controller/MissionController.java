package com.mickc0.gtac.controller;

import com.mickc0.gtac.model.Mission;
import com.mickc0.gtac.repository.MissionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class MissionController {
    private MissionRepository missionRepository;

    @GetMapping("/index")
    public String index(Model model){
        List<Mission> missionList=missionRepository.findAll();
        model.addAttribute("listMissions",missionList);
        return "missions";
    }
}
