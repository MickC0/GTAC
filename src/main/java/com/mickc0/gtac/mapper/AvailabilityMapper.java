package com.mickc0.gtac.mapper;

import com.mickc0.gtac.dto.AvailabilityDTO;
import com.mickc0.gtac.entity.Availability;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AvailabilityMapper {

    public AvailabilityDTO mapToDto(Availability availability) {
        AvailabilityDTO availabilityDTO = new AvailabilityDTO();
        availabilityDTO.setUuid(availability.getUuid());
        availabilityDTO.setStartTime(availability.getStartTime());
        availabilityDTO.setEndTime(availability.getEndTime());
        availabilityDTO.setDayOfWeek(availability.getDayOfWeek());
        return availabilityDTO;
    }

    public List<AvailabilityDTO> mapToAvailabilityDtoListForVolunteerEditDto(Set<Availability> availabilities) {
        List<AvailabilityDTO> availabilityDTOS = availabilities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        sortAvailabilities(availabilityDTOS);
        return availabilityDTOS;
    }



    private static class AvailabilityComparator implements Comparator<AvailabilityDTO> {
        @Override
        public int compare(AvailabilityDTO a1, AvailabilityDTO a2) {
            int dayCompare = a1.getDayOfWeek().compareTo(a2.getDayOfWeek());
            if (dayCompare != 0) {
                return dayCompare;
            }
            return a1.getStartTime().compareTo(a2.getStartTime());
        }
    }
    private void sortAvailabilities(List<AvailabilityDTO> availabilities) {
        availabilities.sort(new AvailabilityComparator());
    }


}
