package ru.practicum.ewm.stats.analyzer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.stats.analyzer.service.model.EventSimilarity;
import ru.practicum.ewm.stats.analyzer.service.model.Rating;
import ru.practicum.ewm.stats.analyzer.service.model.UserInteraction;
import ru.practicum.ewm.stats.analyzer.service.repositories.EventSimilarityRepository;
import ru.practicum.ewm.stats.analyzer.service.repositories.UserInteractionRepository;
import ru.practicum.ewm.stats.avro.ActionTypeAvro;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;
import ru.practicum.ewm.stats.avro.UserActionAvro;
import ru.practicum.ewm.stats.service.dashboard.InteractionsCountRequestProto;
import ru.practicum.ewm.stats.service.dashboard.RecommendedEventProto;
import ru.practicum.ewm.stats.service.dashboard.SimilarEventsRequestProto;
import ru.practicum.ewm.stats.service.dashboard.UserPredictionsRequestProto;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyzerService {

    private final UserInteractionRepository interactionRepository;
    private final EventSimilarityRepository similarityRepository;

    @Transactional
    public void analyze(EventSimilarityAvro value) {
        EventSimilarity eventsSimilarity = similarityRepository.findByEventAAndEventB(value.getEventA(), value.getEventB())
                .orElseGet(() -> mapToNewEventSimilarity(value));
        if (Objects.nonNull(eventsSimilarity.getId())) {
            eventsSimilarity.setScore(value.getScore());
            eventsSimilarity.setTimestamp(value.getTimestamp());
        }
        similarityRepository.save(eventsSimilarity);
    }

    @Transactional
    public void analyze(UserActionAvro value) {
        UserInteraction userInteraction = interactionRepository.findByUserIdAndEventId(value.getUserId(), value.getEventId())
                .orElseGet(() -> addNewUserInteraction(value));
        if (userInteraction.getRating() < caseActionType(value.getActionType())) {
            userInteraction.setRating(caseActionType(value.getActionType()));
            userInteraction.setTimestamp(value.getTimestamp());
            interactionRepository.save(userInteraction);
        }
    }

    //Возвращает поток рекомендованных мероприятий для указанного пользователя
    public Stream<RecommendedEventProto> analyzeRecommendationsForUser(UserPredictionsRequestProto request) {
        Set<Long> eventIds = interactionRepository.findAllByUserIdOrderByTimestampDesc(request.getUserId(),
                Limit.of(request.getMaxResults())).stream().map(UserInteraction::getEventId).collect(Collectors.toSet());
        if (eventIds.isEmpty()) {
            return Stream.of();
        }
        Set<Long> eventsIds =
                similarityRepository.findAllByEventAInOrEventBInOrderByScoreDesc(
                                eventIds,
                                eventIds,
                                Limit.of(request.getMaxResults()))
                        .stream()
                        .map(EventSimilarity::getEventB)
                        .filter(eventId -> !interactionRepository.existsByEventIdAndUserId(eventId, request.getUserId()))
                        .collect(Collectors.toSet());
        return eventsIds.stream()
                .map(eventId ->
                        RecommendedEventProto.newBuilder()
                                .setEventId(eventId)
                                .setScore(calculateScore(eventId, request.getUserId(), request.getMaxResults()))
                                .build())
                .sorted(Comparator.comparing(RecommendedEventProto::getScore).reversed())
                .limit(request.getMaxResults());
    }

    //возвращает поток мероприятий, с которыми не взаимодействовал этот пользователь,
    // но которые максимально похожи на указанное мероприятие
    public Stream<RecommendedEventProto> analyzeSimilarEvents(SimilarEventsRequestProto request) {
        return similarityRepository.findAllByEventAOrderByScoreDesc(request.getEventId(), Limit.of(request.getMaxResults()))
                .stream()
                .filter(s -> !interactionRepository.existsByEventIdAndUserId(s.getEventB(), request.getUserId()))
                .map(s -> RecommendedEventProto.newBuilder()
                        .setEventId(s.getEventB())
                        .setScore(s.getScore())
                        .build())
                .sorted(Comparator.comparing(RecommendedEventProto::getScore).reversed());
    }

    //получает идентификаторы мероприятий и возвращает их поток с суммой максимальных весов действий
    // каждого пользователя с этими мероприятиями
    public Stream<RecommendedEventProto> analyzeInteractionsCounts(InteractionsCountRequestProto request) {
        return interactionRepository.findAllByEventIdIn(request.getEventIdList())
                .stream()
                .collect(Collectors.groupingBy(Rating::getEventId, Collectors.summingDouble(Rating::getRating)))
                .entrySet()
                .stream()
                .map(r -> RecommendedEventProto.newBuilder().setEventId(r.getKey()).setScore(r.getValue()).build())
                .sorted(Comparator.comparing(RecommendedEventProto::getScore).reversed());
    }

    private double calculateScore(Long eventId, Long userId, int limit) {
        Map<Long, Double> scores = similarityRepository.findAllByEventAOrEventBOrderByScoreDesc(eventId, eventId, Limit.of(limit))
                .stream()
                .filter(es -> interactionRepository.existsByEventIdAndUserId(es.getEventB(), userId))
                .collect(Collectors.toMap(EventSimilarity::getEventB, EventSimilarity::getScore));
        Map<Long, Double> ratings = interactionRepository.findAllByEventIdInAndUserId(scores.keySet(),
                        userId).stream()
                .collect(Collectors.toMap(UserInteraction::getEventId, UserInteraction::getRating));
        Double sumWeightedMarks = scores.entrySet().stream()
                .map(entry -> ratings.get(entry.getKey()) * entry.getValue())
                .mapToDouble(Double::doubleValue).sum();

        Double sumScores = scores.values().stream().mapToDouble(Double::doubleValue).sum();
        return sumWeightedMarks / sumScores;
    }

    private EventSimilarity mapToNewEventSimilarity(EventSimilarityAvro value) {
        return EventSimilarity.builder()
                .eventA(value.getEventA())
                .eventB(value.getEventB())
                .score(value.getScore())
                .timestamp(value.getTimestamp())
                .build();
    }

    private UserInteraction addNewUserInteraction(UserActionAvro value) {
        return interactionRepository.save(UserInteraction.builder()
                .userId(value.getUserId())
                .eventId(value.getEventId())
                .rating(caseActionType(value.getActionType()))
                .timestamp(value.getTimestamp())
                .build());
    }

    private Double caseActionType(ActionTypeAvro actionType) {
        return switch (actionType) {
            case VIEW -> 0.4;
            case REGISTER -> 0.8;
            case LIKE -> 1.0;
        };
    }
}