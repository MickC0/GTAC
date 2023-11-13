package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.MissionDTO;
import com.mickc0.gtac.mapper.MissionMapper;
import com.mickc0.gtac.model.Mission;
import com.mickc0.gtac.model.MissionStatus;
import com.mickc0.gtac.repository.MissionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    public void saveNewMission(MissionDTO missionDTO) {
        Mission mission = missionMapper.mapToEntityWithoutId(missionDTO);
        mission.setUuid(UUID.randomUUID());
        if (mission.getStartingDate() != null && mission.getEndingDate() != null){
            mission.setStatus(MissionStatus.PLANNED);
        } else {
            mission.setStatus(MissionStatus.NEW);
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


    @Scheduled(cron = "0 0 */1 * * *") // Exécute toutes les heures
    public void updateMissionStatusScheduled() {
        List<Mission> missions = missionRepository.findAll();
        for (Mission mission : missions) {
            updateMissionStatus(mission);
        }
    }


    private void updateMissionStatus(Mission mission) {
        LocalDateTime currentDate = LocalDateTime.now();

        // Cas 1
        if (mission.getStatus() == null) {
            mission.setStatus(MissionStatus.NEW);
        }

        // Cas 2
        if (mission.getStartingDate() != null && mission.getEndingDate() != null && mission.getMissionVolunteers().isEmpty()) {
            mission.setStatus(MissionStatus.PLANNED);
        }

        // Cas 3
        if ((mission.getStartingDate() == null && mission.getEndingDate() != null) || (mission.getStartingDate() != null && mission.getEndingDate() == null)) {
            mission.setStatus(MissionStatus.NEW);
        }

        // Cas 4
        if (mission.getStartingDate() == null && mission.getEndingDate() == null && !mission.getMissionVolunteers().isEmpty()) {
            mission.setStatus(MissionStatus.NEW);
        }

        // Cas 5
        if (mission.getStartingDate() != null && mission.getEndingDate() != null && !mission.getMissionVolunteers().isEmpty()) {
            mission.setStatus(MissionStatus.VALID);
        }

        // Cas 6
        if ((mission.getStartingDate() == null || mission.getEndingDate() == null) && !mission.getMissionVolunteers().isEmpty()) {
            mission.setStatus(MissionStatus.NEW);
        }

        // Cas 7
        if (mission.getStartingDate() != null && mission.getStartingDate().isEqual(currentDate) && mission.getEndingDate() != null && !mission.getMissionVolunteers().isEmpty()) {
            mission.setStatus(MissionStatus.ONGOING);
        }
        missionRepository.save(mission);
    }

    private boolean isUUIDPresent(UUID uuid) {
        return uuid != null && !uuid.equals(UUID.fromString("00000000-0000-0000-0000-000000000000"));
    }


}
