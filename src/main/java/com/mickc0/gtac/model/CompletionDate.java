package com.mickc0.gtac.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table (name = "completion_dates")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompletionDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "starting_date")
    private LocalDateTime startingDate;

    @Column(name = "ending_date")
    private LocalDateTime endingDate;
}
