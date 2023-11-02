package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.MissionDTO;

import java.util.List;

public interface MissionService {

    List<MissionDTO> findAll();

    void save(MissionDTO missionDTO);


}
