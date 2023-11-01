package com.mickc0.gtac.dto;

import com.mickc0.gtac.model.Mission;
import com.mickc0.gtac.model.MissionStatus;
import com.mickc0.gtac.model.MissionType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class MissionDTO {
    private UUID uuid;
    private String name;
    private String description;
    private String comment;
    private String missionType;
    private MissionStatus status;
    private int requiredVolunteerNumber;
    private LocalDateTime startingDate;
    private LocalDateTime endingDate;

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
            entity.setStartingDate(this.startingDate);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Erreur de conversion pour missionStartingDate : " + ex.getMessage());
        }
        try {
            entity.setEndingDate(this.endingDate);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Erreur de conversion pour missionEndingDate : " + ex.getMessage());
        }
        entity.setRequiredVolunteerNumber(this.requiredVolunteerNumber);
        entity.setStatus(MissionStatus.valueOf(String.valueOf(this.status)));

        return entity;
    }

}
