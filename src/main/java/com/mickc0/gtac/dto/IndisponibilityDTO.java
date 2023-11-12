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
}
