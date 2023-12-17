package com.mickc0.gtac.dto;

import com.mickc0.gtac.entity.MissionStatus;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class MissionDTO {

    private UUID uuid;
    @Size(min = 3, max = 20)
    private String title;
    private String description;
    private String comment;
    private MissionTypeDTO missionType;
    private MissionStatus status;
    private int requiredVolunteerNumber;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private boolean reportDone;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public MissionTypeDTO getMissionType() {
        return missionType;
    }

    public void setMissionType(MissionTypeDTO missionType) {
        this.missionType = missionType;
    }

    public MissionStatus getStatus() {
        return status;
    }

    public void setStatus(MissionStatus status) {
        this.status = status;
    }

    public int getRequiredVolunteerNumber() {
        return requiredVolunteerNumber;
    }

    public void setRequiredVolunteerNumber(int requiredVolunteerNumber) {
        this.requiredVolunteerNumber = requiredVolunteerNumber;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public boolean isReportDone() {
        return reportDone;
    }

    public void setReportDone(boolean reportDone) {
        this.reportDone = reportDone;
    }
}
