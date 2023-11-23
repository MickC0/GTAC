package com.mickc0.gtac.dto;

import com.mickc0.gtac.entity.Availability;
import com.mickc0.gtac.entity.Mission;
import com.mickc0.gtac.entity.MissionType;
import com.mickc0.gtac.entity.Unavailability;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class VolunteerEditDTO {

    private Long id;
    private UUID uuid;
    private String lastName;
    private String firstName;
    private String email;
    private String phoneNumber;
    private List<AvailabilityDTO> availabilities;
    private List<Long> missionTypes;

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

    public List<AvailabilityDTO> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<AvailabilityDTO> availabilities) {
        this.availabilities = availabilities;
    }


    public List<Long> getMissionTypes() {
        return missionTypes;
    }

    public void setMissionTypes(List<Long> missionTypes) {
        this.missionTypes = missionTypes;
    }
}
