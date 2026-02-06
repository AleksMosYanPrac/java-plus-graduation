package ru.practicum.ewm.stats.analyzer.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.stats.analyzer.service.model.EventSimilarity;

import java.util.Optional;

public interface EventSimilarityRepository extends JpaRepository<EventSimilarity, Long> {
    Optional<EventSimilarity> findByEventAAndEventB(long eventA, long eventB);
}