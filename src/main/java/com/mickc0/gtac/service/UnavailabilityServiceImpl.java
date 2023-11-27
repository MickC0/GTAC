package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.UnavailabilityDTO;
import com.mickc0.gtac.dto.UnavailabilityWithoutIdDTO;
import com.mickc0.gtac.entity.Unavailability;
import com.mickc0.gtac.mapper.UnavailabilityMapper;
import com.mickc0.gtac.repository.UnavailabilityRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UnavailabilityServiceImpl implements UnavailabilityService {

    private final UnavailabilityRepository unavailabilityRepository;
    private final UnavailabilityMapper unavailabilityMapper;

    @Autowired
    public UnavailabilityServiceImpl(UnavailabilityRepository unavailabilityRepository, UnavailabilityMapper unavailabilityMapper) {
        this.unavailabilityRepository = unavailabilityRepository;
        this.unavailabilityMapper = unavailabilityMapper;
    }

    @Override
    @Transactional
    public void save(Unavailability unavailability) {
        /*Unavailability unavailability;
        if (unavailabilityDTO.getUuid() != null){
            unavailability = unavailabilityRepository.findByUuid(unavailabilityDTO.getUuid())
                    .orElseThrow(() -> new EntityNotFoundException("Indisponibilité non trouvée"));
            unavailability.setStartDate(unavailabilityDTO.getStartDate());
            unavailability.setEndDate(unavailabilityDTO.getEndDate());
        } else {
            unavailability = unavailabilityMapper.mapToEntity(unavailabilityDTO);
        }*/

        unavailabilityRepository.save(unavailability);
    }

    @Override
    public List<UnavailabilityWithoutIdDTO> findAll() {
        return unavailabilityRepository.findAll()
                .stream()
                .map(unavailabilityMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        unavailabilityRepository.deleteById(id);
    }

    @Override
    public UnavailabilityWithoutIdDTO findById(Long id) {
        return unavailabilityMapper.mapToDto(unavailabilityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Indisponibilité non trouvée")));
    }

    @Override
    @Transactional
    public void removeExpiredUnavailabilities() {
        LocalDate today = LocalDate.now();
        unavailabilityRepository.deleteByEndDateBefore(today);
    }

    @Override
    public UnavailabilityDTO findUnavailabilityDtoByUuid(UUID uuid) {
        return unavailabilityMapper.mapToCompleteDto(unavailabilityRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Indisponibilité non trouvée")));
    }


}
