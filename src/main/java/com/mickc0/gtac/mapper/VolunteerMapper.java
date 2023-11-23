package com.mickc0.gtac.mapper;

import com.mickc0.gtac.dto.VolunteerEditDTO;
import com.mickc0.gtac.dto.VolunteerNewDTO;
import com.mickc0.gtac.dto.VolunteerStatusDTO;
import com.mickc0.gtac.entity.MissionStatus;
import com.mickc0.gtac.entity.MissionType;
import com.mickc0.gtac.entity.Volunteer;
import com.mickc0.gtac.repository.MissionAssignmentRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class VolunteerMapper {

    private final MissionAssignmentRepository missionAssignmentRepository;
    private final AvailabilityMapper availabilityMapper;
    public VolunteerMapper(MissionAssignmentRepository missionAssignmentRepository, AvailabilityMapper availabilityMapper) {
        this.missionAssignmentRepository = missionAssignmentRepository;
        this.availabilityMapper = availabilityMapper;
    }


    public VolunteerStatusDTO mapToStatusDTO(Volunteer volunteer, LocalDateTime now) {
        boolean isInMission = missionAssignmentRepository
                .findByVolunteerAndAssignedFromBeforeAndAssignedUntilAfter(volunteer, now, now)
                .stream()
                .anyMatch(ma -> ma.getMission().getStatus() == MissionStatus.ONGOING);
        String status = isInMission ? "En mission" : "Disponible";
        return new VolunteerStatusDTO(volunteer.getId(), volunteer.getUuid(),
                volunteer.getLastName(), volunteer.getFirstName(),
                volunteer.getEmail(), volunteer.getPhoneNumber(), status);
    }

    public Volunteer mapToEntityLowDetail(VolunteerNewDTO volunteerNewDTO){
        Volunteer volunteer = new Volunteer();
        volunteer.setLastName(volunteerNewDTO.getLastName());
        volunteer.setFirstName(volunteerNewDTO.getFirstName());
        volunteer.setEmail(volunteerNewDTO.getEmail());
        volunteer.setPhoneNumber(volunteerNewDTO.getPhoneNumber());
        return volunteer;
    }

    public VolunteerEditDTO mapToEditVolunteerDTO(Volunteer volunteer){
        VolunteerEditDTO volunteerEditDTO = new VolunteerEditDTO();
        volunteerEditDTO.setId(volunteer.getId());
        volunteerEditDTO.setUuid(volunteer.getUuid());
        volunteerEditDTO.setLastName(volunteer.getLastName());
        volunteerEditDTO.setFirstName(volunteer.getFirstName());
        volunteerEditDTO.setEmail(volunteer.getEmail());
        volunteerEditDTO.setPhoneNumber(volunteer.getPhoneNumber());
        volunteerEditDTO.setMissionTypes(Optional.ofNullable(volunteer.getMissionTypes()).orElse(Collections.emptySet()).stream()
                .map(MissionType::getId)
                .collect(Collectors.toList()));
        volunteerEditDTO.setAvailabilities(availabilityMapper.mapToAvailabilityDtoListForVolunteerEditDto(volunteer.getAvailabilities()));
        return volunteerEditDTO;
    }


}
