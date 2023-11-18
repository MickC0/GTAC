package com.mickc0.gtac.service;

import com.mickc0.gtac.entity.MissionType;
import com.mickc0.gtac.repository.MissionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MissionTypeServiceImpl implements MissionTypeService{

    private final MissionTypeRepository missionTypeRepository;

    @Autowired
    public MissionTypeServiceImpl(MissionTypeRepository missionTypeRepository) {
        this.missionTypeRepository = missionTypeRepository;
    }

    @Override
    @Transactional
    public void save(MissionType missionType) {
        missionTypeRepository.save(missionType);
    }

    @Override
    public List<MissionType> findAll() {
        return missionTypeRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        missionTypeRepository.deleteById(id);
    }

    @Override
    public Optional<MissionType> findById(Long id) {
        return missionTypeRepository.findById(id);
    }
}
