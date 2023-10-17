package com.mickc0.gtac.service.internal;

import com.mickc0.gtac.service.MissionService;
import com.mickc0.gtac.service.MissionVolunteerService;
import com.mickc0.gtac.service.VolunteerService;
import org.springframework.stereotype.Service;

@Service
public class MissionInternalServiceImpl implements MissionInternalService{

    private final MissionService missionService;
    private final MissionVolunteerService missionVolunteerService;
    private final VolunteerService volunteerService;

    public MissionInternalServiceImpl(MissionService missionService, MissionVolunteerService missionVolunteerService, VolunteerService volunteerService) {
        this.missionService = missionService;
        this.missionVolunteerService = missionVolunteerService;
        this.volunteerService = volunteerService;
    }

    //ici on organise la "persistence" des données (orchestration)
}
