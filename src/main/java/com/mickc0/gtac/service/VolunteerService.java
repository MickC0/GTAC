package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.VolunteerDTO;

import java.util.List;
import java.util.UUID;

public interface VolunteerService {
    List<VolunteerDTO> findAll();

    void saveVolunteer(VolunteerDTO volunteerDTO);

    VolunteerDTO findVolunteerByUUID(UUID uuid);

    void updateVolunteer(VolunteerDTO volunteerDTO);

    void deleteVolunteer(UUID uuid);

    List<VolunteerDTO> searchVolunteers(String query);
}
