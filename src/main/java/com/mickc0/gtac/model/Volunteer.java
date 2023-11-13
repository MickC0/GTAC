package com.mickc0.gtac.model;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "volunteers")
public class Volunteer {

    @Id
    @Column(name = "volunteer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private UUID uuid;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @OneToMany(mappedBy = "volunteer")
    private Set<MissionVolunteer> missionVolunteers = new HashSet<>();
    @OneToMany(mappedBy = "volunteer")
    private Set<Disponibility> disponibilities = new HashSet<>();
    @OneToMany(mappedBy = "volunteer")
    private Set<Indisponibility> indisponibilities = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "volunteers_mission_types", joinColumns = @JoinColumn(name = "volunteer_id"), inverseJoinColumns = @JoinColumn(name = "mission_type_id"))
    private Set<MissionType> missionTypes = new HashSet<>();

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

    public Set<MissionVolunteer> getMissionVolunteers() {
        return missionVolunteers;
    }

    public void setMissionVolunteers(Set<MissionVolunteer> missionVolunteers) {
        this.missionVolunteers = missionVolunteers;
    }

    public Set<Disponibility> getDisponibilities() {
        return disponibilities;
    }

    public void setDisponibilities(Set<Disponibility> disponibilities) {
        this.disponibilities = disponibilities;
    }

    public Set<Indisponibility> getIndisponibilities() {
        return indisponibilities;
    }

    public void setIndisponibilities(Set<Indisponibility> indisponibilities) {
        this.indisponibilities = indisponibilities;
    }

    public Set<MissionType> getMissionTypes() {
        return missionTypes;
    }

    public void setMissionTypes(Set<MissionType> missionTypes) {
        this.missionTypes = missionTypes;
    }
}
