package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.UnavailabilityDTO;
import com.mickc0.gtac.entity.Unavailability;

import java.util.List;

public interface UnavailabilityService {
    void save(Unavailability unavailability);
    List<UnavailabilityDTO> findAll();
    void delete(Long id);
    UnavailabilityDTO findById(Long id);
    void removeExpiredUnavailabilities();
}
