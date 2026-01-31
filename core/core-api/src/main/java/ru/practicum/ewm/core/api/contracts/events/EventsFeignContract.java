package ru.practicum.ewm.core.api.contracts.events;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.practicum.ewm.core.api.contracts.events.dto.EventFullDto;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;

public interface EventsFeignContract {
    @GetMapping("/feign/{eventId}")
    EventFullDto findById(@PathVariable Long eventId) throws NotFoundException;
}