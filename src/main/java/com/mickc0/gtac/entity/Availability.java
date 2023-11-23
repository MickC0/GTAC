package com.mickc0.gtac.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "availabilities")
public class Availability {
    @Id
    @Column(name = "availability_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", updatable = false, nullable = false)
    private UUID uuid = UUID.randomUUID();
    @Column(name = "starting_time")
    private LocalTime startTime;
    @Column(name = "ending_time")
    private LocalTime endTime;
    @Column(name="day")
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;
    @ManyToOne
    @JoinColumn(name = "volunteer_id")
    private Volunteer volunteer;
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

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    @Override
    public String toString() {
        return "Availability{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", dayOfWeek=" + dayOfWeek +
                ", volunteer=" + volunteer +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Availability that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getUuid(), that.getUuid()) && Objects.equals(getStartTime(), that.getStartTime()) && Objects.equals(getEndTime(), that.getEndTime()) && getDayOfWeek() == that.getDayOfWeek() && Objects.equals(getVolunteer(), that.getVolunteer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUuid(), getStartTime(), getEndTime(), getDayOfWeek(), getVolunteer());
    }
}
