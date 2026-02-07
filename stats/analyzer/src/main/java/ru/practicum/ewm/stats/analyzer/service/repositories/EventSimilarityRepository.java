package ru.practicum.ewm.stats.analyzer.service.repositories;

import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.stats.analyzer.service.model.EventSimilarity;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public interface EventSimilarityRepository extends JpaRepository<EventSimilarity, Long> {
    Optional<EventSimilarity> findByEventAAndEventB(long eventA, long eventB);

    List<EventSimilarity> findAllByEventAInOrderByScoreDesc(Set<Long> collect, Limit limit);

    List<EventSimilarity> findAllByEventBInOrderByScoreDesc(Set<Long> collect, Limit limit);

    List<EventSimilarity> findAllByEventAOrderByScoreDesc(Long eventId, Limit limit);

    List<EventSimilarity> findAllByEventBOrderByScoreDesc(Long eventId);

    List<EventSimilarity> findAllByEventAOrEventBOrderByScoreDesc(Long eventA, Long eventB, Limit limit);

    List<EventSimilarity> findAllByEventAInOrEventBInOrderByScoreDesc(Set<Long> eventAIds, Set<Long> eventBIds, Limit of);
}