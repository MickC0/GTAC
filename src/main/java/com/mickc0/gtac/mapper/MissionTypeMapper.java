package com.mickc0.gtac.mapper;

import com.mickc0.gtac.dto.MissionTypeDTO;
import com.mickc0.gtac.dto.MissionTypeSelectionDTO;
import com.mickc0.gtac.entity.MissionType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MissionTypeMapper {

    public MissionTypeDTO mapToMissionTypeDto(MissionType missionType){
        MissionTypeDTO missionTypeDTO = new MissionTypeDTO();
        missionTypeDTO.setUuid(missionType.getUuid());
        missionTypeDTO.setName(missionType.getName());
        missionTypeDTO.setSelected(false);
        missionTypeDTO.setActive(missionType.isActive());
        return missionTypeDTO;
    }

    public MissionType mapToNewMissionTypeEntity(MissionTypeDTO missionTypeDTO){
        MissionType missionType = new MissionType();
        missionType.setName(missionTypeDTO.getName());
        missionType.setDescription(missionTypeDTO.getDescription());
        return missionType;
    }

    public MissionType mapToMissionTypeEntity(MissionTypeDTO missionTypeDTO){
        MissionType missionType = new MissionType();
        missionType.setUuid(missionTypeDTO.getUuid());
        missionType.setName(missionTypeDTO.getName());
        missionType.setDescription(missionTypeDTO.getDescription());
        return missionType;
    }

    public MissionTypeDTO mapToMissionTypeDtoForVolunteerEditDto(MissionType missionType){
        MissionTypeDTO missionTypeDTO = new MissionTypeDTO();
        missionTypeDTO.setUuid(missionType.getUuid());
        missionTypeDTO.setName(missionType.getName());
        missionTypeDTO.setSelected(true);
        return missionTypeDTO;
    }

    public List<MissionTypeDTO> mapToMissionTypeDtoListForVolunteerEditDto(Set<MissionType> missionTypes) {
        return missionTypes.stream()
                .map(this::mapToMissionTypeDtoForVolunteerEditDto)
                .collect(Collectors.toList());
    }

    public Set<MissionType> mapToMissionTypeEntitySetFromDtoList(List<MissionTypeDTO> missionTypeDTOs) {
        return missionTypeDTOs.stream()
                .map(this::mapToMissionTypeEntity)
                .collect(Collectors.toSet());
    }

}
