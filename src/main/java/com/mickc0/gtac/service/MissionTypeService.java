package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.MissionTypeDTO;

import java.util.List;
import java.util.UUID;

public interface MissionTypeService {

    List<MissionTypeDTO> findAll();
    void save(MissionTypeDTO missionTypeDTO);

    void update(MissionTypeDTO missionTypeDTO);

    MissionTypeDTO findMissionTypeByUuid(UUID uuid);

    void deleteMissionType(UUID uuid);

    List<MissionTypeDTO> searchMissionTypes(String query);





}
