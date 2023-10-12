package com.mickc0.gtac;

import com.mickc0.gtac.model.Mission;
import com.mickc0.gtac.model.MissionStatus;
import com.mickc0.gtac.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class GtacApplication implements CommandLineRunner {

    @Autowired
    private MissionService missionService;


    public static void main(String[] args) {
        SpringApplication.run(GtacApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Mission mission = new Mission();
        mission.setName("mission1");
        mission.setComment("comment");
        mission.setMissionType("type");
        mission.setDescription("description");
        mission.setRequiredVolunteerNumber(1);
        mission.setStatus(MissionStatus.NEW);
        mission.setStartingDate(LocalDateTime.of(2023,10,10,22,00));
        mission.setEndingDate(LocalDateTime.of(2023,10,10,22,00));

        Mission mission2 = new Mission();
        mission2.setName("mission2");
        mission2.setComment("comment");
        mission2.setMissionType("type");
        mission2.setDescription("description");
        mission2.setRequiredVolunteerNumber(10);
        mission2.setStatus(MissionStatus.NEW);
        mission2.setStartingDate(LocalDateTime.of(2023,10,10,22,00));
        mission2.setEndingDate(LocalDateTime.of(2023,10,10,22,00));
        missionService.save(mission);
        missionService.save(mission2);
    }
}
