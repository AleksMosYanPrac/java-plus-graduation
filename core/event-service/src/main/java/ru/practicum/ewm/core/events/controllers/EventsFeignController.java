package ru.practicum.ewm.core.events.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.core.api.contracts.events.EventsFeignContract;
import ru.practicum.ewm.core.api.contracts.events.dto.EventFullDto;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;
import ru.practicum.ewm.core.events.interfaces.EventService;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventsFeignController implements EventsFeignContract {

    private final EventService eventService;

    @Override
    public EventFullDto findById(Long eventId) throws NotFoundException {
        return eventService.findEventById(eventId);
    }
}