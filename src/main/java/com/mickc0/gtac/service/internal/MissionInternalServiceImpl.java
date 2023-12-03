package com.mickc0.gtac.service.internal;

import com.mickc0.gtac.service.MissionService;
import com.mickc0.gtac.service.MissionTypeService;
import com.mickc0.gtac.service.MissionAssignmentService;
import com.mickc0.gtac.service.VolunteerService;
import org.springframework.stereotype.Service;

@Service
public class MissionInternalServiceImpl implements MissionInternalService{

    //TODO On ne met que des services
    private final MissionService missionService;
    private final MissionAssignmentService missionAssignmentService;
    private final VolunteerService volunteerService;
    private final MissionTypeService missionTypeService;

    public MissionInternalServiceImpl(MissionService missionService, MissionAssignmentService missionAssignmentService, VolunteerService volunteerService, MissionTypeService missionTypeService) {
        this.missionService = missionService;
        this.missionAssignmentService = missionAssignmentService;
        this.volunteerService = volunteerService;
        this.missionTypeService = missionTypeService;
    }


    //TODO implémenter la méthode getMissionTypes
    // on ne travail qu'avec les DTO !!!

    //ici on organise la "persistence" des données (orchestration)



}
