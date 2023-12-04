package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.MissionAssignmentDTO;
import com.mickc0.gtac.entity.Mission;
import com.mickc0.gtac.entity.MissionAssignment;
import com.mickc0.gtac.entity.MissionStatus;
import com.mickc0.gtac.entity.Volunteer;
import com.mickc0.gtac.mapper.MissionAssignmentMapper;
import com.mickc0.gtac.repository.MissionAssignmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MissionAssignmentServiceImpl implements MissionAssignmentService {
    private final MissionAssignmentRepository missionAssignmentRepository;
    private final MissionAssignmentMapper missionAssignmentMapper;
    private final MissionService missionService;
    private final VolunteerService volunteerService;

    public MissionAssignmentServiceImpl(MissionAssignmentRepository missionAssignmentRepository, MissionAssignmentMapper missionAssignmentMapper, MissionService missionService, VolunteerService volunteerService) {
        this.missionAssignmentRepository = missionAssignmentRepository;
        this.missionAssignmentMapper = missionAssignmentMapper;
        this.missionService = missionService;
        this.volunteerService = volunteerService;
    }


    @Override
    public List<MissionAssignmentDTO> findAllCurrentMissionAssignment(UUID uuid) {
        return missionAssignmentRepository.findByMissionUuid(uuid).stream()
                .map(missionAssignmentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void assignVolunteersToMission(UUID uuid, List<UUID> volunteerUuids, UUID chiefUuid) {
        Mission mission = missionService.findMissionByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("La mission avec l'Id: " + uuid + " n'existe pas"));
        List<Volunteer> volunteers = volunteerService.findVolunteersByUuids(volunteerUuids);
        List<MissionAssignment> assignments = volunteers.stream().map(volunteer -> {
            MissionAssignment assignment = new MissionAssignment();
            assignment.setMission(mission);
            assignment.setVolunteer(volunteer);
            assignment.setAssignedFrom(mission.getStartDateTime());
            assignment.setAssignedUntil(mission.getEndDateTime());
            assignment.setChief(volunteer.getUuid().equals(chiefUuid));
            return assignment;
        }).collect(Collectors.toList());
        missionAssignmentRepository.saveAll(assignments);
        mission.setStatus(MissionStatus.CONFIRMED);
        missionService.saveMission(mission);
    }
}
