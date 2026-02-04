package ru.practicum.ewm.core.requests.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.core.api.contracts.events.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.core.api.contracts.requests.RequestsFeignContract;
import ru.practicum.ewm.core.api.contracts.requests.dto.ParticipationRequestDto;
import ru.practicum.ewm.core.api.contracts.requests.dto.Status;
import ru.practicum.ewm.core.requests.interfaces.RequestService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/requests")
@RequiredArgsConstructor
public class RequestFeignController implements RequestsFeignContract {

    private final RequestService requestService;

    @Override
    public List<ParticipationRequestDto> findAllByEventId(Long eventId) {
        return requestService.findAllRequestsByEventId(eventId);
    }

    @Override
    public Boolean hasAllRequestsHavePendingStatus(List<Long> requestIds) {
        return requestService.hasAllRequestHavePendingStatus(requestIds);
    }

    @Override
    public EventRequestStatusUpdateResult updateRequestStatus(Map<Long, Status> requests) {
        return requestService.updateStatusForRequests(requests);
    }
}