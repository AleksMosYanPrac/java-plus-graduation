package ru.practicum.ewm.core.requests;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.core.api.contracts.events.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.core.api.contracts.requests.dto.ParticipationRequestDto;
import ru.practicum.ewm.core.requests.interfaces.RequestMapper;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static ru.practicum.ewm.core.api.contracts.requests.dto.Status.*;

@Component
public class RequestMapperImpl implements RequestMapper {
    @Override
    public ParticipationRequestDto toParticipationRequestDto(ParticipationRequest request) {
        ParticipationRequestDto participationRequestDto = new ParticipationRequestDto();
        participationRequestDto.setId(request.getId());
        participationRequestDto.setCreated(request.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
        participationRequestDto.setEvent(request.getEventId());
        participationRequestDto.setRequester(request.getRequesterId());
        participationRequestDto.setStatus(request.getStatus());
        return participationRequestDto;
    }

    @Override
    public EventRequestStatusUpdateResult toEventRequestStatusUpdateResult(List<ParticipationRequest> participationRequests) {
        List<ParticipationRequestDto> confirmedRequests = new ArrayList<>();
        List<ParticipationRequestDto> rejectedRequests = new ArrayList<>();
        participationRequests.forEach(r -> {
            if (r.getStatus() == CONFIRMED) {
                confirmedRequests.add(toParticipationRequestDto(r));
            } else if (r.getStatus() == REJECTED) {
                rejectedRequests.add(toParticipationRequestDto(r));
            }
        });
        EventRequestStatusUpdateResult result = new EventRequestStatusUpdateResult();
        result.setConfirmedRequests(confirmedRequests);
        result.setRejectedRequests(rejectedRequests);
        return result;
    }
}
