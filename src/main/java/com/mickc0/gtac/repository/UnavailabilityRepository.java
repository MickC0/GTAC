package com.mickc0.gtac.repository;

import com.mickc0.gtac.entity.Unavailability;
import com.mickc0.gtac.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UnavailabilityRepository extends JpaRepository<Unavailability, Long> {
    List<Unavailability> findByVolunteerId(Long volunteerId);

    Optional<Unavailability> findByUuid(UUID uuid);

    @Query("SELECT u FROM Unavailability u WHERE u.volunteer = :volunteer AND u.startDate <= :date AND u.endDate >= :date")
    List<Unavailability> findByVolunteerAndDate(@Param("volunteer") Volunteer volunteer, @Param("date") LocalDate date);

    @Modifying
    @Query("DELETE FROM Unavailability u WHERE u.endDate < :date")
    void deleteByEndDateBefore(@Param("date") LocalDate date);
}
