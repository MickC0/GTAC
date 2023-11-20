package com.mickc0.gtac.service;

import com.mickc0.gtac.entity.Availability;

import java.util.List;

public interface AvailabilityService {
    Availability createAvailability(Availability availability);
    void save(Availability availability);
    List<Availability> getAllAvailabilities();
    void deleteAvailability(Long id);
}
