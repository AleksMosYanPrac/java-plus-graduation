package ru.practicum.ewm.core.compilations.dto;

import lombok.Data;
import ru.practicum.ewm.core.events.dto.EventShortDto;

import java.util.List;

@Data
public class CompilationDto {
    private Long id;
    private List<EventShortDto> events;
    private Boolean pinned;
    private String title;
}