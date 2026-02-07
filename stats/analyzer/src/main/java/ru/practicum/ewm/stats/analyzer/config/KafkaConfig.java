package ru.practicum.ewm.stats.analyzer.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;
import ru.practicum.ewm.stats.avro.UserActionAvro;

import java.util.List;
import java.util.Properties;

@Configuration
public class KafkaConfig {

    @Bean
    Consumer<String, EventSimilarityAvro> hubEventConsumer(ConsumerConfig eventSimilarityConsumerConfig) {
        Consumer<String, EventSimilarityAvro> consumer = new KafkaConsumer<>(eventSimilarityConsumerConfig.getProperties());
        consumer.subscribe(List.of(eventSimilarityConsumerConfig.getTopic()));
        return consumer;
    }

    @Bean
    Consumer<String, UserActionAvro> sensorsSnapshotConsumer(ConsumerConfig userActionConsumerConfig) {
        Consumer<String, UserActionAvro> consumer = new KafkaConsumer<>(userActionConsumerConfig.getProperties());
        consumer.subscribe(List.of(userActionConsumerConfig.getTopic()));
        return consumer;
    }

    @Bean
    @ConfigurationProperties(prefix = "kafka.events-similarity-consumer")
    public ConsumerConfig eventSimilarityConsumerConfig() {
        return new ConsumerConfig();
    }

    @Bean
    @ConfigurationProperties(prefix = "kafka.user-actions-consumer")
    public ConsumerConfig userActionConsumerConfig() {
        return new ConsumerConfig();
    }

    @Getter
    @Setter
    public static class ConsumerConfig {
        private String topic;
        private Properties properties;
    }
}