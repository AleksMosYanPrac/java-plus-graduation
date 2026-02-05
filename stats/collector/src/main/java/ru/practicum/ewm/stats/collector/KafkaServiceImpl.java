package ru.practicum.ewm.stats.collector;

import com.google.protobuf.Timestamp;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.avro.ActionTypeAvro;
import ru.practicum.ewm.stats.avro.UserActionAvro;
import ru.practicum.ewm.stats.collector.interfaces.KafkaService;
import ru.practicum.ewm.stats.service.collector.UserActionProto;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<String, SpecificRecordBase> producer;

    @Value("${collector.kafka.producer.user-action-topic}")
    private String userActionTopic;

    @Override
    public void send(UserActionProto request) {
        log.debug("Sent message to kafka topic:{}", userActionTopic);
        SpecificRecordBase message = UserActionAvro.newBuilder()
                .setUserId(request.getUserId())
                .setEventId(request.getEventId())
                .setActionType(ActionTypeAvro.valueOf(request.getActionType().toString()))
                .setTimestamp(protoTimestampToAvro(request.getTimestamp()))
                .build();
        producer.send(userActionTopic, message);
    }

    @PreDestroy
    public void preDestroy(){
        log.info("Pre Destroy method");
        producer.flush();
        producer.setCloseTimeout(Duration.ofSeconds(5));
        producer.destroy();
    }

    private Instant protoTimestampToAvro(Timestamp timestamp) {
        return Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
    }
}