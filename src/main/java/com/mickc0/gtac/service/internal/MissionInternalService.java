package com.mickc0.gtac.service.internal;

import com.mickc0.gtac.dto.MissionDTO;
import com.mickc0.gtac.model.MissionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissionInternalService {

    void saveMissionWithType(MissionDTO missionDTO);
}
