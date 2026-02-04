package ru.practicum.ewm.core.events.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import ru.practicum.ewm.core.api.contracts.events.dto.*;
import ru.practicum.ewm.core.api.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;
import ru.practicum.ewm.core.events.Filter;

import ru.practicum.ewm.core.api.contracts.requests.dto.ParticipationRequestDto;

import java.util.List;

@Validated
public interface EventService {
    List<EventFullDto> findAllByCurrentUser(Long userId, Pageable pageable);

    EventFullDto addNewEvent(Long userId, @Valid NewEventDto event) throws DataIntegrityViolation;

    EventFullDto findByIdByCurrentUser(Long userId, Long eventId) throws NotFoundException;

    EventFullDto updateByIdByCurrentUser(Long userId, Long eventId, @Valid UpdateEventUserRequest event)
            throws NotFoundException, DataIntegrityViolation;

    List<ParticipationRequestDto> findParticipationRequests(Long userId, Long eventId);

    EventRequestStatusUpdateResult updateParticipationStatus(Long userId,
                                                             Long eventId,
                                                             EventRequestStatusUpdateRequest request)
            throws NotFoundException, DataIntegrityViolation;

    List<EventShortDto> findEventsByFilter(@Valid Filter filter,
                                           HttpServletRequest request) throws DataIntegrityViolation;

    EventFullDto findEventById(Long eventId, HttpServletRequest request) throws NotFoundException;

    List<EventFullDto> findEventsByFilterByAdmin(Filter filter);

    EventFullDto updateEventByIdByAdmin(Long eventId, @Valid UpdateEventAdminRequest event)
            throws NotFoundException, DataIntegrityViolation;

    EventFullDto findEventById(Long eventId) throws NotFoundException;
}