package ru.practicum.ewm.core.events.interfaces;

import ru.practicum.ewm.core.api.contracts.events.dto.EventFullDto;
import ru.practicum.ewm.core.api.contracts.events.dto.EventShortDto;
import ru.practicum.ewm.core.events.Event;

public interface EventMapper {
    EventFullDto toEventFullDto(Event event);

    EventShortDto toEventShortDto(Event event);
}