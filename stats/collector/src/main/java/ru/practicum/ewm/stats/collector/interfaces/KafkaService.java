package ru.practicum.ewm.stats.collector.interfaces;

import ru.practicum.ewm.stats.service.collector.UserActionProto;

public interface KafkaService {
    void send(UserActionProto request);
}