package com.mickc0.gtac.dto;

import java.time.LocalDate;
import java.util.UUID;

public class UnavailabilityDTO {
    private UUID uuid;
    private LocalDate startDate;
    private LocalDate endDate;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "UnavailabilityDTO{" +
                "uuid=" + uuid +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
