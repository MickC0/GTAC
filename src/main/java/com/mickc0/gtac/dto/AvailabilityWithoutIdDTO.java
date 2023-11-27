package com.mickc0.gtac.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

public class AvailabilityWithoutIdDTO {
    private UUID uuid;
    private LocalTime startTime;
    private LocalTime endTime;
    private DayOfWeek dayOfWeek;

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
}
