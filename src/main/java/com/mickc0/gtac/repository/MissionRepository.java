package com.mickc0.gtac.repository;

import com.mickc0.gtac.model.Mission;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    Mission findMissionByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);

    List<Mission> searchAllByNameContainingIgnoreCase(String query);

}
