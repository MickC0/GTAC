package com.mickc0.gtac.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "volunteers")
public class Volunteer {

    @Id
    private Long id;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @OneToMany(mappedBy = "volunteer")
    private Set<MissionVolunteer> missionVolunteers;

    @OneToMany(mappedBy = "volunteer")
    private Set<MissionTypeVolunteer> missionTypeVolunteers;


    public Volunteer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<MissionTypeVolunteer> getMissionTypeVolunteers() {
        return missionTypeVolunteers;
    }

    public void setMissionTypeVolunteers(Set<MissionTypeVolunteer> missionTypeVolunteers) {
        this.missionTypeVolunteers = missionTypeVolunteers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volunteer volunteer = (Volunteer) o;
        return Objects.equals(id, volunteer.id) && Objects.equals(lastName, volunteer.lastName) && Objects.equals(firstName, volunteer.firstName) && Objects.equals(email, volunteer.email) && Objects.equals(phoneNumber, volunteer.phoneNumber) && Objects.equals(missionVolunteers, volunteer.missionVolunteers) && Objects.equals(missionTypeVolunteers, volunteer.missionTypeVolunteers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastName, firstName, email, phoneNumber, missionVolunteers, missionTypeVolunteers);
    }

    @Override
    public String toString() {
        return "Volunteer{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", missionVolunteers=" + missionVolunteers +
                ", missionTypeVolunteers=" + missionTypeVolunteers +
                '}';
    }
}
