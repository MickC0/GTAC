package com.mickc0.gtac.repository;

import com.mickc0.gtac.model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
}
