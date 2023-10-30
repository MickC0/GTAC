package com.mickc0.gtac.dto;

import com.mickc0.gtac.model.Mission;
import com.mickc0.gtac.model.MissionStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class MissionDTO {
    private UUID uuid;
    private String name;
    private String description;
    private String comment;
    private String missionType;
    private String status;
    private int requiredVolunteerNumber;
    private String startingDate;
    private String endingDate;

    public MissionDTO() {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRequiredVolunteerNumber() {
        return requiredVolunteerNumber;
    }

    public void setRequiredVolunteerNumber(int requiredVolunteerNumber) {
        this.requiredVolunteerNumber = requiredVolunteerNumber;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public String getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(String endingDate) {
        this.endingDate = endingDate;
    }

    public MissionDTO toDTO() {
        MissionDTO dto = new MissionDTO();
        dto.setUuid(this.uuid);
        dto.setName(this.name);
        dto.setDescription(this.description);
        dto.setComment(this.comment);
        dto.setMissionType(this.missionType);
        try {
            dto.setStartingDate(this.startingDate);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Erreur de conversion pour missionStartingDate : " + ex.getMessage());
        }
        try {
            dto.setEndingDate(this.endingDate);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Erreur de conversion pour missionEndingDate : " + ex.getMessage());
        }
        dto.setRequiredVolunteerNumber(this.requiredVolunteerNumber);
        dto.setStatus(this.status);

        return dto;
    }

    public Mission toEntity() {
        Mission entity = new Mission();
        entity.setUuid(this.uuid);
        entity.setName(this.name);
        entity.setDescription(this.description);
        entity.setComment(this.comment);
        entity.setMissionType(this.missionType);
        try {
            entity.setStartingDate(LocalDateTime.parse(this.startingDate));
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Erreur de conversion pour missionStartingDate : " + ex.getMessage());
        }
        try {
            entity.setEndingDate(LocalDateTime.parse(this.endingDate));
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Erreur de conversion pour missionEndingDate : " + ex.getMessage());
        }
        entity.setRequiredVolunteerNumber(this.requiredVolunteerNumber);
        entity.setStatus(MissionStatus.valueOf(this.status));

        return entity;
    }

}
