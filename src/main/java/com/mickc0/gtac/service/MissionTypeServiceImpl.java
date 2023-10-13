package com.mickc0.gtac.service;

import com.mickc0.gtac.model.MissionType;
import com.mickc0.gtac.repository.MissionTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class MissionTypeServiceImpl implements MissionTypeService{
    private final MissionTypeRepository missionTypeRepository;

    public MissionTypeServiceImpl(MissionTypeRepository missionTypeRepository) {
        this.missionTypeRepository = missionTypeRepository;
    }


    @Override
    public void save(MissionType missionType) {
        missionTypeRepository.save(missionType);
    }
}
