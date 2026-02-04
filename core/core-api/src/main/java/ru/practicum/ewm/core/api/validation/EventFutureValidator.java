package ru.practicum.ewm.core.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class EventFutureValidator implements ConstraintValidator<EventFuture, EventDatable> {

    @Override
    public boolean isValid(EventDatable value, ConstraintValidatorContext context) {
        try {
            if (Objects.nonNull(value.getEventDate())) {
                LocalDateTime eventDate = LocalDateTime.parse(value.getEventDate(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                return LocalDateTime.now().isBefore(eventDate);
            } else {
                return true;
            }
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}