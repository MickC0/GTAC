package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.MissionDTO;
import com.mickc0.gtac.dto.MissionTypeDTO;
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

    //TODO on ne met que un repo et mapper
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
                .map(missionMapper::mapToDtoWithoutId)
                .collect(Collectors.toList());
    }


    @Override
    public void saveMission(MissionDTO missionDTO) {
        Mission mission = missionMapper.mapToEntityWithoutId(missionDTO);
        mission.setUuid(UUID.randomUUID());
        if (missionDTO.getStartingDate() == null && mission.getEndingDate() == null){
            mission.setStatus(MissionStatus.NEW);
        } else {
            mission.setStatus(MissionStatus.PLANNED);
        }
        missionRepository.save(mission);
    }

    @Override
    public void updateMission(MissionDTO missionDTO) {
        Mission existingMission = missionRepository.findMissionByUuid(missionDTO.getUuid()).orElseThrow(
                () -> new RuntimeException("Le type de mission avec uuid : " + missionDTO.getUuid() + " n'existe pas.")
        );
        Mission mission = missionMapper.mapToEntityWithoutId(missionDTO);
        mission.setId(existingMission.getId());
        missionRepository.save(mission);
    }

    @Override
    public MissionDTO findMissionByUUID(UUID uuid) {
        return missionMapper.mapToDtoWithoutId(missionRepository.findMissionByUuid(uuid).orElseThrow(
                () -> new RuntimeException("Le type de mission avec uuid : " + uuid + " n'existe pas."))
        );
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
                .map(missionMapper::mapToDtoWithoutId)
                .collect(Collectors.toList());
    }




    private boolean isUUIDPresent(UUID uuid) {
        return uuid != null && !uuid.equals(UUID.fromString("00000000-0000-0000-0000-000000000000"));
    }


}
