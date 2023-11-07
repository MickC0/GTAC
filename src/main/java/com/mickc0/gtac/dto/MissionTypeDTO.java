package com.mickc0.gtac.dto;

import java.util.UUID;

public class MissionTypeDTO {

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private UUID uuid;

    private String name;
}
