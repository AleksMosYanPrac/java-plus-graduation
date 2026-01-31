package ru.practicum.ewm.core.api.contracts.requests;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.core.api.contracts.events.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.core.api.contracts.requests.dto.ParticipationRequestDto;
import ru.practicum.ewm.core.api.contracts.requests.dto.Status;

import java.util.List;
import java.util.Map;

public interface RequestsFeignContract {

    @GetMapping("/feign/{eventId}")
    List<ParticipationRequestDto> findAllByEventId(@PathVariable Long eventId);

    @PostMapping("/feign/all/statuses")
    Boolean hasAllRequestsHavePendingStatus(@RequestBody List<Long> requestIds);

    @PostMapping("/feign/statuses")
    EventRequestStatusUpdateResult updateRequestStatus(@RequestBody Map<Long, Status> requests);
}