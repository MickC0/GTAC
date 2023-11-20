package com.mickc0.gtac.dto;

import java.util.ArrayList;
import java.util.List;

public class MissionTypeSelectionDTO {
    private List<MissionTypeDTO> missionTypes;

    // Constructeur, getters et setters
    public MissionTypeSelectionDTO() {
        this.missionTypes = new ArrayList<>();
    }

    public List<MissionTypeDTO> getMissionTypes() {
        return missionTypes;
    }

    public void setMissionTypes(List<MissionTypeDTO> missionTypes) {
        this.missionTypes = missionTypes;
    }

}


