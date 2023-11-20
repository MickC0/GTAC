package com.mickc0.gtac.repository;

import com.mickc0.gtac.entity.MissionAssignment;
import com.mickc0.gtac.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MissionAssignmentRepository extends JpaRepository<MissionAssignment, Long> {
    List<MissionAssignment> findByMissionId(Long missionId);
    List<MissionAssignment> findByVolunteerAndAssignedFromBeforeAndAssignedUntilAfter(Volunteer volunteer, LocalDateTime dateFrom, LocalDateTime dateTo);

}