package com.mickc0.gtac.model;

import jakarta.persistence.*;

import java.util.HashSet;
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
    private Set<MissionVolunteer> missionVolunteers = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "volunteers_mission_types", joinColumns = @JoinColumn(name = "volunteer_id"), inverseJoinColumns = @JoinColumn(name = "mission_type_id"))
    private Set<MissionType> missionTypes = new HashSet<>();


    public Volunteer() {
    }




}
