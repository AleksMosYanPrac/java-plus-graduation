package ru.practicum.ewm.core.events;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.core.api.contracts.events.dto.EventFullDto;
import ru.practicum.ewm.core.api.contracts.events.dto.EventShortDto;
import ru.practicum.ewm.core.api.contracts.events.dto.Location;
import ru.practicum.ewm.core.api.contracts.users.dto.UserShortDto;
import ru.practicum.ewm.core.categories.Category;
import ru.practicum.ewm.core.api.contracts.categories.dto.CategoryDto;
import ru.practicum.ewm.core.events.interfaces.EventMapper;

import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class EventMapperImpl implements EventMapper {

    @Override
    public EventFullDto toEventFullDto(Event event) {
        EventFullDto eventFullDto = new EventFullDto();
        eventFullDto.setId(event.getId());
        eventFullDto.setAnnotation(event.getAnnotation());
        eventFullDto.setCategory(toCategoryDto(event.getCategory()));
        eventFullDto.setDescription(event.getDescription());
        eventFullDto.setEventDate(event.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        eventFullDto.setLocation(toLocation(event.getLocation()));
        eventFullDto.setPaid(event.getPaid());
        eventFullDto.setParticipantLimit(event.getParticipantLimit());
        eventFullDto.setRequestModeration(event.getRequestModeration());
        eventFullDto.setTitle(event.getTitle());
        eventFullDto.setConfirmedRequests(event.getConfirmedRequests());
        eventFullDto.setCreatedOn(event.getCreatedOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        eventFullDto.setInitiator(toInitiator(event.getInitiatorId(), event.getInitiatorName()));
        if (event.getPublishedOn() != null) {
            eventFullDto.setPublishedOn(event.getPublishedOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        } else {
            eventFullDto.setPublishedOn(null);
        }
        eventFullDto.setState(event.getState());
        eventFullDto.setViews(event.getViews());
        //eventFullDto.setComments(commentRepository.getCommentsByEventId(event.getId()));
        return eventFullDto;
    }

    @Override
    public EventShortDto toEventShortDto(Event event) {
        EventShortDto eventShortDto = new EventShortDto();
        eventShortDto.setId(event.getId());
        eventShortDto.setAnnotation(event.getAnnotation());
        eventShortDto.setCategory(toCategoryDto(event.getCategory()));
        eventShortDto.setConfirmedRequests(event.getConfirmedRequests());
        eventShortDto.setEventDate(event.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        eventShortDto.setInitiator(toInitiator(event.getInitiatorId(), event.getInitiatorName()));
        eventShortDto.setPaid(event.getPaid());
        eventShortDto.setTitle(event.getTitle());
        eventShortDto.setViews(event.getViews());
        return eventShortDto;
    }

    private UserShortDto toInitiator(Long initiatorId, String initiatorName) {
        UserShortDto userShortDto = new UserShortDto();
        userShortDto.setId(initiatorId);
        userShortDto.setName(initiatorName);
        return userShortDto;
    }

    private CategoryDto toCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    Location toLocation(ru.practicum.ewm.core.events.Location location) {
        Location dto = new Location();
        dto.setLat(location.getLat());
        dto.setLon(location.getLon());
        return dto;
    }
}