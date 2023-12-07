package com.mickc0.gtac.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "mission_assignment")
public class MissionAssignment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_assignment_id")
    private Long id;
    @Column(name = "uuid",updatable = false, nullable = false)
    private UUID uuid = UUID.randomUUID();
    @ManyToOne
    @JoinColumn(name = "volunteer_id")
    private Volunteer volunteer;
    @ManyToOne
    @JoinColumn(name = "mission_id")
    private Mission mission;
    @Column(name = "is_chief")
    private boolean isChief;
    @Column(name = "has_participated")
    private boolean hasParticipated = false;

    private LocalDateTime assignedFrom;
    private LocalDateTime assignedUntil;

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

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public boolean isChief() {
        return isChief;
    }

    public void setChief(boolean chief) {
        isChief = chief;
    }

    public LocalDateTime getAssignedFrom() {
        return assignedFrom;
    }

    public void setAssignedFrom(LocalDateTime assignedFrom) {
        this.assignedFrom = assignedFrom;
    }

    public LocalDateTime getAssignedUntil() {
        return assignedUntil;
    }

    public void setAssignedUntil(LocalDateTime assignedUntil) {
        this.assignedUntil = assignedUntil;
    }
    public boolean hasParticipated() {
        return hasParticipated;
    }

    public void setHasParticipated(boolean hasParticipated) {
        this.hasParticipated = hasParticipated;
    }

    @Override
    public String toString() {
        return "MissionAssignment{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", volunteer=" + volunteer +
                ", mission=" + mission +
                ", isChief=" + isChief +
                ", hasParticipated=" + hasParticipated +
                ", assignedFrom=" + assignedFrom +
                ", assignedUntil=" + assignedUntil +
                '}';
    }
}
