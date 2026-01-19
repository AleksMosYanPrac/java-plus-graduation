package ru.practicum.ewm.core.events.dto;

import lombok.Data;
import ru.practicum.ewm.core.categories.dto.CategoryDto;
import ru.practicum.ewm.core.users.dto.UserShortDto;

@Data
public class EventShortDto {
    private Long id;
    private String annotation;
    private CategoryDto category;
    private Integer confirmedRequests;
    private String eventDate;
    private UserShortDto initiator;
    private Boolean paid;
    private String title;
    private Integer views;
}