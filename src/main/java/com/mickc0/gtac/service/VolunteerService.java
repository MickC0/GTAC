package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.VolunteerDTO;
import com.mickc0.gtac.dto.VolunteerDetailsDTO;
import com.mickc0.gtac.dto.VolunteerProfilDTO;
import com.mickc0.gtac.dto.VolunteerStatusDTO;
import com.mickc0.gtac.entity.RoleName;
import com.mickc0.gtac.entity.Volunteer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VolunteerService {
    void saveDetails(VolunteerDetailsDTO volunteerDetailsDTO);
    Volunteer saveAndReturn(Volunteer volunteer);
    void saveOrUpdate(VolunteerDTO volunteerDTO);
    Optional<Volunteer> findById(Long id);
    VolunteerDTO findVolunteerDTOByUuid(UUID uuid);
    List<Volunteer> findAll();
    void deleteVolunteer(UUID uuid);
    List<VolunteerStatusDTO> findAllVolunteersWithStatus();

    List<VolunteerDTO> getAvailableUsersForMission(LocalDateTime start, LocalDateTime end, UUID missionTypeUuid, UUID currentMissionUuid);


    VolunteerProfilDTO findVolunteerProfilDTOByUuid(UUID uuid);

    List<Volunteer> findVolunteersByUuids(List<UUID> volunteerUuids);

    Optional<Volunteer> findVolunteerByUuid(UUID volunteerUuid);

    List<VolunteerDetailsDTO> findAllVolunteerByRole(RoleName roleName);
}
