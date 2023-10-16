package com.mickc0.gtac.service;

import com.mickc0.gtac.model.Volunteer;
import com.mickc0.gtac.repository.MissionVolunteerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MissionVolunteerServiceImpl implements MissionVolunteerService{
    private final MissionVolunteerRepository missionVolunteerRepository;

    public MissionVolunteerServiceImpl(MissionVolunteerRepository missionVolunteerRepository) {
        this.missionVolunteerRepository = missionVolunteerRepository;
    }

}
