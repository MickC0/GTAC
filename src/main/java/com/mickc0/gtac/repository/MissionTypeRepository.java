package com.mickc0.gtac.repository;

import com.mickc0.gtac.model.MissionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MissionTypeRepository extends JpaRepository<MissionType, Long> {

    Optional<MissionType> findMissionTypeByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);

    List<MissionType> findAllByNameContainsIgnoreCase(String query);
}
