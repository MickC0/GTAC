package com.mickc0.gtac.service;

import com.mickc0.gtac.entity.Unavailability;

import java.util.List;

public interface UnavailabilityService {
    Unavailability createUnavailability(Unavailability unavailability);
    List<Unavailability> getAllUnavailabilities();
    void deleteUnavailability(Long id);
}
