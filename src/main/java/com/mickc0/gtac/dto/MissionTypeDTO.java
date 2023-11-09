package com.mickc0.gtac.dto;

import com.mickc0.gtac.model.Mission;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MissionTypeDTO {

    private Long id;
    private UUID uuid;
    private String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

}
