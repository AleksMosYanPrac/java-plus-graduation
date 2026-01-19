package ru.practicum.ewm.core.requests;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.core.requests.dto.ParticipationRequestDto;
import ru.practicum.ewm.core.requests.interfaces.RequestMapper;

import java.time.format.DateTimeFormatter;

@Component
public class RequestMapperImpl implements RequestMapper {
    @Override
    public ParticipationRequestDto toParticipationRequestDto(ParticipationRequest request) {
        ParticipationRequestDto participationRequestDto = new ParticipationRequestDto();
        participationRequestDto.setId(request.getId());
        participationRequestDto.setCreated(request.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
        participationRequestDto.setEvent(request.getEvent().getId());
        participationRequestDto.setRequester(request.getRequester().getId());
        participationRequestDto.setStatus(request.getStatus().toString());
        return participationRequestDto;
    }
}
