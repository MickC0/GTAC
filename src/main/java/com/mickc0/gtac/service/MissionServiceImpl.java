package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.MissionDTO;
import com.mickc0.gtac.mapper.MissionMapper;
import com.mickc0.gtac.model.Mission;
import com.mickc0.gtac.model.MissionStatus;
import com.mickc0.gtac.repository.MissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class MissionServiceImpl implements MissionService {
    private final MissionRepository missionRepository;
    private final MissionMapper missionMapper;

    public MissionServiceImpl(MissionRepository missionRepository, MissionMapper missionMapper) {
        this.missionRepository = missionRepository;
        this.missionMapper = missionMapper;
    }

    @Override
    public List<MissionDTO> findAll() {
        List<Mission> missions = missionRepository.findAll();
        return missions.stream()
                .map(missionMapper::mapToDto)
                .collect(Collectors.toList());
    }


    @Override
    public void saveMission(MissionDTO missionDTO) {
        Mission mission = missionMapper.mapToEntity(missionDTO);
        mission.setUuid(UUID.randomUUID());
        mission.setStatus(MissionStatus.NEW);
        missionRepository.save(mission);
    }

    @Override
    public void updateMission(MissionDTO missionDTO) {
        Mission existingMission = missionRepository.findMissionByUuid(missionDTO.getUuid());
        Mission mission = missionMapper.mapToEntity(missionDTO);
        mission.setId(existingMission.getId());
        missionRepository.save(mission);
    }

    @Override
    public MissionDTO findMissionByUUID(UUID uuid) {
        return missionMapper.mapToDto(missionRepository.findMissionByUuid(uuid));
    }

    @Override
    public void deleteMission(UUID uuid) {
        if (isUUIDPresent(uuid)){
            missionRepository.deleteByUuid(uuid);
        } else {
            throw new RuntimeException("La mission ne peut être supprimée id : " + uuid + " non conforme");
        }
    }

    @Override
    public List<MissionDTO> searchMissions(String query) {
        List<Mission> missions = missionRepository.searchAllByNameContainingIgnoreCase(query);

        return missions.stream()
                .map(missionMapper::mapToDto)
                .collect(Collectors.toList());
    }


    private boolean isUUIDPresent(UUID uuid) {
        return uuid != null && !uuid.equals(UUID.fromString("00000000-0000-0000-0000-000000000000"));
    }


}
