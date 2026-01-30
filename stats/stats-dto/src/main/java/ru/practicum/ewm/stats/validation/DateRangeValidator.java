package ru.practicum.ewm.stats.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class DateRangeValidator implements ConstraintValidator<DateRange, DatableRange> {

    @Override
    public void initialize(DateRange constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(DatableRange value, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.nonNull(value.getRangeStart()) && Objects.nonNull(value.getRangeEnd())) {
            return value.getRangeStart().isBefore(value.getRangeEnd());
        } else {
            return true;
        }
    }
}