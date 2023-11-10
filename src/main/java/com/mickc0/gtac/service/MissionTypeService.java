package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.MissionTypeDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MissionTypeService {

    List<MissionTypeDTO> findAll();
    List<MissionTypeDTO> findAllOnlyName();
    void save(MissionTypeDTO missionTypeDTO);

    void update(MissionTypeDTO missionTypeDTO);

    MissionTypeDTO findMByUuid(UUID uuid);
   MissionTypeDTO findByName(String name);

    void deleteMissionType(UUID uuid);

    List<MissionTypeDTO> searchMissionTypes(String query);





}
