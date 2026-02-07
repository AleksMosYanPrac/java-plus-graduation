package ru.practicum.ewm.stats.analyzer.service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Builder
@Getter
@Setter
@Entity
@Table(name = "similarities")
@NoArgsConstructor
@AllArgsConstructor
public class EventSimilarity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long eventA;
    private Long eventB;
    private double score;
    private Instant timestamp;
}