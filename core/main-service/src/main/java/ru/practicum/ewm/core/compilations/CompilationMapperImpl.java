package ru.practicum.ewm.core.compilations;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.core.categories.Category;
import ru.practicum.ewm.core.categories.dto.CategoryDto;
import ru.practicum.ewm.core.compilations.dto.CompilationDto;
import ru.practicum.ewm.core.compilations.interfaces.CompilationMapper;
import ru.practicum.ewm.core.events.Event;
import ru.practicum.ewm.core.events.dto.EventShortDto;
import ru.practicum.ewm.core.users.User;
import ru.practicum.ewm.core.users.dto.UserShortDto;

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
        eventShortDto.setInitiator(toUserShortDto(event.getInitiator()));
        eventShortDto.setPaid(event.getPaid());
        eventShortDto.setTitle(event.getTitle());
        eventShortDto.setViews(event.getViews());
        return eventShortDto;
    }

    private UserShortDto toUserShortDto(User user) {
        UserShortDto userShortDto = new UserShortDto();
        userShortDto.setId(user.getId());
        userShortDto.setName(user.getName());
        return userShortDto;
    }

    private CategoryDto toCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(categoryDto.getName());
        return categoryDto;
    }
}