package com.mickc0.gtac.repository;

import com.mickc0.gtac.model.MissionVolunteer;
import com.mickc0.gtac.model.MissionVolunteerKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissionVolunteerRepository extends JpaRepository<MissionVolunteer, MissionVolunteerKey> {

}
