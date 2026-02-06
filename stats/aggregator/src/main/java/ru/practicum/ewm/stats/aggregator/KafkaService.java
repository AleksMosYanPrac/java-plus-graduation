package ru.practicum.ewm.stats.aggregator;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;
import ru.practicum.ewm.stats.avro.UserActionAvro;

import java.time.Duration;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaService {

    private final Consumer<String, UserActionAvro> consumer;
    private final Producer<String, EventSimilarityAvro> producer;

    @Value("${aggregator.kafka.producer.events-similarity}")
    private String eventsSimilarityTopic;

    @Value("${aggregator.kafka.consumer.user-actions}")
    private String userActionsTopic;

    public ConsumerRecords<String, UserActionAvro> poll() {
        return consumer.poll(Duration.ofSeconds(5));
    }

    public void send(EventSimilarityAvro eventSimilarityMessage) {
        producer.send(new ProducerRecord<>(eventsSimilarityTopic, eventSimilarityMessage));
    }

    public void subscribeTopics(){
        consumer.subscribe(List.of(userActionsTopic));
    }

    @PreDestroy
    public void stop() {
        try {
            producer.flush();
            consumer.commitSync();
        } finally {
            log.info("Close producer");
            producer.close();
            log.info("Close consumer");
            consumer.close();
        }
    }
}