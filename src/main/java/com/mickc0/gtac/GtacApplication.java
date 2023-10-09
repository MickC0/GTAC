package com.mickc0.gtac;

import com.mickc0.gtac.model.Mission;
import com.mickc0.gtac.model.MissionStatus;
import com.mickc0.gtac.repository.MissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GtacApplication implements CommandLineRunner {

    @Autowired
    private MissionRepository missionRepository;

    public static void main(String[] args) {
        SpringApplication.run(GtacApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Mission mission = Mission.builder()
                .name("Nom")
                .description("description")
                .comment("commentaire")
                .status(MissionStatus.NEW)
                .requiredVolunteerNumber(1)
                .missionType("Type mission")
                .build();
        Mission mission2 = Mission.builder()
                .name("Nom2")
                .description("description2")
                .comment("commentaire2")
                .status(MissionStatus.COMPLETED)
                .requiredVolunteerNumber(2)
                .missionType("Type mission2")
                .build();
        missionRepository.save(mission);
        missionRepository.save(mission2);
    }
}
