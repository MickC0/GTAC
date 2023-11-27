package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.VolunteerDTO;
import com.mickc0.gtac.dto.VolunteerNewDTO;
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
    public void save(VolunteerNewDTO volunteer) {
        Volunteer newVolunteer = saveAndReturn(volunteerMapper.mapToEntityLowDetail(volunteer));

        Set<Unavailability> unavailabilities = Optional.ofNullable(volunteer.getUnavailabilities())
                .orElse(Collections.emptyList())
                .stream()
                .map(unavailabilityDTO -> {
                    Unavailability unavailability;
                    if (unavailabilityDTO.getUuid() == null){
                        unavailability = unavailabilityMapper.mapToNewEntity(unavailabilityDTO);
                    } else {
                        unavailability = unavailabilityMapper.mapToEntity(unavailabilityDTO);
                    }
                    unavailability.setVolunteer(newVolunteer);
                    unavailabilityService.save(unavailability);
                    return unavailability;
                })
                .collect(Collectors.toSet());
        newVolunteer.setUnavailabilities(unavailabilities);

        Set<Availability> updatedAvailabilities = Optional.ofNullable(volunteer.getAvailabilities())
                .orElse(Collections.emptyList())
                .stream()
                .map(availabilityDTO -> {
                    Availability availability;
                    if (availabilityDTO.getUuid() == null){
                        availability = availabilityMapper.mapToNewEntity(availabilityDTO);
                    } else {
                        availability = availabilityMapper.mapToEntity(availabilityDTO);
                    }
                    availability.setVolunteer(newVolunteer);
                    availabilityService.save(availability);
                    return availability;
                })
                .collect(Collectors.toSet());
        newVolunteer.setAvailabilities(updatedAvailabilities);

        Set<MissionType> updatedMissionTypes = missionTypeMapper.mapToMissionTypeEntitySetFromDtoList(volunteer.getMissionTypes());
        newVolunteer.setMissionTypes(updatedMissionTypes);



        volunteerRepository.save(newVolunteer);
    }

    @Override
    public Optional<Volunteer> findById(Long id) {
        return volunteerRepository.findById(id);
    }

    @Override
    public VolunteerDTO findVolunteerEditDTOById(Long id) {
        return volunteerMapper.mapToDTO(volunteerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Le bénévole avec l'Id: " + id + " n'existe pas")));
    }

    @Override
    public List<Volunteer> findAll() {
        return volunteerRepository.findAll();
    }

    @Override
    @Transactional
    public void updateVolunteer(VolunteerDTO volunteerDTO) {
        Volunteer existingVolunteer = volunteerRepository.findByUuid(volunteerDTO.getUuid())
                .orElseThrow(() -> new EntityNotFoundException("Ce volontaire n'existe pas"));
        //existingVolunteer.getMissionTypes().clear();
        existingVolunteer.setLastName(volunteerDTO.getLastName());
        existingVolunteer.setFirstName(volunteerDTO.getFirstName());
        existingVolunteer.setPhoneNumber(volunteerDTO.getPhoneNumber());
        existingVolunteer.setEmail(volunteerDTO.getEmail());

        Set<Unavailability> updatedUnavailabilities = Optional.ofNullable(volunteerDTO.getUnavailabilities())
                .orElse(Collections.emptyList())
                .stream()
                .map(unavailabilityDTO -> {
                    Unavailability unavailability;
                    if (unavailabilityDTO.getUuid() == null){
                        unavailability = unavailabilityMapper.mapToNewEntity(unavailabilityDTO);
                    } else {
                        unavailability = unavailabilityMapper.mapToEntity(unavailabilityDTO);
                    }
                    unavailability.setVolunteer(existingVolunteer);
                    unavailabilityService.save(unavailability);
                    return unavailability;
                })
                .collect(Collectors.toSet());
        existingVolunteer.setUnavailabilities(updatedUnavailabilities);

        Set<Availability> updatedAvailabilities = Optional.ofNullable(volunteerDTO.getAvailabilities())
                .orElse(Collections.emptyList())
                .stream()
                .map(availabilityDTO -> {
                    Availability availability;
                    if (availabilityDTO.getUuid() == null){
                        availability = availabilityMapper.mapToNewEntity(availabilityDTO);
                    } else {
                        availability = availabilityMapper.mapToEntity(availabilityDTO);
                    }
                    availability.setVolunteer(existingVolunteer);
                    availabilityService.save(availability);
                    return availability;
                })
                .collect(Collectors.toSet());
        existingVolunteer.setAvailabilities(updatedAvailabilities);

        Set<MissionType> updatedMissionTypes = missionTypeMapper.mapToMissionTypeEntitySetFromDtoList(volunteerDTO.getMissionTypes());
        existingVolunteer.setMissionTypes(updatedMissionTypes);


        volunteerRepository.save(existingVolunteer);
    }

    @Override
    @Transactional
    public void deleteVolunteer(Long id) {
        availabilityService.deleteAllByVolunteerId(id);
        volunteerRepository.deleteById(id);
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
