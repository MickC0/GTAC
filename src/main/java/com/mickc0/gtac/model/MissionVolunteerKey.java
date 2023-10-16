package com.mickc0.gtac.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MissionVolunteerKey implements Serializable {

    @Column(name = "mission_id")
    private Long missionId;

    @Column(name = "volunteer_id")
    private Long volunteerId;

    public MissionVolunteerKey() {
    }

    public MissionVolunteerKey(Long missionId, Long volunteerId) {
        this.missionId = missionId;
        this.volunteerId = volunteerId;
    }

    public Long getMissionId() {
        return missionId;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
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
        MissionVolunteerKey that = (MissionVolunteerKey) o;
        return Objects.equals(missionId, that.missionId) && Objects.equals(volunteerId, that.volunteerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(missionId, volunteerId);
    }
}
