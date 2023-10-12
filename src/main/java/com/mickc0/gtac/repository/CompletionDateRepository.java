package com.mickc0.gtac.repository;

import com.mickc0.gtac.model.CompletionDate;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompletionDateRepository extends JpaRepository<CompletionDate, Long> {
    @Transactional
    void deleteByMissionId (Long missionId);

}
