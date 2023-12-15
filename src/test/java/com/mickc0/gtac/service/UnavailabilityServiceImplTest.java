package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.UnavailabilityDTO;
import com.mickc0.gtac.entity.Unavailability;
import com.mickc0.gtac.entity.Volunteer;
import com.mickc0.gtac.mapper.UnavailabilityMapper;
import com.mickc0.gtac.repository.UnavailabilityRepository;
import com.mickc0.gtac.service.UnavailabilityServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UnavailabilityServiceImplTest {

    private UnavailabilityServiceImpl unavailabilityService;
    @Mock
    private UnavailabilityRepository unavailabilityRepository;
    @Mock
    private UnavailabilityMapper unavailabilityMapper;
    @BeforeEach
    void initService() {
        unavailabilityService = new UnavailabilityServiceImpl(unavailabilityRepository,unavailabilityMapper);
    }


    @Test
    void findAll_ReturnsAllUnavailabilities() {
        Unavailability unavailability1 = new Unavailability();
        unavailability1.setId(1L);
        unavailability1.setUuid(UUID.randomUUID());

        Unavailability unavailability2 = new Unavailability();
        unavailability2.setId(2L);
        unavailability2.setUuid(UUID.randomUUID());

        when(unavailabilityRepository.findAll()).thenReturn(Arrays.asList(unavailability1, unavailability2));
        when(unavailabilityMapper.mapToDto(any(Unavailability.class))).thenAnswer(invocation -> {
            Unavailability unavailability = invocation.getArgument(0);
            UnavailabilityDTO unavailabilityDTO = new UnavailabilityDTO();
            unavailabilityDTO.setUuid(unavailability.getUuid());
            unavailabilityDTO.setStartDate(unavailability.getStartDate());
            unavailabilityDTO.setEndDate(unavailability.getEndDate());
            return unavailabilityDTO;
        });

        List<UnavailabilityDTO> result = unavailabilityService.findAll();

        assertEquals(2, result.size());
        assertEquals(unavailability1.getUuid(), result.get(0).getUuid());
        assertEquals(unavailability2.getUuid(), result.get(1).getUuid());
    }

    @Test
    void findById_ReturnsUnavailabilityDTO() {
        Long id = 1L;
        Unavailability unavailability = new Unavailability();
        unavailability.setId(id);
        unavailability.setUuid(UUID.randomUUID());

        when(unavailabilityRepository.findById(id)).thenReturn(Optional.of(unavailability));
        when(unavailabilityMapper.mapToDto(any(Unavailability.class))).thenAnswer(invocation -> {
            UnavailabilityDTO unavailabilityDTO = new UnavailabilityDTO();
            unavailabilityDTO.setUuid(unavailability.getUuid());
            unavailabilityDTO.setStartDate(unavailability.getStartDate());
            unavailabilityDTO.setEndDate(unavailability.getEndDate());
            return unavailabilityDTO;
        });

        UnavailabilityDTO result = unavailabilityService.findById(id);

        assertNotNull(result);
        assertEquals(unavailability.getUuid(), result.getUuid());

    }

    @Test
    void findById_ThrowsEntityNotFoundException() {
        Long id = 2L;
        when(unavailabilityRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> unavailabilityService.findById(id));
    }
    @Test
    void removeExpiredUnavailabilities() {
        LocalDate today = LocalDate.now();
        doNothing().when(unavailabilityRepository).deleteByEndDateBefore(today);

        unavailabilityService.removeExpiredUnavailabilities();

        verify(unavailabilityRepository).deleteByEndDateBefore(today);
    }
    @Test
    void findUnavailabilityDtoByUuid_Found() {
        Long id = 1L;
        UUID uuid = UUID.randomUUID();
        Unavailability unavailability = new Unavailability();
        unavailability.setId(id);
        unavailability.setUuid(uuid);

        when(unavailabilityRepository.findByUuid(uuid)).thenReturn(Optional.of(unavailability));
        when(unavailabilityMapper.mapToDto(any(Unavailability.class))).thenAnswer(invocation -> {
            UnavailabilityDTO unavailabilityDTO = new UnavailabilityDTO();
            unavailabilityDTO.setUuid(unavailability.getUuid());
            unavailabilityDTO.setStartDate(unavailability.getStartDate());
            unavailabilityDTO.setEndDate(unavailability.getEndDate());
            return unavailabilityDTO;
        });

        UnavailabilityDTO result = unavailabilityService.findUnavailabilityDtoByUuid(uuid);

        assertNotNull(result);

    }

    @Test
    void findUnavailabilityDtoByUuid_NotFound() {
        UUID uuid = UUID.randomUUID();
        when(unavailabilityRepository.findByUuid(uuid)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> unavailabilityService.findUnavailabilityDtoByUuid(uuid));
    }
    @Test
    void deleteUnavailability() {
        Long id = 1L;
        Unavailability unavailability = new Unavailability();
        unavailability.setId(id);
        unavailability.setUuid(UUID.randomUUID());

        doNothing().when(unavailabilityRepository).delete(unavailability);

        unavailabilityService.delete(unavailability);

        verify(unavailabilityRepository).delete(unavailability);
    }
    @Test
    void deleteAllByVolunteer() {
        Volunteer volunteer = new Volunteer();
        volunteer.setLastName("DUPONT");
        volunteer.setFirstName("JEAN");
        volunteer.setEmail("jean.dupont@gmail.com");
        volunteer.setPhoneNumber("0606060606");
        doNothing().when(unavailabilityRepository).deleteAllByVolunteer(volunteer);

        unavailabilityService.deleteAllByVolunteer(volunteer);

        verify(unavailabilityRepository).deleteAllByVolunteer(volunteer);
    }
    @Test
    void deleteAllByVolunteerUuid() {
        UUID uuid = UUID.randomUUID();

        doNothing().when(unavailabilityRepository).deleteAllByVolunteerUuid(uuid);

        unavailabilityService.deleteAllByVolunteerUuid(uuid);

        verify(unavailabilityRepository).deleteAllByVolunteerUuid(uuid);
    }
    @Test
    void isVolunteerUnavailableOnDate() {
        Volunteer volunteer = new Volunteer();
        LocalDate date = LocalDate.now();

        when(unavailabilityRepository.findByVolunteerAndDate(volunteer, date)).thenReturn(Collections.emptyList());

        boolean result = unavailabilityService.isVolunteerUnavailableOnDate(volunteer, date);

        assertFalse(result);
    }


}
