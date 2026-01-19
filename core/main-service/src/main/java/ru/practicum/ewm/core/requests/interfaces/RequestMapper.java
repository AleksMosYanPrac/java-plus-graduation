package ru.practicum.ewm.core.requests.interfaces;

import ru.practicum.ewm.core.requests.ParticipationRequest;
import ru.practicum.ewm.core.requests.dto.ParticipationRequestDto;

public interface RequestMapper {
    ParticipationRequestDto toParticipationRequestDto(ParticipationRequest request);
}
