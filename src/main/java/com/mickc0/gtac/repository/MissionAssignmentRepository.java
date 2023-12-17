package com.mickc0.gtac.repository;

import com.mickc0.gtac.entity.MissionAssignment;
import com.mickc0.gtac.entity.MissionStatus;
import com.mickc0.gtac.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface MissionAssignmentRepository extends JpaRepository<MissionAssignment, Long> {
    List<MissionAssignment> findByMissionId(Long missionId);
    List<MissionAssignment> findByVolunteerAndAssignedFromBeforeAndAssignedUntilAfter(Volunteer volunteer, LocalDateTime dateFrom, LocalDateTime dateTo);

    List<MissionAssignment> findByMissionUuid(UUID missionUuid);

    void deleteAllAssignmentsByMissionUuid(UUID uuid);

    boolean existsByVolunteerUuid(UUID uuid);

    @Query("SELECT ma FROM MissionAssignment ma WHERE :now BETWEEN ma.assignedFrom AND ma.assignedUntil AND ma.mission.status = 'ONGOING'")
    List<MissionAssignment> findVolunteersInMissionOnDate(@Param("now") LocalDateTime now);

}
