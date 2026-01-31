package ru.practicum.ewm.core.api.contracts.events.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.core.api.validation.EventDatable;
import ru.practicum.ewm.core.api.validation.EventFuture;

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
    private AdminStateAction stateAction;
    @Length(min = 3, max = 120)
    private String title;
}