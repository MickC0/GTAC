package com.mickc0.gtac.mapper;

import com.mickc0.gtac.dto.MissionAssignmentDTO;
import com.mickc0.gtac.entity.MissionAssignment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MissionAssignmentMapper {
    private final VolunteerMapper volunteerMapper;

    public MissionAssignmentMapper(VolunteerMapper volunteerMapper) {
        this.volunteerMapper = volunteerMapper;
    }

    public MissionAssignmentDTO mapToDto(MissionAssignment missionAssignment){
        MissionAssignmentDTO missionAssignmentDTO = new MissionAssignmentDTO();
        missionAssignmentDTO.setUuid(missionAssignment.getUuid());
        missionAssignmentDTO.setVolunteer(volunteerMapper.mapToConfirmDto(missionAssignment.getVolunteer()));
        missionAssignmentDTO.setAssignedFrom(missionAssignment.getAssignedFrom());
        missionAssignmentDTO.setAssignedUntil(missionAssignment.getAssignedUntil());
        missionAssignmentDTO.setChief(missionAssignment.isChief());
        missionAssignmentDTO.setHasParticiped(missionAssignment.hasParticipated());
        return missionAssignmentDTO;
    }


}
