package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.MissionTypeDTO;

import java.util.List;
import java.util.UUID;

public interface MissionTypeService {

    List<MissionTypeDTO> findAll();
    List<MissionTypeDTO> findAllOnlyUuidName();
    void save(MissionTypeDTO missionTypeWithoutIdDTO);

    void update(MissionTypeDTO missionTypeWithoutIdDTO);

    MissionTypeDTO findByUuid(UUID uuid);
    MissionTypeDTO findByName(String name);

    void deleteMissionType(UUID uuid);

    List<MissionTypeDTO> searchMissionTypes(String query);

    MissionTypeDTO findByUuidFullDto(UUID uuid);





}
