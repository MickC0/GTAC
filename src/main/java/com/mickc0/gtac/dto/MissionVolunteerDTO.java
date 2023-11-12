package com.mickc0.gtac.dto;

import com.mickc0.gtac.model.MissionVolunteer;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class MissionVolunteerDTO {

    private UUID uuid;
    private boolean isAvailable;
    private String contactDate;
    private boolean isAffected;
    private boolean isChief;
    private boolean hasAttended;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getContactDate() {
        return contactDate;
    }

    public void setContactDate(String contactDate) {
        this.contactDate = contactDate;
    }

    public boolean isAffected() {
        return isAffected;
    }

    public void setAffected(boolean affected) {
        isAffected = affected;
    }

    public boolean isChief() {
        return isChief;
    }

    public void setChief(boolean chief) {
        isChief = chief;
    }

    public boolean isHasAttended() {
        return hasAttended;
    }

    public void setHasAttended(boolean hasAttended) {
        this.hasAttended = hasAttended;
    }
}
