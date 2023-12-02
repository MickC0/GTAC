package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.MissionTypeDTO;
import com.mickc0.gtac.entity.MissionType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MissionTypeService {

    void save(MissionTypeDTO missionTypeDTO);

    List<MissionType> findAll();
    List<MissionTypeDTO> findAllDto();
    List<MissionType> findAllById(List<Long> ids);
    void deleteByUuid(UUID uuid);
    Optional<MissionTypeDTO> findMissionTypeDTOByUuid(UUID uuid);
    Optional<MissionType> findMissionTypeByUuid(UUID uuid);

    List<MissionTypeDTO> findAllActive();


    void deactivateByUuid(UUID uuid);
}
