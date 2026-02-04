package ru.practicum.ewm.core.api.contracts.events.dto;

import lombok.Data;
import ru.practicum.ewm.core.api.contracts.categories.dto.CategoryDto;
import ru.practicum.ewm.core.api.contracts.comments.dto.CommentShort;
import ru.practicum.ewm.core.api.contracts.users.dto.UserShortDto;


import java.util.List;

@Data
public class EventFullDto {
    private Long id;
    private String annotation;
    private CategoryDto category;
    private String description;
    private String eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private String title;
    private Integer confirmedRequests;
    private String createdOn;
    private UserShortDto initiator;
    private String publishedOn;
    private State state;
    private Long views;
    private List<CommentShort> comments;
}