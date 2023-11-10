package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.MissionDTO;
import com.mickc0.gtac.dto.MissionTypeDTO;
import com.mickc0.gtac.mapper.MissionTypeMapper;
import com.mickc0.gtac.model.MissionType;
import com.mickc0.gtac.repository.MissionTypeRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
                .map(missionTypeMapper::mapToFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MissionTypeDTO> findAllOnlyName() {
        List<MissionType> missionTypes = missionTypeRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        return missionTypes.stream()
                .map(missionTypeMapper::mapToDtoOnlyName)
                .collect(Collectors.toList());
    }

    @Override
    public void save(MissionTypeDTO missionTypeDTO) {
        MissionType missionType = missionTypeMapper.mapToEntityWithoutId(missionTypeDTO);
        missionType.setUuid(UUID.randomUUID());
        missionTypeRepository.save(missionType);
    }

    @Override
    public void update(MissionTypeDTO missionTypeDTO) {
        //On récupère l'objet complet en base
        MissionType existingMissionType = missionTypeRepository.findByUuid(missionTypeDTO.getUuid()).orElseThrow(
                () -> new RuntimeException("Le type de mission avec uuid : " + missionTypeDTO.getUuid() + " n'existe pas.")
        );
        //On transforme le DTO qui vient du front en entity sans id
        MissionType missionType = missionTypeMapper.mapToEntityWithoutId(missionTypeDTO);
        //On set l'id dans l'objet à enregistrer.
        missionType.setId(existingMissionType.getId());
        missionTypeRepository.save(missionType);
    }

    @Override
    public MissionTypeDTO findMByUuid(UUID uuid) {
        return missionTypeMapper.mapToDtoWithoutId(missionTypeRepository.findByUuid(uuid).orElseThrow(
                () -> new RuntimeException("Le type de mission avec uuid : " + uuid + " n'existe pas."))
        );
    }

    @Override
    public MissionTypeDTO findByName(String name) {
        System.out.println(name);
        return missionTypeMapper.mapToFullDto(missionTypeRepository.findByName(name).orElseThrow(
                ()-> new RuntimeException("Ce type de mission n'existe pas"))
        );
    }

    @Override
    public void deleteMissionType(UUID uuid) {
        missionTypeRepository.deleteByUuid(uuid);
    }

    @Override
    public List<MissionTypeDTO> searchMissionTypes(String query) {
        List<MissionType> missionTypes = missionTypeRepository.findAllByNameContainsIgnoreCase(query);
        return missionTypes.stream()
                .map(missionTypeMapper::mapToDtoWithoutId)
                .collect(Collectors.toList());
    }
}
