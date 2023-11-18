package com.mickc0.gtac.repository;

import com.mickc0.gtac.entity.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability,Long> {
    List<Availability> findByVolunteerId(Long volunteerId);
}
