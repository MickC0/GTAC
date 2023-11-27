package com.mickc0.gtac.mapper;

import com.mickc0.gtac.dto.UnavailabilityDTO;
import com.mickc0.gtac.dto.UnavailabilityWithoutIdDTO;
import com.mickc0.gtac.entity.Unavailability;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UnavailabilityMapper {

    public UnavailabilityWithoutIdDTO mapToDto(Unavailability unavailability){
        UnavailabilityWithoutIdDTO unavailabilityWithoutIdDTO = new UnavailabilityWithoutIdDTO();
        unavailabilityWithoutIdDTO.setUuid(unavailability.getUuid());
        unavailabilityWithoutIdDTO.setStartDate(unavailability.getStartDate());
        unavailabilityWithoutIdDTO.setEndDate(unavailability.getEndDate());
        return unavailabilityWithoutIdDTO;
    }
    public UnavailabilityDTO mapToCompleteDto(Unavailability unavailability){
        UnavailabilityDTO unavailabilityDTO = new UnavailabilityDTO();
        unavailabilityDTO.setId(unavailability.getId());
        unavailabilityDTO.setUuid(unavailability.getUuid());
        unavailabilityDTO.setStartDate(unavailability.getStartDate());
        unavailabilityDTO.setEndDate(unavailability.getEndDate());
        return unavailabilityDTO;
    }

    public Unavailability mapToEntity(UnavailabilityWithoutIdDTO unavailabilityWithoutIdDTO){
        Unavailability unavailability = new Unavailability();
        unavailability.setUuid(unavailabilityWithoutIdDTO.getUuid());
        unavailability.setStartDate(unavailabilityWithoutIdDTO.getStartDate());
        unavailability.setEndDate(unavailabilityWithoutIdDTO.getEndDate());
        return unavailability;
    }
    public Unavailability mapToCompleteEntity(UnavailabilityDTO unavailabilityDTO){
        Unavailability unavailability = new Unavailability();
        unavailability.setId(unavailabilityDTO.getId());
        unavailability.setUuid(unavailabilityDTO.getUuid());
        unavailability.setStartDate(unavailabilityDTO.getStartDate());
        unavailability.setEndDate(unavailabilityDTO.getEndDate());
        return unavailability;
    }

    public Unavailability mapToNewEntity(UnavailabilityWithoutIdDTO unavailabilityWithoutIdDTO){
        Unavailability unavailability = new Unavailability();
        unavailability.setStartDate(unavailabilityWithoutIdDTO.getStartDate());
        unavailability.setEndDate(unavailabilityWithoutIdDTO.getEndDate());
        return unavailability;
    }

    public List<UnavailabilityWithoutIdDTO> mapToUnavailabilityDtoListForVolunteerEditDto(Set<Unavailability> unavailabilities) {

        //sortAvailabilities(unavailabilityDTOList);
        return unavailabilities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
