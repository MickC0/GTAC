package com.mickc0.gtac.mapper;

import com.mickc0.gtac.dto.AvailabilityDTO;
import com.mickc0.gtac.dto.AvailabilityWithoutIdDTO;
import com.mickc0.gtac.entity.Availability;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AvailabilityMapper {

    public AvailabilityWithoutIdDTO mapToDto(Availability availability) {
        AvailabilityWithoutIdDTO dto = new AvailabilityWithoutIdDTO();
        dto.setUuid(availability.getUuid());
        dto.setStartTime(availability.getStartTime());
        dto.setEndTime(availability.getEndTime());
        dto.setDayOfWeek(availability.getDayOfWeek());
        return dto;
    }

    public Availability mapToEntity(AvailabilityWithoutIdDTO dto) {
        Availability availability = new Availability();
        availability.setUuid(dto.getUuid());
        availability.setStartTime(dto.getStartTime());
        availability.setEndTime(dto.getEndTime());
        availability.setDayOfWeek(dto.getDayOfWeek());
        return availability;
    }
    public AvailabilityDTO mapToCompleteDto(Availability availability) {
        AvailabilityDTO availabilityDTO = new AvailabilityDTO();
        availabilityDTO.setId(availability.getId());
        availabilityDTO.setUuid(availability.getUuid());
        availabilityDTO.setStartTime(availability.getStartTime());
        availabilityDTO.setEndTime(availability.getEndTime());
        availabilityDTO.setDayOfWeek(availability.getDayOfWeek());
        return availabilityDTO;
    }

    public Availability mapToCompleteEntity(AvailabilityDTO availabilityDTO) {
        Availability availability = new Availability();
        availability.setId(availabilityDTO.getId());
        availability.setUuid(availabilityDTO.getUuid());
        availability.setStartTime(availabilityDTO.getStartTime());
        availability.setEndTime(availabilityDTO.getEndTime());
        availability.setDayOfWeek(availabilityDTO.getDayOfWeek());
        return availability;
    }

    public Availability mapToNewEntity(AvailabilityWithoutIdDTO dto) {
        Availability availability = new Availability();
        availability.setStartTime(dto.getStartTime());
        availability.setEndTime(dto.getEndTime());
        availability.setDayOfWeek(dto.getDayOfWeek());
        return availability;
    }

    public List<AvailabilityWithoutIdDTO> mapToAvailabilityDtoListForVolunteerEditDto(Set<Availability> availabilities) {
        List<AvailabilityWithoutIdDTO> availabilityWithoutIdDTOList = availabilities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        sortAvailabilities(availabilityWithoutIdDTOList);
        return availabilityWithoutIdDTOList;
    }

    public Set<Availability> mapToEntitySet(List<AvailabilityWithoutIdDTO> availabilities) {
        return availabilities.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toSet());
    }

    private static class AvailabilityComparator implements Comparator<AvailabilityWithoutIdDTO> {
        @Override
        public int compare(AvailabilityWithoutIdDTO a1, AvailabilityWithoutIdDTO a2) {
            int dayCompare = a1.getDayOfWeek().compareTo(a2.getDayOfWeek());
            if (dayCompare != 0) {
                return dayCompare;
            }
            return a1.getStartTime().compareTo(a2.getStartTime());
        }
    }
    private void sortAvailabilities(List<AvailabilityWithoutIdDTO> availabilities) {
        availabilities.sort(new AvailabilityComparator());
    }


}
