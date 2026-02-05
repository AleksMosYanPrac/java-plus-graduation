package ru.practicum.ewm.stats.aggregator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;
import ru.practicum.ewm.stats.avro.UserActionAvro;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class AggregatorService {


    public Optional<EventSimilarityAvro> updateState(UserActionAvro action) {
        return null;
    }
}
