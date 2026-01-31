package ru.practicum.ewm.core.requests.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.core.api.contracts.requests.RequestsPrivateContract;
import ru.practicum.ewm.core.api.contracts.requests.dto.ParticipationRequestDto;
import ru.practicum.ewm.core.api.exceptions.ApiErrorContract;
import ru.practicum.ewm.core.api.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;
import ru.practicum.ewm.core.requests.interfaces.RequestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PrivateRequestController implements RequestsPrivateContract, ApiErrorContract {

    private final RequestService requestService;

    @Override
    public List<ParticipationRequestDto> getUserRequests(Long userId) throws NotFoundException {
        return requestService.findUserRequests(userId);
    }

    @Override
    public ParticipationRequestDto createUserRequest(Long userId, Long eventId)
            throws NotFoundException, DataIntegrityViolation {
        return requestService.addNewUserRequest(userId, eventId);
    }

    @Override
    public ParticipationRequestDto cancelUserRequest(Long userId, Long requestId)
            throws NotFoundException {
        return requestService.cancelRequestByUser(userId, requestId);
    }
}