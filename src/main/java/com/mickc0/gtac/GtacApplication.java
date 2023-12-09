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

        Role role = new Role();
        role.setName(String.valueOf(RoleName.ROLE_ADMIN));
        roleRepository.save(role);

        Volunteer volunteer = new Volunteer();
        volunteer.setEmail("admin@gmail.com");
        volunteer.setPassword(passwordEncoder.encode("admin"));
        volunteer.setRoles(List.of(role));
        volunteerRepository.save(volunteer);


    }
}
