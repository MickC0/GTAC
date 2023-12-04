package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.MissionAssignmentDTO;

import java.util.List;
import java.util.UUID;

public interface MissionAssignmentService {

    List<MissionAssignmentDTO> findAllCurrentMissionAssignment(UUID uuid);

    void assignVolunteersToMission(UUID uuid, List<UUID> volunteerUuids, UUID chiefUuid);

    void deleteAllAssignmentsForMission(UUID uuid);
}
