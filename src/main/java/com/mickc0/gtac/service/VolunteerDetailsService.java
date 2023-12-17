package com.mickc0.gtac.service;

import com.mickc0.gtac.entity.Volunteer;
import com.mickc0.gtac.repository.VolunteerRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class VolunteerDetailsService implements UserDetailsService {

    private final VolunteerRepository volunteerRepository;

    public VolunteerDetailsService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Volunteer volunteer = volunteerRepository.findByEmail(email);
        if (volunteer == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé : " + email);
        } else {

            return new User(
                    volunteer.getEmail(),
                    volunteer.getPassword(),
                    volunteer.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority(role.getName()))
                            .collect(Collectors.toList())
            );
        }
    }
}
