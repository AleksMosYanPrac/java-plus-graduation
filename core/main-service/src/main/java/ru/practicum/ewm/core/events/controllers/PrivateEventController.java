package ru.practicum.ewm.core.events.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.core.events.dto.*;
import ru.practicum.ewm.core.events.interfaces.EventService;
import ru.practicum.ewm.core.exceptions.ApiErrorHandler;
import ru.practicum.ewm.core.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.exceptions.NotFoundException;
import ru.practicum.ewm.core.requests.dto.ParticipationRequestDto;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class PrivateEventController implements ApiErrorHandler {

    private final EventService eventService;

    @GetMapping
    public List<EventFullDto> getAllByCurrentUser(@PathVariable Long userId,
                                                  @RequestParam(defaultValue = "0") int from,
                                                  @RequestParam(defaultValue = "10") int size) {
        return eventService.findAllByCurrentUser(userId, PageRequest.of(from, size));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public EventFullDto create(@PathVariable Long userId,
                               @RequestBody NewEventDto event) throws DataIntegrityViolation {
        return eventService.addNewEvent(userId, event);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getByIdByOwner(@PathVariable Long userId,
                                       @PathVariable Long eventId) throws NotFoundException {
        return eventService.findByIdByCurrentUser(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateByIdByOwner(@PathVariable Long userId,
                                          @PathVariable Long eventId,
                                          @RequestBody UpdateEventUserRequest event)
            throws NotFoundException, DataIntegrityViolation {
        return eventService.updateByIdByCurrentUser(userId, eventId, event);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestsByOwner(@PathVariable Long userId,
                                                            @PathVariable Long eventId) {
        return eventService.findParticipationRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult updateStatus(@PathVariable Long userId,
                                                       @PathVariable Long eventId,
                                                       @RequestBody EventRequestStatusUpdateRequest request)
            throws NotFoundException, DataIntegrityViolation {
        return eventService.updateParticipationStatus(userId, eventId, request);
    }
}