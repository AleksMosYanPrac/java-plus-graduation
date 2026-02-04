package ru.practicum.ewm.core.api.contracts.compilations.dto;

import lombok.Data;
import ru.practicum.ewm.core.api.contracts.events.dto.EventShortDto;

import java.util.List;

@Data
public class CompilationDto {
    private Long id;
    private List<EventShortDto> events;
    private Boolean pinned;
    private String title;
}