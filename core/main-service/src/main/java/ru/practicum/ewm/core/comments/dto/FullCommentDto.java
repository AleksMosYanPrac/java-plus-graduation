package ru.practicum.ewm.core.comments.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FullCommentDto {
    private Long id;
    private Long eventId;
    private String text;
    private String authorName;
    private LocalDateTime created;
}