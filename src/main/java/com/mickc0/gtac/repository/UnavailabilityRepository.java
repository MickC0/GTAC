package com.mickc0.gtac.repository;

import com.mickc0.gtac.entity.Unavailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnavailabilityRepository extends JpaRepository<Unavailability, Long> {
    List<Unavailability> findByVolunteerId(Long volunteerId);
}
