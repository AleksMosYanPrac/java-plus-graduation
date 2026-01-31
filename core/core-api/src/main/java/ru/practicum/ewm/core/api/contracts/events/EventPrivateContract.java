package ru.practicum.ewm.core.api.contracts.events;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.core.api.contracts.events.dto.*;
import ru.practicum.ewm.core.api.contracts.requests.dto.ParticipationRequestDto;
import ru.practicum.ewm.core.api.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RequestMapping("/users/{userId}/events")
public interface EventPrivateContract {

    @GetMapping
    public List<EventFullDto> getAllByCurrentUser(@PathVariable Long userId,
                                                  @RequestParam(defaultValue = "0") int from,
                                                  @RequestParam(defaultValue = "10") int size);

    @PostMapping
    @ResponseStatus(CREATED)
    public EventFullDto create(@PathVariable Long userId,
                               @RequestBody NewEventDto event) throws DataIntegrityViolation;

    @GetMapping("/{eventId}")
    public EventFullDto getByIdByOwner(@PathVariable Long userId,
                                       @PathVariable Long eventId) throws NotFoundException;

    @PatchMapping("/{eventId}")
    public EventFullDto updateByIdByOwner(@PathVariable Long userId,
                                          @PathVariable Long eventId,
                                          @RequestBody UpdateEventUserRequest event)
            throws NotFoundException, DataIntegrityViolation;

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestsByOwner(@PathVariable Long userId,
                                                            @PathVariable Long eventId);

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult updateStatus(@PathVariable Long userId,
                                                       @PathVariable Long eventId,
                                                       @RequestBody EventRequestStatusUpdateRequest request)
            throws NotFoundException, DataIntegrityViolation;
}