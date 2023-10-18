package com.mickc0.gtac.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "disponibilities")
public class Disponibility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "starting_hour")
    private LocalTime startingHour;
    @Column(name = "ending_hour")
    private LocalTime endingHour;
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
