package com.mickc0.gtac.service;

import com.mickc0.gtac.entity.Mission;
import com.mickc0.gtac.entity.MissionStatus;
import com.mickc0.gtac.entity.Volunteer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MissionService {

    void save(Mission mission);
    List<Mission> findAll();
    void deleteById(Long id);

    Optional<Mission> findById(Long id);
    List<Mission> findByStatus(MissionStatus status);

    boolean isUserAvailableForMission(Long userId, LocalDateTime missionStart, LocalDateTime missionEnd);
    //Mission planMission(Long missionId, List<Long> userIds, Long chiefUserId);
    List<Volunteer> getAvailableUsersForMission(Long missionId, Long missionTypeId);
    Mission startMission(Long missionId);
    void cancelMission(Long missionId);

    List<Mission> findMissionsToUpdateStatus(LocalDateTime now);
    void updateMissionStatus(Long missionId, LocalDateTime now);
    void planMission(Mission mission);



}
