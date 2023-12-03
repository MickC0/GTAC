package com.mickc0.gtac.dto;

import java.util.List;

public class MissionConfirmationDTO {
    private MissionDTO mission;
    private List<VolunteerDTO> availableVolunteers;
    private List<MissionAssignmentDTO> currentAssignments;

    public MissionDTO getMission() {
        return mission;
    }

    public void setMission(MissionDTO mission) {
        this.mission = mission;
    }

    public List<VolunteerDTO> getAvailableVolunteers() {
        return availableVolunteers;
    }

    public void setAvailableVolunteers(List<VolunteerDTO> availableVolunteers) {
        this.availableVolunteers = availableVolunteers;
    }

    public List<MissionAssignmentDTO> getCurrentAssignments() {
        return currentAssignments;
    }

    public void setCurrentAssignments(List<MissionAssignmentDTO> currentAssignments) {
        this.currentAssignments = currentAssignments;
    }
}
