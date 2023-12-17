package com.mickc0.gtac;

import com.mickc0.gtac.entity.Role;
import com.mickc0.gtac.entity.RoleName;
import com.mickc0.gtac.entity.Volunteer;
import com.mickc0.gtac.repository.RoleRepository;
import com.mickc0.gtac.repository.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class GtacApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(GtacApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
