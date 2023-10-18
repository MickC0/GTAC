package com.mickc0.gtac.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "missions")
public class Mission {

    @Id
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

    @Column(name = "mission_type")
    private String missionType;

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

    public Mission() {
    }

    public Mission(Long id) {
        this.id = id;
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

    public String getMissionType() {
        return missionType;
    }

    public void setMissionType(String missionType) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mission mission = (Mission) o;
        return requiredVolunteerNumber == mission.requiredVolunteerNumber && Objects.equals(id, mission.id) && Objects.equals(uuid, mission.uuid) && Objects.equals(name, mission.name) && Objects.equals(description, mission.description) && Objects.equals(comment, mission.comment) && Objects.equals(missionType, mission.missionType) && status == mission.status && Objects.equals(startingDate, mission.startingDate) && Objects.equals(endingDate, mission.endingDate) && Objects.equals(missionVolunteers, mission.missionVolunteers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, name, description, comment, missionType, status, requiredVolunteerNumber, startingDate, endingDate, missionVolunteers);
    }

    @Override
    public String toString() {
        return "Mission{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", comment='" + comment + '\'' +
                ", missionType='" + missionType + '\'' +
                ", status=" + status +
                ", requiredVolunteerNumber=" + requiredVolunteerNumber +
                ", startingDate=" + startingDate +
                ", endingDate=" + endingDate +
                ", missionVolunteers=" + missionVolunteers +
                '}';
    }
}
