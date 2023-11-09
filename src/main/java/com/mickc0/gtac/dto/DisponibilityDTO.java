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
}
