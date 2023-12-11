package com.mickc0.gtac.utils;

import com.mickc0.gtac.entity.MissionStatus;
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

    public static String translateMissionStatus(MissionStatus status) {
        return switch (status) {
            case NEW -> "Nouveau";
            case PLANNED -> "Planifié";
            case CONFIRMED -> "Confirmé";
            case ONGOING -> "En cours";
            case COMPLETED -> "Terminé";
            case CANCELLED -> "Annulé";
            default -> status.toString();
        };
    }

    public static String translateRole(String roleName){
        return switch (roleName) {
            case "ROLE_ADMIN" -> "Administrateur";
            case "ROLE_MISSION" -> "Gestionnaire de missions";
            case "ROLE_VOLUNTEER" -> "Gestionnaire de bénévoles";
            case "ROLE_GUEST" -> "Bénévole";
            default -> "Inconnu";
        };
    }

}
