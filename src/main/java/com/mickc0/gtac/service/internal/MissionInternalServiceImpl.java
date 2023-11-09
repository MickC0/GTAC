package com.mickc0.gtac.service.internal;

import com.mickc0.gtac.dto.MissionTypeDTO;
import com.mickc0.gtac.model.MissionType;
import com.mickc0.gtac.service.MissionService;
import com.mickc0.gtac.service.MissionTypeService;
import com.mickc0.gtac.service.MissionVolunteerService;
import com.mickc0.gtac.service.VolunteerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MissionInternalServiceImpl implements MissionInternalService{

    //TODO On ne met que des services
    private final MissionService missionService;
    private final MissionVolunteerService missionVolunteerService;
    private final VolunteerService volunteerService;
    private final MissionTypeService missionTypeService;

    public MissionInternalServiceImpl(MissionService missionService, MissionVolunteerService missionVolunteerService, VolunteerService volunteerService, MissionTypeService missionTypeService) {
        this.missionService = missionService;
        this.missionVolunteerService = missionVolunteerService;
        this.volunteerService = volunteerService;
        this.missionTypeService = missionTypeService;
    }

    //TODO implémenter la méthode getMissionTypes
    // on ne travail qu'avec les DTO !!!

    //ici on organise la "persistence" des données (orchestration)


}
