package com.mickc0.gtac.repository;

import com.mickc0.gtac.entity.MissionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MissionTypeRepository extends JpaRepository<MissionType, Long> {
    Optional<MissionType> findByName(String name);
    Optional<MissionType> findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);
}
