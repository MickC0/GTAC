package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.MissionTypeDTO;
import com.mickc0.gtac.dto.MissionTypeSelectionDTO;
import com.mickc0.gtac.entity.MissionType;
import com.mickc0.gtac.mapper.MissionTypeMapper;
import com.mickc0.gtac.repository.MissionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MissionTypeServiceImpl implements MissionTypeService{

    private final MissionTypeRepository missionTypeRepository;

    private final MissionTypeMapper missionTypeMapper;

    @Autowired
    public MissionTypeServiceImpl(MissionTypeRepository missionTypeRepository, MissionTypeMapper missionTypeMapper) {
        this.missionTypeRepository = missionTypeRepository;
        this.missionTypeMapper = missionTypeMapper;
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
    public List<MissionType> findAllById(List<Long> ids) {
        return missionTypeRepository.findAllById(ids);
    }

    @Override
    public List<MissionTypeDTO> getAll() {
        List<MissionType> missionTypes = missionTypeRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        return missionTypes.stream()
                .map(missionTypeMapper::mapToMissionTypeDto)
                .collect(Collectors.toList());
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
