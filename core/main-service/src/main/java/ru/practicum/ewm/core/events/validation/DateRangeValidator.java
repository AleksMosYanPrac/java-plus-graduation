package ru.practicum.ewm.core.events.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.practicum.ewm.core.events.Filter;

import java.util.Objects;

public class DateRangeValidator implements ConstraintValidator<DateRange, Filter> {

    @Override
    public void initialize(DateRange constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Filter filter, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.nonNull(filter.getRangeStart()) && Objects.nonNull(filter.getRangeEnd())) {
            return filter.getRangeStart().isBefore(filter.getRangeEnd());
        }else {
            return true;
        }
    }
}