package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.MissionDTO;
import com.mickc0.gtac.model.Mission;
import com.mickc0.gtac.model.MissionStatus;
import com.mickc0.gtac.repository.MissionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MissionServiceImpl implements MissionService {
    private final MissionRepository missionRepository;

    public MissionServiceImpl(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
    }

    @Override
    public List<MissionDTO> findAll() {
        List<Mission> missions = missionRepository.findAll();
        return missions.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    public void save(MissionDTO missionDTO) {
        missionRepository.save(toEntity(missionDTO));
    }

    private MissionDTO toDTO(Mission mission) {
        MissionDTO dto = new MissionDTO();
        dto.setId(mission.getId());
        dto.setUuid(mission.getUuid());
        dto.setName(mission.getName());
        dto.setDescription(mission.getDescription());
        dto.setComment(mission.getComment());
        dto.setMissionType(mission.getMissionType());
        try {
            dto.setStartingDate(mission.getStartingDate());
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Erreur de conversion pour missionStartingDate : " + ex.getMessage());
        }
        try {
            dto.setEndingDate(mission.getEndingDate());
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Erreur de conversion pour missionEndingDate : " + ex.getMessage());
        }
        dto.setRequiredVolunteerNumber(mission.getRequiredVolunteerNumber());
        dto.setStatus(mission.getStatus());

        return dto;
    }

    private Mission toEntity(MissionDTO missionDTO) {
        Mission entity = new Mission();
        entity.setUuid(UUID.randomUUID());
        entity.setName(missionDTO.getName());
        entity.setDescription(missionDTO.getDescription());
        entity.setComment(missionDTO.getComment());
        entity.setMissionType(missionDTO.getMissionType());
        try {
            entity.setStartingDate(missionDTO.getStartingDate());
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Erreur de conversion pour missionStartingDate : " + ex.getMessage());
        }
        try {
            entity.setEndingDate(missionDTO.getEndingDate());
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Erreur de conversion pour missionEndingDate : " + ex.getMessage());
        }
        entity.setRequiredVolunteerNumber(missionDTO.getRequiredVolunteerNumber());
        entity.setStatus(MissionStatus.NEW);

        return entity;
    }




    private List<String> getMissionStatuses() {
        MissionStatus[] missionStatuses = MissionStatus.values();
        List<String> missionStatusesDTO = Arrays.stream(missionStatuses)
                .map(Enum::name)
                .toList();
        return missionStatusesDTO;
    }

}
