package com.mickc0.gtac.dto;

import com.mickc0.gtac.model.*;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class VolunteerDTO {

    private UUID uuid;
    private String lastName;
    private String firstName;
    private String email;
    private String phoneNumber;
    private Set<MissionVolunteerDTO> missionVolunteers = new HashSet<>();
    private Set<DisponibilityDTO> disponibilities = new HashSet<>();
    private Set<IndisponibilityDTO> indisponibilities = new HashSet<>();
    private Set<MissionTypeDTO> missionTypes = new HashSet<>();

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

    public Set<MissionVolunteerDTO> getMissionVolunteers() {
        return missionVolunteers;
    }

    public void setMissionVolunteers(Set<MissionVolunteerDTO> missionVolunteers) {
        this.missionVolunteers = missionVolunteers;
    }

    public Set<DisponibilityDTO> getDisponibilities() {
        return disponibilities;
    }

    public void setDisponibilities(Set<DisponibilityDTO> disponibilities) {
        this.disponibilities = disponibilities;
    }

    public Set<IndisponibilityDTO> getIndisponibilities() {
        return indisponibilities;
    }

    public void setIndisponibilities(Set<IndisponibilityDTO> indisponibilities) {
        this.indisponibilities = indisponibilities;
    }

    public Set<MissionTypeDTO> getMissionTypes() {
        return missionTypes;
    }

    public void setMissionTypes(Set<MissionTypeDTO> missionTypes) {
        this.missionTypes = missionTypes;
    }
}

