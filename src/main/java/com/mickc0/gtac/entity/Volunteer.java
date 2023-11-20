package com.mickc0.gtac.entity;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "volunteers")
public class Volunteer {

    @Id
    @Column(name = "volunteer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "uuid",updatable = false, nullable = false)
    private UUID uuid = UUID.randomUUID();
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "phone_number")
    private String phoneNumber;
    @OneToMany(mappedBy = "volunteer")
    private Set<Availability> availabilities;

    @OneToMany(mappedBy = "volunteer")
    private Set<Unavailability> unavailabilities;

    @ManyToMany(mappedBy = "assignedVolunteers")
    private Set<Mission> missions;

    @ManyToMany
    @JoinTable(
            name = "volunteer_mission_types",
            joinColumns = @JoinColumn(name = "volunteer_id"),
            inverseJoinColumns = @JoinColumn(name = "mission_type_id")
    )
    private Set<MissionType> missionTypes;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Availability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(Set<Availability> availabilities) {
        this.availabilities = availabilities;
    }

    public Set<Unavailability> getUnavailabilities() {
        return unavailabilities;
    }

    public void setUnavailabilities(Set<Unavailability> unavailabilities) {
        this.unavailabilities = unavailabilities;
    }

    public Set<Mission> getMissions() {
        return missions;
    }

    public void setMissions(Set<Mission> missions) {
        this.missions = missions;
    }

    public Set<MissionType> getMissionTypes() {
        return missionTypes;
    }

    public void setMissionTypes(Set<MissionType> preferredMissionTypes) {
        this.missionTypes = preferredMissionTypes;
    }
}
