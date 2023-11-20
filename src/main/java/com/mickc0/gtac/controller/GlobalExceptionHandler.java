package com.mickc0.gtac.controller;

import com.mickc0.gtac.exception.CustomDuplicateEntryException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomDuplicateEntryException.class)
    public String handleCustomDuplicateEntryException(CustomDuplicateEntryException ex, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String referrerUrl = request.getHeader("Referer");
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:" + (referrerUrl != null ? referrerUrl : "/home");
    }
}
