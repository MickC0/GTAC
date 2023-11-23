package com.mickc0.gtac.dto;

import java.util.Objects;
import java.util.UUID;

public class MissionTypeDTO {
    private Long id;
    private UUID uuid;
    private String name;
    private boolean selected;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "MissionTypeDTO{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", name='" + name + '\'' +
                ", selected=" + selected +
                '}';
    }
}
