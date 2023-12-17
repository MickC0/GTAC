package com.mickc0.gtac.service;


import com.mickc0.gtac.dto.*;
import com.mickc0.gtac.entity.*;
import com.mickc0.gtac.exception.DeleteAdminException;
import com.mickc0.gtac.mapper.VolunteerMapper;
import com.mickc0.gtac.repository.VolunteerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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

        volunteerService.saveOrUpdateVolunteerDetails(volunteerDetailsDTO, authentication);

        verify(volunteerRepository).save(any(Volunteer.class));
    }

    @Test
    public void testSaveNewVolunteer() {
        Role role = new Role();
        role.setId(1L);
        role.setName(RoleName.ROLE_VOLUNTEER.name());
        VolunteerDetailsDTO volunteerDetailsDTO = new VolunteerDetailsDTO();
        volunteerDetailsDTO.setUuid(null);
        volunteerDetailsDTO.setLastName("DUPONT");
        volunteerDetailsDTO.setFirstName("JEAN");
        volunteerDetailsDTO.setEmail("jean.dupont@gmail.com");
        volunteerDetailsDTO.setPhoneNumber("0606060606");
        volunteerDetailsDTO.setRoles(List.of(role.getName()));

        when(roleService.findByName(RoleName.ROLE_VOLUNTEER.name())).thenReturn(role);
        when(volunteerRepository.save(any(Volunteer.class))).thenReturn(new Volunteer());

        volunteerService.saveOrUpdateVolunteerDetails(volunteerDetailsDTO, mock(Authentication.class));

        ArgumentCaptor<Volunteer> volunteerCaptor = ArgumentCaptor.forClass(Volunteer.class);
        verify(volunteerRepository).save(volunteerCaptor.capture());
        Volunteer savedVolunteer = volunteerCaptor.getValue();

        assertNotNull(savedVolunteer);

    }

    @Test
    public void testUpdateExistingVolunteer() {
        UUID existingVolunteerUuid = UUID.randomUUID();
        Role roleMission = new Role();
        roleMission.setId(1L);
        roleMission.setName(RoleName.ROLE_MISSION.name());
        Role volunteerRole = new Role();
        volunteerRole.setId(2L);
        volunteerRole.setName(RoleName.ROLE_VOLUNTEER.name());

        VolunteerDetailsDTO dto = new VolunteerDetailsDTO();
        dto.setUuid(existingVolunteerUuid);
        dto.setLastName("Updated");
        dto.setFirstName("User");
        dto.setEmail("updated@example.com");
        dto.setPhoneNumber("0123456789");
        dto.setRoles(List.of(RoleName.ROLE_VOLUNTEER.name()));

        Volunteer existingVolunteer = new Volunteer();
        existingVolunteer.setUuid(existingVolunteerUuid);
        existingVolunteer.setLastName("Original");
        existingVolunteer.setFirstName("User");
        existingVolunteer.setEmail("original@example.com");
        existingVolunteer.setPhoneNumber("9876543210");
        existingVolunteer.setRoles(List.of(roleMission));

        when(volunteerRepository.findByUuid(existingVolunteerUuid)).thenReturn(Optional.of(existingVolunteer));
        when(roleService.findByName(anyString())).thenReturn(volunteerRole);

        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("original@example.com");

        volunteerService.saveOrUpdateVolunteerDetails(dto, authentication);

        ArgumentCaptor<Volunteer> volunteerCaptor = ArgumentCaptor.forClass(Volunteer.class);

        verify(volunteerRepository).save(volunteerCaptor.capture());
        Volunteer savedVolunteer = volunteerCaptor.getValue();

        assertEquals("Updated", savedVolunteer.getLastName());
        assertEquals("updated@example.com", savedVolunteer.getEmail());
        assertEquals("0123456789", savedVolunteer.getPhoneNumber());
        assertTrue(savedVolunteer.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_VOLUNTEER")));
    }
    @Test
    public void testSelfEditVolunteer() {
        UUID existingVolunteerUuid = UUID.randomUUID();
        String authenticatedUserEmail = "authenticated@example.com";
        Role missionRole = new Role();
        missionRole.setId(1L);
        missionRole.setName(RoleName.ROLE_MISSION.name());
        Role volunteerRole = new Role();
        volunteerRole.setId(2L);
        volunteerRole.setName(RoleName.ROLE_VOLUNTEER.name());
        Role adminRole = new Role();
        adminRole.setName(RoleName.ROLE_ADMIN.name());
        adminRole.setId(3L);
        Role guestRole = new Role();
        guestRole.setName(RoleName.ROLE_GUEST.name());
        guestRole.setId(4L);

        VolunteerDetailsDTO dto = new VolunteerDetailsDTO();
        dto.setUuid(existingVolunteerUuid);
        dto.setLastName("Self");
        dto.setFirstName("Edit");
        dto.setEmail(authenticatedUserEmail);
        dto.setPhoneNumber("1234567890");
        dto.setRoles(List.of(RoleName.ROLE_MISSION.name(),RoleName.ROLE_VOLUNTEER.name()));

        Volunteer existingVolunteer = new Volunteer();
        existingVolunteer.setUuid(existingVolunteerUuid);
        existingVolunteer.setEmail(authenticatedUserEmail);
        existingVolunteer.setFirstName("Origin");
        existingVolunteer.setLastName("Self");
        existingVolunteer.setRoles(new ArrayList<>(List.of(volunteerRole, guestRole)));

        Authentication authentication = mock(Authentication.class);
        UserDetails principal = mock(UserDetails.class);
        when(authentication.getPrincipal()).thenReturn(principal);
        when(principal.getUsername()).thenReturn(authenticatedUserEmail);

        when(roleService.findByName(RoleName.ROLE_VOLUNTEER.name())).thenReturn(volunteerRole);
        when(roleService.findByName(RoleName.ROLE_MISSION.name())).thenReturn(missionRole);

        when(volunteerRepository.findByUuid(existingVolunteerUuid)).thenReturn(Optional.of(existingVolunteer));

        volunteerService.saveOrUpdateVolunteerDetails(dto, authentication);

        ArgumentCaptor<Volunteer> volunteerCaptor = ArgumentCaptor.forClass(Volunteer.class);
        verify(volunteerRepository).save(volunteerCaptor.capture());
        Volunteer savedVolunteer = volunteerCaptor.getValue();


        assertEquals("Self", savedVolunteer.getLastName());
        assertEquals("Edit", savedVolunteer.getFirstName());
        assertEquals(authenticatedUserEmail, savedVolunteer.getEmail());


        assertTrue(savedVolunteer.getRoles().stream()
                .anyMatch(role -> role.getName().equals(RoleName.ROLE_VOLUNTEER.name())));
        assertTrue(savedVolunteer.getRoles().stream()
                .anyMatch(role -> role.getName().equals(RoleName.ROLE_MISSION.name())));
    }
    @Test
    public void testAddAdminRoleToVolunteer() {
        UUID existingVolunteerUuid = UUID.randomUUID();
        Role missionRole = new Role();
        missionRole.setId(1L);
        missionRole.setName(RoleName.ROLE_MISSION.name());
        Role volunteerRole = new Role();
        volunteerRole.setId(2L);
        volunteerRole.setName(RoleName.ROLE_VOLUNTEER.name());
        Role adminRole = new Role();
        adminRole.setName(RoleName.ROLE_ADMIN.name());
        adminRole.setId(3L);

        VolunteerDetailsDTO dto = new VolunteerDetailsDTO();
        dto.setUuid(existingVolunteerUuid);
        dto.setRoles(List.of(RoleName.ROLE_ADMIN.name()));

        Volunteer existingVolunteer = new Volunteer();
        existingVolunteer.setUuid(existingVolunteerUuid);
        existingVolunteer.setRoles(Arrays.asList(missionRole,volunteerRole));

        when(volunteerRepository.findByUuid(existingVolunteerUuid)).thenReturn(Optional.of(existingVolunteer));

        when(roleService.findByName(RoleName.ROLE_ADMIN.name())).thenReturn(adminRole);

        when(volunteerRepository.save(any(Volunteer.class))).thenReturn(existingVolunteer);

        volunteerService.saveOrUpdateVolunteerDetails(dto, mock(Authentication.class));

        ArgumentCaptor<Volunteer> volunteerCaptor = ArgumentCaptor.forClass(Volunteer.class);
        verify(volunteerRepository).save(volunteerCaptor.capture());
        Volunteer savedVolunteer = volunteerCaptor.getValue();

        assertTrue(savedVolunteer.getRoles().contains(adminRole));
    }
    @Test
    public void testResetPassword() {
        String email = "test@example.com";
        boolean resetPassword = true;
        Role adminRole = new Role();
        adminRole.setName(RoleName.ROLE_ADMIN.name());
        adminRole.setId(3L);

        Volunteer volunteer = new Volunteer();
        volunteer.setEmail(email);
        volunteer.setRoles(List.of(adminRole));

        when(volunteerRepository.findByEmail(email)).thenReturn(volunteer);
        when(passwordEncoder.encode(anyString())).thenAnswer(invocation -> invocation.getArgument(0));

        volunteerService.resetPassword(resetPassword, email);

        ArgumentCaptor<Volunteer> volunteerCaptor = ArgumentCaptor.forClass(Volunteer.class);
        verify(volunteerRepository).save(volunteerCaptor.capture());
        Volunteer updatedVolunteer = volunteerCaptor.getValue();

        assertNotNull(updatedVolunteer.getPassword());
        assertEquals("admin", updatedVolunteer.getPassword());
    }
    @Test
    public void testUpdateAdminVolunteer() {
        UUID existingVolunteerUuid = UUID.randomUUID();
        Role missionRole = new Role();
        missionRole.setId(1L);
        missionRole.setName(RoleName.ROLE_MISSION.name());
        Role volunteerRole = new Role();
        volunteerRole.setId(2L);
        volunteerRole.setName(RoleName.ROLE_VOLUNTEER.name());
        Role adminRole = new Role();
        adminRole.setName(RoleName.ROLE_ADMIN.name());
        adminRole.setId(3L);
        Role guestRole = new Role();
        guestRole.setName(RoleName.ROLE_GUEST.name());
        guestRole.setId(4L);

        VolunteerDetailsDTO dto = new VolunteerDetailsDTO();
        dto.setUuid(existingVolunteerUuid);
        dto.setRoles(List.of(RoleName.ROLE_ADMIN.name(), RoleName.ROLE_MISSION.name(), RoleName.ROLE_VOLUNTEER.name()));

        Volunteer existingVolunteer = new Volunteer();
        existingVolunteer.setUuid(existingVolunteerUuid);
        existingVolunteer.setRoles(List.of(adminRole));

        when(volunteerRepository.findByUuid(existingVolunteerUuid)).thenReturn(Optional.of(existingVolunteer));
        when(roleService.findByName(RoleName.ROLE_ADMIN.name())).thenReturn(adminRole);
        when(roleService.findByName(RoleName.ROLE_MISSION.name())).thenReturn(missionRole);
        when(roleService.findByName(RoleName.ROLE_VOLUNTEER.name())).thenReturn(volunteerRole);
        when(roleService.findByName(RoleName.ROLE_GUEST.name())).thenReturn(guestRole);
        when(volunteerRepository.save(any(Volunteer.class))).thenReturn(existingVolunteer);

        volunteerService.saveOrUpdateVolunteerDetails(dto, mock(Authentication.class));

        ArgumentCaptor<Volunteer> volunteerCaptor = ArgumentCaptor.forClass(Volunteer.class);
        verify(volunteerRepository).save(volunteerCaptor.capture());
        Volunteer savedVolunteer = volunteerCaptor.getValue();

        assertTrue(savedVolunteer.getRoles().contains(adminRole));
        assertFalse(savedVolunteer.getRoles().contains(missionRole));
        assertFalse(savedVolunteer.getRoles().contains(volunteerRole));
        assertTrue(savedVolunteer.getRoles().contains(guestRole));
    }
    @Test
    public void testSaveOrUpdateNewVolunteer() {

        VolunteerDTO volunteerDTO = new VolunteerDTO();
        volunteerDTO.setLastName("NewLastName");
        volunteerDTO.setFirstName("NewFirstName");
        volunteerDTO.setEmail("new@example.com");
        volunteerDTO.setPhoneNumber("1234567890");
        volunteerDTO.setAvailabilities(Collections.emptyList());
        volunteerDTO.setUnavailabilities(Collections.emptyList());
        volunteerDTO.setMissionTypes(Collections.emptyList());
        Volunteer volunteer = new Volunteer();
        volunteer.setId(1L);
        volunteer.setUuid(UUID.randomUUID());
        volunteer.setLastName("NewLastName");
        volunteer.setFirstName("NewFirstName");
        volunteer.setEmail("new@example.com");
        volunteer.setPhoneNumber("1234567890");


        Role guestRole = new Role();
        guestRole.setId(1L);
        guestRole.setName(RoleName.ROLE_GUEST.name());
        when(roleService.findByName(RoleName.ROLE_GUEST.name())).thenReturn(guestRole);
        when(volunteerRepository.save(any(Volunteer.class))).thenReturn(volunteer);

        volunteerService.saveOrUpdate(volunteerDTO);
        verify(volunteerRepository).save(any(Volunteer.class));

        ArgumentCaptor<Volunteer> volunteerCaptor = ArgumentCaptor.forClass(Volunteer.class);
        verify(volunteerRepository).save(volunteerCaptor.capture());
        Volunteer savedVolunteer = volunteerCaptor.getValue();

        assertEquals("NewLastName", savedVolunteer.getLastName());
        assertEquals("NewFirstName", savedVolunteer.getFirstName());
        assertEquals("new@example.com", savedVolunteer.getEmail());
        assertEquals("1234567890", savedVolunteer.getPhoneNumber());
        assertTrue(savedVolunteer.getRoles().contains(guestRole));
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

        assertThrows(EntityNotFoundException.class, () -> volunteerService.findVolunteerDTOByUuid(uuid));
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
    public void deleteNonAdminVolunteer() {
        UUID uuid = UUID.randomUUID();
        Role volunteerRole = new Role();
        volunteerRole.setId(1L);
        volunteerRole.setName(RoleName.ROLE_VOLUNTEER.name());
        Volunteer volunteer = mock(Volunteer.class);
        when(volunteerRepository.findByUuid(uuid)).thenReturn(Optional.of(volunteer));
        when(volunteer.getRoles()).thenReturn(Collections.singletonList(volunteerRole));

        volunteerService.deleteVolunteer(uuid);

        verify(volunteerRepository).deleteByUuid(uuid);
        verify(availabilityService).deleteAllByVolunteerUuid(uuid);
        verify(unavailabilityService).deleteAllByVolunteerUuid(uuid);
    }

    @Test
    public void deleteAdminVolunteerWithMultipleAdmins() {
        UUID uuid = UUID.randomUUID();
        Role adminRole = new Role();
        adminRole.setId(1L);
        adminRole.setName(RoleName.ROLE_ADMIN.name());
        Volunteer volunteer = mock(Volunteer.class);
        when(volunteerRepository.findByUuid(uuid)).thenReturn(Optional.of(volunteer));
        when(volunteer.getRoles()).thenReturn(Collections.singletonList(adminRole));
        when(volunteerRepository.countByRoleName("ROLE_ADMIN")).thenReturn(2L);

        volunteerService.deleteVolunteer(uuid);

        verify(volunteerRepository).deleteByUuid(uuid);
        verify(availabilityService).deleteAllByVolunteerUuid(uuid);
        verify(unavailabilityService).deleteAllByVolunteerUuid(uuid);
    }

    @Test
    public void throwExceptionWhenDeletingOnlyAdmin() {
        UUID uuid = UUID.randomUUID();
        Role adminRole = new Role();
        adminRole.setId(1L);
        adminRole.setName(RoleName.ROLE_ADMIN.name());
        Volunteer volunteer = mock(Volunteer.class);
        when(volunteerRepository.findByUuid(uuid)).thenReturn(Optional.of(volunteer));
        when(volunteer.getRoles()).thenReturn(Collections.singletonList(adminRole));
        when(volunteerRepository.countByRoleName("ROLE_ADMIN")).thenReturn(1L);

        assertThrows(DeleteAdminException.class, () -> volunteerService.deleteVolunteer(uuid));
    }

    @Test
    public void throwExceptionWhenVolunteerDoesNotExist() {
        UUID uuid = UUID.randomUUID();
        when(volunteerRepository.findByUuid(uuid)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> volunteerService.deleteVolunteer(uuid));
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

        assertThrows(EntityNotFoundException.class, () -> volunteerService.findVolunteerProfilDTOByUuid(uuid));
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

        assertThrows(EntityNotFoundException.class, () -> volunteerService.findVolunteerDetailsByUuid(uuid));
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

        assertThrows(UsernameNotFoundException.class, () -> volunteerService.findVolunteerRoleProfilByEmail(email));
    }
    @Test
    void changePassword_VolunteerNotFound() {
        String email = "nonexistent@example.com";

        when(volunteerRepository.findByEmail(email)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> volunteerService.changePassword(email, "oldPassword", "newPassword"));
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
