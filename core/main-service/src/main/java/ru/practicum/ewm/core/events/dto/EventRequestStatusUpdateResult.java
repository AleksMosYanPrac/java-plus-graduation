package ru.practicum.ewm.core.events.dto;

import lombok.Data;
import ru.practicum.ewm.core.requests.dto.ParticipationRequestDto;

import java.util.List;

@Data
public class EventRequestStatusUpdateResult {
    private List<ParticipationRequestDto> confirmedRequests;
    private List<ParticipationRequestDto> rejectedRequests;
}