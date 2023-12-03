package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.MissionAssignmentDTO;
import com.mickc0.gtac.mapper.MissionAssignmentMapper;
import com.mickc0.gtac.repository.MissionAssignmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MissionAssignmentServiceImpl implements MissionAssignmentService {
    private final MissionAssignmentRepository missionAssignmentRepository;
    private final MissionAssignmentMapper missionAssignmentMapper;

    public MissionAssignmentServiceImpl(MissionAssignmentRepository missionAssignmentRepository, MissionAssignmentMapper missionAssignmentMapper) {
        this.missionAssignmentRepository = missionAssignmentRepository;
        this.missionAssignmentMapper = missionAssignmentMapper;
    }


    @Override
    public List<MissionAssignmentDTO> findAllCurrentMissionAssignment(UUID uuid) {
        return missionAssignmentRepository.findByMissionUuid(uuid).stream()
                .map(missionAssignmentMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
