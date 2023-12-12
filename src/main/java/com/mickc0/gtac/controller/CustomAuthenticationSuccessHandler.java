package com.mickc0.gtac.controller;

import com.mickc0.gtac.entity.Volunteer;
import com.mickc0.gtac.service.VolunteerService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final VolunteerService volunteerService;

    public CustomAuthenticationSuccessHandler(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            Volunteer volunteer = volunteerService.findByEmail(username)
                    .orElseThrow(()-> new EntityNotFoundException("Le bénévole avec l'email : " + username + " n'existe pas."));

            if (volunteer.isMustChangePassword()) {
                response.sendRedirect("/change-password");
            } else {
                response.sendRedirect("/home");
            }
        } else {
            response.sendRedirect("/login?error");
        }
    }
}

