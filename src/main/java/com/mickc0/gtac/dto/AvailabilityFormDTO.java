package com.mickc0.gtac.dto;

import com.mickc0.gtac.entity.Availability;

import java.util.ArrayList;
import java.util.List;

public class AvailabilityFormDTO {
    private List<Availability> availabilities = new ArrayList<>();

    public AvailabilityFormDTO() {
    }

    public AvailabilityFormDTO(List<Availability> availabilities) {
        this.availabilities = availabilities;
    }

    public List<Availability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<Availability> availabilities) {
        this.availabilities = availabilities;
    }
}
