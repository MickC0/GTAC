package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.MissionTypeDTO;
import com.mickc0.gtac.entity.MissionType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MissionTypeService {

    void save(MissionTypeDTO missionTypeDTO);

    List<MissionType> findAll();
    List<MissionTypeDTO> getAll();
    List<MissionType> findAllById(List<Long> ids);
    void deleteById(Long id);

    Optional<MissionTypeDTO> findByUuid(UUID uuid);





}
