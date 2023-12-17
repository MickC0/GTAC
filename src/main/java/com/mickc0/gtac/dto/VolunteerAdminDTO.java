package com.mickc0.gtac.dto;

import jakarta.validation.constraints.*;


public class VolunteerAdminDTO {
    @Size(min = 3, max = 20)
    @NotEmpty(message = "Renseigner le Nom")
    private String lastName;
    @Size(min = 3, max = 20)
    @NotEmpty(message = "Renseigner le Prénom")
    private String firstName;
    @Email(message = "Adresse email non valide.")
    @NotEmpty(message = "Renseigner l'email")
    private String email;
    @Pattern(regexp = "(\\+33\\s?([0-9]{2}\\s?){4}|0[0-9]{9})", message = "Numéro de téléphone non valide.")
    private String phoneNumber;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Le mot de passe doit contenir au moins 8 caractères, dont une majuscule, un chiffre et un caractère spécial.")
    private String password;
    private String matchingPassword;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }
}
