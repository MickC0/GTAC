package com.mickc0.gtac.service;

import com.mickc0.gtac.model.CompletionDate;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CompletionDateService {
    List<CompletionDate> findAllByMissionId(Long missionId);
    void save(CompletionDate completionDate);
}
