package com.mickc0.gtac.dto;

import com.mickc0.gtac.entity.Volunteer;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class VolunteerStatusDTO {

    private UUID uuid;
    @Size(min = 3, max = 20)
    private String lastName;
    @Size(min = 3, max = 20)
    private String firstName;
    @Email(message = "Adresse email non valide.")
    private String email;
    @Pattern(regexp = "(\\+33\\s?([0-9]{2}\\s?){4}|0[0-9]{9})", message = "Numéro de téléphone non valide.")
    private String phoneNumber;
    private String status;


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
