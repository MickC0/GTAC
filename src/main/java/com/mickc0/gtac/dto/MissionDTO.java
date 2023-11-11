package com.mickc0.gtac.dto;

import com.mickc0.gtac.model.MissionStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class MissionDTO {

    private Long id;
    private UUID uuid;
    private String name;
    private String description;
    private String comment;
    private MissionTypeDTO missionType;
    private MissionStatus status;
    private int requiredVolunteerNumber;
    private LocalDateTime startingDate;
    private LocalDateTime endingDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public LocalDateTime getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDateTime startingDate) {
        this.startingDate = startingDate;
    }

    public LocalDateTime getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(LocalDateTime endingDate) {
        this.endingDate = endingDate;
    }

    public MissionTypeDTO getMissionType() {
        return missionType;
    }

    public void setMissionType(MissionTypeDTO missionType) {
        this.missionType = missionType;
    }

    @Override
    public String toString() {
        return "MissionDTO{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", comment='" + comment + '\'' +
                ", missionType=" + missionType +
                ", status=" + status +
                ", requiredVolunteerNumber=" + requiredVolunteerNumber +
                ", startingDate=" + startingDate +
                ", endingDate=" + endingDate +
                '}';
    }
}
