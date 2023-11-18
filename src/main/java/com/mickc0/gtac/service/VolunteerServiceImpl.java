package com.mickc0.gtac.service;

import com.mickc0.gtac.entity.Volunteer;
import com.mickc0.gtac.repository.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VolunteerServiceImpl implements VolunteerService{

    private final VolunteerRepository volunteerRepository;

    @Autowired
    public VolunteerServiceImpl(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    @Override
    @Transactional
    public Volunteer save(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    @Override
    public Optional<Volunteer> findVolunteerById(Long id) {
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


}
