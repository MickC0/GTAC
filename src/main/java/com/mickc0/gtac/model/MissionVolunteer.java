package com.mickc0.gtac.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "missions_volunteers")
public class MissionVolunteer {


    @EmbeddedId
    private MissionVolunteerKey id;

    @ManyToOne
    @MapsId("missionId")
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @ManyToOne
    @MapsId("volunterId")
    @JoinColumn(name = "volunteer_id")
    private Volunteer volunteer;

    @Column(name = "is_available")
    private boolean isAvailable;

    @Column(name = "contact_date")
    private LocalDate contactDate;

    @Column(name = "is_affected")
    private boolean isAffected;

    @Column(name = "is_chief")
    private boolean isChief;

    @Column(name = "has_attended")
    private boolean hasAttended;

    public MissionVolunteer() {
    }

    public MissionVolunteerKey getId() {
        return id;
    }

    public void setId(MissionVolunteerKey id) {
        this.id = id;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public LocalDate getContactDate() {
        return contactDate;
    }

    public void setContactDate(LocalDate contactDate) {
        this.contactDate = contactDate;
    }

    public boolean isAffected() {
        return isAffected;
    }

    public void setAffected(boolean affected) {
        isAffected = affected;
    }

    public boolean isChief() {
        return isChief;
    }

    public void setChief(boolean chief) {
        isChief = chief;
    }

    public boolean isHasAttended() {
        return hasAttended;
    }

    public void setHasAttended(boolean hasAttended) {
        this.hasAttended = hasAttended;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MissionVolunteer that = (MissionVolunteer) o;
        return isAvailable == that.isAvailable && isAffected == that.isAffected && isChief == that.isChief && hasAttended == that.hasAttended && Objects.equals(id, that.id) && Objects.equals(mission, that.mission) && Objects.equals(volunteer, that.volunteer) && Objects.equals(contactDate, that.contactDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mission, volunteer, isAvailable, contactDate, isAffected, isChief, hasAttended);
    }

    @Override
    public String toString() {
        return "MissionVolunteer{" +
                "id=" + id +
                ", mission=" + mission +
                ", volunteer=" + volunteer +
                ", isAvailable=" + isAvailable +
                ", contactDate=" + contactDate +
                ", isAffected=" + isAffected +
                ", isChief=" + isChief +
                ", hasAttended=" + hasAttended +
                '}';
    }
}
