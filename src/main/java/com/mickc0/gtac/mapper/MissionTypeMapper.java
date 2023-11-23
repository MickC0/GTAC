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
        missionTypeDTO.setId(missionType.getId());
        missionTypeDTO.setUuid(missionType.getUuid());
        missionTypeDTO.setName(missionType.getName());
        missionTypeDTO.setSelected(false);
        return missionTypeDTO;
    }

    public MissionType mapToMissionTypeEntity(MissionTypeDTO missionTypeDTO){
        MissionType missionType = new MissionType();
        missionTypeDTO.setId(missionType.getId());
        missionTypeDTO.setUuid(missionType.getUuid());
        missionTypeDTO.setName(missionType.getName());
        return missionType;
    }

    public MissionTypeDTO mapToMissionTypeDtoForVolunteerEditDto(MissionType missionType){
        MissionTypeDTO missionTypeDTO = new MissionTypeDTO();
        missionTypeDTO.setId(missionType.getId());
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

}
