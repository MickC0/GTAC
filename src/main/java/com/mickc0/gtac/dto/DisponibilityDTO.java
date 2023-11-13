package com.mickc0.gtac.dto;

import com.mickc0.gtac.model.Day;

import java.time.LocalTime;
import java.util.UUID;

public class DisponibilityDTO {

    private UUID uuid;
    private LocalTime startingHour;
    private LocalTime endingHour;
    private Day day;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalTime getStartingHour() {
        return startingHour;
    }

    public void setStartingHour(LocalTime startingHour) {
        this.startingHour = startingHour;
    }

    public LocalTime getEndingHour() {
        return endingHour;
    }

    public void setEndingHour(LocalTime endingHour) {
        this.endingHour = endingHour;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }
}
