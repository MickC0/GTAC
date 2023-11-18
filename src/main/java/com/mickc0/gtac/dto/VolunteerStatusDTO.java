package com.mickc0.gtac.dto;

import com.mickc0.gtac.entity.Volunteer;
import jakarta.persistence.Column;

import java.util.UUID;

public class VolunteerStatusDTO {

    private Long id;
    private UUID uuid;
    private String lastName;
    private String firstName;
    private String email;
    private String phoneNumber;
    private String status;

    public VolunteerStatusDTO(Long id, UUID uuid, String lastName, String firstName, String email, String phoneNumber, String status) {
        this.id = id;
        this.uuid = uuid;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
