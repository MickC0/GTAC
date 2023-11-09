package com.mickc0.gtac.mapper;

import com.mickc0.gtac.dto.MissionTypeDTO;
import com.mickc0.gtac.model.MissionType;
import org.springframework.stereotype.Component;

@Component
public class MissionTypeMapper {


    public MissionTypeDTO mapToFullDto(MissionType missionType){
        MissionTypeDTO missionTypeDTO = new MissionTypeDTO();
        missionTypeDTO.setId(missionType.getId());
        missionTypeDTO.setUuid(missionType.getUuid());
        missionTypeDTO.setName(missionType.getName());
        return missionTypeDTO;
    }
    public MissionTypeDTO mapToDtoWithoutId(MissionType missionType){
        MissionTypeDTO missionTypeDTO = new MissionTypeDTO();
        missionTypeDTO.setUuid(missionType.getUuid());
        missionTypeDTO.setName(missionType.getName());
        return missionTypeDTO;
    }
    public MissionType mapToFullEntity(MissionTypeDTO missionTypeDTO){
        MissionType missionType = new MissionType();
        missionType.setId(missionTypeDTO.getId());
        missionType.setUuid(missionTypeDTO.getUuid());
        missionType.setName(missionTypeDTO.getName());
        return missionType;
    }
    public MissionType mapToEntityWithoutId(MissionTypeDTO missionTypeDTO){
        MissionType missionType = new MissionType();
        missionType.setUuid(missionTypeDTO.getUuid());
        missionType.setName(missionTypeDTO.getName());
        return missionType;
    }
}
