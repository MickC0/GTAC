package com.mickc0.gtac.service;

import com.mickc0.gtac.entity.Availability;
import com.mickc0.gtac.entity.Volunteer;

import java.util.List;
import java.util.UUID;

public interface AvailabilityService {
    Availability createAvailability(Availability availability);
    void save(Availability availability);
    List<Availability> getAllAvailabilities();
    void deleteAvailability(Long id);

    void deleteByUuid(UUID uuid);

    void deleteAllByVolunteer(Volunteer volunteer);

    void deleteAllByVolunteerId(Long id);
}
