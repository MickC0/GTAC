package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.VolunteerDTO;
import com.mickc0.gtac.dto.VolunteerNewDTO;
import com.mickc0.gtac.dto.VolunteerStatusDTO;
import com.mickc0.gtac.entity.Volunteer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VolunteerService {
    Volunteer saveAndReturn(Volunteer volunteer);
    void save(VolunteerNewDTO volunteer);
    Optional<Volunteer> findById(Long id);
    VolunteerDTO findVolunteerEditDTOById(Long id);
    List<Volunteer> findAll();
    void updateVolunteer(VolunteerDTO volunteerDTO);
    void deleteVolunteer(Long id);
    List<VolunteerStatusDTO> findAllVolunteersWithStatus();

    List<Volunteer> getAvailableUsersForMission(LocalDateTime start, LocalDateTime end, Long missionTypeId);


}
