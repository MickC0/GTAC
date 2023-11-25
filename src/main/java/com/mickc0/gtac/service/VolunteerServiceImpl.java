package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.AvailabilityDTO;
import com.mickc0.gtac.dto.VolunteerEditDTO;
import com.mickc0.gtac.dto.VolunteerStatusDTO;
import com.mickc0.gtac.entity.Availability;
import com.mickc0.gtac.entity.MissionType;
import com.mickc0.gtac.entity.Volunteer;
import com.mickc0.gtac.mapper.AvailabilityMapper;
import com.mickc0.gtac.mapper.VolunteerMapper;
import com.mickc0.gtac.repository.VolunteerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class VolunteerServiceImpl implements VolunteerService{

    private final VolunteerRepository volunteerRepository;
    private final VolunteerMapper volunteerMapper;
    private final AvailabilityMapper availabilityMapper;
    private final MissionTypeService missionTypeService;
    private final AvailabilityService availabilityService;

    @Autowired
    public VolunteerServiceImpl(VolunteerRepository volunteerRepository, VolunteerMapper volunteerMapper, AvailabilityMapper availabilityMapper, MissionTypeService missionTypeService, AvailabilityService availabilityService) {
        this.volunteerRepository = volunteerRepository;
        this.volunteerMapper = volunteerMapper;
        this.availabilityMapper = availabilityMapper;
        this.missionTypeService = missionTypeService;
        this.availabilityService = availabilityService;
    }

    @Override
    @Transactional
    public Volunteer saveAndReturn(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    @Override
    @Transactional
    public void save(Volunteer volunteer) {
        volunteerRepository.save(volunteer);
    }

    @Override
    public Optional<Volunteer> findById(Long id) {
        return volunteerRepository.findById(id);
    }

    @Override
    public VolunteerEditDTO findVolunteerEditDTOById(Long id) {
        return volunteerMapper.mapToEditVolunteerDTO(volunteerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Le bénévole avec l'Id: " + id + " n'existe pas")));
    }

    @Override
    public List<Volunteer> findAll() {
        return volunteerRepository.findAll();
    }

    @Override
    @Transactional
    public void updateVolunteer(VolunteerEditDTO volunteerEditDTO) {
        Volunteer existingVolunteer = volunteerRepository.findById(volunteerEditDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Ce volontaire n'existe pas"));
        existingVolunteer.getMissionTypes().clear();
        existingVolunteer.setLastName(volunteerEditDTO.getLastName());
        existingVolunteer.setFirstName(volunteerEditDTO.getFirstName());
        existingVolunteer.setPhoneNumber(volunteerEditDTO.getPhoneNumber());
        existingVolunteer.setEmail(volunteerEditDTO.getEmail());
        availabilityService.deleteAllByVolunteer(existingVolunteer);



        Set<Availability> updatedAvailabilities = Optional.ofNullable(volunteerEditDTO.getAvailabilities())
                .orElse(Collections.emptyList())
                .stream()
                .map(availabilityDTO -> {
                    Availability availability = availabilityMapper.mapToNewEntity(availabilityDTO);
                    availability.setVolunteer(existingVolunteer);
                    availabilityService.save(availability);
                    return availability;
                })
                .collect(Collectors.toSet());
        existingVolunteer.setAvailabilities(updatedAvailabilities);


        Set<MissionType> updatedMissionTypes = Optional.ofNullable(volunteerEditDTO.getMissionTypes())
                .orElse(Collections.emptyList())
                .stream()
                .map(missionTypeService::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        existingVolunteer.setMissionTypes(updatedMissionTypes);


        volunteerRepository.save(existingVolunteer);
    }

    @Override
    @Transactional
    public void deleteVolunteer(Long id) {
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

}
