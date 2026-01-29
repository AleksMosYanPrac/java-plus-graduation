package ru.practicum.ewm.core.events.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.core.events.Filter;
import ru.practicum.ewm.core.events.dto.EventFullDto;
import ru.practicum.ewm.core.events.dto.EventShortDto;
import ru.practicum.ewm.core.events.dto.Sort;
import ru.practicum.ewm.core.events.interfaces.EventService;
import ru.practicum.ewm.core.exceptions.ApiErrorHandler;
import ru.practicum.ewm.core.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.exceptions.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class PublicEventController implements ApiErrorHandler {

    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> searchEvents(@RequestParam(required = false) String text,
                                            @RequestParam(required = false) Integer[] categories,
                                            @RequestParam(required = false) Boolean paid,
                                            @RequestParam(required = false)
                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                            @RequestParam(required = false)
                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                            @RequestParam(defaultValue = "true", required = false) Boolean onlyAvailable,
                                            @RequestParam(required = false, defaultValue = "EVENT_DATE") Sort sort,
                                            @RequestParam(required = false, defaultValue = "0") int from,
                                            @RequestParam(required = false, defaultValue = "10") int size,
                                            HttpServletRequest request) throws DataIntegrityViolation {
        Filter filter = new Filter(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        return eventService.findEventsByFilter(filter, request);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventById(@PathVariable Long eventId, HttpServletRequest request)
            throws NotFoundException {
        return eventService.findEventById(eventId, request);
    }
}