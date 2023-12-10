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

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private VolunteerRepository volunteerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public static void main(String[] args) {
        SpringApplication.run(GtacApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Role adminRole = new Role();
        adminRole.setName(String.valueOf(RoleName.ROLE_ADMIN));
        roleRepository.save(adminRole);
        Role missionRole = new Role();
        missionRole.setName(String.valueOf(RoleName.ROLE_MISSION));
        roleRepository.save(missionRole);
        Role volunteerRole = new Role();
        volunteerRole.setName(String.valueOf(RoleName.ROLE_VOLUNTEER));
        roleRepository.save(volunteerRole);
        Role guestRole = new Role();
        guestRole.setName(String.valueOf(RoleName.ROLE_GUEST));
        roleRepository.save(guestRole);

        Volunteer volunteer = new Volunteer();
        volunteer.setEmail("admin@gmail.com");
        volunteer.setPassword(passwordEncoder.encode("admin"));
        volunteer.setRoles(List.of(adminRole));
        volunteerRepository.save(volunteer);


    }
}
