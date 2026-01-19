package ru.practicum.ewm.core.requests.interfaces;

import ru.practicum.ewm.core.requests.dto.ParticipationRequestDto;
import ru.practicum.ewm.core.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.exceptions.NotFoundException;

import java.util.List;

public interface RequestService {
    List<ParticipationRequestDto> findUserRequests(Long userId) throws NotFoundException;

    ParticipationRequestDto addNewUserRequest(Long userId, Long eventId)
            throws NotFoundException, DataIntegrityViolation;

    ParticipationRequestDto cancelUserRequest(Long userId, Long requestId) throws NotFoundException;
}