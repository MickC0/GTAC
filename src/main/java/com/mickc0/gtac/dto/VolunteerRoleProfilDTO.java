package com.mickc0.gtac.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public class VolunteerRoleProfilDTO {

    private UUID uuid;
    @Size(min = 3, max = 20)
    private String lastName;
    @Size(min = 3, max = 20)
    private String firstName;
    @Email(message = "Adresse email non valide.")
    @NotEmpty(message = "Renseigner l'email")
    private String email;
    @Pattern(regexp = "(\\+33\\s?([0-9]{2}\\s?){4}|0[0-9]{9})", message = "Numéro de téléphone non valide.")
    private String phoneNumber;
    private List<AvailabilityDTO> availabilities;
    private List<MissionTypeDTO> missionTypes;
    private List<UnavailabilityDTO> unavailabilities;
    private List<String> roles;
    private boolean mustChangePassword;
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

    public List<AvailabilityDTO> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<AvailabilityDTO> availabilities) {
        this.availabilities = availabilities;
    }

    public List<MissionTypeDTO> getMissionTypes() {
        return missionTypes;
    }

    public void setMissionTypes(List<MissionTypeDTO> missionTypes) {
        this.missionTypes = missionTypes;
    }

    public List<UnavailabilityDTO> getUnavailabilities() {
        return unavailabilities;
    }

    public void setUnavailabilities(List<UnavailabilityDTO> unavailabilities) {
        this.unavailabilities = unavailabilities;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public boolean isMustChangePassword() {
        return mustChangePassword;
    }

    public void setMustChangePassword(boolean mustChangePassword) {
        this.mustChangePassword = mustChangePassword;
    }
}
