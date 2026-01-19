package ru.practicum.ewm.core.events.interfaces;

import ru.practicum.ewm.core.events.Event;
import ru.practicum.ewm.core.events.dto.EventFullDto;
import ru.practicum.ewm.core.events.dto.EventShortDto;

public interface EventMapper {
    EventFullDto toEventFullDto(Event event);

    EventShortDto toEventShortDto(Event event);
}