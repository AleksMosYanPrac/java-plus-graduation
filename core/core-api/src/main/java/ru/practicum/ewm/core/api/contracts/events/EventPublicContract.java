package ru.practicum.ewm.core.api.contracts.events;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewm.core.api.contracts.events.dto.EventFullDto;
import ru.practicum.ewm.core.api.contracts.events.dto.EventShortDto;
import ru.practicum.ewm.core.api.contracts.events.dto.Sort;
import ru.practicum.ewm.core.api.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/events")
public interface EventPublicContract {

    @GetMapping
    List<EventShortDto> searchEvents(@RequestParam(required = false) String text,
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
                                     HttpServletRequest request) throws DataIntegrityViolation;

    @GetMapping("/{eventId}")
    EventFullDto getEventById(@PathVariable Long eventId, HttpServletRequest request) throws NotFoundException;
}