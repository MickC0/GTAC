package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.MissionDTO;

import java.util.List;
import java.util.UUID;

public interface MissionService {

    List<MissionDTO> findAll();

    void saveMission(MissionDTO missionDTO);

    void updateMission(MissionDTO missionDTO);

    MissionDTO findMissionByUUID(UUID uuid);

    void deleteMission(UUID uuid);

    List<MissionDTO> searchMissions(String query);


}
