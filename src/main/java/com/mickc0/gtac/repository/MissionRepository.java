package com.mickc0.gtac.repository;

import com.mickc0.gtac.model.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long> {
}
