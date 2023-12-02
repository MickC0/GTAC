package com.mickc0.gtac.repository;

import com.mickc0.gtac.entity.MissionType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MissionTypeRepository extends JpaRepository<MissionType, Long> {
    Optional<MissionType> findByName(String name);
    Optional<MissionType> findByUuid(UUID uuid);
    @Query("SELECT mt FROM MissionType mt WHERE mt.isActive = true")
    List<MissionType> findAllActive(Sort sort);


    void deleteByUuid(UUID uuid);
}
