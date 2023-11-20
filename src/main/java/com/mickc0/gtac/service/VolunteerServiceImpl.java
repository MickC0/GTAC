package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.VolunteerStatusDTO;
import com.mickc0.gtac.entity.Volunteer;
import com.mickc0.gtac.mapper.VolunteerMapper;
import com.mickc0.gtac.repository.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class VolunteerServiceImpl implements VolunteerService{

    private final VolunteerRepository volunteerRepository;
    private final VolunteerMapper volunteerMapper;

    @Autowired
    public VolunteerServiceImpl(VolunteerRepository volunteerRepository, VolunteerMapper volunteerMapper) {
        this.volunteerRepository = volunteerRepository;
        this.volunteerMapper = volunteerMapper;
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
    public List<Volunteer> findAll() {
        return volunteerRepository.findAll();
    }

    @Override
    @Transactional
    public void updateVolunteer(Volunteer volunteer) {
        Volunteer existingVolunteer = volunteerRepository.findById(volunteer.getId()).orElseThrow(() -> new RuntimeException("User not found"));
        existingVolunteer.setLastName(volunteer.getLastName());
        existingVolunteer.setFirstName(volunteer.getFirstName());
        existingVolunteer.setPhoneNumber(volunteer.getPhoneNumber());
        existingVolunteer.setEmail(volunteer.getEmail());
        volunteerRepository.save(volunteer);
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
