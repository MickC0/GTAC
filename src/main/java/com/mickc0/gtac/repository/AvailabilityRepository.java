package com.mickc0.gtac.repository;

import com.mickc0.gtac.entity.Availability;
import com.mickc0.gtac.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AvailabilityRepository extends JpaRepository<Availability,Long> {
    List<Availability> findByVolunteerId(Long volunteerId);

    void deleteByUuid(UUID uuid);

    void deleteAllByVolunteer(Volunteer volunteer);

    void deleteAllByVolunteerUuid(UUID uuid);

    Availability findByUuid(UUID uuid);
}
