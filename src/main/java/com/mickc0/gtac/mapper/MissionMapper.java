package com.mickc0.gtac.mapper;

import com.mickc0.gtac.dto.MissionDTO;
import com.mickc0.gtac.entity.Mission;
import com.mickc0.gtac.entity.Volunteer;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MissionMapper {
    private final MissionTypeMapper missionTypeMapper;

    public MissionMapper(MissionTypeMapper missionTypeMapper) {
        this.missionTypeMapper = missionTypeMapper;
    }

    public MissionDTO mapToDTO(Mission mission){
        MissionDTO missionDTO = new MissionDTO();
        missionDTO.setUuid(mission.getUuid());
        missionDTO.setTitle(mission.getTitle());
        missionDTO.setDescription(mission.getDescription());
        missionDTO.setComment(mission.getComment());
        missionDTO.setStatus(mission.getStatus());
        missionDTO.setRequiredVolunteerNumber(mission.getRequiredVolunteerNumber());
        missionDTO.setStartDateTime(mission.getStartDateTime());
        missionDTO.setEndDateTime(mission.getEndDateTime());
        missionDTO.setMissionType(missionTypeMapper.mapToMissionTypeDto(mission.getMissionType()));
        return missionDTO;
    }
}
