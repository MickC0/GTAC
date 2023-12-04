package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.MissionTypeDTO;
import com.mickc0.gtac.entity.MissionType;
import com.mickc0.gtac.entity.Volunteer;
import com.mickc0.gtac.exception.CustomDuplicateEntryException;
import com.mickc0.gtac.mapper.MissionTypeMapper;
import com.mickc0.gtac.repository.MissionTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
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
        if (missionTypeDTO.getUuid() != null && missionTypeRepository.findByUuid(missionTypeDTO.getUuid()).isPresent()) {
            missionType = missionTypeRepository.findByUuid(missionTypeDTO.getUuid())
                    .orElseThrow(() -> new EntityNotFoundException("Le type de mission avec l'Id: " + missionTypeDTO.getUuid() + " n'existe pas"));
        } else {
            missionType = new MissionType();
        }
        missionType.setName(missionTypeDTO.getName());
        missionType.setDescription(missionTypeDTO.getDescription());

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
    public List<MissionTypeDTO> findAllDto() {
        List<MissionType> missionTypes = missionTypeRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        return missionTypes.stream()
                .map(missionTypeMapper::mapToMissionTypeDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteByUuid(UUID uuid) {
        missionTypeRepository.deleteByUuid(uuid);
    }

    @Override
    public Optional<MissionTypeDTO> findMissionTypeDTOByUuid(UUID uuid) {
        return Optional.ofNullable(missionTypeMapper.mapToMissionTypeDto(missionTypeRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Invalid mission type Id:" + uuid))));
    }

    @Override
    public Optional<MissionType> findMissionTypeByUuid(UUID uuid) {
        return Optional.ofNullable(missionTypeRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Invalid mission type Id:" + uuid)));
    }

    @Override
    public List<MissionTypeDTO> findAllActive() {
        List<MissionType> missionTypes = missionTypeRepository.findAllActive(Sort.by(Sort.Direction.ASC, "name"));
        return missionTypes.stream()
                .map(missionTypeMapper::mapToMissionTypeDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deactivateByUuid(UUID uuid) {
        MissionType missionType = missionTypeRepository.findByUuid(uuid)
                    .orElseThrow(() -> new EntityNotFoundException("Le type de mission avec l'Id: " + uuid + " n'existe pas"));
        missionType.setActive(false);
        missionTypeRepository.save(missionType);
    }

    @Override
    public void activateByUuid(UUID uuid) {
        MissionType missionType = missionTypeRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Le type de mission avec l'Id: " + uuid + " n'existe pas"));
        missionType.setActive(true);
        missionTypeRepository.save(missionType);
    }

}
