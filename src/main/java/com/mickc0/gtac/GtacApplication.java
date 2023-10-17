package com.mickc0.gtac;

import com.mickc0.gtac.model.Mission;
import com.mickc0.gtac.model.MissionStatus;
import com.mickc0.gtac.model.MissionType;
import com.mickc0.gtac.service.MissionService;
import com.mickc0.gtac.service.MissionTypeService;
import com.mickc0.gtac.service.MissionTypeServiceImpl;
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
    private MissionTypeService missionTypeService;


    public static void main(String[] args) {
        SpringApplication.run(GtacApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {


        MissionType missionType = new MissionType();
        missionType.setName("Type1");
        missionTypeService.save(missionType);

        Mission mission = new Mission();
        mission.setName("mission1");
        mission.setComment("comment");
        mission.setMissionType(missionType.getName());
        mission.setDescription("description");
        mission.setRequiredVolunteerNumber(1);
        mission.setStatus(MissionStatus.NEW);
        mission.setStartingDate(LocalDateTime.of(2023,10,10,22,00));
        mission.setEndingDate(LocalDateTime.of(2023,10,10,22,00));
        missionService.save(mission);




    }
}
