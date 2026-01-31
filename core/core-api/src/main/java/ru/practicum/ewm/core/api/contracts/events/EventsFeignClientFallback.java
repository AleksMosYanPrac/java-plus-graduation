package ru.practicum.ewm.core.api.contracts.events;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.core.api.contracts.events.dto.EventFullDto;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;

@Component
public class EventsFeignClientFallback implements EventsFeignClient {
    @Override
    public EventFullDto findById(Long eventId) throws NotFoundException {
        return null;
    }
}