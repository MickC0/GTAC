package com.mickc0.gtac.repository;

import com.mickc0.gtac.model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    Volunteer findVolunteerByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);

    @Query("SELECT v from Volunteer  v WHERE " +
            "v.lastName LIKE CONCAT('%', :query, '%') OR " +
            "v.firstName LIKE CONCAT('%', :query, '%')")
    List<Volunteer> searchVolunteers(String query);

}
