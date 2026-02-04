package ru.practicum.ewm.core.api.validation;

import java.time.LocalDateTime;

public interface DatableRange {

    LocalDateTime getRangeStart();

    LocalDateTime getRangeEnd();
}