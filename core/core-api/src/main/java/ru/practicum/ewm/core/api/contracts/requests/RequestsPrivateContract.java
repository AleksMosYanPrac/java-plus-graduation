package ru.practicum.ewm.core.api.contracts.requests;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.core.api.contracts.requests.dto.ParticipationRequestDto;
import ru.practicum.ewm.core.api.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RequestMapping("/users/{userId}/requests")
public interface RequestsPrivateContract {

    @GetMapping
    List<ParticipationRequestDto> getUserRequests(@PathVariable Long userId) throws NotFoundException;

    @PostMapping
    @ResponseStatus(CREATED)
    ParticipationRequestDto createUserRequest(@PathVariable Long userId, @RequestParam Long eventId)
            throws NotFoundException, DataIntegrityViolation;

    @PatchMapping("/{requestId}/cancel")
    ParticipationRequestDto cancelUserRequest(@PathVariable Long userId, @PathVariable Long requestId)
            throws NotFoundException;
}