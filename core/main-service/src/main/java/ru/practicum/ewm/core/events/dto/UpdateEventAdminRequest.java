package ru.practicum.ewm.core.events.dto;

import lombok.Data;
import ru.practicum.ewm.core.events.Location;
import ru.practicum.ewm.core.events.validation.EventDatable;
import ru.practicum.ewm.core.events.validation.EventFuture;

@Data
@EventFuture
public class UpdateEventAdminRequest implements EventDatable {
    private Long id;
    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private String stateAction;
    private String title;
}