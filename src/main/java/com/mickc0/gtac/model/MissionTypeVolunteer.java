package com.mickc0.gtac.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "mission_types_volunteers")
public class MissionTypeVolunteer {

    @EmbeddedId
    private MissionTypeVolunteerKey id;

    @ManyToOne
    @MapsId("volunterId")
    @JoinColumn(name = "volunteer_id")
    private Volunteer volunteer;

    @ManyToOne
    @MapsId("missionTypeId")
    @JoinColumn(name = "mission_type_id")
    private MissionType missionType;

    public MissionTypeVolunteer() {
    }

    public MissionTypeVolunteer(MissionTypeVolunteerKey id, Volunteer volunteer, MissionType missionType) {
        this.id = id;
        this.volunteer = volunteer;
        this.missionType = missionType;
    }

    public MissionTypeVolunteerKey getId() {
        return id;
    }

    public void setId(MissionTypeVolunteerKey id) {
        this.id = id;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public MissionType getMissionType() {
        return missionType;
    }

    public void setMissionType(MissionType missionType) {
        this.missionType = missionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MissionTypeVolunteer that = (MissionTypeVolunteer) o;
        return Objects.equals(id, that.id) && Objects.equals(volunteer, that.volunteer) && Objects.equals(missionType, that.missionType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, volunteer, missionType);
    }

    @Override
    public String toString() {
        return "MissionTypeVolunteer{" +
                "id=" + id +
                ", volunteer=" + volunteer +
                ", missionType=" + missionType +
                '}';
    }
}
