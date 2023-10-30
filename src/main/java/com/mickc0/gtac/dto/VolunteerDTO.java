package com.mickc0.gtac.dto;

import com.mickc0.gtac.model.Volunteer;

import java.util.UUID;

public class VolunteerDTO {

    private UUID uuid;
    private String lastName;
    private String firstName;
    private String email;
    private String phoneNumber;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public VolunteerDTO toDTO(){
        VolunteerDTO dto = new VolunteerDTO();
        dto.setUuid(this.uuid);
        dto.setFirstName(this.firstName);
        dto.setLastName(this.lastName);
        dto.setEmail(this.email);
        dto.setPhoneNumber(this.phoneNumber);
        return dto;
    }

    public Volunteer toEntity(){
        Volunteer entity = new Volunteer();
        entity.setUuid(this.uuid);
        entity.setFirstName(this.firstName);
        entity.setLastName(this.lastName);
        entity.setEmail(this.email);
        entity.setPhoneNumber(this.phoneNumber);
        return entity;
    }


}
