package com.mickc0.gtac.mapper;

import com.mickc0.gtac.dto.MissionTypeDTO;
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


    public List<MissionTypeDTO> mapToMissionTypeDtoListForVolunteerDto(Set<MissionType> missionTypes) {
        return missionTypes.stream().map(this::mapToMissionTypeDto).collect(Collectors.toList());
    }
}
