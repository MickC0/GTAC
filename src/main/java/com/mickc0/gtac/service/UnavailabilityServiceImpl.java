package com.mickc0.gtac.service;

import com.mickc0.gtac.entity.Unavailability;
import com.mickc0.gtac.repository.UnavailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UnavailabilityServiceImpl implements UnavailabilityService {

    private final UnavailabilityRepository unavailabilityRepository;

    @Autowired
    public UnavailabilityServiceImpl(UnavailabilityRepository unavailabilityRepository) {
        this.unavailabilityRepository = unavailabilityRepository;
    }

    @Override
    @Transactional
    public Unavailability createUnavailability(Unavailability unavailability) {
        return unavailabilityRepository.save(unavailability);
    }

    @Override
    public List<Unavailability> getAllUnavailabilities() {
        return unavailabilityRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteUnavailability(Long id) {
        unavailabilityRepository.deleteById(id);
    }
}
