package com.mickc0.gtac.service;

import com.mickc0.gtac.entity.Availability;
import com.mickc0.gtac.entity.Volunteer;
import com.mickc0.gtac.repository.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {
    private final AvailabilityRepository availabilityRepository;

    @Autowired
    public AvailabilityServiceImpl(AvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

    @Override
    @Transactional
    public Availability createAvailability(Availability availability) {
        return availabilityRepository.save(availability);
    }

    @Override
    @Transactional
    public void save(Availability availability) {
        availabilityRepository.save(availability);
    }

    @Override
    public List<Availability> getAllAvailabilities() {
        return availabilityRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteAvailability(Long id) {
        availabilityRepository.deleteById(id);
    }

    @Override
    public void deleteByUuid(UUID uuid) {
        availabilityRepository.deleteByUuid(uuid);
    }

    @Override
    public void deleteAllByVolunteer(Volunteer volunteer) {

        availabilityRepository.deleteAllByVolunteer(volunteer);
    }
}
