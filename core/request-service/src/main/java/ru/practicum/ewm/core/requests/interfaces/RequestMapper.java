package ru.practicum.ewm.core.requests.interfaces;

import ru.practicum.ewm.core.api.contracts.events.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.core.requests.ParticipationRequest;
import ru.practicum.ewm.core.api.contracts.requests.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestMapper {
    ParticipationRequestDto toParticipationRequestDto(ParticipationRequest request);

    EventRequestStatusUpdateResult toEventRequestStatusUpdateResult(List<ParticipationRequest> participationRequests);
}
