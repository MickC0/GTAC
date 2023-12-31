package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.AvailabilityDTO;
import com.mickc0.gtac.entity.Availability;
import com.mickc0.gtac.entity.Volunteer;
import com.mickc0.gtac.mapper.AvailabilityMapper;
import com.mickc0.gtac.repository.AvailabilityRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {
    private final AvailabilityRepository availabilityRepository;
    private final AvailabilityMapper availabilityMapper;

    @Autowired
    public AvailabilityServiceImpl(AvailabilityRepository availabilityRepository, AvailabilityMapper availabilityMapper) {
        this.availabilityRepository = availabilityRepository;
        this.availabilityMapper = availabilityMapper;
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
    public void deleteById(Long id) {
        availabilityRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(Availability availability) {
        availabilityRepository.delete(availability);
    }

    @Override
    @Transactional
    public void deleteByUuid(UUID uuid) {
        availabilityRepository.deleteByUuid(uuid);
    }

    @Override
    @Transactional
    public void deleteAllByVolunteer(Volunteer volunteer) {

        availabilityRepository.deleteAllByVolunteer(volunteer);
    }

    @Override
    public void deleteAllByVolunteerUuid(UUID uuid) {
        availabilityRepository.deleteAllByVolunteerUuid(uuid);
    }

    @Override
    public AvailabilityDTO findAvailabilityDtoByUuid(UUID uuid) {
        return availabilityMapper.mapToDto(availabilityRepository.findByUuid(uuid)
                        .orElseThrow(() -> new EntityNotFoundException("La disponibilité avec l'Id: " + uuid + " n'existe pas")));
    }

    @Override
    public Availability findByUuid(UUID uuid) {
        return availabilityRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("La disponibilité avec l'Id: " + uuid + " n'existe pas"));
    }
}
