package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.UnavailabilityDTO;
import com.mickc0.gtac.entity.Unavailability;
import com.mickc0.gtac.entity.Volunteer;

import java.util.List;
import java.util.UUID;

public interface UnavailabilityService {
    void save(Unavailability unavailability);
    List<UnavailabilityDTO> findAll();
    void delete(Long id);
    UnavailabilityDTO findById(Long id);
    void removeExpiredUnavailabilities();
    UnavailabilityDTO findUnavailabilityDtoByUuid(UUID uuid);
    Unavailability findByUuid(UUID uuid);

    void delete(Unavailability unavailability);

    void deleteAllByVolunteer(Volunteer volunteer);

    void deleteAllByVolunteerUuid(UUID uuid);
}
