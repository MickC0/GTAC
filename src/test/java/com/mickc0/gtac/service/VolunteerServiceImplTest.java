package com.mickc0.gtac.service;


import com.mickc0.gtac.dto.*;
import com.mickc0.gtac.entity.*;
import com.mickc0.gtac.mapper.VolunteerMapper;
import com.mickc0.gtac.repository.VolunteerRepository;
import com.mickc0.gtac.service.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class VolunteerServiceImplTest {


    private VolunteerServiceImpl  volunteerService;
    @Mock
    private VolunteerRepository volunteerRepository;
    @Mock
    private VolunteerMapper volunteerMapper;
    @Mock
    private MissionTypeService missionTypeService;
    @Mock
    private AvailabilityService availabilityService;
    @Mock
    private UnavailabilityService unavailabilityService;
    @Mock
    private RoleService roleService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void initService() {
        volunteerService = new VolunteerServiceImpl(volunteerRepository,volunteerMapper,missionTypeService, availabilityService, unavailabilityService, roleService, passwordEncoder);
    }

    @Test
    void saveOrUpdateVolunteerDetails() {
        VolunteerDetailsDTO volunteerDetailsDTO = new VolunteerDetailsDTO();
        volunteerDetailsDTO.setLastName("DUPONT");
        volunteerDetailsDTO.setFirstName("JEAN");
        volunteerDetailsDTO.setEmail("jean.dupont@gmail.com");
        volunteerDetailsDTO.setPhoneNumber("0606060606");
        volunteerDetailsDTO.setRoles(List.of(RoleName.ROLE_MISSION.name()));
        Role mockRole = new Role();
        mockRole.setName(RoleName.ROLE_MISSION.name());

        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("jean.dupont@gmail.com");

        when(roleService.findByName(anyString())).thenReturn(mockRole);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        volunteerService.saveOrUpdateVolunteerDetails(volunteerDetailsDTO, false, authentication);

        verify(volunteerRepository).save(any(Volunteer.class));
    }

    @Test
    void saveAndReturnNewVolunteer(){
        Volunteer volunteer = new Volunteer();
        volunteer.setLastName("DUPONT");
        volunteer.setFirstName("JEAN");
        volunteer.setEmail("jean.dupont@gmail.com");
        volunteer.setPhoneNumber("0606060606");

        Role mockRole = new Role();
        mockRole.setName(RoleName.ROLE_MISSION.name());
        mockRole.setId(1L);

        volunteer.setRoles(Collections.singletonList(mockRole));
        volunteerService.saveAndReturn(volunteer);

        verify(volunteerRepository).save(any(Volunteer.class));
    }

    @Test
    void saveAndReturnNewVolunteerIsNull(){
        assertThrows(IllegalArgumentException.class, () -> volunteerService.saveAndReturn(null));
        verify(volunteerRepository, never()).save(any(Volunteer.class));
    }

    @Test
    void handleUnavailabilities_WhenEmpty() {
        VolunteerDTO volunteerDTO = new VolunteerDTO();
        Volunteer volunteer = new Volunteer();

        volunteerService.handleUnavailabilities(volunteerDTO, volunteer);

        verify(unavailabilityService).deleteAllByVolunteer(volunteer);
        verify(unavailabilityService, never()).save(any(Unavailability.class));
    }

    @Test
    void handleUnavailabilities_WithUnavailabilities() {
        VolunteerDTO volunteerDTO = new VolunteerDTO();
        List<UnavailabilityDTO> unavailabilitiesDTO = new ArrayList<>();
        UnavailabilityDTO unavailabilityDTO = new UnavailabilityDTO();
        unavailabilityDTO.setUuid(UUID.randomUUID());
        unavailabilitiesDTO.add(unavailabilityDTO);
        volunteerDTO.setUnavailabilities(unavailabilitiesDTO);
        Volunteer volunteer = new Volunteer();
        Unavailability unavailability = new Unavailability();
        unavailability.setUuid(unavailabilityDTO.getUuid());

        when(unavailabilityService.findByUuid(unavailabilityDTO.getUuid())).thenReturn(unavailability);
        doNothing().when(unavailabilityService).save(any(Unavailability.class));

        volunteerService.handleUnavailabilities(volunteerDTO, volunteer);

        verify(unavailabilityService, atLeastOnce()).save(any(Unavailability.class));
        verify(unavailabilityService, never()).deleteAllByVolunteer(volunteer);
    }

    @Test
    void handleAvailabilities_WhenEmpty() {
        VolunteerDTO volunteerDTO = new VolunteerDTO();
        Volunteer volunteer = new Volunteer();

        volunteerService.handleAvailabilities(volunteerDTO, volunteer);

        verify(availabilityService).deleteAllByVolunteer(volunteer);
        verify(availabilityService, never()).save(any(Availability.class));
    }

    @Test
    void handleAvailabilities_WithAvailabilities() {
        VolunteerDTO volunteerDTO = new VolunteerDTO();
        List<AvailabilityDTO> availabilitiesDTO = new ArrayList<>();
        AvailabilityDTO availabilityDTO = new AvailabilityDTO();
        availabilityDTO.setUuid(UUID.randomUUID());
        availabilityDTO.setDayOfWeek(DayOfWeek.MONDAY);
        availabilityDTO.setStartTime(LocalTime.of(9, 0));
        availabilityDTO.setEndTime(LocalTime.of(17, 0));
        availabilitiesDTO.add(availabilityDTO);
        volunteerDTO.setAvailabilities(availabilitiesDTO);

        Volunteer volunteer = new Volunteer();
        Availability availability = new Availability();
        availability.setUuid(availabilityDTO.getUuid());

        when(availabilityService.findByUuid(availabilityDTO.getUuid())).thenReturn(availability);
        doNothing().when(availabilityService).save(any(Availability.class));

        volunteerService.handleAvailabilities(volunteerDTO, volunteer);

        verify(availabilityService, atLeastOnce()).save(any(Availability.class));
        verify(availabilityService, never()).deleteAllByVolunteer(volunteer);
    }
    @Test
    void handleMissionTypes_WhenEmpty() {
        VolunteerDTO volunteerDTO = new VolunteerDTO();
        Volunteer volunteer = new Volunteer();

        volunteerService.handleMissionTypes(volunteerDTO, volunteer);

        assertTrue(volunteer.getMissionTypes().isEmpty());
    }

    @Test
    void handleMissionTypes_WithMissionTypes() {
        VolunteerDTO volunteerDTO = new VolunteerDTO();
        volunteerDTO.setMissionTypes(Arrays.asList(UUID.randomUUID().toString(), UUID.randomUUID().toString()));

        MissionType missionType1 = new MissionType();
        MissionType missionType2 = new MissionType();
        when(missionTypeService.findMissionTypeByUuid(any(UUID.class)))
                .thenReturn(Optional.of(missionType1))
                .thenReturn(Optional.of(missionType2));

        Volunteer volunteer = new Volunteer();

        volunteerService.handleMissionTypes(volunteerDTO, volunteer);

        verify(missionTypeService, times(2)).findMissionTypeByUuid(any(UUID.class));
        assertEquals(2, volunteer.getMissionTypes().size());
    }

    @Test
    void findByEmail_WhenVolunteerExists() {
        String email = "test@example.com";
        Volunteer volunteer = new Volunteer();
        volunteer.setEmail(email);

        when(volunteerRepository.findByEmail(email)).thenReturn(volunteer);

        Optional<Volunteer> result = volunteerService.findByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());
    }

    @Test
    void findByEmail_WhenVolunteerDoesNotExist() {
        String email = "nonexistent@example.com";

        when(volunteerRepository.findByEmail(email)).thenReturn(null);

        Optional<Volunteer> result = volunteerService.findByEmail(email);

        assertFalse(result.isPresent());
    }

    @Test
    void findVolunteerDTOByUuid_WhenVolunteerExists() {
        UUID uuid = UUID.randomUUID();
        Volunteer volunteer = new Volunteer();
        VolunteerDTO volunteerDTO = new VolunteerDTO();

        when(volunteerRepository.findByUuid(uuid)).thenReturn(Optional.of(volunteer));
        when(volunteerMapper.mapToDto(volunteer)).thenReturn(volunteerDTO);

        VolunteerDTO result = volunteerService.findVolunteerDTOByUuid(uuid);

        assertNotNull(result);
        verify(volunteerMapper).mapToDto(volunteer);
    }

    @Test
    void findVolunteerDTOByUuid_WhenVolunteerDoesNotExist() {
        UUID uuid = UUID.randomUUID();

        when(volunteerRepository.findByUuid(uuid)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            volunteerService.findVolunteerDTOByUuid(uuid);
        });
    }

    @Test
    void findAll_ReturnsListOfVolunteers() {
        Volunteer volunteer1 = new Volunteer();
        Volunteer volunteer2 = new Volunteer();

        when(volunteerRepository.findAll()).thenReturn(Arrays.asList(volunteer1, volunteer2));

        List<Volunteer> result = volunteerService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(volunteerRepository).findAll();
    }
    @Test
    void deleteVolunteer() {
        UUID uuid = UUID.randomUUID();

        volunteerService.deleteVolunteer(uuid);

        verify(availabilityService).deleteAllByVolunteerUuid(uuid);
        verify(unavailabilityService).deleteAllByVolunteerUuid(uuid);
        verify(volunteerRepository).deleteByUuid(uuid);
    }

    @Test
    void findAllVolunteersWithStatus_AllAvailable() {
        Volunteer volunteer1 = new Volunteer();
        Volunteer volunteer2 = new Volunteer();
        when(volunteerRepository.findAll()).thenReturn(Arrays.asList(volunteer1, volunteer2));
        when(unavailabilityService.isVolunteerUnavailableOnDate(any(Volunteer.class), any())).thenReturn(false);

        VolunteerStatusDTO statusDTO1 = new VolunteerStatusDTO();
        VolunteerStatusDTO statusDTO2 = new VolunteerStatusDTO();
        when(volunteerMapper.mapToStatusDTO(volunteer1, "Disponible")).thenReturn(statusDTO1);
        when(volunteerMapper.mapToStatusDTO(volunteer2, "Disponible")).thenReturn(statusDTO2);

        List<VolunteerStatusDTO> result = volunteerService.findAllVolunteersWithStatus();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(volunteerMapper, times(2)).mapToStatusDTO(any(Volunteer.class), eq("Disponible"));
    }

    @Test
    void findAllVolunteersWithStatus_SomeAbsent() {
        Volunteer volunteer1 = new Volunteer();
        Volunteer volunteer2 = new Volunteer();
        when(volunteerRepository.findAll()).thenReturn(Arrays.asList(volunteer1, volunteer2));
        when(unavailabilityService.isVolunteerUnavailableOnDate(volunteer1, LocalDateTime.now().toLocalDate())).thenReturn(false);
        when(unavailabilityService.isVolunteerUnavailableOnDate(volunteer2, LocalDateTime.now().toLocalDate())).thenReturn(true);

        VolunteerStatusDTO statusDTO1 = new VolunteerStatusDTO();
        VolunteerStatusDTO statusDTO2 = new VolunteerStatusDTO();
        when(volunteerMapper.mapToStatusDTO(volunteer1, "Disponible")).thenReturn(statusDTO1);
        when(volunteerMapper.mapToStatusDTO(volunteer2, "Absent")).thenReturn(statusDTO2);

        List<VolunteerStatusDTO> result = volunteerService.findAllVolunteersWithStatus();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(volunteerMapper).mapToStatusDTO(volunteer1, "Disponible");
        verify(volunteerMapper).mapToStatusDTO(volunteer2, "Absent");
    }
    @Test
    void getAvailableUsersForMission() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusHours(2);
        DayOfWeek dayOfWeek = start.getDayOfWeek();
        LocalTime startTime = start.toLocalTime();
        LocalTime endTime = end.toLocalTime();
        UUID missionTypeUuid = UUID.randomUUID();
        UUID currentMissionUuid = UUID.randomUUID();

        Volunteer volunteer1 = new Volunteer(); // Créer et configurer des bénévoles
        Volunteer volunteer2 = new Volunteer();

        when(volunteerRepository.findAvailableVolunteersForMission(start, end, dayOfWeek, startTime, endTime, missionTypeUuid, currentMissionUuid))
                .thenReturn(Arrays.asList(volunteer1, volunteer2));

        VolunteerDTO volunteerDTO1 = new VolunteerDTO();
        VolunteerDTO volunteerDTO2 = new VolunteerDTO();
        when(volunteerMapper.mapToConfirmDto(volunteer1)).thenReturn(volunteerDTO1);
        when(volunteerMapper.mapToConfirmDto(volunteer2)).thenReturn(volunteerDTO2);

        List<VolunteerDTO> result = volunteerService.getAvailableUsersForMission(start, end, missionTypeUuid, currentMissionUuid);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(volunteerRepository).findAvailableVolunteersForMission(start, end, dayOfWeek, startTime, endTime, missionTypeUuid, currentMissionUuid);
        verify(volunteerMapper).mapToConfirmDto(volunteer1);
        verify(volunteerMapper).mapToConfirmDto(volunteer2);
    }

    @Test
    void findVolunteerProfilDTOByUuid_WhenVolunteerExists() {
        UUID uuid = UUID.randomUUID();
        Volunteer volunteer = new Volunteer();
        VolunteerGuestProfilDTO volunteerGuestProfilDTO = new VolunteerGuestProfilDTO();

        when(volunteerRepository.findByUuid(uuid)).thenReturn(Optional.of(volunteer));
        when(volunteerMapper.mapToProfilDto(volunteer)).thenReturn(volunteerGuestProfilDTO);

        VolunteerGuestProfilDTO result = volunteerService.findVolunteerProfilDTOByUuid(uuid);

        assertNotNull(result);
        verify(volunteerMapper).mapToProfilDto(volunteer);
    }

    @Test
    void findVolunteerProfilDTOByUuid_WhenVolunteerDoesNotExist() {
        UUID uuid = UUID.randomUUID();

        when(volunteerRepository.findByUuid(uuid)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            volunteerService.findVolunteerProfilDTOByUuid(uuid);
        });
    }

    @Test
    void findVolunteerByUuid_WhenVolunteerExists() {
        UUID uuid = UUID.randomUUID();
        Volunteer volunteer = new Volunteer();

        when(volunteerRepository.findByUuid(uuid)).thenReturn(Optional.of(volunteer));

        Optional<Volunteer> result = volunteerService.findVolunteerByUuid(uuid);

        assertTrue(result.isPresent());
        assertEquals(volunteer, result.get());
    }

    @Test
    void findVolunteerByUuid_WhenVolunteerDoesNotExist() {
        UUID uuid = UUID.randomUUID();
        when(volunteerRepository.findByUuid(uuid)).thenReturn(Optional.empty());
        Optional<Volunteer> result = volunteerService.findVolunteerByUuid(uuid);
        assertFalse(result.isPresent());
    }


    @Test
    void findAllVolunteerByRole() {
        RoleName roleName = RoleName.ROLE_ADMIN;
        Volunteer volunteer1 = new Volunteer();
        Volunteer volunteer2 = new Volunteer();

        when(volunteerRepository.findByRole(roleName.name())).thenReturn(Arrays.asList(volunteer1, volunteer2));

        VolunteerDetailsDTO volunteerDetailsDTO1 = new VolunteerDetailsDTO();
        VolunteerDetailsDTO volunteerDetailsDTO2 = new VolunteerDetailsDTO();
        when(volunteerMapper.mapToDetailsDto(volunteer1)).thenReturn(volunteerDetailsDTO1);
        when(volunteerMapper.mapToDetailsDto(volunteer2)).thenReturn(volunteerDetailsDTO2);

        List<VolunteerDetailsDTO> result = volunteerService.findAllVolunteerByRole(roleName);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(volunteerRepository).findByRole(roleName.name());
        verify(volunteerMapper).mapToDetailsDto(volunteer1);
        verify(volunteerMapper).mapToDetailsDto(volunteer2);
    }

    @Test
    void findVolunteerDetailsByUuid_WhenVolunteerExists() {
        UUID uuid = UUID.randomUUID();
        Volunteer volunteer = new Volunteer();
        VolunteerDetailsDTO volunteerDetailsDTO = new VolunteerDetailsDTO();

        when(volunteerRepository.findByUuid(uuid)).thenReturn(Optional.of(volunteer));
        when(volunteerMapper.mapToDetailsDto(volunteer)).thenReturn(volunteerDetailsDTO);

        VolunteerDetailsDTO result = volunteerService.findVolunteerDetailsByUuid(uuid);

        assertNotNull(result);
        verify(volunteerMapper).mapToDetailsDto(volunteer);
    }

    @Test
    void findVolunteerDetailsByUuid_WhenVolunteerDoesNotExist() {
        UUID uuid = UUID.randomUUID();

        when(volunteerRepository.findByUuid(uuid)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            volunteerService.findVolunteerDetailsByUuid(uuid);
        });
    }
    @Test
    void findVolunteerRoleProfilByEmail_WhenVolunteerExists() {
        String email = "test@example.com";
        Volunteer volunteer = new Volunteer();
        VolunteerRoleProfilDTO volunteerRoleProfilDTO = new VolunteerRoleProfilDTO();

        when(volunteerRepository.findByEmail(email)).thenReturn(volunteer);
        when(volunteerMapper.mapToFullDetailsDto(volunteer)).thenReturn(volunteerRoleProfilDTO);

        VolunteerRoleProfilDTO result = volunteerService.findVolunteerRoleProfilByEmail(email);

        assertNotNull(result);
        verify(volunteerMapper).mapToFullDetailsDto(volunteer);
    }

    @Test
    void findVolunteerRoleProfilByEmail_WhenVolunteerDoesNotExist() {
        String email = "nonexistent@example.com";

        when(volunteerRepository.findByEmail(email)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            volunteerService.findVolunteerRoleProfilByEmail(email);
        });
    }
    @Test
    void changePassword_VolunteerNotFound() {
        String email = "nonexistent@example.com";

        when(volunteerRepository.findByEmail(email)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            volunteerService.changePassword(email, "oldPassword", "newPassword");
        });
    }

    @Test
    void changePassword_IncorrectOldPassword() {
        String email = "test@example.com";
        Volunteer volunteer = new Volunteer();
        volunteer.setPassword("encodedOldPassword");

        when(volunteerRepository.findByEmail(email)).thenReturn(volunteer);
        when(passwordEncoder.matches("oldPassword", volunteer.getPassword())).thenReturn(false);

        assertFalse(volunteerService.changePassword(email, "oldPassword", "newPassword"));
        assertTrue(volunteer.isMustChangePassword());
    }

    @Test
    void changePassword_Successful() {
        String email = "test@example.com";
        Volunteer volunteer = new Volunteer();
        volunteer.setPassword("encodedOldPassword");

        when(volunteerRepository.findByEmail(email)).thenReturn(volunteer);
        when(passwordEncoder.matches("oldPassword", volunteer.getPassword())).thenReturn(true);
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");

        assertTrue(volunteerService.changePassword(email, "oldPassword", "newPassword"));
        assertFalse(volunteer.isMustChangePassword());
        assertEquals("encodedNewPassword", volunteer.getPassword());
        verify(volunteerRepository).save(volunteer);
    }
}
