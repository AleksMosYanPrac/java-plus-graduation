package ru.practicum.ewm.core.requests.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.stats.exceptions.ApiErrorHandler;
import ru.practicum.ewm.stats.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.stats.exceptions.NotFoundException;
import ru.practicum.ewm.core.requests.dto.ParticipationRequestDto;
import ru.practicum.ewm.core.requests.interfaces.RequestService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
public class PrivateRequestController implements ApiErrorHandler {

    private final RequestService requestService;

    @GetMapping
    public List<ParticipationRequestDto> getUserRequests(@PathVariable Long userId) throws NotFoundException {
        return requestService.findUserRequests(userId);
    }

    @PostMapping()
    @ResponseStatus(CREATED)
    public ParticipationRequestDto createUserRequest(@PathVariable Long userId, @RequestParam Long eventId)
            throws NotFoundException, DataIntegrityViolation {
        return requestService.addNewUserRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelUserRequest(@PathVariable Long userId, @PathVariable Long requestId)
            throws NotFoundException {
        return requestService.cancelUserRequest(userId, requestId);
    }
}