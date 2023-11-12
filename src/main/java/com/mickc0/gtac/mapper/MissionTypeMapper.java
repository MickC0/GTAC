package com.mickc0.gtac.mapper;

import com.mickc0.gtac.dto.MissionTypeDTO;
import com.mickc0.gtac.model.MissionType;
import org.springframework.stereotype.Component;

@Component
public class MissionTypeMapper {


    public MissionTypeDTO mapToFullDto(MissionType missionType){
        MissionTypeDTO missionTypeWithoutIdDTO = new MissionTypeDTO();
        missionTypeWithoutIdDTO.setId(missionType.getId());
        missionTypeWithoutIdDTO.setUuid(missionType.getUuid());
        missionTypeWithoutIdDTO.setName(missionType.getName());
        return missionTypeWithoutIdDTO;
    }
    public MissionTypeDTO mapToDtoWithoutId(MissionType missionType){
        MissionTypeDTO missionTypeWithoutIdDTO = new MissionTypeDTO();
        missionTypeWithoutIdDTO.setUuid(missionType.getUuid());
        missionTypeWithoutIdDTO.setName(missionType.getName());
        return missionTypeWithoutIdDTO;
    }
    public MissionType mapToFullEntity(MissionTypeDTO missionTypeDTO){
        MissionType missionType = new MissionType();
        missionType.setId(missionTypeDTO.getId());
        missionType.setUuid(missionTypeDTO.getUuid());
        missionType.setName(missionTypeDTO.getName());
        return missionType;
    }
    public MissionType mapToEntityWithoutId(MissionTypeDTO missionTypeWithoutIdDTO){
        MissionType missionType = new MissionType();
        missionType.setUuid(missionTypeWithoutIdDTO.getUuid());
        missionType.setName(missionTypeWithoutIdDTO.getName());
        return missionType;
    }

}
