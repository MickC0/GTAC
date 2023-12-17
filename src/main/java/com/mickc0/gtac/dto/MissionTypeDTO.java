package com.mickc0.gtac.dto;

import jakarta.validation.constraints.Size;

import java.util.UUID;

public class MissionTypeDTO {
    private UUID uuid;
    @Size(min = 3, max = 20)
    private String name;
    private String description;
    private boolean selected;
    private boolean isActive;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
