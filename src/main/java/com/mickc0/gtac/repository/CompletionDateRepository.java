package com.mickc0.gtac.repository;

import com.mickc0.gtac.model.CompletionDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompletionDateRepository extends JpaRepository<CompletionDate, Long> {
    @Query(value = "select c from completion_dates c where c.mission_id = :mission_Id")
    List<CompletionDate> findAllByMissionId(@Param("mission_id") Long missionId);
}
