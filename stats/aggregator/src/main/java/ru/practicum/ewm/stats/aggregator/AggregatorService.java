package ru.practicum.ewm.stats.aggregator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.avro.ActionTypeAvro;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;
import ru.practicum.ewm.stats.avro.UserActionAvro;

import java.time.Instant;
import java.util.*;

@Slf4j
@Service
public class AggregatorService {
    // EventId, UserId, ActionWeight
    Map<Long, Map<Long, Double>> usersActionWeightWithEvent = new HashMap<>();

    // EventId, SumWeight
    private final Map<Long, Double> sumUsersWeightsForOneEvent = new HashMap<>();

    // EventId, EventId, SumMinWeight
    private final Map<Long, Map<Long, Double>> sumMinWeightsBetweenTwoEvents = new HashMap<>();

    public List<EventSimilarityAvro> updateSimilarity(UserActionAvro action) {
        if (updateSumWeightForEvent(action)) {
            return calculateSimilarity(action);
        }
        return List.of();
    }

    private EventSimilarityAvro calculateSimilarity(Long eventA, Long eventB) {
        double score = calcSumMinWeights(eventA,eventB) / calcPersonSum(eventA,eventB);
        if (score > 0.0){
            return EventSimilarityAvro.newBuilder()
                    .setEventA(Math.min(eventA, eventB))
                    .setEventB(Math.max(eventA, eventB))
                    .setScore(score)
                    .setTimestamp(Instant.now())
                    .build();
        }
        return null;
    }

    private double calcPersonSum(Long eventA, Long eventB) {
        return 0;
    }

    private double calcSumMinWeights(Long eventA, Long eventB) {
        return 0;
    }

    private boolean updateSumWeightForEvent(UserActionAvro action) {
        double newWeightValue = caseActionType(action.getActionType());
        if (isActionWithNewEvent(action)) {
            usersActionWeightWithEvent.put(action.getEventId(), new HashMap<>());
            usersActionWeightWithEvent.get(action.getEventId()).put(action.getUserId(), newWeightValue);
            sumUsersWeightsForOneEvent.put(action.getEventId(), newWeightValue);
            return true;
        } else {
            double oldWeightValue = usersActionWeightWithEvent.get(action.getEventId()).get(action.getUserId());
            if (oldWeightValue < newWeightValue) {
                usersActionWeightWithEvent.get(action.getEventId()).put(action.getUserId(), newWeightValue);
                sumUsersWeightsForOneEvent.merge(action.getEventId(), newWeightValue - oldWeightValue, Double::sum);
                return true;
            }
        }
        return false;
    }

    private List<EventSimilarityAvro> calculateSimilarity(UserActionAvro action) {
        return usersActionWeightWithEvent.keySet().stream()
                .filter(eventId -> !eventId.equals(action.getEventId()))
                .map((eventB) -> calculateSimilarity(action.getEventId(), eventB))
                .filter(Objects::nonNull)
                .toList();
    }

    private boolean isActionWithNewEvent(UserActionAvro action) {
        return usersActionWeightWithEvent.containsKey(action.getEventId());
    }

    private double caseActionType(ActionTypeAvro actionType) {
        return switch (actionType) {
            case VIEW -> 0.4;
            case REGISTER -> 0.8;
            case LIKE -> 1.0;
        };
    }
}