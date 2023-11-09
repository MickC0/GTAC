package com.mickc0.gtac.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "missions")
public class Mission {

    @Id
    @Column(name = "mission_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "mission_type_id", nullable = false)
    private MissionType missionType;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private MissionStatus status;

    @Column(name = "required_volunteer_number")
    private int requiredVolunteerNumber;

    @Column(name = "starting_date")
    private LocalDateTime startingDate;
    @Column(name = "ending_date")
    private LocalDateTime endingDate;

    @OneToMany(mappedBy = "mission")
    private Set<MissionVolunteer> missionVolunteers = new HashSet<>();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public MissionType getMissionType() {
        return missionType;
    }

    public void setMissionType(MissionType missionType) {
        this.missionType = missionType;
    }

    public MissionStatus getStatus() {
        return status;
    }

    public void setStatus(MissionStatus status) {
        this.status = status;
    }

    public int getRequiredVolunteerNumber() {
        return requiredVolunteerNumber;
    }

    public void setRequiredVolunteerNumber(int requiredVolunteerNumber) {
        this.requiredVolunteerNumber = requiredVolunteerNumber;
    }

    public LocalDateTime getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDateTime startingDate) {
        this.startingDate = startingDate;
    }

    public LocalDateTime getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(LocalDateTime endingDate) {
        this.endingDate = endingDate;
    }

    public Set<MissionVolunteer> getMissionVolunteers() {
        return missionVolunteers;
    }

    public void setMissionVolunteers(Set<MissionVolunteer> missionVolunteers) {
        this.missionVolunteers = missionVolunteers;
    }
}
