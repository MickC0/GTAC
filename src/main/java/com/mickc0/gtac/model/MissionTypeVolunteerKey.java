package com.mickc0.gtac.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MissionTypeVolunteerKey implements Serializable {

    @Column(name = "mission_type_id")
    private Long missionTypeId;

    @Column(name = "volunteer_id")
    private Long volunteerId;

    public MissionTypeVolunteerKey() {
    }

    public MissionTypeVolunteerKey(Long missionTypeId, Long volunteerId) {
        this.missionTypeId = missionTypeId;
        this.volunteerId = volunteerId;
    }

    public Long getMissionTypeId() {
        return missionTypeId;
    }

    public void setMissionTypeId(Long missionTypeId) {
        this.missionTypeId = missionTypeId;
    }

    public Long getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(Long volunteerId) {
        this.volunteerId = volunteerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MissionTypeVolunteerKey that = (MissionTypeVolunteerKey) o;
        return Objects.equals(missionTypeId, that.missionTypeId) && Objects.equals(volunteerId, that.volunteerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(missionTypeId, volunteerId);
    }
}
