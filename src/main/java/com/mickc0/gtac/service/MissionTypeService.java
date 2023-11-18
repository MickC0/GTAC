package com.mickc0.gtac.service;

import com.mickc0.gtac.entity.MissionType;

import java.util.List;
import java.util.Optional;

public interface MissionTypeService {

    void save(MissionType missionType);
    List<MissionType> findAll();
    void deleteById(Long id);

    Optional<MissionType> findById(Long id);





}
