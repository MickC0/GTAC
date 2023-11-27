package com.mickc0.gtac.entity;

import jakarta.persistence.*;

import java.util.*;


@Entity
@Table(name = "mission_types")
public class MissionType {

    @Id
    @Column(name = "mission_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid",updatable = false, nullable = false)
    private UUID uuid = UUID.randomUUID();

    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "is_active")
    private boolean isActive = true;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

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

    public List<Mission> getMissions() {
        return missions;
    }

    public void setMissions(List<Mission> missions) {
        this.missions = missions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MissionType that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getUuid(), that.getUuid()) && Objects.equals(getName(), that.getName()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getMissions(), that.getMissions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUuid(), getName(), getDescription(), getMissions());
    }

    @Override
    public String toString() {
        return "MissionType{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", missions=" + missions +
                '}';
    }
}
