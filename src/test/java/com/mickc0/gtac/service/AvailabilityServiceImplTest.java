package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.AvailabilityDTO;
import com.mickc0.gtac.dto.UnavailabilityDTO;
import com.mickc0.gtac.entity.Availability;
import com.mickc0.gtac.entity.Unavailability;
import com.mickc0.gtac.entity.Volunteer;
import com.mickc0.gtac.mapper.AvailabilityMapper;
import com.mickc0.gtac.repository.AvailabilityRepository;
import com.mickc0.gtac.service.AvailabilityServiceImpl;
import com.mickc0.gtac.service.UnavailabilityServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class AvailabilityServiceImplTest {

    private AvailabilityServiceImpl availabilityService;
    @Mock
    private AvailabilityMapper availabilityMapper;
    @Mock
    private AvailabilityRepository availabilityRepository;

    @BeforeEach
    void initService() {
        availabilityService = new AvailabilityServiceImpl(availabilityRepository,availabilityMapper);
    }

    @Test
    void createAvailability() {
        Availability availability = new Availability();
        when(availabilityRepository.save(isA(Availability.class))).thenAnswer(i -> i.getArgument(0));


        Availability created = availabilityService.createAvailability(availability);

        assertNotNull(created);
        verify(availabilityRepository).save(availability);
    }

    @Test
    void getAllAvailabilities() {
        List<Availability> availabilities = Arrays.asList(new Availability(), new Availability());
        when(availabilityRepository.findAll()).thenReturn(availabilities);

        List<Availability> result = availabilityService.getAllAvailabilities();

        assertEquals(2, result.size());
        verify(availabilityRepository).findAll();
    }
    @Test
    void deleteById() {
        Long id = 1L;
        doNothing().when(availabilityRepository).deleteById(id);

        availabilityService.deleteById(id);

        verify(availabilityRepository).deleteById(id);
    }
    @Test
    void deleteAvailability() {
        Availability availability = new Availability();
        doNothing().when(availabilityRepository).delete(availability);

        availabilityService.delete(availability);

        verify(availabilityRepository).delete(availability);
    }

    @Test
    void deleteByUuid() {
        UUID uuid = UUID.randomUUID();
        doNothing().when(availabilityRepository).deleteByUuid(uuid);

        availabilityService.deleteByUuid(uuid);

        verify(availabilityRepository).deleteByUuid(uuid);
    }
    @Test
    void deleteAllByVolunteer() {
        Volunteer volunteer = new Volunteer();
        doNothing().when(availabilityRepository).deleteAllByVolunteer(volunteer);

        availabilityService.deleteAllByVolunteer(volunteer);

        verify(availabilityRepository).deleteAllByVolunteer(volunteer);
    }

    @Test
    void deleteAllByVolunteerUuid() {
        UUID uuid = UUID.randomUUID();
        doNothing().when(availabilityRepository).deleteAllByVolunteerUuid(uuid);

        availabilityService.deleteAllByVolunteerUuid(uuid);

        verify(availabilityRepository).deleteAllByVolunteerUuid(uuid);
    }
    @Test
    void findAvailabilityDtoByUuid_Found() {
        UUID uuid = UUID.randomUUID();
        Long id = 1L;
        Availability availability = new Availability();
        availability.setUuid(uuid);
        availability.setId(id);

        when(availabilityRepository.findByUuid(uuid)).thenReturn(Optional.of(availability));
        when(availabilityMapper.mapToDto(ArgumentMatchers.any(Availability.class))).thenAnswer(invocation -> {
            Availability availabilityToInvoke = invocation.getArgument(0);
            AvailabilityDTO availabilityDTO = new AvailabilityDTO();
            availabilityDTO.setUuid(availabilityToInvoke.getUuid());
            return availabilityDTO;
        });

        AvailabilityDTO result = availabilityService.findAvailabilityDtoByUuid(uuid);

        assertNotNull(result);

    }

    @Test
    void findAvailabilityDtoByUuid_NotFound() {
        UUID uuid = UUID.randomUUID();
        when(availabilityRepository.findByUuid(uuid)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> availabilityService.findAvailabilityDtoByUuid(uuid));
    }

    @Test
    void findByUuid_Found() {
        UUID uuid = UUID.randomUUID();
        Availability availability = new Availability();
        availability.setUuid(uuid);

        when(availabilityRepository.findByUuid(uuid)).thenReturn(Optional.of(availability));

        Availability result = availabilityService.findByUuid(uuid);

        assertNotNull(result);
        assertEquals(uuid, result.getUuid());
    }

    @Test
    void findByUuid_NotFound() {
        UUID uuid = UUID.randomUUID();
        when(availabilityRepository.findByUuid(uuid)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> availabilityService.findByUuid(uuid));
    }






}
