package ru.practicum.ewm.core.events.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.core.api.contracts.events.EventPrivateContract;
import ru.practicum.ewm.core.api.contracts.events.dto.*;
import ru.practicum.ewm.core.api.exceptions.ApiErrorContract;
import ru.practicum.ewm.core.api.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;
import ru.practicum.ewm.core.events.interfaces.EventService;
import ru.practicum.ewm.core.api.contracts.requests.dto.ParticipationRequestDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PrivateEventController implements EventPrivateContract, ApiErrorContract {

    private final EventService eventService;

    @Override
    public List<EventFullDto> getAllByCurrentUser(Long userId, int from, int size) {
        return eventService.findAllByCurrentUser(userId, PageRequest.of(from, size));
    }

    @Override
    public EventFullDto create(Long userId, NewEventDto event) throws DataIntegrityViolation {
        return eventService.addNewEvent(userId, event);
    }

    @Override
    public EventFullDto getByIdByOwner(Long userId, Long eventId) throws NotFoundException {
        return eventService.findByIdByCurrentUser(userId, eventId);
    }

    @Override
    public EventFullDto updateByIdByOwner(Long userId, Long eventId, UpdateEventUserRequest event)
            throws NotFoundException, DataIntegrityViolation {
        return eventService.updateByIdByCurrentUser(userId, eventId, event);
    }

    @Override
    public List<ParticipationRequestDto> getRequestsByOwner(Long userId, Long eventId) {
        return eventService.findParticipationRequests(userId, eventId);
    }

    @Override
    public EventRequestStatusUpdateResult updateStatus(Long userId, Long eventId, EventRequestStatusUpdateRequest request)
            throws NotFoundException, DataIntegrityViolation {
        return eventService.updateParticipationStatus(userId, eventId, request);
    }
}