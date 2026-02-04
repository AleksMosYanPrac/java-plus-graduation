package ru.practicum.ewm.core.compilations;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.core.api.contracts.events.dto.EventShortDto;
import ru.practicum.ewm.core.api.contracts.users.dto.UserShortDto;
import ru.practicum.ewm.core.categories.Category;
import ru.practicum.ewm.core.api.contracts.categories.dto.CategoryDto;
import ru.practicum.ewm.core.api.contracts.compilations.dto.CompilationDto;
import ru.practicum.ewm.core.compilations.interfaces.CompilationMapper;
import ru.practicum.ewm.core.events.Event;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class CompilationMapperImpl implements CompilationMapper {
    @Override
    public CompilationDto toDto(Compilation compilation) {
        CompilationDto compilationDto = new CompilationDto();
        compilationDto.setId(compilation.getId());
        compilationDto.setEvents(toEventShortDto(compilation.getEvents()));
        compilationDto.setTitle(compilationDto.getTitle());
        compilationDto.setPinned(compilation.getPinned());
        compilationDto.setTitle(compilation.getTitle());
        return compilationDto;
    }

    private List<EventShortDto> toEventShortDto(List<Event> events) {
        return events.stream().map(this::toEventShortDto).toList();
    }

    private EventShortDto toEventShortDto(Event event) {
        EventShortDto eventShortDto = new EventShortDto();
        eventShortDto.setId(event.getId());
        eventShortDto.setAnnotation(eventShortDto.getAnnotation());
        eventShortDto.setCategory(toCategoryDto(event.getCategory()));
        eventShortDto.setConfirmedRequests(eventShortDto.getConfirmedRequests());
        eventShortDto.setEventDate(event.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        eventShortDto.setInitiator(toInitiator(event.getInitiatorId(), event.getInitiatorName()));
        eventShortDto.setPaid(event.getPaid());
        eventShortDto.setTitle(event.getTitle());
        eventShortDto.setViews(event.getViews());
        return eventShortDto;
    }

    private CategoryDto toCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(categoryDto.getName());
        return categoryDto;
    }

    private UserShortDto toInitiator(Long initiatorId, String initiatorName) {
        UserShortDto userShortDto = new UserShortDto();
        userShortDto.setId(initiatorId);
        userShortDto.setName(initiatorName);
        return userShortDto;
    }
}