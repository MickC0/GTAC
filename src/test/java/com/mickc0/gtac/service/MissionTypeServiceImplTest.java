package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.MissionTypeDTO;
import com.mickc0.gtac.entity.MissionType;
import com.mickc0.gtac.mapper.MissionTypeMapper;
import com.mickc0.gtac.repository.MissionTypeRepository;
import com.mickc0.gtac.service.MissionTypeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class MissionTypeServiceImplTest {

    private MissionTypeServiceImpl missionTypeService;
    @Mock
    private MissionTypeRepository missionTypeRepository;
    @Mock
    private MissionTypeMapper missionTypeMapper;

    @BeforeEach
    void initService() {
        missionTypeService = new MissionTypeServiceImpl(missionTypeRepository,missionTypeMapper);
    }

    @Test
    void saveMissionType() {
        MissionTypeDTO missionTypeDTO = new MissionTypeDTO();
        missionTypeDTO.setName("Mission Name");
        missionTypeDTO.setDescription("Description");

        MissionType missionType = new MissionType();
        missionType.setName(missionTypeDTO.getName());
        missionType.setDescription(missionTypeDTO.getDescription());

        when(missionTypeRepository.save(any(MissionType.class))).thenReturn(missionType);

        missionTypeService.save(missionTypeDTO);

        verify(missionTypeRepository).save(any(MissionType.class));
    }
    @Test
    void findAllMissionTypes() {
        List<MissionType> missionTypes = Arrays.asList(new MissionType(), new MissionType());
        when(missionTypeRepository.findAll()).thenReturn(missionTypes);

        List<MissionType> result = missionTypeService.findAll();

        assertEquals(2, result.size());
        verify(missionTypeRepository).findAll();
    }
    @Test
    void findAllMissionTypesById() {
        List<Long> ids = Arrays.asList(1L, 2L);
        when(missionTypeRepository.findAllById(ids)).thenReturn(Arrays.asList(new MissionType(), new MissionType()));

        List<MissionType> result = missionTypeService.findAllById(ids);

        assertEquals(2, result.size());
        verify(missionTypeRepository).findAllById(ids);
    }
    @Test
    void findAllMissionTypeDTOs() {
        List<MissionType> missionTypes = Arrays.asList(new MissionType(), new MissionType());
        when(missionTypeRepository.findAll(any(Sort.class))).thenReturn(missionTypes);

        List<MissionTypeDTO> result = missionTypeService.findAllDto();

        assertEquals(2, result.size());
        verify(missionTypeRepository).findAll(any(Sort.class));
    }
    @Test
    void deleteMissionTypeByUuid() {
        UUID uuid = UUID.randomUUID();
        MissionType missionType = new MissionType();
        missionType.setUuid(uuid);
        missionType.setMissions(Collections.emptyList());
        when(missionTypeRepository.findByUuid(uuid)).thenReturn(Optional.of(missionType));
        doNothing().when(missionTypeRepository).deleteByUuid(uuid);

        missionTypeService.deleteByUuid(uuid);

        verify(missionTypeRepository).deleteByUuid(uuid);
    }
    @Test
    void findMissionTypeDTOByUuid_Found() {
        UUID uuid = UUID.randomUUID();
        MissionType missionType = new MissionType();
        missionType.setUuid(uuid);

        when(missionTypeRepository.findByUuid(uuid)).thenReturn(Optional.of(missionType));
        when(missionTypeMapper.mapToMissionTypeDto(any(MissionType.class))).thenAnswer(invocation -> {
            MissionTypeDTO missionTypeDTO = new MissionTypeDTO();
            missionTypeDTO.setUuid(missionType.getUuid());
            missionTypeDTO.setName(missionType.getName());
            missionTypeDTO.setSelected(false);
            missionTypeDTO.setActive(missionType.isActive());
            return missionTypeDTO;
        });

        Optional<MissionTypeDTO> result = missionTypeService.findMissionTypeDTOByUuid(uuid);

        assertTrue(result.isPresent());
    }

    @Test
    void findMissionTypeDTOByUuid_NotFound() {
        UUID uuid = UUID.randomUUID();
        when(missionTypeRepository.findByUuid(uuid)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> missionTypeService.findMissionTypeDTOByUuid(uuid));
    }
    @Test
    void findAllActiveMissionTypes() {
        List<MissionType> missionTypes = Arrays.asList(new MissionType(), new MissionType());
        when(missionTypeRepository.findAllActive(any(Sort.class))).thenReturn(missionTypes);

        List<MissionTypeDTO> result = missionTypeService.findAllActive();

        assertEquals(2, result.size());
        verify(missionTypeRepository).findAllActive(any(Sort.class));
    }

}
