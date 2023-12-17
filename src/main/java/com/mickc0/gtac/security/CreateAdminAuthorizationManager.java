package com.mickc0.gtac.security;

import com.mickc0.gtac.service.VolunteerService;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.function.Supplier;

public class CreateAdminAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final VolunteerService volunteerService;

    public CreateAdminAuthorizationManager(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        String requestPath = context.getRequest().getRequestURI();
        if ("/create-admin".equals(requestPath) && volunteerService.existsAdminAccount()) {
            return new AuthorizationDecision(false);
        }
        return new AuthorizationDecision(true);
    }
}

