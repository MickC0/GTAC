package com.mickc0.gtac.mapper;

import com.mickc0.gtac.dto.VolunteerNewDTO;
import com.mickc0.gtac.dto.VolunteerStatusDTO;
import com.mickc0.gtac.entity.MissionStatus;
import com.mickc0.gtac.entity.Volunteer;
import com.mickc0.gtac.repository.MissionAssignmentRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class VolunteerMapper {

    private final MissionAssignmentRepository missionAssignmentRepository;
    public VolunteerMapper(MissionAssignmentRepository missionAssignmentRepository) {
        this.missionAssignmentRepository = missionAssignmentRepository;
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
}
