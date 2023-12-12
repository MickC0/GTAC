package com.mickc0.gtac.security;

import com.mickc0.gtac.controller.CustomAuthenticationSuccessHandler;
import com.mickc0.gtac.service.VolunteerDetailsService;
import com.mickc0.gtac.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final VolunteerDetailsService volunteerDetailsService;
    private final VolunteerService volunteerService;

    public SecurityConfiguration(VolunteerDetailsService volunteerDetailsService, VolunteerService volunteerService) {
        this.volunteerDetailsService = volunteerDetailsService;
        this.volunteerService = volunteerService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/webjars/**").permitAll()
                        .requestMatchers("/", "/home").permitAll()
                        .requestMatchers("/missions/**").hasAnyRole("MISSION", "ADMIN")
                        .requestMatchers("/mission-types/**").hasAnyRole("MISSION", "ADMIN")
                        .requestMatchers("/volunteers/**").hasAnyRole("VOLUNTEER", "ADMIN")
                        .requestMatchers("/administration/**").hasRole("ADMIN")
                        .anyRequest().authenticated()

                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler(new CustomAuthenticationSuccessHandler(volunteerService))
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(volunteerDetailsService)
                .passwordEncoder(passwordEncoder());
    }

}
