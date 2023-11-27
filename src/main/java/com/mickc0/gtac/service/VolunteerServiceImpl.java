package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.VolunteerDTO;
import com.mickc0.gtac.dto.VolunteerStatusDTO;
import com.mickc0.gtac.entity.Availability;
import com.mickc0.gtac.entity.MissionType;
import com.mickc0.gtac.entity.Unavailability;
import com.mickc0.gtac.entity.Volunteer;
import com.mickc0.gtac.mapper.AvailabilityMapper;
import com.mickc0.gtac.mapper.MissionTypeMapper;
import com.mickc0.gtac.mapper.UnavailabilityMapper;
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
    private final AvailabilityMapper availabilityMapper;
    private final MissionTypeService missionTypeService;
    private final MissionTypeMapper missionTypeMapper;
    private final AvailabilityService availabilityService;
    private final UnavailabilityMapper unavailabilityMapper;
    private final UnavailabilityService unavailabilityService;

    @Autowired
    public VolunteerServiceImpl(VolunteerRepository volunteerRepository, VolunteerMapper volunteerMapper, AvailabilityMapper availabilityMapper, MissionTypeService missionTypeService, MissionTypeMapper missionTypeMapper, AvailabilityService availabilityService, UnavailabilityMapper unavailabilityMapper, UnavailabilityService unavailabilityService) {
        this.volunteerRepository = volunteerRepository;
        this.volunteerMapper = volunteerMapper;
        this.availabilityMapper = availabilityMapper;
        this.missionTypeService = missionTypeService;
        this.missionTypeMapper = missionTypeMapper;
        this.availabilityService = availabilityService;
        this.unavailabilityMapper = unavailabilityMapper;
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

        Set<MissionType> missionTypes = volunteerDTO.getMissionTypes().stream()
                .map(UUID::fromString)
                .map(missionTypeService::findMissionTypeByUuid)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        volunteer.setMissionTypes(missionTypes);

        /*Set<Unavailability> unavailabilities = Optional.ofNullable(volunteer.getUnavailabilities())
                .orElse(Collections.emptyList())
                .stream()
                .map(unavailabilityDTO -> {
                    Unavailability unavailability;
                    if (unavailabilityDTO.getUuid() == null){
                        unavailability = unavailabilityMapper.mapToNewEntity(unavailabilityDTO);
                    } else {
                        unavailability = unavailabilityMapper.mapToCompleteEntity(unavailabilityService.findUnavailabilityDtoByUuid(unavailabilityDTO.getUuid()));
                        unavailability.setStartDate(unavailabilityDTO.getStartDate());
                        unavailability.setEndDate(unavailabilityDTO.getEndDate());
                    }
                    unavailability.setVolunteer(newVolunteer);
                    unavailabilityService.save(unavailability);
                    return unavailability;
                })
                .collect(Collectors.toSet());
        newVolunteer.setUnavailabilities(unavailabilities);

        //Fonctionnel
        Set<Availability> availabilities = Optional.ofNullable(volunteer.getAvailabilities())
                .orElse(Collections.emptyList())
                .stream()
                .map(availabilityDTO -> {
                    Availability availability;
                    if (availabilityDTO.getUuid() == null){
                        availability = availabilityMapper.mapToNewEntity(availabilityDTO);
                    } else {
                        availability = availabilityMapper.mapToCompleteEntity(availabilityService.findAvailabilityDtoByUuid(availabilityDTO.getUuid()));
                        availability.setDayOfWeek(availabilityDTO.getDayOfWeek());
                        availability.setStartTime(availabilityDTO.getStartTime());
                        availability.setEndTime(availabilityDTO.getEndTime());
                    }
                    availability.setVolunteer(newVolunteer);
                    availabilityService.save(availability);
                    return availability;
                })
                .collect(Collectors.toSet());
        newVolunteer.setAvailabilities(availabilities);*/

        volunteerRepository.save(volunteer);
    }

    @Override
    public Optional<Volunteer> findById(Long id) {
        return volunteerRepository.findById(id);
    }

    @Override
    public VolunteerDTO findVolunteerDTOByUuid(UUID uuid) {
        return volunteerMapper.mapToDTO(volunteerRepository.findByUuid(uuid)
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
    public List<Volunteer> getAvailableUsersForMission(LocalDateTime start, LocalDateTime end, Long missionTypeId) {
        DayOfWeek dayOfWeek = start.getDayOfWeek();
        LocalTime startTime = start.toLocalTime();
        LocalTime endTime = end.toLocalTime();

        return volunteerRepository.findAvailableVolunteersForMission(start, end, dayOfWeek, startTime, endTime, missionTypeId);
    }

}
