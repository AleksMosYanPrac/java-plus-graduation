package ru.practicum.ewm.stats.statistics.dto;

import lombok.Data;
import ru.practicum.ewm.core.api.validation.DatableRange;
import ru.practicum.ewm.core.api.validation.DateRange;


import java.time.LocalDateTime;

@Data
@DateRange
public class StatRequest implements DatableRange {
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;
    private String[] uris;
    private Boolean isUnique;
}