package com.mickc0.gtac.mapper;

import com.mickc0.gtac.dto.MissionDTO;
import com.mickc0.gtac.model.Mission;
import org.springframework.stereotype.Component;

@Component
public class MissionMapper {

    //TODO faire les méthodes pour récupérer le DTO complet et le DTO dans le front
    public MissionDTO mapToFullDto(Mission mission) {
        MissionDTO missionDTO = new MissionDTO();
        missionDTO.setId(mission.getId());
        missionDTO.setUuid(mission.getUuid());
        missionDTO.setName(mission.getName());
        missionDTO.setDescription(mission.getDescription());
        missionDTO.setComment(mission.getComment());
        missionDTO.setMissionType(mission.getMissionType());
        missionDTO.setStatus(mission.getStatus());
        missionDTO.setRequiredVolunteerNumber(mission.getRequiredVolunteerNumber());
        missionDTO.setStartingDate(mission.getStartingDate());
        missionDTO.setEndingDate(mission.getEndingDate());
        return missionDTO;
    }
    public MissionDTO mapToDtoWithoutId(Mission mission) {
        MissionDTO missionDTO = new MissionDTO();
        missionDTO.setUuid(mission.getUuid());
        missionDTO.setName(mission.getName());
        missionDTO.setDescription(mission.getDescription());
        missionDTO.setComment(mission.getComment());
        missionDTO.setMissionType(mission.getMissionType());
        missionDTO.setStatus(mission.getStatus());
        missionDTO.setRequiredVolunteerNumber(mission.getRequiredVolunteerNumber());
        missionDTO.setStartingDate(mission.getStartingDate());
        missionDTO.setEndingDate(mission.getEndingDate());
        return missionDTO;
    }


    public Mission mapToFullEntity(MissionDTO missionDTO){
        Mission mission = new Mission();
        mission.setId(missionDTO.getId());
        mission.setUuid(missionDTO.getUuid());
        mission.setName(missionDTO.getName());
        mission.setDescription(missionDTO.getDescription());
        mission.setComment(missionDTO.getComment());
        mission.setMissionType(missionDTO.getMissionType());
        mission.setStatus(missionDTO.getStatus());
        mission.setRequiredVolunteerNumber(missionDTO.getRequiredVolunteerNumber());
        mission.setStartingDate(missionDTO.getStartingDate());
        mission.setEndingDate(missionDTO.getEndingDate());
        return mission;
    }
    public Mission mapToEntityWithoutId(MissionDTO missionDTO){
        Mission mission = new Mission();
        mission.setUuid(missionDTO.getUuid());
        mission.setName(missionDTO.getName());
        mission.setDescription(missionDTO.getDescription());
        mission.setComment(missionDTO.getComment());
        mission.setMissionType(missionDTO.getMissionType());
        mission.setStatus(missionDTO.getStatus());
        mission.setRequiredVolunteerNumber(missionDTO.getRequiredVolunteerNumber());
        mission.setStartingDate(missionDTO.getStartingDate());
        mission.setEndingDate(missionDTO.getEndingDate());
        return mission;
    }

}


