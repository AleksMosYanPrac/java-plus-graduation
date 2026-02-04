package ru.practicum.ewm.core.api.contracts.events.dto;

import lombok.Data;
import ru.practicum.ewm.core.api.contracts.requests.dto.NewStatus;

import java.util.List;

@Data
public class EventRequestStatusUpdateRequest {
    private List<Long> requestIds;
    private NewStatus status;
}