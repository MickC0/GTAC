package com.mickc0.gtac.dto;

import java.util.ArrayList;
import java.util.List;

public class VolunteerNewDTO {

    private String lastName;
    private String firstName;
    private String email;
    private String phoneNumber;
    private List<AvailabilityDTO> availabilities = new ArrayList<>();
    private List<Long> preferredMissionTypeIds = new ArrayList<>();


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

    public List<Long> getPreferredMissionTypeIds() {
        return preferredMissionTypeIds;
    }

    public void setPreferredMissionTypeIds(List<Long> preferredMissionTypeIds) {
        this.preferredMissionTypeIds = preferredMissionTypeIds;
    }

}
