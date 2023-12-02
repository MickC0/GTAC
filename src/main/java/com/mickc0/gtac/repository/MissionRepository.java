package com.mickc0.gtac.repository;

import com.mickc0.gtac.entity.Mission;
import com.mickc0.gtac.entity.MissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    @Query("SELECT m FROM Mission m WHERE m.startDateTime <= :now AND m.status = com.mickc0.gtac.entity.MissionStatus.PLANNED")
    List<Mission> findMissionsToStart(LocalDateTime now);

    @Query("SELECT m FROM Mission m WHERE m.endDateTime <= :now AND m.status = com.mickc0.gtac.entity.MissionStatus.ONGOING")
    List<Mission> findMissionsToComplete(LocalDateTime now);

    List<Mission> findByStatusAndStartDateTimeLessThanEqual(MissionStatus missionStatus, LocalDateTime now);

    List<Mission> findByStatusAndEndDateTimeLessThanEqual(MissionStatus missionStatus, LocalDateTime now);

    List<Mission> findByStatus(MissionStatus status);

    Optional<Mission> findByUuid(UUID uuid);

}
