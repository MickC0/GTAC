package com.mickc0.gtac.batch;

import com.mickc0.gtac.entity.Mission;
import com.mickc0.gtac.entity.Unavailability;
import com.mickc0.gtac.service.MissionService;
import com.mickc0.gtac.service.UnavailabilityService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class MissionScheduler {

    private final MissionService missionService;
    private final UnavailabilityService unavailabilityService;

    public MissionScheduler(MissionService missionService, UnavailabilityService unavailabilityService) {
        this.missionService = missionService;
        this.unavailabilityService = unavailabilityService;
    }


    @Scheduled(cron = "0 0 * * * *") // À la minute 0 de chaque heure
    public void updateMissionStatuses() {
        LocalDateTime now = LocalDateTime.now();
        List<Mission> missionsToUpdate = missionService.findMissionsToUpdateStatus(now);
        for (Mission mission : missionsToUpdate) {
            missionService.updateMissionStatus(mission.getId(), now);
        }
    }

    @Scheduled(cron = "0 0 1-11 * * ?")
    public void removeExpiredUnavailabilities() {
        unavailabilityService.removeExpiredUnavailabilities();
    }
    /*
        0 : Les secondes (déclenche l'action à la 0ème seconde).
        0 : Les minutes (déclenche l'action à la 0ème minute).
        1-11 : L'heure (déclenche l'action toutes les heures de 1h00 à 11h00 inclus).
        * : Le jour du mois (n'importe quel jour du mois).
        * : Le mois (n'importe quel mois de l'année).
        ? : Le jour de la semaine (pas de valeur spécifique, permettant une exécution quotidienne).

     */
}
