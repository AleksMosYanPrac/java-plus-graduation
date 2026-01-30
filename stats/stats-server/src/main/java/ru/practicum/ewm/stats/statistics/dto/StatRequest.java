package ru.practicum.ewm.stats.statistics.dto;

import lombok.Data;
import ru.practicum.ewm.stats.validation.DateRange;
import ru.practicum.ewm.stats.validation.DatableRange;

import java.time.LocalDateTime;

@Data
@DateRange
public class StatRequest implements DatableRange {
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;
    private String[] uris;
    private Boolean isUnique;
}