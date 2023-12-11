package com.mickc0.gtac.controller;

import com.mickc0.gtac.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateEntryException.class)
    public String handleCustomDuplicateEntryException(DuplicateEntryException ex, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String referrerUrl = request.getHeader("Referer");
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:" + (referrerUrl != null ? referrerUrl : "/home");
    }

    @ExceptionHandler(MissionTypeInUseException.class)
    public String handleMissionTypeInUseException(MissionTypeInUseException ex, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String referrerUrl = request.getHeader("Referer");
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:" + (referrerUrl != null ? referrerUrl : "/home");
    }

    @ExceptionHandler(VolunteerInUseException.class)
    public String handleVolunteerInUseException(VolunteerInUseException ex, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String referrerUrl = request.getHeader("Referer");
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:" + (referrerUrl != null ? referrerUrl : "/home");
    }

    @ExceptionHandler(VolunteerLoginException.class)
    public String handleVolunteerLoginException(VolunteerLoginException ex, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String referrerUrl = request.getHeader("Referer");
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:" + (referrerUrl != null ? referrerUrl : "/home");
    }

    @ExceptionHandler(PasswordException.class)
    public String handlePasswordException(PasswordException ex, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String referrerUrl = request.getHeader("Referer");
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:" + (referrerUrl != null ? referrerUrl : "/home");
    }


}
