package com.mickc0.gtac.unit;

import com.mickc0.gtac.dto.MissionDTO;
import com.mickc0.gtac.entity.Mission;
import com.mickc0.gtac.mapper.MissionMapper;
import com.mickc0.gtac.repository.MissionRepository;
import com.mickc0.gtac.service.MissionServiceImpl;
import com.mickc0.gtac.service.MissionTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MissionServiceImplTest {
    private MissionServiceImpl missionService;
    @Mock
    private MissionRepository missionRepository;
    @Mock
    private MissionMapper missionMapper;
    @Mock
    private MissionTypeService missionTypeService;

    @BeforeEach
    void initService() {
        missionService = new MissionServiceImpl(missionRepository, missionMapper, missionTypeService);
    }

    @Test
    void save_Mission() {
        MissionDTO missionDTO = new MissionDTO();
        missionDTO.setUuid(UUID.randomUUID());
        missionDTO.setTitle("Mission Title");
        // ... Autres attributs de missionDTO

        Mission mission = new Mission();
        // Configurez Mission avec des valeurs de base

        // Simulez getMission et setMissionBaseAttributes si nécessaire
        doReturn(mission).when(missionService).getMission(missionDTO);
        doAnswer(invocation -> {
            MissionDTO dto = invocation.getArgument(0);
            Mission m = invocation.getArgument(1);
            m.setTitle(dto.getTitle());
            // ... Autres attributs copiés de missionDTO à mission
            return null;
        }).when(missionService).setMissionBaseAttributes(missionDTO, mission);

        missionService.save(missionDTO);

        verify(missionRepository).save(any(Mission.class));
        assertEquals(missionDTO.getTitle(), mission.getTitle());
        // ... Autres vérifications d'attributs
    }



}
