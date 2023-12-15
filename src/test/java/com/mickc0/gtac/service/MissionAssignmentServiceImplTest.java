package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.MissionAssignmentDTO;
import com.mickc0.gtac.dto.VolunteerDTO;
import com.mickc0.gtac.entity.Mission;
import com.mickc0.gtac.entity.MissionAssignment;
import com.mickc0.gtac.entity.MissionType;
import com.mickc0.gtac.entity.Volunteer;
import com.mickc0.gtac.mapper.MissionAssignmentMapper;
import com.mickc0.gtac.repository.MissionAssignmentRepository;
import com.mickc0.gtac.service.MissionAssignmentServiceImpl;
import com.mickc0.gtac.service.MissionService;
import com.mickc0.gtac.service.VolunteerService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class MissionAssignmentServiceImplTest {

    private MissionAssignmentServiceImpl missionAssignmentService;
    @Mock
    private MissionAssignmentRepository missionAssignmentRepository;
    @Mock
    private MissionAssignmentMapper missionAssignmentMapper;
    @Mock
    private MissionService missionService;
    @Mock
    private VolunteerService volunteerService;
    @BeforeEach
    void initService() {
        missionAssignmentService = new MissionAssignmentServiceImpl(missionAssignmentRepository,missionAssignmentMapper,
                 missionService, volunteerService);
    }
    @Test
    void findAllCurrentMissionAssignment() {
        UUID missionUuid = UUID.randomUUID();

        VolunteerDTO volunteerDTO = new VolunteerDTO();
        volunteerDTO.setUuid(UUID.randomUUID());
        volunteerDTO.setLastName("DUPONT");
        volunteerDTO.setFirstName("JEAN");
        volunteerDTO.setEmail("jean.dupont@gmail.com");
        volunteerDTO.setPhoneNumber("0606060606");

        Volunteer volunteer1 = new Volunteer();
        volunteer1.setUuid(UUID.randomUUID());
        volunteer1.setLastName("DUPONT");
        volunteer1.setFirstName("JEAN");
        volunteer1.setEmail("jean.dupont@gmail.com");
        volunteer1.setPhoneNumber("0606060606");
        Volunteer volunteer2 = new Volunteer();
        volunteer2.setUuid(UUID.randomUUID());
        volunteer2.setLastName("MARC");
        volunteer2.setFirstName("JEAN");
        volunteer2.setEmail("jean.marc@gmail.com");
        volunteer2.setPhoneNumber("0606060606");

        MissionType missionType = new MissionType();
        missionType.setUuid(UUID.randomUUID());
        missionType.setId(1L);
        missionType.setName("MissionType Name");
        missionType.setActive(true);

        Mission mission = new Mission();
        mission.setTitle("Mission Title");
        mission.setDescription("Mission Description");
        mission.setComment("Mission Comment");
        mission.setMissionType(missionType);
        mission.setReportDone(false);
        mission.setRequiredVolunteerNumber(5);

        MissionAssignment assignment1 = new MissionAssignment();
        assignment1.setId(1L);
        assignment1.setUuid(UUID.randomUUID());
        assignment1.setVolunteer(volunteer1);
        assignment1.setMission(mission);
        assignment1.setChief(true);
        assignment1.setHasParticipated(false);
        assignment1.setAssignedFrom(LocalDateTime.now().minusHours(2));
        assignment1.setAssignedUntil(LocalDateTime.now().plusMinutes(30));

        MissionAssignment assignment2 = new MissionAssignment();
        assignment2.setId(2L);
        assignment2.setUuid(UUID.randomUUID());
        assignment2.setVolunteer(volunteer2);
        assignment2.setMission(mission);
        assignment2.setChief(false);
        assignment2.setHasParticipated(false);
        assignment2.setAssignedFrom(LocalDateTime.now().minusHours(3));
        assignment2.setAssignedUntil(LocalDateTime.now().plusMinutes(30));

        when(missionAssignmentRepository.findByMissionUuid(missionUuid))
                .thenReturn(Arrays.asList(assignment1, assignment2));

        when(missionAssignmentMapper.mapToDto(any(MissionAssignment.class))).thenAnswer(invocation -> {
            MissionAssignment ma = invocation.getArgument(0);
            MissionAssignmentDTO missionAssignmentDTO = new MissionAssignmentDTO();
            missionAssignmentDTO.setUuid(ma.getUuid());
            missionAssignmentDTO.setVolunteer(volunteerDTO);
            missionAssignmentDTO.setAssignedFrom(ma.getAssignedFrom());
            missionAssignmentDTO.setAssignedUntil(ma.getAssignedUntil());
            missionAssignmentDTO.setChief(ma.isChief());
            missionAssignmentDTO.setHasParticiped(ma.hasParticipated());
            return missionAssignmentDTO;
        });

        List<MissionAssignmentDTO> result = missionAssignmentService.findAllCurrentMissionAssignment(missionUuid);

        assertEquals(2, result.size());
        verify(missionAssignmentRepository).findByMissionUuid(missionUuid);
    }
    @Test
    void assignVolunteersToMission() {
        UUID missionUuid = UUID.randomUUID();
        List<UUID> volunteerUuids = Arrays.asList(UUID.randomUUID(), UUID.randomUUID());
        UUID chiefUuid = volunteerUuids.get(0);

        Mission mission = new Mission();
        mission.setUuid(missionUuid);

        Volunteer chief = new Volunteer();
        chief.setUuid(chiefUuid);

        when(missionService.findMissionByUuid(missionUuid)).thenReturn(Optional.of(mission));
        when(volunteerService.findVolunteerByUuid(chiefUuid)).thenReturn(Optional.of(chief));
        when(volunteerService.findVolunteerByUuid(volunteerUuids.get(1))).thenReturn(Optional.of(new Volunteer()));

        when(missionAssignmentRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        missionAssignmentService.assignVolunteersToMission(missionUuid, volunteerUuids, chiefUuid);

        ArgumentCaptor<List<MissionAssignment>> captor = ArgumentCaptor.forClass(List.class);
        verify(missionAssignmentRepository).saveAll(captor.capture());

        List<MissionAssignment> capturedAssignments = captor.getValue();
        assertNotNull(capturedAssignments);
        assertEquals(volunteerUuids.size(), capturedAssignments.size());

        assertTrue(capturedAssignments.stream().anyMatch(assignment -> assignment.isChief() && assignment.getVolunteer().getUuid().equals(chiefUuid)));
    }



    @Test
    void deleteAllAssignmentsForMission() {
        UUID missionUuid = UUID.randomUUID();
        doNothing().when(missionAssignmentRepository).deleteAllAssignmentsByMissionUuid(missionUuid);

        missionAssignmentService.deleteAllAssignmentsForMission(missionUuid);

        verify(missionAssignmentRepository).deleteAllAssignmentsByMissionUuid(missionUuid);
    }
    @Test
    void completeMissionReport() {
        UUID missionUuid = UUID.randomUUID();
        List<UUID> assignmentUuids = Arrays.asList(UUID.randomUUID(), UUID.randomUUID());
        List<MissionAssignment> assignments = Arrays.asList(new MissionAssignment(), new MissionAssignment());
        when(missionAssignmentRepository.findByMissionUuid(missionUuid)).thenReturn(assignments);

        missionAssignmentService.completeMissionReport(assignmentUuids, missionUuid);

        verify(missionAssignmentRepository).saveAll(assignments);
    }
    @Test
    void isVolunteerInUse() {
        UUID volunteerUuid = UUID.randomUUID();
        when(missionAssignmentRepository.existsByVolunteerUuid(volunteerUuid)).thenReturn(true);

        boolean result = missionAssignmentService.isVolunteerInUse(volunteerUuid);

        assertTrue(result);
        verify(missionAssignmentRepository).existsByVolunteerUuid(volunteerUuid);
    }

    @Test
    void getVolunteersInMissionToday() {

        LocalDateTime now = LocalDateTime.of(2023, 12, 15, 19, 32, 50);

        Volunteer volunteer1 = new Volunteer();
        volunteer1.setUuid(UUID.randomUUID());
        Volunteer volunteer2 = new Volunteer();
        volunteer2.setUuid(UUID.randomUUID());

        MissionAssignment assignment1 = new MissionAssignment();
        assignment1.setVolunteer(volunteer1);

        MissionAssignment assignment2 = new MissionAssignment();
        assignment2.setVolunteer(volunteer2);

        List<MissionAssignment> assignments = Arrays.asList(assignment1, assignment2);
        when(missionAssignmentRepository.findVolunteersInMissionOnDate(now)).thenReturn(assignments);

        List<UUID> result = missionAssignmentService.getVolunteersInMissionByLocalDateTime(now);

        assertEquals(assignments.size(), result.size());
        assertTrue(result.contains(volunteer1.getUuid()));
        assertTrue(result.contains(volunteer2.getUuid()));
        verify(missionAssignmentRepository).findVolunteersInMissionOnDate(now);
    }

}
