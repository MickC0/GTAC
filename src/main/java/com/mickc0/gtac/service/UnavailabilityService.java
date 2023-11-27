package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.UnavailabilityDTO;
import com.mickc0.gtac.dto.UnavailabilityWithoutIdDTO;
import com.mickc0.gtac.entity.Unavailability;

import java.util.List;
import java.util.UUID;

public interface UnavailabilityService {
    void save(Unavailability unavailability);
    List<UnavailabilityWithoutIdDTO> findAll();
    void delete(Long id);
    UnavailabilityWithoutIdDTO findById(Long id);
    void removeExpiredUnavailabilities();

    UnavailabilityDTO findUnavailabilityDtoByUuid(UUID uuid);
}
