package com.mickc0.gtac.dto;

import com.mickc0.gtac.model.Day;
import com.mickc0.gtac.model.Disponibility;
import com.mickc0.gtac.model.Indisponibility;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class IndisponibilityDTO {

    private UUID uuid;
    private String startingDate;
    private String endingDate;


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    public IndisponibilityDTO toDTO() {
        IndisponibilityDTO dto = new IndisponibilityDTO();
        dto.setUuid(this.uuid);
        try {
            dto.setStartingDate(this.startingDate);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Erreur de conversion pour startingDate : " + ex.getMessage());
        }
        try {
            dto.setEndingDate(this.endingDate);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Erreur de conversion pour endingDate : " + ex.getMessage());
        }
        return dto;
    }

    public Indisponibility toEntity() {
        Indisponibility entity = new Indisponibility();
        entity.setUuid(this.uuid);

        try {
            entity.setStartingDate(LocalDate.parse(this.startingDate));
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Erreur de conversion pour startingDate : " + ex.getMessage());
        }

        try {
            entity.setEndingDate(LocalDate.parse(this.endingDate));
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Erreur de conversion pour endingDate : " + ex.getMessage());
        }


        return entity;
    }
}
