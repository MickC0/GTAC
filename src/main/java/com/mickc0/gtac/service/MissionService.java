package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.MissionDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MissionService {

    List<MissionDTO> findAll();

    void save(MissionDTO missionDTO);

    void update(MissionDTO missionDTO);

    MissionDTO findByUUID(UUID uuid);



}
