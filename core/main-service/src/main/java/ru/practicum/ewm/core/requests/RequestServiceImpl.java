package ru.practicum.ewm.core.requests;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.core.events.Event;
import ru.practicum.ewm.core.events.EventRepository;
import ru.practicum.ewm.core.events.dto.State;
import ru.practicum.ewm.stats.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.stats.exceptions.NotFoundException;
import ru.practicum.ewm.core.requests.dto.ParticipationRequestDto;
import ru.practicum.ewm.core.requests.interfaces.RequestMapper;
import ru.practicum.ewm.core.requests.interfaces.RequestService;
import ru.practicum.ewm.core.users.User;
import ru.practicum.ewm.core.users.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.core.events.dto.State.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public List<ParticipationRequestDto> findUserRequests(Long userId) throws NotFoundException {
        log.info("get({})", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        return requestRepository.findAllByRequesterId(user.getId())
                .stream().map(requestMapper::toParticipationRequestDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ParticipationRequestDto addNewUserRequest(Long userId, Long eventId) throws NotFoundException, DataIntegrityViolation {
        log.info("create({}, {})", userId, eventId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found ID:" + userId));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Event not found ID:" + eventId));
        if (!event.getState().equals(PUBLISHED)) {
            throw new DataIntegrityViolation("Event state not required participation");
        }
        if (requestRepository.existsByEventAndRequester(event, user)) {
            throw new DataIntegrityViolation("Request already present");
        }
        if (event.getInitiator().equals(user)) {
            throw new DataIntegrityViolation("Вы уже участвуете в событии, будучи организатором");
        }
        if (!event.getRequestModeration()
            && event.getParticipantLimit() == requestRepository.findByEventId(eventId).size()) {
            throw new DataIntegrityViolation("Нет мест для участия в мероприятии");
        }
        ParticipationRequest request = new ParticipationRequest();
        request.setCreated(LocalDateTime.now());
        request.setEvent(event);
        request.setRequester(user);
        State status = event.getRequestModeration() ? PENDING : CONFIRMED;
        request.setStatus(event.getParticipantLimit() == 0 ? CONFIRMED : status);
        ParticipationRequest savedRequest = requestRepository.save(request);
        log.info("Запрос создан: {}", savedRequest);
        return requestMapper.toParticipationRequestDto(savedRequest);
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelUserRequest(Long userId, Long requestId) throws NotFoundException {
        log.info("cancel({}, {})", userId, requestId);
        ParticipationRequest request = requestRepository.findByIdAndRequesterId(requestId, userId)
                .orElseThrow(() -> new NotFoundException("Данные не найдены"));
        request.setStatus(CANCELED);
        return requestMapper.toParticipationRequestDto(requestRepository.save(request));
    }
}