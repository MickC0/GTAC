package com.mickc0.gtac.dto;

import com.mickc0.gtac.model.Day;
import com.mickc0.gtac.model.Disponibility;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class DisponibilityDTO {

    private Long id;
    private UUID uuid;
    private String startingHour;
    private String endingHour;
    private String day;

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

    public String getStartingHour() {
        return startingHour;
    }

    public void setStartingHour(String startingHour) {
        this.startingHour = startingHour;
    }

    public String getEndingHour() {
        return endingHour;
    }

    public void setEndingHour(String endingHour) {
        this.endingHour = endingHour;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public DisponibilityDTO toDTO() {
        DisponibilityDTO dto = new DisponibilityDTO();
        dto.setUuid(this.uuid);
        dto.setStartingHour(this.startingHour);
        dto.setEndingHour(this.endingHour);
        dto.setDay(this.day);
        return dto;
    }

    public Disponibility toEntity() {
        Disponibility entity = new Disponibility();
        entity.setUuid(this.uuid);

        try {
            //TODO parser avec LocalTime.parse + formatter ?
            entity.setStartingHour(LocalTime.parse(this.startingHour));
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Erreur de conversion pour startingHour : " + ex.getMessage());
        }

        try {
            //TODO parser avec LocalTime.parse + formatter ?
            entity.setEndingHour(LocalTime.parse(this.endingHour));
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Erreur de conversion pour endingHour : " + ex.getMessage());
        }

        entity.setDay(Day.valueOf(this.day));

        return entity;
    }

}
