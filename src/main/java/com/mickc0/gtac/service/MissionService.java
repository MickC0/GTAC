package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.MissionDTO;
import com.mickc0.gtac.entity.Mission;
import com.mickc0.gtac.entity.MissionStatus;
import com.mickc0.gtac.entity.Volunteer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MissionService {

    void save(MissionDTO missionDTO);
    List<MissionDTO> findAll();
    void deleteById(Long id);
    void deleteByUuid(UUID uuid);

    Optional<Mission> findById(Long id);
    Optional<MissionDTO> findByUuid(UUID uuid);
    Optional<Mission> findMissionByUuid(UUID uuid);
    List<Mission> findByStatus(MissionStatus status);

    boolean isUserAvailableForMission(Long userId, LocalDateTime missionStart, LocalDateTime missionEnd);
    //Mission planMission(Long missionId, List<Long> userIds, Long chiefUserId);

    Mission startMission(UUID uuid);
    void cancelMission(UUID uuid);

    List<Mission> findMissionsToUpdateStatus(LocalDateTime now);
    void updateMissionStatus(Long missionId, LocalDateTime now);
    void planMission(MissionDTO missionDTO);

    void saveMission(Mission mission);
}
