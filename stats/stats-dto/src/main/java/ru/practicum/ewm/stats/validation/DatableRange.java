package ru.practicum.ewm.stats.validation;

import java.time.LocalDateTime;

public interface DatableRange {

    LocalDateTime getRangeStart();

    LocalDateTime getRangeEnd();
}