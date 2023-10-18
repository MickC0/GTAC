package com.mickc0.gtac.model;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "volunteers")
public class Volunteer {

    @Id
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

    @ManyToMany
    @JoinTable(name = "volunteers_mission_types", joinColumns = @JoinColumn(name = "volunteer_id"), inverseJoinColumns = @JoinColumn(name = "mission_type_id"))
    private Set<MissionType> missionTypes = new HashSet<>();


    public Volunteer() {
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

    public Set<MissionVolunteer> getMissionVolunteers() {
        return missionVolunteers;
    }

    public void setMissionVolunteers(Set<MissionVolunteer> missionVolunteers) {
        this.missionVolunteers = missionVolunteers;
    }

    public Set<MissionType> getMissionTypes() {
        return missionTypes;
    }

    public void setMissionTypes(Set<MissionType> missionTypes) {
        this.missionTypes = missionTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volunteer volunteer = (Volunteer) o;
        return Objects.equals(id, volunteer.id) && Objects.equals(uuid, volunteer.uuid) && Objects.equals(lastName, volunteer.lastName) && Objects.equals(firstName, volunteer.firstName) && Objects.equals(email, volunteer.email) && Objects.equals(phoneNumber, volunteer.phoneNumber) && Objects.equals(missionVolunteers, volunteer.missionVolunteers) && Objects.equals(missionTypes, volunteer.missionTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, lastName, firstName, email, phoneNumber, missionVolunteers, missionTypes);
    }

    @Override
    public String toString() {
        return "Volunteer{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", missionVolunteers=" + missionVolunteers +
                ", missionTypes=" + missionTypes +
                '}';
    }
}
