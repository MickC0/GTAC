package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.AvailabilityDTO;
import com.mickc0.gtac.entity.Availability;
import com.mickc0.gtac.entity.Volunteer;

import java.util.List;
import java.util.UUID;

public interface AvailabilityService {
    Availability createAvailability(Availability availability);
    void save(Availability availability);
    List<Availability> getAllAvailabilities();
    void deleteById(Long id);
    void delete(Availability availability);

    void deleteByUuid(UUID uuid);

    void deleteAllByVolunteer(Volunteer volunteer);

    void deleteAllByVolunteerUuid(UUID uuid);

    AvailabilityDTO findAvailabilityDtoByUuid(UUID uuid);
    Availability findByUuid(UUID uuid);
}
