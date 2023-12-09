package com.mickc0.gtac.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;
    @Column(nullable = false, unique = false)
    private String name;
    @ManyToMany(mappedBy = "role")
    private List<Volunteer> volunteers = new ArrayList<>();




    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
