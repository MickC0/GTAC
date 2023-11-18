package com.mickc0.gtac.batch;

import com.mickc0.gtac.entity.Mission;
import com.mickc0.gtac.service.MissionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class MissionScheduler {

    private final MissionService missionService;

    public MissionScheduler(MissionService missionService) {
        this.missionService = missionService;
    }


    @Scheduled(cron = "0 0 * * * *") // À la minute 0 de chaque heure
    public void updateMissionStatuses() {
        LocalDateTime now = LocalDateTime.now();
        List<Mission> missionsToUpdate = missionService.findMissionsToUpdateStatus(now);
        for (Mission mission : missionsToUpdate) {
            missionService.updateMissionStatus(mission.getId(), now);
        }
    }
}
