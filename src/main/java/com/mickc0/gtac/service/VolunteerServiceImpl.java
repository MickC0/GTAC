package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.VolunteerDTO;
import com.mickc0.gtac.mapper.VolunteerMapper;
import com.mickc0.gtac.model.Volunteer;
import com.mickc0.gtac.repository.VolunteerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class VolunteerServiceImpl implements VolunteerService{
    private final VolunteerRepository volunteerRepository;
    private final VolunteerMapper volunteerMapper;

    public VolunteerServiceImpl(VolunteerRepository volunteerRepository, VolunteerMapper volunteerMapper) {
        this.volunteerRepository = volunteerRepository;
        this.volunteerMapper = volunteerMapper;
    }

    @Override
    public List<VolunteerDTO> findAll() {
        List<Volunteer> volunteers = volunteerRepository.findAll();
        return volunteers.stream()
                .map(volunteerMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void saveVolunteer(VolunteerDTO volunteerDTO) {
        Volunteer volunteer = volunteerMapper.mapToEntity(volunteerDTO);
        volunteer.setUuid(UUID.randomUUID());
        volunteerRepository.save(volunteer);
    }

    @Override
    public VolunteerDTO findVolunteerByUUID(UUID uuid) {
        return volunteerMapper.mapToDto(volunteerRepository.findVolunteerByUuid(uuid));
    }

    @Override
    public void updateVolunteer(VolunteerDTO volunteerDTO) {
        Volunteer existingVolunteer = volunteerRepository.findVolunteerByUuid(volunteerDTO.getUuid());
        Volunteer volunteer = volunteerMapper.mapToEntity(volunteerDTO);
        volunteer.setId(existingVolunteer.getId());
        volunteerRepository.save(volunteer);
    }

    @Override
    public void deleteVolunteer(UUID uuid) {
        if(isUUIDPresent(uuid)){
            volunteerRepository.deleteByUuid(uuid);
        } else {
            throw new RuntimeException("Le bénévole ne peut être supprimé id : " + uuid + " non conforme");
        }
    }

    @Override
    public List<VolunteerDTO> searchVolunteers(String query) {
        List<Volunteer> volunteers = volunteerRepository.searchVolunteers(query);
        return volunteers.stream()
                .map(volunteerMapper::mapToDto)
                .collect(Collectors.toList());
    }


    private boolean isUUIDPresent(UUID uuid) {
        return uuid != null && !uuid.equals(UUID.fromString("00000000-0000-0000-0000-000000000000"));
    }


}
