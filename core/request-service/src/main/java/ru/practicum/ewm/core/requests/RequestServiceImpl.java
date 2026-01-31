package ru.practicum.ewm.core.requests;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import ru.practicum.ewm.core.api.contracts.events.EventsFeignClient;
import ru.practicum.ewm.core.api.contracts.events.dto.EventFullDto;
import ru.practicum.ewm.core.api.contracts.events.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.core.api.contracts.events.dto.State;
import ru.practicum.ewm.core.api.contracts.requests.dto.Status;
import ru.practicum.ewm.core.api.contracts.users.UsersFeignClient;
import ru.practicum.ewm.core.api.contracts.users.dto.UserShortDto;
import ru.practicum.ewm.core.api.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;

import ru.practicum.ewm.core.api.contracts.requests.dto.ParticipationRequestDto;
import ru.practicum.ewm.core.requests.interfaces.RequestMapper;
import ru.practicum.ewm.core.requests.interfaces.RequestService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static ru.practicum.ewm.core.api.contracts.requests.dto.Status.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final UsersFeignClient usersClient;
    private final EventsFeignClient eventsClient;
    private final TransactionTemplate transactionTemplate;

    @Override
    public List<ParticipationRequestDto> findUserRequests(Long userId) throws NotFoundException {
        log.info("get({})", userId);
        UserShortDto user = findUserById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with Id:" + userId));
        return requestRepository.findAllByRequesterId(user.getId())
                .stream().map(requestMapper::toParticipationRequestDto).toList();
    }

    @Override
    public ParticipationRequestDto addNewUserRequest(Long userId, Long eventId) throws NotFoundException, DataIntegrityViolation {
        log.info("create({}, {})", userId, eventId);
        UserShortDto user = findUserById(userId)
                .orElseThrow(() -> new NotFoundException("User not found ID:" + userId));
        EventFullDto event = findEventById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found ID:" + eventId));
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new DataIntegrityViolation("Event state not required participation");
        }
        if (requestRepository.existsByEventIdAndRequesterId(event.getId(), user.getId())) {
            throw new DataIntegrityViolation("Request already present");
        }
        if (event.getInitiator().getId().equals(user.getId())) {
            throw new DataIntegrityViolation("User with id:" + userId + "is initiator of event");
        }
        if (!event.getRequestModeration()
            && event.getParticipantLimit() == requestRepository.findAllByEventId(eventId).size()) {
            throw new DataIntegrityViolation("Нет мест для участия в мероприятии");
        }
        return transactionTemplate.execute(transactionStatus -> {
            ParticipationRequest request = new ParticipationRequest();
            request.setCreated(LocalDateTime.now());
            request.setEventId(event.getId());
            request.setRequesterId(user.getId());
            Status status = event.getRequestModeration() ? PENDING : CONFIRMED;
            request.setStatus(event.getParticipantLimit() == 0 ? CONFIRMED : status);
            return requestMapper.toParticipationRequestDto(requestRepository.save(request));
        });
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelRequestByUser(Long userId, Long requestId) throws NotFoundException {
        log.info("cancel({}, {})", userId, requestId);
        ParticipationRequest request = requestRepository.findByIdAndRequesterId(requestId, userId)
                .orElseThrow(() -> new NotFoundException("Request not found Id:" + requestId));
        request.setStatus(CANCELED);
        return requestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    public List<ParticipationRequestDto> findAllRequestsByEventId(Long eventId) {
        log.info("find all requests for event with Id{}", eventId);
        return requestRepository.findAllByEventId(eventId)
                .stream()
                .map(requestMapper::toParticipationRequestDto)
                .toList();
    }

    @Override
    public boolean hasAllRequestHavePendingStatus(List<Long> requestIds) {
        log.info("Has request PENDING status Ids:{}", requestIds);
        return requestRepository.findAllById(requestIds).stream()
                .allMatch(r -> r.getStatus() == Status.PENDING);
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult updateStatusForRequests(Map<Long, Status> requestsWithStatus) {
        log.info("Update request statuses for:{}", requestsWithStatus);
        List<ParticipationRequest> requestList = requestRepository.findAllById(requestsWithStatus.keySet())
                .stream()
                .peek(r -> r.setStatus(requestsWithStatus.get(r.getId())))
                .toList();
        return requestMapper.toEventRequestStatusUpdateResult(requestRepository.saveAll(requestList));
    }

    private Optional<UserShortDto> findUserById(Long userId) {
        return Optional.ofNullable(usersClient.findById(userId));
    }

    private Optional<EventFullDto> findEventById(Long eventId) {
        return Optional.ofNullable(eventsClient.findById(eventId));
    }
}