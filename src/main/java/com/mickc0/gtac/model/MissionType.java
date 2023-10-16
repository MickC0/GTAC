package com.mickc0.gtac.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "mission_types")
public class MissionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "missionType")
    private Set<MissionTypeVolunteer> missionTypeVolunteers;

    public MissionType() {
    }

    public MissionType(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        MissionType that = (MissionType) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(missionTypeVolunteers, that.missionTypeVolunteers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, missionTypeVolunteers);
    }

    @Override
    public String toString() {
        return "MissionType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", missionTypeVolunteers=" + missionTypeVolunteers +
                '}';
    }
}
