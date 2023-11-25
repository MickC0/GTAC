package com.mickc0.gtac.mapper;

import com.mickc0.gtac.dto.AvailabilityDTO;
import com.mickc0.gtac.entity.Availability;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AvailabilityMapper {

    public AvailabilityDTO mapToDto(Availability availability) {
        AvailabilityDTO dto = new AvailabilityDTO();
        dto.setId(availability.getId());
        dto.setUuid(availability.getUuid());
        dto.setStartTime(availability.getStartTime());
        dto.setEndTime(availability.getEndTime());
        dto.setDayOfWeek(availability.getDayOfWeek());
        return dto;
    }

    public Availability mapToEntity(AvailabilityDTO dto) {
        Availability availability = new Availability();
        availability.setId(dto.getId());
        availability.setUuid(dto.getUuid());
        availability.setStartTime(dto.getStartTime());
        availability.setEndTime(dto.getEndTime());
        availability.setDayOfWeek(dto.getDayOfWeek());
        return availability;
    }

    public Availability mapToNewEntity(AvailabilityDTO dto) {
        Availability availability = new Availability();
        availability.setId(dto.getId());
        availability.setStartTime(dto.getStartTime());
        availability.setEndTime(dto.getEndTime());
        availability.setDayOfWeek(dto.getDayOfWeek());
        return availability;
    }

    public List<AvailabilityDTO> mapToAvailabilityDtoListForVolunteerEditDto(Set<Availability> availabilities) {
        return availabilities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public Set<Availability> mapToEntitySet(List<AvailabilityDTO> dtos) {
        return dtos.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toSet());
    }


}
