package com.mickc0.gtac.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.*;


@Entity
@Table(name = "mission_type")
public class MissionType {

    @Id
    @Column(name = "mission_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid",updatable = false, nullable = false)
    private UUID uuid = UUID.randomUUID();

    @Column(name = "name")
    @Size(min = 3, max = 20)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "is_active")
    private boolean isActive = true;

    @OneToMany(mappedBy = "missionType")
    private List<Mission> missions;

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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Mission> getMissions() {
        return missions;
    }

    public void setMissions(List<Mission> missions) {
        this.missions = missions;
    }
}
