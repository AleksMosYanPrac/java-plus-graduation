package ru.practicum.ewm.stats.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;
import ru.practicum.ewm.stats.avro.UserActionAvro;

import java.util.Properties;

@Configuration
public class KafkaConfig {

    @Bean
    Consumer<String, UserActionAvro> consumer(KafkaConfiguration consumerConfig) {
        return new KafkaConsumer<>(consumerConfig.getProperties());
    }

    @Bean
    Producer<String, EventSimilarityAvro> producer(KafkaConfiguration producerConfig) {
        return new KafkaProducer<>(producerConfig.getProperties());
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.kafka.consumer")
    public KafkaConfiguration consumerConfig() {
        return new KafkaConfiguration();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.kafka.producer")
    public KafkaConfiguration producerConfig() {
        return new KafkaConfiguration();
    }

    @Getter
    @Setter
    public static class KafkaConfiguration {
        private Properties properties;
    }
}