package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.MissionTypeDTO;
import com.mickc0.gtac.dto.MissionTypeSelectionDTO;
import com.mickc0.gtac.entity.MissionType;
import com.mickc0.gtac.exception.CustomDuplicateEntryException;
import com.mickc0.gtac.mapper.MissionTypeMapper;
import com.mickc0.gtac.repository.MissionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
    public void save(MissionTypeDTO missionTypeDTO) throws CustomDuplicateEntryException {
        MissionType missionType;
        if(missionTypeDTO.getUuid() == null){
            missionType = missionTypeMapper.mapToNewMissionTypeEntity(missionTypeDTO);
        } else {
            missionType = missionTypeMapper.mapToMissionTypeEntity(missionTypeDTO);
        }
        String normalizedNewName = normalizeString(missionType.getName());

        Optional<MissionType> existingMissionType = missionTypeRepository
                .findAll()
                .stream()
                .filter(mt -> mt.getId() != null && !mt.getId().equals(missionType.getId()))
                .filter(mt -> normalizeString(mt.getName()).equalsIgnoreCase(normalizedNewName))
                .findFirst();

        if (existingMissionType.isPresent()) {
            throw new CustomDuplicateEntryException("Un type de mission avec le nom '" + missionType.getName() + "' existe déjà.");
        }

        missionTypeRepository.save(missionType);
    }



    private String normalizeString(String input) {
        if (input == null) return null;
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();
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
    public Optional<MissionTypeDTO> findByUuid(UUID uuid) {
        return Optional.ofNullable(missionTypeMapper.mapToMissionTypeDto(missionTypeRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Invalid mission type Id:" + uuid))));
    }
}
