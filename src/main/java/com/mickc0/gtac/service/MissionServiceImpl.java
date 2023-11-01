package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.MissionDTO;
import com.mickc0.gtac.model.Mission;
import com.mickc0.gtac.repository.MissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MissionServiceImpl implements MissionService {
    private final MissionRepository missionRepository;

    public MissionServiceImpl(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
    }

    @Override
    public List<Mission> findAll() {
        return missionRepository.findAll();
    }

    @Override
    public void save(MissionDTO missionDTO) {
        missionRepository.save(missionDTO.toEntity());
    }

}
