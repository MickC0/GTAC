package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.*;
import com.mickc0.gtac.entity.RoleName;
import com.mickc0.gtac.entity.Volunteer;
import org.springframework.security.core.Authentication;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VolunteerService {
    void saveOrUpdateVolunteerDetails(VolunteerDetailsDTO volunteerDetailsDTO, boolean resetPassword, Authentication authentication);
    Volunteer saveAndReturn(Volunteer volunteer);
    void saveOrUpdate(VolunteerDTO volunteerDTO);
    Optional<Volunteer> findById(Long id);
    Optional<Volunteer> findByEmail(String email);
    VolunteerDTO findVolunteerDTOByUuid(UUID uuid);
    List<Volunteer> findAll();
    void deleteVolunteer(UUID uuid);
    List<VolunteerStatusDTO> findAllVolunteersWithStatus();

    List<VolunteerDTO> getAvailableUsersForMission(LocalDateTime start, LocalDateTime end, UUID missionTypeUuid, UUID currentMissionUuid);


    VolunteerGuestProfilDTO findVolunteerProfilDTOByUuid(UUID uuid);

    List<Volunteer> findVolunteersByUuids(List<UUID> volunteerUuids);

    Optional<Volunteer> findVolunteerByUuid(UUID volunteerUuid);

    List<VolunteerDetailsDTO> findAllVolunteerByRole(RoleName roleName);

    VolunteerDetailsDTO findVolunteerDetailsByUuid(UUID uuid);

    VolunteerRoleProfilDTO findVolunteerRoleProfilByEmail(String email);
    VolunteerRoleProfilDTO findVolunteerRoleProfilByUuid(UUID uuid);

    boolean changePassword(String email, String oldPassword, String newPassword);
}
