package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.MissionTypeDTO;
import com.mickc0.gtac.dto.MissionTypeSelectionDTO;
import com.mickc0.gtac.entity.MissionType;

import java.util.List;
import java.util.Optional;

public interface MissionTypeService {

    void save(MissionType missionType);

    List<MissionType> findAll();
    List<MissionTypeDTO> getAll();
    List<MissionType> findAllById(List<Long> ids);
    void deleteById(Long id);

    Optional<MissionType> findById(Long id);





}
