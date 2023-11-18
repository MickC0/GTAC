package com.mickc0.gtac.service;

import com.mickc0.gtac.repository.MissionAssignmentRepository;
import org.springframework.stereotype.Service;

@Service
public class MissionVolunteerServiceImpl implements MissionVolunteerService{
    private final MissionAssignmentRepository missionAssignmentRepository;

    public MissionVolunteerServiceImpl(MissionAssignmentRepository missionAssignmentRepository) {
        this.missionAssignmentRepository = missionAssignmentRepository;
    }

}
