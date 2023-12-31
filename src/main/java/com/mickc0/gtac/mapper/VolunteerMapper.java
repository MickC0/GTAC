package com.mickc0.gtac.mapper;

import com.mickc0.gtac.dto.*;
import com.mickc0.gtac.entity.Role;
import com.mickc0.gtac.entity.Volunteer;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

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

    public VolunteerStatusDTO mapToStatusDTO(Volunteer volunteer, String status) {
        VolunteerStatusDTO volunteerStatusDTO = new VolunteerStatusDTO();
        volunteerStatusDTO.setUuid(volunteer.getUuid());
        volunteerStatusDTO.setEmail(volunteer.getEmail());
        volunteerStatusDTO.setLastName(volunteer.getLastName());
        volunteerStatusDTO.setFirstName(volunteer.getFirstName());
        volunteerStatusDTO.setPhoneNumber(volunteer.getPhoneNumber());
        volunteerStatusDTO.setStatus(status);
        return volunteerStatusDTO;
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

    public VolunteerGuestProfilDTO mapToProfilDto(Volunteer volunteer){
        VolunteerGuestProfilDTO volunteerDTO = new VolunteerGuestProfilDTO();
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
    public VolunteerDetailsDTO mapToDetailsDto(Volunteer volunteer){
        VolunteerDetailsDTO volunteerDTO = new VolunteerDetailsDTO();
        volunteerDTO.setUuid(volunteer.getUuid());
        volunteerDTO.setLastName(volunteer.getLastName());
        volunteerDTO.setFirstName(volunteer.getFirstName());
        volunteerDTO.setEmail(volunteer.getEmail());
        volunteerDTO.setPhoneNumber(volunteer.getPhoneNumber());
        volunteerDTO.setPassword(volunteer.getPassword());
        volunteerDTO.setRoles(volunteer.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        return volunteerDTO;
    }

    public VolunteerRoleProfilDTO mapToFullDetailsDto(Volunteer volunteer){
        VolunteerRoleProfilDTO volunteerDTO = new VolunteerRoleProfilDTO();
        volunteerDTO.setUuid(volunteer.getUuid());
        volunteerDTO.setLastName(volunteer.getLastName());
        volunteerDTO.setFirstName(volunteer.getFirstName());
        volunteerDTO.setEmail(volunteer.getEmail());
        volunteerDTO.setPhoneNumber(volunteer.getPhoneNumber());
        volunteerDTO.setMustChangePassword(volunteer.isMustChangePassword());
        volunteerDTO.setMissionTypes(missionTypeMapper.mapToMissionTypeDtoListForVolunteerProfilDto(volunteer.getMissionTypes()));
        volunteerDTO.setAvailabilities(availabilityMapper.mapToAvailabilityDtoListForVolunteerEditDto(volunteer.getAvailabilities()));
        volunteerDTO.setUnavailabilities(unavailabilityMapper.mapToUnavailabilityDtoListForVolunteerEditDto(volunteer.getUnavailabilities()));
        volunteerDTO.setRoles(volunteer.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        return volunteerDTO;
    }


}
