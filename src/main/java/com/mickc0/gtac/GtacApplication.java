package com.mickc0.gtac;

import com.mickc0.gtac.dto.MissionDTO;
import com.mickc0.gtac.model.MissionStatus;
import com.mickc0.gtac.model.MissionType;
import com.mickc0.gtac.service.MissionService;
import com.mickc0.gtac.service.MissionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication
public class GtacApplication implements CommandLineRunner {

    @Autowired
    private MissionService missionService;
    @Autowired
    private MissionTypeService missionTypeService;


    public static void main(String[] args) {
        SpringApplication.run(GtacApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {


        MissionType missionType = new MissionType();
        missionType.setName("Type1");
        missionType.setUuid(UUID.randomUUID());
        missionTypeService.save(missionType);

        MissionType missionType2 = new MissionType();
        missionType2.setName("Type2");
        missionType2.setUuid(UUID.randomUUID());
        missionTypeService.save(missionType2);


        MissionDTO mission = new MissionDTO();
        mission.setUuid(UUID.randomUUID());
        mission.setName("mission1");
        mission.setComment("comment");
        mission.setMissionType(missionType.getName());
        mission.setDescription("description");
        mission.setRequiredVolunteerNumber(1);
        mission.setStatus(MissionStatus.NEW);
        mission.setStartingDate(LocalDateTime.of(2023,10,10,22,00));
        mission.setEndingDate(LocalDateTime.of(2023,10,10,22,00));
        missionService.saveMission(mission);

        MissionDTO mission2 = new MissionDTO();
        mission2.setName("mission2");
        mission2.setUuid(UUID.randomUUID());
        mission2.setComment("comment");
        mission2.setMissionType(missionType2.getName());
        mission2.setDescription("description");
        mission2.setRequiredVolunteerNumber(10);
        mission2.setStatus(MissionStatus.NEW);
        mission2.setStartingDate(LocalDateTime.of(2023,11,10,22,00));
        mission2.setEndingDate(LocalDateTime.of(2023,11,10,22,00));
        missionService.saveMission(mission2);




    }
}
