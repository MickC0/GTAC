package com.mickc0.gtac.dto;

import com.mickc0.gtac.model.Mission;
import com.mickc0.gtac.model.MissionStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class MissionDTO {
    private Long id;
    private UUID uuid;
    private String name;
    private String description;
    private String comment;
    private String missionType;
    private MissionStatus status;
    private int requiredVolunteerNumber;
    private LocalDateTime startingDate;
    private LocalDateTime endingDate;

    public MissionDTO() {
    }

    public MissionDTO(String name, String description, String comment, String missionType, int requiredVolunteerNumber, LocalDateTime startingDate, LocalDateTime endingDate) {
        this.name = name;
        this.description = description;
        this.comment = comment;
        this.missionType = missionType;
        this.requiredVolunteerNumber = requiredVolunteerNumber;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
    }

    public long getId() {
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

    public String getMissionType() {
        return missionType;
    }

    public void setMissionType(String missionType) {
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



}
