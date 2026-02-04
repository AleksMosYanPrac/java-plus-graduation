package ru.practicum.ewm.core.api.contracts.requests;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.core.api.contracts.events.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.core.api.contracts.requests.dto.ParticipationRequestDto;
import ru.practicum.ewm.core.api.contracts.requests.dto.Status;

import java.util.List;
import java.util.Map;

@Component
public class RequestsFeignClientFallback implements RequestsFeignClient {

    @Override
    public List<ParticipationRequestDto> findAllByEventId(Long eventId) {
        return List.of();
    }

    @Override
    public Boolean hasAllRequestsHavePendingStatus(List<Long> requestIds) {
        return false;
    }

    @Override
    public EventRequestStatusUpdateResult updateRequestStatus(Map<Long, Status> requests) {
        return new EventRequestStatusUpdateResult();
    }
}