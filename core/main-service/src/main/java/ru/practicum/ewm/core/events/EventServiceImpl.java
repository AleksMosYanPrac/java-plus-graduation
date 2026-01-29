package ru.practicum.ewm.core.events;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.core.categories.Category;
import ru.practicum.ewm.core.categories.CategoryRepository;
import ru.practicum.ewm.core.events.dto.*;
import ru.practicum.ewm.core.events.interfaces.EventMapper;
import ru.practicum.ewm.core.events.interfaces.EventService;
import ru.practicum.ewm.core.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.exceptions.NotFoundException;
import ru.practicum.ewm.core.requests.ParticipationRequest;
import ru.practicum.ewm.core.requests.RequestRepository;
import ru.practicum.ewm.core.requests.dto.ParticipationRequestDto;
import ru.practicum.ewm.core.requests.interfaces.RequestMapper;
import ru.practicum.ewm.core.users.User;
import ru.practicum.ewm.core.users.UserRepository;
import ru.practicum.ewm.stats.client.StatsClient;
import ru.practicum.ewm.stats.dto.EndpointHitDto;
import ru.practicum.ewm.stats.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static ru.practicum.ewm.core.events.dto.State.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final StatsClient statsClient;
    private final CategoryRepository categoryRepository;
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;

    @Override
    public List<EventFullDto> findAllByCurrentUser(Long userId, Pageable pageable) {
        log.info("Find events by current User Id:{})", userId);
        return eventRepository.findAllByInitiatorId(userId, pageable)
                .stream()
                .map(eventMapper::toEventFullDto)
                .toList();
    }

    @Override
    @Transactional
    public EventFullDto addNewEvent(Long userId, NewEventDto event) throws DataIntegrityViolation {
        log.info("Create new Event by current User:{}, Title:{}", userId, event.getTitle());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataIntegrityViolation("User required for Event:" + event.getTitle()));
        Category category = categoryRepository.findById(event.getCategory())
                .orElseThrow(() -> new DataIntegrityViolation("Category required for Event:" + event.getTitle()));
        Location location = findLocation(event.getLocation());
        Event newEvent = new Event();
        newEvent.setAnnotation(event.getAnnotation());
        newEvent.setCategory(category);
        newEvent.setDescription(event.getDescription());
        newEvent.setEventDate(parse(event.getEventDate()));
        newEvent.setLocation(location);
        newEvent.setPaid(event.getPaid() != null ? event.getPaid() : false);
        newEvent.setParticipantLimit(event.getParticipantLimit() != null ? event.getParticipantLimit() : 0);
        newEvent.setRequestModeration(event.getRequestModeration() != null ? event.getRequestModeration() : true);
        newEvent.setTitle(event.getTitle());
        newEvent.setConfirmedRequests(0);
        newEvent.setCreatedOn(LocalDateTime.now());
        newEvent.setInitiator(user);
        newEvent.setPublishedOn(null);
        newEvent.setState(PENDING);
        newEvent.setViews(0);
        return eventMapper.toEventFullDto(eventRepository.save(newEvent));
    }

    @Override
    public EventFullDto findByIdByCurrentUser(Long userId, Long eventId) throws NotFoundException {
        log.info("Find Event by current User:{}, Event:{}", userId, eventId);
        Event event = eventRepository.findByIdAndInitiatorId(eventId, userId)
                .orElseThrow(() -> new NotFoundException("Not found event:" + eventId + " for user:" + userId));
        return eventMapper.toEventFullDto(event);
    }

    @Override
    @Transactional
    public EventFullDto updateByIdByCurrentUser(Long userId, Long eventId, UpdateEventUserRequest event)
            throws NotFoundException, DataIntegrityViolation {
        log.info("Update Event by current User{} Event:{}", userId, eventId);
        Event updatedEvent = eventRepository.findByIdAndInitiatorId(eventId, userId)
                .orElseThrow(() -> new NotFoundException("Not found event:" + eventId + " for user:" + userId));
        if (updatedEvent.getState().equals(PUBLISHED)) {
            throw new DataIntegrityViolation("Can not update event" + eventId + " at state Publish");
        }
        if (event.getAnnotation() != null) {
            updatedEvent.setAnnotation(event.getAnnotation());
        }
        if (event.getCategory() != null) {
            Category category = categoryRepository.findById(event.getCategory())
                    .orElseThrow(() -> new NotFoundException("Category not found Id:" + event.getCategory()));
            updatedEvent.setCategory(category);
        }
        if (event.getDescription() != null) {
            updatedEvent.setDescription(event.getDescription());
        }
        if (event.getParticipantLimit() != null) {
            updatedEvent.setParticipantLimit(event.getParticipantLimit());
        }
        if (event.getLocation() != null) {
            Location location = findLocation(event.getLocation());
            updatedEvent.setLocation(location);
        }
        if (event.getPaid() != null) {
            updatedEvent.setPaid(event.getPaid());
        }
        if (event.getParticipantLimit() != null) {
            updatedEvent.setParticipantLimit(event.getParticipantLimit());
        }
        if (event.getRequestModeration() != null) {
            updatedEvent.setRequestModeration(event.getRequestModeration());
        }
        if (event.getTitle() != null) {
            updatedEvent.setTitle(event.getTitle());
        }
        if (event.getStateAction() != null) {
            switch (State.valueOf(event.getStateAction())) {
                case PUBLISH_EVENT -> updatedEvent.setState(PUBLISHED);
                case REJECT_EVENT, CANCEL_REVIEW -> updatedEvent.setState(CANCELED);
                case SEND_TO_REVIEW -> updatedEvent.setState(PENDING);
            }
        }
        updatedEvent.setPublishedOn(LocalDateTime.now());
        return eventMapper.toEventFullDto(eventRepository.save(updatedEvent));
    }


    @Override
    public List<ParticipationRequestDto> findParticipationRequests(Long userId, Long eventId) {
        log.info("Find event participation requests by User:{} Event:{}", userId, eventId);
        Event event = eventRepository.findByIdAndInitiatorId(eventId, userId)
                .orElseGet(Event::new);
        return requestRepository.findAllByEvent(event).stream().map(requestMapper::toParticipationRequestDto).toList();
    }

    @Override
    public EventRequestStatusUpdateResult updateParticipationStatus(Long userId,
                                                                    Long eventId,
                                                                    EventRequestStatusUpdateRequest request)
            throws NotFoundException, DataIntegrityViolation {
        log.info("Update event participation status User:{}, Event:{}", userId, eventId);
        Event event = eventRepository.findByIdAndInitiatorId(eventId, userId)
                .orElseThrow(() -> new NotFoundException("Not found event:" + eventId + " for user:" + userId));
        List<ParticipationRequest> requests = requestRepository.findAllByEvent(event);
        List<Long> processingRequests = request.getRequestIds();
        if (request.getStatus().equals(CONFIRMED)) {
            long confirmed = requests.stream().filter(req -> req.getStatus().equals(CONFIRMED)).count();
            if ((confirmed + processingRequests.size()) > event.getParticipantLimit()
                || !event.getRequestModeration()) {
                throw new DataIntegrityViolation("Participation limit is overflow");
            }
        }
        long confirmedRequests = requests.stream()
                .filter(req -> processingRequests.contains(req.getId()) && req.getStatus().equals(CONFIRMED)).count();
        if (confirmedRequests >= 1) {
            throw new DataIntegrityViolation("Participation request already confirmed");
        }
        List<ParticipationRequestDto> participationRequests = requests.stream()
                .filter(req -> request.getRequestIds().contains(req.getId()))
                .peek(r -> r.setStatus(request.getStatus())).peek(requestRepository::save)
                .map(requestMapper::toParticipationRequestDto).toList();
        EventRequestStatusUpdateResult result = new EventRequestStatusUpdateResult();
        if (request.getStatus().equals(REJECTED)) {
            result.setRejectedRequests(participationRequests);
        } else {
            result.setConfirmedRequests(participationRequests);
        }
        return result;
    }

    @Override
    public List<EventShortDto> findEventsByFilter(Filter filter,
                                                  HttpServletRequest request) {
        log.info("Search Event by filter");
        Page<Event> events = eventRepository.findAll(filter.getPredicate(), filter.getPage());
        statsClient.postHit(mapToHitDto(request));
        return switch (filter.getSort()) {
            case VIEWS -> events
                    .stream()
                    .peek(this::calculateView)
                    .sorted(Comparator.comparing(Event::getViews))
                    .map(eventMapper::toEventShortDto)
                    .toList();
            case EVENT_DATE -> events
                    .stream()
                    .peek(this::calculateView)
                    .filter(e -> Objects.nonNull(e.getEventDate()))
                    .sorted(Comparator.comparing(Event::getEventDate))
                    .map(eventMapper::toEventShortDto)
                    .toList();
        };
    }

    @Override
    public EventFullDto findEventById(Long eventId, HttpServletRequest request) throws NotFoundException {
        log.info("Find published event with Id:{}", eventId);
        Event event = eventRepository.findByIdAndState(eventId, PUBLISHED)
                .orElseThrow(() -> new NotFoundException("Event not found ID:" + eventId));
        statsClient.postHit(mapToHitDto(request));
        return eventMapper.toEventFullDto(calculateView(event));
    }

    @Override
    public List<EventFullDto> findEventsByFilterByAdmin(Filter filter) {
        log.info("searchEventsByAdmin by Filter");
        return eventRepository.findAll(filter.getAdminPredicate(), filter.getSortedPage(ASC, "id"))
                .getContent()
                .stream()
                .map(eventMapper::toEventFullDto)
                .peek(event -> event.setConfirmedRequests((int) requestRepository.findByEventId(event.getId())
                        .stream()
                        .filter(request -> request.getStatus().equals(CONFIRMED)).count()))
                .toList();
    }

    @Override
    public EventFullDto updateEventByIdByAdmin(Long eventId, UpdateEventAdminRequest event)
            throws NotFoundException, DataIntegrityViolation {
        log.info("Update Event by admin Id:{}", eventId);
        Event updatedEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found ID:" + eventId));
        if (!(updatedEvent.getState() == PENDING)) {
            throw new DataIntegrityViolation("Can not update event" + eventId + " at state not Pending");
        }
        if (LocalDateTime.now().isAfter(updatedEvent.getEventDate().minusHours(1))) {
            throw new DataIntegrityViolation("Can not update at 1 hour before start event");
        }
        if (event.getAnnotation() != null) {
            updatedEvent.setAnnotation(event.getAnnotation());
        }
        if (event.getCategory() != null) {
            Category category = categoryRepository.findById(event.getCategory())
                    .orElseThrow(() -> new NotFoundException("Category not found Id:" + event.getCategory()));
            updatedEvent.setCategory(category);
        }
        if (event.getDescription() != null) {
            updatedEvent.setDescription(event.getDescription());
        }
        if (event.getParticipantLimit() != null) {
            updatedEvent.setParticipantLimit(event.getParticipantLimit());
        }
        if (event.getLocation() != null) {
            Location location = findLocation(event.getLocation());
            updatedEvent.setLocation(location);
        }
        if (event.getPaid() != null) {
            updatedEvent.setPaid(event.getPaid());
        }
        if (event.getParticipantLimit() != null) {
            updatedEvent.setParticipantLimit(event.getParticipantLimit());
        }
        if (event.getRequestModeration() != null) {
            updatedEvent.setRequestModeration(event.getRequestModeration());
        }
        if (event.getTitle() != null) {
            updatedEvent.setTitle(event.getTitle());
        }
        if (event.getStateAction() != null) {
            switch (State.valueOf(event.getStateAction())) {
                case PUBLISH_EVENT -> updatedEvent.setState(PUBLISHED);
                case REJECT_EVENT, CANCEL_REVIEW -> updatedEvent.setState(CANCELED);
            }
        }
        updatedEvent.setPublishedOn(LocalDateTime.now());
        return eventMapper.toEventFullDto(eventRepository.save(updatedEvent));
    }

    private Location findLocation(Location event) {
        return locationRepository.findByLatAndLon(event.getLat(), event.getLon())
                .orElseGet(() -> {
                    Location newLocation = new Location();
                    newLocation.setLat(event.getLat());
                    newLocation.setLon(event.getLon());
                    return locationRepository.save(newLocation);
                });
    }

    private LocalDateTime parse(String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private Event calculateView(Event event) {
        log.info("Calculate views for event:{}", event.getId());
        try {
            List<ViewStatsDto> viewStatsDto = statsClient.getStatistics(
                    LocalDateTime.now().minusDays(2),
                    LocalDateTime.now().plusDays(2),
                    new String[]{"/events/" + event.getId()},
                    true
            );
            long views = 0L;
            if (viewStatsDto != null && !viewStatsDto.isEmpty()) {
                views = viewStatsDto.getFirst().getHits() != null ? viewStatsDto.getFirst().getHits() : 0L;
            }
            event.setViews((int) views);
            return event;
        } catch (Exception e) {
            log.error("Exception at request info from stat server {}", event.getId(), e);
            return event;
        }
    }

    private EndpointHitDto mapToHitDto(HttpServletRequest request) {
        EndpointHitDto endpointHit = new EndpointHitDto();
        endpointHit.setApp("event");
        endpointHit.setIp(request.getRemoteAddr());
        endpointHit.setUri(request.getRequestURI());
        endpointHit.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return endpointHit;
    }
}