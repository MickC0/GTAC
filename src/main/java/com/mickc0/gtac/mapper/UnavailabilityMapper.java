package com.mickc0.gtac.mapper;

import com.mickc0.gtac.dto.UnavailabilityDTO;
import com.mickc0.gtac.entity.Unavailability;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UnavailabilityMapper {

    public UnavailabilityDTO mapToDto(Unavailability unavailability){
        UnavailabilityDTO unavailabilityDTO = new UnavailabilityDTO();
        unavailabilityDTO.setUuid(unavailability.getUuid());
        unavailabilityDTO.setStartDate(unavailability.getStartDate());
        unavailabilityDTO.setEndDate(unavailability.getEndDate());
        return unavailabilityDTO;
    }

    public List<UnavailabilityDTO> mapToUnavailabilityDtoListForVolunteerEditDto(Set<Unavailability> unavailabilities) {
        return unavailabilities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
