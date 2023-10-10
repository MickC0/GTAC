package com.mickc0.gtac;

import com.mickc0.gtac.model.CompletionDate;
import com.mickc0.gtac.model.Mission;
import com.mickc0.gtac.model.MissionStatus;
import com.mickc0.gtac.repository.MissionRepository;
import com.mickc0.gtac.service.CompletionDateService;
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
    @Autowired
    private CompletionDateService completionDateService;

    public static void main(String[] args) {
        SpringApplication.run(GtacApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        CompletionDate completionDate = CompletionDate.builder()
                .startingDate(LocalDateTime.of(2023,10,10,22,00))
                .endingDate(LocalDateTime.of(2023, 10,10,23,00))
                .build();
        Mission mission = Mission.builder()
                .name("Nom")
                .description("description")
                .comment("commentaire")
                .status(MissionStatus.NEW)
                .requiredVolunteerNumber(1)
                .missionType("Type mission")
                .completionDates(completionDateService.save(completionDate)
                .build();
        Mission mission2 = Mission.builder()
                .name("Nom2")
                .description("description2")
                .comment("commentaire2")
                .status(MissionStatus.COMPLETED)
                .requiredVolunteerNumber(2)
                .missionType("Type mission2")
                .build();
        missionService.save(mission);
        missionService.save(mission2);
    }
}