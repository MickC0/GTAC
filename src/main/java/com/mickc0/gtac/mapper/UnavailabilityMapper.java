package com.mickc0.gtac.mapper;

import com.mickc0.gtac.dto.AvailabilityDTO;
import com.mickc0.gtac.dto.UnavailabilityDTO;
import com.mickc0.gtac.entity.Availability;
import com.mickc0.gtac.entity.Unavailability;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UnavailabilityMapper {

    public UnavailabilityDTO mapToDto(Unavailability unavailability){
        UnavailabilityDTO unavailabilityDTO = new UnavailabilityDTO();
        unavailabilityDTO.setId(unavailability.getId());
        unavailabilityDTO.setUuid(unavailability.getUuid());
        unavailabilityDTO.setStartDate(unavailability.getStartDate());
        unavailabilityDTO.setEndDate(unavailability.getEndDate());
        return unavailabilityDTO;
    }

    public Unavailability mapToEntity(UnavailabilityDTO unavailabilityDTO){
        Unavailability unavailability = new Unavailability();
        unavailability.setId(unavailabilityDTO.getId());
        unavailability.setUuid(unavailabilityDTO.getUuid());
        unavailability.setStartDate(unavailabilityDTO.getStartDate());
        unavailability.setEndDate(unavailabilityDTO.getEndDate());
        return unavailability;
    }

    public Unavailability mapToNewEntity(UnavailabilityDTO unavailabilityDTO){
        Unavailability unavailability = new Unavailability();
        unavailability.setStartDate(unavailabilityDTO.getStartDate());
        unavailability.setEndDate(unavailabilityDTO.getEndDate());
        return unavailability;
    }

    public List<UnavailabilityDTO> mapToUnavailabilityDtoListForVolunteerEditDto(Set<Unavailability> unavailabilities) {
        List<UnavailabilityDTO> unavailabilityDTOList = unavailabilities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        //sortAvailabilities(unavailabilityDTOList);
        return unavailabilityDTOList;
    }
}
