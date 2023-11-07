package com.mickc0.gtac.mapper;

import com.mickc0.gtac.dto.MissionTypeDTO;
import com.mickc0.gtac.model.MissionType;
import org.springframework.stereotype.Component;

@Component
public class MissionTypeMapper {


    public MissionTypeDTO mapToDto(MissionType missionType){
        MissionTypeDTO missionTypeDTO = new MissionTypeDTO();
        missionTypeDTO.setUuid(missionType.getUuid());
        missionTypeDTO.setName(missionType.getName());
        return missionTypeDTO;
    }

    public MissionType mapToEntity(MissionTypeDTO missionTypeDTO){
        MissionType missionType = new MissionType();
        missionType.setUuid(missionTypeDTO.getUuid());
        missionType.setName(missionTypeDTO.getName());
        return missionType;
    }
}
