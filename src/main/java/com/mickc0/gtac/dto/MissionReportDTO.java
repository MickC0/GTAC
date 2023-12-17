package com.mickc0.gtac.dto;

import java.util.List;

public class MissionReportDTO {
    private MissionDTO missionDTO;
    private List<MissionAssignmentDTO> missionAssignments;

    public MissionDTO getMissionDTO() {
        return missionDTO;
    }

    public void setMissionDTO(MissionDTO missionDTO) {
        this.missionDTO = missionDTO;
    }

    public List<MissionAssignmentDTO> getMissionAssignments() {
        return missionAssignments;
    }

    public void setMissionAssignments(List<MissionAssignmentDTO> missionAssignments) {
        this.missionAssignments = missionAssignments;
    }
}
