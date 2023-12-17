package com.mickc0.gtac.dto;

import java.util.List;

public class MissionViewDTO {

    private MissionDTO missionDTO;
    private List<MissionAssignmentDTO> assignedVolunteers;

    public MissionDTO getMissionDTO() {
        return missionDTO;
    }

    public void setMissionDTO(MissionDTO missionDTO) {
        this.missionDTO = missionDTO;
    }

    public List<MissionAssignmentDTO> getAssignedVolunteers() {
        return assignedVolunteers;
    }

    public void setAssignedVolunteers(List<MissionAssignmentDTO> assignedVolunteers) {
        this.assignedVolunteers = assignedVolunteers;
    }
}
