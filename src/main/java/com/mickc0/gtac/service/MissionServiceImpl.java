package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.MissionDTO;
import com.mickc0.gtac.entity.*;
import com.mickc0.gtac.mapper.MissionMapper;
import com.mickc0.gtac.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private final AvailabilityRepository availabilityRepository;
    private final UnavailabilityRepository unavailabilityRepository;
    private final VolunteerRepository volunteerRepository;
    private final MissionAssignmentRepository missionAssignmentRepository;
    private final MissionTypeRepository missionTypeRepository;

    @Autowired
    public MissionServiceImpl(MissionRepository missionRepository, MissionMapper missionMapper, MissionTypeService missionTypeService, AvailabilityRepository availabilityRepository, UnavailabilityRepository unavailabilityRepository, VolunteerRepository volunteerRepository, MissionAssignmentRepository missionAssignmentRepository, MissionTypeRepository missionTypeRepository) {
        this.missionRepository = missionRepository;
        this.missionMapper = missionMapper;
        this.missionTypeService = missionTypeService;
        this.availabilityRepository = availabilityRepository;
        this.unavailabilityRepository = unavailabilityRepository;
        this.volunteerRepository = volunteerRepository;
        this.missionAssignmentRepository = missionAssignmentRepository;
        this.missionTypeRepository = missionTypeRepository;
    }

    @Override
    @Transactional
    public void save(MissionDTO missionDTO) {
        Mission mission;
        if(missionDTO.getUuid() != null && missionRepository.findByUuid(missionDTO.getUuid()).isPresent()){
            mission = missionRepository.findByUuid(missionDTO.getUuid())
                    .orElseThrow(() -> new EntityNotFoundException("La mission avec l'Id : "+ missionDTO.getUuid() + " n'existe pas."));
        } else {
            mission = new Mission();
        }
        mission.setTitle(missionDTO.getTitle());
        mission.setDescription(missionDTO.getDescription());
        mission.setComment(missionDTO.getComment());
        mission.setMissionType(missionTypeService.findMissionTypeByUuid(missionDTO.getMissionType().getUuid())
                .orElseThrow(() -> new EntityNotFoundException("Le type de mission avec l'Id : "+ missionDTO.getMissionType().getUuid() + " n'existe pas.")));
        mission.setStatus(MissionStatus.NEW);
        mission.setRequiredVolunteerNumber(missionDTO.getRequiredVolunteerNumber());
        missionRepository.save(mission);
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

    public List<Mission> findByStatus(MissionStatus status) {
        return missionRepository.findByStatus(status);
    }

    @Override
    public boolean isUserAvailableForMission(Long userId, LocalDateTime missionStart, LocalDateTime missionEnd) {
        // Récupérer les disponibilités et les indisponibilités de l'utilisateur
        List<Availability> availabilities = availabilityRepository.findByVolunteerId(userId);
        List<Unavailability> unavailabilities = unavailabilityRepository.findByVolunteerId(userId);

        // Vérifier si les indisponibilités chevauchent le créneau de la mission
        for (Unavailability unavailability : unavailabilities) {
            if (missionStart.toLocalDate().isBefore(unavailability.getEndDate()) &&
                    missionEnd.toLocalDate().isAfter(unavailability.getStartDate())) {
                return false; // L'utilisateur est indisponible
            }
        }

        // Vérifier si une disponibilité correspond au jour et à l'heure de la mission
        DayOfWeek missionDay = missionStart.getDayOfWeek();
        int missionStartHour = missionStart.getHour();
        int missionEndHour = missionEnd.getHour();

        for (Availability availability : availabilities) {
            if (availability.getDayOfWeek() == missionDay) {
                int availabilityStartHour = availability.getStartTime().getHour();
                int availabilityEndHour = availability.getEndTime().getHour();

                if (missionStartHour >= availabilityStartHour && missionEndHour <= availabilityEndHour) {
                    return true; // L'utilisateur est disponible
                }
            }
        }

        return false; // Aucune disponibilité ne correspond
    }



    /*@Override
    @Transactional
    public Mission planMission(Long missionId, List<Long> userIds, Long chiefUserId) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new RuntimeException("Mission not found"));

        List<User> assignedUsers = new ArrayList<>();
        for (Long userId : userIds) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            boolean isChief = userId.equals(chiefUserId); // Vérifier si cet utilisateur est le chef
            // Enregistrer l'assignation de l'utilisateur à la mission
            assignUserToMission(user, mission, isChief);
            assignedUsers.add(user);
        }

        mission.setAssignedUsers(assignedUsers);
        mission.setStatus(MissionStatus.PLANNED);
        return missionRepository.save(mission);
    }*/

    private void assignUserToMission(Volunteer volunteer, Mission mission, boolean isChief) {
        MissionAssignment assignment = new MissionAssignment();
        assignment.setVolunteer(volunteer);
        assignment.setMission(mission);
        assignment.setChief(isChief);
        assignment.setAssignedFrom(mission.getStartDateTime());
        assignment.setAssignedUntil(mission.getEndDateTime());
        missionAssignmentRepository.save(assignment);
    }

    @Override
    @Transactional
    public Mission startMission(UUID uuid) {
        Mission mission = missionRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("La mission avec l'id : " + uuid + " n'existe pas."));
        mission.setStatus(MissionStatus.ONGOING);
        return missionRepository.save(mission);
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

        // Trouver les missions qui doivent commencer
        List<Mission> missionsToStart = missionRepository.findByStatusAndStartDateTimeLessThanEqual(MissionStatus.PLANNED, now);
        List<Mission> missionsToUpdate = new ArrayList<>(missionsToStart);

        // Trouver les missions qui doivent se terminer
        List<Mission> missionsToEnd = missionRepository.findByStatusAndEndDateTimeLessThanEqual(MissionStatus.ONGOING, now);
        missionsToUpdate.addAll(missionsToEnd);

        return missionsToUpdate;
    }

    @Override
    @Transactional
    public void updateMissionStatus(Long missionId, LocalDateTime now) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new RuntimeException("Mission not found"));

        // Déterminer le nouveau statut en fonction de l'heure actuelle
        if (now.isEqual(mission.getStartDateTime()) || now.isAfter(mission.getStartDateTime()) && now.isBefore(mission.getEndDateTime())) {
            mission.setStatus(MissionStatus.ONGOING);
        } else if (now.isEqual(mission.getEndDateTime()) || now.isAfter(mission.getEndDateTime())) {
            mission.setStatus(MissionStatus.DONE);
            releaseUsersFromMission(missionId); // Libérer les utilisateurs de la mission
        }

        missionRepository.save(mission);
    }

    @Override
    public void planMission(MissionDTO missionDTO) {
        Mission mission;
        if(missionDTO.getUuid() != null && missionRepository.findByUuid(missionDTO.getUuid()).isPresent()){
            mission = missionRepository.findByUuid(missionDTO.getUuid())
                    .orElseThrow(() -> new EntityNotFoundException("La mission avec l'Id : "+ missionDTO.getUuid() + " n'existe pas."));
        } else {
            mission = new Mission();
        }

        mission.setTitle(missionDTO.getTitle());
        mission.setDescription(missionDTO.getDescription());
        mission.setComment(missionDTO.getComment());
        mission.setMissionType(missionTypeService.findMissionTypeByUuid(missionDTO.getMissionType().getUuid())
                .orElseThrow(() -> new EntityNotFoundException("Le type de mission avec l'Id : "+ missionDTO.getMissionType().getUuid() + " n'existe pas.")));
        mission.setRequiredVolunteerNumber(missionDTO.getRequiredVolunteerNumber());

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

    private void releaseUsersFromMission(Long missionId) {
        List<MissionAssignment> assignments = missionAssignmentRepository.findByMissionId(missionId);
        missionAssignmentRepository.deleteAll(assignments);
    }


}
