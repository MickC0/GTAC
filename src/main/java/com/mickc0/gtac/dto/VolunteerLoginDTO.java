package com.mickc0.gtac.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public class VolunteerLoginDTO {
    @Email(message = "Adresse email non valide.")
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
