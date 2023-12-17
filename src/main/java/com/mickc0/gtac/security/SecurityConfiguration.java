package com.mickc0.gtac.security;

import com.mickc0.gtac.controller.CustomAuthenticationSuccessHandler;
import com.mickc0.gtac.service.VolunteerDetailsService;
import com.mickc0.gtac.service.VolunteerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

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
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", "Vous n'avez pas les droits nécessaires pour accéder à cette ressource.");
            response.sendRedirect("/home");
        };
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/create-admin").access(new CreateAdminAuthorizationManager(volunteerService))
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/webjars/**").permitAll()
                        .requestMatchers("/", "/home","/register").permitAll()
                        .requestMatchers("/missions/**").hasAnyRole("MISSION", "ADMIN")
                        .requestMatchers("/mission-types/**").hasAnyRole("MISSION", "ADMIN")
                        .requestMatchers("/volunteers/**").hasAnyRole("VOLUNTEER", "ADMIN")
                        .requestMatchers("/profil/**").hasAnyRole("MISSION", "VOLUNTEER", "ADMIN")
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
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.accessDeniedHandler(accessDeniedHandler())
                );

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(volunteerDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}
