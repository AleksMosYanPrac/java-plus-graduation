package ru.practicum.ewm.core.events.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.core.api.contracts.events.EventPublicContract;
import ru.practicum.ewm.core.api.contracts.events.dto.EventFullDto;
import ru.practicum.ewm.core.api.contracts.events.dto.EventShortDto;
import ru.practicum.ewm.core.api.contracts.events.dto.Sort;
import ru.practicum.ewm.core.api.exceptions.ApiErrorContract;
import ru.practicum.ewm.core.api.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;
import ru.practicum.ewm.core.events.Filter;
import ru.practicum.ewm.core.events.interfaces.EventService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PublicEventController implements EventPublicContract, ApiErrorContract {

    private final EventService eventService;

    @Override
    public List<EventShortDto> searchEvents(String text,
                                            Integer[] categories,
                                            Boolean paid,
                                            LocalDateTime rangeStart,
                                            LocalDateTime rangeEnd,
                                            Boolean onlyAvailable,
                                            Sort sort,
                                            int from,
                                            int size,
                                            HttpServletRequest request) throws DataIntegrityViolation {
        Filter filter = new Filter(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        return eventService.findEventsByFilter(filter, request);
    }

    @Override
    public EventFullDto getEventById(Long eventId, HttpServletRequest request)
            throws NotFoundException {
        return eventService.findEventById(eventId, request);
    }
}