package ru.practicum.ewm.core.api.contracts.events;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "event-service", path = "/api/v1/events", fallback = EventsFeignClientFallback.class)
public interface EventsFeignClient extends EventsFeignContract{
}