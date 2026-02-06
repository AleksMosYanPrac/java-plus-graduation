package ru.practicum.ewm.stats.analyzer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.stats.analyzer.service.model.EventSimilarity;
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

import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyzerService {

    private final UserInteractionRepository interactionRepository;
    private final EventSimilarityRepository similarityRepository;

    @Transactional
    public void analyze(EventSimilarityAvro value) {
        EventSimilarity eventsSimilarity = similarityRepository.findByEventAAndEventB(value.getEventA(),value.getEventB())
                .orElseGet(()->mapToNewEventSimilarity(value));
        if(Objects.nonNull(eventsSimilarity.getId())){
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

    public Stream<RecommendedEventProto> analyze(UserPredictionsRequestProto request) {
        return Stream.of();
    }

    public Stream<RecommendedEventProto> analyze(SimilarEventsRequestProto request) {
        return Stream.of();
    }

    public Stream<RecommendedEventProto> analyze(InteractionsCountRequestProto request) {
        return Stream.of();
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

    private Float caseActionType(ActionTypeAvro actionType) {
        return 0.0f;
    }
}