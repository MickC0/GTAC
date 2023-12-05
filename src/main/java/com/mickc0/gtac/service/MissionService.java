package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.MissionDTO;
import com.mickc0.gtac.entity.Mission;
import com.mickc0.gtac.entity.MissionStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MissionService {

    void save(MissionDTO missionDTO);
    void planMission(MissionDTO missionDTO);
    void confirmMission(MissionDTO missionDTO);
    void launchMission(UUID uuid);
    void endMission(UUID uuid);

    List<MissionDTO> findAll();

    void deleteById(Long id);
    void deleteByUuid(UUID uuid);
    Optional<Mission> findById(Long id);
    Optional<MissionDTO> findByUuid(UUID uuid);

    Optional<Mission> findMissionByUuid(UUID uuid);

    List<MissionDTO> findByStatus(MissionStatus status);

    void cancelMission(UUID uuid);
    List<Mission> findMissionsToUpdateStatus(LocalDateTime now);
    void updateMissionStatus(Long missionId, LocalDateTime now);

    void saveMission(Mission mission);

}
