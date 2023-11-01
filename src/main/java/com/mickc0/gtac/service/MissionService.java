package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.MissionDTO;
import com.mickc0.gtac.model.Mission;

import java.util.List;

public interface MissionService {

    List<Mission> findAll();
    void save(MissionDTO missionDTO);
}
