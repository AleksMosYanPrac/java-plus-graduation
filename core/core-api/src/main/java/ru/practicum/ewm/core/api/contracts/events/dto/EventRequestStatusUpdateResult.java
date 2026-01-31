package ru.practicum.ewm.core.api.contracts.events.dto;

import lombok.Data;
import ru.practicum.ewm.core.api.contracts.requests.dto.ParticipationRequestDto;

import java.util.List;

@Data
public class EventRequestStatusUpdateResult {
    private List<ParticipationRequestDto> confirmedRequests;
    private List<ParticipationRequestDto> rejectedRequests;
}