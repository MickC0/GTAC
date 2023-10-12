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
import java.util.ArrayList;
import java.util.List;

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



    }
}
