package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.MissionDTO;
import com.mickc0.gtac.entity.*;
import com.mickc0.gtac.mapper.MissionMapper;
import com.mickc0.gtac.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MissionServiceImpl implements MissionService {

    private final MissionRepository missionRepository;
    private final MissionMapper missionMapper;
    private final MissionTypeService missionTypeService;
    private final MissionAssignmentRepository missionAssignmentRepository;

    @Autowired
    public MissionServiceImpl(MissionRepository missionRepository, MissionMapper missionMapper, MissionTypeService missionTypeService, MissionAssignmentRepository missionAssignmentRepository) {
        this.missionRepository = missionRepository;
        this.missionMapper = missionMapper;
        this.missionTypeService = missionTypeService;
        this.missionAssignmentRepository = missionAssignmentRepository;
    }

    @Override
    @Transactional
    public void save(MissionDTO missionDTO) {
        Mission mission = getMission(missionDTO);
        setMissionBaseAttributes(missionDTO, mission);

        mission.setStatus(missionDTO.getStatus() != null ? missionDTO.getStatus() : MissionStatus.NEW);
        missionRepository.save(mission);
    }

    @Override
    public void planMission(MissionDTO missionDTO) {
        Mission mission = getMission(missionDTO);
        setMissionBaseAttributes(missionDTO, mission);

        if (missionDTO.getStartDateTime() != null && missionDTO.getEndDateTime() != null) {
            mission.setStartDateTime(missionDTO.getStartDateTime());
            mission.setEndDateTime(missionDTO.getEndDateTime());
            mission.setStatus(MissionStatus.PLANNED);
            missionRepository.save(mission);
        } else {
            mission.setStartDateTime(null);
            mission.setEndDateTime(null);
            mission.setStatus(MissionStatus.NEW);
            missionRepository.save(mission);
        }
    }

    @Override
    public void confirmMission(MissionDTO missionDTO) {
        Mission mission = getMission(missionDTO);
        setMissionBaseAttributes(missionDTO, mission);
        mission.setStatus(MissionStatus.CONFIRMED);
        missionRepository.save(mission);
    }

    private void setMissionBaseAttributes(MissionDTO missionDTO, Mission mission) {
        mission.setTitle(missionDTO.getTitle());
        mission.setDescription(missionDTO.getDescription());
        mission.setComment(missionDTO.getComment());
        mission.setMissionType(missionTypeService.findMissionTypeByUuid(missionDTO.getMissionType().getUuid())
                .orElseThrow(() -> new EntityNotFoundException("Le type de mission avec l'Id : "+ missionDTO.getMissionType().getUuid() + " n'existe pas.")));
        mission.setRequiredVolunteerNumber(missionDTO.getRequiredVolunteerNumber());
    }
    private Mission getMission(MissionDTO missionDTO) {
        Mission mission;
        if(missionDTO.getUuid() != null && missionRepository.findByUuid(missionDTO.getUuid()).isPresent()){
            mission = missionRepository.findByUuid(missionDTO.getUuid())
                    .orElseThrow(() -> new EntityNotFoundException("La mission avec l'Id : "+ missionDTO.getUuid() + " n'existe pas."));
        } else {
            mission = new Mission();
        }
        return mission;
    }

    @Override
    public List<MissionDTO> findAll() {
        return missionRepository.findAll().stream()
                .map(missionMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        missionRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByUuid(UUID uuid) {
        missionRepository.deleteByUuid(uuid);
    }

    @Override
    public Optional<Mission> findById(Long id) {
        return missionRepository.findById(id);
    }

    @Override
    public Optional<MissionDTO> findByUuid(UUID uuid) {
        return Optional.ofNullable(missionMapper.mapToDTO(missionRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("La mission avec l'Id: " + uuid + " n'existe pas"))));
    }

    @Override
    public Optional<Mission> findMissionByUuid(UUID uuid) {
        return Optional.ofNullable(missionRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("La mission avec l'Id: " + uuid + " n'existe pas")));
    }

    public List<MissionDTO> findByStatus(MissionStatus status) {
        return missionRepository.findByStatus(status).stream()
                .map(missionMapper::mapToDTO)
                .collect(Collectors.toList());
    }



    @Override
    @Transactional
    public void launchMission(UUID uuid) {
        Mission mission = missionRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("La mission avec l'id : " + uuid + " n'existe pas."));
        mission.setStatus(MissionStatus.ONGOING);
        missionRepository.save(mission);
    }

    @Override
    public void endMission(UUID uuid) {
        Mission mission = missionRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("La mission avec l'id : " + uuid + " n'existe pas."));
        mission.setStatus(MissionStatus.COMPLETED);
        missionRepository.save(mission);
    }

    @Override
    @Transactional
    public void cancelMission(UUID uuid) {
        Mission mission = missionRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("La mission avec l'id : " + uuid + " n'existe pas."));
        mission.setStatus(MissionStatus.CANCELLED);
        missionRepository.save(mission);
    }

    @Override
    public List<Mission> findMissionsToUpdateStatus(LocalDateTime now) {

        List<Mission> missionsToStart = missionRepository.findByStatusAndStartDateTimeLessThanEqual(MissionStatus.PLANNED, now);
        List<Mission> missionsToUpdate = new ArrayList<>(missionsToStart);

        List<Mission> missionsToEnd = missionRepository.findByStatusAndEndDateTimeLessThanEqual(MissionStatus.ONGOING, now);
        missionsToUpdate.addAll(missionsToEnd);

        return missionsToUpdate;
    }

    @Override
    @Transactional
    public void updateMissionStatus(Long missionId, LocalDateTime now) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new RuntimeException("Mission not found"));

        if (now.isEqual(mission.getStartDateTime()) || now.isAfter(mission.getStartDateTime()) && now.isBefore(mission.getEndDateTime())) {
            mission.setStatus(MissionStatus.ONGOING);
        } else if (now.isEqual(mission.getEndDateTime()) || now.isAfter(mission.getEndDateTime())) {
            mission.setStatus(MissionStatus.COMPLETED);
            releaseUsersFromMission(missionId);
        }

        missionRepository.save(mission);
    }



    @Override
    public void saveMission(Mission mission) {
        missionRepository.save(mission);
    }

    @Override
    @Transactional
    public void completeMissionReport(UUID uuid) {
        Mission mission = missionRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("La mission avec l'id : " + uuid + " n'existe pas."));
        mission.setReportDone(true);
        missionRepository.save(mission);
    }


    private void releaseUsersFromMission(Long missionId) {
        List<MissionAssignment> assignments = missionAssignmentRepository.findByMissionId(missionId);
        missionAssignmentRepository.deleteAll(assignments);
    }


}
