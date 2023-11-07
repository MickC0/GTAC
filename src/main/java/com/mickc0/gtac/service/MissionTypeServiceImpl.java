package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.MissionTypeDTO;
import com.mickc0.gtac.mapper.MissionTypeMapper;
import com.mickc0.gtac.model.MissionType;
import com.mickc0.gtac.repository.MissionTypeRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class MissionTypeServiceImpl implements MissionTypeService{
    private final MissionTypeRepository missionTypeRepository;

    private final MissionTypeMapper missionTypeMapper;

    public MissionTypeServiceImpl(MissionTypeRepository missionTypeRepository, MissionTypeMapper missionTypeMapper) {
        this.missionTypeRepository = missionTypeRepository;
        this.missionTypeMapper = missionTypeMapper;
    }


    @Override
    public List<MissionTypeDTO> findAll() {
        List<MissionType> missionTypes = missionTypeRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        return missionTypes.stream()
                .map(missionTypeMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void save(MissionTypeDTO missionTypeDTO) {
        MissionType missionType = missionTypeMapper.mapToEntity(missionTypeDTO);
        missionType.setUuid(UUID.randomUUID());
        missionTypeRepository.save(missionType);
    }

    @Override
    public void update(MissionTypeDTO missionTypeDTO) {
        MissionType existingMissionType = missionTypeRepository.findMissionTypeByUuid(missionTypeDTO.getUuid());
        MissionType missionType = missionTypeMapper.mapToEntity(missionTypeDTO);
        missionType.setId(existingMissionType.getId());
        missionTypeRepository.save(missionType);
    }

    @Override
    public MissionTypeDTO findMissionTypeByUuid(UUID uuid) {
        return missionTypeMapper.mapToDto(missionTypeRepository.findMissionTypeByUuid(uuid));
    }

    @Override
    public void deleteMissionType(UUID uuid) {
        missionTypeRepository.deleteByUuid(uuid);
    }

    @Override
    public List<MissionTypeDTO> searchMissionTypes(String query) {
        List<MissionType> missionTypes = missionTypeRepository.findAllByNameContainsIgnoreCase(query);
        return missionTypes.stream()
                .map(missionTypeMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
