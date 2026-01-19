package ru.practicum.ewm.core.events.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.core.events.Location;
import ru.practicum.ewm.core.events.validation.EventDatable;
import ru.practicum.ewm.core.events.validation.EventFuture;

@Data
@EventFuture
public class UpdateEventAdminRequest implements EventDatable {
    private Long id;
    @Length(min = 20, max = 2000)
    private String annotation;
    private Long category;
    @Length(min = 20, max = 7000)
    private String description;
    private String eventDate;
    private Location location;
    private Boolean paid;
    @PositiveOrZero
    private Integer participantLimit;
    private Boolean requestModeration;
    private String stateAction;
    @Length(min = 3, max = 120)
    private String title;
}