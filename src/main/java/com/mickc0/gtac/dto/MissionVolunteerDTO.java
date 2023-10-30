package com.mickc0.gtac.dto;

import com.mickc0.gtac.model.MissionVolunteer;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class MissionVolunteerDTO {

    private boolean isAvailable;
    private String contactDate;
    private boolean isAffected;
    private boolean isChief;
    private boolean hasAttended;

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

    public MissionVolunteerDTO toDTO(){
        MissionVolunteerDTO dto = new MissionVolunteerDTO();
        dto.setAvailable(this.isAvailable);
        try {
            dto.setContactDate(this.contactDate);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Erreur de conversion pour startingDate : " + ex.getMessage());
        }
        dto.setAvailable(this.isAffected);
        dto.setChief(this.isChief);
        dto.setHasAttended(this.hasAttended);
        return dto;
    }

    public MissionVolunteer toEntity() {
        MissionVolunteer entity = new MissionVolunteer();
        entity.setAvailable(this.isAvailable);
        try {
            entity.setContactDate(LocalDate.parse(this.contactDate));
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Erreur de conversion pour startingDate : " + ex.getMessage());
        }
        entity.setAvailable(this.isAffected);
        entity.setChief(this.isChief);
        entity.setHasAttended(this.hasAttended);
        return entity;
    }
}
