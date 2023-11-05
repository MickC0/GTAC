package com.mickc0.gtac.mapper;

import com.mickc0.gtac.dto.VolunteerDTO;
import com.mickc0.gtac.model.Volunteer;
import org.springframework.stereotype.Component;

@Component
public class VolunteerMapper {

    public VolunteerDTO mapToDto(Volunteer volunteer){
        VolunteerDTO volunteerDTO = new VolunteerDTO();
        volunteerDTO.setUuid(volunteer.getUuid());
        volunteerDTO.setLastName(volunteer.getLastName());
        volunteerDTO.setFirstName(volunteer.getFirstName());
        volunteerDTO.setEmail(volunteer.getEmail());
        volunteerDTO.setPhoneNumber(volunteer.getPhoneNumber());
        return volunteerDTO;
    }

    public Volunteer mapToEntity(VolunteerDTO volunteerDTO){
        Volunteer volunteer = new Volunteer();
        volunteer.setUuid(volunteerDTO.getUuid());
        volunteer.setLastName(volunteerDTO.getLastName());
        volunteer.setFirstName(volunteerDTO.getFirstName());
        volunteer.setEmail(volunteerDTO.getEmail());
        volunteer.setPhoneNumber(volunteerDTO.getPhoneNumber());
        return volunteer;
    }


}
