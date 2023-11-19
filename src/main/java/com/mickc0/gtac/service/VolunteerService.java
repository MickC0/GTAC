package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.VolunteerStatusDTO;
import com.mickc0.gtac.entity.Volunteer;

import java.util.List;
import java.util.Optional;

public interface VolunteerService {
    Volunteer save(Volunteer volunteer);
    Optional<Volunteer> findById(Long id);
    List<Volunteer> findAll();
    void updateVolunteer(Volunteer volunteer);
    void deleteVolunteer(Long id);

    List<VolunteerStatusDTO> findAllVolunteersWithStatus();
}
