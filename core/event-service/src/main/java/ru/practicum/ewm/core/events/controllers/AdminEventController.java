package ru.practicum.ewm.core.events.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.core.api.contracts.events.EventAdminContract;
import ru.practicum.ewm.core.api.contracts.events.dto.EventFullDto;
import ru.practicum.ewm.core.api.contracts.events.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.core.api.exceptions.ApiErrorContract;
import ru.practicum.ewm.core.api.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;
import ru.practicum.ewm.core.events.Filter;
import ru.practicum.ewm.core.events.interfaces.EventService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminEventController implements EventAdminContract, ApiErrorContract {

    private final EventService eventService;

    @Override
    public List<EventFullDto> search(Long[] users,
                                     String[] states,
                                     Integer[] categories,
                                     LocalDateTime rangeStart,
                                     LocalDateTime rangeEnd,
                                     int from,
                                     int size) {
        Filter filter = new Filter(users, states, categories, rangeStart, rangeEnd, from, size);
        return eventService.findEventsByFilterByAdmin(filter);
    }

    @Override
    public EventFullDto adminVerification(Long eventId, UpdateEventAdminRequest event)
            throws NotFoundException, DataIntegrityViolation {
        return eventService.updateEventByIdByAdmin(eventId, event);
    }
}