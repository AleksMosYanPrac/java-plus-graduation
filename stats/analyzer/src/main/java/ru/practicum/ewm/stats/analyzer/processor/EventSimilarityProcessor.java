package ru.practicum.ewm.stats.analyzer.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.stereotype.Component;

import ru.practicum.ewm.stats.analyzer.service.AnalyzerService;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventSimilarityProcessor implements Runnable {

    private final AnalyzerService analyzerService;
    private final Consumer<String, EventSimilarityAvro> eventSimilarityAvroConsumer;

    @Override
    public void run() {
        try {
            while (true) {
                eventSimilarityAvroConsumer.poll(Duration.ofSeconds(5)).forEach(record -> {
                    log.info("Receive Event Similarity message {}", record.value().getEventA());
                    analyzerService.analyze(record.value());
                    eventSimilarityAvroConsumer.commitSync();
                });
            }
        } catch (WakeupException ignored) {

        } catch (Exception e) {
            log.error("Exception on processing event similarity", e);
        } finally {
            eventSimilarityAvroConsumer.commitSync();
            eventSimilarityAvroConsumer.close();
        }
    }
}