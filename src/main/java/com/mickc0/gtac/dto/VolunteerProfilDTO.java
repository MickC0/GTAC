package com.mickc0.gtac.dto;

import java.util.List;
import java.util.UUID;

public class VolunteerProfilDTO {

    private UUID uuid;
    private String lastName;
    private String firstName;
    private String email;
    private String phoneNumber;
    private List<AvailabilityDTO> availabilities;
    private List<MissionTypeDTO> missionTypes;
    private List<UnavailabilityDTO> unavailabilities;

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
}
