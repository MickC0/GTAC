package com.mickc0.gtac.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mission_types")
public class MissionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

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

    @Override
    public String toString() {
        return "MissionType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
