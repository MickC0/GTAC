package com.mickc0.gtac.repository;

import com.mickc0.gtac.entity.MissionType;
import com.mickc0.gtac.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    @Query("SELECT v FROM Volunteer v WHERE v.id NOT IN " +
            "(SELECT a.volunteer.id FROM MissionAssignment a WHERE a.mission.uuid != :currentMissionUuid AND a.mission.startDateTime <= :missionEnd " +
            "AND a.mission.endDateTime >= :missionStart) AND v.id NOT IN " +
            "(SELECT i.volunteer.id FROM Unavailability i WHERE i.startDate <= :missionEnd AND i.endDate >= :missionStart) " +
            "AND EXISTS (SELECT d FROM Availability d WHERE d.volunteer = v AND d.dayOfWeek = :dayOfWeek AND d.startTime <= :startTime AND d.endTime >= :endTime) " +
            "AND EXISTS (SELECT mt FROM v.missionTypes mt WHERE mt.uuid = :missionTypeUuid)")
    List<Volunteer> findAvailableVolunteersForMission(LocalDateTime missionStart, LocalDateTime missionEnd, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, UUID missionTypeUuid, UUID currentMissionUuid);

    Optional<Volunteer> findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);

    List<Volunteer> findAllByUuidIn(List<UUID> volunteerUuids);

    Optional<Volunteer> findByEmail(String username);
}
