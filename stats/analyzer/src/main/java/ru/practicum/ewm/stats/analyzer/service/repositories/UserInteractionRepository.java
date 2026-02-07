package ru.practicum.ewm.stats.analyzer.service.repositories;

import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.stats.analyzer.service.model.Rating;
import ru.practicum.ewm.stats.analyzer.service.model.UserInteraction;

import java.util.*;

public interface UserInteractionRepository extends JpaRepository<UserInteraction, Long> {

    Optional<UserInteraction> findByUserIdAndEventId(long userId, long eventId);

    boolean existsByEventIdAndUserId(Long eventId, Long userId);

    List<UserInteraction> findAllByEventIdInAndUserId(Set<Long> longs, Long userId);

    Collection<Rating> findAllByEventIdIn(List<Long> eventIdList);

    List<UserInteraction> findAllByUserIdOrderByTimestampDesc(long userId, Limit limit);
}