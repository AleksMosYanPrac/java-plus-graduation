package ru.practicum.ewm.core.events.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.core.events.Filter;
import ru.practicum.ewm.core.events.dto.EventFullDto;
import ru.practicum.ewm.core.events.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.core.events.interfaces.EventService;
import ru.practicum.ewm.core.exceptions.ApiErrorHandler;
import ru.practicum.ewm.core.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.exceptions.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
public class AdminEventController implements ApiErrorHandler {

    private final EventService eventService;

    @GetMapping
    public List<EventFullDto> search(@RequestParam(required = false) Long[] users,
                                     @RequestParam(required = false) String[] states,
                                     @RequestParam(required = false) Integer[] categories,
                                     @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                     LocalDateTime rangeStart,
                                     @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                     LocalDateTime rangeEnd,
                                     @RequestParam(required = false, defaultValue = "0") int from,
                                     @RequestParam(required = false, defaultValue = "10") int size) {
        Filter filter = new Filter(users, states, categories, rangeStart, rangeEnd, from, size);
        return eventService.findEventsByFilterByAdmin(filter);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto adminVerification(@PathVariable Long eventId,
                                          @RequestBody UpdateEventAdminRequest event)
            throws NotFoundException, DataIntegrityViolation {
        return eventService.updateEventByIdByAdmin(eventId, event);
    }
}