package ru.practicum.ewm.stats.aggregator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AggregatorStarter {

    private final AggregatorService aggregationService;
    private final KafkaService kafkaService;
    private boolean isStarted;

    @EventListener(ContextRefreshedEvent.class)
    public void start() {
        if (!isStarted) {
            isStarted = true;
            try {
                kafkaService.subscribeTopics();
                while (true) {
                    kafkaService.poll().forEach(record -> {
                        log.info("Receive User Action user:{}, event{}", record.value().getUserId(), record.value().getEventId());
                        aggregationService.updateSimilarity(record.value()).forEach((eventSimilarityAvro) -> {
                            kafkaService.send(eventSimilarityAvro);
                            log.info("Send event similarity:{}", eventSimilarityAvro);
                        });
                    });
                }
            } catch (WakeupException ignored) {

            } catch (Exception e) {
                log.error("Exception on processing sensor events", e);
            } finally {
                kafkaService.stop();
            }
        }
    }
}