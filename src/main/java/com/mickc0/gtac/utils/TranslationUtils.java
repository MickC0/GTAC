package com.mickc0.gtac.utils;

import org.springframework.stereotype.Component;

@Component
public class TranslationUtils {
    public String translateDayOfWeek(String day) {
        return switch (day) {
            case "MONDAY" -> "Lundi";
            case "TUESDAY" -> "Mardi";
            case "WEDNESDAY" -> "Mercredi";
            case "THURSDAY" -> "Jeudi";
            case "FRIDAY" -> "Vendredi";
            case "SATURDAY" -> "Samedi";
            case "SUNDAY" -> "Dimanche";
            default -> day;
        };
    }

}
