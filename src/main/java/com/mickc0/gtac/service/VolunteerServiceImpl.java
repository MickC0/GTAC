package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.*;
import com.mickc0.gtac.entity.Availability;
import com.mickc0.gtac.entity.MissionType;
import com.mickc0.gtac.entity.Unavailability;
import com.mickc0.gtac.entity.Volunteer;
import com.mickc0.gtac.mapper.VolunteerMapper;
import com.mickc0.gtac.repository.VolunteerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class VolunteerServiceImpl implements VolunteerService{

    private final VolunteerRepository volunteerRepository;
    private final VolunteerMapper volunteerMapper;
    private final MissionTypeService missionTypeService;
    private final AvailabilityService availabilityService;
    private final UnavailabilityService unavailabilityService;

    @Autowired
    public VolunteerServiceImpl(VolunteerRepository volunteerRepository, VolunteerMapper volunteerMapper,
                                MissionTypeService missionTypeService,
                                AvailabilityService availabilityService,
                                UnavailabilityService unavailabilityService) {
        this.volunteerRepository = volunteerRepository;
        this.volunteerMapper = volunteerMapper;
        this.missionTypeService = missionTypeService;
        this.availabilityService = availabilityService;
        this.unavailabilityService = unavailabilityService;
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
        if (volunteerDTO.getUuid() != null && volunteerRepository.findByUuid(volunteerDTO.getUuid()).isPresent()) {
            volunteer = volunteerRepository.findByUuid(volunteerDTO.getUuid())
                    .orElseThrow(() -> new EntityNotFoundException("Le bénévole avec l'Id: " + volunteerDTO.getUuid() + " n'existe pas"));
        } else {
            volunteer = new Volunteer();
        }

        volunteer.setLastName(volunteerDTO.getLastName());
        volunteer.setFirstName(volunteerDTO.getFirstName());
        volunteer.setEmail(volunteerDTO.getEmail());
        volunteer.setPhoneNumber(volunteerDTO.getPhoneNumber());
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
                .map(volunteer -> volunteerMapper.mapToStatusDTO(volunteer, now))
                .collect(Collectors.toList());
    }

    @Override
    public List<VolunteerDTO> getAvailableUsersForMission(LocalDateTime start, LocalDateTime end, UUID missionTypeUuid) {
        DayOfWeek dayOfWeek = start.getDayOfWeek();
        LocalTime startTime = start.toLocalTime();
        LocalTime endTime = end.toLocalTime();

        return volunteerRepository.findAvailableVolunteersForMission(start, end, dayOfWeek, startTime, endTime, missionTypeUuid).stream()
                .map(volunteerMapper::mapToConfirmDto)
                .collect(Collectors.toList());
    }

    @Override
    public VolunteerProfilDTO findVolunteerProfilDTOByUuid(UUID uuid) {
        return volunteerMapper.mapToProfilDto(volunteerRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Le bénévole avec l'Id: " + uuid + " n'existe pas")));
    }

    @Override
    public List<Volunteer> findVolunteersByUuids(List<UUID> volunteerUuids) {
        return volunteerRepository.findAllByUuidIn(volunteerUuids);

    }


}
