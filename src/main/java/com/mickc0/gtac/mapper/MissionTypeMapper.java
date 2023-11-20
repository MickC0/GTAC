package com.mickc0.gtac.mapper;

import com.mickc0.gtac.dto.MissionTypeDTO;
import com.mickc0.gtac.dto.MissionTypeSelectionDTO;
import com.mickc0.gtac.entity.MissionType;
import org.springframework.stereotype.Component;

@Component
public class MissionTypeMapper {

    public MissionTypeDTO mapToMissionTypeDto(MissionType missionType){
        MissionTypeDTO missionTypeDTO = new MissionTypeDTO();
        missionTypeDTO.setId(missionType.getId());
        missionTypeDTO.setName(missionType.getName());
        missionTypeDTO.setSelected(false);
        return missionTypeDTO;
    }
}
