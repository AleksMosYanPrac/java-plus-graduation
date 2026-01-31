package ru.practicum.ewm.core.requests.interfaces;

import ru.practicum.ewm.core.api.contracts.events.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.core.api.contracts.requests.dto.Status;
import ru.practicum.ewm.core.api.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;
import ru.practicum.ewm.core.api.contracts.requests.dto.ParticipationRequestDto;

import java.util.List;
import java.util.Map;

public interface RequestService {
    List<ParticipationRequestDto> findUserRequests(Long userId) throws NotFoundException;

    ParticipationRequestDto addNewUserRequest(Long userId, Long eventId)
            throws NotFoundException, DataIntegrityViolation;

    ParticipationRequestDto cancelRequestByUser(Long userId, Long requestId) throws NotFoundException;

    List<ParticipationRequestDto> findAllRequestsByEventId(Long id);

    boolean hasAllRequestHavePendingStatus(List<Long> requestIds);

    EventRequestStatusUpdateResult updateStatusForRequests(Map<Long, Status> requestsWithStatus);
}