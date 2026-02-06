package ru.practicum.ewm.stats.aggregator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.avro.ActionTypeAvro;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;
import ru.practicum.ewm.stats.avro.UserActionAvro;

import java.time.Instant;
import java.util.*;

import static java.lang.Math.*;

@Slf4j
@Service
public class AggregatorService {
    // EventId, UserId, ActionWeight
    Map<Long, Map<Long, Double>> matrixOfWeights = new HashMap<>();

    // EventId, SumWeight
    private final Map<Long, Double> sumOfWeights = new HashMap<>();

    // EventId, EventId, SumMinWeight
    private final Map<Long, Map<Long, Double>> sumOfMinWeights = new HashMap<>();

    public List<EventSimilarityAvro> updateSimilarity(UserActionAvro action) {
        Long eventA = action.getEventId();
        Long userId = action.getUserId();
        double newWeightValue = caseActionType(action.getActionType());
        double oldWeightValue = updateMatrixOfWeights(action, newWeightValue);
        if (newWeightValue - oldWeightValue > 0) {
            calcSumOfWeights(eventA, newWeightValue, oldWeightValue);
            return matrixOfWeights.keySet()
                    .stream()
                    .filter(eventB -> !eventB.equals(eventA))
                    .map(eventB -> {
                        double similarity = calcSimilarity(eventA, eventB, newWeightValue, oldWeightValue, userId);
                        if (similarity > 0.0) {
                            return EventSimilarityAvro.newBuilder()
                                    .setEventA(min(eventA, eventB))
                                    .setEventB(max(eventA, eventB))
                                    .setScore(similarity)
                                    .setTimestamp(Instant.now())
                                    .build();
                        } else {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
        }
        return List.of();
    }

    private Double updateMatrixOfWeights(UserActionAvro action, double newWeightValue) {
        double oldWeightValue = matrixOfWeights.getOrDefault(action.getEventId(), new HashMap<>())
                .getOrDefault(action.getUserId(), 0.0);

        if (newWeightValue > oldWeightValue) {
            Map<Long, Double> s = matrixOfWeights.getOrDefault(action.getEventId(), new HashMap<>());
            s.put(action.getUserId(), newWeightValue);
            matrixOfWeights.put(action.getEventId(), s);
        }
        return oldWeightValue;
    }

    private double calcSimilarity(Long eventA, Long eventB, double newWeightValue, double oldWeightValue, Long userId) {
        return calcSumOfMinWeights(eventA, eventB, newWeightValue - oldWeightValue, userId) /
               (sqrt(sumOfWeights.get(eventA)) * sqrt(sumOfWeights.get(eventB)));
    }

    private void calcSumOfWeights(Long eventA, double newWeightValue, double oldWeightValue) {
        sumOfWeights.merge(eventA, newWeightValue - oldWeightValue, Double::sum);
    }

    private Double calcSumOfMinWeights(Long eventA, Long eventB, Double delta, Long userId) {
        if (matrixOfWeights.get(eventB).get(userId) != null) {
            double weightA = matrixOfWeights.get(eventA).get(userId);
            double weightB = matrixOfWeights.get(eventB).get(userId);
            Map<Long, Double> s = sumOfMinWeights.computeIfAbsent(min(eventA, eventB), k -> new HashMap<>());
            Double summa = s.get(max(eventA, eventB));
            if (summa == null) {
                summa = matrixOfWeights.get(eventA)
                        .entrySet()
                        .stream()
                        .mapToDouble(entry ->
                                min(entry.getValue(), matrixOfWeights.get(eventB).getOrDefault(entry.getKey(), 0.0)))
                        .sum();
                s.put(eventB, summa);
                sumOfMinWeights.put(min(eventA, eventB), s);
                return summa;
            } else if (weightA > weightB && (weightA - delta) < weightB) {
                double newSumma = summa + (weightB - (weightA - delta));
                sumOfMinWeights.get(min(eventA, eventB)).put(max(eventA, eventB), newSumma);
                return newSumma;
            } else if (weightA <= weightB) {
                double newSumma = summa + delta;
                sumOfMinWeights.get(min(eventA, eventB)).put(max(eventA, eventB), newSumma);
                return newSumma;
            } else {
                return summa;
            }
        }
        return 0.0;
    }

    private double caseActionType(ActionTypeAvro actionType) {
        return switch (actionType) {
            case VIEW -> 0.4;
            case REGISTER -> 0.8;
            case LIKE -> 1.0;
        };
    }
}