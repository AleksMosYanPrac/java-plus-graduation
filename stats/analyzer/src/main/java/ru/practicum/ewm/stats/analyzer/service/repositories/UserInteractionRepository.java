package ru.practicum.ewm.stats.analyzer.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.stats.analyzer.service.model.UserInteraction;

import java.util.Optional;

public interface UserInteractionRepository extends JpaRepository<UserInteraction, Long> {
    boolean existsByUserIdAndEventId(long userId, long eventId);

    Optional<UserInteraction> findByUserIdAndEventId(long userId, long eventId);
}