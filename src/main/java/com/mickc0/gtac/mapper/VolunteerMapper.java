package com.mickc0.gtac.mapper;

import com.mickc0.gtac.dto.VolunteerDTO;
import com.mickc0.gtac.dto.VolunteerProfilDTO;
import com.mickc0.gtac.dto.VolunteerStatusDTO;
import com.mickc0.gtac.entity.MissionStatus;
import com.mickc0.gtac.entity.Volunteer;
import com.mickc0.gtac.repository.MissionAssignmentRepository;
import com.mickc0.gtac.repository.UnavailabilityRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class VolunteerMapper {


    private final AvailabilityMapper availabilityMapper;
    private final UnavailabilityMapper unavailabilityMapper;
    private final MissionTypeMapper missionTypeMapper;
    public VolunteerMapper(AvailabilityMapper availabilityMapper, UnavailabilityMapper unavailabilityMapper, MissionTypeMapper missionTypeMapper) {
        this.availabilityMapper = availabilityMapper;
        this.unavailabilityMapper = unavailabilityMapper;
        this.missionTypeMapper = missionTypeMapper;
    }


    //TODO A refactorer dans le service
    public VolunteerStatusDTO mapToStatusDTO(Volunteer volunteer, String status) {
        return new VolunteerStatusDTO(volunteer.getUuid(),
                volunteer.getLastName(), volunteer.getFirstName(),
                volunteer.getEmail(), volunteer.getPhoneNumber(), status);
    }

    public Volunteer mapToEntityLowDetail(VolunteerDTO volunteerDTO){
        Volunteer volunteer = new Volunteer();
        volunteer.setLastName(volunteerDTO.getLastName());
        volunteer.setFirstName(volunteerDTO.getFirstName());
        volunteer.setEmail(volunteerDTO.getEmail());
        volunteer.setPhoneNumber(volunteerDTO.getPhoneNumber());
        return volunteer;
    }

    public VolunteerDTO mapToDto(Volunteer volunteer){
        VolunteerDTO volunteerDTO = new VolunteerDTO();
        volunteerDTO.setUuid(volunteer.getUuid());
        volunteerDTO.setLastName(volunteer.getLastName());
        volunteerDTO.setFirstName(volunteer.getFirstName());
        volunteerDTO.setEmail(volunteer.getEmail());
        volunteerDTO.setPhoneNumber(volunteer.getPhoneNumber());
        volunteerDTO.setMissionTypes(missionTypeMapper.mapToMissionTypeUuidListForVolunteerDto(volunteer.getMissionTypes()));
        volunteerDTO.setAvailabilities(availabilityMapper.mapToAvailabilityDtoListForVolunteerEditDto(volunteer.getAvailabilities()));
        volunteerDTO.setUnavailabilities(unavailabilityMapper.mapToUnavailabilityDtoListForVolunteerEditDto(volunteer.getUnavailabilities()));
        return volunteerDTO;
    }

    public VolunteerProfilDTO mapToProfilDto(Volunteer volunteer){
        VolunteerProfilDTO volunteerDTO = new VolunteerProfilDTO();
        volunteerDTO.setUuid(volunteer.getUuid());
        volunteerDTO.setLastName(volunteer.getLastName());
        volunteerDTO.setFirstName(volunteer.getFirstName());
        volunteerDTO.setEmail(volunteer.getEmail());
        volunteerDTO.setPhoneNumber(volunteer.getPhoneNumber());
        volunteerDTO.setMissionTypes(missionTypeMapper.mapToMissionTypeDtoListForVolunteerProfilDto(volunteer.getMissionTypes()));
        volunteerDTO.setAvailabilities(availabilityMapper.mapToAvailabilityDtoListForVolunteerEditDto(volunteer.getAvailabilities()));
        volunteerDTO.setUnavailabilities(unavailabilityMapper.mapToUnavailabilityDtoListForVolunteerEditDto(volunteer.getUnavailabilities()));
        return volunteerDTO;
    }

    public VolunteerDTO mapToConfirmDto(Volunteer volunteer){
        VolunteerDTO volunteerDTO = new VolunteerDTO();
        volunteerDTO.setUuid(volunteer.getUuid());
        volunteerDTO.setLastName(volunteer.getLastName());
        volunteerDTO.setFirstName(volunteer.getFirstName());
        volunteerDTO.setEmail(volunteer.getEmail());
        volunteerDTO.setPhoneNumber(volunteer.getPhoneNumber());
        return volunteerDTO;
    }

}
