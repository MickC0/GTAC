package com.mickc0.gtac.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class MissionAssignmentDTO {
    private UUID uuid;
    private VolunteerDTO volunteer;
    private boolean isChief;
    private LocalDateTime assignedFrom;
    private LocalDateTime assignedUntil;
    private boolean hasParticiped;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public VolunteerDTO getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(VolunteerDTO volunteer) {
        this.volunteer = volunteer;
    }

    public boolean isChief() {
        return isChief;
    }

    public void setChief(boolean chief) {
        isChief = chief;
    }

    public LocalDateTime getAssignedFrom() {
        return assignedFrom;
    }

    public void setAssignedFrom(LocalDateTime assignedFrom) {
        this.assignedFrom = assignedFrom;
    }

    public LocalDateTime getAssignedUntil() {
        return assignedUntil;
    }

    public void setAssignedUntil(LocalDateTime assignedUntil) {
        this.assignedUntil = assignedUntil;
    }

    public boolean hasParticiped() {
        return hasParticiped;
    }

    public void setHasParticiped(boolean hasParticiped) {
        this.hasParticiped = hasParticiped;
    }
}
