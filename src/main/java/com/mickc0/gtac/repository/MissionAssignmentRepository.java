package com.mickc0.gtac.repository;

import com.mickc0.gtac.entity.MissionAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissionAssignmentRepository extends JpaRepository<MissionAssignment, Long> {
    List<MissionAssignment> findByMissionId(Long missionId);

}
