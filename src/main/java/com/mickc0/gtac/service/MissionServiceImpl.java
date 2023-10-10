package com.mickc0.gtac.service;

import com.mickc0.gtac.model.Mission;
import com.mickc0.gtac.repository.MissionRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MissionServiceImpl implements MissionService {
    private final MissionRepository missionRepository;
    @Override
    public List<Mission> findAll() {
        return missionRepository.findAll();
    }

    @Override
    public void save(Mission mission) {
        missionRepository.save(mission);
    }

}
