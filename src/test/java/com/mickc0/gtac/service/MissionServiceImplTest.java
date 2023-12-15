package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.MissionDTO;
import com.mickc0.gtac.dto.MissionTypeDTO;
import com.mickc0.gtac.entity.Mission;
import com.mickc0.gtac.entity.MissionStatus;
import com.mickc0.gtac.entity.MissionType;
import com.mickc0.gtac.mapper.MissionMapper;
import com.mickc0.gtac.mapper.MissionTypeMapper;
import com.mickc0.gtac.repository.MissionRepository;
import com.mickc0.gtac.service.MissionServiceImpl;
import com.mickc0.gtac.service.MissionTypeService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class MissionServiceImplTest {
    private MissionServiceImpl missionService;
    @Mock
    private MissionRepository missionRepository;
    @Mock
    private MissionMapper missionMapper;
    @Mock
    private MissionTypeService missionTypeService;
    @Mock
    MissionTypeMapper missionTypeMapper;

    @BeforeEach
    void initService() {
        missionService = new MissionServiceImpl(missionRepository, missionMapper, missionTypeService);
    }

    @Test
    void saveNewMission() {
        MissionType missionType = new MissionType();
        missionType.setUuid(UUID.randomUUID());
        missionType.setId(1L);
        missionType.setName("MissionType Name");
        missionType.setActive(true);

        MissionTypeDTO missionTypeDTO = new MissionTypeDTO();
        missionTypeDTO.setUuid(missionType.getUuid());
        missionTypeDTO.setName(missionType.getName());
        missionTypeDTO.setSelected(false);
        missionTypeDTO.setActive(missionType.isActive());
        when(missionTypeMapper.mapToMissionTypeDto(any(MissionType.class))).thenReturn(missionTypeDTO);

        Mission mission = new Mission();
        mission.setTitle("Mission Title");
        mission.setDescription("Mission Description");
        mission.setComment("Mission Comment");
        mission.setMissionType(missionType);
        mission.setReportDone(false);
        mission.setRequiredVolunteerNumber(5);

        MissionDTO missionDTO = new MissionDTO();
        missionDTO.setTitle(mission.getTitle());
        missionDTO.setDescription(mission.getDescription());
        missionDTO.setComment(mission.getComment());
        missionDTO.setStatus(mission.getStatus());
        missionDTO.setRequiredVolunteerNumber(mission.getRequiredVolunteerNumber());
        missionDTO.setStartDateTime(mission.getStartDateTime());
        missionDTO.setEndDateTime(mission.getEndDateTime());
        missionDTO.setMissionType(missionTypeMapper.mapToMissionTypeDto(mission.getMissionType()));
        missionDTO.setReportDone(mission.isReportDone());

        when(missionTypeService.findMissionTypeByUuid(missionType.getUuid())).thenReturn(Optional.of(missionType));

        missionService.save(missionDTO);

        verify(missionRepository).save(any(Mission.class));
        verify(missionTypeService).findMissionTypeByUuid(any(UUID.class));
        ArgumentCaptor<Mission> missionCaptor = ArgumentCaptor.forClass(Mission.class);
        verify(missionRepository).save(missionCaptor.capture());
        Mission savedMission = missionCaptor.getValue();

        assertNotNull(savedMission);
        assertEquals(missionDTO.getTitle(), savedMission.getTitle());
        assertEquals(missionDTO.getComment(), savedMission.getComment());
        assertEquals(missionDTO.getDescription(), savedMission.getDescription());
        assertEquals(missionDTO.getRequiredVolunteerNumber(), savedMission.getRequiredVolunteerNumber());
        assertEquals(missionDTO.isReportDone(), savedMission.isReportDone());
        assertEquals(savedMission.getStatus(), MissionStatus.NEW);
    }

    @Test
    void savePlannedMission() {
        UUID existingUuid = UUID.randomUUID();
        MissionType missionType = new MissionType();
        missionType.setUuid(UUID.randomUUID());
        missionType.setId(1L);
        missionType.setName("MissionType Name");
        missionType.setActive(true);

        MissionTypeDTO missionTypeDTO = new MissionTypeDTO();
        missionTypeDTO.setUuid(missionType.getUuid());
        missionTypeDTO.setName(missionType.getName());
        missionTypeDTO.setSelected(false);
        missionTypeDTO.setActive(missionType.isActive());
        when(missionTypeMapper.mapToMissionTypeDto(any(MissionType.class))).thenReturn(missionTypeDTO);

        Mission mission = new Mission();
        mission.setUuid(existingUuid);
        mission.setTitle("Mission Title");
        mission.setDescription("Mission Description");
        mission.setComment("Mission Comment");
        mission.setMissionType(missionType);
        mission.setStatus(MissionStatus.NEW);
        mission.setReportDone(false);
        mission.setRequiredVolunteerNumber(5);


        MissionDTO missionDTO = new MissionDTO();
        missionDTO.setUuid(existingUuid);
        missionDTO.setTitle(mission.getTitle());
        missionDTO.setDescription(mission.getDescription());
        missionDTO.setComment(mission.getComment());
        missionDTO.setStatus(MissionStatus.NEW);
        missionDTO.setRequiredVolunteerNumber(mission.getRequiredVolunteerNumber());
        missionDTO.setStartDateTime(mission.getStartDateTime());
        missionDTO.setEndDateTime(mission.getEndDateTime());
        missionDTO.setMissionType(missionTypeMapper.mapToMissionTypeDto(mission.getMissionType()));
        missionDTO.setReportDone(mission.isReportDone());
        missionDTO.setStartDateTime(LocalDateTime.now());
        missionDTO.setEndDateTime(LocalDateTime.now().plusHours(2));

        when(missionRepository.findByUuid(existingUuid)).thenReturn(Optional.of(mission));
        when(missionTypeService.findMissionTypeByUuid(any(UUID.class))).thenReturn(Optional.of(missionType));

        missionService.planMission(missionDTO);

        ArgumentCaptor<Mission> missionCaptor = ArgumentCaptor.forClass(Mission.class);
        verify(missionRepository).save(missionCaptor.capture());

        Mission savedMission = missionCaptor.getValue();
        assertNotNull(savedMission);
        assertEquals(MissionStatus.PLANNED, savedMission.getStatus());
        assertEquals(missionDTO.getTitle(), savedMission.getTitle());
        assertEquals(missionDTO.getComment(), savedMission.getComment());
        assertEquals(missionDTO.getDescription(), savedMission.getDescription());
        assertEquals(missionDTO.getRequiredVolunteerNumber(), savedMission.getRequiredVolunteerNumber());
        assertEquals(missionDTO.isReportDone(), savedMission.isReportDone());
    }
    @Test
    void saveConfirmedMission() {
        UUID existingUuid = UUID.randomUUID();
        MissionType missionType = new MissionType();
        missionType.setUuid(UUID.randomUUID());
        missionType.setId(1L);
        missionType.setName("MissionType Name");
        missionType.setActive(true);

        MissionTypeDTO missionTypeDTO = new MissionTypeDTO();
        missionTypeDTO.setUuid(missionType.getUuid());
        missionTypeDTO.setName(missionType.getName());
        missionTypeDTO.setSelected(false);
        missionTypeDTO.setActive(missionType.isActive());
        when(missionTypeMapper.mapToMissionTypeDto(any(MissionType.class))).thenReturn(missionTypeDTO);

        Mission mission = new Mission();
        mission.setUuid(existingUuid);
        mission.setTitle("Mission Title");
        mission.setDescription("Mission Description");
        mission.setComment("Mission Comment");
        mission.setMissionType(missionType);
        mission.setStatus(MissionStatus.PLANNED);
        mission.setReportDone(false);
        mission.setRequiredVolunteerNumber(5);


        MissionDTO missionDTO = new MissionDTO();
        missionDTO.setUuid(existingUuid);
        missionDTO.setTitle(mission.getTitle());
        missionDTO.setDescription(mission.getDescription());
        missionDTO.setComment(mission.getComment());
        missionDTO.setStatus(MissionStatus.PLANNED);
        missionDTO.setRequiredVolunteerNumber(mission.getRequiredVolunteerNumber());
        missionDTO.setStartDateTime(mission.getStartDateTime());
        missionDTO.setEndDateTime(mission.getEndDateTime());
        missionDTO.setMissionType(missionTypeMapper.mapToMissionTypeDto(mission.getMissionType()));
        missionDTO.setReportDone(mission.isReportDone());
        missionDTO.setStartDateTime(LocalDateTime.now());
        missionDTO.setEndDateTime(LocalDateTime.now().plusHours(2));

        when(missionRepository.findByUuid(existingUuid)).thenReturn(Optional.of(mission));
        when(missionTypeService.findMissionTypeByUuid(any(UUID.class))).thenReturn(Optional.of(missionType));

        missionService.confirmMission(missionDTO);

        ArgumentCaptor<Mission> missionCaptor = ArgumentCaptor.forClass(Mission.class);
        verify(missionRepository).save(missionCaptor.capture());

        Mission savedMission = missionCaptor.getValue();
        assertNotNull(savedMission);
        assertEquals(MissionStatus.CONFIRMED, savedMission.getStatus());
        assertEquals(missionDTO.getTitle(), savedMission.getTitle());
        assertEquals(missionDTO.getComment(), savedMission.getComment());
        assertEquals(missionDTO.getDescription(), savedMission.getDescription());
        assertEquals(missionDTO.getRequiredVolunteerNumber(), savedMission.getRequiredVolunteerNumber());
        assertEquals(missionDTO.isReportDone(), savedMission.isReportDone());
    }

    @Test
    void findMissionsToUpdateStatus() {
        LocalDateTime now = LocalDateTime.now();

        MissionType missionType = new MissionType();
        missionType.setUuid(UUID.randomUUID());
        missionType.setId(1L);
        missionType.setName("MissionType Name");
        missionType.setActive(true);

        Mission missionToStart = new Mission();
        missionToStart.setId(1L);
        missionToStart.setUuid(UUID.randomUUID());
        missionToStart.setTitle("Mission Imminente");
        missionToStart.setDescription("Description de la mission imminente");
        missionToStart.setComment("Commentaire sur la mission");
        missionToStart.setMissionType(missionType);
        missionToStart.setStatus(MissionStatus.CONFIRMED);
        missionToStart.setRequiredVolunteerNumber(10);
        missionToStart.setStartDateTime(LocalDateTime.now().plusHours(1));
        missionToStart.setEndDateTime(LocalDateTime.now().plusDays(1));
        missionToStart.setReportDone(false);

        Mission missionToEnd = new Mission();
        missionToEnd.setId(2L);
        missionToEnd.setUuid(UUID.randomUUID());
        missionToEnd.setTitle("Mission en Cours");
        missionToEnd.setDescription("Description de la mission en cours");
        missionToEnd.setComment("Commentaire sur la mission");
        missionToEnd.setMissionType(missionType);
        missionToEnd.setStatus(MissionStatus.ONGOING);
        missionToEnd.setRequiredVolunteerNumber(5);
        missionToEnd.setStartDateTime(LocalDateTime.now().minusHours(2));
        missionToEnd.setEndDateTime(LocalDateTime.now().plusMinutes(30));
        missionToEnd.setReportDone(false);

        when(missionRepository.findConfirmedMissionsToStart(MissionStatus.CONFIRMED, now))
                .thenReturn(List.of(missionToStart));
        when(missionRepository.findOngoingMissionsToEnd(MissionStatus.ONGOING, now))
                .thenReturn(List.of(missionToEnd));

        List<Mission> missionsToUpdate = missionService.findMissionsToUpdateStatus(now);

        assertEquals(2, missionsToUpdate.size());
        assertEquals(missionToStart, missionsToUpdate.get(0));
        assertEquals(missionToEnd, missionsToUpdate.get(1));
    }

    @Test
    void updateMissionStatus_LaunchMission() {
        Long missionId = 1L;
        UUID missionUuid = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        Mission mission = new Mission();
        mission.setId(missionId);
        mission.setUuid(missionUuid);
        mission.setStartDateTime(now.minusHours(1));
        mission.setEndDateTime(now.plusHours(1));
        mission.setStatus(MissionStatus.CONFIRMED);

        when(missionRepository.findByUuid(missionUuid)).thenReturn(Optional.of(mission));
        when(missionRepository.findById(missionId)).thenReturn(Optional.of(mission));

        missionService.updateMissionStatus(missionId, now);

        verify(missionRepository).findByUuid(missionUuid);
        verify(missionRepository).findById(missionId);
        ArgumentCaptor<Mission> missionCaptor = ArgumentCaptor.forClass(Mission.class);
        verify(missionRepository).save(missionCaptor.capture());
        assertEquals(MissionStatus.ONGOING, missionCaptor.getValue().getStatus());

    }

    @Test
    void updateMissionStatus_EndMission() {
        Long missionId = 1L;
        UUID missionUuid = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        Mission mission = new Mission();
        mission.setId(missionId);
        mission.setUuid(missionUuid);
        mission.setStartDateTime(now.minusHours(2));
        mission.setEndDateTime(now.minusHours(1));
        mission.setStatus(MissionStatus.ONGOING);
        when(missionRepository.findByUuid(missionUuid)).thenReturn(Optional.of(mission));
        when(missionRepository.findById(missionId)).thenReturn(Optional.of(mission));

        missionService.updateMissionStatus(missionId, now);

        verify(missionRepository).findById(missionId);
        verify(missionRepository).findById(missionId);
        ArgumentCaptor<Mission> missionCaptor = ArgumentCaptor.forClass(Mission.class);
        verify(missionRepository).save(missionCaptor.capture());
        assertEquals(MissionStatus.COMPLETED, missionCaptor.getValue().getStatus());

    }

    @Test
    void updateMissionStatus_MissionNotFound_ThrowsEntityNotFoundException() {
        Long missionId = 1L;
        LocalDateTime now = LocalDateTime.now();

        when(missionRepository.findById(missionId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> missionService.updateMissionStatus(missionId, now));
    }
}
