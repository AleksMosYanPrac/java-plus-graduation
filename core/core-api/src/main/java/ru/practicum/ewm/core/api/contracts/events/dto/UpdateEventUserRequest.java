package ru.practicum.ewm.core.api.contracts.events.dto;

import lombok.Data;
import ru.practicum.ewm.core.api.validation.EventDatable;
import ru.practicum.ewm.core.api.validation.EventFuture;

@Data
@EventFuture
public class UpdateEventUserRequest implements EventDatable {
    private Long id;
    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private InitiatorStateAction stateAction;
    private String title;
}