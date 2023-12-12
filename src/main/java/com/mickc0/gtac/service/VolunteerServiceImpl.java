package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.*;
import com.mickc0.gtac.entity.*;
import com.mickc0.gtac.mapper.VolunteerMapper;
import com.mickc0.gtac.repository.VolunteerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VolunteerServiceImpl implements VolunteerService{

    private final VolunteerRepository volunteerRepository;
    private final VolunteerMapper volunteerMapper;
    private final MissionTypeService missionTypeService;
    private final AvailabilityService availabilityService;
    private final UnavailabilityService unavailabilityService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public VolunteerServiceImpl(VolunteerRepository volunteerRepository, VolunteerMapper volunteerMapper,
                                MissionTypeService missionTypeService,
                                AvailabilityService availabilityService,
                                UnavailabilityService unavailabilityService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.volunteerRepository = volunteerRepository;
        this.volunteerMapper = volunteerMapper;
        this.missionTypeService = missionTypeService;
        this.availabilityService = availabilityService;
        this.unavailabilityService = unavailabilityService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveOrUpdateVolunteerDetails(VolunteerDetailsDTO volunteerDetailsDTO, boolean resetPassword, Authentication authentication) {
        Volunteer volunteer;
        boolean isNewVolunteer = volunteerDetailsDTO.getUuid() == null;

        if (!isNewVolunteer) {
            volunteer = volunteerRepository.findByUuid(volunteerDetailsDTO.getUuid())
                    .orElseThrow(() -> new EntityNotFoundException("Le bénévole avec l'Id: " + volunteerDetailsDTO.getUuid() + " n'existe pas"));
        } else {
            volunteer = new Volunteer();
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String loggedInUserEmail = userDetails.getUsername();
        boolean isSelfEdit = volunteer.getEmail() != null && volunteer.getEmail().equals(loggedInUserEmail);

        if (isSelfEdit) {
            Set<Role> existingRoles = new HashSet<>(volunteer.getRoles());
            List<Role> newRoles = getRoles(volunteerDetailsDTO, isNewVolunteer);
            existingRoles.addAll(newRoles);
            volunteer.setRoles(new ArrayList<>(existingRoles));
        } else {
            List<Role> roles = getRoles(volunteerDetailsDTO, isNewVolunteer);
            volunteer.setRoles(roles);
        }

        volunteer.setLastName(volunteerDetailsDTO.getLastName());
        volunteer.setFirstName(volunteerDetailsDTO.getFirstName());
        volunteer.setEmail(volunteerDetailsDTO.getEmail());
        volunteer.setPhoneNumber(volunteerDetailsDTO.getPhoneNumber());

        if (resetPassword || isNewVolunteer) {
            String defaultPassword = getDefaultPasswordForRoles(volunteer.getRoles());
            volunteer.setPassword(passwordEncoder.encode(defaultPassword));
        }

        volunteerRepository.save(volunteer);
    }




    private String getDefaultPasswordForRoles(List<Role> roles) {
        boolean isAdmin = roles.stream().anyMatch(role -> role.getName().equals(RoleName.ROLE_ADMIN.name()));
        boolean isVolunteer = roles.stream().anyMatch(role -> role.getName().equals(RoleName.ROLE_VOLUNTEER.name()));
        boolean isMission = roles.stream().anyMatch(role -> role.getName().equals(RoleName.ROLE_MISSION.name()));
        if (isAdmin) {
            return "admin";
        } else if (isVolunteer && isMission) {
            return "volunteermission";
        } else if (isVolunteer) {
            return "volunteer";
        } else if (isMission) {
            return "mission";
        }
        return "defaultPassword";
    }

    private List<Role> getRoles(VolunteerDetailsDTO volunteerDetailsDTO, boolean isNewVolunteer) {
        List<Role> roles = new ArrayList<>();
        if (isNewVolunteer) {
            roles = new ArrayList<>();
            Role guestRole = roleService.findByName(RoleName.ROLE_GUEST.name());
            roles.add(guestRole);
        } else {
            if (volunteerDetailsDTO.getRoles() != null && !volunteerDetailsDTO.getRoles().isEmpty()) {
                roles = volunteerDetailsDTO.getRoles().stream()
                        .map(roleService::findByName).collect(Collectors.toList());
            }
            boolean hasGuestRole = roles.stream()
                    .anyMatch(role -> role.getName().equals(RoleName.ROLE_GUEST.name()));
            if (!hasGuestRole) {
                Role guestRole = roleService.findByName(RoleName.ROLE_GUEST.name());
                roles.add(guestRole);
            }
        }
        return roles;
    }


    @Override
    @Transactional
    public Volunteer saveAndReturn(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    @Override
    @Transactional
    public void saveOrUpdate(VolunteerDTO volunteerDTO) {
        Volunteer volunteer;
        boolean isNewVolunteer = volunteerDTO.getUuid() == null;
        if (!isNewVolunteer) {
            volunteer = volunteerRepository.findByUuid(volunteerDTO.getUuid())
                    .orElseThrow(() -> new EntityNotFoundException("Le bénévole avec l'Id: " + volunteerDTO.getUuid() + " n'existe pas"));
        } else {
            volunteer = new Volunteer();
        }
        volunteer.setLastName(volunteerDTO.getLastName());
        volunteer.setFirstName(volunteerDTO.getFirstName());
        volunteer.setEmail(volunteerDTO.getEmail());
        volunteer.setPhoneNumber(volunteerDTO.getPhoneNumber());

        List<Role> roles;
        if (volunteerDTO.getUuid() == null || volunteer.getRoles().isEmpty()) {
            roles = new ArrayList<>();
            Role guestRole = roleService.findByName(RoleName.ROLE_GUEST.name());
            roles.add(guestRole);
        } else {
            roles = new ArrayList<>(volunteer.getRoles());
            boolean hasGuestRole = roles.stream()
                    .anyMatch(role -> role.getName().equals(RoleName.ROLE_GUEST.name()));
            if (!hasGuestRole) {
                Role guestRole = roleService.findByName(RoleName.ROLE_GUEST.name());
                roles.add(guestRole);
            }
        }
        volunteer.setRoles(roles);
        volunteer = saveAndReturn(volunteer);

        handleMissionTypes(volunteerDTO, volunteer);

        handleAvailabilities(volunteerDTO, volunteer);

        handleUnavailabilities(volunteerDTO, volunteer);

        volunteerRepository.save(volunteer);
    }

    @Transactional
    public void handleUnavailabilities(VolunteerDTO volunteerDTO, Volunteer volunteer) {
        Set<Unavailability> unavailabilities = new HashSet<>();

        if (volunteerDTO.getUnavailabilities() == null || volunteerDTO.getUnavailabilities().isEmpty()) {
            unavailabilityService.deleteAllByVolunteer(volunteer);
            volunteer.setUnavailabilities(Collections.emptySet());
            return;
        }
        for (UnavailabilityDTO unavailabilityDTO : volunteerDTO.getUnavailabilities() ) {
            Unavailability unavailability;
            if (unavailabilityDTO.getUuid() == null) {
                unavailability = new Unavailability();
            } else {
                try {
                    unavailability = unavailabilityService.findByUuid(unavailabilityDTO.getUuid());
                } catch (EntityNotFoundException e) {
                    unavailability = new Unavailability();
                }
            }
            unavailability.setStartDate(unavailabilityDTO.getStartDate());
            unavailability.setEndDate(unavailabilityDTO.getEndDate());
            unavailability.setVolunteer(volunteer);
            unavailabilityService.save(unavailability);
            unavailabilities.add(unavailability);
        }

        if (volunteer.getUnavailabilities() != null) {
            Set<UUID> updatedUnavailabilitiesUuids = unavailabilities.stream()
                    .map(Unavailability::getUuid)
                    .collect(Collectors.toSet());
            volunteer.getUnavailabilities().stream()
                    .filter(unavailability -> !updatedUnavailabilitiesUuids.contains(unavailability.getUuid()))
                    .forEach(unavailabilityService::delete);

        }
        volunteer.setUnavailabilities(unavailabilities);

    }

    @Transactional
    public void handleAvailabilities(VolunteerDTO volunteerDTO, Volunteer volunteer) {
        Set<Availability> availabilities = new HashSet<>();

        if (volunteerDTO.getAvailabilities() == null || volunteerDTO.getAvailabilities().isEmpty()) {
            availabilityService.deleteAllByVolunteer(volunteer);
            volunteer.setAvailabilities(Collections.emptySet());
            return;
        }
        for (AvailabilityDTO availabilityDTO : volunteerDTO.getAvailabilities() ) {
            Availability availability;
            if (availabilityDTO.getUuid() == null) {
                availability = new Availability();
            } else {
                try {
                    availability = availabilityService.findByUuid(availabilityDTO.getUuid());
                } catch (EntityNotFoundException e) {
                    availability = new Availability();
                }
            }
            availability.setDayOfWeek(availabilityDTO.getDayOfWeek());
            availability.setStartTime(availabilityDTO.getStartTime());
            availability.setEndTime(availabilityDTO.getEndTime());
            availability.setVolunteer(volunteer);
            availabilityService.save(availability);
            availabilities.add(availability);
        }

        if (volunteer.getAvailabilities() != null) {
            Set<UUID> updatedAvailabilitiesUuids = availabilities.stream()
                    .map(Availability::getUuid)
                    .collect(Collectors.toSet());
            volunteer.getAvailabilities().stream()
                    .filter(availability -> !updatedAvailabilitiesUuids.contains(availability.getUuid()))
                    .forEach(availabilityService::delete);

        }
        volunteer.setAvailabilities(availabilities);

    }

    @Transactional
    public void handleMissionTypes(VolunteerDTO volunteerDTO, Volunteer volunteer) {
        Set<MissionType> missionTypes = Optional.ofNullable(volunteerDTO.getMissionTypes())
                .orElse(Collections.emptyList())
                .stream()
                .map(UUID::fromString)
                .map(missionTypeService::findMissionTypeByUuid)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        volunteer.setMissionTypes(missionTypes);
    }

    @Override
    public Optional<Volunteer> findById(Long id) {
        return volunteerRepository.findById(id);
    }

    @Override
    public VolunteerDTO findVolunteerDTOByUuid(UUID uuid) {
        return volunteerMapper.mapToDto(volunteerRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Le bénévole avec l'Id: " + uuid + " n'existe pas")));
    }

    @Override
    public List<Volunteer> findAll() {
        return volunteerRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteVolunteer(UUID uuid) {
        availabilityService.deleteAllByVolunteerUuid(uuid);
        unavailabilityService.deleteAllByVolunteerUuid(uuid);
        volunteerRepository.deleteByUuid(uuid);
    }

    @Override
    public List<VolunteerStatusDTO> findAllVolunteersWithStatus() {
        List<Volunteer> volunteers = volunteerRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        return volunteers.stream()
                .map(volunteer -> volunteerMapper.mapToStatusDTO(volunteer, determineVolunteerStatus(volunteer, now)))
                .collect(Collectors.toList());
    }

    private String determineVolunteerStatus(Volunteer volunteer, LocalDateTime now) {
        if (isVolunteerAbsent(volunteer, now)) {
            return "Absent";
        } else {
            return "Disponible";
        }
    }

    private boolean isVolunteerAbsent(Volunteer volunteer, LocalDateTime now) {
        return unavailabilityService.isVolunteerUnavailableOnDate(volunteer, now.toLocalDate());
    }


    @Override
    public List<VolunteerDTO> getAvailableUsersForMission(LocalDateTime start, LocalDateTime end, UUID missionTypeUuid, UUID currentMissionUuid) {
        DayOfWeek dayOfWeek = start.getDayOfWeek();
        LocalTime startTime = start.toLocalTime();
        LocalTime endTime = end.toLocalTime();

        return volunteerRepository.findAvailableVolunteersForMission(start, end, dayOfWeek, startTime, endTime, missionTypeUuid, currentMissionUuid).stream()
                .map(volunteerMapper::mapToConfirmDto)
                .collect(Collectors.toList());
    }

    @Override
    public VolunteerGuestProfilDTO findVolunteerProfilDTOByUuid(UUID uuid) {
        return volunteerMapper.mapToProfilDto(volunteerRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Le bénévole avec l'Id: " + uuid + " n'existe pas")));
    }

    @Override
    public List<Volunteer> findVolunteersByUuids(List<UUID> volunteerUuids) {
        return volunteerRepository.findAllByUuidIn(volunteerUuids);

    }

    @Override
    public Optional<Volunteer> findVolunteerByUuid(UUID uuid) {
        return volunteerRepository.findByUuid(uuid);
    }

    @Override
    public List<VolunteerDetailsDTO> findAllVolunteerByRole(RoleName roleName) {
        List<Volunteer> volunteers = volunteerRepository.findByRole(roleName.name());
        return volunteers.stream()
                .map(volunteerMapper::mapToDetailsDto)
                .collect(Collectors.toList());
    }

    @Override
    public VolunteerDetailsDTO findVolunteerDetailsByUuid(UUID uuid) {
        return volunteerMapper.mapToDetailsDto(volunteerRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Le bénévole avec l'Id: " + uuid + " n'existe pas")));
    }

    @Override
    public VolunteerRoleProfilDTO findVolunteerRoleProfilByEmail(String email) {
        Volunteer volunteer = volunteerRepository.findByEmail(email);
        if (volunteer == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé : " + email);
        } else {
            return volunteerMapper.mapToFullDetailsDto(volunteer);
        }
    }

    @Override
    public VolunteerRoleProfilDTO findVolunteerRoleProfilByUuid(UUID uuid) {
        return volunteerMapper.mapToFullDetailsDto(volunteerRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Le bénévole avec l'Id: " + uuid + " n'existe pas")));
    }

    @Override
    @Transactional
    public boolean changePassword(String email, String oldPassword, String newPassword) {
        Volunteer volunteer = volunteerRepository.findByEmail(email);
        if (volunteer == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé : " + email);
        } else {
            if (!passwordEncoder.matches(oldPassword, volunteer.getPassword())) {
                return false;
            }
            volunteer.setPassword(passwordEncoder.encode(newPassword));
            volunteerRepository.save(volunteer);
            return true;
        }
    }


}
