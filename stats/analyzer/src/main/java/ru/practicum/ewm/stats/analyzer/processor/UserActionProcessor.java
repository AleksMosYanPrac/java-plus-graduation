package ru.practicum.ewm.stats.analyzer.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.stats.analyzer.service.AnalyzerService;
import ru.practicum.ewm.stats.avro.UserActionAvro;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserActionProcessor implements Runnable {

    private final AnalyzerService analyzerService;
    private final Consumer<String, UserActionAvro> userActionAvroConsumer;

    @Override
    public void run() {
        try {
            while (true) {
                userActionAvroConsumer.poll(Duration.ofSeconds(3)).forEach(record -> {
                    log.info("Receive User Action: {}", record.value().getUserId());
                    analyzerService.analyze(record.value());
                    userActionAvroConsumer.commitSync();
                });
            }
        } catch (WakeupException ignored) {

        } catch (Exception e) {
            log.error("Exception on processing user action", e);
        } finally {
            userActionAvroConsumer.commitSync();
            userActionAvroConsumer.close();
        }
    }
}